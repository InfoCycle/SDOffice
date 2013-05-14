/**
 * @Title		: BussConsultingResultsFilesServer.java 继承自 BussAbstractService.java
 * @Date		: 2013-03-25 18-13
 * @Author		: chunlei
 * @Description	: 项目咨询成果的事物层
 * @TODO List	: 
 * 重载 deleteObjectById 方法
 * 新增 updataConsulting 更新档案登记 方法 at 2013-03-26 09-51 by chunlei
 * 新增  addConsulting 新建档案登记 方法 at 2013-03-26 09-49 by chunlei
 * 新增 getCosultingById 根据FId返回咨询成果档案登记 方法 at 2013-03-26 11-27 by chunlei
 * 新增 getAppCoden 返回归档内容选项 方法 at 2013-03-26 11-27 by chunlei
 * 新增 getAppCodec 返回成果类型 方法 at 2013-03-26 11-27 by chunlei
 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.domain.TAppCode;
import com.info.domain.TCheckReview;
import com.info.domain.TConsultingResultsFiles;
import com.info.domain.ViewProjectBook;
import com.info.domain.ViewWfProcess;

@Service
@Transactional
public class BussConsultingResultsFilesServer extends BussAbstractService {
    @Autowired
    IBaseDao<TConsultingResultsFiles> resFileDao;
    @Autowired
    IBaseDao<TAppCode> codeDao;
    @Autowired
    IBaseDao<TCheckReview> checkDao;

    /**
     * 重写deleteObjectById方法具体实现
     */
    @Override
    public boolean deleteObjectById(int FId) {
	return resFileDao.Delete(TConsultingResultsFiles.class, FId);
    }
    /**
     * 
     * @Description	: 根据taskId返回完成时间
     * @Author		: chunlei
     * @Date		: 2013-03-27 21-09
     * @param taskId
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getfinishTime(int taskId) {
	String sql="select * from T_CheckReview where FK_Task_Id=?";
	Query query=checkDao.CreateNativeSQL(sql, TCheckReview.class);
	query.setParameter(1, taskId);
	List<TCheckReview> list=query.getResultList();
	TCheckReview checkReview=list.get(0);
	return checkReview.getFGlosignTime();
    }
    
    /**
     * 
     * @Description	: 更新档案登记
     * @Author		: chunlei
     * @Date		: 2013-03-26 09-51
     * @param consultingResultsFiles
     * @return
     */
    public boolean updataConsulting(TConsultingResultsFiles consultingResultsFiles) {
	return resFileDao.Update(consultingResultsFiles);
    }
    /**
     * 
     * @Description	: 新建档案登记
     * @Author		: chunlei
     * @Date		: 2013-03-26 09-49
     * @param consultingResultsFiles
     * @return
     */
    public TConsultingResultsFiles addConsulting(TConsultingResultsFiles consultingResultsFiles) {
	consultingResultsFiles.setFId(appSEQHelper.getCurrentVal("SEQ_CONSULTING"));
	if (resFileDao.Persist(consultingResultsFiles)) {
	    return consultingResultsFiles;
	} else {
	    return null;
	}	
    }
    /**
     * 
     * @Description	: 根据FId返回咨询成果档案登记
     * @Author		: chunlei
     * @Date		: 2013-03-26 11-27
     * @param FId
     * @return
     */
    public TConsultingResultsFiles getCosultingById(int FId) {
	return resFileDao.GetEntity(TConsultingResultsFiles.class, FId);
    }
    /**
     * 
     * @Description	: 返回归档内容选项
     * @Author		: chunlei
     * @Date		: 2013-03-26 14-14
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TAppCode> getAppCoden() {
	String sql="select * from T_App_Code where FK_CODE_TYPE_ID=429";
	Query query=codeDao.CreateNativeSQL(sql, TAppCode.class);
	return query.getResultList();
    }
    /**
     * 
     * @Description	: 返回成果类型
     * @Author		: chunlei
     * @Date		: 2013-03-26 14-17
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TAppCode> getAppCodec() {
	String sql="select * from T_App_Code where FK_CODE_TYPE_ID=381";
	Query query=codeDao.CreateNativeSQL(sql, TAppCode.class);
	return query.getResultList();
    }
    /**
     * 
     * @Description	: 根据from_PKId返回process
     * @Author		: chunlei
     * @Date		: 2013-04-02 10-46
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ViewWfProcess> getcp(int id) {
	String sql="select * from View_Wf_Process where F_TYPE_ID=10008 and F_Form_PKID=?";
	Query query=resFileDao.CreateNativeSQL(sql);
	query.setParameter(1, id);
	return query.getResultList();
    }
}
