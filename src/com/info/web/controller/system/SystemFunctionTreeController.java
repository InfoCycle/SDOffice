package com.info.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.service.SystemOrgUserFunctionService;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemFunctionTreeController extends BaseController {
	@Autowired
	SystemOrgUserFunctionService groupService;
	/**
	 * 从权限管理类里面把取菜单树的函数移出到一个新类里面
	 * 在以前的类里会出现无法输出数据,暂时无法找到原因
	 * ？？？？
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/system/SvrService/FunctionTree/getTree/")
	public void getFunctionTree(HttpServletResponse response) {
		CurrentResponse =response;
		List functionTree= groupService.getFunctionTreeData();
		writeJsonString(getJsonFromArray(functionTree));
	}
}
