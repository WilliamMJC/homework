package com.hzu.homework.app.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.sysback.matched.service.MatchedService;
import com.hzu.homework.sysback.matched.vo.MatchedModel;
import com.hzu.homework.sysback.message.service.MessageService;
import com.hzu.homework.sysback.message.vo.MessageModel;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.TokenUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("api/message")
public class MessageApiController {
	private MessageService myService;
	@Autowired
	private UserService userService;
	@Autowired
	private MatchedService matchedService;
	@Autowired
	public void setMyService(MessageService bs) {
		this.myService=bs;
	}
	
	/**
	 * 获取聊天记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getMessage" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();	
		String token = request.getParameter("token");	
		String toUuid = request.getParameter("toUuid");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		List<MessageModel> list=myService.getListByBelongUuid(uuid, toUuid);
		if(list !=null) {
			for(MessageModel item:list) {
				JSONObject object = new JSONObject();
				object.put("content", item.getContent());
				String newChatTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(item.getCreateTime()));
				object.put("createTime",newChatTime);
				String formUuid = item.getFormUuid();
				if(formUuid.equals(uuid)) {
					object.put("type", "1");
				}else {
					object.put("type", "-1");
				}
				arrays.add(object);
				myService.update(item);
			}
			array.put("code", "success");
			array.put("msg", arrays.toString());	
		}
		return JSONUtil.parseJSON(array);
	}
	
	
	/** 发送信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/addMessage" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String addMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");	
		String toUuid = request.getParameter("toUuid");
		String content = request.getParameter("content");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		MessageModel message = new MessageModel();		
		message.setContent(content);
		message.setFormUuid(uuid);
		message.setToUuid(toUuid);
		message.setBelongUuid(uuid);
		//聊天信息分了两份存储
		myService.create(message);
		MessageModel message0 = new MessageModel();		
		message0.setContent(content);
		message0.setFormUuid(uuid);
		message0.setToUuid(toUuid);
		message0.setBelongUuid(uuid);
		message0.setBelongUuid(toUuid);
		myService.create(message0);	
		//判断是否对接过
		boolean ismatched = matchedService.isMatched(uuid, toUuid);
		if(!ismatched) {
			//创建对接信息，两份
			MatchedModel matched = new MatchedModel();
			matched.setBelongUuid(uuid);
			matched.setFormUuid(uuid);
			matched.setToUuid(toUuid);
			matchedService.create(matched);
			MatchedModel matched1 = new MatchedModel();
			matched1.setBelongUuid(toUuid);
			matched1.setFormUuid(uuid);
			matched1.setToUuid(toUuid);
			matchedService.create(matched1);	
		}
		String nowTime = DateUtil.getStringDate();
		String newChatTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(nowTime));
		array.put("content", content);
		array.put("createTime", newChatTime);
		array.put("code", "success");
		return JSONUtil.parseJSON(array);
	 }
	
	/** 获取最新消息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getNowMessage" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getNowMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");	
		String formUuid = request.getParameter("formUuid");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();	
		MessageModel message=myService.getNewMessage(uuid, formUuid);
		if(message!=null) {
			myService.update(message);
			String newChatTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(message.getCreateTime()));
			array.put("content", message.getContent());
			array.put("createTime", newChatTime);
			array.put("code", "success");
		}else {
			array.put("code", "false");
		}		
		return JSONUtil.parseJSON(array);
	 }
	
	@ResponseBody
	@RequestMapping(value = { "/getChatUserList" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getChatUserList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("进到方法");
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();	
		List<MatchedModel> list= matchedService.getByUserUuid(uuid);
		if(list!=null) {
			for(MatchedModel item:list) {
				JSONObject object = new JSONObject();
				String formUuid=item.getFormUuid();
				String toUuid=item.getToUuid();
				if(formUuid.equals(uuid)) {
					UserModel user = userService.getByUuid(toUuid);
					object.put("userUuid", toUuid);
					object.put("userName", user.getLoginName());
					MessageModel message = myService.getLastMessage(uuid, toUuid);
					object.put("lastMessage", message.getContent());
					String newChatTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(message.getCreateTime()));
					object.put("createTime", newChatTime);
					int unReadNum = myService.getUnreadNum(uuid, toUuid);
					object.put("unReadNum", unReadNum+"");
				}else {
					UserModel user = userService.getByUuid(formUuid);
					object.put("userUuid", formUuid);
					object.put("userName", user.getLoginName());
					MessageModel message = myService.getLastMessage(uuid, formUuid);
					object.put("lastMessage", message.getContent());
					String newChatTime = DateUtil.getNewChatTime(DateUtil.getTimesamp(message.getCreateTime()));
					object.put("createTime", newChatTime);
					int unReadNum = myService.getUnreadNum(uuid, formUuid);
					object.put("unReadNum", unReadNum+"");
				}
				arrays.add(object);				
			}
			array.put("code", "success");
			array.put("chatUser", arrays.toString());
			
		}else {
			array.put("code", "false");
		}
		
		return JSONUtil.parseJSON(array);
	 }
	
}
