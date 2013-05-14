package com.info.web.controller.util;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.info.common.util.SystemCurrentUser;
import com.info.web.CurrentUser;

public class HttpSessionListener implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		if ("CurrentUser".equals(event.getName())) {  
			SystemCurrentUser.setCurrentUser((CurrentUser)event.getValue());  
        } 
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		if ("CurrentUser".equals(event.getName())) {  
			SystemCurrentUser.setCurrentUser((CurrentUser)event.getValue());  
        } 
	}

}
