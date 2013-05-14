package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TLinkman;
import com.info.domain.ViewTclientLinkman;
import com.info.service.BussLinkmanService;
import com.info.web.CurrentUser;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussLinkmanContoroller extends BaseController{
	ResultMessage message;
	@Autowired
	BussLinkmanService linkmanService;

	
	@RequestMapping(value="/buss/linkman/{action}")
	public void liconller(@PathVariable("action")String action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		String result="";
		if(action.equals("add")){
			result=add(getlinkman());
		}else if (action.equals("update")) {
			result=update(getlinkman());
		}else if (action.equals("delete")) {
			result=delete(getlinkman());
		}else if(action.equals("get")){
			if(!getString("name", "").equals("")){
				result=getByName(getString("name", ""),getInt("FClient", 0),getCurrentUserID());
			}else {
				CurrentUser user=getCurrentUser();
				result=getByClientId(getInt("FClient", 0),user.getUserID(),user.getUserOrgID(),user.getUnitStation());
			}
		}
		System.out.println(result);
		writeJsonString(result);
	}
	/**
	 * 根据姓名关键字查询联系人
	 * @param string
	 * @return
	 */     
	private String getByName(String name,int clientId,int userId) {
		message.setRoot(linkmanService.select(name,clientId,userId));
		message.setSuccess(true);
		return getJsonFromObj(message);
	}
	/**
	 * 添加联系人
	 * @param linkman
	 * @return
	 */
	private String add(TLinkman linkman) {
		if(linkmanService.add(linkman)){
			message.setSuccess(true);
			message.setMessage("添加完成！");
		}else {
			message.setSuccess(false);
			message.setMessage("添加失败！");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 修改联系人
	 * @param linkman
	 * @return
	 */
	private String update(TLinkman linkman) {
		if (linkmanService.update(linkman)) {
			message.setSuccess(true);
			message.setMessage("修改成功！");
		}else {
			message.setSuccess(false);
			message.setMessage("修改失败！");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 删除联系人
	 * @param linkman
	 * @return
	 */
	private String delete(TLinkman linkman) {
		if(linkmanService.delete(linkman)){
			message.setSuccess(true);
			message.setMessage("删除成功！");
		}else{
			message.setSuccess(false);
			message.setMessage("删除失败！");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 实例化对象
	 * @return
	 */
	private TLinkman getlinkman() {
		TLinkman linkman=new TLinkman();
		linkman.setFClientId(getInt("FClientId", 0));
		linkman.setFDepartment(getString("FDepartment", ""));
		linkman.setFEmail(getString("FEmail", ""));
		linkman.setFId(getInt("FId", 0));
		linkman.setFName(getString("FName", ""));
		linkman.setFPhone(getString("FPhone", ""));
		linkman.setFPosition(getString("FPosition", ""));
		linkman.setFTel(getString("FTel", ""));
		linkman.setFUserId(getCurrentUser().getUserID());
		System.out.println(linkman.getFUserId());
		linkman.setFUserName(getCurrentUser().getUserName());
		return linkman;
	}
	/**
	 * 根据公司Id返回所有联系人
	 * @param id
	 * @return
	 */
	private String getByClientId(int id,int userId,int org,int station){
		try {
			List<ViewTclientLinkman> list=linkmanService.getByClientId(id,userId,org,station);
			ViewTclientLinkman objLinkman=list.get(0);
			System.out.println("tieer"+objLinkman);
			System.out.println(list.get(0));
			if(objLinkman!=null){
				message.setSuccess(true);
				message.setRoot(list);
				message.setTotalProperty(list.size());
			}else {
				message.setSuccess(true);
				message.setRoot("");
				message.setTotalProperty(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			message.setSuccess(true);
			message.setRoot("");
			message.setTotalProperty(0);
		}
		return getJsonFromObj(message);
	}
}
