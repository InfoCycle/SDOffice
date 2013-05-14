package com.info.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.DateUtil;
import com.info.domain.TAppTreeCode;
import com.info.domain.TAppTreeCodeType;
import com.info.domain.ViewCodeTree;
import com.info.service.SystemTreeCodeService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemTreeCodeController extends BaseController {
	@Autowired
	SystemTreeCodeService treeCodeService;
	
	/**
	 * action 请求类型，目的为了减少MVC映射
	 * @author jibinbin
	 *1：获取代码类型 ;/SystemTreeCodeService/1
	 *2：根据类型获取代码;/SystemTreeCodeService/2?typeId=6
	 *3：保存代码类型 ;/SystemTreeCodeService/3
	 *4：保存代码;/SystemTreeCodeService/4
	 *5：获取所有代码;/SystemTreeCodeService/5
	 *6:根据类型获取有效的代码；/SystemTreeCodeService/6?typeId=6
	 * @param action
	 * @param response
	 */
	@RequestMapping(value = "/system/SvrService/TreeCodeService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response) {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		switch (action) {
		case 1:
			Result = getTreeCodeType();
			break;
		case 2:
			Result = getTreeCodeByType();
			break;
		case 3:
			Result = saveTreeCodeType();
			break;
		case 4:
			Result = saveTreeCode();
			break;
		case 5:
			Result = getAllTreeCode();
			break;
		case 6:
			Result = getValidTreeCodeByType();
			break;
		case 7:
			Result = deleteTreeCode();
			break;
		case 8:
			Result = getAllTreeCodeForView();
			break;
		}
		// 输出响应json串

		writeJsonString(Result);
	}

	// 获取代码类型数据
	public String getTreeCodeType() {
		List<TAppTreeCodeType> list= treeCodeService.getAppTreeCodeTypes();
		message.setTotalProperty(list.size());
		message.setSuccess(true);
		message.setRoot(list);
		return getJsonFromObj(message);
	}

	// 获取代码数据
	public String getTreeCodeByType() {
		int typeId =getInt("typeId", 0);
		List<TAppTreeCode> listCodes = treeCodeService.getTreeCodeByType(typeId);
		message.setTotalProperty(listCodes.size());
		message.setSuccess(true);
		message.setRoot(listCodes);
		return getJsonFromObj(message);
	}

	// 保存代码类型数据
	public String saveTreeCodeType() {
		TAppTreeCodeType codeType = new TAppTreeCodeType();
		codeType.setFId(getInt("FId", 0));
		codeType.setFName(getString("FName", ""));
		codeType.setFContent(getString("FContent", ""));
		codeType.setFSort(getInt("FSort", 0));
		codeType.setFState(getInt("FState", 1));
		message.setSuccess(treeCodeService.SaveOrUpdate(codeType));
		return getJsonFromObj(message);
	}

	// 保存代码数据
	public String saveTreeCode() {
		TAppTreeCode code = new TAppTreeCode();
		code.setFId(getInt("FId", 0));
		code.setFParentId(getInt("FParentId", -1));
		code.setFkTreeCodeTypeId(getInt("fkTreeCodeTypeId", 0));
		code.setFCode(getString("FCode", ""));
		code.setFCodeText(getString("FCodeText", ""));
		code.setFSort(getInt("FSort", 0));
		code.setFState(getInt("FState", 1));
		code.setFCreateTime(DateUtil.getTime().toString());
		boolean flag = treeCodeService.SaveOrUpdate(code);
		message.setSuccess(flag);
		return getJsonFromObj(message);
	}

	// 获取所有代码数据

	public String getAllTreeCode() {
		Integer fkTreeCodeTypeId=getInt("fkTreeCodeTypeId", -1);
		List<TAppTreeCode> listCodes = treeCodeService.getAllTreeCode(fkTreeCodeTypeId);
		if(listCodes.size()>0)
		{
			message.setTotalProperty(listCodes.size());
			message.setSuccess(true);
			message.setRoot(listCodes);
		}
		else{
			message.setSuccess(true);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}
	
	//获得代码数据，通过fkTreeCodeTypeId
	public String getAllTreeCodeForView(){
		Integer fkTreeCodeTypeId=getInt("fkTreeCodeTypeId", -1);
		List<ViewCodeTree> listCodes = treeCodeService.getAllTreeCodeForView(fkTreeCodeTypeId);
		if(listCodes.size()>0)
		{
			message.setTotalProperty(listCodes.size());
			message.setSuccess(true);
			message.setRoot(listCodes);
		}
		else{
			message.setSuccess(true);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}
	//获取有效代码
	public String getValidTreeCodeByType(){
		int typeId =getInt("typeId", 0);
		List<TAppTreeCode> listCodes = treeCodeService.getValidTreeCodeByType(typeId);
		message.setTotalProperty(listCodes.size());
		message.setSuccess(true);
		message.setRoot(listCodes);
		return getJsonFromObj(message);
	}
	
	public String deleteTreeCode(){
		int fid = getInt("FId", -1);
		message.setSuccess(treeCodeService.deleteTreeCode(fid));
		return getJsonFromObj(message);
	}
	
	@RequestMapping(value = "/system/SvrService/TreeCodeServiceGetCode/{pid}")
	public void querySystemTreeFoPID(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		String result = "[";
		// ResultMessage message = new ResultMessage();		
		List<ViewCodeTree> objlist = treeCodeService.GetListSystemTreeCodeForPID(pid);
		// 构造树				
		for (int i = 0; i < objlist.size(); i++) {					
			result += "{id:'" + objlist.get(i).getFId().toString()
					+ "',parent:'"+ objlist.get(i).getFParentId().toString() + "',text:'"
					+ objlist.get(i).getFCodeText() + "',leaf:" 
					+(objlist.get(i).getIsleft() ? "true": "false")
					+",FCode:'"+objlist.get(i).getFCode()
					+"',fkTreeCodeTypeId:"+objlist.get(i).getFkTreeCodeTypeId().toString()+
					",FSort:"+objlist.get(i).getFSort().toString()+",FState:"+objlist.get(i).getFState().toString()+
					",FCreateTime:'"+objlist.get(i).getFCreateTime()+"'"
					+(i<objlist.size()-1 ? "},":"}");
		}		
		result += "]";
		writeJsonString(result);
	}
}
