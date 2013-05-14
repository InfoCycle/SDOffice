package com.info.web.controller.info;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.AppSEQHelper;
import com.info.common.util.StringUtil;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class ThreadSEQ extends BaseController{
	ResultMessage message;	
	@Autowired
	AppSEQHelper seqHelper;
	@RequestMapping(value = "/info/ThreadSEQ/")
	public void threadSEQTest() {
		ExecutorService executor = Executors.newFixedThreadPool(50);
		for(int i=0;i<50;i++){
			executor.execute(new Runnable() {
				String idString="";
				public void run() {
					idString = StringUtil.getFixedLen(seqHelper.getCurrentVal("SEQ_Thread"), 6);
					System.out.println(idString);
				} 			
			});
		}
		message.setSuccess(true);
		writeJsonString(getJsonFromObj(message));
	}
	
	@RequestMapping(value = "/info/getRemoteMac/")
	public void getRemoteMacAddress(){
		String Ip = getRemoteAddress();
		String Mac= getRemoteMACAddress(Ip);
		System.out.println(Mac);
	}
}
