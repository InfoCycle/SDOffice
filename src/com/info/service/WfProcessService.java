package com.info.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.domain.TWfProcessType;
import com.info.domain.TWfStationProcess;
import com.info.web.CurrentUser;

@Service
@Transactional
public class WfProcessService {
	@Autowired
	AppSEQHelper seqHelper;
	@Autowired
	IBaseDao<TWfProcessType> typeDao;
	@Autowired
	IBaseDao<TWfProcess> processDao;
	@Autowired
	IBaseDao<TWfProccessActive> activeDao;
	@Autowired
	IBaseDao<TWfStationProcess> stationProcessDao;
	String CountPageSQL;
	
	@SuppressWarnings("unchecked")
	public List<TWfProcessType> getProcessTypes() {
		String sql="select o from TWfProcessType o where o.FState =1 order by o.FSort";
		javax.persistence.Query query =typeDao.CreateQuery(sql);
		return query.getResultList();
	}
	/**
	 * 根据登录人员的岗位获取对应的业务类型列表
	 * @return List<TWfProcessType>
	 */
	@SuppressWarnings("unchecked")
	public List<TWfProcessType> getTWfProcessTypes() {
		List<Integer> permission = getProcessTypeForStation();
		String sql="select o from TWfProcessType o where o.FState =1 and o.FId in (:ids) order by o.FSort";
		javax.persistence.Query query =typeDao.CreateQuery(sql);
		query.setParameter("ids", permission);
		return query.getResultList();
	}
	
	/**
	 * 获取岗位对应的业务类型
	 * @author liwx at 2013-1-5下午5:40:17
	 * @return
	 */
	public List<Integer> getProcessTypeForStation() {
		CurrentUser currentUser= SystemCurrentUser.getCurrentUser();
		List<Integer> ids = new ArrayList<Integer>();
		String jpql ="select o from TWfStationProcess o where o.FUnitStationId="+currentUser.getUnitStation();
		List<TWfStationProcess> spList =stationProcessDao.Query(jpql);
		for (TWfStationProcess tWfStationProcess : spList) {
			ids.add(tWfStationProcess.getFProcessTypeId());
		}
		return ids;
	}
	
	/**
	 * 新建业务过程
	 * @author liwx at 2013-1-5下午5:40:36
	 * @param typeid
	 * @return
	 */
	public int addNewProcess(int typeid) {
		int proId=-1;
		try {
			proId = seqHelper.getCurrentVal("SEQ_WF_PROCESS");
			CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
			TWfProcess process =new TWfProcess();
			process.setFId(proId);
			process.setFState(0);
			process.setFTypeId(typeid);
			process.setFTitle("空");
			process.setFCreateUserId(loginUser.getUserID());
			process.setFCreateTime(DateUtil.format());
			process.setFCurrentUserId(loginUser.getUserID());
			TWfProccessActive active = new TWfProccessActive();
			active.setFId(seqHelper.getCurrentVal("SEQ_WF_ACTIVE"));
			active.setFProcessId(proId);
			active.setFState(0);
			active.setFAcceptStation(loginUser.getUnitStation());
			active.setFAcceptUser(loginUser.getUserID());
			active.setFSendUser(loginUser.getUserID());
			active.setFSendStation(loginUser.getUnitStation());
			active.setFSendTime(DateUtil.format());
			active.setFCompleteTime(DateUtil.format());
			activeDao.Persist(active);
			processDao.Persist(process);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return proId;
	}
		
	
	/**
	 * 业务保存时更新过程表信息
	 * @author liwx at 2013-1-5下午5:40:57
	 * @param proId 过程表PKID
	 * @param formPKID 业务表单PKID
	 * @param title 业务过程标题
	 * jibinbin  新增：param isHistory 0不是,1是
	 * @return
	 */
	public boolean setProcessTitle(int proId,int formPKID,String title) {
		boolean flag;
		try {
			TWfProcess process =processDao.GetEntity(TWfProcess.class, proId);
			process.setFFormPkid(formPKID);
			process.setFTitle(title);			
			flag = processDao.Update(process);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;		
	}
	
	
	/**
     * 业务保存时更新过程表信息
     * @author liwx at 2013-1-5下午5:40:57
     * @param proId 过程表PKID
     * @param formPKID 业务表单PKID
     * @param title 业务过程标题
     * jibinbin  新增：param isHistory 0不是,1是
     * @return
     */
    public boolean setProcessTitle(int proId,int formPKID,String title,Integer isHistory) {
        boolean flag;
        try {
            TWfProcess process =processDao.GetEntity(TWfProcess.class, proId);
            process.setFFormPkid(formPKID);
            process.setFTitle(title); 
            process.setFIsHistory(isHistory);
            flag = processDao.Update(process);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;        
    }
    
	/**
	 * 设置业务过程当前办理人
	 * @param processId
	 * @param currentUserId
	 * @return
	 */
	public boolean setProcessCurrentUser(int processId, int currentUserId) {
		boolean flag;
		try {
			TWfProcess process =processDao.GetEntity(TWfProcess.class, processId);
			process.setFCurrentUserId(currentUserId);
			flag = processDao.Update(process);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;	
	}
	
	public boolean setProcessState(int processId,int state) {
		boolean flag;
		try {
			TWfProcess process =processDao.GetEntity(TWfProcess.class, processId);
			process.setFState(state);
			flag = processDao.Update(process);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public int getSingleActiveId(int processId) {
		int activeId=-1;
		try {
			String sql="select o from TWfProccessActive o where o.FState=0 and o.FProcessId=?1 order by o.FId desc";
			javax.persistence.Query query=activeDao.CreateQuery(sql, TWfProccessActive.class);
			query.setParameter(1, processId);
			TWfProccessActive active =(TWfProccessActive)query.getSingleResult();
			activeId =active.getFId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activeId;
	}
	
public boolean setActiveState(int activeId,int state) {		
		try {
			TWfProccessActive entity=activeDao.GetEntity(TWfProccessActive.class, activeId);
			entity.setFState(state);
			entity.setFCompleteTime(DateUtil.format());
			activeDao.Update(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
