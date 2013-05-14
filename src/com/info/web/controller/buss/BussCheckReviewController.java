package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.Result;
import com.info.domain.TCheckReview;
import com.info.domain.TWfUpdateRecords;
import com.info.service.BussCheckReviewService;
import com.info.service.BussUpdateRecordsService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussCheckReviewController extends BaseController {
	@Autowired
	BussCheckReviewService serviceBCRS;
	
	@Autowired
    BussUpdateRecordsService burService;

	ResultMessage message;

	private static final String SUCCESS_STRING = "{\"success\":true}";
	private static final String FAIL_STRING = "{\"success\":false}";

	public String result = "";

	/**
	 * 
	 * @function:
	 * @data: 2013-1-15下午3:20:20
	 * @author jibinbin
	 * @param action
	 * @param response
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/Buss/CheckReviewService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response) throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		Integer id = 0;
		try {
			switch (action) {
				case 1:
					id = getInt("ID", 0);
					Result = getCheckReviewForId(id);
					break;
				case 2:
					Result = Save();
					break;
				case 3:
					Result = Insert();
					break;
				case 4:
					id = getInt("ID", 0);
					// Result = Cancle(id);
					break;
				case 5:
					id = getInt("TaskID", 0);
					Result = getTaskInfoForTaskId(id);
					break;
				case 6:
					Result = PostTo(); //固定人的方式提交
					//Post(); //选择人的方式
					break;
				case 7:
					Integer activeIdInteger=getInt("ACTIVEID", -1);
					List<String> tempList=serviceBCRS.GetUsrIdForStation(activeIdInteger);
					if(0<tempList.size()){
						message.setSuccess(true);
						message.setRoot(tempList);
					}else
					{
						message.setSuccess(false);
					}
					Result = getJsonFromObj(message);
					break;
				case 8:
					Result = WTPost();
					break;
				case 9:			
					Result = BanJie();
					break;
				case 10: //签收
	                Result = Acceptance();
	                break;
				case 11: //催办
				    Result = UrgeSb();
                    break;
				case 12: //会签
                    Result = Countersign();
                    break;
				case 13: //打回上一步
				    Result = Return();
                    break;
                    
				case 14:        //判断修改
	                Result = IsModify();
	                break;
	            case 15:        //存储修改
	                Result = SaveModify();
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
			writeJsonString(Result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 查询信息
	 * 
	 * @function:
	 * @data: 2013-1-15下午3:21:31
	 * @author jibinbin
	 * @param id
	 * @return
	 * 
	 */
	public String getCheckReviewForId(Integer id) {
		Integer activeId = getInt("activeId", -1);
		List<TCheckReview> objlist = serviceBCRS.getCheckReviewsForFID(id,
				activeId);
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

	/**
	 * 获取任务信息
	 * 
	 * @function:
	 * @data: 2013-1-15下午3:21:42
	 * @author jibinbin
	 * @param id
	 * @return
	 * 
	 */
	public String getTaskInfoForTaskId(Integer id) {
		try {
			List<String> objlistList = serviceBCRS.getTaskInfo(id);
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

	/**
	 * 保存复核申报信息S
	 * 
	 * @function:
	 * @data: 2013-1-15下午3:21:20
	 * @author jibinbin
	 * @return
	 * 
	 */
	public String Save() {
		int processId = getInt("processId", -1);
		Integer TaskId = getInt("TaskId", -1);
		if (processId == -1 || TaskId == -1)
			return result = FAIL_STRING;
		TCheckReview objSaveCheckReview = new TCheckReview();
		String TaskName = getString("TaskName", "");
		String FNumbers = getString("FFHNumbers", "");
		Integer FId = getInt("FId", -1);
		String FConstructionUnit = getString("FConstructionUnit", "");
		String FResultsType = getString("FResultsType", "");
		String FSubmitMaterials = getString("FSubmitMaterials", "");
		String FResultsTypeName = getString("FResultsTypeName", "");
        String FSubmitMaterialsName = getString("FSubmitMaterialsName", "");
		String FProjectCost = getString("FProjectCost", "");
		String FUnitCost = getString("FUnitCost", "");
		objSaveCheckReview = serviceBCRS.getCheckReviewByID(FId);// getCheckReviewsForFID(FId,// -1).get(0);

		if (objSaveCheckReview.getFId() > 0) {
			objSaveCheckReview.setFkTaskId(TaskId);
			objSaveCheckReview.setFNumbers(FNumbers);
			objSaveCheckReview.setFConstructionUnit(FConstructionUnit);
			objSaveCheckReview.setFResultsType(FResultsType);
			objSaveCheckReview.setFSubmitMaterials(FSubmitMaterials);
			objSaveCheckReview.setFProjectCost(FProjectCost);//Double.valueOf(FProjectCost));
			objSaveCheckReview.setFUnitCost(FUnitCost);
			objSaveCheckReview.setFResultTypeName(FResultsTypeName);
			objSaveCheckReview.setFSubmitMaterialsName(FSubmitMaterialsName);
			if (serviceBCRS.Save(objSaveCheckReview, processId, TaskName))
				result = SUCCESS_STRING;
			else {
				result = FAIL_STRING;
			}
		}
		return result;
	}

	/**
	 * 新建复核申报信息
	 * 
	 * @function:
	 * @data: 2013-1-15下午3:23:00
	 * @author jibinbin
	 * @return
	 * 
	 */
	public String Insert() {
		Integer processId = getInt("processId0", -1);
		Integer activeId = getInt("activeId", -1);
		Result resultObj = new Result();
		if (processId == -1){
            resultObj.setSuccess(false);
            return getJsonFromObj(resultObj);
        }
		resultObj = serviceBCRS.Insert(processId, "起草项目检查复核申报",activeId);		
		return getJsonFromObj(resultObj);
	}

	/**
	 * 提交
	 * @function:
	 * @data: 2013-1-17下午4:50:07
	 * @author jibinbin
	 * @return
	 *
	 */
	public String Post() {		
		Result resultObj = new Result();
		resultObj.setSuccess(false);
		resultObj.setMsg("提交失败！请检查网络重试或联系管理员。");
		
		int processId = getInt("ProcessId", -1);
		if(!serviceBCRS.getLoginIsCurrentUser(processId))
		{
			resultObj.setSuccess(false);
			resultObj.setMsg("当前办理人不是您！请注意查看。");
			return getJsonFromObj(resultObj);
		}
		if (processId == -1){
			resultObj.setSuccess(false);
			return getJsonFromObj(resultObj);
		}
		String acceptUserId=getString("AcceptUserId", "");
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);			
		/**
		 * WhoPost: 1项目经理,2部门经理,3公司总工办
		 */
		Integer WhoPost = getInt("WhoPost", -1);
		TCheckReview postCheckReview = new TCheckReview();
		///通过Id 获得复核申报信息
		postCheckReview = serviceBCRS.getCheckReviewByID(formPKID);
		if (postCheckReview.getFId()>0) {
			switch (WhoPost) {
			case 1:
				String FPmroReviewMan = getString("FPmroReviewMan", "");
				String FPmroReviewTime = getString("FPmroReviewTime", "");
				String FPmroProblems = getString("FPmroProblems", "");
				String FPmroRectification = getString("FPmroRectification", "");
				String FPmroDiscuss = getString("FPmroDiscuss", "");
				postCheckReview.setFPmroReviewMan(FPmroReviewMan);
				postCheckReview.setFPmroReviewTime(FPmroReviewTime);
				postCheckReview.setFPmroProblems(FPmroProblems);
				postCheckReview.setFPmroRectification(FPmroRectification);
				postCheckReview.setFPmroDiscuss(FPmroDiscuss);
				postCheckReview.setFCurrentStep("20");
				break;
			case 2:
				String FDmroReviewMan = getString("FDmroReviewMan", "");
				String FDmroReviewTime = getString("FDmroReviewTime", "");
				String FDmroProblems = getString("FDmroProblems", "");
				String FDmroRectification = getString("FDmroRectification", "");
				String FDmroDiscuss = getString("FDmroDiscuss", "");
				postCheckReview.setFDmroReviewMan(FDmroReviewMan);
				postCheckReview.setFDmroReviewTime(FDmroReviewTime);
				postCheckReview.setFDmroProblems(FDmroProblems);
				postCheckReview.setFDmroRectification(FDmroRectification);
				postCheckReview.setFDmroDiscuss(FDmroDiscuss);
				postCheckReview.setFCurrentStep("30");
				break;
			case 3:
				String FCglroFinalCost = getString("FCglroFinalCost", "");
				String FCglroProblems = getString("FCglroProblems", "");
				String FCglroRectification = getString("FCglroRectification","");
				String FCglroDiscuss = getString("FCglroDiscuss", "");
				String FReviewMan = getString("FReviewMan", "");
				String FReviewManTime = getString("FReviewManTime", "");
				postCheckReview.setFCglroFinalCost(FCglroFinalCost);
				postCheckReview.setFCglroProblems(FCglroProblems);
				postCheckReview.setFCglroRectification(FCglroRectification);
				postCheckReview.setFCglroDiscuss(FCglroDiscuss);
				postCheckReview.setFReviewMan(FReviewMan);
				postCheckReview.setFReviewManTime(FReviewManTime);
				postCheckReview.setFCurrentStep("40");
				break;
			default:
				break;
			}
			if(serviceBCRS.Post(postCheckReview, processId, acceptUserId, aboveActId, remark,formPKID,WhoPost))
			{
				resultObj.setSuccess(true);
				resultObj.setMsg("已成功提交到所选择人员，请注意办理情况。");
			}
		}else {
			resultObj.setId(0);
			resultObj.setSuccess(false);
			resultObj.setMsg("没有项目检查复核申报记录!");
		}
		return getJsonFromObj(resultObj);
	}
	public String PostTo() {     
        Result resultObj = new Result();
        resultObj.setSuccess(false);
        resultObj.setMsg("提交失败！请检查网络重试或联系管理员。");
        
        int processId = getInt("ProcessId", -1);
        if(!serviceBCRS.getLoginIsCurrentUser(processId))
        {
            resultObj.setSuccess(false);
            resultObj.setMsg("当前办理人不是您！请注意查看。");
            return getJsonFromObj(resultObj);
        }
        String acceptUserId=getString("AcceptUserId", "");
        if (processId == -1 || acceptUserId==""){
            resultObj.setSuccess(false);
            return getJsonFromObj(resultObj);
        }
        
        int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        int formPKID= getInt("formPKID", -1);           
        /**
         * WhoPost: 1项目经理,2部门经理,3公司总工办
         */
        Integer WhoPost = getInt("WhoPost", -1);
        /**
         * PostTo提交到: 1部门,2公司总工办
         */
        Integer PostTo = getInt("PostTo", -1);
        
        TCheckReview postCheckReview = new TCheckReview();
        ///通过Id 获得复核申报信息
        postCheckReview = serviceBCRS.getCheckReviewByID(formPKID);
        if (postCheckReview.getFId()>0) {
            String currentStep=postCheckReview.getFCurrentStep();
            switch (WhoPost) {
                case 1: //项目经理
                    String FPmroReviewMan = getString("FPmroReviewMan", "");
                    String FPmroReviewTime = getString("FPmroReviewTime", "");
                    String FPmroProblems = getString("FPmroProblems", "");
                    String FPmroRectification = getString("FPmroRectification", "");
                    String FPmroDiscuss = getString("FPmroDiscuss", "");
                    postCheckReview.setFPmroReviewMan(FPmroReviewMan);
                    postCheckReview.setFPmroReviewTime(FPmroReviewTime);
                    postCheckReview.setFPmroProblems(FPmroProblems);
                    postCheckReview.setFPmroRectification(FPmroRectification);
                    postCheckReview.setFPmroDiscuss(FPmroDiscuss);
                    if(PostTo==1){                        
                        postCheckReview.setFCurrentStep("20");                        
                    }
                    if(PostTo==2){
                        postCheckReview.setFCurrentStep("30");                       
                    }
                    postCheckReview.setFLastStep(currentStep);
                    postCheckReview.setFRecordStep(postCheckReview.getFRecordStep()+"{\"CurrentStep\":"+postCheckReview.getFCurrentStep()+",\"ActiveId\":"+aboveActId+"},");
                    break;
                case 2: //部门经理
                    String FDmroReviewMan = getString("FDmroReviewMan", "");
                    String FDmroReviewTime = getString("FDmroReviewTime", "");
                    String FDmroProblems = getString("FDmroProblems", "");
                    String FDmroRectification = getString("FDmroRectification", "");
                    String FDmroDiscuss = getString("FDmroDiscuss", "");
                    postCheckReview.setFDmroReviewMan(FDmroReviewMan);
                    postCheckReview.setFDmroReviewTime(FDmroReviewTime);
                    postCheckReview.setFDmroProblems(FDmroProblems);
                    postCheckReview.setFDmroRectification(FDmroRectification);
                    postCheckReview.setFDmroDiscuss(FDmroDiscuss);
                    postCheckReview.setFCurrentStep("30");                    
                    postCheckReview.setFLastStep(currentStep);
                    postCheckReview.setFRecordStep(postCheckReview.getFRecordStep()+"{\"CurrentStep\":"+postCheckReview.getFCurrentStep()+",\"ActiveId\":"+aboveActId+"},");
                    break;
                case 3:  //总工办
                    String FCglroFinalCost = getString("FCglroFinalCost", "");
                    String FCglroProblems = getString("FCglroProblems", "");
                    String FCglroRectification = getString("FCglroRectification","");
                    String FCglroDiscuss = getString("FCglroDiscuss", "");
                    String FReviewMan = getString("FReviewMan", "");
                    String FReviewManTime = getString("FReviewManTime", "");
                    postCheckReview.setFCglroFinalCost(FCglroFinalCost);
                    postCheckReview.setFCglroProblems(FCglroProblems);
                    postCheckReview.setFCglroRectification(FCglroRectification);
                    postCheckReview.setFCglroDiscuss(FCglroDiscuss);
                    postCheckReview.setFReviewMan(FReviewMan);
                    postCheckReview.setFReviewManTime(FReviewManTime);
                    postCheckReview.setFCurrentStep("40");
                    postCheckReview.setFLastStep(currentStep);
                    postCheckReview.setFRecordStep(postCheckReview.getFRecordStep()+"{\"CurrentStep\":"+postCheckReview.getFCurrentStep()+",\"ActiveId\":"+aboveActId+"},");
                    break;
                default:
                    break;
            }
            if(serviceBCRS.Post(postCheckReview, processId, acceptUserId, aboveActId, remark, formPKID,WhoPost))
            {
                resultObj.setSuccess(true);
                resultObj.setMsg("已成功提交到所选择人员，请注意办理情况。");
            }
        }else {
            resultObj.setId(0);
            resultObj.setSuccess(false);
            resultObj.setMsg("没有项目检查复核申报记录!");
        }
        return getJsonFromObj(resultObj);
    }
	public String WTPost(){		
		Result resultObj = new Result();
		resultObj.setSuccess(false);
		resultObj.setMsg("提交失败！请检查网络重试或联系管理员。");
		
		int processId = getInt("ProcessId", -1);
		if(!serviceBCRS.getLoginIsCurrentUser(processId))
		{
			resultObj.setSuccess(false);
			resultObj.setMsg("当前办理人不是您！请注意查看。");
			return getJsonFromObj(resultObj);
		}
		if (processId == -1){
			resultObj.setSuccess(false);
			return getJsonFromObj(resultObj);
		}
		String acceptUserId=getString("AcceptUserId", "");
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		int formPKID= getInt("formPKID", -1);			
		String wTOther= getString("wTOther", "-1"); //-1:不委托;1:项目经理委托；2部门经理委托
		
		TCheckReview postCheckReview = new TCheckReview();
		///通过Id 获得复核申报信息
		postCheckReview = serviceBCRS.getCheckReviewByID(formPKID);	
		if (postCheckReview.getFId()>0) {
			if(wTOther!="-1"){
			    String CurrentStep=postCheckReview.getFCurrentStep();
				postCheckReview.setFCurrentStep(wTOther+"1");
				postCheckReview.setFLastStep(CurrentStep);
				postCheckReview.setFRecordStep(postCheckReview.getFRecordStep()+"{\"CurrentStep\":"+postCheckReview.getFCurrentStep()+",\"ActiveId\":"+aboveActId+"},");
				if(serviceBCRS.Post(postCheckReview, processId, acceptUserId, aboveActId, remark, formPKID,0))
				{
					resultObj.setSuccess(true);
					resultObj.setMsg("已成功提交到所选择人员，请注意办理情况。");
				}
			}else {
				resultObj.setSuccess(false);
				resultObj.setMsg("委托失败！");
			}
		}else {
			resultObj.setId(0);
			resultObj.setSuccess(false);
			resultObj.setMsg("没有项目检查复核申报记录!");
		}
		return getJsonFromObj(resultObj);
	}
	/**
	 * banjie
	 * @function:
	 * @data: 2013-2-19下午1:39:57
	 * @author jibinbin
	 * @return
	 *
	 */
	
	public String BanJie(){
		int processId= getInt("ProcessId", -1);			
		int aboveActId= getInt("AboveActId", -1);			
		int formPKID= getInt("formPKID", -1);		
		if (serviceBCRS.BanJie(processId,aboveActId,formPKID)) {
			result = SUCCESS_STRING;
		} else {
			result = FAIL_STRING;
		}
		return result;
	}
	/**
	 * 
	 * @Description	: 签收
	 * @Author		: jibb
	 * @Date		: 2013-03-23 19-50
	 * @return
	 */
	public  String Acceptance() {
		//int processId= getInt("ProcessId", -1);
		//int acceptUserId= getInt("AcceptUserId", -1);
		int aboveActId= getInt("AboveActId", -1);
		String remark= getString("Remark", "");
		if (serviceBCRS.Acceptance(aboveActId,remark)) {
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
        if (serviceBCRS.UrgeSb(aboveActId,remark)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
    }
    
    public String Countersign(){
        Result resultObj = new Result();       
        
        int processId = getInt("ProcessId", -1);       
        if (processId == -1){
            resultObj.setSuccess(false);
            return getJsonFromObj(resultObj);
        }
        int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        int formPKID= getInt("formPKID", -1);        
        TCheckReview postCheckReview = new TCheckReview();
        ///通过Id 获得复核申报信息
        postCheckReview = serviceBCRS.getCheckReviewByID(formPKID);
        if (postCheckReview.getFId()>0) {
                String FGlosign = getString("FGLOSign", "");
                String FGlosignTime = getString("FGlosignTime", "");
                postCheckReview.setFGlosign(FGlosign);
                postCheckReview.setFGlosignTime(FGlosignTime);
            if(serviceBCRS.Cuntersign(postCheckReview, processId, aboveActId, remark, formPKID))
            {
                resultObj.setSuccess(true);                
            }
        }else {
            resultObj.setId(0);
            resultObj.setSuccess(false);
        }
        return getJsonFromObj(resultObj);
    }
    /**
     * 
     * @Description : 打回
     * @Author      : jibb
     * @Date        : 2013-3-19 上午12:06:28
     * @return
     */
    public String Return(){
        //int processId= getInt("ProcessId", -1);
        //int acceptUserId= getInt("AcceptUserId", -1);
        int aboveActId= getInt("AboveActId", -1);
        int formPKID= getInt("FormFId", -1);
        String remark= getString("Remark", "");
        if (serviceBCRS.Return(aboveActId,remark,formPKID)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
    }
    
    /**
     * 
     * @Description	: 是否修改
     * @Author		: jibb
     * @Date		: 2013-04-18 14-12
     * @return
     */
    public String IsModify(){       
        Result resultObj = new Result();
        String jsonDataString=getString("JSONDATA", "");
        TCheckReview checkReview =(TCheckReview)getObjectFromJson(jsonDataString,TCheckReview.class);
        resultObj.setId(0);
        resultObj.setSuccess(true);
        String msg=serviceBCRS.objectCompare(checkReview);
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
    //历史任务
    public String GetHistory(){
        Integer id=getInt("ID", -1);
        List<TCheckReview> objlist = serviceBCRS.getCheckReviewsForFID(id,0);
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
    public String SaveHistory(){       
        String jsonDataString=getString("JSONDATA", "");
        String TaskName= getString("TaskName", "");
        TCheckReview checkReview =(TCheckReview)getObjectFromJson(jsonDataString,TCheckReview.class);
        TCheckReview resultCheckReview=serviceBCRS.SaveHistory(checkReview,TaskName);
        Result resultObj = new Result();
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
        return getJsonFromObj(resultObj);                 
    }
    public String CancleHistory(){       
        Result resultObj = new Result();
        //int processId= getInt("ProcessId", -1);          
        int formPKID= getInt("FId", -1);
        if(serviceBCRS.CancleHistory(formPKID))
        {
            resultObj.setSuccess(true);
            resultObj.setId(formPKID);
        }else {
            resultObj.setSuccess(false);
            resultObj.setId(formPKID);
        }
        return getJsonFromObj(resultObj);
    }
}
