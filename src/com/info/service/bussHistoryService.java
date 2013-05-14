/**
 * @Title		: bussHistoryService.java
 * @Date		: 2013-04-15 10-44
 * @Author		: chunlei
 * @Description	: TODO(用一句话描述该文件做什么)
 * @TODO List	: 
 * (增加、修改)了什么  at 日期 时间  by chunlei
 * 如:增加delete删除人员方法 at 2013-01-01 16:18 by chunlei

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TCheckReview;
import com.info.domain.TCustomerEvaluation;
import com.info.domain.TEfficiencyWage;
import com.info.domain.TImplementPlan;
import com.info.domain.TMajorMattersReport;
import com.info.domain.TProjectAppraisalScoure;
import com.info.domain.TProjectCheck;
import com.info.domain.TProjectProcess;
import com.info.domain.TWfProcess;
import com.info.domain.TWfProcessType;
import com.info.domain.ViewTaskTemp;

@Service
@Transactional
public class bussHistoryService {
    @Autowired
    IBaseDao<TWfProcess> processDao;
    @Autowired
    IBaseDao<TWfProcessType> procedsTypeDao;
    @Autowired
    IBaseDao<ViewTaskTemp> taskTempDao;
    @Autowired
    IBaseDao<TProjectProcess> propDao;
    @Autowired
    IBaseDao<TProjectCheck> projectCDao;
    @Autowired
    IBaseDao<TMajorMattersReport> reportDao;
    @Autowired
    IBaseDao<TCheckReview> checkReviewDao;
    @Autowired
    IBaseDao<TProjectAppraisalScoure> pasDao;
    @Autowired
    IBaseDao<TCustomerEvaluation> customerDao;
    @Autowired
    IBaseDao<TEfficiencyWage> effDao;
    @Autowired
	WfProcessUtils wfUtils;
    @Autowired
    AppSEQHelper seqHelper;
    /**
     * 
     * @Description	: 根据类型返回process
     * @Author		: chunlei
     * @Date		: 2013-04-15 14-53
     * @param typeId 类型Id
     * @param start 开始的记录
     * @param limit 结束的记录
     * @param name 任务名
     * @param time 创建时间
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TWfProcess> getProcessBytype(int typeId,int start,int limit,String name,String entrustUnit,String Industry,String department,String giveperson,String projmgr){
	String sql="";
	switch (typeId) {
	    case 10001:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
		+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
		+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=wf.F_Form_PKID ";
		break;
	    case 10003:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select p.F_Id from T_ProjectProcess p  right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=p.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
		break;
	    case 10004:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select p.F_Id from T_ProjectCheck p  right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=p.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10005:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select m.F_id from T_MajorMattersReport m right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=m.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10006:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select c.F_Id from T_CheckReview c right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=c.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10007:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select p.F_Id from T_ProjectAppraisalScoure p right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=p.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
		break;
	    case 10008:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select c.F_Id from T_CustomerEvaluation c right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ?"
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=c.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10009:
		sql="select wf.* from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select F_Id from T_EfficiencyWage e right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ?"
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=e.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    }
	Query query=processDao.CreateNativeSQL(sql,TWfProcess.class);
	query.setParameter(1, typeId);
	query.setParameter(2, name);
	query.setParameter(3, entrustUnit);
	query.setParameter(4,Industry);
	query.setParameter(5, department);
	query.setParameter(6, giveperson);
	query.setParameter(7, projmgr);
	return query.getResultList();
    }
    
    
     

    /**
     * 
     * @Description	: 返回记录总条数
     * @Author		: chunlei
     * @Date		: 2013-04-15 15-08
     * @param typeId
     * @param name
     * @param time
     * @return
     */
    public Long getCountP(int typeId,int start,int limit,String name,String entrustUnit,String Industry,String department,String giveperson,String projmgr) {
	String sql="";
	switch (typeId) {
	    case 10001:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
		+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
		+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=wf.F_Form_PKID ";
		break;
	    case 10003:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select p.F_Id from T_ProjectProcess p  right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=p.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
		break;
	    case 10004:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select p.F_Id from T_ProjectCheck p  right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=p.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10005:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select m.F_id from T_MajorMattersReport m right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=m.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10006:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select c.F_Id from T_CheckReview c right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=c.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10007:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select p.F_Id from T_ProjectAppraisalScoure p right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ? "
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=p.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
		break;
	    case 10008:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select c.F_Id from T_CustomerEvaluation c right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ?"
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=c.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    case 10009:
		sql="select count(*) from (select * from T_Wf_Process P where F_TYPE_ID=? and F_isHistory=1) wf left join "
			+"(select F_Id from T_EfficiencyWage e right join "
			+"(select F_ID from View_Task_Temp where F_Task_Name like ? and F_EntrustUnit_Name like ? and F_Industry_Category_Name like ?"
			+"and F_Department_Name like ? and F_GivePerson_Name like ? and F_ProjMgr_Name like ?) t on t.F_ID=e.FK_Task_ID "
			+") o on o.F_ID= wf.F_Form_PKID";
	    	break;
	    }
	Query query=processDao.CreateNativeSQL(sql);
	query.setParameter(1, typeId);
	query.setParameter(2, name);
	query.setParameter(3, entrustUnit);
	query.setParameter(4,Industry);
	query.setParameter(5, department);
	query.setParameter(6, giveperson);
	query.setParameter(7, projmgr);
	return Long.valueOf(query.getSingleResult().toString());
    }
    /**
     * 
     * @Description	: 返回processTypes
     * @Author		: chunlei
     * @Date		: 2013-04-15 15-23
     * @return
     */
  @SuppressWarnings("unchecked")
    public List<TWfProcessType>  getProcessType() {
        String sql="select * from T_Wf_Process_Type";
        Query query=procedsTypeDao.CreateNativeSQL(sql,TWfProcessType.class);
        return query.getResultList();
    }
  /**
   * 
   * @Description	: 根据类型Id和formpkid返回processId
   * @Author		: chunlei
   * @Date		: 2013-05-09 10-43
   * @param formpkid
   * @param type
   * @return
   */
  public int getprocessId(int formpkid,int type) {
     String sql="SELECT F_Id from T_Wf_Process where F_TYPE_ID=? and F_Form_PKID=?";
    Query query=processDao.CreateNativeSQL(sql);
    query.setParameter(1, type);
    query.setParameter(2, formpkid);
    List<Integer> list=query.getResultList();
    return list.get(0);
  }
  /**
   * 
   * @Description	: 设置为历史项目
   * @Author		: chunlei
   * @Date		: 2013-05-09 15-35
   * @param processId
   * @return
   */
  public boolean sethistory(int processId) {
      String sql="update T_Wf_Process set F_isHistory=1 where F_ID="+processId;
      int b=processDao.ExecuteSQL(sql);
      return b>0?true:false;
  }
    /**
     * 
     * @Description	: 项目过程情况保存
     * @Author		: chunlei
     * @Date		: 2013-05-09 10-43
     * @param projectProcess
     * @param taskName
     * @return
     */
  public TProjectProcess saveprojectpProcess(TProjectProcess projectProcess,String taskName) {
    if (projectProcess.getFId()==0) {
	projectProcess.setFId(seqHelper.getCurrentVal("SEQ_PROJECTPROCESS"));
	if(propDao.Save(projectProcess)){
	    int processId=wfUtils.addNewProcess(10003);
	    wfUtils.setProcessState(processId, 4);
	    wfUtils.setProcessTitle(processId, projectProcess.getFId(), taskName); 
	    sethistory(processId);
	    return projectProcess;
	}else {
	    return null;
	}
    }else{
	if (propDao.Update(projectProcess)) {
	    int processId=getprocessId(projectProcess.getFId(), 10003);
	    wfUtils.setProcessTitle(processId, projectProcess.getFId(), taskName); 
	    return projectProcess;
	}else {
	    return null;
	}
    }
  }
  /**
   * 
   * @Description	: 项目过程删除
   * @Author		: chunlei
   * @Date		: 2013-05-09 12-14
   * @param projectProcess
   * @return
   */
  public boolean deleteprojectProcess(TProjectProcess projectProcess) {
    if(projectProcess.getFId()==0){
	return true;
    }else {
	int processId=getprocessId(projectProcess.getFId(), 10003);
	if(processDao.Delete(TWfProcess.class, processId)&&propDao.Delete(projectProcess)){
	    return true;
	}else {
	    return false;
	}
    }
  }
  public TProjectCheck saveCheck(TProjectCheck projectCheck,String taskName) {
    if (projectCheck.getFId()==0) {
	projectCheck.setFId(seqHelper.getCurrentVal("SEQ_PROJECTCHECK"));
	if(projectCDao.Save(projectCheck)){
	    int processId=wfUtils.addNewProcess(10004);
	    wfUtils.setProcessState(processId, 4);
	    wfUtils.setProcessTitle(processId, projectCheck.getFId(), taskName);
	    sethistory(processId);
	    return projectCheck;
	}else {
	    return null;
	}
    } else {
	if (projectCDao.Save(projectCheck)) {
	    int processId=getprocessId(projectCheck.getFId(), 10004);
	    wfUtils.setProcessTitle(processId, projectCheck.getFId(), taskName);
	    return projectCheck;
	} else {
	    return null;
	}
    }
}
  /**
   * 
   * @Description	: 删除历史项目检查
   * @Author		: chunlei
   * @Date		: 2013-05-09 18-14
   * @param projectCheck
   * @return
   */
  public boolean deleteCheck(TProjectCheck projectCheck) {
    if(projectCheck.getFId()==0){
	return true;
    }else {
	int processId=getprocessId(projectCheck.getFId(), 10004);
	if (processDao.Delete(TWfProcess.class, processId)&&projectCDao.Delete(projectCheck)) {
	    return true;
	} else {
	    return false;
	}
    }
}
  /**
   * 
   * @Description	: 保存顾客评价
   * @Author		: chunlei
   * @Date		: 2013-05-09 12-26
   * @param customerEvaluation
   * @param taskName
   * @return
   */
  public TCustomerEvaluation saveCustomerEvaluation(TCustomerEvaluation customerEvaluation,String taskName) {
    if(customerEvaluation.getFId()==0){
	customerEvaluation.setFId(seqHelper.getCurrentVal("SEQ_CUSTOMERE"));
	if(customerDao.Save(customerEvaluation)){
	    int processId=wfUtils.addNewProcess(10008);
	    wfUtils.setProcessState(processId, 4);
	    wfUtils.setProcessTitle(processId, customerEvaluation.getFId(), taskName); 
	    sethistory(processId);
	    return customerEvaluation;
	}else {
	    return null;
	}
    }else {
	if (customerDao.Update(customerEvaluation)) {
	    int processId=getprocessId(customerEvaluation.getFId(), 10003);
	    wfUtils.setProcessTitle(processId, customerEvaluation.getFId(), taskName); 
	    return customerEvaluation;
	}else {
	    return null;
	}
    }
  }
  /**
   * 
   * @Description	: 删除顾客评价
   * @Author		: chunlei
   * @Date		: 2013-05-09 12-31
   * @param customerEvaluation
   * @return
   */
  public boolean deletecustomer(TCustomerEvaluation customerEvaluation) {
    if (customerEvaluation.getFId()==0) {
	return true;
    } else {
	int processId=getprocessId(customerEvaluation.getFId(), 10008);
	if (processDao.Delete(TWfProcess.class,processId)&&customerDao.Delete(customerEvaluation)) {
	    return true;
	}else {
	    return false;
	}
    }
  }
}
