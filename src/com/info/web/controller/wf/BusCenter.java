package com.info.web.controller.wf;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TLinkman;
import com.info.domain.ViewArchiveTask;
import com.info.service.WfBusCenterService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

/**
 * 业务中心
 * @author liwx
 *
 */
@Scope("prototype")
@Controller
public class BusCenter extends BaseController{
	@Autowired
	WfBusCenterService busCenterService;
	@RequestMapping( value = "/wf/bcService/{action}/")
	public void getDataService(@PathVariable("action") int action,HttpServletResponse response){
		CurrentResponse = response;
		message = new ResultMessage();
		try {
			switch (action) {
			case 1://获取委托单位类型树数据
				result = getDeputeUnit();
				break;
			case 2://获取委托单位联系人
				result = getDeputeUnitPerson();
				break;
			case 3://获取近三年项目
				result = getDeputeUnitTask();
				break;
			case 4://调整子任务
				result = setParentTask();
				break;
			default:
				break;
			}
			writeJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("rawtypes")
	private String getDeputeUnit() {
		List tree = busCenterService.getTreeData();
		return getJsonFromArray(tree);		
	}
	
	private String getDeputeUnitPerson() {
		int unitId = getInt("unitId", -1);
		List<TLinkman> persons= busCenterService.getLinkManByUnitId(unitId);
		message.setSuccess(persons.size()>0);
		message.setRoot(persons);
		return getJsonFromObj(message);
	}
	
	private String getDeputeUnitTask() {
		int unitId = getInt("unitId", -1);
		List<ViewArchiveTask> archiveTasks =busCenterService.getDeputeUnitTask(unitId);
		message.setSuccess(archiveTasks.size()>0);
		message.setRoot(archiveTasks.size()>0?archiveTasks:null);
		return getJsonFromObj(message);
	}
	
	private String setParentTask() {
		int taskId = getInt("taskId", -1);
		int parentTaskId = getInt("parentTaskId", -1);
		message.setSuccess(busCenterService.updateParentTask(taskId, parentTaskId));
		return getJsonFromObj(message);
	}
}
