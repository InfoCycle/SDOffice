package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.Result;
import com.info.domain.TCheckReview;
import com.info.domain.TMajorMattersReport;
import com.info.domain.TRelatedDetpOpinion;
import com.info.domain.ViewMmreport;
import com.info.service.BussMMReportService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussMMReportController extends BaseController {

	@Autowired
	BussMMReportService reportService;

	ResultMessage message;

	private static final String SUCCESS_STRING = "{\"success\":true}";
	private static final String FAIL_STRING = "{\"success\":false}";

	public String result = "";

	/**
	 * action 请求类型，目的为了减少MVC映射
	 * 
	 * @author jibinbin 1：获得项目重大事项报告信息For ID ;/Buss/MMReportService/1
	 * @param action
	 * @param response
	 */
	@RequestMapping(value = "/Buss/MMReportService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response, TMajorMattersReport report)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		Integer id = 0;
		switch (action) {
    		case 1:
    			id = getInt("ID", 0);
    			Result = getReportForId(id);
    			break;
    		case 2:
    			Result = Save(report);
    			break;
    		case 3:
    			Result = Insert();
    			break;
    		case 4:
    			id = getInt("ID", 0);
    			Result = Cancle(id);
    			break;
    		case 5:
    			id = getInt("ID", 0);
    			Result = getRelatedDetpOForFKId(id);
    			break;
    		case 6:	//提交
    			Result = Post();    		    
    			break;
    		case 7:	//办理完成,相关部门
    			Result = Complete();
    			break;
    		case 8: //签收
                Result = Acceptance();
                break;
    		case 9:          //催办      
                Result = UrgeSb();
                break;
    		case 10: //提交到部门经理
                Result = PostDeptMgr();
                break;
    		case 11: //总经理办理完成
                Result = PostGM();
                break;
    		case 12: //无相关部门意见直接提交到总经理
                Result = PostToGM();
                break;
              ///2013-05-09历史任务
            case 16:   //读取
                Result = GetHistory();
                break;
            case 17:   //保存
                Result = SaveHistory();
                break;
            case 18:   //取消
                Result = CancleHistory();
                break;
		}		
		// 输出响应json串
		writeJsonString(Result);
	}

	// 通过系统编号ID获得项目重大事项报告信息。
	public String getReportForId(Integer id) {
		//List<TMajorMattersReport> objTask = reportService.GetReportListForID(id);
		Integer activeId=getInt("activeId", -1);
		List<ViewMmreport> objReportList = reportService.GetViewReportListForID(id,activeId);
		if (objReportList != null && objReportList.size() > 0) {
			message.setTotalProperty(1);
			message.setSuccess(true);
			message.setRoot(objReportList);
		} else {
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);

	}

	//查询相关部门意见
	public String getRelatedDetpOForFKId(Integer fkid){
		List<TRelatedDetpOpinion> objList=reportService.GetRelateDOListForFKID(fkid);
		if(objList!=null && objList.size()>0){
			message.setTotalProperty(objList.size());
			message.setSuccess(true);
			message.setRoot(objList);
		}else{
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}
	// 保存
	public String Save(TMajorMattersReport objReport) {
		Result resultObj = new Result();
		int processId = getInt("processId", -1);
		if(processId==-1)
			return FAIL_STRING;
		resultObj = reportService.Save(objReport,processId);
		if (resultObj.getSuccess())
			result = SUCCESS_STRING + ",\"Id\":" + resultObj.getId() + "}";
		else {
			result = FAIL_STRING;
		}
		result = result.replace("},", ",");
		return result;
	}

	// 新建
	public String Insert() {
		Integer processId= getInt("processId0",-1);	
		Integer activeId= getInt("activeId", -1);
		Integer resultInteger=reportService.InsertExt(processId,activeId);
		if(resultInteger>0)
			result=SUCCESS_STRING + ",\"Id\":" + resultInteger.toString() + "}";
		else {
			result=FAIL_STRING;
		}
		result = result.replace("},", ",");
		return result;
	}

	/**
	 * 
	 * @function:提交
	 * @data: 2013-1-7下午2:52:48
	 * @author jibinbin
	 * @return
	 *
	 */
	public String Post() {
		//PostType:1提交到相关部门,2:相关部门提交
		Integer postType=getInt("PostType", -1);
		int processId= getInt("ProcessId", -1);		
		String acceptUserId=getString("AcceptUserId", "");
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);		
		if(postType==-1 || processId==-1 || formPKID==-1)
			return result=FAIL_STRING;
		if(reportService.Post(processId, acceptUserId, aboveActId, remark, formPKID,postType))
			result=SUCCESS_STRING;
		else
			result=FAIL_STRING;
		return result;
	}
	/**
	 * 
	 * @Description	: 直接提交到总经理
	 * @Author		: jibb
	 * @Date		: 2013-03-26 10-17
	 * @return
	 */
	public String PostToGM(){
	    int processId= getInt("ProcessId", -1);        
        int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        int formPKID= getInt("formPKID", -1);       
        if(processId==-1 || formPKID==-1)
            return result=FAIL_STRING;
        if(reportService.PostToGM(processId, aboveActId, remark, formPKID))
            result=SUCCESS_STRING;
        else
            result=FAIL_STRING;
        return result; 
	}
	/**
	 * 
	 * @Description	: 提交到部门经理
	 * @Author		: jibb
	 * @Date		: 2013-03-25 10-52
	 * @return
	 */
	public String PostDeptMgr(){
	    int processId= getInt("ProcessId", -1);     
        Integer acceptUserId=getInt("AcceptUserId", -1);
        int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        int formPKID= getInt("formPKID", -1);       
        if(processId==-1 || formPKID==-1 || acceptUserId==-1)
            return result=FAIL_STRING;
        if(reportService.PostDeptMgr(processId, acceptUserId, aboveActId, remark, formPKID))
            result=SUCCESS_STRING;
        else
            result=FAIL_STRING;
        return result; 
	}
	/**
	 * 
	 * @Description	: 总经理办理提交
	 * @Author		: jibb
	 * @Date		: 2013-03-25 16-43
	 * @return
	 */
	public String PostGM(){
	    int processId= getInt("ProcessId", -1);            
        int aboveActId= getInt("AboveActId", -1);           
        int formPKID= getInt("formPKID", -1);
        if (reportService.PostGM(processId,aboveActId,formPKID)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
	}
	/**
	 * ban li wan cheng
	 * @function:
	 * @data: 2013-3-1下午5:49:00
	 * @author jibinbin
	 * @return
	 *
	 */
	public String Complete(){
		int processId= getInt("ProcessId", -1);			
		int aboveActId= getInt("AboveActId", -1);			
		int formPKID= getInt("formPKID", -1);
		if (reportService.Complete(processId,aboveActId,formPKID)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}
	
	// 撤销
	public String Cancle(Integer id) {
		if (reportService.Cancle(id)) {
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
				if (reportService.Acceptance(aboveActId,remark)) {
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
        if (reportService.UrgeSb(aboveActId,remark)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
    }
    
    
  //历史任务
    public String GetHistory(){
        Integer id=getInt("ID", -1);
        /*List<TCheckReview> objlist = reportService.getMMReportForFID(id,0);
        if (objlist != null && objlist.size() > 0) {
            message.setTotalProperty(objlist.size());
            message.setSuccess(true);
            message.setRoot(objlist);
        } else {
            message.setSuccess(false);
            message.setRoot(null);
        }*/
        return getJsonFromObj(message);
    }
    
    public String SaveHistory(){       
        String jsonDataString=getString("JSONDATA", "");
        String TaskName= getString("TaskName", "");
        TCheckReview checkReview =(TCheckReview)getObjectFromJson(jsonDataString,TCheckReview.class);
        //TCheckReview resultCheckReview=reportService.SaveHistory(checkReview,TaskName);
        /*Result resultObj = new Result();
        if(null!=resultCheckReview)
        {
            if (resultCheckReview.getFId()>0){
                resultObj.setSuccess(true);
                resultObj.setId(resultCheckReview.getFId());
            }else {
                resultObj.setSuccess(false);
                resultObj.setId(0);
            }
        }else {
            resultObj.setSuccess(false);
            resultObj.setId(0);
        }
        return getJsonFromObj(resultObj);  */  
        return "";
    }
    public String CancleHistory(){       
        Result resultObj = new Result();
        //int processId= getInt("ProcessId", -1);          
        int formPKID= getInt("FId", -1);
        /*if(reportService.CancleHistory(formPKID))
        {
            resultObj.setSuccess(true);
            resultObj.setId(formPKID);
        }else {
            resultObj.setSuccess(false);
            resultObj.setId(formPKID);
        }
        return getJsonFromObj(resultObj);*/
        return "";
    }
}
