package com.zt.homework.service;

import com.zt.homework.Utils.ConnUtill;
import com.zt.homework.dao.TaskDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.entity.Task;
import com.zt.homework.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailReceiveServiceTest {
    @Autowired
    private TaskDao taskDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailReceiveService mailReceiveService;

    @Test
    public void queryMail() throws Exception {
        List<User> teaUserList = userDao.queryAllTeaTypeUser();
        for(User tea : teaUserList) {
            System.out.println("---------" + tea.getUsername() + "---------");

            String personalMail = tea.getPersonalMail();
            String workMail = tea.getWorkMail();
            String workMailPwd = tea.getWorkMailPwd();
            String host = ConnUtill.getPOP3Host(workMail);

            Store store = ConnUtill.popConnect(host, workMail, workMailPwd);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();

            List<Task> taskList = taskDao.queryTaskByUserId(tea.getUserId());

            for(Task task : taskList) {
                System.out.println("------" + task.getTaskName() + "------");
                mailReceiveService.queryMail(messages, task);
            }
        }
    }
}