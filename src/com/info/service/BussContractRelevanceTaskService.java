package com.info.service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TContract;
import com.info.domain.TContractRelevanceTask;
import com.info.domain.ViewContractTree;
import com.info.domain.ViewTask;
import com.info.domain.ViewTaskTree;

@Service
@Transactional
public class BussContractRelevanceTaskService {
	@Autowired
	IBaseDao<TContract> contractDao;
	@Autowired
	IBaseDao<TContractRelevanceTask> contractRelevanceTaskDao;
	@Autowired
	IBaseDao<ViewContractTree> contractTreeDao;
	@Autowired
	IBaseDao<ViewTaskTree> taskTreeDao;
	@Autowired
	IBaseDao<ViewTask> viewTaskDao;
	@Autowired
	AppSEQHelper SEQHelper;
	
	public List<TContractRelevanceTask> contractRelevanceTaskList(int contractid) {
		return (List<TContractRelevanceTask>)contractRelevanceTaskDao.FindByProperty(TContractRelevanceTask.class, "FContractId", contractid, null);
	}
	
	public boolean deleteContractRelevanceTask(String ids) {
		if(!"".equals(ids) && null != ids){
			   String sqlnews ="delete from t_contractrelevancetask where f_id in("+ids+")";
			   int count = contractRelevanceTaskDao.ExecuteSQL(sqlnews);
			   return count>0;
		}else{
			return false;
		}
	}
	
	public boolean saveContractRelevanceTask(int contractid,String[] ids){
		List<TContractRelevanceTask> list = contractRelevanceTaskList(contractid);
		List<Integer> strs = new ArrayList<Integer>();
		if(null != list && list.size() > 0){
			for(int i = 0;i < list.size();i++){
				strs.add(list.get(i).getFId());
			}
			if(deleteContractRelevanceTask(StringUtils.join(strs,","))){
				for(int i = 0;i < ids.length;i++){
					TContractRelevanceTask crt = new TContractRelevanceTask();
					Integer id = SEQHelper.getCurrentVal("SEQ_CONTRACTRELEVANCETASK");
					crt.setFId(id);
					crt.setFContractId(contractid);
					crt.setFTaskId(Integer.valueOf(ids[i]));
					contractRelevanceTaskDao.Save(crt);
				}
				return true;
			}else {
				return false;
			}
		}else {
			for(int i = 0;i < ids.length;i++){
				TContractRelevanceTask crt = new TContractRelevanceTask();
				Integer id = SEQHelper.getCurrentVal("SEQ_CONTRACTRELEVANCETASK");
				crt.setFId(id);
				crt.setFContractId(contractid);
				crt.setFTaskId(Integer.valueOf(ids[i]));
				contractRelevanceTaskDao.Save(crt);
			}
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewContractTree> GetListContractTreeForPID(Integer pid) {
		String SQL = "select a.* from View_ContractTree a where a.F_Parent_ID= ?";
		javax.persistence.Query query = contractTreeDao.CreateNativeSQL(SQL,
				ViewContractTree.class);
		query.setParameter(1, pid);
		return (List<ViewContractTree>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewContractTree> GetListContractTree() {
		String SQL = "select a.* from View_ContractTree a order by F_Sort";
		javax.persistence.Query query = contractTreeDao.CreateNativeSQL(SQL,
				ViewContractTree.class);
		return (List<ViewContractTree>) query.getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<ViewTaskTree> GetListTaskTreeForPID(Integer pid) {
		String SQL = "select a.* from View_TaskTree a where a.F_Parent_ID= ?";
		javax.persistence.Query query = taskTreeDao.CreateNativeSQL(SQL,
				ViewTaskTree.class);
		query.setParameter(1, pid);
		return (List<ViewTaskTree>) query.getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<ViewTaskTree> GetListTaskTree() {
		String SQL = "select a.* from View_TaskTree a";
		javax.persistence.Query query = taskTreeDao.CreateNativeSQL(SQL,
				ViewTaskTree.class);
		return (List<ViewTaskTree>) query.getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<ViewTask> GetTaskTree() {
		String SQL = "select a.* from View_Task a order by F_Sort";
		javax.persistence.Query query = viewTaskDao.CreateNativeSQL(SQL,
				ViewTask.class);
		return (List<ViewTask>) query.getResultList();
	}
}
