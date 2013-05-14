package com.info.web.controller.system;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.service.SystemUeserStationService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemUserStationController extends BaseController{
	@Autowired
	SystemUeserStationService stationService;
	@RequestMapping( value = "/system/station/")
	public void getStationData(HttpServletResponse response){
		CurrentResponse = response;
		try {
			message = new ResultMessage();
			message.setSuccess(true);
			message.setRoot(stationService.getStationData());
			writeJsonString(getJsonFromObj(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
