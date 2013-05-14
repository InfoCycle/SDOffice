/**
 * @Title		: BussUpdateRecordsService.java
 * @Date		: 2013-04-15 15-08
 * @Author		: jibb
 * @Description	: TODO(用一句话描述该文件做什么)
 * @TODO List	: 
 * (增加、修改)了什么  at 日期 时间  by jibb
 * 如:增加delete删除人员方法 at 2013-01-01 16:18 by jibb 

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TWfProcess;
import com.info.domain.TWfUpdateRecords;

/**
 * 
 * @ClassName	: BussUpdateRecordsService   
 * @Description	: 修改记录   
 * @Author		: jibb
 * @Date		: 2013-04-15 15-08   
 *
 */
@Service
@Transactional
public class BussUpdateRecordsService {
    @Autowired
    IBaseDao<TWfUpdateRecords> updateRecordsDao;
    
    @Autowired
    AppSEQHelper SEQHelper;
    
    /**
     * 
     * @Description	: 新增修改记录
     * @Author		: jibb
     * @Date		: 2013-04-15 15-16
     * @param objRecords
     * @return
     */
    public boolean SaveUpdateRecord(TWfUpdateRecords objRecords){
        boolean flag=false;
        try {
           if(null==objRecords.getFId() || objRecords.getFId()==0){ //新增
               Integer id=SEQHelper.getCurrentVal("SEQ_UPDATE_RECORDS");
               objRecords.setFId(id);               
               flag= updateRecordsDao.Persist(objRecords);
           }else{ //修改
               flag= updateRecordsDao.Update(objRecords); 
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 
     * @Description	: 删除修改记录
     * @Author		: jibb
     * @Date		: 2013-04-15 15-19
     * @param fid
     * @return
     */
    public boolean DeleteUpdateRecord(Integer fid){
        boolean flag=false;
        try {
           if(null==fid || fid==0){ //新增                
               flag= false;
           }else{ //修改
               flag= updateRecordsDao.Delete(updateRecordsDao.GetEntity(TWfUpdateRecords.class, fid));
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    /**
     * 
     * @Description	: 获得修改记录列表
     * @Author		: jibb
     * @Date		: 2013-04-15 15-28
     * @param pid  TWfProcess表F_ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TWfUpdateRecords> getUpdateRecordsListForProcessId(Integer pid){
        String sSQL="select o from TWfUpdateRecords o where o.fkProcessId=?1";
        javax.persistence.Query query=updateRecordsDao.CreateQuery(sSQL, TWfUpdateRecords.class);
        query.setParameter(1, pid);
        return query.getResultList();
    }
    
    public TWfUpdateRecords getUpdateRecordsForProcessId(Integer pid){
        String sql = "select a.* from dbo.T_WF_UPDATE_RECORDS a "
                +" where a.FK_PROCESS_ID=?1";
        TWfUpdateRecords objResult;
        try {
            javax.persistence.Query query =updateRecordsDao.CreateNativeSQL(sql, TWfUpdateRecords.class);
            query.setMaxResults(1);
            query.setParameter(1, pid);
            objResult = (TWfUpdateRecords)query.getSingleResult();
        } catch (Exception e) {
            objResult=null;
            e.printStackTrace();
        }       
        return objResult;
    }
}
