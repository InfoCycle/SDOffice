package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.Result;
import com.info.domain.TProjectAppraisalScoure;
import com.info.service.BussProjAppraisalScoureService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussProjAppraisalScoureController extends BaseController {
	@Autowired
	BussProjAppraisalScoureService pasService;

	ResultMessage message;

	private static final String SUCCESS_STRING = "{\"success\":true}";
	private static final String FAIL_STRING = "{\"success\":false}";

	public String result = "";

	/**
	 * action 请求类型，目的为了减少MVC映射
	 * 
	 * @author jibinbin 1：获得任务信息For ID ;/ProjAppraisalScoureService/1
	 * @param action
	 * @param response
	 */
	@RequestMapping(value = "/Buss/ProjAppraisalScoureService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response) throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		Integer id = 0;
		try{
			switch (action) {
    			case 1:
    				id = getInt("ID", 0);
    				Result = getProjAppraisalScoureForId(id);
    				break;
    			case 2:
    				Result = Save();
    				break;
    			case 3:
    				Result = Insert();
    				break;
    			case 4:
    				id = getInt("ID", 0);
    				Result = Cancle(id);
    				break;
    			case 5:			
    				Result = "";	//预留
    				break;
    			case 6:			
    				Result = "";	//预留
    				break;
    			case 7:
    				id = getInt("TaskID", 0);
    				Result = getTaskInfoForTaskId(id);
    				break;
    			case 8:		//部门
    				Result =Post1007();
    				break;
    			case 9:		//生产部
    				Result = Post1002();
    				break;
    			case 10: 	//总工办
    				Result = Post1003();
    				break;
    			case 11: //签收
                    Result = Acceptance();
                    break;
			}		
			writeJsonString(Result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// 通过系统编号FPasId获得打分信息。
	public String getProjAppraisalScoureForId(Integer id) {
		Integer activeId=getInt("activeId", -1);
		List<TProjectAppraisalScoure> objlist = pasService.GetProjAppraisalScoureForId(id,activeId);
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

	public String getTaskInfoForTaskId(Integer id){
		try {		
			List<String> objlistList=pasService.getTaskPMAndPerson(id);
			if (objlistList != null && objlistList.size() > 0) {
				message.setTotalProperty(objlistList.size());
				message.setSuccess(true);
				message.setRoot(objlistList);
			} else {
				message.setSuccess(false);
				message.setRoot(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonFromObj(message);
		
	}
	// 保存
	public String Save() {
		Result resultObj = new Result();
		int processId = getInt("processId", -1);
		if(processId==-1)
			return result=FAIL_STRING;
		//TProjectAppraisalScoure objSave=new TProjectAppraisalScoure();
		String TaskID=getString("TaskID", "");
		String FPasId=getString("FPasId", "");
		String Title=getString("Title", "");
		String FNumber=getString("FNumber", "");
		/**
		 * WhoSave: 1部门,2生产副总,3总工办
		 */
		Integer WhoSave=getInt("WhoSave", -1);
		//获取页面提交参数
		if(TaskID.equals("") || FPasId.equals(""))
			result=FAIL_STRING;
		else {			
			String FID0="";
			switch (WhoSave) {
			case 1:
				resultObj = pasService.Save(TaskID,FPasId,processId,Title,FNumber);
				FID0=getString("FID0", "");
				String FDepartmentScore=getString("FDepartmentScore", "");
				String FDeptScoreThat=getString("FDeptScoreThat", "");
				pasService.Post1007(-1,"",-1,"",-1,FID0,FDepartmentScore,FDeptScoreThat,false);
				break;
			case 2:
				FID0=getString("FID0", "");
				String FCompanyScore=getString("FCompanyScore", "");
				String FCTS=getString("FCTS", "");				
				resultObj.setSuccess(pasService.Post1002(-1,"",-1,"",-1,FID0,FCompanyScore,FCTS,false));
				break;
			case 3:
				FID0=getString("FID0", "");
				FCompanyScore=getString("FCompanyScore", "");
				FCTS=getString("FCTS", "");				
				resultObj.setSuccess(pasService.Post1003(-1,"",-1,"",-1,FID0,FCompanyScore,FCTS,false));
				break;
			default:
				result = FAIL_STRING;
				break;
			}
			if (resultObj.getSuccess())
				result = SUCCESS_STRING + ",\"Id\":" + resultObj.getId() + "}";
			else {
				result = FAIL_STRING;
			}
			result = result.replace("},", ",");
		}
		return result;
	}

	// 新建
	public String Insert() {
		//Result resultObj = new Result();
		Integer processId= getInt("processId0",-1);
		Integer activeId = getInt("activeId", -1);
		//resultObj = pasService.Insert(processId);
		List<TProjectAppraisalScoure> objlist = pasService.Insert(processId,activeId);
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

	// 提交
	/*public String Post() {
		int processId= getInt("ProcessId", -1);
		int acceptUserId= getInt("AcceptUserId", -1);
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);
		int PFDepartmentId=getInt("PFDepartmentId",-1);
		String PFDepartmentName= getString("PFDepartmentName", "");
		if (taskService.Post(processId,acceptUserId,aboveActId,remark,formPKID,PFDepartmentId,PFDepartmentName)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}*/
	//CompanySC
	//CompanyZG
	//部门提交
	public String  Post1007(){
		int processId= getInt("ProcessId", -1);		
		String acceptUserId=getString("AcceptUserId", "");
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);
		
		String FID0=getString("FID0", "");
		String FDepartmentScore=getString("FDepartmentScore", "");
		String FDeptScoreThat=getString("FDeptScoreThat", "");
		//String TaskName=getString("TaskName", "");
		if(pasService.Post1007(processId,acceptUserId,aboveActId,remark,formPKID,FID0,FDepartmentScore,FDeptScoreThat,true))
			result = SUCCESS_STRING;
		else {
			result = FAIL_STRING;
		}
		return result;
	}
	//生产副总
	public String  Post1002(){
		int processId= getInt("ProcessId", -1);		
		String acceptUserId=getString("AcceptUserId", "");
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);
		
		String FID0=getString("FID0", "");
		String FCompanyScore=getString("FCompanyScore", "");
		String FCTS=getString("FCTS", "");
		if(pasService.Post1002(processId,acceptUserId,aboveActId,remark,formPKID,FID0,FCompanyScore,FCTS,true))
			result = SUCCESS_STRING;
		else {
			result = FAIL_STRING;
		}
		return result;
	}
	//技术负责人
	public String  Post1003(){
		int processId= getInt("ProcessId", -1);		
		String acceptUserId=getString("AcceptUserId", "");
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);
		
		String FID0=getString("FID0", "");
		String FCompanyScore=getString("FCompanyScore", "");
		String FCTS=getString("FCTS", "");
		if(pasService.Post1003(processId,acceptUserId,aboveActId,remark,formPKID,FID0,FCompanyScore,FCTS,true))
			result = SUCCESS_STRING;
		else {
			result = FAIL_STRING;
		}
		return result;
	}	
	
	// 撤销
	public String Cancle(Integer id) {
		if (pasService.Cancle(id)) {
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
				String remark= getString("Remark", "");
				if (pasService.Acceptance(aboveActId,remark)) {
					result = SUCCESS_STRING;
				} else {
					result = FAIL_STRING;
				}
				return result;
	}
}
