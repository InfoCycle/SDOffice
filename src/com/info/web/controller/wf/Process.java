package com.info.web.controller.wf;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TWfProcessType;
import com.info.service.WfProcessService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
/**
 * 业务过程处理
 */
@Scope("prototype")
@Controller
public class Process extends BaseController {
	@Autowired
	WfProcessService service;
	
	@RequestMapping(value = "/wf/process/{action}/")
	public void ProcessAction(@PathVariable("action") int action,
			HttpServletResponse response){
		CurrentResponse =response;
		message = new ResultMessage();
		String resultString="";
		switch (action) {
		case 1://根据岗位获取过程类型数据列表
			resultString =getProcessTypeList();
			break;
		case 2://新建业务过程列表
			resultString =addNewProcess();
			break;
		case 3://获取全部业务过程类型
			resultString =getProcessTypes();
			break;
		}
		writeJsonString(resultString);
	}
	
	private String getProcessTypeList() {
		try {
			List<TWfProcessType> list=service.getTWfProcessTypes();
			message.setSuccess(list.size()>0);
			//<li onclick="javascript:addProcess(90,'下达项目任务书','../task/Tasks.html');">下达项目任务书</li>
			if(list.size()==0){
				message.setMessage("此登录用户未分配新建业务权限！");
			}
			String formatsString ="<li onclick=\"javascript:addProcess(''{0}'',''{1}'',''{2}'');\">{3}</li>";
			MessageFormat format =new MessageFormat(formatsString);
			String HtmlString;		
			StringBuilder sb=new StringBuilder();
			sb.append("<div class=\"lines\"></div>");
			for(TWfProcessType processType:list){
				HtmlString=format.format(new Object[]{
				        processType.getFId().toString(),
						processType.getFTypeName(),
						processType.getFFormUrl(),
						processType.getFTypeName()
					});
				sb.append(HtmlString);
			}
			if(list.size()<12){//此处补足12行,增加显示效果
				int count = 12-list.size();
				for(int i=0;i<count;i++){
					sb.append("<li>&nbsp;</li>");
				}
			}
			message.setRoot(sb.toString());			
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}		
		return getJsonFromObj(message);
	}
	
	private String addNewProcess() {
		try {
			int typeId =getInt("typeid", -1);
			int processId = service.addNewProcess(typeId);
			int activeId = service.getSingleActiveId(processId);
			message.setSuccess(true);
			//暂时如此处理
			message.setRoot(processId);
			message.setMessage(String.valueOf(activeId));
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
	
	private String getProcessTypes() {
		try {
			List<TWfProcessType> list=service.getProcessTypes();
			message.setSuccess(list.size()>0);
			message.setRoot(list);
		} catch (Exception e) {
			message.setSuccess(false);
			e.printStackTrace();
		}
		return getJsonFromObj(message);
	}
}
