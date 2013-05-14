package com.info.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TClient;
import com.info.domain.TImplementPlan;
import com.info.domain.TIndustry;
import com.info.domain.TTask;
import com.info.domain.TTeamPerson;
import com.info.domain.TWfProcess;

@Service
@Transactional
public class HistoryTastService {
	@Autowired
	IBaseDao<TTask> taskDao;
	@Autowired
	AppSEQHelper SEQHelper;
	@Autowired
	IBaseDao<TImplementPlan> iPlanDao;
	@Autowired
	IBaseDao<TTeamPerson> teamPersonDao;
	@Autowired
	IBaseDao<TWfProcess> processDao;
	@Autowired
	IBaseDao<TClient> clientDao;
	@Autowired
	IBaseDao<TIndustry> industryDao;
	@Autowired
	WfProcessUtils utils;
	
	/**
	 * 
	 * @Description	: 返回项目组成员
	 * @Author		: chunlei
	 * @Date		: 2013-04-19 15-50
	 * @param implmentId
	 * @return
	 */
	public List<TTeamPerson> geTeamPersons(int implmentId) {
	    String sql="select * from T_TeamPerson where FK_ImplementPlan_ID=?";
	    Query query=teamPersonDao.CreateNativeSQL(sql,TTeamPerson.class);
	    query.setParameter(1, implmentId);
	    return query.getResultList();
	}
	/**
	 * 
	 * @Description	: 根据taskID返回任务计划集合
	 * @Author		: chunlei
	 * @Date		: 2013-04-18 11-01
	 * @param taskid
	 * @return
	 */
	public List<Object> gettaskplan(int taskid) {
	    List<Object> retlist=new ArrayList<Object>();
	    TTask task=taskDao.GetEntity(TTask.class, taskid);
	    Query query=iPlanDao.CreateNativeSQL("select * from T_ImplementPlan where FK_Task_ID=?",TImplementPlan.class);
	    query.setParameter(1, taskid);
	    List<TImplementPlan> plans=query.getResultList();
	    TImplementPlan plan=plans.get(0);
	    retlist.add(task);
	    retlist.add(plan);
	    retlist.add(GetTeamPersonListForIPlanID(plan.getFId()));
	    return retlist;
	}
	/**
	 * 
	 * @Description	: 设置为历史
	 * @Author		: chunlei
	 * @Date		: 2013-04-17 14-32
	 * @param processId
	 * @return
	 */
	public boolean setprocesshistory(int taskid) {
	    int processId=getProcessId(10001, taskid);
	    String sql="update T_Wf_Process set F_isHistory=1 where F_ID="+processId;
	    int b=processDao.ExecuteSQL(sql);
	    return b>0?true:false;
	}
	
	/**
	 * 
	 * @Description	: 返回processId
	 * @Author		: chunlei
	 * @Date		: 2013-04-17 11-50
	 * @param typeid
	 * @param taskid
	 * @return
	 */
	public int getProcessId(int typeid,int taskid) {
	    String sql="select F_Id from T_Wf_Process where F_TYPE_ID=? and F_Form_PKID=?";
	    Query query=processDao.CreateNativeSQL(sql);
	    query.setParameter(1, typeid);
	    query.setParameter(2, taskid);
	    return Integer.parseInt(query.getSingleResult().toString());
	}
	@SuppressWarnings({ "unchecked" })
	public List<TTeamPerson> GetTeamPersonListForIPlanID(
			Integer ImplementPlanId) {
		String SQL = "select a.* from T_TeamPerson a where a.FK_ImplementPlan_ID= ? order by F_Personnel_Name";
		javax.persistence.Query query = teamPersonDao.CreateNativeSQL(SQL,
				TTeamPerson.class);
		query.setParameter(1, ImplementPlanId);
		return (List<TTeamPerson>) query.getResultList();
	}
	
	public TImplementPlan save(TTask task,TImplementPlan taskplan,int typeid){
		Integer id = SEQHelper.getCurrentVal("SEQ_TASK");
		task.setFId(id);
		Integer taskplanid = SEQHelper.getCurrentVal("SEQ_TASKPLAN");
		taskplan.setFId(taskplanid);
		taskplan.setFkTaskId(id);
		if(taskDao.Save(task) && iPlanDao.Save(taskplan)){
		    	int processId=utils.addNewProcess(typeid);
		    	utils.setProcessTitle(processId, task.getFId(), task.getFTaskName());
		    	utils.setProcessState(processId, 5);    
			return taskplan;
		}else{
			return null;
		}
	}
	
	public TImplementPlan update(TTask task,TImplementPlan taskplan){
		if(taskDao.Update(task) && iPlanDao.Update(taskplan)){
		    	utils.setProcessTitle(getProcessId(10001, task.getFId()), task.getFId(), task.getFTaskName());
			return taskplan;
		}else{
			return null;
		}
	}
	
	public boolean deleteTaskAndTaskPlan(Integer taskid,Integer taskplanid) {
		if(taskDao.Delete(TTask.class, taskid) && iPlanDao.Delete(TImplementPlan.class, taskplanid)){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean deleteTeamPersons(String ids) {
		if(!"".equals(ids) && null != ids){
			   String sqlnews ="delete from t_teamperson where f_id in("+ids+")";
			   int count = teamPersonDao.ExecuteSQL(sqlnews);
			   return count>0;
		}else{
			return false;
		}
	}
	
	public boolean deleteTaskAndTaskPlanAndPerson(Integer taskid,Integer taskplanid,String ids) {
	    	String sql="select * from T_Wf_Process where F_TYPE_ID=10001 and F_isHistory=1 and F_Form_PKID = ?";
	    	Query query=processDao.CreateNativeSQL(sql, TWfProcess.class);
	    	List<TWfProcess> processes=query.getResultList();
	    	TWfProcess process=processes.get(0);
		if(taskDao.Delete(TTask.class, taskid) && iPlanDao.Delete(TImplementPlan.class, taskplanid) && this.deleteTeamPersons(ids)&&processDao.Delete(TWfProcess.class, process.getFId())){
			return true;
		}else {
			return false;
		}
		
	}
	
	public TTeamPerson saveTeamPerson(TTeamPerson teamPerson) {
		Integer id = SEQHelper.getCurrentVal("SEQ_TEAMPERSON");
		teamPerson.setFId(id);
		if(teamPersonDao.Save(teamPerson)){
			return teamPerson;
		}else{
			return null;
		}
	}
	
	public TTeamPerson updateTeamPerson(TTeamPerson teamPerson){
		if(teamPersonDao.Update(teamPerson)){
			return teamPerson;
		}else{
			return null;
		}
	}
	
	public TTask getTaskByFId(Integer id) {
		return taskDao.GetEntity(TTask.class, id);
	}
	
	public TImplementPlan getImplementPlanByFId(Integer id) {
		return iPlanDao.GetEntity(TImplementPlan.class, id);
	}
	
	public TTeamPerson getTeamPersonByFId(Integer id) {
		return teamPersonDao.GetEntity(TTeamPerson.class, id);
	}
	
	public boolean deleteTeamPerson(Integer id){
		return teamPersonDao.Delete(TTeamPerson.class, id);
	}
}
