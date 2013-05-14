
package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.Result;
import com.info.domain.TTask;
import com.info.domain.TTaskPm;
import com.info.domain.TWfUpdateRecords;
import com.info.service.BussTaskService;
import com.info.service.BussUpdateRecordsService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;


/**   
 * @ClassName	: BussTaskController   
 * @Description	: TODO(这里用一句话描述这个类的作用)   
 * @Author		: jibb
 * @Date		: 2013-03-20 18-06   
 * 新增：T_Task 表中增加F_Current_Step记录任务的状态。
 *      说明：10:“项目任务书”起草：填写任务书信息，提交时选择承接部门及部门经理；
            20:“项目任务书”任命项目经理：部门经理签收并任命项目经理；可以打回到上一步；
            30:“项目任务书”项目经理签收：项目经理签收或打回上一步；
            40:“项目任务书”起草计划：签收确认后，起草计划成功就不能打回；同时项目任务书流程就结束。      
 */
@Controller
public class BussTaskController extends BaseController {
	@Autowired
	BussTaskService taskService;
	
	@Autowired
	BussUpdateRecordsService burService;        

	ResultMessage message;

	private static final String SUCCESS_STRING = "{\"success\":true}";
	private static final String FAIL_STRING = "{\"success\":false}";

	public String result = "";

