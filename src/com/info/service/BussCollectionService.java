package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TCollection;
import com.info.domain.TInvoice;
import com.info.domain.TReceipt;
import com.info.domain.TWfProcess;
import com.info.domain.ViewProcessCustomer;
import com.info.domain.ViewTaskCollection;

@Service
@Transactional
public class BussCollectionService {
	@Autowired
	IBaseDao<TInvoice> invoiceDao;
	@Autowired
	IBaseDao<TReceipt> receiptDao;
	@Autowired
	IBaseDao<TCollection> collectionDao;
	@Autowired
	IBaseDao<TWfProcess> processDao;
	@Autowired
	IBaseDao<ViewTaskCollection> taskcollectionDao;
	@Autowired
	IBaseDao<ViewProcessCustomer> customerDao;
	@Autowired
	AppSEQHelper SEQHelper;
	/**
	 * 返回项目列表的查询信息
	 * @param start
	 * @param limit
	 * @param name
	 * @param number
	 * @param pm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewProcessCustomer> getProcessCustomers(int start,int limit,String name,String number,String pm) {
		
		String sql="select * from View_Process_Customer where f_task_name like ? and f_task_numbers like ? and TaskPM like ?"
				+" and F_Id not in(select F_Task_Id from T_Collection)";
		Query query=customerDao.CreateNativeSQL(sql, ViewProcessCustomer.class);
		query.setParameter(1, "%"+name+"%");
		query.setParameter(2, "%"+number+"%");
		query.setParameter(3, "%"+pm+"%");
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 返回行
	 * @param name
	 * @param number
	 * @param pm
	 * @return
	 */
	public Long getCountByPro(String name,String number,String pm) {
		String sql="select count(*) from View_Process_Customer where f_task_name like ? and f_task_numbers like ? and TaskPM like ?";
		Query query=customerDao.CreateNativeSQL(sql);
		query.setParameter(1, "%"+name+"%");
		query.setParameter(2, "%"+number+"%");
		query.setParameter(3, "%"+pm+"%");
		return Long.valueOf(query.getSingleResult().toString());
	}
	/**
	 * 返回查询的数据
	 * @param start
	 * @param limit
	 * @param name
	 * @param number
	 * @param pm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewTaskCollection> getTaskCollections(int start,int limit,String name,String number,String pm) {
		String sql="select * from View_Task_Collection where f_task_name like ? and f_task_numbers like ? and TaskPM like ?";
		Query query=taskcollectionDao.CreateNativeSQL(sql, ViewTaskCollection.class);
		query.setParameter(1, "%"+name+"%");
		query.setParameter(2, "%"+number+"%");
		query.setParameter(3, "%"+pm+"%");
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<ViewTaskCollection>)query.getResultList();
	}
	/**
	 * 返回查询到数据的条数
	 * @param name
	 * @param number
	 * @param pm
	 * @return
	 */
	public Long getCountByViewTaskCokkection(String name,String number,String pm) {
		String sql="select count(*) from View_Task_Collection where f_task_name like ? and f_task_numbers like ? and TaskPM like ?";
		Query query=taskcollectionDao.CreateNativeSQL(sql);
		query.setParameter(1, "%"+name+"%");
		query.setParameter(2, "%"+number+"%");
		query.setParameter(3, "%"+pm+"%");
		return Long.valueOf(query.getSingleResult().toString());
	}
	/**
	 * 项目收费回填
	 * @param list
	 * @return
	 */
	public boolean updataReceivable(List<Object> list) {
		String sql="update T_Collection set F_Accounts=?,F_Outstanding=? where F_Id=?";
		if (collectionDao.ExecuteSQL(sql, list)>0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 发票收款回填
	 * @param list
	 * @return
	 */
	public boolean updateMoney(List<Object> list) {
		String sql="update T_Invoice set F_Received=?,F_Difference =? where F_Id=?";
		if(invoiceDao.ExecuteSQL(sql,list)>0){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 根据发票Id回填收到款额和未收到款额
	 * @param list
	 * @return
	 */
	public boolean updateByInvoice(List<Object> list) {
		String sql="update T_Invoice set F_Received=?,F_Difference=? where F_Id=?";
		if(invoiceDao.ExecuteSQL(sql, list)>0){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 返回所有项目
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TWfProcess> getTWfProcesses() {
		String sql="select * from T_Wf_Process where f_type_id=10001";
		Query query=processDao.CreateNativeSQL(sql,TWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据项目Id返回项目收费信息
	 * @param start
	 * @param limit
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCollection> getCollectionByTaskId(int start,int limit,int taskId) {
		String sql="select * from T_Collection where F_Task_Id=?";
		Query query=collectionDao.CreateNativeSQL(sql,TCollection.class);
		query.setParameter(1, taskId);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 根据项目收费Id返回项目收费
	 * @param collectionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public double getCollectionByCollectionId(int collectionId) {
		double d=0.0;
		try {
			TCollection collection=collectionDao.GetEntity(TCollection.class, collectionId);
			d=collection.getFReceivable()+collection.getFContractYjCharge();//项目收费等于预计收费+应收费（当项目收费不等于0时预计收费等于0）
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 根据项目收费ID返回发票信息(分页的)
	 * @param start
	 * @param limit
	 * @param collectionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TInvoice> getInvicesByCollectionId(int start,int limit,int collectionId) {
		String sql="select * from T_Invoice where F_Collection_Id=?";
		Query query=invoiceDao.CreateNativeSQL(sql, TInvoice.class);
		query.setParameter(1, collectionId);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 根据项目收费信息返回发票合计收款
	 * @param collectionId
	 * @return
	 */
	public double getInvicesByCollectionId(int collectionId) {
		String sql="select isnull(sum(F_Received),0.00) from T_Invoice where F_Collection_Id=?";
		Query query=invoiceDao.CreateNativeSQL(sql);
		query.setParameter(1, collectionId);
		return Double.valueOf(query.getSingleResult().toString());
	}/**
	 * 根据项目收费信息返回发票总金额
	 * @param collectionId
	 * @return
	 */
	public double getInvicesByCollId(int collectionId) {
		String sql="select isnull(sum(F_Invoice_Money),0.00) from T_Invoice where F_Collection_Id=?";
		Query query=invoiceDao.CreateNativeSQL(sql);
		query.setParameter(1, collectionId);
		return Double.valueOf(query.getSingleResult().toString());
	}
	/**
	 * 根据时间返回发票信息
	 * @param start
	 * @param limit
	 * @param collectionId
	 * @param time
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TInvoice> getInvocesByTime(int start,int limit,int collectionId,String time) {
		String sql="select * from T_Invoice where F_Collection_Id=? and F_Invoice_Time like ?";
		Query query=invoiceDao.CreateNativeSQL(sql,TInvoice.class);
		query.setParameter(1, collectionId);
		query.setParameter(2, "%"+time+"%");
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 根据发票Id返回发票信息
	 * @param start
	 * @param limit
	 * @param collectionId
	 * @param number
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TInvoice> getInvoicesByNumber(int start,int limit,int collectionId,String number) {
		String sql="select * from T_Invoice where F_Collection_Id=? and F_Invoice_Number like ?";
		Query query=invoiceDao.CreateNativeSQL(sql,TInvoice.class);
		query.setParameter(1, collectionId);
		query.setParameter(2, "%"+number+"%");
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 返回发票金额
	 * @param invoiceId
	 * @return
	 */
	public double getMoneyById(int invoiceId) {
		TInvoice invoice=invoiceDao.GetEntity(TInvoice.class, invoiceId);
		return invoice.getFInvoiceMoney();
		//return Double.parseDouble(query.getSingleResult().toString());
	}
	public TInvoice getInvoiceById(int id){
		TInvoice invoice=invoiceDao.GetEntity(TInvoice.class, id);
		return invoice;
	}
	/**
	 * 根据发票ID返回收费信息
	 * @param start
	 * @param limit
	 * @param invoiceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TReceipt> getReceiptsByInvoiceId(int start,int limit,int invoiceId) {
		String sql="select * from T_Receipt where F_Invoice_Id=?";
		Query query=receiptDao.CreateNativeSQL(sql, TReceipt.class);
		query.setParameter(1, invoiceId);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 返回合计收费信息
	 * @param invoiceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public double getReceiptsByInvoiceId(int invoiceId) {
		String sql="select isnull(sum(F_Receipt_Money),0.00) from T_Receipt where F_Invoice_Id=?";
		Query query=receiptDao.CreateNativeSQL(sql);
		query.setParameter(1, invoiceId);
		return Double.valueOf(query.getSingleResult().toString());
		//return d;
		//return Double.parseDouble(query.getSingleResult().toString());
	}
	/**
	 * 根据收款人查询收款信息
	 * @param start
	 * @param limit
	 * @param invoice
	 * @param payee
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TReceipt> getReceiptsByPayee(int start,int limit,int invoiceId,String payee) {
		String sql="select * from T_Receipt where F_Invoice_Id=? and F_Receipt_Payee like ?";
		Query query=receiptDao.CreateNativeSQL(sql, TReceipt.class);
		query.setParameter(1, invoiceId);
		query.setParameter(2, "%"+payee+"%");
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 根据缴费的序号返回缴费信息
	 * @param start
	 * @param limit
	 * @param invoice
	 * @param order
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TReceipt> getReceiptsByOrder(int start,int limit,int invoiceId,int order) {
		String sql="select * from T_Receipt where F_Invoice_Id=? and F_Order=?";
		Query query=receiptDao.CreateNativeSQL(sql, TReceipt.class);
		query.setParameter(1, invoiceId);
		query.setParameter(2, order);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	/**
	 * 修改项目收费
	 * @param collection
	 * @return
	 */
	public boolean updateCollection(TCollection collection) {
		return collectionDao.Update(collection);
	}
	/**
	 * 修改发票信息
	 * @param invoice
	 * @return
	 */
	public boolean updateInvice(TInvoice invoice) {
		return invoiceDao.Update(invoice);
	}
	/**
	 * 修改收费信息
	 * @param receipt
	 * @return
	 */
	public boolean updateReceipt(TReceipt receipt) {
		return receiptDao.Update(receipt);
	}
	/**
	 * 删除项目收费
	 * @param collection
	 * @return
	 */
	public boolean deleteCollection(TCollection collection) {
		return collectionDao.Delete(collection);
	}
	/**
	 * 删除发票信息
	 * @param invoice
	 * @return
	 */
	public boolean deleteInvoice(TInvoice invoice) {
		return invoiceDao.Delete(invoice);
	}
	/**
	 * 删除收费信息
	 * @param receipt
	 * @return
	 */
	public boolean deleteReceipt(TReceipt receipt) {
		return receiptDao.Delete(receipt);
	}
	
	/**
	 * 添加收费信息
	 * @param receipt
	 * @return
	 */
	public boolean addReceipt(TReceipt receipt) {
		receipt.setFId(SEQHelper.getCurrentVal("SEQ_RECEIPT"));
		return receiptDao.Persist(receipt);
	}
	/**
	 * 添加发票
	 * @param invoice
	 * @return
	 */
	public boolean addIncoice(TInvoice invoice) {
		invoice.setFId(SEQHelper.getCurrentVal("SEQ_INVOICE"));
		return invoiceDao.Persist(invoice);
	}
	
	/**
	 * 添加项目收费
	 * @param collection
	 * @return
	 */
	public boolean addCollection(TCollection collection) {
		collection.setFId(SEQHelper.getCurrentVal("SEQ_COLLECTION"));
		return collectionDao.Persist(collection);
	}
	/**
	 * 返回当前发票的收费次数（条数）
	 * @param invoiceId
	 * @return
	 */
	public Long getCountByReceipt(int invoiceId) {
		String d="";
		try {
			String sql="select count(*) from T_Receipt where F_Invoice_Id=?";
			Query query=receiptDao.CreateNativeSQL(sql);
			query.setParameter(1, invoiceId);
			d=query.getSingleResult().toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Long.valueOf(d);
	}
	/**
	 * 返回当前项目收费所开的发票数
	 * @param collectionId
	 * @return
	 */
	public Long getCountByInvoice(int collectionId) {
		String sql="select count(*) from T_Invoice where F_Collection_Id=?";
		Query query=invoiceDao.CreateNativeSQL(sql);
		query.setParameter(1, collectionId);
		return Long.valueOf(query.getSingleResult().toString());
	}
	/**
	 * 返回当前项目下的收费信息
	 * @param taskId
	 * @return
	 */
	public Long getCountByC(int taskId) {
		String sql="select count(*) from T_Collection where F_Task_Id=?";
		Query query=collectionDao.CreateNativeSQL(sql);
		query.setParameter(1, taskId);
		return Long.valueOf(query.getSingleResult().toString());
	}
}
