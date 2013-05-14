package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.domain.TAppUser;
import com.info.domain.TTask;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.domain.TWfProcessSend;

@Service
@Transactional
public class BussCommonService {
	@Autowired
	IBaseDao<TWfProccessActive> servicePADao;
	
	@Autowired
	IBaseDao<TWfProcessSend> servicePSDao;
	
	@Autowired
	IBaseDao<TWfProcess> serviceWPDao;
	
	@Autowired
	IBaseDao<TTask> serverTaskDao;
	
	@Autowired
	IBaseDao<TAppUser> serverAppUserDao;
	/**
	 * 通过 activeId 获得活动信息
	 * @function:
	 * @data: 2013-2-5下午2:42:26
	 * @author jibinbin
	 * @param activeId
	 * @return
	 *
	 */
	 
	public TWfProccessActive getWfProccessActiveForActiveId(Integer activeId){	
		TWfProccessActive resulTWfProccessActive=new TWfProccessActive();
		try {
			resulTWfProccessActive= servicePADao.GetEntity(TWfProccessActive.class, activeId);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resulTWfProccessActive;
	}
	/**
	 * 
	 * @Description	: 获得抄送信息
	 * @Author		: jibb
	 * @Date		: 2013-04-02 10-58
	 * @param id
	 * @return
	 */
	public TWfProcessSend getWfProcessSendForFId(Integer id){
	    TWfProcessSend resultObj=new TWfProcessSend();
        try {
            resultObj= servicePSDao.GetEntity(TWfProcessSend.class, id);          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
	    
	}
	/**
	 * 通过 ID 获得过程信息
	 * @function:
	 * @data: 2013-2-5下午2:46:33
	 * @author jibinbin
	 * @param id
	 * @return
	 *
	 */
	 
	public TWfProcess getWfProcessForID(Integer id){
		TWfProcess resultTWfProcess=new TWfProcess();
		try {
			resultTWfProcess = serviceWPDao.GetEntity(TWfProcess.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultTWfProcess;
	}
	
	/**
	 * 获得任务名称
	 * @param id
	 * @return
	 */
	public String getTaskName(Integer id){
		TTask objTask=new TTask();
		try{
			objTask=serverTaskDao.GetEntity(TTask.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return objTask.getFTaskName();
	}
	
	/**
	 * 获得FormPKID 从TWFProcess表中
	 * @param processId
	 * @return
	 */
	public  Integer getFormPKIDForWFProcess(Integer processId){
		TWfProcess resultTWfProcess=new TWfProcess();
		try {
			resultTWfProcess = serviceWPDao.GetEntity(TWfProcess.class, processId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultTWfProcess.getFFormPkid();
	}
	
	 /**
	  * 通过岗位查询用户
	  * @param uid
	  * @return
	  */
	@SuppressWarnings("unchecked")
	public List<String> getAppUserForStation(Integer uid){
		String SQLString = "select f_id from dbo.T_APP_USER  where f_unit_station=?";
		javax.persistence.Query query = serverAppUserDao.CreateNativeSQL(SQLString);
		query.setParameter(1, uid);
		return (List<String>) query.getResultList();
	}
	
}
