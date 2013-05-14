package com.info.web.controller.buss;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.info.domain.TAppCode;
import com.info.domain.TAppTreeCode;
import com.info.domain.TAppUser;
import com.info.domain.TProjectCheck;
import com.info.service.BussCommonUtils;
import com.info.service.BussProjectCheckService;
import com.info.service.WfProcessUtils;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
@Controller
public class BussProjectCheckController extends BaseController {
	@Autowired
	BussProjectCheckService projectCheckService;
	@Autowired
	WfProcessUtils utils;
	@Autowired
	BussCommonUtils commonUtils;

	ResultMessage message;
	public String result = "";
	
	@RequestMapping(value = "/Buss/ProjectCheckService/{action}")
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
			Result = getProjectCheckForID();
			break;	
		case 3:		
			Result = delete();
			break;
		case 4:		
			Result = post(getInt("processId", -1),
					getInt("aboveActId", -1), 
					getInt("uid", -1), 
					getString("remark", ""));
			break;	
		case 5:		
			Result = getCodeByID();
			break;	
		case 6:		
			Result = activeReturn();
			break;	
		case 7:		
			Result = activeUrge();
			break;
		case 8:
			Result=setActiveAcceptTime(getInt("activeId", -1));
			break;
		case 9:
			Result=getAccept(getInt("activeId", -1));
			break;
		case 10:
			Result=setActiveComplet(getInt("activeId", -1));
			break;
		}
		// 输出响应json串
		writeJsonString(Result);
	}
	/**
	 * 
	 * @Description	: 设置任务完成
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 20-44
	 * @param activeId
	 * @return
	 */
	private String setActiveComplet(int activeId) {
		if(utils.activeComplet(activeId)){
			message.setSuccess(true);
			message.setMessage("任务完成！");
		}else {
			message.setMessage("提交失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 获取当前办理人
	 * @param activeId
	 * @return
	 */
	private String getAccept(int activeId) {
		return getJsonFromObj(projectCheckService.getWfProccessActiveByID(activeId));
	}
	/**
	 * 设置接收时间
	 * 
	 * @param activeId
	 * @return
	 */

	private String setActiveAcceptTime(int activeId) {
		try {
			utils.setActiveAcceptTime(activeId);
			message.setMessage("接收成功！");
		} catch (Exception e) {
			message.setMessage("接收失败！");
		}
			return getJsonFromObj(message);
	}

	private String activeUrge() {
		Integer activeId = getInt("activeId", 0);
		String remark = getString("remark", "");
		if(projectCheckService.activeUrge(activeId, remark)){
			message.setSuccess(true);
			message.setTotalProperty(1);
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}

	private String activeReturn() {
		//Integer id = getInt("ID", 0);
		Integer activeId = getInt("activeId", 0);
		String remark = getString("remark", "");
		if(projectCheckService.activeReturn(activeId, remark)){
			message.setSuccess(true);
			message.setTotalProperty(1);
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}

	private String getCodeByID() {
		Integer id = getInt("ID", 0);
		if(id > 0 ){
			TAppTreeCode code = projectCheckService.getTreeCodeById(id);
			if(null != code){
				message.setSuccess(true);
				message.setRoot(code);
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
		String title = getString("title", "项目检查记录登记");
		int personId=getInt("FCheckPersonId", -1);
		String personName=getString("FCheckPersonName", "");
		if(id <= 0 ){
			Integer  fromPKId=commonUtils.getFormPKIDForProcess(processId);
			TProjectCheck projectCheck = new TProjectCheck();
			if (null==fromPKId) {
				projectCheck = projectCheckService.save(projectCheck,processId,title,personId,personName);
				if(projectCheck != null){
					message.setSuccess(true);
					message.setRoot(projectCheck);
					message.setTotalProperty(1);
				}else{
					message.setSuccess(false);
					message.setTotalProperty(0);
				}
			}else{
				projectCheck.setFId(fromPKId);
				message.setSuccess(true);
				message.setRoot(projectCheck);
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
		String FNumbers = getString("FNumbers", "");
		Integer fkTaskId = getInt("fkTaskId", 0);
		String FExecutePhase = getString("FExecutePhase", "");
		String FInspectionRecord = getString("FInspectionRecord", "");
		String FEpapo = getString("FEpapo", "");
		String FTcwcr = getString("FTcwcr", "");
		Integer FBcheckPersonId = getInt("FBcheckPersonId", 0);
		String FBcheckPersonName = getString("FBcheckPersonName", "");
		String FBeCheckedTime = getString("FBeCheckedTime", "");
		Integer FCheckPersonId = getInt("FCheckPersonId", 0);
		String FCheckPersonName = getString("FCheckPersonName", "");
		String FCheckPersonTime = getString("FCheckPersonTime", "");
		String FEpr = getString("FEpr", "");
		Integer FEprCheckPersonId = getInt("FEprCheckPersonId", 0);
		String FEprCheckPersonName = getString("FEprCheckPersonName", "");
		String FEprCheckPersonTime = getString("FEprCheckPersonTime", "");
		String title = getString("title", "项目检查记录登记");
		TProjectCheck projectCheck = projectCheckService.getProjectCheckForID(id);
		projectCheck.setFNumbers(FNumbers);
		projectCheck.setFkTaskId(fkTaskId);
		projectCheck.setFExecutePhase(FExecutePhase);
		projectCheck.setFInspectionRecord(FInspectionRecord);
		projectCheck.setFEpapo(FEpapo);
		projectCheck.setFTcwcr(FTcwcr);
		projectCheck.setFBcheckPersonId(FBcheckPersonId);
		projectCheck.setFBcheckPersonName(FBcheckPersonName);
		projectCheck.setFBeCheckedTime(FBeCheckedTime);
		projectCheck.setFCheckPersonId(FCheckPersonId);
		projectCheck.setFCheckPersonName(FCheckPersonName);
		projectCheck.setFCheckPersonTime(FCheckPersonTime);
		projectCheck.setFEpr(FEprCheckPersonTime);
		projectCheck.setFEpr(FEpr);
		projectCheck.setFEprCheckPersonId(FEprCheckPersonId);
		projectCheck.setFEprCheckPersonName(FEprCheckPersonName);
		projectCheck.setFEprCheckPersonTime(FEprCheckPersonTime);
		projectCheck = projectCheckService.update(projectCheck, processId, title);
		message.setSuccess(true);
		message.setRoot(projectCheck);
		message.setTotalProperty(1);
		
		return getJsonFromObj(message);
	}

	private String getProjectCheckForID() {
		Integer id = getInt("ID", 0);
		if(id > 0 ){
			TProjectCheck projectCheck = projectCheckService.getProjectCheckForID(id);
			if(projectCheck != null){
				message.setSuccess(true);
				message.setRoot(projectCheck);
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
		//TWfProcess process = projectCheckService.getWfProcessByID(processId);
		//TWfProccessActive active = projectCheckService.getWfProccessActiveByID(activeId);
		
		if(id > 0 && processId > 0 && activeId > 0 ){
			//if(projectCheckService.isCanDestroy(processId)){
				if(projectCheckService.delete(id,activeId,processId)){
					message.setSuccess(true);
					message.setTotalProperty(1);
				}else{
					message.setSuccess(false);
					message.setTotalProperty(0);
					message.setMessage("撤销失败，请检查网络是否正常！");
				}
			/*}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
				message.setMessage("此业务正在办理中，不能撤销！");
			}*/
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
			message.setMessage("撤销失败，获取数据异常！");
		}
		return getJsonFromObj(message);
	}

	private String post(int processId,int aboveActId,int id,String remark) {
		boolean b=utils.addProcessActiveItem(processId, id, aboveActId, remark)>0?true:false;
		TAppUser user=projectCheckService.getuserById(id);
		message.setRoot("{\"FId\":\""+user.getFId()+"\",\"FName\":\""+user.getFName()+"\"}");
		if (b) {
			message.setMessage("提交成功！");
		}else {
			message.setMessage("提交失败！");
		}
		message.setSuccess(b);
		return getJsonFromObj(message);
	}
	
}
