/**
 * @Title		: WfTaskPackage.java
 * @Date		: 2013-03-21 15-50
 * @Author		: liwx
 * @Description	: 持久化任务包信息
 * @TODO List	: 

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TTask;
/**
 * @ClassName	: WfTaskPackage   
 * @Description	: 持久化任务包信息  
 * @Author		: liwx
 * @Date		: 2013-03-21 15-51   
 */
@Service
@Transactional
public class WfTaskPackage {
	@Autowired
	AppSEQHelper SEQHelper;
	@Autowired
	IBaseDao<TTask> taskDao;
	
	public TTask addTaskPackage(TTask object) {
		object.setFId(SEQHelper.getCurrentVal("SEQ_TASK"));
		object.setFYear(Integer.parseInt(object.getFGiveTime().substring(0, 4)));
		object.setFMainTaskId(1);//任务包标记
		taskDao.Persist(object);
		return object;
	}
	
	public boolean updateTaskPackage(TTask object) {
		return taskDao.Update(object);
	}
	/**
	 * @Description	: 删除任务包，需更新任务包下的子任务的任务包未null
	 * @Author		: liwx
	 * @Date		: 2013-03-21 16-03
	 * @param fId
	 * @return
	 */
	public boolean deleteTaskPackage(int fId) {
		boolean flag=true;
		try {
			taskDao.ExecuteSQL("delete from T_Task where f_id="+fId);
			taskDao.ExecuteSQL("update T_Task set F_ParentTaskID=NULL where F_ParentTaskID="+fId);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public TTask getTaskById(int fId) {
		return taskDao.GetEntity(TTask.class, fId);
	}
}
