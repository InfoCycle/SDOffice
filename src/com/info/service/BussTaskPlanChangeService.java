/**
 * @Title		: BussTaskPlanChangeService.java
 * @Date		: 2013-04-07 14-06
 * @Author		: jibb
 * @Description	: TODO(用一句话描述该文件做什么)
 * @TODO List	: 
 * (增加、修改)了什么  at 日期 时间  by jibb
 * 如:增加delete删除人员方法 at 2013-01-01 16:18 by jibb 

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TImplementPlan;
import com.info.domain.TImplementPlanChange;
import com.info.domain.TTask;
import com.info.domain.TTeamPerson;
import com.info.domain.TTeamPersonChange;
import com.info.domain.TWfProcessSend;
import com.info.domain.ViewImplementPlan;
import com.info.domain.ViewImplementPlanChange;
import com.info.web.CurrentUser;

/**
 * 
 * @ClassName	: BussTaskPlanChangeService   
 * @Description	: 项目实施计划变更  
 * @Author		: jibb
 * @Date		: 2013-04-07 14-06   
 *
 */
@Service
@Transactional
public class BussTaskPlanChangeService {
    @Autowired
    IBaseDao<TTask> taskDao;

    @Autowired
    IBaseDao<TImplementPlan> iPlanDao;
    
    @Autowired
    IBaseDao<TImplementPlanChange> iPlanChangeDao;

    @Autowired
    IBaseDao<ViewImplementPlanChange> iPlanViewDao;

    @Autowired
    IBaseDao<TTeamPerson> teamPersonDao;
    
    @Autowired
    IBaseDao<TTeamPersonChange> teamPersonChangeDao;
    
    @Autowired
    WfProcessUtils processUtils;
    
    @Autowired
    IBaseDao<TWfProcessSend> processSendDao;
    
    @Autowired
    AppSEQHelper SEQHelper;
    
