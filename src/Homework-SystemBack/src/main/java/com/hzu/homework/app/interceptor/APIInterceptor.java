package com.hzu.homework.app.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.TokenUtil;

import net.sf.json.JSONObject;

public class APIInterceptor extends HandlerInterceptorAdapter {
    @Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
    	JSONObject array = new JSONObject();
        String token = request.getParameter("token");  
        DecodedJWT jwt = TokenUtil.deToken(token);
        if(jwt==null) {
        	array.put("code", "noToken");
        	array.put("msg", "登陆过期请重新登录!");
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().write(JSONUtil.parseJSON(array));
        	return false;
        }    
        return true;  
    }  
  
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
          
    }

}
