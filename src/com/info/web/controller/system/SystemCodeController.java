package com.info.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.DateUtil;
import com.info.domain.TAppCode;
import com.info.domain.TAppCodeType;
import com.info.service.SystemCodeService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemCodeController extends BaseController {
	
	@Autowired
	SystemCodeService codeService;
	
	/**
	 * action 请求类型，目的为了减少MVC映射
	 * @author liwx
	 *1：获取代码类型 ;/SystemCodeService/1
	 *2：根据类型获取代码;/SystemCodeService/2?typeId=6
	 *3：保存代码类型 ;/SystemCodeService/3
	 *4：保存代码;/SystemCodeService/4
	 *5：获取所有代码;/SystemCodeService/5
	 *6:根据类型获取有效的代码；/SystemCodeService/6?typeId=6
	 * @param action
	 * @param response
	 */
	@RequestMapping(value = "/system/SvrService/CodeService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response) {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		switch (action) {
		case 1:
			Result = getCodeType();
			break;
		case 2:
			Result = getCodeByType();
			break;
		case 3:
			Result = saveCodeType();
			break;
		case 4:
			Result = saveCode();
			break;
		case 5:
			Result = getAllCode();
			break;
		case 6:
			Result = getValidCodeByType();
			break;
		}
		// 输出响应json串		writeJsonString(Result);
	}

	// 获取代码类型数据
	public String getCodeType() {
		List<TAppCodeType> list= codeService.getAppCodeTypes();
		message.setTotalProperty(list.size());
		message.setSuccess(true);
		message.setRoot(list);
		return getJsonFromObj(message);
	}

	// 获取代码数据
	public String getCodeByType() {
		int typeId =getInt("typeId", 0);
		List<TAppCode> listCodes = codeService.getCodeByType(typeId);
		message.setTotalProperty(listCodes.size());
		message.setSuccess(true);
		message.setRoot(listCodes);
		return getJsonFromObj(message);
	}

	// 保存代码类型数据
	public String saveCodeType() {
		TAppCodeType codeType = new TAppCodeType();
		codeType.setFId(getInt("FId", 0));
		codeType.setFName(getString("FName", ""));
		codeType.setFContent(getString("FContent", ""));
		codeType.setFSort(getInt("FSort", 0));
		codeType.setFState(getInt("FState", 1));
		message.setSuccess(codeService.SaveOrUpdate(codeType));
		return getJsonFromObj(message);
	}

	// 保存代码数据
	public String saveCode() {
		TAppCode code = new TAppCode();
		code.setFId(getInt("FId", 0));
		code.setFkCodeTypeId(getInt("fkCodeTypeId", 0));
		code.setFCode(getString("FCode", ""));
		code.setFCodeText(getString("FCodeText", ""));
		code.setFSort(getInt("FSort", 0));
		code.setFState(getInt("FState", 1));
		code.setFCreateTime(DateUtil.getTime());
		boolean flag = codeService.SaveOrUpdate(code);
		message.setSuccess(flag);
		return getJsonFromObj(message);
	}

	// 获取所有代码数据
	public String getAllCode() {
		List<TAppCode> listCodes = codeService.getAllCode();
		if(listCodes.size()>0)
		{
			message.setTotalProperty(listCodes.size());
			message.setSuccess(true);
			message.setRoot(listCodes);
		}
		else{
			message.setSuccess(false);
			message.setRoot(null);
		}
		return getJsonFromObj(message);
	}
	//获取有效代码
	public String getValidCodeByType(){
		int typeId =getInt("typeId", 0);
		List<TAppCode> listCodes = codeService.getValidCodeByType(typeId);
		message.setTotalProperty(listCodes.size());
		message.setSuccess(true);
		message.setRoot(listCodes);
		return getJsonFromObj(message);
	}
	
}
