<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<style>	
	#main {};
	#left {background:url(<%=request.getContextPath()%>/html/images/error_128.png) no-repeat};
	#right{};
</style>
</head>
<%
	Integer statusCode = (Integer) request
			.getAttribute("javax.servlet.error.status_code");
	String message = (String) request
			.getAttribute("javax.servlet.error.message");
	String servletName = (String) request
			.getAttribute("javax.servlet.error.servlet_name");
	String uri = (String) request
			.getAttribute("javax.servlet.error.request_uri");
	Throwable t = (Throwable) request
			.getAttribute("javax.servlet.error.exception");
	if (t == null) {
		t = (Throwable) request.getAttribute("exception");
	}
	Class<?> exception = (Class<?>) request
			.getAttribute("javax.servlet.error.exception_type");
	String exceptionName = "";
	if (exception != null) {
		exceptionName = exception.getName();
	}
	if (statusCode == null) {
		statusCode = 0;
	}
	if (statusCode == 500) {
		LOGGER.error(
				statusCode + "|" + message + "|" + servletName + "|"
						+ uri + "|" + exceptionName + "|"
						+ request.getRemoteAddr(), t);
	} else if (statusCode == 404) {
		LOGGER.error(statusCode + "|" + message + "|" + servletName
				+ "|" + uri + "|" + request.getRemoteAddr());
	}

	String queryString = request.getQueryString();
	String url = uri
			+ (queryString == null || queryString.length() == 0 ? ""
					: "?" + queryString);
	url = url.replaceAll("&amp;", "&").replaceAll("&", "&amp;");

	if (t != null) {
		LOGGER.error("error", t);
	}
%>
<body>
	<div id="main">
		<div id="left"></div>
		<div id="right">
			<%
				if (statusCode == 404) {
					out.println("对不起,暂时没有找到您所访问的页面地址,请联系管理员解决此问题.<br/><br/>");
					out.println("<a href=\"" + url + "\">刷新,看看是否能访问了</a><br/>");
				} else {
					out.println("对不起,您访问的页面出了一点内部小问题,请<a href=\"" + url
							+ "\">刷新一下</a>重新访问,或者先去别的页面转转,过会再来吧~<br/><br/>");
				}
			%>
			<a href="javascript:void(0);" onclick="history.go(-1)">返回刚才页面</a><br />
			<br />
		</div>
	</div>
</body>
</html>
<%!private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
			.getLogger("DAILY_ALL");%>