package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.common.dao.IBaseDao;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TCheckReview;

import com.info.domain.TProjectAppraisalScoure;
import com.info.domain.TTask;
import com.info.domain.ViewTaskItem;
import com.info.web.CurrentUser;

@Service
public class WfBusCenterTaskItemService {
	@Autowired
	IBaseDao<TTask> taskDao;
	
	@Autowired
	IBaseDao<TProjectAppraisalScoure> pasDao;
		
	@Autowired
	IBaseDao<ViewTaskItem> taskItemDao;
	@Autowired
	IBaseDao<TCheckReview> checkReviewDao;
	
	@SuppressWarnings("unchecked")
	public List<ViewTaskItem> getTaskItemById(int taskId,int typeId) {
		String JPQL="select o from ViewTaskItem o where fkTaskId=?1 and FTypeId=?2 order by o.FProcessId";
		javax.persistence.Query query= taskItemDao.CreateQuery(JPQL, ViewTaskItem.class);
		query.setParameter(1, taskId);
		query.setParameter(2, typeId);
		return query.getResultList();
	}
	
	public TTask getTaskById(int taskId) {
		return taskDao.GetEntity(TTask.class, taskId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TCheckReview> getCheckReviewByTaskId(int taskId) {
		String SQL="select o from TCheckReview o where o.fkTaskId=?1 order by o.FReviewManTime desc";
		javax.persistence.Query query = checkReviewDao.CreateQuery(SQL, TCheckReview.class);
		query.setParameter(1, taskId);
		query.setMaxResults(1);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public int getTaskActiveId(int processId) {
		CurrentUser user= SystemCurrentUser.getCurrentUser();	
		int activeId=-1;
		
		String SQL="select MAX(F_ID) as activeId  from dbo.T_WF_PROCCESS_ACTIVE" 
				+" where F_PROCESS_ID=?1 ";//and F_SEND_USER=?2 or  F_ACCEPT_USER=?3";
		javax.persistence.Query query = taskItemDao.CreateNativeSQL(SQL);
		query.setParameter(1, processId);
		//query.setParameter(2, user.getUserID());
		//query.setParameter(3, user.getUserID());
		List<Integer> results = query.getResultList();
		activeId =results.get(0)==null?-1:results.get(0);		
		return activeId;
	}
	@SuppressWarnings({ "unchecked" })
	public int getTaskAction(int processId,int state) {
		int action=2;
		int activeId=-1;
		int activeState=-1;
		int MaxActiveId=-1;
		
		CurrentUser user= SystemCurrentUser.getCurrentUser();		
		String SQL="select MAX(F_ID) as activeId  from dbo.T_WF_PROCCESS_ACTIVE" 
					+" where F_PROCESS_ID=?1 and F_SEND_USER=?2 or  F_ACCEPT_USER=?3";
		javax.persistence.Query query = taskItemDao.CreateNativeSQL(SQL);
		query.setParameter(1, processId);
		query.setParameter(2, user.getUserID());
		query.setParameter(3, user.getUserID());
		List<Integer> results = query.getResultList();
		MaxActiveId =results.get(0)==null?-1:results.get(0);
		if(MaxActiveId>0&&state==1){
			activeId = MaxActiveId;			
			activeState = getActiveState(activeId);
			if("3,4,5".indexOf(activeState)>-1){
				action = 2;
			}else{
				action =1;
			}
		}
		if(state==0){
			if(MaxActiveId>0){
				action =1;
			}else{
				action =2;
			}
		}					
		return action;
	}
	@SuppressWarnings("unchecked")
	public int getActiveState(int activeId) {
		int activeState=-1;
		String SQL="select F_STATE from dbo.T_WF_PROCCESS_ACTIVE where F_ID=?1";
		javax.persistence.Query query = taskItemDao.CreateNativeSQL(SQL);
		query.setParameter(1, activeId);
		List<Integer> results = query.getResultList();
		if(results!=null){
			for (Integer objects : results) {
				activeState = Integer.parseInt(objects.toString());
			}
		}
		return activeState;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getProjectAppraisalScoureByTaskId(int taskId) {
		String jpql= "select distinct a.F_Pas_Id as F_Id,a.F_Number from T_ProjectAppraisalScoure a where a.fk_Task_Id=?1 order by a.F_Pas_Id";
		javax.persistence.Query query = pasDao.CreateNativeSQL(jpql);
		query.setParameter(1, taskId);
		List<Object[]> results = query.getResultList();		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getBillByTaskId(int taskId) {
		String SQL="SELECT isnull(F_ContractYJ_Charge,0) F_ContractYJ_Charge ,isnull(F_Receivable,0) F_Receivable,"
				+" F_Accounts,F_Outstanding,F_Supporter_Receipt"
				+"  FROM [T_Collection] where F_Task_Id =?1";
		javax.persistence.Query query = pasDao.CreateNativeSQL(SQL);
		query.setParameter(1, taskId);
		List<Object[]> results = query.getResultList();		
		return results;
	}
}
