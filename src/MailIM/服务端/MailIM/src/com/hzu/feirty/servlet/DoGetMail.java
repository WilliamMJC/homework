package com.hzu.feirty.servlet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzu.feirty.contorl.MailReceive;
import com.hzu.feirty.contorl.MailSenter;
import com.hzu.feirty.contorl.MailToZip;
import com.hzu.feirty.contorl.PraseMimeMessage;
import com.hzu.feirty.dao.ConstructionDaoImpl;
import com.hzu.feirty.dao.TeacherDaoImpl;
import com.hzu.feirty.dao.UserDaoImpl;
import com.hzu.feirty.entity.Construction;
import com.hzu.feirty.entity.Email;
import com.hzu.feirty.entity.Teacher;
import com.hzu.feirty.utils.GetFileSize;
import com.hzu.feirty.utils.ExportExcel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DoGetMail extends HttpServlet {
	private ArrayList<ArrayList<InputStream>> attachmentsInputStreamsList = new ArrayList<ArrayList<InputStream>>();
	ArrayList<String> attachments= new ArrayList<String>();
	private List<Email> maillist;
	//ArrayList<String> attachments;
	public DoGetMail() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
		
/**
 * 邮件处理类
 * 
 * 
 */	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String user = request.getParameter("user");
		PrintWriter out = response.getWriter();
		JSONObject array = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		if(action.equals("send")){
			String subject = request.getParameter("subject");
			String to = request.getParameter("to");
			String content = request.getParameter("content");
			try {
				Teacher teacher =new TeacherDaoImpl().find2(user);
				MailSenter mailsend =new MailSenter("smtp.qq.com",teacher.getMail_name(), teacher.getMail_pwd());
				mailsend.send(to,subject,content);
				array.put("code", "success");
				array.put("msg", "发送成功");
				array.put("data", "");
				System.out.println("发送成功！");
			} catch (Exception e) {
				e.printStackTrace();
				array.put("code", "succ");
				array.put("msg", "发布作业失败");
				array.put("data", "");
				System.out.println("发布作业失败！");
			}
		}else if(action.equals("receive")){
			JSONArray arrays = new JSONArray();		
			try {
				String str =new UserDaoImpl().SearchType(user);
				/*
				 * 老师部分，作业邮件内容接收
				 * 
				 */
				if(str.equals("teacher")){
					maillist = MailReceive.getAllMailByTeacher(user);
					array.put("code", "success");
					array.put("msg", "11");	
					for (int i = 0; i < maillist.size(); i++) {
						JSONObject object = new JSONObject();
						object.put("from", maillist.get(i).getFrom());
						object.put("subject", maillist.get(i).getSubject());
						object.put("content", maillist.get(i).getContent());
						object.put("time", maillist.get(i).getSentdata());
						object.put("attachment", maillist.get(i).getAttachmentname());
						arrays.add(object);
					}
					array.put("data", arrays.toString());	
				}
				/*
				 * 学生部分：作业邮件内容接收
				 * 
				 */
				else if(str.equals("student")){
					maillist = MailReceive.getAllMail(user);
					array.put("code", "success");
					array.put("msg", "11");	
					for (int i = 0; i < maillist.size(); i++) {
						JSONObject object = new JSONObject();
						object.put("from", maillist.get(i).getFrom());
						object.put("subject", maillist.get(i).getSubject());
						object.put("content", maillist.get(i).getContent());
						//System.out.println(maillist.get(i).getContent());
						object.put("time", maillist.get(i).getSentdata());
						object.put("attachment", maillist.get(i).getAttachmentname());
						arrays.add(object);
					}
					array.put("data", arrays.toString());										
				}else{
					array.put("code", "false");
					array.put("msg", "11");						
				}		
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		/*
		 * 收作业功能，学生邮件作业附件下载
		 * 
		 */
		
		else if(action.equals("receive2")){
			try {
				array.put("code", "success");
				array.put("msg", "作业下载成功");
				array.put("data", "");
				System.out.println("作业下载成功");
				maillist = MailReceive.getAllMailByTeacher(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array.put("code", "suc");
				array.put("msg", "作业下载失败");
				array.put("data", "");
				System.out.println("作业下载失败");
			}			
		}
		/*
		 * 学生学号收集功能
		 * 
		 */
		else if(action.equals("number")){
			try {
				array.put("code", "success");
				array.put("msg", "学号添加成功");
				array.put("data", "");
				if(MailReceive.getAllMailByNumber(user)){
					System.out.println("学号添加成功");					
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array.put("code", "suc");
				array.put("msg", "学号添加失败");
				array.put("data", "");
				System.out.println("学号添加失败");
			}			
		}
		/*
		 * 学生作业附件打包发送功能
		 * 
		 */
		else if(action.equals("RECEIVEHOMEWORK")){
			try {
				String type=new UserDaoImpl().SearchType(user);
				if(type.equals("teacher")){
					Teacher teacher = new TeacherDaoImpl().find2(user);
					if(!teacher.getPeasonmail().equals("")){
						//作业匹配下载					
						String docsPath = request.getSession().getServletContext().getRealPath("docs");
						//  i为作业的数量
						int i = MailReceive.getAllMailByTeacher2(user,docsPath);
						String worknumber=String.valueOf(i-1);
						array.put("code", "success");
						String imagesPath = request.getSession().getServletContext().getRealPath("images");
						(new ExportExcel()).test(imagesPath, docsPath);
						String zipsPath = request.getSession().getServletContext().getRealPath("zips");
						File sourceFilePath=new File(docsPath);
						File zipFilePath=new File(zipsPath);
						String fileName="作业附件";
						DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
						String date =dateFormat.format(new Date());
						//作业压缩zip
						MailToZip.mailToZip(sourceFilePath.toString(), zipFilePath.toString(), fileName+date);
						System.out.println("作业打包成功！");
						File zippath = new File(zipFilePath.toString()+"\\"+fileName+date+".zip");
						String attachments = zippath.toString();
						String mailname = teacher.getMail_name();
						String mailpwd = teacher.getMail_pwd();
						String peason_mail = teacher.getPeasonmail();					
						 // 作业压缩文件发送 
						MailSenter mailsend =new MailSenter("smtp.qq.com", mailname, mailpwd);					
						mailsend.send(peason_mail,fileName+date,fileName+date,attachments,"收发作业系统");
						System.out.println("作业打包文件发送成功！");
						String send_date =dateFormat.format(new Date());
            			GetFileSize getFileSize = new GetFileSize();
            			String zip_size =getFileSize.FormetFileSize(getFileSize.getFileSizes(zippath));
            			String zip_name = fileName+send_date+".zip";
            			GetFileSize g = new GetFileSize();
            			long filenumber= g.getlist(sourceFilePath);
            			//Date sendDate = new Date();
            			java.sql.Date sendtime= new java.sql.Date(new java.util.Date().getTime());
            			ConstructionDaoImpl constructionDaoImpl = new ConstructionDaoImpl();
            			//统计作业的提交情况
            			Construction construction = new Construction(user, filenumber, zip_name, zip_size,sendtime);
            			constructionDaoImpl.inSert(construction);
						array.put("msg", "作业打包发送成功");
						array.put("zipname",zip_name);
						array.put("number", worknumber);
						array.put("zipsize", zip_size);
						array.put("sendtime", send_date);									
					}else{
						array.put("code", "nomail");
						array.put("msg", "nomail");					
					}				
				}else{
					array.put("code", "noidentry");
					array.put("msg", "noidentry");				
				}
			} catch (Exception e) {
				e.printStackTrace();
				array.put("code", "false");
				array.put("msg", "收作业失败");
				System.out.println("收作业失败");
			}
		}
		out.print(array);
		out.flush();
		out.close();
	}
  
}
