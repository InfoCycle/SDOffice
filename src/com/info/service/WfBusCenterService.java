package com.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.DataOption;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TClient;
import com.info.domain.TIndustry;
import com.info.domain.TLinkman;
import com.info.domain.TTask;
import com.info.domain.ViewArchiveTask;
import com.info.web.CurrentUser;

@Service
@Transactional
public class WfBusCenterService {
	@Autowired
	IBaseDao<TIndustry> typeDao;
	@Autowired
	IBaseDao<TClient> deputeUnitDao;
	@Autowired
	IBaseDao<TTask> unitTaskDao;
	
	@Autowired
	IBaseDao<TLinkman> linkManDao;
	@Autowired
	IBaseDao<ViewArchiveTask> archiveTaskDao;
	List<TIndustry> typeList;
	List<TClient>   deputeUnitList;
	List<TTask>     unitTaskList;
	List<Object[]> taskYear;
	@SuppressWarnings({ "rawtypes"})
	List parentTaskId;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTreeData() {
		typeList       = typeDao.Query("select o from TIndustry o where o.FId !=0 order by o.FSort");
		deputeUnitList = deputeUnitDao.Query("select o from TClient o where o.FIndustry !=null order by o.FIndustry,o.FSort");
		String SQL="SELECT F_Year,F_EntrustUnit_ID  FROM [SDOffice].[dbo].[T_Task] "
					+"where [F_ParentTaskID] is null and F_EntrustUnit_ID is not null "
					+"group by F_Year,F_EntrustUnit_ID ";
		String taskSQL;
		javax.persistence.Query query = unitTaskDao.CreateNativeSQL(SQL);
		taskYear = query.getResultList();	
		CurrentUser user= SystemCurrentUser.getCurrentUser();
		/**
		 * added by liwx at 2013-03-21 18:31
		 * @Description	: 按照登录用户查看数据的权限过滤任务
		 * DataOption.ALL 查看全部任务
		 * DataOption.DEPT 查看本部门的任务
		 * DataOption.OWNER 查看本人参与的任务
		 */
		
		if(user.getDataOption()==DataOption.ALL){
			unitTaskList   = unitTaskDao.Query("select o from TTask o where o.FEntrustUnitId !=null order by o.FId");
		}
		if(user.getDataOption()==DataOption.DEPT){
			taskSQL="select o.* from T_Task o where o.F_MainTask_Id =1"
					+" union "
					+" select o.* from T_Task o where o.F_EntrustUnit_ID is not null and o.F_Department_ID="+user.getUserOrgID();
			unitTaskList = unitTaskDao.ExecuteSQLResult(taskSQL,TTask.class);
		}
		//过滤本人参与的任务，关联任务计划表，项目成员表
		if(user.getDataOption()==DataOption.OWNER){
			taskSQL="select * from dbo.T_Task where F_MainTask_ID=1"
					+" union"
					+" select a.* from T_Task a,T_ImplementPlan b,T_TeamPerson c"
					+" where a.F_ID=b.FK_Task_ID and b.F_ID = c.FK_ImplementPlan_ID"
					+" and c.F_Personnel_ID="+user.getUserID();
			unitTaskList = unitTaskDao.ExecuteSQLResult(taskSQL,TTask.class);
		}		
		parentTaskId = getParentTaskID();
		List tree = buildTree();
		return tree;
	}
	/**
	 * 单位联系人
	 * @param unitId
	 * @return
	 */
	public List<TLinkman> getLinkManByUnitId(int unitId) {
		return linkManDao.FindByProperty(TLinkman.class, "FClientId", unitId, null);
	}
	
