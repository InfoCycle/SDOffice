package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TCompaniesInChapter;
import com.info.service.BussCompaniesInChapterService;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussCompaniesInChapterController extends BaseController {
	@Autowired
	BussCompaniesInChapterService cicservice;
	
	ResultMessage message;
	EasyDataGrid dataGrid;
	
	@RequestMapping(value="/buss/cic/action")
	public void companiesInCapter(@PathVariable("action")Integer action,HttpServletResponse response){
		CurrentResponse=response;
		String result=null;
		switch (action) {
		case 0:
			int start=getInt("page", 0);
			int limit=getInt("rows", 0);
			if(start>1){
				start*=limit;
				start-=limit;
			}else {
				start=0;
			}
			result=getCics(start,limit,getString("title", ""));
			break;
		case 1:
			result=addCic(getCompaniesInChapter());
			break;
		case 2:
			result=updataCic(getCompaniesInChapter());
			break;
		case 3:
			result=deleteCic(getCompaniesInChapter());
			break;
		}
		writeJsonString(result);
	}
	/**
	 * 删除公司用章
	 * @param cic
	 * @return
	 */
	private String deleteCic(TCompaniesInChapter cic) {
		if (cicservice.deleteCic(cic)) {
			message.setMessage("删除成功!");
			message.setSuccess(true);
		}else {
			message.setMessage("删除失败,请与管理员联系!");
			message.setSuccess(true);
		}
		return getJsonFromObj(cic);
	}
	/**
	 * 修改公司用章
	 * @param cic
	 * @return
	 */
	private String updataCic(TCompaniesInChapter cic) {
		if(cicservice.updataCic(cic)){
			message.setMessage("修改成功!");
			message.setSuccess(true);
		}else {
			message.setMessage("修改失败,请与管理员联系!");
			message.setSuccess(false);
		}
		return getJsonFromObj(cic);
	}
	/**
	 * 从页面获取数据
	 * @return
	 */
	private TCompaniesInChapter getCompaniesInChapter() {
		TCompaniesInChapter cic=new TCompaniesInChapter();
		cic.setFAgent(getString("FAgent", ""));
		cic.setFAgentTime(getString("FAgentTime", ""));
		cic.setFApprover(getString("FApprover", ""));
		cic.setFApproverTime(getString("FApproverTime", ""));
		cic.setFCounterparts(getString("FCounterparts", ""));
		cic.setFId(getInt("FId", -1));
		cic.setFNote(getString("FNote", ""));
		cic.setFProposer(getString("FProposer", ""));
		cic.setFTitle(getString("FTitle", ""));
		cic.setFType(getString("FType", ""));
		return cic;
	}
	/**
	 * 返回页面grid的数据
	 * @param start
	 * @param limit
	 * @param title
	 * @return
	 */
	private String getCics(int start, int limit, String title) {
		List<TCompaniesInChapter> list=cicservice.getTCompaniesInChapters(start, limit, title);
		dataGrid.setRows(list);
		dataGrid.setTotal(cicservice.getCountCic(start, limit, title));
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 添加公司用章
	 * @param cic
	 * @return
	 */
	private String addCic(TCompaniesInChapter cic) {
		if(cicservice.addCic(cic)){
			message.setMessage("添加成功!");
			message.setSuccess(true);
		}else {
			message.setMessage("添加失败,请与管理员联系!");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
}