    @Autowired
    BussCommonUtils commonUtils;
    /**
     * 获得任务名称
     * @param id
     * @return
     */
    public String getTaskName(Integer id){
        TTask objTask=new TTask();
        try{
            objTask=taskDao.GetEntity(TTask.class, id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return objTask.getFTaskName();
    }
    
    @SuppressWarnings({ "unchecked" })
    public List<ViewImplementPlanChange> GetTaskPlanForID(Integer id,Integer activeId) {
        //设置业务活动接收时间,修改：业务活动时间在签收时设置。
        //processUtils.setActiveAcceptTime(activeId);
        String SQL = "select a.* from View_ImplementPlanChange a where a.f_id= ? and a.f_state=2";
        javax.persistence.Query query = iPlanViewDao.CreateNativeSQL(SQL,
                ViewImplementPlanChange.class);
        query.setParameter(1, id);
        return (List<ViewImplementPlanChange>) query.getResultList();
    }
    /**
     * 
     * @Description	: 登录用户 获得计划列表，已经抄送的计划
     * @Author		: jibb
     * @Date		: 2013-04-09 15-05
     * @param currentUser
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ViewImplementPlan> GetTaskPlanForPMID(String currentUser){
        String SQL = "select a.* from View_ImplementPlan a where a.F_Current_Step=50 and a.f_projmgr_name like ?";
        javax.persistence.Query query = iPlanViewDao.CreateNativeSQL(SQL,
                ViewImplementPlan.class);
        query.setParameter(1, "%"+currentUser+"%");
        return (List<ViewImplementPlan>) query.getResultList();
    }
    
    
    public Result Save(TImplementPlanChange objIPlan,Integer processId) {
        Result resultObj = new Result();
        if (objIPlan.getFId() > 0) {
            TImplementPlanChange updateObj = iPlanChangeDao.GetEntity(TImplementPlanChange.class,
                    objIPlan.getFId());
            updateObj.setFId(objIPlan.getFId());
            updateObj.setFPlanNumbers(objIPlan.getFPlanNumbers());
            updateObj.setFkTaskId(objIPlan.getFkTaskId());
            updateObj.setFCollectDataTime(objIPlan.getFCollectDataTime());
            updateObj.setFProcessImnTime(objIPlan.getFProcessImnTime());
            updateObj.setFSubmitRewTime(objIPlan.getFSubmitRewTime());
            updateObj.setFIssueResultsTime(objIPlan.getFIssueResultsTime());
            updateObj.setFOther(objIPlan.getFOther());
            // updateObj.setFDeptOpinion(objIPlan.getFDeptOpinion());
            // updateObj.setFDeptOpinionTime(objIPlan.getFDeptOpinionTime());
            updateObj.setFPlanningPerId(objIPlan.getFPlanningPerId());
            updateObj.setFPlanningPerName(objIPlan.getFPlanningPerName());
            updateObj.setFPlanningTime(objIPlan.getFProcessImnTime());
            resultObj.setSuccess(iPlanChangeDao.Update(updateObj));
            resultObj.setId(updateObj.getFId());
            //更新标题
            //processUtils.setProcessTitle(processId,updateObj.getFId(),getTaskName(updateObj.getFkTaskId()));//updateObj.getFPlanNumbers());
        }
        return resultObj;
    }
    public boolean UpdateDeptOpinion(Integer FId,String FDeptOpinions,String FDeptOpinionTime) {
        boolean result=false;
        try {
            if (FId > 0) {
                TImplementPlanChange updateObj = iPlanChangeDao.GetEntity(TImplementPlanChange.class,FId);                 
                updateObj.setFDeptOpinion(FDeptOpinions);
                updateObj.setFDeptOpinionTime(FDeptOpinionTime);
                result = iPlanChangeDao.Update(updateObj);                 
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    // /////////////////////////////////////////////////////////////////////////////////////////
    /*
     * 查询计划组员信息
     */
    @SuppressWarnings({ "unchecked" })
    public List<TTeamPersonChange> GetTeamPersonListForIPlanID(
            Integer ImplementPlanId, Integer start, Integer length) {
        String SQL = "select a.* from T_TeamPersonChange a where a.FK_ImplementPlan_ID= ? order by F_Personnel_Name";
        javax.persistence.Query query = teamPersonChangeDao.CreateNativeSQL(SQL,
                TTeamPersonChange.class);
        query.setParameter(1, ImplementPlanId);
        return (List<TTeamPersonChange>) query.getResultList();
    }

    /**
     * 
     * @Description : 查询计划组员列表 
     * @Author      : jibb
     * @Date        : 2013-03-21 16-15
     * @param pidInteger 计划ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TTeamPersonChange> GetTeamPersonListForIPlanIDEasy(Integer pidInteger){
        String SQL = "select a.* from T_TeamPersonChange a where a.FK_ImplementPlan_ID= ? and f_state=2 order by F_Personnel_Name";
        javax.persistence.Query query = teamPersonChangeDao.CreateNativeSQL(SQL,
                TTeamPersonChange.class);
        query.setParameter(1, pidInteger);
        return (List<TTeamPersonChange>) query.getResultList();
    }
    /**
     * 
     * @Description	: 人员保存
     * @Author		: jibb
     * @Date		: 2013-04-11 10-35
     * @param obj
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public List<TTeamPersonChange> SaveTeamPerson(TTeamPersonChange obj) {
        Integer fIdInteger = 0;
        List<TTeamPersonChange> resultList = null;
        if (obj.getFId() == 0) {
            fIdInteger = SEQHelper.getCurrentVal("SEQ_TEAMPERSON");
            obj.setFId(fIdInteger);
            obj.setFState(2);
            if (teamPersonChangeDao.Persist(obj)) {
                String SQL = "select a.* from T_TeamPersonChange a where a.f_id= ?";
                javax.persistence.Query query = teamPersonChangeDao.CreateNativeSQL(
                        SQL, TTeamPersonChange.class);
                query.setParameter(1, fIdInteger);
                resultList = (List<TTeamPersonChange>) query.getResultList();
            } else {
                return null;
            }
        } else if (obj.getFId() > 0) {
            TTeamPersonChange updateObj = teamPersonChangeDao.GetEntity(TTeamPersonChange.class,
                    obj.getFId());
            // updateObj.setFId(obj.getFId());
            updateObj.setFkImplementPlanId(obj.getFkImplementPlanId());
            updateObj.setFPersonnelId(obj.getFPersonnelId());
            updateObj.setFPersonnelName(obj.getFPersonnelName());
            updateObj.setFJobContent(obj.getFJobContent());
            updateObj.setFAsPosition(obj.getFAsPosition());
            updateObj.setFContactPhone(obj.getFContactPhone());
            updateObj.setFNote(obj.getFNote());
            if (teamPersonChangeDao.Update(updateObj)) {
                String SQL = "select a.* from T_TeamPersonChange a where a.f_id= ?";
                javax.persistence.Query query = teamPersonChangeDao.CreateNativeSQL(
                        SQL, TTeamPersonChange.class);
                query.setParameter(1, obj.getFId());
                resultList = (List<TTeamPersonChange>) query.getResultList();
            }
        }
        return resultList;
    }

    public Result UpdateTeamPerson(TTeamPersonChange obj) {
        Result resultObj = new Result();
        if (obj.getFId() > 0) {
            TTeamPersonChange updateObj = teamPersonChangeDao.GetEntity(TTeamPersonChange.class,
                    obj.getFId());
            // updateObj.setFId(obj.getFId());
            updateObj.setFkImplementPlanId(obj.getFkImplementPlanId());
            updateObj.setFPersonnelId(obj.getFPersonnelId());
            updateObj.setFPersonnelName(obj.getFPersonnelName());
            updateObj.setFJobContent(obj.getFJobContent());
            updateObj.setFAsPosition(obj.getFAsPosition());
            updateObj.setFContactPhone(obj.getFContactPhone());
            updateObj.setFNote(obj.getFNote());
            resultObj.setSuccess(teamPersonChangeDao.Update(updateObj));
            resultObj.setId(updateObj.getFId());
        }
        return resultObj;
    }

    public boolean DeleteTeamPerson(Integer id) {
        try {
            teamPersonChangeDao.Delete(TTeamPersonChange.class, id);
            return true;          
        } catch (Exception e) {
            return false;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     * @Description	: 选择计划确认，取消
     * @Author		: jibb
     * @Date		: 2013-04-11 10-33
     * @param FormFId
     * @param Type
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean SelectPlanAffirm(Integer FormFId,Integer Type,Integer ProcessId,Integer AboveActId){
        boolean flag=false;
        String sSQL="";
        try {
            if(Type==1){ //拷贝计划               
                //复制计划信息
                TImplementPlan objImplementPlan = iPlanDao.GetEntity(TImplementPlan.class, FormFId);
                //更新ProcessFormID及标题
                processUtils.setProcessTitle(ProcessId, FormFId, getTaskName(objImplementPlan.getFkTaskId()));
                TImplementPlanChange objTeamPersonChange=new TImplementPlanChange();
                objTeamPersonChange.setFId(SEQHelper.getCurrentVal("SEQ_PLANCHANGE"));
                objTeamPersonChange.setFPid(objImplementPlan.getFId());
                objTeamPersonChange.setFPlanNumbers(objImplementPlan.getFPlanNumbers());
                objTeamPersonChange.setFkTaskId(objImplementPlan.getFkTaskId());
                objTeamPersonChange.setFCollectDataTime(objImplementPlan.getFCollectDataTime());
                objTeamPersonChange.setFProcessImnTime(objImplementPlan.getFProcessImnTime());
                objTeamPersonChange.setFSubmitRewTime(objImplementPlan.getFSubmitRewTime());
                objTeamPersonChange.setFIssueResultsTime(objImplementPlan.getFIssueResultsTime());
                objTeamPersonChange.setFOther(objImplementPlan.getFOther());
                objTeamPersonChange.setFDeptOpinion(objImplementPlan.getFDeptOpinion());
                objTeamPersonChange.setFDeptOpinionTime(objImplementPlan.getFDeptOpinionTime());
                objTeamPersonChange.setFDeptOpinionManId(objImplementPlan.getFDeptOpinionManId());
                objTeamPersonChange.setFDeptOpinionManName(objImplementPlan.getFDeptOpinionManName());
                objTeamPersonChange.setFPlanningPerId(objImplementPlan.getFPlanningPerId());
                objTeamPersonChange.setFPlanningPerName(objImplementPlan.getFPlanningPerName());
                objTeamPersonChange.setFPlanningTime(objImplementPlan.getFPlanningTime());                
                objTeamPersonChange.setFCurrentStep("10");
                objTeamPersonChange.setFLastStep("10");
                objTeamPersonChange.setFRecordStep(objTeamPersonChange.getFCurrentStep()+"{\"CurrentStep\":10,\"ActiveId\":"+AboveActId+"},");
                objTeamPersonChange.setFState(2);
                iPlanChangeDao.Persist(objTeamPersonChange);
                //获取原始计划人员记录
                sSQL = "select a.* from T_TeamPerson a where a.FK_ImplementPlan_ID= ?";
                javax.persistence.Query query = teamPersonDao.CreateNativeSQL(sSQL,TTeamPerson.class);
                query.setParameter(1, FormFId);
                List<TTeamPerson>  objTeamPersonList=query.getResultList();
                //在历史表中更新数据
                for (TTeamPerson tTeamPerson : objTeamPersonList) {
                    TTeamPersonChange objInsertTPC=new TTeamPersonChange();
                    objInsertTPC.setFId(SEQHelper.getCurrentVal("SEQ_TEAMPERSON"));//tTeamPerson.getFId());
                    objInsertTPC.setFkImplementPlanId(tTeamPerson.getFkImplementPlanId());
                    objInsertTPC.setFPersonnelId(tTeamPerson.getFPersonnelId());
                    objInsertTPC.setFPersonnelName(tTeamPerson.getFPersonnelName());
                    objInsertTPC.setFJobContent(tTeamPerson.getFJobContent());
                    objInsertTPC.setFAsPosition(tTeamPerson.getFAsPosition());
                    objInsertTPC.setFContactPhone(tTeamPerson.getFContactPhone());
                    objInsertTPC.setFNote(tTeamPerson.getFNote());
                    objInsertTPC.setFState(2);
                    teamPersonChangeDao.Persist(objInsertTPC);
                }
                flag=true;
            }else
                if(Type==2){ //撤销拷贝的计划
                    //修改
                    processUtils.setProcessTitle(ProcessId, 0, "空");
                    sSQL="delete from T_ImplementPlanChange where f_pid="+FormFId.toString()+";delete from T_TeamPersonChange where fk_implementplan_id="+FormFId.toString();
                    flag=iPlanChangeDao.ExecuteSQL(sSQL)>0?true:false;
                }else {
                    flag=false;
                }            
            //insert into dbo.T_ImplementPlanChange  select * from dbo.T_ImplementPlan where f_id=247
            //insert into dbo.T_TeamPersonChange  select * from dbo.T_TeamPerson where fk_implementplan_id=247
        } catch (Exception e) {
            flag=false;
        }
        return flag;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *     
     * @Description	: 提交到部门审核变更计划
     * @Author		: jibb
     * @Date		: 2013-04-11 14-55
     * @param processId
     * @param acceptUserId
     * @param aboveActId
     * @param remark
     * @param formPKID
     * @return
     */
    public boolean PostDept(Integer processId,Integer acceptUserId,Integer aboveActId,String remark,
            Integer formPKID,String FCollectDataTime,String FProcessImnTime,
            String FSubmitRewTime,String FIssueResultsTime,String FOther){
        boolean flag=false;
        String sSQL="";
        try {
            //TImplementPlanChange objIPlan= iPlanChangeDao.GetEntity(TImplementPlanChange.class, formPKID);
            sSQL = "select a.* from T_ImplementPlanChange a where a.f_pid= ?";
            javax.persistence.Query query = iPlanChangeDao.CreateNativeSQL(
                    sSQL, TImplementPlanChange.class);
            query.setParameter(1, formPKID);
            TImplementPlanChange objIPlan= (TImplementPlanChange)query.getSingleResult();
            if(objIPlan!=null){
                objIPlan.setFCollectDataTime(FCollectDataTime);
                objIPlan.setFProcessImnTime(FProcessImnTime);
                objIPlan.setFSubmitRewTime(FSubmitRewTime);
                objIPlan.setFIssueResultsTime(FIssueResultsTime);
                objIPlan.setFOther(FOther);
                //提交到部门20：为部门意见
                objIPlan.setFCurrentStep("20");
                objIPlan.setFLastStep("10");
                objIPlan.setFRecordStep(objIPlan.getFRecordStep()+"{\"CurrentStep\":20,\"ActiveId\":"+aboveActId+"},");
                if(iPlanChangeDao.Update(objIPlan)){
                    processUtils.setActiveAcceptTime(aboveActId);
                    flag= processUtils.addProcessActiveItem(processId,acceptUserId,aboveActId,remark)>0 ? true : false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return flag;
    }
    /**
     * 
     * @Description	: 变更审批
     * @Author		: jibb
     * @Date		: 2013-04-11 16-11
     * @param processId
     * @param acceptUserId
     * @param aboveActId
     * @param remark
     * @param formPKID
     * @param isApproval
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean Approval(Integer processId,Integer acceptUserId,Integer aboveActId,String remark,
            Integer formPKID,Integer isApproval){
        boolean flag=false;
        String sSQL="";
        try {
            //TImplementPlanChange objIPlan= iPlanChangeDao.GetEntity(TImplementPlanChange.class, formPKID);
            sSQL = "select a.* from T_ImplementPlanChange a where a.f_pid= ?";
            javax.persistence.Query querypc = teamPersonDao.CreateNativeSQL(sSQL,TImplementPlanChange.class);
            querypc.setParameter(1, formPKID);
            TImplementPlanChange objIPlan= (TImplementPlanChange)querypc.getSingleResult();
            
            if(objIPlan!=null){
                if(isApproval==1){
                    objIPlan.setFCurrentStep("21");  //21同意变更   
                    //同意变更
                    //1、原始计划复制一份副本存储到变更表中
                    TImplementPlan objImplementPlan = iPlanDao.GetEntity(TImplementPlan.class, formPKID);                    
                    TImplementPlanChange objTeamPersonChange=new TImplementPlanChange();
                    objTeamPersonChange.setFId(SEQHelper.getCurrentVal("SEQ_PLANCHANGE"));
                    objTeamPersonChange.setFPid(objImplementPlan.getFId());
                    objTeamPersonChange.setFPlanNumbers(objImplementPlan.getFPlanNumbers());
                    objTeamPersonChange.setFkTaskId(objImplementPlan.getFkTaskId());
                    objTeamPersonChange.setFCollectDataTime(objImplementPlan.getFCollectDataTime());
                    objTeamPersonChange.setFProcessImnTime(objImplementPlan.getFProcessImnTime());
                    objTeamPersonChange.setFSubmitRewTime(objImplementPlan.getFSubmitRewTime());
                    objTeamPersonChange.setFIssueResultsTime(objImplementPlan.getFIssueResultsTime());
                    objTeamPersonChange.setFOther(objImplementPlan.getFOther());
                    objTeamPersonChange.setFDeptOpinion(objImplementPlan.getFDeptOpinion());
                    objTeamPersonChange.setFDeptOpinionTime(objImplementPlan.getFDeptOpinionTime());
                    objTeamPersonChange.setFDeptOpinionManId(objImplementPlan.getFDeptOpinionManId());
                    objTeamPersonChange.setFDeptOpinionManName(objImplementPlan.getFDeptOpinionManName());
                    objTeamPersonChange.setFPlanningPerId(objImplementPlan.getFPlanningPerId());
                    objTeamPersonChange.setFPlanningPerName(objImplementPlan.getFPlanningPerName());
                    objTeamPersonChange.setFPlanningTime(objImplementPlan.getFPlanningTime());                
                    objTeamPersonChange.setFCurrentStep(objImplementPlan.getFCurrentStep());
                    objTeamPersonChange.setFLastStep(objImplementPlan.getFLastStep());
                    objTeamPersonChange.setFRecordStep(objImplementPlan.getFRecordStep());
                    objTeamPersonChange.setFState(1);
                    iPlanChangeDao.Persist(objTeamPersonChange);
                    //1.1 将原始计划中的记录用变更表中的记录覆盖
                    objImplementPlan.setFPlanNumbers(objIPlan.getFPlanNumbers());
                    objImplementPlan.setFkTaskId(objIPlan.getFkTaskId());
                    objImplementPlan.setFCollectDataTime(objIPlan.getFCollectDataTime());
                    objImplementPlan.setFProcessImnTime(objIPlan.getFProcessImnTime());
                    objImplementPlan.setFSubmitRewTime(objIPlan.getFSubmitRewTime());
                    objImplementPlan.setFIssueResultsTime(objIPlan.getFIssueResultsTime());
                    objImplementPlan.setFOther(objIPlan.getFOther());
                    objImplementPlan.setFDeptOpinion(objIPlan.getFDeptOpinion());
                    objImplementPlan.setFDeptOpinionTime(objIPlan.getFDeptOpinionTime());
                    objImplementPlan.setFDeptOpinionManId(objIPlan.getFDeptOpinionManId());
                    objImplementPlan.setFDeptOpinionManName(objIPlan.getFDeptOpinionManName());
                    objImplementPlan.setFPlanningPerId(objIPlan.getFPlanningPerId());
                    objImplementPlan.setFPlanningPerName(objIPlan.getFPlanningPerName());
                    objImplementPlan.setFPlanningTime(objIPlan.getFPlanningTime());                
                    //objImplementPlan.setFCurrentStep(objIPlan.getFCurrentStep());
                    //objImplementPlan.setFLastStep(objIPlan.getFLastStep());
                    //objImplementPlan.setFRecordStep(objIPlan.getFRecordStep());
                    iPlanDao.Update(objImplementPlan);
                    //2、原始计划人员安排情况复制一份副本存储到变更表中人员安排情况                    
                    sSQL = "select a.* from T_TeamPerson a where a.FK_ImplementPlan_ID= ?";
                    javax.persistence.Query query = teamPersonDao.CreateNativeSQL(sSQL,TTeamPerson.class);
                    query.setParameter(1, formPKID);
                    List<TTeamPerson>  objTeamPersonList=query.getResultList();
                    //复制原计划人员到变更表中
                    for (TTeamPerson tTeamPerson : objTeamPersonList) {
                        TTeamPersonChange objInsertTPC=new TTeamPersonChange();
                        objInsertTPC.setFId(SEQHelper.getCurrentVal("SEQ_TEAMPERSON"));
                        objInsertTPC.setFkImplementPlanId(tTeamPerson.getFkImplementPlanId());
                        objInsertTPC.setFPersonnelId(tTeamPerson.getFPersonnelId());
                        objInsertTPC.setFPersonnelName(tTeamPerson.getFPersonnelName());
                        objInsertTPC.setFJobContent(tTeamPerson.getFJobContent());
                        objInsertTPC.setFAsPosition(tTeamPerson.getFAsPosition());
                        objInsertTPC.setFContactPhone(tTeamPerson.getFContactPhone());
                        objInsertTPC.setFNote(tTeamPerson.getFNote());
                        objInsertTPC.setFState(1); //原记录
                        teamPersonChangeDao.Persist(objInsertTPC);
                    }
                    //2.1 将变更表中人员人员移动到变更表中
                    sSQL="delete from T_TeamPerson where FK_ImplementPlan_ID="+formPKID +";"+
                         "insert into T_TeamPerson(f_id,fk_implementplan_id,f_personnel_id,f_personnel_name,f_job_content,f_as_position,f_contact_phone,f_note) "+
                         " select f_id,fk_implementplan_id,f_personnel_id,f_personnel_name,f_job_content,f_as_position,f_contact_phone,f_note from  T_TeamPersonChange where fk_implementplan_id="+formPKID+" and f_state=2";
                    flag =teamPersonDao.ExecuteSQL(sSQL)>0?true:false;
                }
                else{
                    objIPlan.setFCurrentStep("22");  //22不同意变更 
                }
                objIPlan.setFLastStep("20");
                objIPlan.setFRecordStep(objIPlan.getFRecordStep()+"{\"CurrentStep\":"+objIPlan.getFCurrentStep()+",\"ActiveId\":"+aboveActId+"},");
                if(iPlanChangeDao.Update(objIPlan)){
                    processUtils.setActiveState(aboveActId, 4);
                    flag= processUtils.addProcessActiveItem(processId,acceptUserId,aboveActId,remark)>0 ? true : false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return flag;
    }
    
    /**
     * 抄送生产部及总工办
     * @param processId
     * @return
     */  
    public Result CopyAction(Integer processId,Integer aboveActId,Integer formPKID,String remark){
        Result resultObj = new Result();
        try {
            //设置为办理完成
            processUtils.setActiveState(aboveActId, 4);
            processUtils.setProcessState(processId, 4);
            TImplementPlanChange objIPlan= iPlanChangeDao.GetEntity(TImplementPlanChange.class, formPKID);
            if(objIPlan!=null){
                //提交到部门50：为抄送
                objIPlan.setFCurrentStep("50");
                objIPlan.setFLastStep("21");
                objIPlan.setFRecordStep(objIPlan.getFRecordStep()+"{\"CurrentStep\":50,\"ActiveId\":"+aboveActId+"},");
                iPlanChangeDao.Update(objIPlan);
            }
            //生产副总
            String [] userSCList=commonUtils.getAppUserForStation(1002).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
            //技术负责人
            String [] userJSList=commonUtils.getAppUserForStation(1003).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
            //副总工
            String [] userFGList=commonUtils.getAppUserForStation(1006).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
            
            //抄送
            int i=0;
            for(i=0;i<userSCList.length;i++){                
                addProcessSend(processId,  Integer.parseInt(userSCList[i]), remark);
            }       
            for (i=0;i<userJSList.length;i++) {
                addProcessSend(processId,  Integer.parseInt(userJSList[i]), remark);
            }
            for (i=0;i<userFGList.length;i++) {
                addProcessSend(processId,  Integer.parseInt(userFGList[i]), remark);
            }
            resultObj.setId(1);
            resultObj.setSuccess(true);
            resultObj.setMsg("已成功抄送到生产部和总工办。请注意办理情况。");
        } catch (Exception e) {
            resultObj.setId(0);
            resultObj.setSuccess(false);
        }
        return resultObj;
    }
    
    /**
     * 
     * @Description : 增加抄送活动
     * @Author      : jibb
     * @Date        : 2013-04-01 12-54
     * @param processId 过程Id
     * @param acceptUserId 接收人Id
     * @param remark
     * @return
     */
    public Integer addProcessSend(int processId,int acceptUserId,String remark) {
        Integer id=0;
        TWfProcessSend objProcessSend=new TWfProcessSend();
        try {
            id=SEQHelper.getCurrentVal("SEQ_WF_SEND");
            CurrentUser currentUser =SystemCurrentUser.getCurrentUser();
            objProcessSend.setFId(id);
            objProcessSend.setFProcessId(processId);
            objProcessSend.setFSendUser(currentUser.getUserID());
            objProcessSend.setFSendTime(DateUtil.format());
            objProcessSend.setFAcceptUser(acceptUserId);
            objProcessSend.setFRemark(remark);
            objProcessSend.setFState(0);
            processSendDao.Persist(objProcessSend);  
        } catch (Exception e) {
           e.printStackTrace();
        }
        return id;
    }
    /**
     * 
     * @Description : //更新抄送件的阅读状态和阅读时间 最新
     * @Author      : jibb
     * @Date        : 2013-04-02 10-06
     * @param aboveActId
     * @return
     */
    public boolean ReadExt(Integer aboveActId){
        try {
            
            return processUtils.setReadState(aboveActId);           
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 
     * @Description	: 总记录数
     * @Author		: jibb
     * @Date		: 2013-04-09 15-04
     * @param currentUser
     * @return
     */
    public Long getQueryTotalCount(String currentUser) {
        String SQL = "select count(*) from (select a.* from View_ImplementPlan a where a.f_projmgr_name like '%"+currentUser+"%') t";
        javax.persistence.Query query = iPlanViewDao.CreateNativeSQL(SQL);
        Long totalCount = Long.parseLong(query.getSingleResult().toString());//Integer.parseInt(query.getSingleResult().toString());
        return totalCount;
    }
    //insert into dbo.T_ImplementPlanChange  select * from dbo.T_ImplementPlan where f_id=247
    //insert into dbo.T_TeamPersonChange  select * from dbo.T_TeamPerson where fk_implementplan_id=247
}
