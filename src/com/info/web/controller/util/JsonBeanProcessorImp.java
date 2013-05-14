package com.info.web.controller.util;

import java.util.Date;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonBeanProcessorImp implements
		net.sf.json.processors.JsonBeanProcessor {

	public JsonBeanProcessorImp() {

	}

	
	public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
		JSONObject jsonObject = null;
		if (bean instanceof java.sql.Date) {
			bean = new Date(((java.sql.Date) bean).getTime());
		}
		if (bean instanceof java.sql.Timestamp) {
			bean = new Date(((java.sql.Timestamp) bean).getTime());
		}
		if (bean instanceof java.util.Date) {
			bean = new Date(((java.util.Date) bean).getTime());
		} else {
			jsonObject = new JSONObject(true);
		}
		return jsonObject;
	}

}
