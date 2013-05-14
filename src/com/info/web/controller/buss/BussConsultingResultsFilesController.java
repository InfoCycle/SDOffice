/**
 * @Title		: BussConsultingResultsFilesController.java 继承自 BaseController
 * @Date		: 2013-03-26 12-11
 * @Author		: chunlei
 * @Description	: 项目咨询成果请求控制
 * @TODO List	: 
 *  consultingResultsFilesController 方法:用于接收页面url
 *  activeReturn 打回方法
 *  activeComplet 设置任务为完成状态
 *  activeUrge 催办方法
 *  setActiveAcceptTime 签收方法
 *  addProcessActiveItem 提交方法
 *  setProcessTitle 设置标题方法
 *  getCodec 返回成果类型
 *  getCoden返回内容选择
 *  deleteConsulting 删除项目档案登记（撤销方法）
 *  updateConsulting 更新档案登记
 *  addConsulting添加方法
 *  TConsultingResultsFiles 从页面获取参数方法
 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import com.info.domain.TAppCode;
import com.info.domain.TConsultingResultsFiles;
import com.info.domain.ViewWfProcess;
import com.info.service.BussArchiveService;
import com.info.service.BussConsultingResultsFilesServer;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussConsultingResultsFilesController extends BaseController{
    @Autowired
    BussConsultingResultsFilesServer consultingServer;
    @Autowired
    BussArchiveService archiveService;
    
    EasyDataGrid dataGrid;
    ResultMessage message;
    String result="";
    
    @RequestMapping(value="/buss/consulting/{action}")
    public void consultingResultsFilesController(@PathVariable("action")Integer action,HttpServletResponse response) throws Exception {
	CurrentResponse =response;
	message=new ResultMessage();
	dataGrid=new EasyDataGrid();
	switch (action) {
	case 0:
	    result=addConsulting(getConsulting());
	    break;
	case 1:
	    result=updateConsulting(getConsulting());
	    break;
	case 2:
	    result=deleteConsulting(getInt("FId", -1),getInt("processId", -1),getInt("activeId", -1));
	    break;
	case 3:
	    result=getCoden();
	    break;
	case 4:
	    result=getCodec();
	    break;
	case 5:
	    result=setProcessTitle(getInt("processId", -1),getInt("formPKID", 0),getString("title", "项目咨询成果档案登记"));
	    break;
	case 6:
	    result=addProcessActiveItem(getInt("processId", -1), getInt("userId", -1), getInt("aboveActId", -1), getString("remark", ""));
	    break;
	case 7:
	    result=setActiveAcceptTime(getInt("activeId", -1));
	    break;
	case 8:
	    result=activeUrge(getInt("activeId", -1),getString("remark", ""));
	    break;
	case 9:
	    result=activeComplet(getInt("taskId", -1),getInt("activeId", -1));
	    break;
	case 10:
	    result=activeReturn(getInt("activeId", -1),getString("remark", ""));
	    break;
	case 11:
	    result=getCosultingById(getInt("formPKID", 0));
	    break;
	case 12:
	    result=getfinishTime(getInt("taskId", -1));
	    break;
	}
	writeJsonString(result);
    }
    /**
     * 
     * @Description	: 根据taskId返回完成时间
     * @Author		: chunlei
     * @Date		: 2013-03-27 21-12
     * @param taskId
     * @return
     */
    private String getfinishTime(int taskId) {
	String jsons="{\"success\":true,\"finishTime\":\"";
	String jsone="{\"success\":false,\"finishTime\":\"";
	try {
	    String time=consultingServer.getfinishTime(taskId);
	    if (time.equals("null")) {
		 jsone+="\"}";
	    }else {
		jsons=jsons+=time+"\"}";
	    }
	    return jsons;
	} catch (Exception e) {
	    jsone+="\"}";
	    return jsone;
	}
    }
    /**
     * 
     * @Description	: 根据Id返回登记
     * @Author		: chunlei
     * @Date		: 2013-03-27 21-12
     * @param FId
     * @return
     * @throws Exception
     */
    private String getCosultingById(int FId) throws Exception {
	try {
	    message.setRoot(consultingServer.getCosultingById(FId));
	    if(message.root!=null){
		message.setSuccess(true);
	    }else {
		message.setSuccess(false);
	    }
	} catch (Exception e) {
	   throw e;
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	:打回方法
     * @Author		: chunlei
     * @Date		: 2013-03-26 16-33
     * @param activeId
     * @param remark
     * @return
     * @throws Exception 
     */
    private String activeReturn(int activeId, String remark) throws Exception {
	try {
	    message.setSuccess(consultingServer.activeReturn(activeId, remark));
	    if (message.success) {
		message.setMessage("打回成功！");
	    } else {
		message.setMessage("打回失败！");
	    }
	} catch (Exception e) {
	    message.setMessage("打回失败！请与管理员联系");
	    message.setSuccess(false);
	    throw e;
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 设置任务为完成状态,同时设置相关内容为归档状态
     * @Author		: chunlei
     * @Date		: 2013-03-26 16-23
     * @param activeId
     * @param taskId
     * @return
     */
    private String activeComplet(int taskId,int activeId) {
	try {
	    message.setSuccess(consultingServer.activeComplet(activeId));
	    String ids="";
	    if (message.success) {
		message.setMessage("任务完成！");
		List<ViewWfProcess> efflist=archiveService.geteff(taskId);
		ids+=efflist.get(0).getFId()+",";
		List<ViewWfProcess> scolist=archiveService.getSco(taskId);
		if (scolist!=null) {
		    if(scolist.size()>0){
			for (ViewWfProcess viewWfProcess : scolist) {
			    ids+=viewWfProcess.getFId()+",";
			}
		    }
		}
		List<ViewWfProcess> cuslist=archiveService.getCus(taskId);
		ids+=cuslist.get(0).getFId()+",";
		List<ViewWfProcess> revlist=archiveService.getReview(taskId);
		ids+=revlist.get(0).getFId()+",";
		List<ViewWfProcess> majlist=archiveService.getMajo(taskId);
		ids+=majlist.get(0).getFId()+",";
		List<ViewWfProcess> chelist=archiveService.getCheckl(taskId);
		for (ViewWfProcess viewWfProcess : chelist) {
		    ids+=viewWfProcess.getFId()+",";
		}
		List<ViewWfProcess> proclist=archiveService.getProc(taskId);
		for (ViewWfProcess viewWfProcess : proclist) {
		    ids+=viewWfProcess.getFId()+",";
		}
		List<ViewWfProcess> implist=archiveService.getImpl(taskId);
		ids+=implist.get(0).getFId();
		archiveService.archivedProcess(ids);
	    } else {
		message.setMessage("提交失败！");
	    }
	} catch (Exception e) {
	    message.setSuccess(false);
	    message.setMessage("提交失败！请与管理员联系");
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 催办方法
     * @Author		: chunlei
     * @Date		: 2013-03-26 16-15
     * @param activeId
     * @param remark
     * @return
     * @throws Exception
     */
    private String activeUrge(int activeId,String remark) throws Exception {
	try {
	    message.setSuccess(consultingServer.activeUrge(activeId, remark));
	    if(message.success){
		message.setMessage("催办信息提交成功！");
	    }else {
		message.setMessage("催办信息提交失败！");
	    }
	} catch (Exception e) {
	    message.setSuccess(false);
	    message.setMessage("任务提交失败！请于管理员联系");
	    throw e;
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 签收方法
     * @Author		: chunlei
     * @Date		: 2013-03-26 16-02
     * @param activeId
     * @return
     */
    private String setActiveAcceptTime(int activeId) {
	try {
	    consultingServer.setActiveAcceptTime(activeId);
	    message.setSuccess(true);
	    message.setMessage("签收成功！");
	} catch (Exception e) {
	    message.setSuccess(false);
	    message.setMessage("签收失败！");
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 提交时的操作
     * @Author		: chunlei
     * @Date		: 2013-03-26 15-54
     * @param processId
     * @param userId
     * @param aboveActId
     * @param remark
     * @return
     * @throws Exception
     */
    private String addProcessActiveItem(int processId, int userId, int aboveActId,
	    String remark) throws Exception {
	try {
	    message.setSuccess(consultingServer.addProcessActiveItem(processId, userId, aboveActId, remark));
	    if (message.success) {
		message.setMessage("提交成功！");
	    } else {
		message.setMessage("提交失败！");
	    }
	} catch (Exception e) {
	    message.setSuccess(false);
	    message.setMessage("提交失败,请与管理员联系");
	    throw e;
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 用于新建登记表时设置标题
     * @Author		: chunlei
     * @Date		: 2013-03-26 15-42
     * @param processId
     * @param formPKID
     * @param title
     * @return
     * @throws Exception
     */
    private String setProcessTitle(int processId, int formPKID, String title) throws Exception {
	try {
	    message.setSuccess(consultingServer.setProcessTitle(processId, formPKID, title));
	    if(message.success){
		message.setMessage("设置标题成功");
	    }else{
		message.setMessage("设置标题失败");
	    }
	} catch (Exception e) {
	   throw e;
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 返回成果类型选择
     * @Author		: chunlei
     * @Date		: 2013-03-26 15-35
     * @return
     * @throws Exception
     */
    private String getCodec() throws Exception {
	String json="[{";
	try {
	    List<TAppCode> list =consultingServer.getAppCodec();
	    for (TAppCode tAppCode : list) {
		json+="\"id\":\""+tAppCode.getFId()+"\",\"text\":\""+tAppCode.getFCodeText()+"\"},{";
	    }
	    json+="\"id\":-1,\"text\":\"请选择...\",\"selected\":true}]";
	} catch (Exception e) {
	    throw e;
	}
	return json;
    }
    /**
     * 
     * @Description	: 返回内容选择
     * @Author		: chunlei
     * @Date		: 2013-03-26 15-34
     * @return
     * @throws Exception
     */
   private String getCoden() throws Exception {
       String json="[{";
	try {
	    List<TAppCode> list =consultingServer.getAppCoden();
	    for (TAppCode tAppCode : list) {
		json+="\"id\":\""+tAppCode.getFId()+"\",\"text\":\""+tAppCode.getFCodeText()+"\"},{";
	    }
	    json+="\"id\":-1,\"text\":\"请选择...\",\"selected\":true}]";
	} catch (Exception e) {
	    throw e;
	}
	return json;
    }
/**
    * 
    * @Description	: 删除项目档案登记（撤销方法）
    * @Author		: chunlei
    * @Date		: 2013-03-26 15-15
    * @param FId
    * @param processId
    * @param activeId
    * @return
    */
    private String deleteConsulting(int FId,int processId,int activeId) {
	try {
	    if (FId>0) {
		message.setSuccess(consultingServer.activeRevoke(processId, activeId)&&consultingServer.deleteObjectById(FId));
	    } else {
		message.setSuccess(consultingServer.activeRevoke(processId, activeId));
	    }
	    if(message.success){
		message.setMessage("撤销成功！");
	    }else {
		message.setMessage("撤销失败！");
	    }
	} catch (Exception e) {
	    message.setSuccess(false);
	    message.setMessage("撤销失败！请与管理员联系");
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 更新档案登记
     * @Author		: chunlei
     * @Date		: 2013-03-26 15-09
     * @param consulting
     * @return
     * @throws Exception
     */
    private String updateConsulting(TConsultingResultsFiles consulting) throws Exception {
	try {
	    
	    if(consultingServer.updataConsulting(consulting)){
		consultingServer.setProcessTitle(getInt("processId", -1),consulting.getFId(), getString("title", ""));
		message.setRoot(consulting);
		message.setSuccess(true);
		message.setMessage("保存成功！");
	    }else {
		message.setSuccess(false);
    	    	message.setMessage("保存失败！请与管理员联系");
	    }
	} catch (Exception e) {
	    message.setSuccess(false);
	    message.setMessage("保存失败！请与管理员联系");
	    throw e;
	}
	return getJsonFromObj(message);
    }
    /**
     * 
     * @Description	: 添加方法
     * @Author		: chunlei
     * @Date		: 2013-03-26 14-58
     * @param consulting
     * @return
     * @throws Exception 
     */
    private String addConsulting(TConsultingResultsFiles consulting) throws Exception {
        try {
           
            consulting=consultingServer.addConsulting(consulting);
            if(consulting!=null){
        	 consultingServer.setProcessTitle(getInt("processId", -1), consulting.getFId(), getString("title", "归档登记"));
       	    	message.setRoot(consulting);
       	    	message.setSuccess(true);
       	    	message.setMessage("保存成功！");
            }else{
        	message.setSuccess(false);
        	message.setMessage("保存失败！请与管理员联系");
            }
        } catch (Exception e) {
        	message.setSuccess(false);
        	message.setMessage("保存失败！请与管理员联系");
        	throw e;
        }
	return getJsonFromObj(message);
    }
/**
 * 
 * @Description	: 从页面获取参数
 * @Author		: chunlei
 * @Date		: 2013-03-26 14-53
 * @return
 */
    private TConsultingResultsFiles getConsulting() {
	TConsultingResultsFiles consultingResultsFiles=new TConsultingResultsFiles();
	consultingResultsFiles.setFId(getInt("FId", 0));
	consultingResultsFiles.setFCompletionTime(getString("FCompletionTime", ""));
	consultingResultsFiles.setFFileNumbers(getString("FFileNumbers", ""));
	consultingResultsFiles.setFFilingAddress(getString("FFilingAddress", ""));
	consultingResultsFiles.setFFilingContents(getString("FFilingContents", ""));
	//consultingResultsFiles.setFFilingContentsId(getString("FFilingContentsId",""));
	consultingResultsFiles.setFFilingPeople(getString("FFilingPeople", ""));
	consultingResultsFiles.setFFilingPeopleId(getInt("FFilingPeopleId", -1));
	consultingResultsFiles.setFFilingTime(getString("FFilingTime", ""));
	consultingResultsFiles.setFFnpvp(getString("FFnpvp", ""));
	consultingResultsFiles.setFkTaskId(getInt("fkTaskId", -1));
	consultingResultsFiles.setFPmdcro(getString("FPmdcro", ""));
	consultingResultsFiles.setFProjAchtId(getInt("FProjAchtId", -1));
	consultingResultsFiles.setFProjAchtType(getString("FProjAchtType", ""));
	consultingResultsFiles.setFReceived(getString("FReceived", ""));
	consultingResultsFiles.setFReceivedId(getInt("FReceivedId", -1));
	consultingResultsFiles.setFReceivingTime(getString("FReceivingTime", ""));
	return consultingResultsFiles;
    }
}
