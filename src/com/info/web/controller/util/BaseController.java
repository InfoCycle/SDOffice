package com.info.web.controller.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.info.web.CurrentUser;
import com.info.web.ResultMessage;

public class BaseController extends MultiActionController {
	protected int start;
	protected int limit;
	protected ResultMessage message;
	protected String result="";
	
	@Autowired(required = true)
	protected HttpServletRequest CurrentRequest;

	// @Autowired(required=true)
	// 无法注入response，不知是否思路错误？
	protected HttpServletResponse CurrentResponse;

	public HttpServletResponse getCurrentResponse() {
		return CurrentResponse;
	}

	public void setCurrentResponse(HttpServletResponse currentResponse) {
		CurrentResponse = currentResponse;
	}

	/**
	 * 输出json字符串
	 * @param json
	 */
	public void writeJsonString(String json) {
		CurrentResponse.setContentType("application/json;charset=UTF-8");
		CurrentResponse.setCharacterEncoding("UTF-8");
		CurrentResponse.setHeader("Cache-Control",
				"no-store, max-age=0, no-cache, must-revalidate");
		CurrentResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
		CurrentResponse.setHeader("Pragma", "no-cache");
		try {
			PrintWriter out = CurrentResponse.getWriter();
			out.flush();
			out.write(json);
			out.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 为前台用submit提交的请求返回json
	// 一直想不通？？	
	public void writeJsonStringForSubmit(String json) {
		CurrentResponse.setContentType("text/html;charset=UTF-8");
		CurrentResponse.setCharacterEncoding("UTF-8");
		CurrentResponse.setHeader("Cache-Control",
				"no-store, max-age=0, no-cache, must-revalidate");
		CurrentResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
		CurrentResponse.setHeader("Pragma", "no-cache");
		try {
			PrintWriter out = CurrentResponse.getWriter();
			out.flush();
			out.write(json);
			out.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出图片 byte[]流
	 * @param img
	 */
	public void writeByte(byte[] img) {
		CurrentResponse.setContentType("image/jpeg");
		CurrentResponse.setHeader("Cache-Control",
				"no-store, max-age=0, no-cache, must-revalidate");
		CurrentResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
		CurrentResponse.setHeader("Pragma", "no-cache");
		try {
			ServletOutputStream out = CurrentResponse.getOutputStream();
			out.write(img);
			out.flush();
			out.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 对象实例系列化JSON
	public String getJsonFromObj(Object obj) {
		String JsonStr = "";
		if (InfoUtils.isEmpty(obj)) {

		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class,
					new JsonValueProcessorImp());
			cfg.registerJsonValueProcessor(java.util.Date.class,
					new JsonValueProcessorImp());
			cfg.registerJsonValueProcessor(java.sql.Date.class,
					new JsonValueProcessorImp());
			JsonStr = JSONObject.fromObject(obj, cfg).toString();
		}
		return JsonStr;
	}

	// 对象实例系列化JSON
	public String getJsonFromArray(Object obj) {
		String JsonStr = "";
		if (InfoUtils.isEmpty(obj)) {

		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class,
					new JsonValueProcessorImp());
			cfg.registerJsonValueProcessor(java.util.Date.class,
					new JsonValueProcessorImp());
			cfg.registerJsonValueProcessor(java.sql.Date.class,
					new JsonValueProcessorImp());
			JsonStr = JSONArray.fromObject(obj, cfg).toString();
		}
		return JsonStr;
	}

	// JSON串实例系列化对象
	public Object getObjectFromJson(String JsonData, Class<?> entityClass) {
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
		JSONObject obj = JSONObject.fromObject(JsonData);
		return JSONObject.toBean(obj, entityClass);
	}

	public List<?> getArrayFromJson(String JsonData, Class<?> entityClass){
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
		JSONArray arr=JSONArray.fromObject(JsonData);
		return JSONArray.toList(arr,entityClass);
	}
	
	// 简化取参数方法
	public String getString(String paramName, String defaultValue) {
		String paramValue = CurrentRequest.getParameter(paramName);
		if (paramValue == null || paramValue.equals(""))
			return defaultValue;
		return paramValue;
	}

	public int getInt(String paramName, int defaultValue) {
		String paramValue = CurrentRequest.getParameter(paramName);
		if (paramValue == null || paramValue.equals(""))
			return defaultValue;
		return Integer.parseInt(paramValue);
	}

	public long getLong(String paramName, long defaultValue) {
		String paramValue = CurrentRequest.getParameter(paramName);
		if (paramValue == null || paramValue.equals(""))
			return defaultValue;
		return Long.parseLong(paramValue);
	}

	public float getFloat(String paramName) {
		String s = CurrentRequest.getParameter(paramName);
		return Float.parseFloat(s);
	}

	// 取HttpSession
	public Object getSession(String session) {
		try {
			return CurrentRequest.getSession().getAttribute(session);
		} catch (Exception e) {
			return null;
		}
	}

	// 取当前登录用户ID
	public int getCurrentUserID() {
		CurrentUser currentUser = (CurrentUser) getSession("CurrentUser");
		return currentUser.getUserID();
	}

	// 取当前登录用户信息
	public CurrentUser getCurrentUser() {
		CurrentUser currentUser = (CurrentUser) getSession("CurrentUser");
		return currentUser;
	}

	// 设置Session
	public void setSession(String sessionName, Object value) {
		CurrentRequest.getSession().setAttribute(sessionName, value);
	}
	
	public String getRemoteAddress() {  
        String ip = CurrentRequest.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = CurrentRequest.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = CurrentRequest.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = CurrentRequest.getRemoteAddr();  
        }  
        return ip;  
    }  
	
	//此方法只能获取同一网段内的MAC地址，意义不大。
	public String getRemoteMACAddress(String ip) {  
        String str = "";  
        String macAddress = "";  
        try {  
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);  
            InputStreamReader ir = new InputStreamReader(p.getInputStream());  
            LineNumberReader input = new LineNumberReader(ir);  
            for (int i = 1; i < 100; i++) {  
                str = input.readLine();  
                if (str != null) {  
                    if (str.indexOf("MAC") > 1) {  
                        macAddress = str.substring(  
                                str.indexOf("=")+1, str.length());  
                        macAddress=macAddress.replace(" ", "");
                        break;  
                    }  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace(System.out);  
        }  
        return macAddress;  
    }  
}
