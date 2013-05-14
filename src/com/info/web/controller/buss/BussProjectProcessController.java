package com.info.web.controller.buss;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.info.domain.TProjectProcess;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.service.BussCommonUtils;
import com.info.service.BussProjectProcessService;
import com.info.service.WfProcessService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussProjectProcessController extends BaseController{
	@Autowired
	BussProjectProcessService projectProcessService;
	@Autowired
	BussCommonUtils commonUtils;
	
	ResultMessage message;
	public String result = "";
	
	@RequestMapping(value = "/Buss/ProjectProcessService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();		
		switch (action) {
		case 0:		
			Result = save();
			break;	
		case 1:		
			Result = update();
			break;	
		case 2:		
			Result = getProjectProcessForID();
			break;	
		case 3:		
			Result = delete();
			break;
		case 4:		
			Result = post();
			break;	
		case 5:		
			Result = getTaskPMById();
			break;
		case 6:
		    Result = historyAdd();
		}
		// 输出响应json串

		writeJsonString(Result);
	}
	/**
	 * 
	 * @Description	: 历史项目添加
	 * @Author		: chunlei
	 * @Date		: 2013-04-23 17-43
	 * @return
	 */
	private String historyAdd() {
	    try {
		Integer id = getInt("ID", 0);
		Integer fkTaskId = getInt("fkTaskId", 0);
		String FTheFirst = getString("FTheFirst", "");
		String FTheSecond = getString("FTheSecond", "");
		String FOther = getString("FOther", "");
		Integer FJiLuRenId = getInt("FJiLuRenId", 0);
		String FJiLuRenName = getString("FJiLuRenName", "");
		String FJiLuTime = getString("FJiLuTime", new Date().toString());
		String FNote = getString("FNote", "");
		String title = getString("title", "");
		String Number=getString("FNumbers", "");
		TProjectProcess projectProcess = projectProcessService.getProjectProcessForID(id);
		projectProcess.setFkTaskId(fkTaskId);
		projectProcess.setFTheFirst(FTheFirst);
		projectProcess.setFTheSecond(FTheSecond);
		projectProcess.setFOther(FOther);
		projectProcess.setFJiLuRenId(FJiLuRenId);
		projectProcess.setFJiLuRenName(FJiLuRenName);
		projectProcess.setFJiLuTime(FJiLuTime);
		projectProcess.setFNote(FNote);
		projectProcess.setFNumbers(Number);
		
		
	    } catch (Exception e) {
		// TODO: handle exception
	    }
	    return null;
	}

	private String getTaskPMById() {
		Integer id = getInt("ID", 0);
		if(id > 0){
			Object object = projectProcessService.getTaskPMById(id);
			if(null != object){
				message.setSuccess(true);
				message.setRoot(object);
				message.setTotalProperty(1);
			}else {
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}

	private String save() {
		Integer id = getInt("ID", 0);
		Integer processId = getInt("processId", 0);
		String title = getString("title", "项目过程情况");
		if(id <= 0 ){
			Integer  fromPKId=commonUtils.getFormPKIDForProcess(processId);
			TProjectProcess projectProcess = new TProjectProcess();
			if(null==fromPKId){				
				projectProcess = projectProcessService.save(projectProcess,processId,title);
				if(projectProcess != null){
					message.setSuccess(true);
					message.setRoot(projectProcess);
					message.setTotalProperty(1);
					message.setMessage("加载成功!");
				}else{
					message.setSuccess(false);
					message.setTotalProperty(0);
				}
			}
			else {
				projectProcess.setFId(fromPKId);
				message.setSuccess(true);
				message.setRoot(projectProcess);
				message.setTotalProperty(1);
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}

	private String update() {
		
		Integer id = getInt("ID", 0);
		Integer processId = getInt("processId", 0);
		Integer fkTaskId = getInt("fkTaskId", 0);
		String FTheFirst = getString("FTheFirst", "");
		String FTheSecond = getString("FTheSecond", "");
		String FOther = getString("FOther", "");
		Integer FJiLuRenId = getInt("FJiLuRenId", 0);
		String FJiLuRenName = getString("FJiLuRenName", "");
		String FJiLuTime = getString("FJiLuTime", new Date().toString());
		String FNote = getString("FNote", "");
		String title = getString("title", "");
		String Number=getString("FNumbers", "");
		TProjectProcess projectProcess = projectProcessService.getProjectProcessForID(id);
		projectProcess.setFkTaskId(fkTaskId);
		projectProcess.setFTheFirst(FTheFirst);
		projectProcess.setFTheSecond(FTheSecond);
		projectProcess.setFOther(FOther);
		projectProcess.setFJiLuRenId(FJiLuRenId);
		projectProcess.setFJiLuRenName(FJiLuRenName);
		projectProcess.setFJiLuTime(FJiLuTime);
		projectProcess.setFNote(FNote);
		projectProcess.setFNumbers(Number);
		projectProcess = projectProcessService.update(projectProcess,
				processId, title);
		message.setSuccess(true);
		message.setRoot(projectProcess);
		message.setTotalProperty(1);
		
		return getJsonFromObj(message);
	}

	private String post() {
		Integer id = getInt("ID", 0);
		Integer activeId = getInt("activeId", 0);
		Integer fkTaskId = getInt("fkTaskId", 0);
		String FTheFirst = getString("FTheFirst", "");
		String FTheSecond = getString("FTheSecond", "");
		String FOther = getString("FOther", "");
		Integer FJiLuRenId = getInt("FJiLuRenId", 0);
		String FJiLuRenName = getString("FJiLuRenName", "");
		String FJiLuTime = getString("FJiLuTime", new Date().toString());
		String FNote = getString("FNote", "");
		TProjectProcess projectProcess = projectProcessService.getProjectProcessForID(id);
		projectProcess.setFkTaskId(fkTaskId);
		projectProcess.setFTheFirst(FTheFirst);
		projectProcess.setFTheSecond(FTheSecond);
		projectProcess.setFOther(FOther);
		projectProcess.setFJiLuRenId(FJiLuRenId);
		projectProcess.setFJiLuRenName(FJiLuRenName);
		projectProcess.setFJiLuTime(FJiLuTime);
		projectProcess.setFNote(FNote);
		projectProcess = projectProcessService.post(projectProcess, activeId);
		message.setSuccess(true);
		message.setRoot(projectProcess);
		message.setTotalProperty(1);
		
		return getJsonFromObj(message);
	}
	
	private String getProjectProcessForID() {
		Integer id = getInt("ID", 0);
		if(id > 0 ){
			TProjectProcess projectProcess = projectProcessService.getProjectProcessForID(id);
			if(projectProcess != null){
				message.setSuccess(true);
				message.setRoot(projectProcess);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setRoot(null);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}
	
	private String delete() {
		Integer id = getInt("ID", 0);
		Integer processId = getInt("processId", 0);
		Integer activeId = getInt("activeId", 0);
		if(id > 0 && processId > 0 && activeId > 0 ){
			if(projectProcessService.isCanDestroy(processId)){
				if(projectProcessService.delete(id, activeId,processId)){
					message.setSuccess(true);
					message.setTotalProperty(1);
				}else{
					message.setSuccess(false);
					message.setTotalProperty(0);
					message.setMessage("撤销失败，请检查网络是否正常！");
				}
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
				message.setMessage("此业务正在办理中，不能撤销！");
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
			message.setMessage("撤销失败，获取数据异常！");
		}
		return getJsonFromObj(message);
	}
}
