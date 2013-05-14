package com.info.web.controller.buss;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.DateUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TContract;
import com.info.domain.TFiles;
import com.info.service.BussContractService;
import com.info.service.BussFileService;
import com.info.web.CurrentUser;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
@Controller
public class BussContractController extends BaseController {
	@Autowired
	BussContractService contractService;
	@Autowired
	BussFileService fileService;
	
	ResultMessage message;
	public String result = "";
	
	@RequestMapping(value = "/Buss/ContractService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();		
		switch (action) {
		case 1:		
			Result = getContracts();
			break;	
		case 2:		
			Result = getFiles();
			break;	
		case 3:		
			Result = SaveOrUpdate();
			break;	
		case 4:		
			Result = delete();
			break;	
		case 5:		
			Result = cancel();
			break;	
		case 6:		
			Result = lock();
			break;	
		case 7:		
			Result = deblock();
			break;	
		}
		// 输出响应json串
		writeJsonString(Result);
	}
	//合同解锁
	private String deblock() {
		Integer FId = getInt("FId", 0);
		if(FId > 0){
			TContract contract = contractService.getContractByID(FId);
			if(contractService.deblock(contract)){
				message.setSuccess(true);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}
	//合同锁定
	private String lock() {
		Integer FId = getInt("FId", 0);
		if(FId > 0){
			TContract contract = contractService.getContractByID(FId);
			if(contractService.lock(contract)){
				message.setSuccess(true);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}
	//合同作废
	private String cancel() {
		Integer FId = getInt("FId", 0);
		if(FId > 0){
			TContract contract = contractService.getContractByID(FId);
			if(contractService.cancel(contract)){
				message.setSuccess(true);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}

	private String delete() {
		Integer FId = getInt("FId", 0);
		if(FId > 0){
			if(contractService.delete(FId)){
				message.setSuccess(true);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			message.setSuccess(false);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}

	private String SaveOrUpdate() {
		Integer FId = getInt("FId", 0);
		String FContractName = getString("FContractName", "");
		String FContractNumbers = getString("FContractNumbers", "");
		String FProjectType = getString("FProjectType", "");
		String FServiceMode = getString("FServiceMode", "");
		String FServicePerion = getString("FServicePerion", "");
		String FSigningDate = getString("FSigningDate", "");
		String FInvesttScale = getString("FInvesttScale", "");
		String FContractFees = getString("FContractFees", "");
		String FOthers = getString("FOthers", "");
		//Integer FMainContractId = getInt("FId", 0);
		Integer FEntrustUnitId = getInt("FEntrustUnitId", 0);
		String FEntrustUnitName = getString("FEntrustUnitName", "");
		CurrentUser currentUser =SystemCurrentUser.getCurrentUser();
		if(FId > 0){
			TContract contract = contractService.getContractByID(FId);
			contract.setFContractName(FContractName);
			contract.setFContractNumbers(FContractNumbers);
			contract.setFProjectType(FProjectType);
			contract.setFServiceMode(FServiceMode);
			contract.setFServicePerion(FServicePerion);
			contract.setFSigningDate(FSigningDate);
			contract.setFInvesttScale(FInvesttScale);
			contract.setFContractFees(FContractFees);
			contract.setFOthers(FOthers);
			contract.setFEntrustUnitId(FEntrustUnitId);
			contract.setFEntrustUnitName(FEntrustUnitName);
			contract.setFState(0);
			contract = contractService.update(contract);
			if(null != contract){
				message.setSuccess(true);
				message.setRoot(contract);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}else{
			TContract contract = new TContract();
			contract.setFContractName(FContractName);
			contract.setFContractNumbers(FContractNumbers);
			contract.setFProjectType(FProjectType);
			contract.setFServiceMode(FServiceMode);
			contract.setFServicePerion(FServicePerion);
			contract.setFSigningDate(FSigningDate);
			contract.setFInvesttScale(FInvesttScale);
			contract.setFContractFees(FContractFees);
			contract.setFOthers(FOthers);
			contract.setFEntrustUnitId(FEntrustUnitId);
			contract.setFEntrustUnitName(FEntrustUnitName);
			contract.setFOperateId(currentUser.getUserID());
			contract.setFOperateName(currentUser.getUserName());
			contract.setFOperateDate(DateUtil.format());
			contract.setFState(0);
			contract = contractService.save(contract);
			if(null != contract){
				message.setSuccess(true);
				message.setRoot(contract);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}

	private String getFiles() {
		int FTypeId=getInt("FTypeId",0);
		List<TFiles> list= fileService.getFiles(FTypeId,"contract");
		if(list!=null && list.size()>0){
			message.setRoot(list);
			message.setSuccess(true);
			message.setMessage("数据获取成功！");
		}else{
			message.setSuccess(false);
			message.setMessage("数据获取失败！");
		}
		return getJsonFromObj(message);
	}

	private String getContracts() {
		int startIndex = getInt("start", 0);
		int pageSize = getInt("limit", 20);
		int unitid = getInt("UnitId", 0);
		if(unitid > 0){
			List<TContract> list = contractService.getContracts(unitid,startIndex, pageSize);
			if(list.size() > 0){
				int count = contractService.getContractsCount(unitid);
				message.setSuccess(true);
				message.setRoot(list);
				message.setTotalProperty(count);
			}else{
				message.setSuccess(true);
				message.setRoot(list);
				message.setTotalProperty(0);
			}
		}else{
			message.setSuccess(true);
			message.setRoot(null);
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}
	
	
}