	public List<ViewArchiveTask> getDeputeUnitTask(int unitId) {
		return archiveTaskDao.FindByProperty(ViewArchiveTask.class, "FUnitId", unitId, "order by model.FArchiveTime desc");
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List buildTree() {
		List list = new ArrayList();
		for (TIndustry type : typeList) {
			Map<String, Object> treeTypeMap = new HashMap();
			treeTypeMap.put("id", type.getFId());
			treeTypeMap.put("parent", "root");
			treeTypeMap.put("text", type.getFName());
			treeTypeMap.put("dataType", "type");
			treeTypeMap.put("iconCls", "type");
			treeTypeMap.put("expanded", false);
			treeTypeMap.put("leaf", false);
			treeTypeMap.put("children",getUnitNode(type.getFId()));
			list.add(treeTypeMap);
		}
		return list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getUnitNode(int typeId) {
		List list = new ArrayList();		
		for (TClient unit : deputeUnitList) {
			if(unit.getFIndustry().equals(typeId)){
				Map<String, Object> treeUnitMap = new HashMap();
				treeUnitMap.put("id", unit.getFId());
				treeUnitMap.put("parent", typeId);
				treeUnitMap.put("text", unit.getFName());
				treeUnitMap.put("dataType", "unit");
				treeUnitMap.put("iconCls", "dept");
				treeUnitMap.put("expanded", false);
				treeUnitMap.put("leaf", false);
				treeUnitMap.put("children",getTaskYearNode(unit.getFId()));
				list.add(treeUnitMap);
			}			
		}
		return list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getTaskYearNode(int unitId) {
		List list = new ArrayList();
		for(Object[] obj : taskYear){
			if(obj[1].equals(unitId)){
				Map<String, Object> yearMap = new HashMap();
				yearMap.put("id", "Y"+obj[1].toString()+obj[0].toString());
				yearMap.put("parent", unitId);
				yearMap.put("text", obj[0]+"年度");
				yearMap.put("dataType", "year");
				yearMap.put("iconCls", "year");
				yearMap.put("expanded", false);
				yearMap.put("leaf", false);
				yearMap.put("children",getTaskNode(unitId,(Integer) obj[0]));
				list.add(yearMap);
			}
		}
		return list;
	}
	@SuppressWarnings({"unchecked", "rawtypes" })
	private List getTaskNode(int unitId,int year) {
		List list = new ArrayList();		
		for (TTask task : unitTaskList) {			
			if(parentTaskId.contains(task.getFId())
					&& task.getFEntrustUnitId().equals(unitId)
					&& task.getFYear().equals(year)
					&& null == task.getFParentTaskId()){
				Map<String, Object> treeTaskMap = new HashMap();
				treeTaskMap.put("id", task.getFId());
				treeTaskMap.put("parent", unitId);
				treeTaskMap.put("text", task.getFTaskName());
				treeTaskMap.put("dataType", "package");
				treeTaskMap.put("iconCls", "package");
				treeTaskMap.put("expanded", true);
				treeTaskMap.put("children",getTaskChildNode(task.getFId()));
				treeTaskMap.put("leaf", false);
				list.add(treeTaskMap);
			}else if(task.getFEntrustUnitId().equals(unitId)
					&& task.getFYear().equals(year)
					&& null ==task.getFParentTaskId()){
				Map<String, Object> treeTaskMap = new HashMap();
				treeTaskMap.put("id", task.getFId());
				treeTaskMap.put("parent", unitId);
				treeTaskMap.put("text", task.getFTaskName());
				treeTaskMap.put("dataType", "task");
				treeTaskMap.put("iconCls", "task");
				treeTaskMap.put("expanded", false);
				treeTaskMap.put("leaf", true);
				list.add(treeTaskMap);
			}
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List  getTaskChildNode(int taskId) {
		List TaskChildNode = new ArrayList();
		for (TTask task : unitTaskList) {
			if(null!=task.getFParentTaskId() && task.getFParentTaskId().equals(taskId))
			if(parentTaskId.contains(taskId) ){
				Map<String, Object> treeTaskMap = new HashMap();
				treeTaskMap.put("id", task.getFId());
				treeTaskMap.put("parent", taskId);
				treeTaskMap.put("text", task.getFTaskName());
				treeTaskMap.put("dataType", "task");
				treeTaskMap.put("iconCls", "task");
				treeTaskMap.put("expanded", true);
				treeTaskMap.put("children",getTaskChildNode(task.getFId()));
				treeTaskMap.put("leaf", false);
				TaskChildNode.add(treeTaskMap);
			}else if(null!=task.getFParentTaskId() 
					&& task.getFParentTaskId().equals(taskId)){
				Map<String, Object> treeTaskMap = new HashMap();
				treeTaskMap.put("id", task.getFId());
				treeTaskMap.put("parent", taskId);
				treeTaskMap.put("text", task.getFTaskName());
				treeTaskMap.put("dataType", "task");
				treeTaskMap.put("iconCls", "task");
				treeTaskMap.put("expanded", true);
				treeTaskMap.put("leaf", true);
				TaskChildNode.add(treeTaskMap);
			}
		}
		return TaskChildNode;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List getParentTaskID() {
		List parentid = new ArrayList();
		for (TTask task : unitTaskList) {			
			if(null!=task.getFParentTaskId()){
				if(!parentid.contains(task.getFParentTaskId())){
					parentid.add(task.getFParentTaskId());
				}
			}
			if(null!=task.getFMainTaskId()&&task.getFMainTaskId()==1){
				if(!parentid.contains(task.getFId())){
					parentid.add(task.getFId());
				}
			}
		}
		return parentid;
	}
	public boolean updateParentTask(int taskId,int parentTaskId) {
		String updateSQL= "update T_Task set F_ParentTaskID=?1 where F_ID=?2";
		javax.persistence.Query query = unitTaskDao.CreateNativeSQL(updateSQL);
		query.setParameter(1, parentTaskId==-1?null:parentTaskId);
		query.setParameter(2, taskId);
		return query.executeUpdate()>0;
	}
}
