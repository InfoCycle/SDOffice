package com.info.web.controller.wf;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.StringUtil;
import com.info.domain.TCheckReview;

import com.info.domain.TProjectResultsFile;
import com.info.domain.TTask;
import com.info.domain.ViewTaskItem;
import com.info.service.BussResultsFileService;
import com.info.service.WfBusCenterTaskItemService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BusCenterTaskItem extends BaseController{
	int taskId;
	/*"<li onclick=\"javascript:showTaskItem(''{0}'',''{1}'',''{2}'',''{3}'',''{4}''''{5}'');\">"
	+"<table class=\"liTable\"><tr><td class=\"tdTitle\">{6}</td><td class=\"tdother\">{7}</td>"
	+"<td class=\"tddate\">{8}</td><td class=\"tdother\">{9}</td></tr></table></li>"*/
	String formatsString="<li>"
			+"<table class=\"liTable\">"
			+"<tr onclick=\"javascript:showTaskItem(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''{6}'');\">"
			+"<td class=\"tdTitle\">{7}</td><td class=\"tdother\">{8}</td>"
			+"<td class=\"tddate\">{9}</td><td class=\"tdother\">{10}</td></tr></table></li>";
		
	//String formatsString ="<li onclick=\"javascript:showTaskItem(''{0}'',''{1}'',''{2}'',''{3}'');\">{4}</li>";
	
	MessageFormat format =new MessageFormat(formatsString);
	String HtmlString;		
	
	@Autowired
	WfBusCenterTaskItemService taskItemService;
	
	@Autowired
	BussResultsFileService service;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/wf/buCenter/taskItem/")
	public void getTaskItem(HttpServletResponse response){
		CurrentResponse = response;
		taskId = getInt("taskId", -1);
		message = new ResultMessage();
		Map<String, Object> map = new HashMap();
		try {	
			map.put("beginDateTask", getTaskBeginDate());
			//项目任务书
			map.put("task", getTaskItem(taskId,10001));
			//项目实施计划
			map.put("ImplementPlan", getTaskItem(taskId,10002));
			//项目过程情况记录
			map.put("ProjectProcess", getTaskItem(taskId,10003));
			//项目检查记录
			map.put("ProjectCheck", getTaskItem(taskId,10004));
			//项目重大事项报告
			map.put("MajorMattersReport", getTaskItem(taskId,10005));
			//检查复核申报告
			map.put("CheckReview", getTaskItem(taskId,10006));
			//项目成果文件
			map.put("CheckReviewFile",getDownloadFile(taskId));
			//项目成果提交时间
			map.put("endDateTask", getReviewResultTime(taskId));
			//项目考评打分
			map.put("ProjectAppraisalScoure", getTaskItem(taskId,10007));
			//顾客评价表
			map.put("CustomerEvaluation", getTaskItem(taskId,10008));
			//效益工资提取表
			map.put("EfficiencyWage", getTaskItem(taskId,10009));
			
			map.put("taskBill", gettaskBill(taskId));
			
			message.setSuccess(true);
			message.setRoot(map);
		} catch (Exception e) {			
			e.printStackTrace();
			message.setSuccess(false);
		}
		writeJsonString(getJsonFromObj(message));
	}
	private String getTaskBeginDate() {
		TTask task = taskItemService.getTaskById(taskId);
		if(null!=task.getFYjstartTime()){
			return task.getFYjstartTime().substring(0, 10);
		}else{
			return "";
		}		
	}
	/**
	 * 项目成果时间
	 * @param taskId
	 * @return
	 */
	private String getReviewResultTime(int taskId) {
		String endDateTask="";
		List<TCheckReview>  items=taskItemService.getCheckReviewByTaskId(taskId);
		if(items.size()>0){
			TCheckReview item=items.get(0);
			if(!StringUtil.isEmpty(item.getFReviewManTime())){
				endDateTask = item.getFReviewManTime().substring(0, 10);
			}
		}
		return endDateTask;
	}
	private String getTaskItem(int taskId,int typeId) {
		StringBuilder sb=new StringBuilder();
		sb.append("");
		HtmlString ="";
		List<ViewTaskItem> items = taskItemService.getTaskItemById(taskId, typeId);
		for (ViewTaskItem item : items) {
			HtmlString=format.format(new Object[]{
			        String.valueOf(taskItemService.getTaskAction(item.getFProcessId(), item.getFState())),
			        String.valueOf(item.getFProcessId()),
			        String.valueOf(item.getFFormPkid()),
					//以下代码有问题
					String.valueOf(taskItemService.getTaskActiveId(item.getFProcessId())),
					item.getFState(),
					item.getFFormUrl(),
					item.getFNumbers(),
					item.getFNumbers(),
					item.getFStateText(),
					item.getFCreateTime().substring(0, 10),
					item.getFCurrentUserName()
				});
			sb.append(HtmlString);
		}
		return sb.toString();
	}
	/**
	 * 项目成果文件
	 * @param taskId
	 * @return
	 */
	private String getDownloadFile(int taskId) {
		String formatDownFile="<li><a href=''/Buss/ResultsLoadFileService/LoadFile/{0}''>{1}</a>";
		MessageFormat formatFile =new MessageFormat(formatDownFile);
		String FileHtmlString;
		StringBuilder sb=new StringBuilder();
		sb.append("");
		int fkCheckReviewId=0;
		List<TCheckReview>  items=taskItemService.getCheckReviewByTaskId(taskId);
		if(items.size()>0){
			fkCheckReviewId = items.get(0).getFId();
			List<TProjectResultsFile> objList = service.GetProjectResultsFileList(fkCheckReviewId,"asc",null,null);
			for (TProjectResultsFile obj : objList) {
				FileHtmlString = formatFile.format(new Object[]{
						obj.getFId(),
						obj.getFFileName()
				});
				sb.append(FileHtmlString);
			}
		}
		return sb.toString();
	}
	/**
	 * 项目收费
	 * @param taskId
	 * @return
	 */
	private String gettaskBill(int taskId) {
		String billHTML="<span>{0}:{1} 元；已收费:{2} 元；待收费：{3} 元</span>";
		MessageFormat billFormat =new MessageFormat(billHTML);
		String HtmlString;
		StringBuilder sb=new StringBuilder();
		sb.append("");
		List<Object[]> items= taskItemService.getBillByTaskId(taskId);
		for (Object[] objects : items) {
			HtmlString = billFormat.format(new Object[]{
					objects[0].equals(0)?"项目应收费":"项目预计收费",
					objects[0].equals(0)?objects[1]:objects[0],
					objects[2],
					objects[3]
			});
			sb.append(HtmlString);
		}
		return sb.toString();
	}
}
