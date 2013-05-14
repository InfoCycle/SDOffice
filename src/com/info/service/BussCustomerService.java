package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TContract;
import com.info.domain.TContractRelevanceTask;
import com.info.domain.TCustomerEvaluation;
import com.info.domain.TFiles;
import com.info.domain.TWfProcess;
import com.info.domain.ViewProcessCustomer;

@Service
@Transactional
public class BussCustomerService {
	@Autowired
	IBaseDao<TWfProcess> processDao;
	@Autowired
	IBaseDao<ViewProcessCustomer> customerDao;
	@Autowired
	IBaseDao<TCustomerEvaluation> customerEDao;
	@Autowired
	IBaseDao<TContract>  contractDao;
	@Autowired
	IBaseDao<TFiles> filesDao;
	@Autowired
	IBaseDao<TContractRelevanceTask> crDao;
	@Autowired
	AppSEQHelper SEQHelper;
	
	/**
	 * 根据Id返回customerEvaluation
	 * @param id
	 * @return
	 */
	public TCustomerEvaluation getCustomerEvaluation(int id) {
		return customerEDao.GetEntity(TCustomerEvaluation.class, id);
	}
	
	/**
	 * 返回所有处于完成阶段 且起草评价的任务
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TWfProcess> getProcess(){
		String sql="select * from T_Wf_Process where f_type_id=10001 AND f_state=4";
		Query query=processDao.CreateNativeSQL(sql,TWfProcess.class);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<TWfProcess> getYQC() {
		String sql="select * from T_WF_PROCESS where f_type_id=10008 and F_Form_Pkid is not null";
		Query query=processDao.CreateNativeSQL(sql,TWfProcess.class);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<ViewProcessCustomer> getpCustomers(int taskId) {
		String sql="select * from View_Process_Customer where f_id=?";
		Query query=customerDao.CreateNativeSQL(sql,ViewProcessCustomer.class);
		query.setParameter(1, taskId);
		return (List<ViewProcessCustomer>)query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<TContract> geTContracts(int taskid) {
		/*String sql="select * from T_ContractRelevanceTask where F_Task_ID=?";
		Query query=crDao.CreateNativeSQL(sql,TContractRelevanceTask.class);
		query.setParameter(1, taskid);
		List<TContractRelevanceTask> list=query.getResultList();
		String sqll="select * from T_Contract where F_Id=?";
		Query query2=contractDao.CreateNativeSQL(sqll,TContract.class);
		query2.setParameter(1, list.get(0).getFContractId());
		return query2.getResultList();*/
		String sql="select * from T_Contract where F_ID in (select F_Contract_ID from T_ContractRelevanceTask where F_Task_ID=? )";
		Query query=contractDao.CreateNativeSQL(sql, TContract.class);
		query.setParameter(1, taskid);
		return query.getResultList();
	}
	
	public TCustomerEvaluation addcus(TCustomerEvaluation customerEvaluation) {
		customerEvaluation.setFId(SEQHelper.getCurrentVal("SEQ_CUSTOMERE"));
		if(customerEDao.Persist(customerEvaluation)){
			return customerEvaluation;
		}else {
			return null;
		}
		
	}
	public TCustomerEvaluation updatacus(TCustomerEvaluation customerEvaluation) {
		if(customerEDao.Update(customerEvaluation)){
			return customerEvaluation;
		}else {
			return null;
		}
	}
	public boolean upcus(TCustomerEvaluation customerEvaluation) {
		return customerEDao.Update(customerEvaluation);
	}
	@SuppressWarnings("unchecked")
	public List<TFiles> getTFiles(int typeId,String type) {
		String sql="select * from T_Files where F_TypeId=? and F_Type=? order by F_Date desc";
		Query query=filesDao.CreateNativeSQL(sql,TFiles.class);
		query.setParameter(1, typeId);
		query.setParameter(2, type);
		return query.getResultList();
	}
	
}
