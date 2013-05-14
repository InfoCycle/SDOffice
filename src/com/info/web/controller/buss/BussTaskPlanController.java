package com.info.web.controller.buss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;


import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TImplementPlan;
import com.info.domain.TTask;
import com.info.domain.TTeamPerson;
import com.info.domain.ViewImplementPlan;
import com.info.service.BussTaskPlanService;
import com.info.web.CurrentUser;
import com.info.web.EasyDataGrid;
import com.info.web.JSONParam;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussTaskPlanController extends BaseController {
	@Autowired
	BussTaskPlanService tPlanService;

	ResultMessage message;
	public String result = "";

	private static final String SUCCESS_STRING = "{\"success\":true}";
	private static final String FAIL_STRING = "{\"success\":false}";

	/**
	 * action 请求类型，目的为了减少MVC映射
	 * 
	 * @author jibinbin 1：获得任务信息For ID ;/TaskService/1
	 * @param action
	 * @param response
	 */
	@RequestMapping(value = "/Buss/TaskPlanService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response, TImplementPlan implementPlan)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		Integer id = 0;
		switch (action) {
    		case 1:
    			id = getInt("ID", 0);
    			Result = getTaskForId(id);
    			break;
    		case 2:
    			id = getInt("ID", 0);
    			Result = getTaskPlanForId(id);
    			break;
    		case 3:
    			Integer taskId = getInt("TaskID", 0);
    			Result = Insert(taskId);
    			break;
    		case 4:
    			Result = Save();
    			break;
    		case 5:
    			id = getInt("ID", 0);
    			Result = Cancel(id);
    			break;
    		case 6:      //审批通过、不通过
    			Result = Approval();
    			break;
    		case 7:          //催办      
                Result = UrgeSb();
                break;
    		case 8:
    			Result = SaveTeamPerson();
    			break;
    		case 9:
    			Result = DeleteTeamPerson();
    			break;
    		case 10:
    			Result = UpdateDeptOpinion();
    			break;
    		case 11:	 //提交
    			Result = Post();
    			break;
    		case 12:			
    			Result = BanJie();
    			break;
    		case 13: //抄送生产部及总工办
    			Result = CopyAction();
    			break;
    		case 14: //签收
    		    Result = Acceptance();
                break;
    		case 15: //阅览
                Result = Read();
                break;            
		}
		// 输出响应json串
		writeJsonString(Result);
	}

	// 通过系统编号ID获得任务信息。
	public String getTaskForId(Integer id) {
		List<TTask> objTask = tPlanService.GetTaskListForID(id);
		if (objTask != null && objTask.size() > 0) {
			message.setTotalProperty(1);
			message.setSuccess(true);
			message.setRoot(objTask);
		} else {
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}

	// 通过系统编号ID获得任务计划信息。
	public String getTaskPlanForId(Integer id) {
		Integer activeId=getInt("activeId", -1);
		List<ViewImplementPlan> objTaskPlan = tPlanService.GetTaskPlanForID(id,activeId);
		if (objTaskPlan != null && objTaskPlan.size() > 0) {
			message.setTotalProperty(1);
			message.setSuccess(true);
			message.setRoot(objTaskPlan);
		} else {
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}

	// 新建任务计划
	public String Insert(Integer taskId) {
		List<TImplementPlan> objTaskPlan = tPlanService.Insert(taskId);
		if (objTaskPlan != null && objTaskPlan.size() > 0) {
			message.setTotalProperty(1);
			message.setSuccess(true);
			message.setRoot(objTaskPlan);
		} else {
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}

	public String Save() {
		TImplementPlan obj = new TImplementPlan();
		int processId = getInt("processId", -1);
		if(processId==-1)
			return FAIL_STRING;
		obj.setFId(getInt("FId", 0));
		obj.setFPlanNumbers(getString("FPlanNumbers", ""));
		obj.setFkTaskId(getInt("FkTaskId", 0));
		obj.setFCollectDataTime(getString("FCollectDataTime", ""));
		obj.setFProcessImnTime(getString("FProcessImnTime", ""));
		obj.setFSubmitRewTime(getString("FSubmitRewTime", ""));
		obj.setFIssueResultsTime(getString("FIssueResultsTime", ""));
		obj.setFOther(getString("FOther", ""));
		 //obj.setFDeptOpinion(getString("FDeptOpinion", ""));
		 //obj.setFDeptOpinionTime(getString("FDeptOpinionTime", ""));
		CurrentUser objCU=SystemCurrentUser.getCurrentUser();
		obj.setFPlanningPerId(String.valueOf(objCU.getUserID()));
		obj.setFPlanningPerName(objCU.getUserName());
		obj.setFPlanningTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//getString("FPlanningTime", ""));
		Result resultObj = new Result();
		resultObj = tPlanService.Save(obj,processId);
		if (resultObj.getSuccess())
			result = SUCCESS_STRING + ",\"Id\":" + resultObj.getId() + "}";
		else {
			result = FAIL_STRING;
		}
		result = result.replace("},", ",");
		return result;
	}

	public String UpdateDeptOpinion(){

		Integer FId= getInt("FId", 0);
		String FDeptOpinions=getString("FDeptOpinions", "");
		String FDeptOpinionTime=getString("FDeptOpinionTime", "");
		if (tPlanService.UpdateDeptOpinion(FId,FDeptOpinions,FDeptOpinionTime)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}
	public String Cancel(Integer id) {
		if (tPlanService.Cancle(id)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}
	/**
     * 
     * @Description : 催办下一步
     * @Author      : jibb
     * @Date        : 2013-3-19 上午12:03:39
     * @return
     */
    public String UrgeSb(){
        int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        if (tPlanService.UrgeSb(aboveActId,remark)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
    }
    
	/*
	 组员信息表
	 */	 
	@RequestMapping(value = "/Buss/TaskPlanService/searchPlanPerson/{pId}")
	public  void GetTeamPersonList(@PathVariable("pId") Integer pId,HttpServletResponse response){
		CurrentResponse = response;	
		//2013-3-20注销，改为EasyuiGrid
	    /*String sEcho = getString("sEcho", "");  
	    Integer planId =pId;  
	    Integer start = getInt("iDisplayStart", 0);  
	    Integer length = getInt("iDisplayLength", 10);  	      
	    JDataTablesReturnData returnData=new JDataTablesReturnData();
	    List<TTeamPerson> objList = tPlanService.GetTeamPersonListForIPlanID(planId, start, length);
	    returnData.setAaData(objList);
	    returnData.setiTotalDisplayRecords(objList.size());
	    returnData.setiTotalRecords(objList.size());
	    returnData.setsEcho(sEcho);	    	    
	    writeJsonString(getJsonFromObj(returnData));*/
		EasyDataGrid ResultJson= new EasyDataGrid();
		try {           
            List<TTeamPerson> objectList=tPlanService.GetTeamPersonListForIPlanIDEasy(pId);           
            if(objectList.size()>0){
                ResultJson.setRows(objectList);
                ResultJson.setTotal(Long.valueOf(objectList.size()));
            }else{
                ResultJson.setRows(objectList);
                ResultJson.setTotal(Long.valueOf(0));
            }
        } catch (Exception e) {
             
        }
		writeJsonString(getJsonFromObj(ResultJson));
	}
	/*
	@RequestMapping(value = "/Buss/TaskPlanService/searchPlanPerson", method = RequestMethod.POST)  
	@ResponseBody
	public  void GetTeamPersonList(@RequestBody JSONParam[] params) throws IllegalAccessException, InvocationTargetException{		
	    HashMap<String, String> paramMap = convertToMap(params);  
	    String sEcho = paramMap.get("sEcho");  
	    Integer planId =Integer.parseInt(paramMap.get("planId"));  
	    Integer start = Integer.parseInt(paramMap.get("iDisplayStart"));  
	    Integer length = Integer.parseInt(paramMap.get("iDisplayLength"));  	      
	    JDataTablesReturnData returnData=new JDataTablesReturnData();
	    List<TTeamPerson> objList = tPlanService.GetTeamPersonListForIPlanID(planId, start, length);
	    returnData.setAaData(objList);
	    returnData.setiTotalDisplayRecords(objList.size());
	    returnData.setiTotalRecords(objList.size());
	    returnData.setsEcho(sEcho);	    	    
	    writeJsonString(getJsonFromObj(returnData));	    
	}
	*/
	public String SaveTeamPerson() {
		TTeamPerson obj=new TTeamPerson();
		obj.setFId(getInt("PFId", 0));
		obj.setFkImplementPlanId(getInt("PFkImplementPlanId", 0));
		obj.setFPersonnelId(getInt("PFPersonnelId", 0));
		obj.setFPersonnelName(getString("PFPersonnelName", ""));
		obj.setFJobContent(getString("PFJobContent", ""));
		obj.setFAsPosition(getString("PFAsPosition", ""));
		obj.setFContactPhone(getString("PFContactPhone", ""));
		List<TTeamPerson> objlist=tPlanService.SaveTeamPerson(obj);
		if (objlist != null && objlist.size() > 0) {
			message.setTotalProperty(objlist.size());
			message.setSuccess(true);
			message.setRoot(objlist);
		} else {
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}

	public String DeleteTeamPerson() {
		Integer tpidInteger=getInt("TPId", 0);
		if(tPlanService.DeleteTeamPerson(tpidInteger)){
			result=SUCCESS_STRING;
		}else {
			result=FAIL_STRING;
		}
		return result;
	}

	public String UpdateTeamPerson() {
		TTeamPerson obj=new TTeamPerson();
		obj.setFId(getInt("PFId", 0));
		obj.setFkImplementPlanId(getInt("PFkImplementPlanId", 0));
		obj.setFPersonnelId(getInt("PFPersonnelId", 0));
		obj.setFPersonnelName(getString("PPersonnelName", ""));
		obj.setFJobContent(getString("PFJobContent", ""));
		obj.setFAsPosition(getString("PFAsPosition", ""));
		obj.setFContactPhone(getString("PFContactPhone", ""));
		Result objResult=tPlanService.UpdateTeamPerson(obj);
		if (objResult.getSuccess()) {
			 result = SUCCESS_STRING;
		} else {
			 result = FAIL_STRING;
		}
		return  result;
	}

	/**
	 * 提交计划到部门
	 * @Description	: TODO(这里用一句话描述这个方法的作用)
	 * @Author		: jibb
	 * @Date		: 2013-03-22 11-06
	 * @return
	 */
		public String Post() {
			int processId= getInt("ProcessId", -1);
			int acceptUserId= getInt("AcceptUserId", -1);
			int aboveActId= getInt("AboveActId", -1);
			String remark= getString("Remark", "");
			int formPKID= getInt("formPKID", -1);
			if (tPlanService.Post(processId,acceptUserId,aboveActId,remark,formPKID)) {
				result = SUCCESS_STRING;
			} else {
				result = FAIL_STRING;
			}
			return result;
		}
		/**
		 * 办结计划
		 * @function:
		 * @data: 2013-1-28下午2:17:07
		 * @author jibinbin
		 * @return
		 *
		 */
		public String BanJie(){
			int processId= getInt("ProcessId", -1);
			int aboveActId= getInt("AboveActId", -1);			
			int formPKID= getInt("formPKID", -1);
			//int PFDepartmentId=getInt("PFDepartmentId",-1);
			//String PFDepartmentName= getString("PFDepartmentName", "");
			if (tPlanService.BanJie(processId,aboveActId,formPKID)) {
				result = SUCCESS_STRING;
			} else {
				result = FAIL_STRING;
			}
			return result;
		}
		/**
		 * 签收
		 * @Description	:签收
		 * @Author		: jibb
		 * @Date		: 2013-03-21 21-06
		 * @return
		 */
		public  String Acceptance() {
			//int processId= getInt("ProcessId", -1);
			//int acceptUserId= getInt("AcceptUserId", -1);
		    int aboveActId= getInt("AboveActId", -1);
            int FormPKID= getInt("FormFId", -1);
            int isUpdateOther= getInt("isUpdateOther", -1);
            String remark= getString("Remark", ""); 
			if (tPlanService.Acceptance(aboveActId,remark,FormPKID,isUpdateOther)) {
				result = SUCCESS_STRING;
			} else {
				result = FAIL_STRING;
			}
			return result;
		}
		public String Read(){
            int aboveActId= getInt("AboveActId", -1);
            //String remark= getString("Remark", ""); 
            if (tPlanService.ReadExt(aboveActId)) {
                result = SUCCESS_STRING;
            } else {
                result = FAIL_STRING;
            }
            return result;
		}
		/**
		 * 抄送生产部和总工办 有问题
		 * @return
		 */
		public String CopyAction(){
			int processId= getInt("ProcessId", -1);
			int aboveActId= getInt("AboveActId", -1);
			int formPKID= getInt("formPKID", -1);
			String remark=getString("Remark", "");
			Result resultObj = new Result();
			resultObj=tPlanService.CopyAction(processId, aboveActId,formPKID,remark);
			return getJsonFromObj(resultObj);
		}
		
		/**
		 * 
		 * @Description	: 审批通过、不通过
		 * @Author		: jibb
		 * @Date		: 2013-03-22 14-24
		 * @return
		 */
		public String Approval(){
		    int processId= getInt("ProcessId", -1);
            int acceptUserId= getInt("AcceptUserId", -1);
            int aboveActId= getInt("AboveActId", -1);
            String remark= getString("Remark", "");
            int formPKID= getInt("formPKID", -1);
            int isApproval = getInt("isApproval", -1);
            
            if (tPlanService.Approval(processId,acceptUserId,aboveActId,remark,formPKID,isApproval)) {
                result = SUCCESS_STRING;
            } else {
                result = FAIL_STRING;
            }
            return result;
		}		 
		@SuppressWarnings("unused")
        private HashMap<String, String> convertToMap(JSONParam[] params){
			HashMap<String, String> hm = new HashMap<String,String>();
			for (JSONParam jsonParam : params) {
				hm.put(jsonParam.getName(), jsonParam.getValue());
			}		
			return hm;
		}
}
