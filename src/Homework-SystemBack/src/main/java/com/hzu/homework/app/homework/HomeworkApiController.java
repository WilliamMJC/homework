package com.hzu.homework.app.homework;

import java.io.File;
import java.util.List;

import javax.mail.Message;
import javax.mail.Store;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.emailreceive.EmailReceiveService;
import com.hzu.homework.sysback.course.service.CourseService;
import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.sysback.homework.service.HomeworkService;
import com.hzu.homework.sysback.homework.vo.HomeworkModel;
import com.hzu.homework.sysback.stucourserel.service.StudentCourseRelService;
import com.hzu.homework.sysback.teacourserel.service.TeacherCourseRelService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.sysback.worksturel.service.WorkStuRelService;
import com.hzu.homework.util.ConnUtil;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.FileUtil;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.MailSendUtil;
import com.hzu.homework.util.StringUtil;
import com.hzu.homework.util.TokenUtil;
import com.hzu.homework.util.ZipUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("api/homework")
public class HomeworkApiController {
	private String path = "c:/emailHomework/";
	private HomeworkService myService;
	@Autowired
	private UserService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentCourseRelService stuRelService;
	@Autowired
	private EmailReceiveService emailReceiveService;
	@Autowired
	private TeacherCourseRelService teacherRelService;
	@Autowired
	private WorkStuRelService workStuService;
	@Autowired
	public void setMyDao(HomeworkService dao) {
		this.myService=dao;
	}
	
