/**
 * @Title		: BussService.java
 * @Date		: 2013-03-26 10-28
 * @Author		: chunlei
 * @Description	: 所有关于流程的service方法
 * @TODO List	: 
 * 新增 setProsessTitle方法  at 2013-03-26 10-50
 * 新增 addProcessActiveItem 方法at 2013-03-26 10-50
 * 新增 setActiveAcceptTime 方法at 2013-03-26 10-50
 * 新增 activeUrge 方法at 2013-03-26 10-50
 * 新增 activeComplet 方法at 2013-03-26 10-50
 * 新增 activeReturn 方法at 2013-03-26 10-50
 * 新增 revoke 方法at 2013-03-26 10-50
 * 新增 deleteObjectById 方法at 2013-03-26 10-50
 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

public interface BussService {
    /**
     * 
     * @Description	: 设置标题方法
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-50
     * @param processId
     * @param fromPKID
     * @param title
     * @return
     */
    public boolean setProcessTitle(int processId, int fromPKID, String title);
    /**
     * 
     * @Description	提交方法
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-48
     * @param processId 
     * @param userId
     * @param aboveActId
     * @param remark 
     * @return
     */
    public boolean addProcessActiveItem(int processId, int userId, int aboveActId, String remark);
    /**
     * 
     * @Description	: 签收方法
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-47
     * @param activeId
     * @return
     */
    public void setActiveAcceptTime(int activeId);
    /**
     * 
     * @Description	: 催办
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-29
     * @param activeId
     * @param remark
     * @return
     */
    public boolean activeUrge(int activeId,String remark);
    /**
     * 
     * @Description	: 完成任务
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-43
     * @param activeId
     * @return
     */
    public boolean activeComplet(int activeId);
    /**
     * 
     * @Description	: 打回任务
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-44
     * @param activeId
     * @param remark
     * @return
     */
    public boolean activeReturn(int activeId,String remark);
    /**
     *  
     * @Description	: 撤销任务
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-44
     * @param processId
     * @param activeId
     * @return
     */
    public boolean activeRevoke(int processId,int activeId);
    /**
     * 
     * @Description	: 根据ID删除实体（与撤销方法一起使用）
     * @Author		: chunlei
     * @Date		: 2013-03-26 10-45
     * @param FId
     * @return
     */
    public boolean deleteObjectById(int FId);
}
