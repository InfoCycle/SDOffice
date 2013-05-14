package com.info.web.controller.buss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.StringUtil;
import com.info.domain.TContractRelevanceTask;
import com.info.domain.ViewContractTree;
import com.info.domain.ViewTask;
import com.info.domain.ViewTaskTree;
import com.info.service.BussContractRelevanceTaskService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
@Scope("prototype")
@Controller
public class BussContractRelevanceTaskController extends BaseController {
	@Autowired
	BussContractRelevanceTaskService contractRelevanceTaskService;
	
	ResultMessage message;
	public String result = "";
	
	@RequestMapping(value = "/Buss/ContractRelevanceTaskService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();		
		switch (action) {
		case 1:		
			Result = save();
			break;	
		case 2:		
			Result = TaskTree();
			break;		
		case 3:		
			Result = contractRelevanceTaskList();
			break;		
		}
		// 输出响应json串
		writeJsonString(Result);
	}

	private String contractRelevanceTaskList() {
		Integer contractid = getInt("contractid", 0);
		if(contractid > 0){
			List<TContractRelevanceTask> list = contractRelevanceTaskService.contractRelevanceTaskList(contractid);
			if(null !=list && list.size() > 0){
				message.setSuccess(true);
				message.setRoot(list);
				message.setTotalProperty(list.size());
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}

	private String TaskTree() {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		List<ViewTask> taskTree = contractRelevanceTaskService.GetTaskTree();
		if (taskTree.size() >= 0) {
			for(int i=0;i<taskTree.size();i++){
				Map<String, Object> mp = new HashMap<String, Object>();
				ViewTask tree = (ViewTask)taskTree.get(i);
				mp.put("id", tree.getFId());
				mp.put("name", tree.getFName());
				mp.put("isTask", tree.getIsTask());
				mp.put("FTaskNumbers", tree.getFTaskNumbers());
				mp.put("FContractYjCharge", tree.getFContractYjCharge());//合同预计收费
				mp.put("FIndustryCategoryName", tree.getFIndustryCategoryName());//行业类别
				mp.put("FDepartmentName", tree.getFDepartmentName());//承接部门
				mp.put("FYjstartTime", tree.getFYjstartTime());//预计开始时间
				mp.put("FYjfinishTime", tree.getFYjfinishTime());//预计完成时间s
				mp.put("FGivePersonName", tree.getFGivePersonName());//下达人
				mp.put("FGiveTime", tree.getFGiveTime());//下达时间
				mp.put("FDeptMgrName", tree.getFDeptMgrName());//部门经理
				mp.put("FReceivingTaskTime", tree.getFReceivingTaskTime());//接收时间
				mp.put("FProjMgrName", tree.getFProjMgrName());//主项目经理
				mp.put("FProjMgrViceName", tree.getFProjMgrViceName());//副项目经理
				mp.put("_parentId", tree.getFParentId());
				mp.put("taskid", tree.getFTaskId());
				list.add(mp);
			}
			map.put("rows", list);
			map.put("total", taskTree.size());
		} else {
			map.put("rows", new ArrayList<ViewTask>());
			map.put("total", 0);
		}
		return getJsonFromObj(map);
	}

	private String save() {
		Integer contractid = getInt("contractid", 0);
		String taskids = getString("taskids", "");
		if(contractid > 0 && !"".equals(taskids)){
			String[] ids =StringUtil.split(taskids, ",");
			if(contractRelevanceTaskService.saveContractRelevanceTask(contractid, ids)){
				message.setSuccess(true);
				message.setRoot(ids);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}
	
	@RequestMapping(value = "/GetTree/ContractTree/{pid}")
	public void getContractTreeForPID(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		String result = "[";
		// ResultMessage message = new ResultMessage();		
		List<ViewContractTree> objlist = contractRelevanceTaskService.GetListContractTreeForPID(pid);
		// 构造树		
		String iconCls="";	
		for (int i = 0; i < objlist.size(); i++) {
			iconCls="";
			if(objlist.get(i).getIsleft())
				if(objlist.get(i).getIsContract()==1)
				   iconCls=",iconCls:\"isContract-iconCls\"";
			result += "{id:'" + objlist.get(i).getFId().toString()
					+ "',parent:'"
					+ objlist.get(i).getFParentId().toString() + "',text:'"
					+ objlist.get(i).getFName() + "',leaf:" + 
					(objlist.get(i).getIsleft() ? "true": "false")+
					",isContract:'"+objlist.get(i).getIsContract()+"',FClientId:"+
					objlist.get(i).getFContractId().toString()+iconCls+
					(i<objlist.size()-1 ? "},":"}");
		}		
		result += "]";
		writeJsonString(result);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/GetTree/ContractAllTree/{pid}")
	public void getContractTree(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		String treeJson = ""; 
		List<ViewContractTree> objfirstlist = new ArrayList();
		List<ViewContractTree> list = contractRelevanceTaskService.GetListContractTree();
		// 构造树		
		for (ViewContractTree obj : list) {
			if (obj.getFParentId().toString().equals("0")) {
				objfirstlist.add(obj);
			}
		}
		List tree = buildContractTree(list,objfirstlist);
        treeJson= getJsonFromArray(tree);
        writeJsonString(treeJson);
	}
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private List buildContractTree(List<ViewContractTree> objList,
				List<ViewContractTree> objparentList) {
			List list = new ArrayList();
			for (int i = 0; i < objparentList.size(); i++) {
				Map<String, Object> map = new HashMap();
				ViewContractTree obj = (ViewContractTree) objparentList.get(i);
				// 1、为叶子节点
				if (obj.getIsleft()) {
					map.put("id", obj.getFId().toString());
					//map.put("state", "closed");
					map.put("text", obj.getFName());
					map.put("attributes", obj.getIsContract());
					map.put("iconCls", "icon-100");
					// map.put("checked", false);
					list.add(map);
				} else { // 2、有下级节点
					map.put("id", obj.getFId().toString());
					//map.put("state", "open");
					map.put("text", obj.getFName());
					map.put("attributes", obj.getIsContract());
					map.put("iconCls", "icon-200");
					//map.put("checked", false);
					map.put("children",buildContractTree(objList,findChildCode(objList, obj.getFId())));
					list.add(map);
				}
			}
			return list;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private List<ViewContractTree> findChildCode(List<ViewContractTree> objList,
				Integer id) {
			List<ViewContractTree> objlist = new ArrayList();
			for (ViewContractTree obj : objList) {
				if (obj.getFParentId() == id)
					objlist.add(obj);
			}
			return objlist;
		}

	@RequestMapping(value = "/GetTree/TaskTree/{pid}")
	public void getTaskTreeForPID(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		String result = "[";
		// ResultMessage message = new ResultMessage();		
		List<ViewTaskTree> objlist = contractRelevanceTaskService.GetListTaskTreeForPID(pid);
		// 构造树		
		String iconCls="";	
		for (int i = 0; i < objlist.size(); i++) {
			iconCls="";
			if(objlist.get(i).getIsleft())
				if(objlist.get(i).getIsTask()==1)
				   iconCls=",iconCls:\"isTask-iconCls\"";
			result += "{id:'" + objlist.get(i).getFId().toString()
					+ "',parent:'"+ objlist.get(i).getFParentId().toString() + 
					"',text:'"+ objlist.get(i).getFName() + 
					"',leaf:" + (objlist.get(i).getIsleft() ? "true": "false")+
					",IsTask:'"+objlist.get(i).getIsTask()+
					"',checked:"+true+
					",FTaskId:"+objlist.get(i).getFTaskId().toString()+iconCls+
					(i<objlist.size()-1 ? "},":"}");
		}		
		result += "]";
		writeJsonString(result);
	}
}
