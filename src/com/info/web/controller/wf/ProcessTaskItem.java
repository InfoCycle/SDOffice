package com.info.web.controller.wf;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.info.domain.ViewWfProcessTemplete;
import com.info.service.WfProcessTaskItemService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

/*
 * 待办业务过程列表
 */
@Scope("prototype")
@Controller
public class ProcessTaskItem extends BaseController {	
	@Autowired
	WfProcessTaskItemService service;
	
	@RequestMapping(value = "/wf/processtask/")
	public void getWaitTaskList(HttpServletResponse response){
		message =new ResultMessage();
		CurrentResponse = response;
		String result="";
		int action =getInt("action", -1);
		start = getInt("start", 0);
		limit = getInt("limit", 15);
		switch (action) {
		case 1: //待办
			result =waitTask();
			break;
		case 2: //我新建
			result =ownerTask();		
			break;
		case 3: //我经手
			result =handleTask();
			break;
		case 4: //催办列表
			result =urgeTask();
			break;
		case 5: //查询列表
			result =searchTask();
			break;
		}
		writeJsonString(result);
	}
	
	private String waitTask() {
		try {
			List<ViewWfProcessTemplete> list = service.getWaitProcess(start, limit);
			for (ViewWfProcessTemplete item : list) {
				item.setFSendUser(service.getSendUserById(item.getFSendUser()));
			}
			message.setSuccess(true);
			message.setTotalProperty(service.getWaitProcessCount());
			message.setRoot(list);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
	
	private String urgeTask() {
		try {
			List<ViewWfProcessTemplete> list = service.getUrgeProcess(start, limit);
			for (ViewWfProcessTemplete item : list) {
				item.setFSendUser(service.getSendUserById(item.getFSendUser()));
			}
			message.setSuccess(true);
			message.setTotalProperty(service.getUrgeProcessCount());
			message.setRoot(list);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
	
	private String ownerTask() {
		try {
			List<ViewWfProcessTemplete> list = service.getOwnerProcess(start, limit);
			for (ViewWfProcessTemplete item : list) {
				item.setFSendUser(service.getSendUserById(item.getFSendUser()));
			}
			message.setRoot(list);
			message.setSuccess(true);
			message.setTotalProperty(service.getOwnerProcessCount());
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
	
	private String handleTask() {
		try {
			List<ViewWfProcessTemplete> list = service.getHandleProcess(start, limit);
			for (ViewWfProcessTemplete item : list) {
				item.setFSendUser(service.getSendUserById(item.getFSendUser()));
			}
			message.setRoot(list);
			message.setSuccess(true);
			message.setTotalProperty(service.getHandleProcessCount());
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
	
	private String searchTask() {
		int scopeData = getInt("scopeData", -1);
		String filetype = getString("filetype","");
		String title =getString("title","");
		try {
			List<ViewWfProcessTemplete> list = service.getQueryTask(start, limit, scopeData, filetype, title);
			for (ViewWfProcessTemplete item : list) {
				item.setFSendUser(service.getSendUserById(item.getFSendUser()));
			}
			message.setRoot(list);
			message.setTotalProperty(service.getQueryTotalCount());
			message.setSuccess(true);
		} catch (Exception e) {
			message.setSuccess(false);
			e.printStackTrace();
		}		
		return getJsonFromObj(message);
	}
}
