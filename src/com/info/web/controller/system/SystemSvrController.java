package com.info.web.controller.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.SystemCurrentUser;
import com.info.domain.TAppUser;
import com.info.service.SystemIndexService;
import com.info.web.CurrentUser;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
import com.info.web.controller.util.ValidCodeImage;

@Controller
public class SystemSvrController extends BaseController {

	@Autowired
	SystemIndexService service;

	// Action:由client ajax传入的一个状态，目的是减少MVC请求地址的映射
	// 1:用户登录
	// 2:生成随机验证码
	// 3:大厅功能菜单
	// 根据登录的用户ID获取权限菜单
	@RequestMapping(value = "/system/SvrService/AppIndex/{Action}/")
	public void getService(@PathVariable("Action") Integer Action,
			HttpServletResponse response) {
		CurrentResponse = response;
		String Result = "";
		switch (Action) {
		case 1:
			Result = userlogin();
			break;
		case 2:
			validateImgCode();
			break;
		case 3:
			Result = getPermissionMenu();
			break;
		case 4:
			Result = checkUserLogin();
			break;
		case 5:
			Result = updatePWD();
			break;		
		}
		// 图片用输出流输出
		if (Action != 2)
			this.writeJsonString(Result);
	}

	// 用户登录验证
	private String userlogin() {
		ResultMessage message = new ResultMessage();
		String UserCode = getString("usercode", "");
		String Userpwd = getString("userpwd", "");
		String checkcode = getString("checkcode", "");
		if (!checkcode.toUpperCase().equals(
				getSession("validateCode").toString())) {
			message.setSuccess(false);
			message.setMessage("验证码输入错误！");
			return getJsonFromObj(message);
		}
		int c = service.findUserByCode(UserCode);
		if (c == 0) {
			message.setSuccess(false);
			message.setMessage("用户编码输入错误！");
			return getJsonFromObj(message);
		}
		TAppUser user = (TAppUser) service.findUserByCodeAndPwd(UserCode,
				Userpwd);
		if (user != null) {
			if (user.getFState() == 0) {
				message.setSuccess(false);
				message.setMessage("该用户已经被注销！");
			} else {
				message.setSuccess(true);
				message.setMessage("登录成功！");
				CurrentUser User = new CurrentUser();
				User.setUserID(user.getFId());
				User.setUserName(user.getFName());
				int orgId = service.getOrgIdByUserId(user.getFId());
				User.setUserOrgID(orgId);
				User.setUserOrgName(service.getOrgNameById(orgId));
				String UserGroupID = service.getGroupIDByUserID(user.getFId());
				if (UserGroupID.equals("-1")) {
					message.setSuccess(false);
					message.setMessage("该用户还未分配使用权限！");
				}
				User.setUserGroupID(UserGroupID);
				User.setLoginClientIP(getRemoteAddress());
				User.setLoginDate(new Date());
				User.setUnitStation(user.getFUnitStation());
				this.setSession("CurrentUser", User);
				message.setRoot(User);
				assignUserInfo(User);
			}
		} else {
			message.setSuccess(false);
			message.setMessage("用户密码错误！");
		}
		return getJsonFromObj(message);
	}

	// 生成验证码图片
	private void validateImgCode() {
		try {
			ValidCodeImage code = new ValidCodeImage();
			code.validCodeImage();
			setSession("validateCode", code.ValidCode);
			this.writeByte(code.imageByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据登录用户的权限生成大厅功能菜单
	@SuppressWarnings("rawtypes")
	private String getPermissionMenu() {
		try {
			String UserGroupID = ((CurrentUser) getSession("CurrentUser"))
					.getUserGroupID();
			List functionMenu = service.getIndexTree(UserGroupID);
			return getJsonFromArray(functionMenu);
		} catch (Exception e) {
			return null;
		}
	}

	// 验证用户是否登录
	private String checkUserLogin() {
		ResultMessage message = new ResultMessage();
		try {
			CurrentUser User = (CurrentUser) getSession("CurrentUser");
			if (User == null) {
				message.setSuccess(false);
				message.setMessage("用户没有登录或登录超时！请登录");
			} else {
				message.setSuccess(true);
				message.setRoot(User);
				assignUserInfo(User);
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("用户没有登录或登录超时！请登录");
		}
		return getJsonFromObj(message);
	}
	//此处用法错误，无法获取正确的登录Session
	//不可以使用静态类存储登录状态。
	private void assignUserInfo(CurrentUser object) {
		SystemCurrentUser.setCurrentUser(object);
	}
	
	private String updatePWD() {
		ResultMessage message = new ResultMessage();
		int FId = getInt("FId", 0);
		String pwd=getString("pwd", "");		
		boolean flag=service.updateUserPwd(FId, pwd);
		if(flag){
			message.setSuccess(true);
			message.setMessage("密码修改成功！");
		}else{
			message.setSuccess(false);
			message.setMessage("密码修改失败！");
		}		
		return getJsonFromObj(message);
	}
}
