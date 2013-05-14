package com.info.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TAppFunction;
import com.info.service.SystemFunctionService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemFunctionController extends BaseController {
	/**
	 * @author liwx 功能菜单管理
	 * @param action
	 *            1:获取树形菜单列表
	 * @param action
	 *            2:保存、新增、修改
	 * action 
	 * 		3:删除
	 */
	String result;
	
	@Autowired
	SystemFunctionService service;
	@RequestMapping(value = "/system/SvrService/FunctionService/{action}")
	public void ControllerService(@PathVariable("action") Integer action,
			HttpServletResponse response) {
		CurrentResponse = response;
		message = new ResultMessage();		
		switch (action) {
		case 1:
			result = getFunctionMenu();
			break;
		case 2:
			result = saveOrUpdate();
			break;
		case 3:
			result = deleteFunction();
			break;
		}
		writeJsonString(result);
	}
	
	@SuppressWarnings("rawtypes")
	private String getFunctionMenu(){
		List functionList = service.getFunctionMenu();
		return getJsonFromArray(functionList);
	}
	
	private String saveOrUpdate() {
		//获取request参数
		TAppFunction object = new TAppFunction();
		object.setFId(getInt("FId", 0));
		object.setFParentId(getInt("FParentId", 0));
		object.setFName(getString("FName", ""));
		object.setFFunctionUrl(getString("FFunctionUrl", ""));
		object.setFSort(getInt("FSort", 0));
		object.setFState(getInt("FState", 0));	
		//提交数据库
		TAppFunction functionObj = service.saveOrUpdate(object);
		if(functionObj==null)
		{
			message.setSuccess(false);			
		}else {
			message.setSuccess(true);
			message.setRoot(functionObj);
		}
		return getJsonFromObj(message);
	}
	
	private String deleteFunction() {
		Integer FId=getInt("FId", 0);
		if(service.delete(FId)){
			message.setSuccess(true);
		}else {
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
}
