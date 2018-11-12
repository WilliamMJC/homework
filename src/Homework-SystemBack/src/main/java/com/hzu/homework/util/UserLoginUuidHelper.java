package com.hzu.homework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserLoginUuidHelper {
	public static String getLoginUuid(HttpServletRequest request) {
		HttpSession session=request.getSession();
		return (String)session.getAttribute("loginUuid");
	}

}