	/**
	 * ������ҵ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/addHomework" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String addHomework(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");
		String homeworkTitle = request.getParameter("homeworkTitle");
		String courseName  = request.getParameter("courseName");
		String content =  request.getParameter("content");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);
		if(user.getUserType().equals("2")) {
			String courseUuid =courseService.getUuidByTAndCN(uuid, courseName);
			if(!StringUtil.isEmpty(courseUuid)) {
				//ͨ���γ�uuid��ѯ��ҵ�����Ƿ���ڸÿγ���ҵ�����������򷵻ؿգ������ڰ��մ�����������ȡ��һ����ҵ
				//���ؿգ�������һ����ҵ
				//���� ���´�����ҵ��������һ�����ô�����ҵ
				HomeworkModel lastHomework = myService.getLastHomework(uuid, courseUuid);
				HomeworkModel homework = new HomeworkModel();
				int stuNum = stuRelService.getStudentCountByCourseUuid(courseUuid);				
				homework.setCourseUuid(courseUuid);
				homework.setTeacherUuid(uuid);
				homework.setAllNum(stuNum+"");
				homework.setTitle(homeworkTitle);
				homework.setContext(content);
				if(lastHomework==null) {
					homework.setTimes(1);	
				}else {
					int nowTimes = lastHomework.getTimes()+1;
					homework.setTimes(nowTimes);							
				}
				myService.create(homework);				
				array.put("code", "success");
				array.put("msg", "�����ɹ�");			
			}else {
				array.put("code", "false");
				array.put("msg", "δ�ҵ��ÿγ�");
			}
		}else {
			array.put("code", "false");
			array.put("msg", "��Ȩ�޲���");
		}	
		return  JSONUtil.parseJSON(array);
	}
	
	
	/**
	 * ��ȡ��ҵ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getHomework" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getHomework(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();	
		String token = request.getParameter("token");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);	
		if(user.getUserType().equals("2")) {
			List<HomeworkModel> list=myService.getByTeacherUuid(uuid);
			for(HomeworkModel item:list) {
				JSONObject object = new JSONObject();
				String courseName = courseService.getByUuid(item.getCourseUuid()).getCourseName();
				int allNum=stuRelService.getStudentCountByCourseUuid(item.getCourseUuid());
				object.put("course",courseName );
				object.put("title",item.getTitle());
				object.put("content",item.getContext());
				object.put("submitted", item.getSubmittedNum());
				object.put("students",""+allNum);
				String updateTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getOprTime()));
				String createTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getCreateTime()));
				object.put("update_time",updateTime);
				object.put("issue_time",createTime);
				object.put("submit_state","���ύ");
				object.put("homeworkUuid", item.getUuid());
				object.put("teacherUuid", item.getTeacherUuid());
				object.put("teacherName", user.getLoginName());
				String state=item.getState();
				if(state.equals("0")) {
					object.put("receive_state","δ��ȡ");
				}else {
					object.put("receive_state","����ȡ");
				}
				arrays.add(object);
			}
			array.put("data", arrays.toString());
			array.put("code", "success");			
		}else if(user.getUserType().equals("1")) {
			if(!StringUtil.isEmpty(user.getStudentNo())) {
				List<String> list=stuRelService.getCourseByNo(user.getStudentNo());
				for(String item2:list) {
					String teacherUuid = teacherRelService.getTeacherUuidByCourseUuid(item2);
					String teacherName= userService.getByUuid(teacherUuid).getLoginName();
					List<HomeworkModel> homeworkList=myService.getByTeacherUuid(teacherUuid);
					for(HomeworkModel item:homeworkList) {
						JSONObject object = new JSONObject();
						String courseName = courseService.getByUuid(item.getCourseUuid()).getCourseName();
						int allNum=stuRelService.getStudentCountByCourseUuid(item.getCourseUuid());
						object.put("course",courseName );
						object.put("title",item.getTitle());
						object.put("content",item.getContext());
						object.put("submitted", item.getSubmittedNum());
						object.put("students",""+allNum);
						String updateTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getOprTime()));
						String createTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getCreateTime()));
						object.put("update_time",updateTime);
						object.put("issue_time",createTime);
						object.put("homeworkUuid", item.getUuid());
						object.put("teacherUuid", item.getTeacherUuid());
						object.put("teacherName", teacherName);
						boolean is=workStuService.isSubmit(item.getUuid(), user.getStudentNo());
						if(is) {
							object.put("submit_state","���ύ");
						}else {
							object.put("submit_state","δ�ύ");
						}
						String state=item.getState();
						if(state.equals("0")) {
							object.put("receive_state","δ��ȡ");
						}else {
							object.put("receive_state","����ȡ");
						}
						arrays.add(object);
					}
					array.put("data", arrays.toString());
					array.put("code", "success");									
				}
			}
					
		}else {
			array.put("data", arrays.toString());
			array.put("code", "false");		
		}
		return  JSONUtil.parseJSON(array);	
	}
	
	/**
	 * ������ҵ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateHomework" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateHomework(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();	
		String token = request.getParameter("token");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);		
		if(user.getUserType().equals("2")) {
			//���²���
			Store store = ConnUtil.login("pop.qq.com", user.getEmail(), user.getEmailPwd());
			Message[] messages=emailReceiveService.getAllMessage(store);
			emailReceiveService.getHomeworkModel(messages, uuid);		
			List<HomeworkModel> list=myService.getByTeacherUuid(uuid);
			for(HomeworkModel item:list) {
				JSONObject object = new JSONObject();
				String courseName = courseService.getByUuid(item.getCourseUuid()).getCourseName();
				int allNum=stuRelService.getStudentCountByCourseUuid(item.getCourseUuid());
				object.put("course",courseName );
				object.put("title",item.getTitle());
				object.put("content",item.getContext());
				object.put("submitted", item.getSubmittedNum());
				object.put("students",""+allNum);
				String updateTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getOprTime()));
				String createTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getCreateTime()));
				object.put("update_time",updateTime);
				object.put("issue_time",createTime);
				object.put("homeworkUuid", item.getUuid());
				object.put("teacherUuid", item.getTeacherUuid());
				object.put("teacherName", user.getLoginName());
				object.put("submit_state","");
				String state=item.getState();
				if(state.equals("0")) {
					object.put("receive_state","δ��ȡ");
				}else {
					object.put("receive_state","����ȡ");
				}
				arrays.add(object);
			}
			array.put("data", arrays.toString());
			array.put("code", "success");			
		}else if(user.getUserType().equals("1")) {
			if(!StringUtil.isEmpty(user.getStudentNo())) {
				List<String> list=stuRelService.getCourseByNo(user.getStudentNo());
				for(String item2:list) {
					String teacherUuid = teacherRelService.getTeacherUuidByCourseUuid(item2);
					//���²���
					UserModel teacher = userService.getByUuid(teacherUuid);
					Store store = ConnUtil.login("pop.qq.com", teacher.getEmail(), teacher.getEmailPwd());
					Message[] messages=emailReceiveService.getAllMessage(store);
					emailReceiveService.getHomeworkModel(messages, teacherUuid);
					
					List<HomeworkModel> homeworkList=myService.getByTeacherUuid(teacherUuid);
					for(HomeworkModel item:homeworkList) {
						JSONObject object = new JSONObject();
						String courseName = courseService.getByUuid(item.getCourseUuid()).getCourseName();
						int allNum=stuRelService.getStudentCountByCourseUuid(item.getCourseUuid());
						object.put("course",courseName );
						object.put("title",item.getTitle());
						object.put("content",item.getContext());
						object.put("submitted", item.getSubmittedNum());
						object.put("students",""+allNum);
						String updateTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getOprTime()));
						String createTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getCreateTime()));
						object.put("update_time",updateTime);
						object.put("issue_time",createTime);
						object.put("homeworkUuid", item.getUuid());
						object.put("teacherUuid", item.getTeacherUuid());
						object.put("teacherName", teacher.getLoginName());
						boolean is=workStuService.isSubmit(item.getUuid(), user.getStudentNo());
						if(is) {
							object.put("submit_state","���ύ");
						}else {
							object.put("submit_state","δ�ύ");
						}
						String state=item.getState();
						if(state.equals("0")) {
							object.put("receive_state","δ��ȡ");
						}else {
							object.put("receive_state","����ȡ");
						}
						arrays.add(object);
					}
					array.put("data", arrays.toString());
					array.put("code", "success");									
				}
			}
			
		}else {
			array.put("data", arrays.toString());
			array.put("code", "false");		
		}
		return  JSONUtil.parseJSON(array);	
	}
	
	/**
	 * ��ҵ��ȡ����
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/collectHomework" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String collectHomework(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String separator = System.getProperty("file.separator");
		String homeworkUuid = request.getParameter("homeworkUuid");
		String token = request.getParameter("token");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);
		if(user.getUserType().equals("2")) {
			HomeworkModel homework = myService.getByUuid(homeworkUuid);		
			String submitNUm=homework.getSubmittedNum();
			CourseModel course = courseService.getByUuid(homework.getCourseUuid());
			String courseName=course.getCourseName();
			if(submitNUm.equals("0")) {
				array.put("code", "nonew");
			}else {
				//���ز���
				Store store = ConnUtil.login("pop.qq.com", user.getEmail(), user.getEmailPwd());
				Message[] messages=emailReceiveService.getAllMessage(store);
				String ret=emailReceiveService.collectEmail(messages, homework);
				//������ɽ����ļ�ѹ��
				if(ret.equals("success")) {
					String zipPath = path+separator+user.getLoginName()+separator+courseName+separator+"��ҵ��ȡѹ���ļ�";
					path+=user.getLoginName()+separator+courseName+separator+"��ҵ"+homework.getTimes();
					File file=new File(path);
					File zipfile=new File(zipPath);
					if(!zipfile.exists()){
						zipfile.mkdirs();
					}
					//��ҵѹ��zip
					ZipUtil.mailToZip(file.toString(), zipfile.toString(), "��ҵ"+homework.getTimes());
					String zipFilePath2=zipPath+separator+"��ҵ"+homework.getTimes()+".zip";
					File zipFilePath=new File(zipFilePath2);
					MailSendUtil mailsend =new MailSendUtil("smtp.qq.com","1050416617@qq.com", "jwovgwaypwrebecd");					
					mailsend.send(user.getPrivEmail(),courseName+"��"+homework.getTimes()+"����ҵ","�뼰ʱ����",zipFilePath.toString(),"�շ���ҵϵͳ");
					//����ѹ���ļ���С
					File zippath = new File(zipPath+separator+"��ҵ"+homework.getTimes()+".zip");
					long zipSize =new FileUtil().getFileSizes(zippath);
					FileUtil g = new FileUtil();
					//�ļ���
        			long filenumber= g.getlist(file);
        			homework.setFileName("��ҵ"+homework.getTimes());
        			homework.setFileSize(zipSize+"");
        			homework.setState("1");      		
        			myService.update(homework);
        			array.put("zip_name", "��ҵ"+homework.getTimes());
        			array.put("zip_size", zipSize);
        			array.put("amount", filenumber);
        			array.put("code","success");
				}
			}
			
		}
		return  JSONUtil.parseJSON(array);	
	}

}
