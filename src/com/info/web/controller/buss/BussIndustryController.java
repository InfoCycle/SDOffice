package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TIndustry;
import com.info.domain.ViewClientTree;
import com.info.service.BussIndustrySerivce;
import com.info.web.ResultMessage;
import com.info.web.controller.util.AutoCord;
import com.info.web.controller.util.BaseController;


@Controller
public class BussIndustryController extends BaseController {
	ResultMessage message;
	@Autowired
	BussIndustrySerivce industrySerivce;
	
	AutoCord autoId=new AutoCord();
	
	@RequestMapping(value="/industry/{action}")
	public void industrycl(@PathVariable("action")Integer action,HttpServletResponse response){
		CurrentResponse = response;
		
		message=new ResultMessage();
		String result="";
		switch (action) {
		case 0:
			result=getAllInsdustry();
			break;
		case 1:
			result=add(getIndustry());
			break;
		case 2:
			result=update(getIndustry());
			break;
		case 3:
			result=getAllnode();
			break;
		case 4:
			result=getById(getInt("id", -1));
			break;
		case 5:
			result=delete(getIndustry());
			break;
		}
		writeJsonString(result);
	}
	@RequestMapping(value="/industry/cb/{pid}")
	public void cbTree(@PathVariable("pid")Integer pid,HttpServletResponse response) {
		CurrentResponse =response;
		message=new ResultMessage();
		String result="";
		result=getTreeNoId(pid);
		writeJsonString(result);
	}
	@RequestMapping(value = "/client/industryTree/{pid}")
	public void industryTree(@PathVariable("pid")Integer pid,HttpServletResponse response) {
		CurrentResponse =response;
		message=new ResultMessage();
		String result="";
		result=getTree(pid);
		writeJsonString(result);
	}
	private String getTreeNoId(int id) {
		List<TIndustry> list=industrySerivce.cbIndustries(id);
		
		return getJsonFromArray(list);
	}
	/**
	 * 生成Industry
	 * @return
	 */
	private TIndustry getIndustry() {
		TIndustry industry=new TIndustry();
		industry.setFId(getInt("FId", -1));
		industry.setFCode(getString("FCode", ""));
		industry.setFName(getString("FName", ""));
		industry.setFNote(getString("FNote", ""));
		industry.setFParentId(getInt("FParentId", 0));
		industry.setFSort(getInt("FSort", 0));
		if (industry.getFCode().equals("")) {
			int prientId=industry.getFParentId();
			String code=autoId.autoInduCode(prientId, industrySerivce.getCode(prientId));
			industry.setFCode(code);
		}
		return industry;
	}
	/**
	 * 生成行业树
	 * @return
	 */
	private String getTree(Integer pid){
		String treeString="[";
		List<ViewClientTree> list=industrySerivce.getClientFoPid(pid);
		String iconCls="";
		int i=0;
		for (ViewClientTree tree : list) {
			i++;
			 iconCls="";
			System.out.println(tree.getIsleft());
			if(tree.getIsleft()){
				if(tree.getIsClient()==1){
					 iconCls=",iconCls:\"isClient-iconCls\"";
				}
			}
			treeString+="{id:'"+tree.getFId()+
					"',text:'"+tree.getFName()+
					"',leaf:"+tree.getIsleft()+
					",isClient:'"+tree.getIsClient().toString()+
					"',FClientId:'"+tree.getFClientId()+"'"+
					iconCls+(i<list.size()?"},":"}");
		}
		return treeString+="]";
	}
	private String getAllInsdustry() {
		List<TIndustry> list=industrySerivce.all();
		return getJsonFromArray(list);
	}
	/**
	 * 添加行业类型
	 * @param industry
	 * @return
	 */
	private String add(TIndustry industry) {
		if(industrySerivce.add(industry)){
			message.setMessage("添加完成！");
			message.setSuccess(true);
		}else {
			message.setMessage("添加失败");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private String update(TIndustry industry) {
		if (industrySerivce.updata(industry)) {
			message.setMessage("修改成功！");
			message.setSuccess(true);
		}else {
			message.setMessage("修改失败！请稍后再试");
			message.setSuccess(true);
		}
		return getJsonFromObj(message);
	}
	private String getAllnode() {
		String treeString="[";
		List<ViewClientTree> list=industrySerivce.getAllnode();
		String iconCls="";
		int i=0;
		for (ViewClientTree tree : list) {
			i++;
			 iconCls="";
			System.out.println(tree.getIsleft());
			if(tree.getIsleft()){
				if(tree.getIsClient()==1){
					 iconCls=",iconCls:\"isClient-iconCls\"";
				}
			}
			treeString+="{id:'"+tree.getFId()+
					"',text:'"+tree.getFName()+
					"',leaf:"+tree.getIsleft()+
					",isClient:'"+tree.getIsClient().toString()+
					"',FClientId:'"+tree.getFClientId()+"'"+
					iconCls+(i<list.size()?"},":"}");
		}
		System.out.println(treeString);
		return treeString+="]";
	}
	private String getById(int id) {
		List<TIndustry> list=industrySerivce.getIndustryById(id);
		message.setSuccess(true);
		message.setRoot(list);
		message.setTotalProperty(list.size());
		return getJsonFromObj(message);
	}
	private String delete(TIndustry industry) {
		if (industrySerivce.delete(industry)) {
			message.setSuccess(true);
			message.setMessage("["+industry.getFName()+"]删除成功!");
		}else {
			message.setSuccess(false);
			message.setMessage("["+industry.getFName()+"]删除失败!请稍后再试");
		}
		return getJsonFromObj(message);
	}
}
