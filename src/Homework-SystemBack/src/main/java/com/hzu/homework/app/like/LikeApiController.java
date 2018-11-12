package com.hzu.homework.app.like;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.sysback.homework.service.HomeworkService;
import com.hzu.homework.sysback.like.service.LikeService;
import com.hzu.homework.sysback.like.vo.LikeModel;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.TokenUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/api/like")
public class LikeApiController {
	private LikeService myService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private HomeworkService homeworkService;
	
	@Autowired
	public void setMyService(LikeService bs) {
		this.myService=bs;
	}
	
	/**
	 * 获取点赞数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getLikeNum" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getLikeNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");	
		String homeworkUuid = request.getParameter("homeworkUuid");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		int likeNum = myService.getCount(homeworkUuid);
		boolean isLike = myService.isLike(homeworkUuid, uuid);		
		array.put("code", "success");
		array.put("likeNum", likeNum+"");
		if(isLike) {
			array.put("isLike","1");
		}else {
			array.put("isLike", "0");
		}
		return JSONUtil.parseJSON(array);
	}
	
	
	/**
	 * 更新点赞数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateLikeNum" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateLikeNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");	
		String homeworkUuid = request.getParameter("homeworkUuid");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		boolean isLike = myService.isLike(homeworkUuid, uuid);
		LikeModel like = new LikeModel();
		if(isLike) {
			like=myService.getUpdateLike(homeworkUuid, uuid);
			myService.delete(like);	
			array.put("isLike", "0");
		}else {
			like.setHomeworkUuid(homeworkUuid);
			like.setUserUuid(uuid);
			myService.create(like);
			array.put("isLike", "1");
		}
		int likeNum = myService.getCount(homeworkUuid);			
		array.put("code", "success");
		array.put("likeNum", likeNum+"");	
		return JSONUtil.parseJSON(array);
	}

}
