package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.ViewWfProcess;
import com.info.service.BussArchiveService;
import com.info.service.WfProcessUtils;
import com.info.web.EasyDataGrid;
import com.info.web.JsonArch;
import com.info.web.JsonArchive;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussArchiveController extends BaseController {
	ResultMessage message;
	EasyDataGrid dataGrid;
	JsonArch jsonArch;
	@Autowired
	BussArchiveService archiveService;
	@Autowired
	WfProcessUtils utils;
	
	
	@RequestMapping(value="/buss/archive/{action}")
	public void archiveController(@PathVariable("action")int action,HttpServletResponse response) {
		CurrentResponse = response;
		message=new ResultMessage();
		dataGrid=new EasyDataGrid();
		jsonArch=new JsonArch();
	
		String result="";
		switch (action) {
		case 0:
			result=getTask(getString("name", ""));
			break;
		case 1:
			result=check(getInt("taskId", -1));
			break;
		case 2:
			result=archive(getString("ids", ""));	
		}
		writeJsonString(result);
	}
	/**
	 * 根据id归档
	 * @param ids
	 * @return
	 */
	private String archive(String ids) {
		if(archiveService.archivedProcess(ids)){
			message.setSuccess(true);
			message.setMessage("归档成功");
		}else {
			message.setSuccess(false);
			message.setMessage("归档失败,请与管理员联系!");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 验证是否相关项目都已经完成
	 * @param taskId
	 * @return
	 */
	private String check(int taskId) {
		try {
			jsonArch.setImp(getimp(taskId));
			jsonArch.setChe(getcheck(taskId));
			jsonArch.setCus(getCus(taskId));
			jsonArch.setEff(geteff(taskId));
			jsonArch.setMajor(getMajor(taskId));
			jsonArch.setPro(getPro(taskId));
			jsonArch.setRev(getRev(taskId));
			jsonArch.setSoc(getSco(taskId));
			message.setRoot(jsonArch);
			message.setSuccess(true);
			message.setMessage("准备完毕，正在检查...！") ;
			return getJsonFromObj(message);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("数据加载失败！请与管理员联系");
			return getJsonFromObj(message);
		}
	}
	private JsonArchive geteff(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.geteff(taskId);
		jsonArchive.setData(list);
		jsonArchive.setName("效益工资提取");
		return jsonArchive;
	}
	private JsonArchive getSco(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.getSco(taskId);
		if(list!=null){
			if(list.size()>0){
				jsonArchive.setData(list);
			}else {
				jsonArchive.setData(null);
			}
		}else {
			jsonArchive.setData(null);
		}
		jsonArchive.setName("考评打分");
		return jsonArchive;
	}
	private JsonArchive getCus(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.getCus(taskId);
		jsonArchive.setData(list);
		jsonArchive.setName("顾客评价");
		return jsonArchive;
	}
	private JsonArchive getRev(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.getReview(taskId);
		jsonArchive.setData(list);
		jsonArchive.setName("项目复核申报");
		return jsonArchive;
	}
	private JsonArchive getMajor(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.getMajo(taskId);
		jsonArchive.setData(list);
		jsonArchive.setName("重大事项报告");
		return jsonArchive;
	}
	private JsonArchive getcheck(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.getCheckl(taskId);
		jsonArchive.setData(list);
		jsonArchive.setName("项目检查记录");
		return jsonArchive;
	}
	private JsonArchive getPro(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.getProc(taskId);
		jsonArchive.setData(list);
		jsonArchive.setName("项目过程记录");
		return jsonArchive;
	}
	private JsonArchive getimp(int taskId) {
		JsonArchive jsonArchive =new JsonArchive();
		List<ViewWfProcess> list=archiveService.getImpl(taskId);
		jsonArchive.setData(list);
		jsonArchive.setName("项目实施计划");
		return jsonArchive;
	}
	/**
	 * 返回所有处于完成状态的项目
	 * @param name
	 * @return
	 */
	private String getTask(String name) {
		List<ViewWfProcess> list=archiveService.getViewWfProcesses(name);
		dataGrid.setRows(list);
		return getJsonFromObj(dataGrid);
	}

	
}
