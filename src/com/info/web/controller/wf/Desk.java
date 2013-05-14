
package com.info.web.controller.wf;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.StringUtil;
import com.info.domain.TConstructionData;
import com.info.service.WfDesk;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;


/**   
 * @ClassName	: Desk   
 * @Description	: 提供工作台显示公告、造价资料、待阅文件  
 * @Author		: liwx
 * @Date		: 2013-02-21 10-09   
 */
@Scope("prototype")
@Controller
public class Desk extends BaseController{
	@Autowired
	WfDesk service;
	@RequestMapping(value = "/wf/desk/{action}/")
	public void deskAction(@PathVariable("action") int action,
			HttpServletResponse response){
		CurrentResponse =response;
		message = new ResultMessage();		
		String resultString="";
		try {
			switch (action) {
			case 1://公示公告
				resultString =getNotice();
				break;
			case 2://造价资料
				resultString =getFileList();
				break;
			case 3://待阅文件数量
				resultString =getUnReadCount();
				break;
			case 4://待阅文件列表
				resultString =getUnReadList();
				break;
			}
			writeJsonString(resultString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getNotice() {
		return "";
	}
	/**
	 * @Description	: 造价资料，工作台查看部分
	 * @Author		: liwx
	 * @Date		: 2013-03-28 16-05
	 * @return
	 */
	private String getFileList() {
		//id url limitTitle title
		String formatsString ="<li onclick=\"javascript:showFileView(''{0}'',''{1}'',''{2}'');\">{3}</li>";
		MessageFormat format =new MessageFormat(formatsString);
		String HtmlString;		
		StringBuilder sb=new StringBuilder();
		List<TConstructionData> list = service.getConstructions();
		
		for(TConstructionData obj:list){
			HtmlString=format.format(new Object[]{
					obj.getFId().toString(),
					"../construction/ConstructionLook.html",
					"(造价资料)"+StringUtil.limitStringLength(obj.getFTitle(), 10),
					obj.getFTitle()
				});
			sb.append(HtmlString);
		}
		message.setSuccess(list.size()>0);
		message.setRoot(sb.toString());
		return getJsonFromObj(message);
	}
	
	private String getUnReadCount() {
		message.setSuccess(true);
		message.setRoot(service.getUnReadCount());
		return getJsonFromObj(message);
	}
	
	private String getUnReadList() {
		message.setSuccess(true);
		message.setRoot(service.getUnReadList());
		return getJsonFromObj(message);
	}
}
