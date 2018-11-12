package com.hzu.homework.app.comment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.sysback.comment.service.CommentService;
import com.hzu.homework.sysback.comment.vo.CommentModel;
import com.hzu.homework.sysback.homework.service.HomeworkService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.TokenUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/api/comment")
public class CommentApiController {
	private CommentService myService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private HomeworkService homeworkService;
	
	@Autowired
	public void setMyService(CommentService bs) {
		this.myService=bs;
	}
	
	/**
	 * 发布评论
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/addComment" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String addComment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");	
		String homeworkUuid = request.getParameter("homeworkUuid");
		String content = request.getParameter("content");
		String teacherUuid = homeworkService.getByUuid(homeworkUuid).getTeacherUuid();
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);
		CommentModel comment = new CommentModel();
		comment.setFormUuid(uuid);
		if(user.getUserType().equals("2")) {
			comment.setHomeworkUuid(homeworkUuid);
			comment.setToUuid("0");
		}else {
			comment.setHomeworkUuid(homeworkUuid);
			comment.setToUuid(teacherUuid);			
		}
		comment.setContent(content);
		myService.create(comment);
		String userName=user.getLoginName();
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+userName);
		array.put("code", "success");
		array.put("userName",userName);
		array.put("userUuid",uuid);
		array.put("createTime", DateUtil.getStringDate());
		array.put("content", content);			
		return JSONUtil.parseJSON(array);
	}
	
	/**
	 * 获取评论
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getComment" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getComment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();	
		String token = request.getParameter("token");	
		String homeworkUuid = request.getParameter("homeworkUuid");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		List<CommentModel> list=myService.getByHomeworkUuid(homeworkUuid);
		if(list!=null) {
			for(CommentModel item:list) {
				JSONObject object = new JSONObject();
				String userName= userService.getByUuid(item.getFormUuid()).getLoginName();
				object.put("userName",userName);
				object.put("userUuid",item.getFormUuid());
				object.put("createTime",item.getCreateTime());
				object.put("content", item.getContent());	
				arrays.add(object);
			}
			array.put("data", arrays.toString());
			array.put("code", "success");
		}else {
			array.put("code", "false");
		}
		return JSONUtil.parseJSON(array);
	}

}
