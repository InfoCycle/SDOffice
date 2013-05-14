/**
 * @Title		: BussAbstractService.java
 * @Date		: 2013-03-26 10-59
 * @Author		: chunlei
 * @Description	: 实现了BussService接口的多数功能功能
 * @TODO List	: 
 * (增加、修改)了什么  at 日期 时间  by chunlei
 * 如:增加delete删除人员方法 at 2013-01-01 16:18 by chunlei

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;

@Service
@Transactional
public abstract class BussAbstractService implements BussService {
    @Autowired
    IBaseDao<TWfProcess> processDao;
    @Autowired
    IBaseDao<TWfProccessActive> activeDao;
    @Autowired
    WfProcessUtils utils;
    @Autowired
    AppSEQHelper appSEQHelper;

    @Override
    public boolean setProcessTitle(int processId, int fromPKID, String title) {
	// TODO Auto-generated method stub
	return utils.setProcessTitle(processId, fromPKID, title);
    }

    @Override
    public boolean addProcessActiveItem(int processId, int userId,
	    int aboveActId, String remark) {
	// TODO Auto-generated method stub
	return utils.addProcessActiveItem(processId, userId, aboveActId, remark)>0;
    }

    @Override
    public void setActiveAcceptTime(int activeId) {
	// TODO Auto-generated method stub
	utils.setActiveAcceptTime(activeId);
    }

    @Override
    public boolean activeUrge(int activeId, String remark) {
	// TODO Auto-generated method stub
	return utils.activeUrge(activeId, remark);
    }

    @Override
    public boolean activeComplet(int activeId) {
	// TODO Auto-generated method stub
	return utils.activeComplet(activeId);
    }

    @Override
    public boolean activeReturn(int activeId, String remark) {
	// TODO Auto-generated method stub
	return utils.activeReturn(activeId, remark);
    }

    @Override
    public boolean activeRevoke(int processId, int activeId) {
	// TODO Auto-generated method stub
	return processDao.Delete(TWfProcess.class, processId)&&activeDao.Delete(TWfProccessActive.class, activeId);
    }
}
