package com.info.web.controller.wf;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.ViewWfActive;
import com.info.domain.ViewWfActiveReturn;
import com.info.domain.ViewWfActiveUrge;
import com.info.service.WfProcessUtils;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Scope("prototype")
@Controller
public class HandleStatus extends BaseController {
	int processId;
	@Autowired
	WfProcessUtils wfUtils;
	@RequestMapping( value = "/wf/handlestatus/{action}/")
	public void handleStatus(@PathVariable("action") int action,HttpServletResponse response){
		CurrentResponse = response;
		processId =getInt("processId", -1);
		message =new ResultMessage();
		switch (action) {
		case 1:
			result = getActives();
			break;
		case 2:
			result = getActiveUrge();
			break;
		case 3:
			result = getActiveReturn();
			break;
		default:
			break;
		}
		writeJsonString(result);
	}
	
	public String getActives() {
		try {
			List<ViewWfActive> active = wfUtils.getWfActive(processId);
			if(active.size()>0){
				message.setSuccess(true);
				message.setRoot(active);
			}else{
				message.setSuccess(false);
			}					
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
		}
		return getJsonFromObj(message);
	}
	public String getActiveUrge() {
		try {
			List<ViewWfActiveUrge> active = wfUtils.getWfActiveUrges(processId);
			if(active.size()>0){
				message.setSuccess(true);
				message.setRoot(active);
			}else{
				message.setSuccess(false);
			}	
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
		}
		return getJsonFromObj(message);
	}
	public String getActiveReturn() {
		try {
			List<ViewWfActiveReturn> active = wfUtils.getWfActiveReturns(processId);
			if(active.size()>0){
				message.setSuccess(true);
				message.setRoot(active);
			}else{
				message.setSuccess(false);
			}			
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
		}
		return getJsonFromObj(message);
	}
}
