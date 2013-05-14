package com.info.web.controller.buss;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.DateUtil;
import com.info.domain.TClient;
import com.info.domain.ViewClientIndustry;
import com.info.domain.ViewTclientLinkman;
import com.info.service.BussClientService;
import com.info.service.BussLinkmanService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.AutoCord;
import com.info.web.controller.util.BaseController;

@Controller
public class BussClientController extends BaseController{
	ResultMessage message;
	@Autowired
	BussClientService clientService;
	@Autowired
	BussLinkmanService linkmanService;
	AutoCord autoCord=new AutoCord();
	
	
	@RequestMapping(value="/buss/client/{action}")
	public void clController(@PathVariable("action")String action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		/*String[] selectStrings={"select * from View_Client_Industry where F_Name like ?",
				"select * from View_Client_Industry where F_Industry_Name like ?",
				"select * from View_Client_Industry where F_EntryTime like ?"};*/
		String result="";
		if(action.equals("add")){
			result=add(getClient());
		}else if (action.equals("updata")) {
			result=updata("修改", getClient());
		}else if (action.equals("enOrDis")) {
			result=updataP(getString("Ids", ""), getString("FWay", ""));
		}else if (action.equals("get")) {
			result=getClientbyId(getInt("FId", 0));
		}else if(action.equals("getall")){
			//int wayn=getInt("Way", -1);
			String cn=getString("cn", "");
			String ln=getString("ln", "");
			if(cn.equals("")&&ln.equals("")){
				result=getClients(getInt("start", 0),getInt("limit", 0));
			}else {
				/*String way=selectStrings[wayn];
				result=getByWAY(way, iterm);
				System.out.println("result"+result);*/
				result=select(cn,ln,getInt("start", 0),getInt("limit", 0));
			}
		}else if(action.equals("linkm")){
			result=getlinkm(getInt("cid", -1));
		}
		writeJsonString(result);
	}
	private String getlinkm(int cid) {
		List<ViewTclientLinkman> list=linkmanService.getByClientId(cid);
		if(list.get(0)==null){
		    message.setRoot("[]");
		}else {
		    message.setRoot(list);
		}
		message.setTotalProperty(list.size());
		message.setSuccess(true);
		return getJsonFromObj(message);
	}
	/**
	 * client的模糊查询
	 * @param cname client名称
	 * @param lname 联系人名称
	 * @return
	 */
	private String select(String cname, String lname,int start,int limit) {
		List<Integer> cIds =linkmanService.getClient(cname, lname);
		List<Integer> ids=new ArrayList<Integer>();
		String idss="";
		for (Integer cid : cIds) {
			if(!ids.contains(cid)){
				ids.add(cid);
			}
		}
		for (Integer id : ids) {
			idss+=id+",";
		}
		if(idss.length()>0){
			List<TClient> list=clientService.getById(idss.substring(0, idss.length()-1),start,limit);
			int con=clientService.getByIdcon(idss.substring(0,idss.length()-1));
			message.setRoot(list);
			message.setTotalProperty(con);
			message.setSuccess(true);
			return getJsonFromObj(message);
		}else {
			message.setRoot("");
			message.setTotalProperty(0);
			message.setSuccess(true);
			return getJsonFromObj(message); 
		}
	}

	/**
	 * 生成client
	 * @return
	 */
	private TClient getClient() {
		TClient client=new TClient();
		client.setFAccount(getString("FAccount", ""));
		client.setFAddress(getString("FAddress", ""));
		client.setFBank(getString("FBank", ""));
		client.setFCord(getString("FCord", ""));
		client.setFEmail(getString("FEmail", ""));
		client.setFEntryPeople(getCurrentUser().getUserName());
		client.setFEntryTime(DateUtil.getTime());
		client.setFFax(getString("FFax", ""));
		client.setFId(getInt("FId",-1));
		client.setFLegalPerson(getString("FLegalPerson", ""));
		client.setFName(getString("FName",""));
		client.setFShortName(getString("FShortName",""));
		client.setFPossition(getString("FPossition", ""));
		client.setFPrincipar(getString("FPrincipar",	""));
		client.setFTel(getString("FTel", ""));
		client.setFZipCord(getString("FZipCord", ""));
		client.setFIndustry(getInt("FIndustry", 0));
		if(client.getFCord().equals("")){
			client.setFCord(autoCord());
		}
		
		System.out.println("this"+client.getFEntryTime());
		return client;
	}
	/**
	 * 将字符串装换为时间
	 * @param date
	 * @return
	 */
	private Timestamp strToTime(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date data = null;
		Timestamp ts = null;
		try {
			data = format.parse(date);
			String time = sdf.format(data);
			ts = Timestamp.valueOf(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ts;
	}
	/**
	 * 添加委托公司
	 * @param client
	 * @return
	 */
	private String add(TClient client) {
		if(clientService.addClient(client)){
			message.setMessage("添加完成！");
			message.setSuccess(true);
		}else {
			message.setMessage("添加失败！请稍后再试");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 修改委托公司信息
	 * @param client
	 * @return
	 */
	private String updata(String ms,TClient client) {
		if(clientService.updateClient(client)){
			message.setSuccess(true);
			message.setMessage(ms+"成功！");
		}else {
			message.setSuccess(false);
			message.setMessage(ms+"失败！请稍后再试");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 启用或停用
	 * @param ids
	 * @param way
	 * @return
	 */
	private String updataP(String ids,String way) {
		int no=0;
		if (way.equals("启用")) {
			no=clientService.updataStart(ids);
		}else {
			no=clientService.updataStope(ids);
		}
		if(no==0){
			message.setMessage(way+"失败！");
			message.setSuccess(false);
		}else {
			message.setMessage(no+"家公司"+way+"成功");
			message.setSuccess(true);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 启用或停用
	 * @param client
	 * @return
	 */
	private String enableOrDisnable(TClient client) {
		return updata(client.getFName()+"启用", client);
	}
	/**
	 * 根据Id返回client实体
	 * @param id
	 * @return
	 */
	private String getClientbyId(int id) {
			List<ViewClientIndustry> list=clientService.getClient(id);
			if(list.size()<1){
				message.setSuccess(false);
				message.setMessage("该项不是公司！");
			}else {
				message.setSuccess(true);
				message.setRoot(list);
			}	
		return getJsonFromObj(message);
	}
	/**
	 * 返回所有委托单位
	 * @return
	 */
	private String getClients(int start,int limit) {
		try {
			List<ViewClientIndustry>list=clientService.getAllClient(start,limit);
			message.setSuccess(true);
			message.setRoot(list);
			message.setTotalProperty(clientService.getcon());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonFromObj(message);
	}
	/**
	 * 根据条件查询client
	 * @param way查询方式
	 * @param iterm条件
	 * @return
	 */
	private String getByWAY(String way,String iterm,int start,int limit) {
		List<ViewClientIndustry> list=clientService.getByWay(iterm, way,start,limit);
		message.setSuccess(true);
		message.setRoot(list);
		return getJsonFromObj(message);
	}
	/**
	 * 自动生成编号
	 * @return
	 */
	private String autoCord() {
		List<String> list=clientService.getAllCord();
		return autoCord.autoCord(list);
	}
	/**
	 * 自动时间
	 * @return
	 */
	private String autoTime() {
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sm.format(new Date());
		return time;
	}
}
