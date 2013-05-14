package com.info.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.info.domain.TAppOrg;
import com.info.service.SystemOrgService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemOrgController extends BaseController {

	String result;
	
	@Autowired
	SystemOrgService service;
	@RequestMapping(value="/system/SvrService/OrgService/{action}")
	public void getDataService(@PathVariable("action") Integer action,HttpServletResponse response)
	{
		CurrentResponse = response;
		message = new ResultMessage();		
		switch (action) {
		case 1:
			result = getOrgData();
			break;
		case 2:
			result = saveOrUpdate();
			break;
		case 3:
			result = deleteOrgData();
			break;
		}
		writeJsonString(result);
	}
	
	@SuppressWarnings("rawtypes")
	private String getOrgData() {
		List list = service.getOrgData();
		return getJsonFromArray(list);
	}
	
	private String saveOrUpdate() {
		TAppOrg org = new TAppOrg();
		org.setFId(getInt("FId", 0));
		org.setFParentId(getInt("FParentId", 0));
		org.setFName(getString("FName", ""));
		org.setFAno(getString("FAno", ""));
		org.setFSort(getInt("FSort", 0));
		org.setFState(getInt("FState", 1));
		TAppOrg object = service.saveOrUpdate(org);
		if(object!=null){
			message.setSuccess(true);
			message.setRoot(object);
		}else {
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	
	private String deleteOrgData() {
		Integer FId=getInt("FId", 0);
		if(service.delete(FId)){
			message.setSuccess(true);
		}else {
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
}
