package com.info.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TAppReport;
import com.info.service.SystemReportService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemReportController extends BaseController {
	@Autowired
	SystemReportService reportService;
	/**
	 * @author liwx
	 * @param reportId 
	 * 		登记报表模板id
	 * @return 报名服务的域、报表模板路径
	 * 暂时未考虑模板参数？？？
	 */
	@RequestMapping(value="/system/SvrService/ReportService/{reportId}")
	public void getReportTemplate(@PathVariable("reportId") Integer reportId,
			HttpServletResponse response){
		CurrentResponse = response;
		String JsonData;
		Map<String,Object> reportMap = new HashMap<String, Object>();
		try {
			reportMap.put("success", true);
			reportMap.put("reportDomain", reportService.getReportDomain());
			reportMap.put("reportlet", reportService.getReportletById(reportId));
		} catch (Exception e) {
			reportMap.put("success", false);
			e.printStackTrace();
		}
		JsonData =getJsonFromObj(reportMap);
		writeJsonString(JsonData);
	}
	
	@RequestMapping(value="/system/SvrService/ReportService/get/")
	public void getTemplateData(HttpServletResponse response){
		CurrentResponse = response;
		String reportName=getString("txt_rname", "");
		start =getInt("start", 0);
		limit =getInt("limit", 20);
		message = new ResultMessage();
		List<TAppReport> list = reportService.getTemplate(start,limit,reportName);
		int count = reportService.getTotalCount();
		message.setSuccess(true);
		message.setTotalProperty(count);
		message.setRoot(list);
		writeJsonString(getJsonFromObj(message));
	}
	
	@RequestMapping(value="/system/SvrService/ReportService/save/")
	public void saveOrUpdate(HttpServletResponse response){
		CurrentResponse = response;
		TAppReport report= new TAppReport();
		report.setFId(getInt("FId", 0));
		report.setFReportName(getString("FReportName", ""));
		report.setFReportlet(getString("FReportlet", ""));
		message = new ResultMessage();
		try {
			reportService.saveOrUpdate(report);
			message.setSuccess(true);
		} catch (Exception e) {
			message.setSuccess(false);
		}
		writeJsonString(getJsonFromObj(message));
	}
	
	@RequestMapping(value="/system/SvrService/ReportService/delete/{id}")
	public void deleteReport(@PathVariable("id") int id,HttpServletResponse response){
		CurrentResponse = response;
		message = new ResultMessage();
		try {
			reportService.delete(id);
			message.setSuccess(true);
		} catch (Exception e) {
			message.setSuccess(false);
		}
		writeJsonString(getJsonFromObj(message));
	}
}
