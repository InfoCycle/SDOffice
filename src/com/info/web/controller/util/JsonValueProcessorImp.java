package com.info.web.controller.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonValueProcessorImp implements JsonValueProcessor {

	private String format = "yyyy-MM-dd HH:mm:ss";

	public JsonValueProcessorImp() {
	};

	public JsonValueProcessorImp(String format) {
		this.format = format;
	}

	
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof java.util.Date[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			java.util.Date[] dates = (java.util.Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		if (value instanceof Timestamp[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Timestamp[] dates = (Timestamp[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		if (value instanceof java.sql.Date[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			java.sql.Date[] dates = (java.sql.Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	
	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if (InfoUtils.isEmpty(value))
			return "";
		if (value instanceof Timestamp) {
			String str = new SimpleDateFormat(format).format((Timestamp) value);
			return str;
		} else if (value instanceof java.util.Date) {
			String str = new SimpleDateFormat(format)
					.format((java.util.Date) value);
			return str;
		} else if (value instanceof java.sql.Date) {
			String str = new SimpleDateFormat(format)
					.format((java.sql.Date) value);
			return str;
		}
		return value.toString();
	}

	public JSONObject processBean(Object bean, JsonConfig arg1) {
		JSONObject jsonObject = null;
		System.out.println("processor class name:" + bean.getClass().getName());
		if (bean instanceof java.sql.Date) {
			bean = new Date(((java.sql.Date) bean).getTime());
		}
		if (bean instanceof java.sql.Timestamp) {
			System.out.println("bean timestamp");
			bean = new Date(((java.sql.Timestamp) bean).getTime());
		}
		if (bean instanceof Date) {
			jsonObject = new JSONObject();
			jsonObject.element("time", ((Date) bean).getTime());
		} else {
			jsonObject = new JSONObject(true);
		}
		return jsonObject;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
