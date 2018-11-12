package com.hzu.homework.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hzu.homework.util.LoginUserHelper;

public class SysManagerFilter implements Filter {
	
	protected FilterConfig filterConfig = null;
	
	protected boolean ignore = true;

	@Override
	public void destroy() {
		this.filterConfig = null;		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String sum = (String)session.getAttribute("loginName");
		System.out.println(sum);
		/**
		 * 如果帐号不存在
		 */
		if (sum == null
				&& !httpRequest.getRequestURI().contains("/sysback/toLogin")
				&& !httpRequest.getRequestURI().contains("/sysback/login")) {
			
			String refresh = "refresh";
			String toUri = httpRequest.getContextPath() + "/sysback/toLogin?"+"refresh="+refresh;
			httpResponse.sendRedirect(toUri);
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		/*this.filterConfig = filterConfig;
		String value = filterConfig.getInitParameter("ignore");
		if (value == null) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("true")) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("yes")) {
			this.ignore = true;
		} else {
			this.ignore = false;
		}*/
		
	}

}
