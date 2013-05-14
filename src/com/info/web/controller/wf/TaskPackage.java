/**
 * @Title		: TaskPackage.java
 * @Date		: 2013-03-21 14-37
 * @Author		: liwx
 * @Description	: 任务包的新增、修改、删除
 * @TODO List	: 

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */

package com.info.web.controller.wf;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.DateUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TTask;
import com.info.service.WfTaskPackage;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

/**   
 * @ClassName	: TaskPackage   
 * @Description	: 任务包维护   
 * @Author		: liwx
 * @Date		: 2013-03-21 14-37   
 */
@Controller
public class TaskPackage extends BaseController{
	@Autowired
	WfTaskPackage service;
	@RequestMapping(value = "/wf/taskpackage/")
	public void taskPackageData(HttpServletResponse response){
		CurrentResponse = response;
		int action = getInt("action", -1);
		int fId	   = getInt("fId", 0);
		message = new ResultMessage();
		try {
			switch (action) {
			case 1: //新增任务包
				result = addTaskPackage(fId);
				break;
			case 2://修改任务包
				result = editTaskPackage(fId);
				break;
			case 3://删除任务包
				result = deleteTaskPackage(fId);
				break;
			case 4://提取任务包
				result = getPackage(fId);
				break;
			default:
				break;
			}
			writeJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * @Description	: 任务包不做单独记录，存储于任务表中，用FMainTaskId=1做标记
	 * @Author		: liwx
	 * @Date		: 2013-03-21 16-25
	 * @param fId
	 * @return
	 */
	private String addTaskPackage(int fId) {
		TTask taskPackage = new TTask();
		taskPackage.setFTaskName(getString("packageName", ""));
		taskPackage.setFEntrustUnitId(fId);
		taskPackage.setFGivePersonId(SystemCurrentUser.getCurrentUser().getUserID());
		taskPackage.setFGiveTime(getString("dateTime", ""));
		taskPackage.setFNote(DateUtil.format());//记录当前创建时间
		TTask obj = service.addTaskPackage(taskPackage);	
		message.setSuccess(true);
		message.setRoot(obj);
		return getJsonFromObj(message);
	}
	
	private String editTaskPackage(int fId) {
		TTask obj = service.getTaskById(fId);
		obj.setFTaskName(getString("packageName", ""));
		obj.setFGiveTime(getString("dateTime", ""));
		obj.setFYear(Integer.parseInt(obj.getFGiveTime().substring(0, 4)));
		if(service.updateTaskPackage(obj)){
			message.setSuccess(true);
		}else{
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private String deleteTaskPackage(int fId) {
		message.setSuccess(service.deleteTaskPackage(fId));
		return getJsonFromObj(message);
	}
	
	private String getPackage(int fId) {
		message.setRoot(service.getTaskById(fId));
		message.setSuccess(true);
		return getJsonFromObj(message);
	}
}
