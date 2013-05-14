package com.info.web.controller.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TAppGroup;
import com.info.service.SystemOrgUserFunctionService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Scope("prototype")
@Controller
public class SystemOrgUserFunctionController extends BaseController{
	
	@Autowired
	SystemOrgUserFunctionService groupService;
	/**
	 * 权限组维护
	 * @author liwx
	 * @param action 1:获取用户组
	 * @param action 2:获取功能菜单树
	 * @param action 3:获取机构人员树
	 * @param action 4:保存用户组数据
	 * @param action 5:保存用户组权限数据
	 * @param action 6:删除用户组
	 * @param action 7：获取用户组对应的权限及人员
	 */
	@RequestMapping(value="/system/SvrService/OrgUserFunction/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response){
		CurrentResponse = response;
		message =new ResultMessage();
		String resultString ="";
		switch (action) {
		case 1:
			resultString =getAppGroup();
			break;
		case 2:
			resultString =getFunctionTree();
			break;
		case 3:
			resultString =getOrgUserTree();
			break;
		case 4:
			resultString =saveOrUpdate();
			break;
		case 5:
			resultString =insertUserFunctionGroup();
			break;
		case 6:
			resultString =delete();
			break;
		case 7:
			resultString =getUserFunctionByGroupId();
			break;
		}
		writeJsonString(resultString);
	}
	
	private String getAppGroup() {
		List<TAppGroup> groups=groupService.getAppGroupList();
		message.setSuccess(true);
		message.setRoot(groups);
		message.setTotalProperty(groups.size());
		return getJsonFromObj(message);
	}
	@SuppressWarnings("rawtypes")
	private String getFunctionTree() {
		List functionTree= groupService.getFunctionTreeData();
		return getJsonFromArray(functionTree);
	}
	@SuppressWarnings("rawtypes")
	private String getOrgUserTree() {
		List orgUserTree=groupService.getOrgUserTreeData();
		return getJsonFromArray(orgUserTree);
	}
	private String saveOrUpdate() {
		TAppGroup appGroup = new TAppGroup();
		appGroup.setFId(getInt("FId", 0));
		appGroup.setFName(getString("FName", ""));
		appGroup.setFContent(getString("FContent", ""));
		appGroup.setFSort(getInt("FSort", 0));
		boolean flag=groupService.saveOrUpdate(appGroup);
		message.setSuccess(flag);
		return getJsonFromObj(message);
	}
	private String delete() {
		Integer FId=getInt("FId", 0);
		boolean flag = groupService.deleteAppGroup(FId);
		message.setSuccess(flag);
		return getJsonFromObj(message);
	}
	
	//保存用户组对应的人员、功能
	private String insertUserFunctionGroup() {
		Integer groupId=getInt("FId", 0);
		String functionIdList=getString("functionIdList", "");
		String userIdList=getString("typeIdList", "");
		List<String> functionList=Arrays.asList(functionIdList.split(","));
		List<String> userList=Arrays.asList(userIdList.split(","));
		boolean flag = groupService.insertUserFunctionGroup(groupId, functionList, userList);
		message.setSuccess(flag);
		return getJsonFromObj(message);
	}
	
	//获取权限组对应的人员、功能
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getUserFunctionByGroupId() {
		Integer groupId=getInt("userGroupId", 0);
		String functionIdString=groupService.getFunctionByGroupId(groupId);
		String userIdString=groupService.getOrgUserByGroupId(groupId);
		Map<String, Object> map = new HashMap();
		map.put("success", true);
		map.put("functionId", functionIdString);
		map.put("typeid", userIdString);
		return getJsonFromObj(map);
	}
}
