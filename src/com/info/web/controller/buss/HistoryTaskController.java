package com.info.web.controller.buss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TClient;
import com.info.domain.TImplementPlan;
import com.info.domain.TIndustry;
import com.info.domain.TTask;
import com.info.domain.TTeamPerson;
import com.info.domain.ViewClientIndustry;
import com.info.service.BussIndustrySerivce;
import com.info.service.HistoryTastService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
@Controller
public class HistoryTaskController extends BaseController {
	@Autowired
	HistoryTastService historyTastService;
	
	ResultMessage message;
	public String result = "";
	
	@RequestMapping(value = "/Buss/HistoryTaskService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();	
		try {
			switch (action) {
			case 1:		
				Result = GetTeamPersonListForIPlanID();
				break;	
			case 2:		
				Result = saveOrUpdate();
				break;	
			case 3:		
				Result = saveOrUpdateTeamPerson();
				break;	
			case 4:		
				Result = deleteTeamPerson();
				break;	
			case 5:		
				Result = deleteTaskAndTaskPlan();
				break;
			case 6:
			    Result=getTaskplan();
			    break;
			}
			writeJsonString(Result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 输出响应json串

		
	}
	/**
	 * 
	 * @Description	: 返回taskplan
	 * @Author		: chunlei
	 * @Date		: 2013-04-18 11-18
	 * @return
	 */
	private String getTaskplan() {
	    int taskid=getInt("taskid", -1);
	    if(taskid==-1){
		message.setSuccess(false);
		message.setMessage("加载失败！请与管理员联系");
	    }else{
		try {
		    List<Object> list=historyTastService.gettaskplan(taskid);
		    message.setSuccess(true);
		    message.setMessage("加载完成！");
		    message.setRoot(list);
		} catch (Exception e) {
		    message.setSuccess(false);
		    message.setMessage("加载失败！请与管理员联系");
		}
	    }
	    return getJsonFromObj(message);
	}

	private String deleteTaskAndTaskPlan() {
		Integer taskid = getInt("taskid", 0);
		Integer taskplanid = getInt("taskplanid", 0);
		String ids = getString("ids", "");
		if(null != ids && !"".equals(ids) && taskid > 0 && taskplanid > 0){
			if(historyTastService.deleteTaskAndTaskPlanAndPerson(taskid, taskplanid, ids)){
				message.setSuccess(true);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else if(taskid > 0 && taskplanid > 0){
			if(historyTastService.deleteTaskAndTaskPlan(taskid, taskplanid)){
				message.setSuccess(true);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}

	private String deleteTeamPerson() {
		Integer id = getInt("FId", 0);
		if(id > 0){
			if(historyTastService.deleteTeamPerson(id)){
				message.setSuccess(true);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}

	private String saveOrUpdateTeamPerson() {
		Integer FId = getInt("teamperson_FId", 0);
		Integer fkImplementPlanId = getInt("fkImplementPlanId", 0);
		Integer FPersonnelId = getInt("FPersonnelId", 0);
		String FPersonnelName = getString("FPersonnelName", "");
		String FJobContent = getString("FJobContent", "");
		String FAsPosition = getString("FAsPosition", "");
		String FContactPhone = getString("FContactPhone", "");
		String FNote = getString("FNote", "");
		if(FId > 0){
			TTeamPerson teamPerson = historyTastService.getTeamPersonByFId(FId);
			teamPerson.setFkImplementPlanId(fkImplementPlanId);
			teamPerson.setFPersonnelId(FPersonnelId);
			teamPerson.setFPersonnelName(FPersonnelName);
			teamPerson.setFJobContent(FJobContent);
			teamPerson.setFAsPosition(FAsPosition);
			teamPerson.setFContactPhone(FContactPhone);
			teamPerson.setFNote(FNote);
			teamPerson = historyTastService.updateTeamPerson(teamPerson);
			if(null != teamPerson){
				message.setSuccess(true);
				message.setRoot(teamPerson);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			TTeamPerson teamPerson = new TTeamPerson();
			teamPerson.setFkImplementPlanId(fkImplementPlanId);
			teamPerson.setFPersonnelId(FPersonnelId);
			teamPerson.setFPersonnelName(FPersonnelName);
			teamPerson.setFJobContent(FJobContent);
			teamPerson.setFAsPosition(FAsPosition);
			teamPerson.setFContactPhone(FContactPhone);
			teamPerson.setFNote(FNote);
			teamPerson = historyTastService.saveTeamPerson(teamPerson);
			if(null != teamPerson){
				message.setSuccess(true);
				message.setRoot(teamPerson);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}

	private String saveOrUpdate() {
		//任务表
	    	Integer typeid=getInt("typeId", -1);
		Integer taskFId =  getInt("FId", 0);
		String FTaskNumbers = getString("FTaskNumbers", "");
		String FTaskName = getString("FTaskName", "");
		Integer FEntrustUnitId = getInt("FEntrustUnitId", 0);
		String FEntrustUnitName = getString("FEntrustUnitName", "");
		String FProjectScale = getString("FProjectScale", "");
		String FStructureType = getString("FStructureType", "");
		String FIndustryCategoryId = getString("FIndustryCategoryId", "");
		String FIndustryCategoryName = getString("FIndustryCategoryName", "");
		Integer FServiceCategoryId=getInt("FServiceCategoryId", 0);
		String FServiceCategory = getString("FServiceCategory", "");
		Integer FBusinessCategoryId=getInt("FBusinessCategoryId", 0);
		String FBusinessCategory = getString("FBusinessCategory", "");
		String FContractYjCharge = getString("FContractYjCharge", "");
		Integer FDepartmentId =  getInt("FDepartmentId", 0);
		String FDepartmentName = getString("FDepartmentName", "");
		String FYjstartTime = getString("FYjstartTime", "");
		String FYjfinishTime = getString("FYjfinishTime", "");
		Integer FGivePersonId = getInt("FGivePersonId", 0);
		String FGivePersonName = getString("FGivePersonName", "");
		String FGiveTime = getString("FGiveTime", "");
		Integer FDeptMgrId = getInt("FDeptMgrId", 0);
		String FDeptMgrName = getString("FDeptMgrName", "");
		String FReceivingTaskTime = getString("FReceivingTaskTime", "");
		Integer FProjMgrId = getInt("FProjMgrId", 0);
		String FProjMgrName = getString("FProjMgrName", "");
		String FProjMgrViceId = getString("FProjMgrViceId", "0");
		String FProjMgrViceName = getString("FProjMgrViceName", "");
		//任务计划表
		Integer taskplanFId = getInt("taskplan_FId", 0);
		String FPlanNumbers = getString("FPlanNumbers", "");
		Integer fkTaskId = getInt("FId", 0);
		String FCollectDataTime = getString("FCollectDataTime", "");
		String FProcessImnTime = getString("FProcessImnTime", "");
		String FSubmitRewTime = getString("FSubmitRewTime", "");
		String FIssueResultsTime = getString("FIssueResultsTime", "");
		String FOther = getString("FOther", "");
		String FDeptOpinion = getString("FDeptOpinion", "");
		//String FDeptOpinionTime = getString("FDeptOpinionTime", "");
		String FPlanningPerId = getString("FPlanningPerId", "");
		String FPlanningPerName = getString("FPlanningPerName", "");
		String FPlanningTime = getString("FPlanningTime", "");
		if(taskFId > 0 && taskplanFId > 0){
			TTask task = historyTastService.getTaskByFId(taskFId);
			task.setFTaskName(FTaskName);
			task.setFTaskNumbers(FTaskNumbers);
			task.setFEntrustUnitId(FEntrustUnitId);
			task.setFEntrustUnitName(FEntrustUnitName);
			task.setFProjectScale(FProjectScale);
			task.setFStructureType(FStructureType);
			task.setFIndustryCategoryId(FIndustryCategoryId);
			task.setFIndustryCategoryName(FIndustryCategoryName);
			task.setFServiceCategoryId(FServiceCategoryId);
			task.setFServiceCategory(FServiceCategory);
			task.setFBusinessCategoryId(FBusinessCategoryId);
			task.setFBusinessCategory(FBusinessCategory);
			task.setFContractYjCharge(FContractYjCharge);
			task.setFDepartmentId(FDepartmentId);
			task.setFDepartmentName(FDepartmentName);
			task.setFYjstartTime(FYjstartTime);
			task.setFYjfinishTime(FYjfinishTime);
			task.setFGivePersonId(FGivePersonId);
			task.setFGivePersonName(FGivePersonName);
			task.setFGiveTime(FGiveTime);
			task.setFDeptMgrId(FDeptMgrId);
			task.setFDeptMgrName(FDeptMgrName);
			task.setFReceivingTaskTime(FReceivingTaskTime);
			task.setFProjMgrId(FProjMgrId);
			task.setFProjMgrName(FProjMgrName);
			task.setFProjMgrViceId(FProjMgrViceId);
			task.setFProjMgrViceName(FProjMgrViceName);
			TImplementPlan taskplan = historyTastService.getImplementPlanByFId(taskplanFId);
			taskplan.setFPlanNumbers(FPlanNumbers);
			taskplan.setFkTaskId(fkTaskId);
			taskplan.setFCollectDataTime(FCollectDataTime);
			taskplan.setFProcessImnTime(FProcessImnTime);
			taskplan.setFSubmitRewTime(FSubmitRewTime);
			taskplan.setFIssueResultsTime(FIssueResultsTime);
			taskplan.setFOther(FOther);
			taskplan.setFDeptOpinion(FDeptOpinion);
			//taskplan.setFDeptOpinionTime(FDeptOpinionTime);
			taskplan.setFPlanningPerId(FPlanningPerId);
			taskplan.setFPlanningPerId(FPlanningPerId);
			taskplan.setFPlanningPerName(FPlanningPerName);
			taskplan.setFPlanningTime(FPlanningTime);
			taskplan = historyTastService.update(task, taskplan);
			if(null != taskplan){
				message.setSuccess(true);
				message.setRoot(taskplan);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else {
			TTask task = new TTask();
			task.setFTaskName(FTaskName);
			task.setFTaskNumbers(FTaskNumbers);
			task.setFEntrustUnitId(FEntrustUnitId);
			task.setFEntrustUnitName(FEntrustUnitName);
			task.setFProjectScale(FProjectScale);
			task.setFStructureType(FStructureType);
			task.setFIndustryCategoryId(FIndustryCategoryId);
			task.setFIndustryCategoryName(FIndustryCategoryName);
			task.setFServiceCategoryId(FServiceCategoryId);
			task.setFServiceCategory(FServiceCategory);
			task.setFBusinessCategoryId(FBusinessCategoryId);
			task.setFBusinessCategory(FBusinessCategory);
			task.setFContractYjCharge(FContractYjCharge);
			task.setFDepartmentId(FDepartmentId);
			task.setFDepartmentName(FDepartmentName);
			task.setFYjstartTime(FYjstartTime);
			task.setFYjfinishTime(FYjfinishTime);
			task.setFGivePersonId(FGivePersonId);
			task.setFGivePersonName(FGivePersonName);
			task.setFGiveTime(FGiveTime);
			task.setFDeptMgrId(FDeptMgrId);
			task.setFDeptMgrName(FDeptMgrName);
			task.setFReceivingTaskTime(FReceivingTaskTime);
			task.setFProjMgrId(FProjMgrId);
			task.setFProjMgrName(FProjMgrName);
			task.setFProjMgrViceId(FProjMgrViceId);
			task.setFProjMgrViceName(FProjMgrViceName);
			
			TImplementPlan taskplan = new TImplementPlan();
			taskplan.setFPlanNumbers(FPlanNumbers);
			taskplan.setFkTaskId(fkTaskId);
			taskplan.setFCollectDataTime(FCollectDataTime);
			taskplan.setFProcessImnTime(FProcessImnTime);
			taskplan.setFSubmitRewTime(FSubmitRewTime);
			taskplan.setFIssueResultsTime(FIssueResultsTime);
			taskplan.setFOther(FOther);
			taskplan.setFDeptOpinion(FDeptOpinion);
			//taskplan.setFDeptOpinionTime(FDeptOpinionTime);
			taskplan.setFPlanningPerId(FPlanningPerId);
			taskplan.setFPlanningPerId(FPlanningPerId);
			taskplan.setFPlanningPerName(FPlanningPerName);
			taskplan.setFPlanningTime(FPlanningTime);
			taskplan = historyTastService.save(task, taskplan,10001);
			if(null != taskplan){
				message.setSuccess(true);
				message.setRoot(taskplan);
				message.setTotalProperty(1);
				historyTastService.setprocesshistory(taskplan.getFkTaskId());
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}

	private String GetTeamPersonListForIPlanID() {
		Integer ImplementPlanId =  getInt("ID", 0);
		Map<String, Object> map=new HashMap<String, Object>();
		if(ImplementPlanId > 0){
			List<TTeamPerson> teamPersons = historyTastService.GetTeamPersonListForIPlanID(ImplementPlanId);
			if( teamPersons.size() >= 0){
				map.put("rows", teamPersons);
				map.put("total", teamPersons.size());
			}else{
				map.put("rows", new ArrayList<TTeamPerson>());
				map.put("total", 0);
			}
		}
		return getJsonFromObj(map);
	}

}
