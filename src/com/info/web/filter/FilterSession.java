package com.info.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.common.util.SystemCurrentUser;
import com.info.web.CurrentUser;

/**
 * 拦截需验证的服务，检查session状态
 * 
 * @author liwx 修改时间 : 2013-1-16下午2:44:54
 */
public class FilterSession implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String actionUrl = httpRequest.getRequestURI().toLowerCase();
		CurrentUser userSession = (CurrentUser) httpRequest.getSession()
				.getAttribute("CurrentUser");
		if (userSession != null) {
			//把Session赋值到线程便量，方便后面在不影响代码结构情况下可获取session
			SystemCurrentUser.setCurrentUser(userSession);
			filterChain.doFilter(request, response);
			return;
		}
		// 验证必须登录的服务
		if (actionUrl.indexOf("/system/") > -1
				|| actionUrl.indexOf("/buss/") > -1
				|| actionUrl.indexOf("/wf/") > -1) {
			// 判断是否为Ajax请求
			String head = httpRequest.getHeader("x-requested-with");
			if (head != null && (head.equalsIgnoreCase("XMLHttpRequest"))) {
				// 添加respone报错信息 ,在ajax请求时统一拦截再做跳转处理
				httpResponse.setHeader("sessionstatus", "timeout");
				//status=401 未授权的请求
				httpResponse.setStatus(401);
				filterChain.doFilter(request, response);
				return;
			} else {
				// 如果不是Ajax请求，直接跳转到登录页面
				if (actionUrl.indexOf("loginapp.html") == -1) {
					httpResponse
							.sendRedirect("/html/module/system/loginApp.html");
					return;
				}
			}
		}
		//其他的请求
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
