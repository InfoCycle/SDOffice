/**
 * @Title		: BussTaskPlanChangeController.java
 * @Date		: 2013-04-07 14-05
 * @Author		: jibb
 * @Description	: TODO(用一句话描述该文件做什么)
 * @TODO List	: 
 * (增加、修改)了什么  at 日期 时间  by jibb
 * 如:增加delete删除人员方法 at 2013-01-01 16:18 by jibb 

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TImplementPlan;
import com.info.domain.TTeamPersonChange;
import com.info.domain.ViewImplementPlan;
import com.info.domain.ViewImplementPlanChange;
import com.info.service.BussTaskPlanChangeService;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

/**
 * 
 * @ClassName	: BussTaskPlanChangeController   
 * @Description	: 项目实施计划变更
 * @Author		: jibb
 * @Date		: 2013-04-07 14-05   
 *
 */
@Controller
public class BussTaskPlanChangeController extends BaseController {
    @Autowired
    BussTaskPlanChangeService service;
    
    ResultMessage message;
    public String result = "";

    private static final String SUCCESS_STRING = "{\"success\":true}";
    private static final String FAIL_STRING = "{\"success\":false}";
    /**
     * action 请求类型，目的为了减少MVC映射
     * 
     * @author jibinbin 
     * @param action
     * @param response
     */
    @RequestMapping(value = "/Buss/TaskPlanChangeService/{action}")
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
                Result = getTaskPlanForId(id);
                break;
            case 2:
                Result = getTaskPlanForPMID(); 
                break;
            case 3:
                Result =PostDept();//提交到部门审批
                break;
            case 4:
                Result = SelectPlanAffirm();
                break;
            case 5:         //抄送生产部及总工办
                Result = CopyAction();
                break;
            case 6:         //变更审批
                Result = Approval();
                break;
            case 7:         //阅览
                Result = Read();
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
        }
        // 输出响应json串
        writeJsonString(Result);
    }
    /**
     * 
     * @Description	: 获得变更计划信息
     * @Author		: jibb
     * @Date		: 2013-04-12 10-00
     * @param id    计划ID
     * @return
     */
    public String getTaskPlanForId(Integer id) {
        Integer activeId=getInt("activeId", -1);
        List<ViewImplementPlanChange> objTaskPlan = service.GetTaskPlanForID(id,activeId);
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
    /**
     * 
     * @Description	: 获得原始计划列表，通过项目经理姓名
     * @Author		: jibb
     * @Date		: 2013-04-12 10-00
     * @return
     */
    public String getTaskPlanForPMID(){
        EasyDataGrid ResultJson= new EasyDataGrid();        
        String currentUser=SystemCurrentUser.getCurrentUser().getUserName();
        List<ViewImplementPlan> objTaskPlan = service.GetTaskPlanForPMID(currentUser);
        ResultJson.setRows(objTaskPlan);
        ResultJson.setTotal(service.getQueryTotalCount(currentUser));
        return getJsonFromObj(ResultJson);
    }
    
    /*
            组员信息表
    */
    /**
     * 
     * @Description	: 获得变更的人员列表
     * @Author		: jibb
     * @Date		: 2013-04-12 10-00
     * @param pId   计划ID
     * @param response
     */
   @RequestMapping(value = "/Buss/TaskPlanChangeService/searchPlanPersonChange/{pId}")
   public  void GetTeamPersonList(@PathVariable("pId") Integer pId,HttpServletResponse response){
       CurrentResponse = response;      
       EasyDataGrid ResultJson= new EasyDataGrid();
       try {           
           List<TTeamPersonChange> objectList=service.GetTeamPersonListForIPlanIDEasy(pId);           
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
   //////////////////////////////////////////////////////////////////////////////////
   /**
    * 
    * @Description	: 保存人员信息
    * @Author		: jibb
    * @Date		: 2013-04-12 10-00
    * @return
    */
   public String SaveTeamPerson() {
       TTeamPersonChange obj=new TTeamPersonChange();
       obj.setFId(getInt("PFId", 0));
       obj.setFkImplementPlanId(getInt("PFkImplementPlanId", 0));
       obj.setFPersonnelId(getInt("PFPersonnelId", 0));
       obj.setFPersonnelName(getString("PFPersonnelName", ""));
       obj.setFJobContent(getString("PFJobContent", ""));
       obj.setFAsPosition(getString("PFAsPosition", ""));
       obj.setFContactPhone(getString("PFContactPhone", ""));
       List<TTeamPersonChange> objlist=service.SaveTeamPerson(obj);
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
    * 
    * @Description	: 删除人员信息
    * @Author		: jibb
    * @Date		: 2013-04-12 10-00
    * @return
    */
   public String DeleteTeamPerson() {
       Integer tpidInteger=getInt("TPId", 0);
       if(service.DeleteTeamPerson(tpidInteger)){
           result=SUCCESS_STRING;
       }else {
           result=FAIL_STRING;
       }
       return result;
   }
    /**
     * 
     * @Description	: 更新人员信息
     * @Author		: jibb
     * @Date		: 2013-04-12 10-05
     * @return
     */
   public String UpdateTeamPerson() {
       TTeamPersonChange obj=new TTeamPersonChange();
       obj.setFId(getInt("PFId", 0));
       obj.setFkImplementPlanId(getInt("PFkImplementPlanId", 0));
       obj.setFPersonnelId(getInt("PFPersonnelId", 0));
       obj.setFPersonnelName(getString("PPersonnelName", ""));
       obj.setFJobContent(getString("PFJobContent", ""));
       obj.setFAsPosition(getString("PFAsPosition", ""));
       obj.setFContactPhone(getString("PFContactPhone", ""));
       Result objResult=service.UpdateTeamPerson(obj);
       if (objResult.getSuccess()) {
            result = SUCCESS_STRING;
       } else {
            result = FAIL_STRING;
       }
       return  result;
   }
//////////////////////////////////////////////////////////////////////////////////
   public String UpdateDeptOpinion(){

       Integer FId= getInt("FId", 0);
       String FDeptOpinions=getString("FDeptOpinions", "");
       String FDeptOpinionTime=getString("FDeptOpinionTime", "");
       if (service.UpdateDeptOpinion(FId,FDeptOpinions,FDeptOpinionTime)) {
           result = SUCCESS_STRING;
       } else {
           result = FAIL_STRING;
       }
       return result;
   }
   /**
    * 
    * @Description	: 提交到部门审批
    * @Author		: jibb
    * @Date		: 2013-04-12 10-01
    * @return
    */
    public String PostDept(){
        int processId= getInt("ProcessId", -1);
        int acceptUserId= getInt("AcceptUserId", -1);
        int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        int formPKID= getInt("formPKID", -1);
        String FCollectDataTime=getString("FCollectDataTime", "");
        String FProcessImnTime=getString("FProcessImnTime", "");
        String FSubmitRewTime=getString("FSubmitRewTime", "");
        String FIssueResultsTime=getString("FIssueResultsTime", "");
        String FOther=getString("FOther", "");
        if (service.PostDept(processId,acceptUserId,aboveActId,remark,formPKID,FCollectDataTime,FProcessImnTime,
                FSubmitRewTime,FIssueResultsTime,FOther)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
    }
    /**
     * 
     * @Description	: 部门经理审批
     * @Author		: jibb
     * @Date		: 2013-04-12 10-05
     * @return
     */
    public String Approval(){
        int processId= getInt("ProcessId", -1);
        int acceptUserId= getInt("AcceptUserId", -1);
        int aboveActId= getInt("AboveActId", -1);
        String remark= getString("Remark", "");
        int formPKID= getInt("formPKID", -1);
        int isApproval = getInt("isApproval", -1);
        
        if (service.Approval(processId,acceptUserId,aboveActId,remark,formPKID,isApproval)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
    }
    
    /**
     * 
     * @Description	: 确认选择及撤销变更计划
     * @Author		: jibb
     * @Date		: 2013-04-11 09-33
     * @return
     */
    public String SelectPlanAffirm(){
       Integer FormFId=getInt("FormFId", -1);
       Integer Type=getInt("Type", -1);
       Integer ProcessId=getInt("ProcessId", -1);
       Integer AboveActId=getInt("AboveActId", -1);
       if (service.SelectPlanAffirm(FormFId, Type,ProcessId,AboveActId)) {
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
    /**
     * 
     * @Description	: 抄送生产部及总工办
     * @Author		: jibb
     * @Date		: 2013-04-12 10-06
     * @return
     */
    public String CopyAction(){
        int processId= getInt("ProcessId", -1);
        int aboveActId= getInt("AboveActId", -1);
        int formPKID= getInt("formPKID", -1);
        String remark=getString("Remark", "");
        Result resultObj = new Result();
        resultObj=service.CopyAction(processId, aboveActId,formPKID,remark);
        return getJsonFromObj(resultObj);
    }
     /* 
     * @Description	: 抄送阅览
     * @Author		: jibb
     * @Date		: 2013-04-12 10-07
     * @return
     */
    public String Read(){
        int aboveActId= getInt("AboveActId", -1);
        //String remark= getString("Remark", ""); 
        if (service.ReadExt(aboveActId)) {
            result = SUCCESS_STRING;
        } else {
            result = FAIL_STRING;
        }
        return result;
    }
}
