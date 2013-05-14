package com.info.web.controller.buss;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.SystemCurrentUser;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.domain.TWfProcessSend;
import com.info.service.BussCommonService;
import com.info.web.CurrentUser;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussCommonController extends BaseController {
	
	@Autowired
	BussCommonService service;
	
	ResultMessage message;
	
	/**
	 * 
	 * @function:
	 * @data: 2013-2-5下午3:20:47
	 * @author jibinbin
	 * @param action
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/Buss/CommonService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response) throws Exception {
		CurrentResponse = response;
		String Result = null;		
		try {
			switch (action) {		
			case 1:				
				Result = getUserInfoAndActiveInfo();
				break;
			case 2:
				Result = getWfProcessForID();
				break;			
			}
			writeJsonString(Result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	/**
	 * 获得用户登录和当前活动的信息
	 * @function:
	 * @data: 2013-2-5下午3:15:49
	 * @author jibinbin
	 * @return
	 *
	 */
	private String getUserInfoAndActiveInfo(){
		message = new ResultMessage();
		Integer activeId=getInt("activeId", -1);
        String type=getString("Type", "-1");
		try {
			
			CurrentUser objCuser=SystemCurrentUser.getCurrentUser();
			if(type.equals("-1")){ //参数为空时默认正常流程
    			TWfProccessActive objProccessActive = service.getWfProccessActiveForActiveId(activeId);
    			Map<String, Object> result=new HashMap<String, Object>();			
    			if(null!=objCuser && null!=objProccessActive)
    			{
    				result.put("userID", objCuser.getUserID());
    				result.put("userName", objCuser.getUserName());
    				result.put("userOrgID", objCuser.getUserOrgID());
    				result.put("unitStation", objCuser.getUnitStation());
    				result.put("fAcceptUser", objProccessActive.getFAcceptUser());
    				result.put("fState", objProccessActive.getFState());
    				result.put("isAcceptance", objProccessActive.getFAcceptTime()==null?"0":"1");
    				message.setRoot(result);
    				message.setSuccess(true);
    				message.setMessage("获取数据成功。");
    			} else if(null==objProccessActive && null!=objCuser){
    			    result.put("userID", objCuser.getUserID());
                    result.put("userName", objCuser.getUserName());
                    result.put("userOrgID", objCuser.getUserOrgID());
                    result.put("unitStation", objCuser.getUnitStation());
                    result.put("fAcceptUser", 0);
                    result.put("fState", 2);
                    result.put("isAcceptance", 1);
                    message.setRoot(result);
                    message.setSuccess(true);
                    message.setMessage("获取数据成功。");
    			}
			}else if(type.equals("1")){ //type:1 抄送
			    TWfProcessSend objProccessSend = service.getWfProcessSendForFId(activeId);
                Map<String, Object> result=new HashMap<String, Object>();           
                if(null!=objCuser && null!=objProccessSend)
                {
                    result.put("userID", objCuser.getUserID());
                    result.put("userName", objCuser.getUserName());
                    result.put("userOrgID", objCuser.getUserOrgID());
                    result.put("unitStation", objCuser.getUnitStation());
                    result.put("fAcceptUser", objProccessSend.getFAcceptUser());
                    result.put("fState", objProccessSend.getFState());
                    result.put("isAcceptance", objProccessSend.getFAcceptTime()==null?"0":"1");
                    message.setRoot(result);
                    message.setSuccess(true);
                    message.setMessage("获取数据成功。");
                }
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("数据获取失败！");
		}
		return getJsonFromObj(message);
	}

	/**
	 * 
	 * @function:
	 * @data: 2013-2-5下午3:20:24
	 * @author jibinbin
	 * @return
	 *
	 */
	private String getWfProcessForID(){	  
		message = new ResultMessage();
		try {
			Integer id=getInt("ProcessId", -1);
			TWfProcess resultProcess = service.getWfProcessForID(id);
			Map<String, Object> result=new HashMap<String, Object>();			
			if(null!=resultProcess)	
			{
				result.put("fCurrentUserId", resultProcess.getFCurrentUserId());
				result.put("fFormPkid", resultProcess.getFFormPkid());
				result.put("fState", resultProcess.getFState());	
				message.setRoot(result);
				message.setSuccess(true);
				message.setMessage("获取数据成功。");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("数据获取失败！");
		}
		return getJsonFromObj(message);
	}
}