	/**
	 * action 请求类型，目的为了减少MVC映射
	 * 
	 * @author jibinbin 1：获得任务信息For ID ;/TaskService/1
	 * @param action
	 * @param response
	 */
	@RequestMapping(value = "/Buss/TaskService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response, TTask task) throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		Integer id = 0;
		try{
		switch (action) {
			case 1:			//通过ID获得任务书信息
				id = getInt("ID", 0);
				Result = getTaskForId(id);
				break;
			case 2:			//保存任务书信息
				Result = Save(task);
				break;
			case 3:			//新建任务书信息
				Result = Insert();
				break;
			case 4:			//撤销任务书信息
				id = getInt("ID", 0);
				Result = Cancle(id);
				break;
			case 5:			//提交任务书信息	
				Result = Post();
				break;
			case 6:		    //打回	
				Result = Return();
				break;
			case 7:		    //任命项目经理	
				Result = AppointPM();
				break;
			case 8:
				id = getInt("ID", 0);
				Result = getTaskPMForId(id);
				break;
			case 9:			//签收		
				Result = Acceptance();
				break;
			case 10:        //催办      
                Result = UrgeSb();
                break;
			case 11:        //起草计划 
                Result = AddTaskPlan();
                break;
			case 12:        //判断任务书名称是否存在
                Result = IsHaveTask();
                break;
			case 13:        //判断修改
			    Result = IsModify();
			    break;
			case 14:        //存储修改
                Result = SaveModify();
                break;
		}
		// 输出响应json串
		writeJsonString(Result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// 通过系统编号ID获得任务信息。
	public String getTaskForId(Integer id) {
		Integer activeId=getInt("activeId", -1);
		List<TTask> objTask = taskService.GetTaskListForID(id,activeId);
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

	public String getTaskPMForId(Integer id){
		List<TTaskPm> objTaskPm = taskService.GetTaskPmListForID(id);
		if (objTaskPm != null && objTaskPm.size() > 0) {
			message.setTotalProperty(1);
			message.setSuccess(true);
			message.setRoot(objTaskPm);
		} else {
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}
	// 保存任务
	public String Save(TTask objTask) {
		Result resultObj = new Result();
		int processId = getInt("processId", -1);
		if(processId==-1)
			return FAIL_STRING;
		resultObj = taskService.Save(objTask,processId);
		if (resultObj.getSuccess())
			result = SUCCESS_STRING + ",\"Id\":" + resultObj.getId() + "}";
		else {
			result = FAIL_STRING;
		}
		result = result.replace("},", ",");
		return result;
	}

	/**
	 * 
	 * @Description	: 起草一个新任务
	 * @Author		: jibb
	 * @Date		: 2013-03-20 18-15
	 * @return       返回是否成功和任务ID
	 */
	public String Insert() {
		Integer processId= getInt("processId0",-1);
		Integer activeId= getInt("activeId",-1);
		Result resultObj = new Result();
		if(processId==-1)
		{
		   resultObj.setId(0);
		   resultObj.setSuccess(false);
		   resultObj.setMsg("活动参数获取失败！");
		}else
		{			
			resultObj=  taskService.Insert(processId,activeId);			 
		}
		return getJsonFromObj(resultObj);
	}

	// 提交任务
	public String Post() {
		int processId= getInt("ProcessId", -1);
		//int acceptUserId= getInt("AcceptUserId", -1);
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);
		int PFDepartmentId=getInt("PFDepartmentId",-1);
		String PFDepartmentName= getString("PFDepartmentName", "");
		int acceptUserId=getInt("AcceptUserId",-1);
        String PFDeptMgrName= getString("PFDeptMgrName", "");
		if (taskService.Post(processId,acceptUserId,aboveActId,remark,formPKID,PFDepartmentId,PFDepartmentName,PFDeptMgrName)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}

	/**
	 * 
	 * @Description	: 打回
	 * @Author		: jibb
	 * @Date		: 2013-3-19 上午12:06:28
	 * @return
	 */
	public String Return(){
		//int processId= getInt("ProcessId", -1);
		//int acceptUserId= getInt("AcceptUserId", -1);
		int aboveActId= getInt("AboveActId", -1);
		int formPKID= getInt("FormFId", -1);
		String remark= getString("Remark", "");
		if (taskService.Return(aboveActId,remark,formPKID)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}
	/**
	 * 
	 * @Description	: 催办下一步
	 * @Author		: jibb
	 * @Date		: 2013-3-19 上午12:03:39
	 * @return
	 */
	public String UrgeSb(){
	    int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        if (taskService.UrgeSb(aboveActId,remark)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
	    return result;
	}
	/**
	 * 签收
	 * @return
	 */
	public  String Acceptance() {
		//int processId= getInt("ProcessId", -1);
				//int acceptUserId= getInt("AcceptUserId", -1);
				int aboveActId= getInt("AboveActId", -1);
				int FormPKID= getInt("FormFId", -1);
				int isUpdateOther= getInt("isUpdateOther", -1);
				String remark= getString("Remark", "");				
				if (taskService.Acceptance(aboveActId,remark,FormPKID,isUpdateOther)) {
					result = SUCCESS_STRING;
				} else {
					result = FAIL_STRING;
				}
				return result;
	}
	
	//指派项目经理
	public String AppointPM(){
		Integer ProcessId=getInt("ProcessId", -1);
		//Integer AcceptUserId=getInt("AcceptUserId", -1);
		Integer AboveActId=getInt("AboveActId", -1);
		String Remark=getString("Remark", "");
		Integer formPKID=getInt("formPKID", -1);
		Integer FProjMgrId=getInt("FProjMgrId", -1);
		String FProjMgrName=getString("FProjMgrName", "");
		String FProjMgrViceIdList=getString("FProjMgrViceIdList", "-1");
		String FProjMgrViceName=getString("FProjMgrViceName", "");		
		if (taskService.AppointPM(ProcessId,AboveActId,Remark,formPKID,FProjMgrId,
				FProjMgrName,FProjMgrViceIdList,FProjMgrViceName)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}
	// 撤销任务
	public String Cancle(Integer id) {
	    Integer processId=getInt("ProcessId", -1);
	    Integer activeId=getInt("ActiveId", -1);
		if (taskService.Cancle(id,processId,activeId)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}
	/**
	 * 起草任务计划
	 * @Description	: TODO(这里用一句话描述这个方法的作用)
	 * @Author		: jibb
	 * @Date		: 2013-3-20 上午11:23:20
	 * @return
	 */
	public String AddTaskPlan(){
	    String TaskNumber=getString("TaskNumber", "");
	    Integer TaskID=getInt("TaskID", -1);
	    String TaskName=getString("TaskName", "");
	    Integer ProcessId=getInt("ProcessId", -1);       
        Integer AboveActId=getInt("AboveActId", -1);
        String JHNmubers=getString("JHNmubers", "");
        if(ProcessId==-1 || TaskID==-1)
            result = FAIL_STRING;
        else {
            if (taskService.AddTaskPlan(TaskNumber,TaskID,TaskName,ProcessId,AboveActId,JHNmubers)) {
                result = SUCCESS_STRING;
            } else {
                result = FAIL_STRING;
            } 
        }
	   
	    return result;
	}
	/**
	 *  
	 * @Description	:  判断任务名称是否存在
	 * @Author		: jibb
	 * @Date		: 2013-03-28 14-18
	 * @return
	 */
	public String IsHaveTask(){
	    Integer fIdInteger=getInt("FId", -1);
	    String TaskName=getString("TaskName", "");
	    Result resultObj = new Result();
	    resultObj.setId(0);	    
	    resultObj.setSuccess(taskService.IsHaveTaskForTaskName(TaskName,fIdInteger));
	    return getJsonFromObj(resultObj);
	}
	
	public String IsModify(){	    
	    Result resultObj = new Result();
	    String jsonDataString=getString("JSONDATA", "");
	    TTask task =(TTask)getObjectFromJson(jsonDataString,TTask.class);
        resultObj.setId(0);
        resultObj.setSuccess(true);
        String msg=taskService.objectCompare(task);
        resultObj.setMsg(msg);
        return getJsonFromObj(resultObj);
	}
	public String SaveModify(){
	    Result resultObj = new Result();
	    Integer ProcessId=getInt("ProcessId", -1);
        String ModifyMessage=getString("ModifyMessage", ""); 
        TWfUpdateRecords objRecords=burService.getUpdateRecordsForProcessId(ProcessId);
        if(null==objRecords){
            objRecords=new TWfUpdateRecords();
            objRecords.setFId(0);
            objRecords.setFModifyInfo("");
        }
        objRecords.setFkProcessId(ProcessId);        
        objRecords.setFModifyInfo(objRecords.getFModifyInfo() ==null ?("\n"+ModifyMessage):(objRecords.getFModifyInfo()+"\n"+ModifyMessage));
        resultObj.setSuccess(burService.SaveUpdateRecord(objRecords));
        resultObj.setMsg("成功。");
        return getJsonFromObj(resultObj);
	}
}
