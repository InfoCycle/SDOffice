package com.info.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TAppLog;
import com.info.domain.TAppLogDetail;
import com.info.domain.TAppLogTemplate;
import com.info.domain.TAppLogTemplateDetail;
import com.info.service.SystemLogTemplateService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemLogTemplateController extends BaseController {
	@Autowired
	SystemLogTemplateService logService;

	/**
	 * @author liwx
	 * @param action
	 *            1:获取日志模板
	 * @param action
	 *            2:获取模板字段
	 * @param action
	 *            3:保存模板信息
	 * @param action
	 *            4:保存字段信息
	 * @param action
	 *            5:日志信息查询
	 * @param action
	 *            6:根据日志Id获取日志详细信息
	 * @param response
	 */
	@RequestMapping(value = "/system/SvrService/LogTemplateService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response) {
		CurrentResponse = response;
		message = new ResultMessage();
		String resultString = "";
		switch (action) {
		case 1:
			resultString = getLogTemplate();
			break;
		case 2:
			resultString = getLogTemplateDetail();
			break;
		case 3:
			resultString = saveOrUpdateTemplate();
			break;
		case 4:
			resultString = saveOrUpdateTemplateDetail();
			break;
		case 5:
			resultString = queryLogData();
			break;
		case 6:
			resultString = queryLogDetailByLogId();
			break;
		}
		writeJsonString(resultString);
	}

	private String getLogTemplate() {
		List<TAppLogTemplate> template=logService.getLogTemplate();
		message.setSuccess(true);
		message.setTotalProperty(template.size());
		message.setRoot(template);
		return getJsonFromObj(message);
	}

	private String getLogTemplateDetail() {
		int templateId = getInt("templateId", 0);
		List<TAppLogTemplateDetail> details = logService.getLogTemplateDetailsByTempId(templateId);
		message.setSuccess(true);
		message.setTotalProperty(details.size());
		message.setRoot(details);
		return getJsonFromObj(message);
	}

	private String saveOrUpdateTemplate() {
		TAppLogTemplate template = new TAppLogTemplate();
		template.setFId(getInt("FId", 0));
		template.setFTable(getString("FTable", ""));
		template.setFTableName(getString("FTableName", ""));
		template.setFState(getInt("FState", 1));
		TAppLogTemplate objAppLogTemplate = logService.saveOrUpdateTemplate(template);
		message.setSuccess(true);
		message.setTotalProperty(0);
		message.setRoot(objAppLogTemplate);
		return getJsonFromObj(message);
	}

	private String saveOrUpdateTemplateDetail() {
		TAppLogTemplateDetail detail =new TAppLogTemplateDetail();
		detail.setFId(getInt("FId", 0));
		detail.setFColumn(getString("FColumn", ""));
		detail.setFColumnName(getString("FColumnName", ""));
		detail.setFkTemplateId(getInt("fkTemplateId", 0));
		detail.setFSort(getInt("FSort", 0));
		boolean flag = logService.saveOrUpdateTemplateDetail(detail);
		message.setSuccess(flag);
		return getJsonFromObj(message);
	}

	private String queryLogData() {
		String startActionDate=getString("startActionDate", "");
		String endActionDate=getString("endActionDate", "");
		String actionUserName=getString("actionUserName", "");
		String actionObject=getString("actionObject", "");
		start=getInt("start", 0);
		limit=getInt("limit", 20);
		List<TAppLog> logMasters=logService.queryLogData(startActionDate, 
				endActionDate, actionUserName, actionObject,start,limit);
		message.setSuccess(true);
		message.setTotalProperty(logService.getQueryTotalCount());
		message.setRoot(logMasters);
		return getJsonFromObj(message);
	}
	
	private String queryLogDetailByLogId() {
		int logMasterId =getInt("logMasterId", 0);
		List<TAppLogDetail> details = logService.getLogDetailByLogId(logMasterId);
		message.setSuccess(true);
		message.setTotalProperty(details.size());
		message.setRoot(details);
		return getJsonFromObj(message);
	}
}
