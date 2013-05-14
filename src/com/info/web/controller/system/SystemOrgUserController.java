package com.info.web.controller.system;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.EncryptUtil;
import com.info.domain.TAppUser;
import com.info.service.SystemOrgUserFunctionService;
import com.info.service.SystemOrgUserService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class SystemOrgUserController extends BaseController {
	@Autowired
	SystemOrgUserService userService;
	
	@Autowired
	SystemOrgUserFunctionService groupService;
	@RequestMapping(value = "/system/SvrService/UserService/{action}")
	public void initService(@PathVariable("action") Integer action,
			HttpServletResponse response) {
		CurrentResponse =response;
		message = new ResultMessage();
		String resultString="";
		switch (action) {
		case 1:
			resultString = getUserData();	
			break;
		case 2:
			resultString = saveOrUpdate();
			break;
		case 3:
			resultString = delete();
			break;
		case 4:
			resultString =getOrgUserTree();
			break;
		case 5:	//jibinbin add 2012-6-2
			resultString = getUserForIDData();
			break;
		}
		writeJsonString(resultString);
	}
	
	private String getUserData() {
		String txt_name =getString("txt_name", null);
		Integer fOrgId= getInt("FOrgId", 0);
		List<TAppUser> users=null;
		if(txt_name!=null){
			users = userService.findUserByName(txt_name);
		}else {
			users = userService.getUserByOrgId(fOrgId);
		}
		message.setSuccess(true);
		message.setRoot(users);
		message.setTotalProperty(users.size());
		return getJsonFromObj(message);
	}
	
	private String getUserForIDData(){		
		Integer fId= getInt("FId", 0);
		List<TAppUser> users=null;		
		users = userService.findUserByFId(fId);		 
		message.setSuccess(true);
		message.setRoot(users);
		message.setTotalProperty(users.size());
		return getJsonFromObj(message);
	}
	private String saveOrUpdate() {
		Integer FId=getInt("FId", 0);
		Integer fOrgId= getInt("FOrgId", 0);
		String pwdString = getString("FPassword", "");
		if(!(pwdString.length()>26)){
			pwdString = EncryptUtil.md5Digest(pwdString);
		}
		TAppUser user=new TAppUser();
		user.setFId(FId);
		user.setFName(getString("FName", ""));
		user.setFUnitStation(getInt("FUnitStation", -1));
		user.setFUserCode(getString("FUserCode", ""));
		user.setFSort(getInt("FSort", 0));
		user.setFState(getInt("FState", 1));
		user.setFPassword(pwdString);
		user.setFCardid(getString("FCardid", ""));
		user.setFPhone(getString("FPhone", ""));
		user.setFCreateTime(new Timestamp((new Date()).getTime()));
		TAppUser uAppUser =userService.saveOrUpdate(user, fOrgId);
		message.setSuccess(uAppUser==null?false:true);
		message.setRoot(uAppUser);
		return getJsonFromObj(message);
	}
	
	private String delete() {
		boolean flag=userService.delete(getInt("FId", 0));
		message.setSuccess(flag);
		return getJsonFromObj(message);
	}
	
	@SuppressWarnings("rawtypes")
	private String getOrgUserTree() {
		List orgUserTree=groupService.getOrgUserTreeData();
		return getJsonFromArray(orgUserTree);
	}
}
