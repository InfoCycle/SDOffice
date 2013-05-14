/**
 * @Title		: WfObjectCompare.java
 * @Date		: 2013-04-02 11-01
 * @Author		: liwx
 * @Description	: 新对象与原对象比对，找出二者差异属性
 * @TODO List	: 
 * TODO(增加、修改)了什么  at 2013-04-02 11-01  by liwx
 * TODO如:增加delete删除人员方法 at 2013-04-02 11-01 by liwx 

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.common.dao.IBaseDao;
import com.info.common.util.DateUtil;
import com.info.common.util.StringUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TTask;
import com.info.web.CurrentUser;
/**
 * @ClassName	: WfObjectCompare   
 * @Description	: 对象属性比对  
 * @Author		: liwx
 * @Date		: 2013-04-02 11-05   
 */
@Service
public class WfObjectCompare {
	@Autowired
	IBaseDao<TTask> taskDao;
	/**
	 * @Description	: 属性比对
	 * @Author		: liwx
	 * @Date		: 2013-04-02 11-12
	 * @param CurrentObj
	 * @return
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String objectCompare(Object CurrentObj) throws IllegalAccessException {
		//比对差异集合
		List list = new ArrayList();
		
		//此处用任务对象实现
		TTask currentObj  = (TTask)CurrentObj;
		TTask originalObj = taskDao.GetEntity(TTask.class, currentObj.getFId());
		if(null==originalObj){
			throw new IllegalAccessException("需比对的原对象不存在。");
		}
		//判断行业类别差异
		if(null!=originalObj.getFIndustryCategoryName()
				&&!originalObj.getFIndustryCategoryName().equals(currentObj.getFIndustryCategoryName())){
			Map<String, String> diffMap = new HashMap();
			diffMap.put("propertyField", "行业类别");
			diffMap.put("originalValue", originalObj.getFIndustryCategoryName());
			diffMap.put("currentValue", currentObj.getFIndustryCategoryName());
			list.add(diffMap);
		}
		//判断服务类别差异
		if(null!=originalObj.getFServiceCategory()
				&&!originalObj.getFServiceCategory().equals(currentObj.getFServiceCategory())){
			Map<String, String> diffMap = new HashMap();
			diffMap.put("propertyField", "服务类别");
			diffMap.put("originalValue", originalObj.getFServiceCategory());
			diffMap.put("currentValue", currentObj.getFServiceCategory());
			list.add(diffMap);
		}
		//判断业务类别差异
		if(null!=originalObj.getFBusinessCategory()
				&&!originalObj.getFBusinessCategory().equals(currentObj.getFBusinessCategory())){
			Map<String, String> diffMap = new HashMap();
			diffMap.put("propertyField", "业务类别");
			diffMap.put("originalValue", originalObj.getFBusinessCategory());
			diffMap.put("currentValue", currentObj.getFBusinessCategory());
			list.add(diffMap);
		}
		return wrapCompareInfo(list);
		
	}
	/**
	 * @Description	: 包装比对差异描述
	 * @Author		: liwx
	 * @Date		: 2013-04-02 11-30
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private String wrapCompareInfo(List list) {
		String result="";
		StringBuilder wrap = new StringBuilder();
		CurrentUser user= SystemCurrentUser.getCurrentUser();
		for (Object object : list) {
			Map<String, String> info =(Map<String, String>)object;
			if(StringUtil.isEmpty(wrap.toString())){
				wrap.append(user.getUserName()+"于"+DateUtil.format());
				wrap.append("修改了"+info.get("propertyField")+",");
				wrap.append("从原来的\""+info.get("originalValue")+"\"修改为\"");
				wrap.append(info.get("currentValue")+"\";");
			}else{
				wrap.append("同时");
				wrap.append("修改了"+info.get("propertyField")+",");
				wrap.append("从原来的\""+info.get("originalValue")+"\"修改为\"");
				wrap.append(info.get("currentValue")+"\";");
			}
		}
		if(StringUtil.isNotEmpty(wrap.toString()))
			result = wrap.toString()+"\n原因:(自填)";
		return result;
	}
}
