package com.info.web.controller.buss;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.DateUtil;
import com.info.domain.TContract;
import com.info.domain.TCustomerEvaluation;
import com.info.domain.TFiles;
import com.info.domain.TWfProcess;
import com.info.domain.ViewProcessCustomer;
import com.info.service.BussCustomerService;
import com.info.service.WfProcessUtils;
import com.info.web.CurrentUser;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
@Controller
public class BussCustomerController extends BaseController {
	ResultMessage message;
	@Autowired
	BussCustomerService customerService;
	@Autowired
	WfProcessUtils utils;
	@RequestMapping(value="/buss/customer/{action}")
	public void custController(@PathVariable("action")int action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		String result="";
		switch (action) {
		case 0:
			result=getProcess();
			break;
		case 1:
			result=getProCus(getInt("cid", 0));
			break;
		case 2:
			result=addcus(getcus());
			break;
		case 3:
			result=getconnumb(getInt("cid", -1));
			break;
		case 4:
			result=updataCus(getInt("fid", -1));
			break;
		case 5:
			result=getFile(getInt("fid", -1));
			break;
		case 6:
			int pid=getInt("proId", -1);
			int fid=getInt("formPKID", -1);
			String titel=getString("title", "");
			result=setproc(pid,fid,titel);
			break;
		case 7:
			result=post(getInt("processId", -1),
					getInt("aboveActId", -1), 
					getInt("uid", -1), 
					getString("remark", ""));
			break;
		case 8:
			result=setActiveComplet(getInt("activeId", -1));
			break;
		case 9:
			result=setActiveAcceptTime(getInt("activeId", -1));
			break;
		case 10:
			result=getCusBycid(getInt("cid", -1));
		}
		writeJsonString(result);
	}
	
	private String getCusBycid(int cid) {
		TCustomerEvaluation customerEvaluation=customerService.getCustomerEvaluation(cid);
		return getProCus(customerEvaluation.getFkTaskId());
	}

	private String setproc(int proId, int formPKID, String title) {
		if (utils.setProcessTitle(proId, formPKID, title)) {
			message.setMessage("保存成功！");
			message.setSuccess(true);
		}else{
			message.setMessage("保存失败！");
			message.setSuccess(true);
		}
		return getJsonFromObj(message);
	}

	private String updataCus(int fid) {
		TCustomerEvaluation cus=customerService.getCustomerEvaluation(fid);
		List<TFiles> list =customerService.getTFiles(cus.getFId(), "pinjia");
		String upact=list.get(0).getFId()+"";
		cus.setFUploadAttachment(upact);
		if(customerService.upcus(cus)){
			message.setSuccess(true);
			message.setMessage("文件上传完成");
		}else {
			message.setSuccess(false);
			message.setMessage("文件上传失败");
		}
		return getJsonFromObj(message);
	}

	private String getconnumb(int taskid) {
		String root="{\"root\":\"";
		List<TContract> list=customerService.geTContracts(taskid);
		if (list.size()>0) {
			for (TContract tContract : list) {
				root+=tContract.getFContractNumbers()+"\\n";
			}
			root=root.substring(0, root.length()-2);
			root+="\"}";
		}else {
			root+="\"}";
		}
		return getJsonFromObj(root);
	}

	private TCustomerEvaluation getcus() {
		TCustomerEvaluation evaluation=new TCustomerEvaluation();
		CurrentUser currentUser=getCurrentUser();
		evaluation.setFId(getInt("fid",0));
		evaluation.setFkTaskId(getInt("taskId", -1));
		evaluation.setFNumbers(getString("numbers", ""));
		evaluation.setFLegendOnId(currentUser.getUserID());
		evaluation.setFLegendOnName(currentUser.getUserName());
		evaluation.setFLegendOnTime(DateUtil.getTime().toString().substring(0, 18));
		evaluation.setFUploadAttachment(getString("imageid", ""));
		return evaluation;
	}

	private String getProCus(int cid) {
		List<ViewProcessCustomer> list=customerService.getpCustomers(cid);
		if(list.size()>0){
			message.setSuccess(true);
			message.setRoot(list);
			message.setMessage("加载成功！");
		}else {
			message.setSuccess(false);
			message.setRoot(list);
			message.setMessage("加载失败！");
		}
		return getJsonFromObj(message);
	}
	private String getProcess() {
		List<TWfProcess> list=customerService.getProcess();
		List<TWfProcess> wpids=customerService.getYQC();
		List<Integer> pids=new ArrayList<Integer>();
		for (TWfProcess tWfProcess : wpids) {
			pids.add(tWfProcess.getFFormPkid());
		}
		String process="[{";
		for (TWfProcess tWfProcess : list) {
			if(!pids.contains(tWfProcess.getFFormPkid())){
				process+="\"id\":"+tWfProcess.getFFormPkid()
						+",\"text\":\""+tWfProcess.getFTitle()+"\"},{";
			}
		}
		process+="\"id\":-1,\"text\":\"请选择...\",\"selected\":true}]";
		return process;
	}

	private String addcus(TCustomerEvaluation customerEvaluation) {
		TCustomerEvaluation cus=new TCustomerEvaluation();
		if (customerEvaluation.getFId()==0) {
			cus=customerService.addcus(customerEvaluation);
		}else {
			cus=customerService.updatacus(customerEvaluation);
		}
		if(cus!=null){
			message.setSuccess(true);
			message.setMessage("保存成功！");
			message.setRoot(cus);
		}else{
			message.setSuccess(false);
			message.setMessage("保存失败！");
		}
		return getJsonFromObj(message);
	}
	private String getFile(int typeId) {
		List<TFiles> list=customerService.getTFiles(typeId, "pinjia");
		message.setRoot(list);
		message.setSuccess(true);
		if (list.size()>0) {
			message.setMessage("加载完成！");
		}else {
			message.setMessage("未找到数据！");
		}
		return getJsonFromObj(message);
	}
	private String post(int processId,int aboveActId,int id,String remark) {
		boolean b=utils.addProcessActiveItem(processId, id, aboveActId, remark)>0?true:false;
		if (b) {
			message.setMessage("提交成功！");
		}else {
			message.setMessage("提交失败！");
		}
		message.setSuccess(b);
		return getJsonFromObj(message);
	}
	private String setActiveComplet(int activeId) {
		if(utils.activeComplet(activeId)){
			message.setSuccess(true);
			message.setMessage("任务完成！");
		}else {
			message.setMessage("提交失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private String setActiveAcceptTime(int activeId) {
		try {
			utils.setActiveAcceptTime(activeId);
			message.setMessage("接收成功！");
		} catch (Exception e) {
			
		}
			return getJsonFromObj(message);
	}
}
