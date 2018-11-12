package com.hzu.homework.app.school;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.sysback.school.service.SchoolService;
import com.hzu.homework.sysback.school.vo.SchoolModel;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.TokenUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/api/school")
public class SchoolApiController {
	private SchoolService myService;
	
	@Autowired
	private UserService userService;
	@Autowired
	public void setMyService(SchoolService bs) {
		this.myService=bs;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getAllSchool" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getAllSchool(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		List<SchoolModel> list=myService.getAll();
		//List<String> schools = new ArrayList<String>();
		for(SchoolModel item:list) {
			JSONObject object = new JSONObject();
			object.put("school", item.getSchoolName());
			arrays.add(object);
			//schools.add(item.getSchoolName());		
		}
		array.put("code", "success");
		array.put("schools", arrays.toString());
		return JSONUtil.parseJSON(array);
	}
}
