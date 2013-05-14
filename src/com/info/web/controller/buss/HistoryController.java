/**
 * @Title		: HistoryController.java
 * @Date		: 2013-04-15 10-33
 * @Author		: chunlei
 * @Description	: TODO(用一句话描述该文件做什么)
 * @TODO List	: 
 * (增加、修改)了什么  at 日期 时间  by chunlei
 * 如:增加delete删除人员方法 at 2013-01-01 16:18 by chunlei

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TWfProcess;
import com.info.domain.TWfProcessType;
import com.info.service.bussHistoryService;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class HistoryController extends BaseController {
    EasyDataGrid dataGrid;
    ResultMessage message;
    @Autowired
    bussHistoryService historyService;
    @RequestMapping(value="/buss/history/{action}")
    public void history(@PathVariable("action")int action,HttpServletResponse response) {
	CurrentResponse=response;
	dataGrid=new EasyDataGrid();
	String result="";
	switch (action) {
		case 0:
        	    result=getProcess();
        	    break;
        	case 1:
        	    result=getProcessType();
        	    break;
    	}
	writeJsonString(result);
    }
    /**
     * 
     * @Description	: 返回页面标题
     * @Author		: chunlei
     * @Date		: 2013-04-15 15-31
     * @return
     */
    private String getProcessType() {
	List<TWfProcessType> list=null;
	String Mydata="[";
	try {
	   list=historyService.getProcessType();
	} catch (Exception e) {
	    System.out.println(e.toString());
	}
	if (list==null) {
	    Mydata="[{\"id\":\"\",\"text\":\"\"}]";
	} else {
	    for (TWfProcessType tWfProcessType : list) {
		Mydata+="{\"id\":\""+tWfProcessType.getFId()+"\",\"text\":\""+tWfProcessType.getFTypeName()+"\"},";
	    }
	    Mydata=Mydata.substring(0, Mydata.length()-1);
	    Mydata+="]";
	}
	return Mydata;
    }
    /**
     * 
     * @Description	: 根据页面请求返回grid
     * @Author		: chunlei
     * @Date		: 2013-04-15 15-12
     * @return
     */
    private String getProcess() {
	int typeId=getInt("typeId", -1);
	int start=getInt("page", 0);
	int limit=getInt("rows", 0);
	if(start>1){
		start*=limit;
		start-=limit;
	}else {
		start=0;
	}
	String name=getString("name", "");
	String entrustUnit=getString("entrustUnit", "");
	String Industry=getString("Industry","");
	String department=getString("department", "");
	String giveperson=getString("giveperson", "");
	String projmgr=getString("projmgr", "");
	
	List<TWfProcess> list=null;
	long total=0;
	try {
	    list=historyService.getProcessBytype(typeId, start, limit, name,entrustUnit,Industry,department,giveperson,projmgr);
	    total=historyService.getCountP(typeId, start, limit, name,entrustUnit,Industry,department,giveperson,projmgr);
	} catch (Exception e) {
	    list=null;
	    total=0;
	}
	dataGrid.setRows(list);
	dataGrid.setTotal(total);
	return getJsonFromObj(dataGrid);
    }
    
    @RequestMapping(value="/buss/historyproject/{action}")
    public void historyco(@PathVariable("action")int action,HttpServletResponse response) {
	CurrentResponse=response;
	message=new ResultMessage();
	String result=null;
	switch (action) {
	case 1:
	    result=projectprocesssave();
	    break;
	case 2:
	    result=projectprocessdelete();
	    break;
	}
	writeJsonString(result);
    }
    private String projectprocessdelete() {
	
	return null;
    }
    /**
     * 
     * @Description	: 添加项目过程
     * @Author		: chunlei
     * @Date		: 2013-04-31 12-08
     * @return
     */
    private String projectprocesssave() {
	
	return null;
    }
}
