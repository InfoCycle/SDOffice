package com.info.web.controller.buss;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Function;
import com.info.common.util.DateUtil;
import com.info.domain.TCollection;
import com.info.domain.TInvoice;
import com.info.domain.TReceipt;
import com.info.domain.ViewProcessCustomer;
import com.info.domain.ViewTaskCollection;
import com.info.service.BussCollectionService;
import com.info.web.CurrentUser;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussCollectionController extends BaseController {
	ResultMessage message;
	EasyDataGrid dataGrid;
	@Autowired
	BussCollectionService collectionService;	
	
	/**
	 * 项目列表控制
	 * @param action
	 * @param response
	 */
	@RequestMapping(value="/buss/pro/{action}")
	public void prossController(@PathVariable("action")int action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		dataGrid=new EasyDataGrid();
		String result="";
		switch (action) {
		case 0:
			int start=getInt("page", 0);
			int limit=getInt("rows", 0);
			if(start>1){
				start*=limit;
				start-=limit;
			}else {
				start=0;
			}
			result=selectPro(start,limit,getString("name", ""),getString("number", ""),getString("pm", ""));
		}
		writeJsonString(result);
	}
	/**
	 * 返回项目列表
	 * @param start
	 * @param limit
	 * @param name
	 * @param number
	 * @param pm
	 * @return
	 */
	private String selectPro(int start, int limit, String name,
			String number, String pm) {
		try {
			List<ViewProcessCustomer> list=collectionService.getProcessCustomers(start, limit, name, number, pm);
			dataGrid.setRows(list);
			dataGrid.setTotal(collectionService.getCountByPro(name, number, pm));//Long.parseLong(collectionService.getCountByPro(name, number, pm)+"L"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 项目收费信息控制
	 * @param action
	 * @param response
	 */
	@RequestMapping(value="/buss/collectionTask/{action}")
	public void taskCollectionController(@PathVariable("action")int action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		dataGrid=new EasyDataGrid();
		String result="";
		switch (action) {
		case 0:
			int limit=getInt("rows", 0);
			int start=getInt("page", 0);
			if(start>1){
				start*=limit;
				start-=limit;
			}else {
				start-=1;
			}
			result=selectCollection(start,limit,getString("name", ""),getString("number", ""),getString("pm", ""));
			break;
		}
		writeJsonString(result);
	}
	private String selectCollection(int start, int limit, String name,	
			String number, String pm) {
		try {
			List<ViewTaskCollection> list=collectionService.getTaskCollections(start, limit, name, number, pm);
			dataGrid.setRows(list);
			dataGrid.setTotal(collectionService.getCountByViewTaskCokkection(name, number, pm));
		} catch (Exception e) {
		}
		
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 项目收费控制
	 * @param action
	 * @param response
	 */
	@RequestMapping(value="/buss/collection/{action}")
	public void collectionController(@PathVariable("action")int action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		String result="";
		switch (action) {
		case 0:
			result=addCollection(getCollection());
			break;
		case 1:
			result=updataCollection(getCollection());
			break;
		case 2:
			result=deleteCollection(getCollection());
			break;
		case 3:
			result=getcollections(getInt("start", 0),getInt("limit", 0),getInt("taskId", 0));
			break;
		}
		writeJsonString(result);
	}
	/**
	 * 根据taskId返回项目收费信息
	 * @param start
	 * @param limit
	 * @param taskId
	 * @return
	 */
	private String getcollections(int start, int limit, int taskId) {
		try {
			List<TCollection> list=collectionService.getCollectionByTaskId(start, limit, taskId);
			dataGrid.setRows(list);
			dataGrid.setTotal(collectionService.getCountByC(taskId));
		} catch (Exception e) {
		}
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 删除项目收费信息
	 * @param collection
	 * @return
	 */
	private String deleteCollection(TCollection collection) {
		boolean b=collectionService.deleteCollection(collection);
		if (b) {
			message.setSuccess(b);
			message.setMessage("删除成功！");
		} else {
			message.setSuccess(b);
			message.setMessage("删除失败！");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 修改项目收费信息
	 * @param collection
	 * @return
	 */
	private String updataCollection(TCollection collection) {
		CurrentUser user=getCurrentUser();
		collection.setFRecordUser(user.getUserName());
		collection.setFRecordTime(DateUtil.format());
		boolean b=collectionService.updateCollection(collection);
		if (b) {
			boolean b1=backfillByCollection(collection);
			if (b1) {
				message.setSuccess(b);
				message.setMessage("修改成功！");
			}else {
				message.setSuccess(b1);
				message.setMessage("修改成功！");
			}
		} else {
			message.setSuccess(b);
			message.setMessage("修改失败！");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 添加项目收费信息
	 * @param collection
	 * @return
	 */
	private String addCollection(TCollection collection) {
		boolean b=collectionService.addCollection(collection);
		if (b) {
			message.setSuccess(b);
			message.setMessage("添加成功！");
		} else {
			message.setSuccess(b);
			message.setMessage("添加失败！");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 发票信息控制
	 * @param action
	 * @param response
	 */
	@RequestMapping(value="/buss/invoice/{action}")
	public void invoiceController(@PathVariable("action")int action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		String result="";
		switch (action) {
		case 0:
			result=addInvoice(getInvoice());
			break;
		case 1:
			result=updataInvoice(getInvoice());
			break;
		case 2:
			result=deleteInvoice(getInvoice());
			break;
		case 3:
			int start=getInt("page", 0);
			int limit=getInt("rows", 0);
			if(start>1){
				start*=limit;
				start-=limit;
			}else {
				start=0;
			}
			result=getInvoices(start,limit,getInt("collectionId", -1));
			break;
		case 4:
			result=getCount(getInt("collectionId", 0));
			break;
		case 5:
			result=getInvicesByCollectionId(getInt("collectionId", -1));
		}
		writeJsonString(result);
	}
	/**
	 * 
	 * @Description	:返回发票总金额与项目收费的差额
	 * @Author		: chunlei
	 * @Date		: 2013-03-25 11-29
	 * @param collectionId
	 * @return
	 */
	private String getInvicesByCollectionId(int collectionId)  {
		//message.setRoot("{\'money\':\'"+collectionService.getInvicesByCollId(collectionId)+"\'}");
	    double chaE=0.00;
	    	try {
	    	    double shoufei =collectionService.getCollectionByCollectionId(collectionId);
	    	    double fapiaoJE=collectionService.getInvicesByCollId(collectionId);
	    	    chaE=shoufei-fapiaoJE;
		} catch (Exception e) {
		    
		}
	    	message.setRoot("{chaE:"+chaE+"}");
	    	return getJsonFromObj(message);
	}
	/**
	 * 查询项目收费下的发票数
	 * @param collectionId
	 * @return
	 */
	private String getCount(int collectionId) {
		long i=collectionService.getCountByInvoice(collectionId);
		if(i>0){
			message.setSuccess(false);
			message.setMessage("该项目收费下存在发票，请先删除发票！");
		}else {
			message.setSuccess(true);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 返回当前项目收费下的发票
	 * @param start
	 * @param limit
	 * @param collectionId
	 * @return
	 */
	private String getInvoices(int start, int limit, int collectionId) {
		try {
			List<TInvoice> list=collectionService.getInvicesByCollectionId(start, limit, collectionId);
			dataGrid.setRows(list);
			dataGrid.setTotal(collectionService.getCountByInvoice(collectionId));
		} catch (Exception e) {
		}
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 删除发票信息
	 * @param invoice
	 * @return
	 */
	private String deleteInvoice(TInvoice invoice) {
		boolean b=collectionService.deleteInvoice(getInvoice());
		if (b) {
			boolean b1=backfillByCollection(invoice.getFCollectionId());
			if (b1) {
				message.setMessage("删除成功！");
				message.setSuccess(b);
			} else {
				message.setMessage("回填失败！");
				message.setSuccess(b1);
			}
		} else {
			message.setMessage("删除失败！");
			message.setSuccess(b);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 修改发票信息
	 * @param invoice
	 * @return
	 */
	private String updataInvoice(TInvoice invoice) {
		boolean b=collectionService.updateInvice(invoice);
		if(b){
			boolean b1=backfill(invoice.getFId());
			boolean b2=backfillByCollection(invoice.getFCollectionId());
			b1=b1&&b2;
			if(b1){
				message.setMessage("修改成功！");
				message.setSuccess(b);
			}else {
				message.setMessage("回填失败！");
				message.setSuccess(b1);
			}
		}else {
			message.setMessage("修改失败！");
			message.setSuccess(b);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 添加发票信息
	 * @param invoice
	 * @return
	 */
	private String addInvoice(TInvoice invoice) {
		boolean b=collectionService.addIncoice(invoice);
		if (b) {
				message.setMessage("添加成功！");
				message.setSuccess(b);
		}else {
			message.setMessage("添加失败！");
			message.setSuccess(b);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 调用回填方式
	 * @param collectionId
	 * @return
	 */
	private boolean backfillByCollection(TCollection collection) {
		double accounts=collectionService.getInvicesByCollectionId(collection.getFId());
		double receivable=collectionService.getCollectionByCollectionId(collection.getFId());
		double outstanding=receivable-accounts;
		collection.setFAccounts(accounts);
		collection.setFOutstanding(outstanding);
		return collectionService.updateCollection(collection);
	}

	public boolean backfillByCollection(int collectionId) {
		double accounts=collectionService.getInvicesByCollectionId(collectionId);
		double receivable=collectionService.getCollectionByCollectionId(collectionId);
		double outstanding=receivable-accounts;
		List<Object> list=new ArrayList<Object>();
		list.add(accounts);
		list.add(outstanding);
		list.add(collectionId);
		return collectionService.updataReceivable(list);
	}
	/**
	 * 收费信息控制
	 * @param action
	 * @param response
	 */
	@RequestMapping(value="/buss/receipt/{action}")
	public void receiptController(@PathVariable("action")int action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		String result="";
		switch (action) {
		case 0:
			result=addReceipt(getReceipt());
			break;
		case 1:
			result=deleteReceipt(getReceipt());
			break;
		case 2:
			result=updataReceipt(getReceipt());
			break;
		case 3:
			int start=getInt("page", 0);
			int limit=getInt("rows", 0);
			if(start>1){
				start*=limit;
				start-=limit;
			}else {
				start=0;
			}
			result=getreceipts(start,limit,getInt("invoiceId", -1));
			break;
		case 4:
			result=getCountR(getInt("invoiceId", -1));
		}
		writeJsonString(result);
	}
	
	private String getCountR(int invoiceId) {
		long i =collectionService.getCountByReceipt(invoiceId);
		if(i>0){
			message.setSuccess(false);
			message.setMessage("该发票下存在收费信息，请先删除收费信息！");
		}else {
			message.setSuccess(true);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 返回发票下的所有收费信息
	 * @param start
	 * @param limit
	 * @param invoiceId
	 * @return
	 */

	private String getreceipts(int start, int limit, int invoiceId) {
		try {
			List<TReceipt> list=collectionService.getReceiptsByInvoiceId(start, limit, invoiceId);
			dataGrid.setRows(list);
			long l=collectionService.getCountByReceipt(invoiceId);
			dataGrid.setTotal(l);
		} catch (Exception e) {
			System.out.println(e);
		}
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 修改收款信息
	 * @param receipt
	 * @return
	 */
	private String updataReceipt(TReceipt receipt) {
		boolean b=collectionService.updateReceipt(receipt);
		if (b) {
			boolean b1=backfill(receipt.getFInvoiceId());
			if (b1) {
				message.setMessage("修改成功！");
				message.setSuccess(b);
			} else {
				message.setMessage("回填失败！");
				message.setSuccess(b);
			}
		} else {
			message.setMessage("修改失败！");
			message.setSuccess(b);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 删除收费信息
	 * @param receipt
	 * @return
	 */
	private String deleteReceipt(TReceipt receipt) {
		boolean b=collectionService.deleteReceipt(receipt);
		if(b){
			boolean b1=backfill(receipt.getFInvoiceId());
			if(b1){
				message.setMessage("删除成功！");
				message.setSuccess(b);
			}else {
				message.setMessage("回填失败！");
				message.setSuccess(b1);
			}
		}else {
			message.setMessage("删除失败！");
			message.setSuccess(b);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 添加收费信息
	 * @param receipt
	 * @return
	 */
	private String addReceipt(TReceipt receipt) {
		boolean b=collectionService.addReceipt(receipt);
		if (b) {
			boolean b1=backfill(receipt.getFInvoiceId());
			if(b1){
				message.setMessage("添加成功！");
				message.setSuccess(b);
			}else {
				message.setMessage("回填失败！");
				message.setSuccess(b1);
			}
		}else {
			message.setMessage("添加失败！");
			message.setSuccess(b);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 获取项目收费信息
	 * @return
	 */

	private TCollection getCollection() {
		CurrentUser user=getCurrentUser();
		TCollection collection=new TCollection();
		collection.setFId(getInt("CFId", -1));
		collection.setFOutstanding(Double.parseDouble(getString("CFOutstanding", "0")));
		collection.setFReceivable(Double.parseDouble(getString("CFReceivable", "0")));
		collection.setFRecordTime(getString("CFRecordTime", DateUtil.format()));
		collection.setFRecordUser(getString("CFRecordUser", user.getUserName()));
		collection.setFState(getInt("CFState", 0));
		collection.setFSupporterReceipt(Double.parseDouble(getString("CFSupporterReceipt", "0")));
		collection.setFTaskId(getInt("CFTaskId", -1));
		collection.setFContractYjCharge(Double.parseDouble(getString("FContractYjCharge", "0")));
		collection.setFAccounts(Double.parseDouble(getString("CFAccounts", "0")));
		return collection;
	}
	/**
	 * 获取发票信息
	 * @return
	 */
	private TInvoice getInvoice() {
		CurrentUser user=getCurrentUser();
		TInvoice invoice=new TInvoice();
		invoice.setFId(getInt("IFId", -1));
		invoice.setFInvoiceMoney(Double.parseDouble(getString("IFInvoiceMoney", "0")));
		invoice.setFInvoiceNumber(getString("IFInvoiceNumber", ""));
		invoice.setFInvoiceTime(getString("IFInvoiceTime", ""));
		invoice.setFReceived(Double.parseDouble(getString("FReceived", "0")));
		invoice.setFDifference(Double.parseDouble(getString("FDifference", "0")));
		invoice.setFCollectionId(getInt("FCollectionId", -1));
		invoice.setFRecordUserName(getString("IFRecordUserName", user.getUserName()));
		invoice.setFRecordUserTime(getString("IFRecordUserTime", DateUtil.format()));
		return invoice;
	}
	/**
	 * 获取收费信息
	 * @return
	 */
	private TReceipt getReceipt() {
		CurrentUser user=getCurrentUser();
		TReceipt receipt=new TReceipt();
		receipt.setFId(getInt("RFId", -1));
		receipt.setFInvoiceId(getInt("RFInvoiceId", -1));
		receipt.setFOrder(getInt("RFOrder", -1));
		receipt.setFReceiptMoney(Double.parseDouble(getString("RFReceiptMoney", "0")));
		receipt.setFReceiptPayee(getString("RFReceiptPayee", ""));
		receipt.setFReceiptTime(getString("RFReceiptTime", ""));
		receipt.setFRecordUserName(getString("RFRecordUserName", user.getUserName()));
		receipt.setFRecordUserTime(getString("RFRecordUserTime", DateUtil.format()));
		return receipt;
	}
	/**
	 * 发票收费信息回填方法
	 * @param invoiceId
	 * @return
	 */

	private boolean backfill(int invoiceId) {
		
		double received=collectionService.getReceiptsByInvoiceId(invoiceId);
		double money=collectionService.getMoneyById(invoiceId);
		double difference=money-received;
		List<Object> list=new ArrayList<Object>();
		list.add(received);
		list.add(difference);
		list.add(invoiceId);
		collectionService.updateMoney(list);
		return backfillByCollection(collectionService.getInvoiceById(invoiceId).getFCollectionId());
	}
}
