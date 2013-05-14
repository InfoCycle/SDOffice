package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.common.dao.IBaseDao;
import com.info.common.util.StringUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TAppUser;
import com.info.domain.ViewWfProcessTemplete;
import com.info.web.CurrentUser;

@Service
public class WfProcessTaskItemService {
	String SQLPage="";
	String UrgeSQL="";
	String QuerySQL="";
	String SQLCount;
	int userId;
	int station;
	@Autowired
	IBaseDao<ViewWfProcessTemplete> processDao;
	@Autowired
	IBaseDao<TAppUser> userDao;
	/**
	 * 获取待办列表
	 * @author liwx at 2013-1-5下午5:47:36
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcessTemplete> getWaitProcess(int start,int limit) {		
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		station= currentUser.getUnitStation();
		SQLPage ="select * from (select a.*,b.f_id as ActiveId,c.F_SEND_USER,c.F_SEND_TIME from dbo.View_wf_process a,"
				+"(select MAX(f_id) f_id,[F_PROCESS_ID] from [T_WF_PROCCESS_ACTIVE]"
				+" where (F_ACCEPT_USER =?1 or F_SEND_USER=?2 or F_ACCEPT_STATION=?3)"
				+" group by [F_PROCESS_ID]) b,T_WF_PROCCESS_ACTIVE c"
				+" where a.F_ID =b.F_PROCESS_ID and b.F_ID = c.F_ID and a.F_STATE in(0,1,2) ) t";
		javax.persistence.Query query =processDao.CreateNativeSQL(SQLPage+" order by t.F_Title,t.F_Type_Name,t.F_SEND_TIME desc",ViewWfProcessTemplete.class);
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		query.setParameter(3, station);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<ViewWfProcessTemplete>)query.getResultList();
	}
	
	public int getWaitProcessCount() {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		station= currentUser.getUnitStation();
		SQLCount ="select count(*) from ("+SQLPage+") as t";		
		javax.persistence.Query query =processDao.CreateNativeSQL(SQLCount);
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		query.setParameter(3, station);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	/**
	 * 获取催办列表
	 * @author liwx at 2013-1-5下午5:47:57
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcessTemplete> getUrgeProcess(int start,int limit) {		
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		station= currentUser.getUnitStation();
		UrgeSQL ="select * from (select a.*,b.f_id as ActiveId,c.F_SEND_USER,c.F_SEND_TIME from dbo.View_wf_process a,"
				+"(select MAX(f_id) f_id,[F_PROCESS_ID] from [T_WF_PROCCESS_ACTIVE]"
				+" where (F_ACCEPT_USER =?1 or F_ACCEPT_STATION=?2) and F_STATE=1 and F_ISURGE=1"
				+" group by [F_PROCESS_ID]) b,T_WF_PROCCESS_ACTIVE c"
				+" where a.F_ID =b.F_PROCESS_ID and b.F_ID = c.F_ID and a.F_STATE=1 ) t";
		javax.persistence.Query query =processDao.CreateNativeSQL(UrgeSQL+" order by t.F_Title,t.F_Type_Name,t.F_SEND_TIME desc",ViewWfProcessTemplete.class);
		query.setParameter(1, userId);
		query.setParameter(2, station);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<ViewWfProcessTemplete>)query.getResultList();
	}
	
	public int getUrgeProcessCount() {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		station= currentUser.getUnitStation();
		String urgeCount ="select count(*) from ("+UrgeSQL+") as t";		
		javax.persistence.Query query =processDao.CreateNativeSQL(urgeCount);
		query.setParameter(1, userId);
		query.setParameter(2, station);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	/**
	 * 获取我新建
	 * @author liwx at 2013-1-5下午5:48:15
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcessTemplete> getOwnerProcess(int start,int limit) {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		SQLPage ="select * from (select a.*,b.f_id as ActiveId,c.F_SEND_USER,c.F_SEND_TIME from dbo.View_wf_process a,"
				+"(select MAX(f_id) f_id,[F_PROCESS_ID] from [T_WF_PROCCESS_ACTIVE]"
				+" group by [F_PROCESS_ID]) b,T_WF_PROCCESS_ACTIVE c"
				+" where a.F_ID =b.F_PROCESS_ID and b.F_ID = c.F_ID"
				+" and a.F_CreateUserID=?1 ) t ";		
		javax.persistence.Query query =processDao.CreateNativeSQL(SQLPage+" order by t.F_Title,t.F_Type_Name, t.F_CreateTime desc",ViewWfProcessTemplete.class);
		query.setParameter(1, userId);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<ViewWfProcessTemplete>)query.getResultList();
	}
	
	public int getOwnerProcessCount() {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		SQLCount ="select count(*) from ("+SQLPage+") as t";		
		javax.persistence.Query query =processDao.CreateNativeSQL(SQLCount);
		query.setParameter(1, userId);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	/**
	 * 获取我经手
	 * @author liwx at 2013-1-5下午5:48:30
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcessTemplete> getHandleProcess(int start,int limit) {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		SQLPage ="select * from (select a.*,b.f_id as ActiveId,c.F_SEND_USER,c.F_SEND_TIME from dbo.View_wf_process a,"
				+"(select MAX(f_id) f_id,[F_PROCESS_ID] from [T_WF_PROCCESS_ACTIVE]"
				+" where F_ACCEPT_USER=?1 or F_SEND_USER=?2"
				+" group by [F_PROCESS_ID]) b,T_WF_PROCCESS_ACTIVE c"
				+" where a.F_ID =b.F_PROCESS_ID and b.F_ID = c.F_ID) t ";
		javax.persistence.Query query =processDao.CreateNativeSQL(SQLPage+" order by t.F_Title,t.F_Type_Name, t.F_SEND_TIME desc",ViewWfProcessTemplete.class);
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<ViewWfProcessTemplete>)query.getResultList();
	}
	
	public int getHandleProcessCount() {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		SQLCount ="select count(*) from ("+SQLPage+") as t";		
		javax.persistence.Query query =processDao.CreateNativeSQL(SQLCount);
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewWfProcessTemplete> getQueryTask(int start,int limit,
			int scopeData,String filetype,String title) {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		userId = currentUser.getUserID();
		QuerySQL ="select * from (select a.*,b.f_id as ActiveId,c.F_SEND_USER,c.F_SEND_TIME from dbo.View_wf_process a,"
				+"(select MAX(f_id) f_id,[F_PROCESS_ID] from [T_WF_PROCCESS_ACTIVE] where 1=1 ";
		if(scopeData==1){
			QuerySQL=QuerySQL+" and (F_ACCEPT_USER="+userId+" or F_SEND_USER="+userId +")";
		}
		QuerySQL =QuerySQL +" group by [F_PROCESS_ID]) b,T_WF_PROCCESS_ACTIVE c"
				+ " where a.F_ID =b.F_PROCESS_ID and b.F_ID = c.F_ID";
		if(scopeData==2){
			QuerySQL =QuerySQL +" and a.F_STATE=1 ";
		}
		if(scopeData==3){
			QuerySQL =QuerySQL +" and a.F_STATE=5 ";
		}
		if(!StringUtil.isEmpty(filetype)){
			QuerySQL = QuerySQL + " and a.F_TYPE_ID in("+filetype+")";
		}
		if(!StringUtil.isEmpty(title)){
			QuerySQL = QuerySQL + " and a.F_TITLE like '%"+title+"%' ";
		}
		QuerySQL = QuerySQL + " ) t";
				
		javax.persistence.Query query =processDao.CreateNativeSQL(QuerySQL+" order by t.F_Title,t.F_Type_Name, t.F_SEND_TIME desc",ViewWfProcessTemplete.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<ViewWfProcessTemplete>)query.getResultList();
	}
	
	public int getQueryTotalCount() {
		SQLCount ="select count(*) from ("+QuerySQL+") as t";		
		javax.persistence.Query query =processDao.CreateNativeSQL(SQLCount);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	public String getSendUserById(String FId) {
		String result="";
		try {
			TAppUser user=userDao.GetEntity(TAppUser.class,Integer.parseInt(FId));
			if(null!=user){
				result = user.getFName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
