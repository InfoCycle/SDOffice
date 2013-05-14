package com.info.web.controller.system;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.*;
import com.info.common.util.DataOption;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TAppUser;
import com.info.service.SystemIndexService;
import com.info.web.CurrentUser;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Scope("prototype")
@Controller
public class SystemUserLoginController extends BaseController {
	@Autowired
	SystemIndexService service;

	@RequestMapping(value = "/user/login/")
	public void userLogin(HttpServletResponse response) {
		CurrentResponse = response;
		try {
			result = userlogin();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		writeJsonString(result);
	}

	// 用户登录验证
	private String userlogin() {
		message = new ResultMessage();
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
				User.setDataOption(DataOption.getOption(
						service.getDataOption(user.getFUnitStation())
				));
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

	private void assignUserInfo(CurrentUser object) {
		SystemCurrentUser.setCurrentUser(object);
	}
	/**
	 * @Description	: 用户注销，清空session
	 * @Author		: liwx
	 * @Date		: 2013-03-21 20-01
	 */
	@RequestMapping(value = "/user/logout/")
	public void logout(HttpServletResponse response){
		CurrentResponse = response;
		message = new ResultMessage();
		try {
			//CurrentRequest.getSession().invalidate();
			CurrentRequest.getSession().removeAttribute("CurrentUser");
			message.setSuccess(true);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
			message.setSuccess(false);
		}
		writeJsonString(getJsonFromObj(message));
	}
	@RequestMapping(value = "/user/login/captcha/")
	public void captcha(HttpServletResponse response) {
		CurrentResponse = response;
		Properties props = new Properties();
		props.put("kaptcha.border", "no");
		//props.put("kaptcha.border.color", "105,179,90");
		props.put("kaptcha.textproducer.char.string","ce23456789fknuxp");
		props.put("kaptcha.textproducer.font.color", "black");
		props.put("kaptcha.image.width", "140");
		props.put("kaptcha.image.height", "34");
		//props.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
		//props.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
		props.put("kaptcha.textproducer.font.size", "30");		
		props.put("kaptcha.session.key", "validateCode");
		props.put("kaptcha.textproducer.char.length", "4");
		props.put("kaptcha.textproducer.font.names", "Arial");

		Config config = new Config(props);
		Producer kaptchaProducer = config.getProducerImpl();
		String sessionKeyValue = config.getSessionKey();
		String sessionKeyDateValue = config.getSessionDate();
		ImageIO.setUseCache(false);
		String captchaText = kaptchaProducer.createText();
		captchaText = captchaText.toUpperCase();
		setSession(sessionKeyValue, captchaText);
		setSession(sessionKeyDateValue,new Date());
		BufferedImage bi = kaptchaProducer.createImage(captchaText);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeByte(out.toByteArray());		
	}
}
