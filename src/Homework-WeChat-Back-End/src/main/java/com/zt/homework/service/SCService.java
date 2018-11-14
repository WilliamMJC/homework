package com.zt.homework.service;

import com.zt.homework.Utils.ConnUtill;
import com.zt.homework.Utils.IOUtil;
import com.zt.homework.Utils.ParseMimeMessage;
import com.zt.homework.config.AppContext;
import com.zt.homework.dao.CourseMemberDao;
import com.zt.homework.dao.SCDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.entity.SC;
import com.zt.homework.entity.User;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import com.zt.homework.exception.MailException;
import com.zt.homework.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SCService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SCService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private SCDao scDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    public List<String> getInitStuIdList(Integer userId, String courseName, Integer courseId) throws Exception {
        InputStream in = getInitMailFile(userId, courseName);
        List<String> stuIdList = IOUtil.readTxt(in);
        List<SC> scList = new ArrayList<>();
        for(String stuId : stuIdList) {
            SC sc = new SC();
            sc.setStuId(stuId);
            sc.setCourseId(courseId);
            scList.add(sc);
        }
        scDao.insertSCs(scList);

        return stuIdList;
    }

    /**
     * 获取学生列表附件输入流
     * @param userId
     * @param courseName
     * @return
     * @throws Exception
     */
    private InputStream getInitMailFile(Integer userId, String courseName) throws Exception {
        InputStream in = null;

        User user = userDao.queryUserByUserId(userId);
        String personalMail = user.getPersonalMail();
        String workMail = user.getWorkMail();
        String workMailPwd = user.getWorkMailPwd();
        String host = ConnUtill.getPOP3Host(workMail);

        Store store = ConnUtill.popConnect(host, workMail, workMailPwd);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();

        for (Message message : messages) {
            ParseMimeMessage pmm = new ParseMimeMessage((MimeMessage) message);
            String mailFrom = pmm.getMailFromString();
            if(mailFrom.equals(personalMail)) {
                if(pmm.getSubject().equals(courseName + "-学生列表")) {
                    in = pmm.isContainAttach(message);
                    if (in == null) {
                        throw new MailException(ResultEnum.MAIL_WITHOUT_ATTACH_FILE);
                    } else if (!pmm.getAttachFileName(message).endsWith(".txt")) {
                        throw new MailException((ResultEnum.ATTACH_FILE_NOT_ACCEPT));
                    }
                    break;
                }
            }
        }

        return in;
    }

    public void updateSC(Integer courseId, String stuId, String newStuId) {
        isCurrentCourseTea(courseId);
        SC sc = new SC();
        sc.setCourseId(courseId);
        sc.setStuId(stuId);
        Integer num = scDao.updateSCBySC(newStuId, sc);
        if(num == null || num < 0) {
            throw new ServerException(ResultEnum.SERVER_ERROR);
        }
    }

    /**
     * 当前用户是否对此课程有完整的权限
     *
     * @param courseId
     * @return
     */
    private void isCurrentCourseTea(Integer courseId) {
        Integer userId = AppContext.getCurrentUserId();
        Integer permission = courseMemberDao.queryPermissionByCM(courseId, userId);
        if (permission == null || permission != 4) {
            LOGGER.warn("该用户不能进行此项操作");
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        }
    }
}
