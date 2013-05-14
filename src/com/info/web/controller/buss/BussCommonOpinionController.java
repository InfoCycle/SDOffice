package com.info.web.controller.buss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.SystemCurrentUser;
import com.info.domain.TCommonOpinion;
import com.info.domain.ViewWfActiveAll;
import com.info.service.BussCommonOpinionService;
import com.info.web.EasyDataGrid;
import com.info.web.JDataTablesReturnData;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussCommonOpinionController extends BaseController{

	@Autowired
	BussCommonOpinionService service;
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
	@RequestMapping(value = "/Buss/CommonOpinionService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();		
		switch (action) {
		case 1:		
			Result =Save();
			break;		
		case 2:	
			Result =Delete();
			break;
		case 3:
			Result = UpdateOpinion();
			break;
		case 6:
			Result = getWfActive();
		}
		// 输出响应json串
		writeJsonString(Result);
	}
	@RequestMapping(value = "/Buss/CommonOpinionService/search/{userid}")
	public  void GetTeamPersonList(@PathVariable("userid") Integer userid,HttpServletResponse response){
		CurrentResponse = response;	     
	    String sEcho = getString("sEcho", "");  
	    Integer UserId = SystemCurrentUser.getCurrentUser().getUserID();  
	    Integer start = getInt("iDisplayStart", 0);  
	    Integer length = getInt("iDisplayLength", 10);  	      
	    JDataTablesReturnData returnData=new JDataTablesReturnData();
	    List<TCommonOpinion> objList = service.GetComOpinionsListForUserID(UserId, start, length);
	    returnData.setAaData(objList);
	    returnData.setiTotalDisplayRecords(objList.size());
	    returnData.setiTotalRecords(objList.size());
	    returnData.setsEcho(sEcho);	    	    
	    writeJsonString(getJsonFromObj(returnData));	    
	}
	
	@RequestMapping(value = "/Buss/CommonOpinionService/searchEasy/{userid}")
	public void GetTeamPersonListForEasy(@PathVariable("userid") Integer userid,HttpServletResponse response){
		CurrentResponse = response;
		EasyDataGrid ResultJson= new EasyDataGrid();
		String order=getString("", "asc");
		Integer page=getInt("page", 1);
		Integer rows=getInt("rows", 10);
		String sort=getString("sort", "f_id");
		Integer currentUserId=SystemCurrentUser.getCurrentUser().getUserID();
		 List<TCommonOpinion> objList = service.GetComOpinionsListForUserID(currentUserId,order,page,rows,sort);
		    ResultJson.setRows(objList);
		    ResultJson.setTotal(service.getQueryTotalCount(currentUserId));//Long.valueOf(service.getQueryTotalCount(currentUserId)));//Long.valueOf(objList.size()));		      	    
		    writeJsonString(getJsonFromObj(ResultJson));
	}
	
	public String Save() {
		TCommonOpinion obj = new TCommonOpinion();
		obj.setFId(getInt("FId", 0));
		//obj.setFUserId(getCurrentUserID());
		obj.setFUserId(SystemCurrentUser.getCurrentUser().getUserID());//SystemCurrentUser.getCurrentUser().getUserID()==null ?1:SystemCurrentUser.getCurrentUser().getUserID());
		obj.setFContent(getString("FContent", "未填写意见！"));
		Date currentTime=new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString=formatter.format(currentTime);
		obj.setFAddDate(dateString);		
		List<TCommonOpinion> objResult = service.Save(obj);
		if (obj != null && objResult.size() > 0) {
			message.setTotalProperty(1);
			message.setSuccess(true);
			message.setRoot(objResult);
		} else {
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);		
	}
	
	public String Delete() {
		Integer id=getInt("FId", 0);
		if(service.Delete(id)){
			result=SUCCESS_STRING;
		}else {
			result=FAIL_STRING;
		}
		return result;
	}
	
	public String UpdateOpinion(){
		Integer formId=getInt("FormID", 0);
		String tableName=getString("Table", "");
		String tableField=getString("Field", "");
		String opinionString=getString("OPinion", "");
			if(service.UpdateOpinion(formId,tableName,tableField,opinionString)){
				result=SUCCESS_STRING;
			}else {
				result=FAIL_STRING;
			}
		return result;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getWfActive(){
		Integer processId =getInt("processId", -1);
		Integer toprecord =getInt("toprecord", 3);
		EasyDataGrid ResultJson= new EasyDataGrid();
		try {
			/*List<ViewWfActive> active = service.getWfActive(processId,toprecord);
			if(active.size()>0){
				 ResultJson.setRows(active);
				 ResultJson.setTotal(Long.valueOf(active.size()));
			}else{
				 
			}*/
			List<ViewWfActiveAll> objectList=service.getWfActiveAlls(processId,toprecord);
			//组装json
			if(objectList.size()>0){
				ResultJson.setRows(objectList);
				ResultJson.setTotal(Long.valueOf(objectList.size()));
			}else{
				ResultJson.setRows(objectList);
				ResultJson.setTotal(Long.valueOf(0));
			}
		} catch (Exception e) {
			 
		}
		return getJsonFromObj(ResultJson);
	}
}
