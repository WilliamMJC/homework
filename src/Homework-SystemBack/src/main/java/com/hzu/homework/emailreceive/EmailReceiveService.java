package com.hzu.homework.emailreceive;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.hzu.homework.sysback.course.service.CourseService;
import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.sysback.homework.service.HomeworkService;
import com.hzu.homework.sysback.homework.vo.HomeworkModel;
import com.hzu.homework.sysback.statistics.service.StatisticsService;
import com.hzu.homework.sysback.statistics.vo.StatisticsModel;
import com.hzu.homework.sysback.stucourserel.service.StudentCourseRelService;
import com.hzu.homework.sysback.stucourserel.vo.StudentCourseRelModel;
import com.hzu.homework.sysback.teacourserel.service.TeacherCourseRelService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.sysback.worksturel.service.WorkStuRelService;
import com.hzu.homework.sysback.worksturel.vo.WorkStuRelModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.FileUtil;
import com.hzu.homework.util.IOUtil;
import com.hzu.homework.util.PraseMimeMessage;
import com.hzu.homework.util.RandomUtil;

@Service
public class EmailReceiveService {
	private String dateformat="yy-MM-dd HH:mm";//Ĭ�ϵ���ǰ��ʾ��ʽ
	private String path = "c:/emailHomework/";
	@Autowired  
    private TaskExecutor executor;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private StudentCourseRelService courseRelService;
	@Autowired
	private TeacherCourseRelService teacherRelService;
	@Autowired
	private HomeworkService homeworkService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private WorkStuRelService workStuService;
	
	
	public  Message[] getAllMessage(Store store) throws MessagingException {
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // ȡ�����е��ʼ�
            Message[] messages = folder.getMessages();
            return messages;
        }
	}
	
	/**
	 * �����γ̹���
	 * @param messages
	 * @param userUuid
	 * @return
	 * @throws Exception
	 */
	public CourseModel getCourseModel(Message[] messages,String userUuid) throws Exception {
		String separator = System.getProperty("file.separator");
		String userName= userService.getByUuid(userUuid).getUserName();
		for (int i = 0; i < messages.length; i++) {
        	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
        	System.out.println("�ʼ�����"+pmm.getSubject());
        	System.out.println("------------��ʼ�ж�-------------");
        	if(pmm.getSubject().startsWith("[ѧ��]")){
        		if(pmm.isContainAttach((Part)messages[i])){
        			String courseName = pmm.getSubject().substring(4);  
        			File file=new File(path+userName+separator+"course");
        			if(!file.exists()){
        				file.mkdirs();
        			}
        			pmm.setAttachPath(file.toString());
        			pmm.saveAttachMent((Part)messages[i]);  
        			String fileName=pmm.getFilename((Part)messages[i]);
        			CourseModel course = new CourseModel();
        			course.setCourseName(courseName);
        			String uuid = RandomUtil.generateString(15);
        			course.setUuid(uuid);    			
        			List<String> numbers =IOUtil.Txt(path+"/"+fileName);
        			if(numbers !=null) {
	        			for(int a=0;a<numbers.size();a++) {
	        				String no=numbers.get(a);
	        				StudentCourseRelModel courseRel = new StudentCourseRelModel();
	        				courseRel.setCourseUuid(uuid);
	        				courseRel.setStudentNo(no);
	        				//ѧ���γ̹���
	        				courseRelService.create(courseRel);
	        			}
	        			course.setTeacherUuid(userUuid);
	        			//�γ̱�
						courseService.create(course);	        			
        			}else {
        				return null;
        			}
        			return course; 			        			
        		}
        	}           
        }
		return null;	
	}
	
	public List<HomeworkModel> getHomeworkModel(Message[] messages,String userUuid) throws Exception{
		UserModel userModel = userService.getByUuid(userUuid);
		String userName= userModel.getUserName();
		this.path +=userName;
		//������ҵ�����ж��̼߳���
		if(userModel.getUserType().equals("2")) {
			List<HomeworkModel> homeworkList=homeworkService.getByTeacherUuid(userUuid);
			for( HomeworkModel item:homeworkList) {
				/*this.homework=item;
				System.out.println("��ҵ����"+homework.getTitle());
				executor.execute(new Runnable() {  
		            @Override  
		            public void run() {  
		                //synchronized (testService) {  
		            	try {
		            		System.out.println("��ҵ��"+homework.getTitle());
							String aa=queryEmail(messages);
							System.out.println("��ҵ��"+aa);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		               // }  
		            }  
		        });*/
				if(item.getState().equals("0")) {
					String aa=queryEmail(messages,item);
				}							
			}
			 
			/*List<CourseModel> courseList =courseService.getByTeacherUuid(userUuid);
			for(CourseModel item:courseList) {
				String courseUuid = item.getUuid();
				List<String> noList=courseRelService.getStudentNoByCourseUuid(courseUuid);
			}		
			//�γ���-ѧ������-��ҵ1
			String MailTitle ="";
			String[] title = MailTitle.split("-");*/
		}
		return null;
	}
	
	
	public String queryEmail(Message[] messages,HomeworkModel homework) throws Exception {
		int submitNum=0;
		CourseModel course = courseService.getByUuid(homework.getCourseUuid());
		String courseName = course.getCourseName();
		String times =homework.getTimes()+"";
		this.path +=courseName;
		List<String> noList=courseRelService.getStudentNoByCourseUuid(course.getUuid());
		System.out.println("------------�ʼ�����"+messages.length);
		for (int i = 0; i < messages.length; i++) {
        	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
        	System.out.println("----------------------�ʼ�����"+pmm.getSubject());
        	System.out.println("------------��ѯ��"+i+"�ʼ�");
        	if(checkTitle(pmm.getSubject(),courseName,times,noList)) {
        		if(pmm.isContainAttach((Part)messages[i])){
        			String studentNo="";
        			String regex = "[0-9]{13}"; //������ʽ��ȡѧ��ѧ��
        			Pattern pattern = Pattern.compile(regex);   
                	Matcher m = pattern.matcher(pmm.getSubject());
                	if(m.find()){ 
                	   studentNo =m.group();
                	}
                	WorkStuRelModel workRek =new WorkStuRelModel();
                	workRek.setHomeworkUuid(homework.getUuid());
                	workRek.setStudentNo(studentNo);
                	workStuService.create(workRek);
        			String sendTime=pmm.getSentDate();
        			String context=pmm.getBodyText();
        			String formEmail = pmm.getFrom();
        			System.out.println(formEmail);
        			System.out.println(context);    		
        			System.out.println(sendTime);
        			submitNum++;
        		}       		
        	}    	
		}
		if(submitNum==0) {
			homework.setUpdateTime(DateUtil.getStringDate());
			homeworkService.update(homework);
			return "û����������Ҫ�����ҵ�ʼ�";
		}
		homework.setSubmittedNum(submitNum+"");
		homework.setUpdateTime(DateUtil.getStringDate());
		homeworkService.update(homework);
		return "������ҵ�ɹ�";	
	}
	
	private boolean checkTitle(String title,String courseName,String times,List<String> noList) {
		if(title.contains(courseName)) {
			for(String item:noList) {
				if(title.contains(item) && title.endsWith(times)) {
					System.out.println("-------------�ҵ�һ�ݷ����ʼ�---------------");
					return true;
				}
			}
			return false;
		}else {
			return false;
		}
	}
	
	/**
	 * ��ȡ��ҵ�ʼ�����
	 * @param message
	 * @param homework
	 * @return
	 * @throws Exception 
	 */
	public String collectEmail(Message[] messages,HomeworkModel homework) throws Exception {
		String separator = System.getProperty("file.separator");
		UserModel userModel = userService.getByUuid(homework.getTeacherUuid());
		String userName= userModel.getLoginName();
		CourseModel course = courseService.getByUuid(homework.getCourseUuid());
		String courseName=course.getCourseName();
		String times=homework.getTimes()+"";
		this.path+=userName+separator+courseName+separator+"��ҵ"+times;
		System.out.println("������ҵ1·��"+path);
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		List<String> noList = courseRelService.getStudentNoByCourseUuid(course.getUuid());
		for (int i = 0; i < messages.length; i++) {
        	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
        	System.out.println("----------------------�ʼ�����"+pmm.getSubject());
        	System.out.println("------------��ѯ��"+i+"�ʼ�");
        	if(checkTitle(pmm.getSubject(),courseName,times,noList)) {
        		if(pmm.isContainAttach((Part)messages[i])){
        			String studentNo="";
        			String regex = "[0-9]{13}"; //������ʽ��ȡѧ��ѧ��
        			Pattern pattern = Pattern.compile(regex);   
                	Matcher m = pattern.matcher(pmm.getSubject());
                	if(m.find()){ 
                	   studentNo =m.group();
                	}                	
        			String sendTime=pmm.getSentDate();
        			String context=pmm.getBodyText();
        			String formEmail = pmm.getFrom(); 
        			//����·��
        			pmm.setAttachPath(file.toString());
        			//�����ļ�
        			pmm.saveAttachMent((Part)messages[i]);
        			//�õ��ļ���
        			String fileName =pmm.getFilename();
        			File path = new File(file.toString()+separator+fileName);
        			//������ҵ�ļ���С
        			String fileSize =FileUtil.FormetFileSize(FileUtil.getFileSizes(path));
        			StatisticsModel statistics = new StatisticsModel();
        			statistics.setStudentNo(studentNo);
        			statistics.setMailTitle(pmm.getSubject());
        			statistics.setMailContent(context);
        			statistics.setSendTime(sendTime);
        			statistics.setFileName(fileName);
        			statistics.setFileSize(fileSize);
        			statistics.setFormEmail(formEmail);
        			statistics.setTeacherUuid(homework.getTeacherUuid());
        			statistics.setTeacherName(userModel.getLoginName());
        			statistics.setHomeworkUuid(homework.getUuid());
        			statistics.setHomeworkName(homework.getTitle()); 
        			statisticsService.create(statistics);
        		}       		
        	}    	
		}
		return "success";
	}
}
