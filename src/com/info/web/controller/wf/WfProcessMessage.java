package com.info.web.controller.wf;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.StringUtil;
import com.info.domain.TAppMessage;
import com.info.service.WfProcessMessageService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Scope("prototype")
@Controller
public class WfProcessMessage extends BaseController{
	@Autowired
	WfProcessMessageService service;
	@RequestMapping( value = "/wf/message/{action}/")
	public void messageService(@PathVariable("action") int action,HttpServletResponse response){
		CurrentResponse = response;
		message = new ResultMessage();
		switch (action) {
		case 1:
			result = getUnReadMessage();
			break;
		case 2:
			result = getMessageForShow();
			break;
		default:
			break;
		}
		writeJsonString(result);
	}
	
	private String getUnReadMessage() {
		try {
			TAppMessage appMessage= service.getUnReadMessage();
			if(appMessage==null){
				message.setSuccess(false);
			}else{
				message.setSuccess(true);
				appMessage.setFContent(StringUtil.limitStringLength(
						appMessage.getFContent(), 
						28));
				message.setRoot(appMessage);
			}			
		} catch (Exception e) {
			message.setSuccess(false);
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
	
	private String getMessageForShow() {
		int MsgId = getInt("MsgId", -1);
		try {
			TAppMessage appMessage= service.show(MsgId);
			if(appMessage==null){
				message.setSuccess(false);
				message.setMessage("消息查看错误");
			}else{
				message.setSuccess(true);
				message.setRoot(appMessage);
			}	
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("消息查看错误。"+e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);		
	}
}
