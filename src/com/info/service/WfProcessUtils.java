package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.StringUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TAppUser;
import com.info.domain.TWfActiveReturn;
import com.info.domain.TWfActiveUrge;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.domain.TWfProcessSend;
import com.info.domain.ViewWfActive;
import com.info.domain.ViewWfActiveReturn;
import com.info.domain.ViewWfActiveUrge;
import com.info.web.CurrentUser;
import com.info.web.ResultMessage;

@Service
@Transactional
public class WfProcessUtils {
	@Autowired
	WfProcessService processService;
	@Autowired
	IBaseDao<TWfProccessActive> activeDao;
	@Autowired
	IBaseDao<TWfProcess> processDao;
	@Autowired
	IBaseDao<TAppUser> userDao;
	@Autowired
	IBaseDao<TWfActiveUrge> urgeDao;
	@Autowired
	IBaseDao<TWfActiveReturn> returnDao;
	@Autowired
	AppSEQHelper SEQHelper;
	
	@Autowired
	IBaseDao<TWfProcessSend> processSendDao;
	@Autowired
	WfProcessMessageService messageService;
	/**
	 * 新建业务过程
	 * @param typeid 业务类型
	 * @return processId
	 */
	public int addNewProcess(int typeid) {
		return processService.addNewProcess(typeid);		
	}
	/**
	 * 设置业务过程标题、业务表单ID
	 * @param processId
	 * @param formPKID
	 * @param title
	 * @return
	 */
	public boolean setProcessTitle(int processId,int formPKID,String title) {
		return processService.setProcessTitle(processId, formPKID, title);
	}
	
	/**
     * 设置业务过程标题、业务表单ID
     * @param processId
     * @param formPKID
     * @param title
     * @return
     * jibinbin  新增：param isHistory 0不是,1是
     */
    public boolean setProcessTitle(int processId,int formPKID,String title,Integer isHistory) {
        return processService.setProcessTitle(processId, formPKID, title,isHistory);
    }
	
	/**
	 * 设置业务过程当前办理人
	 * @param processId
	 * @param currentUserId
	 * @return
	 */
	public boolean setProcessCurrentUser(int processId, int currentUserId) {
		return processService.setProcessCurrentUser(processId, currentUserId);
	}
	
	/**
	 * 获取业务过程当前ActiveID
	 * @param processId
	 * @return
	 */
	public int getSingleActiveId(int processId) {
		return processService.getSingleActiveId(processId);
	}
	/**
	 * 设置业务过程办理状态
	 * @param processId
	 * @param state
	 * @return
	 */
	public boolean setProcessState(int processId,int state) {
		return processService.setProcessState(processId, state);
	}
	
	/**
	 * 设置业务过程办理状态
	 * @param activeId
	 * @return
	 */
	public boolean setActiveState(int activeId,int state) {
		return processService.setActiveState(activeId,state);
	}
	
	/**
	 * 业务提交时增加业务活动信息
	 * @param processId 业务过程ID
	 * @param acceptUserId 业务接受人
	 * @param aboveActId 上一步ActiveID
	 * @return
	 */
	public int addProcessActiveItem(int processId,int acceptUserId,int aboveActId,String remark) {
		int id=-1;		
		try {
			if(aboveActId>0){ //设置上一步完成状态
				TWfProccessActive aboveActive=activeDao.GetEntity(TWfProccessActive.class, aboveActId);
				aboveActive.setFCompleteTime(DateUtil.format());
				aboveActive.setFState(4);
				activeDao.Update(aboveActive);
			}
			id=SEQHelper.getCurrentVal("SEQ_WF_ACTIVE");
			CurrentUser currentUser =SystemCurrentUser.getCurrentUser();
			TWfProccessActive activeObj = new TWfProccessActive();
			activeObj.setFId(id);
			activeObj.setFProcessId(processId);
			activeObj.setFAboveActId(aboveActId);
			activeObj.setFSendUser(currentUser.getUserID());
			activeObj.setFSendStation(currentUser.getUnitStation());
			activeObj.setFSendTime(DateUtil.format());
			activeObj.setFAcceptUser(acceptUserId);
			activeObj.setFAcceptStation(getUserStationForID(activeObj.getFAcceptUser()));
			activeObj.setFAcceptTime(null);
			activeObj.setFState(1);
			activeObj.setFRemark(remark);
			activeDao.Persist(activeObj);
			//更新主过程表中的当前办理人
			TWfProcess process =processDao.GetEntity(TWfProcess.class, processId);
			process.setFCurrentUserId(activeObj.getFAcceptUser());
			process.setFState(1);
			processDao.Update(process);
			messageService.addMessage("提交", process.getFTitle(),acceptUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return id;
	}
	/**
	 * 按岗位提交任务
	 * @author liwx at 2013-1-6下午4:50:46
	 * @param processId
	 * @param acceptStationId
	 * @param aboveActId
	 * @param remark
	 * @return
	 */
	public int addProcessActiveItemByStation(int processId,int acceptStationId,int acceptUserId,int aboveActId,String remark) {
		int id=-1;		
		try {
			if(aboveActId>0){ //设置上一步完成状态
				TWfProccessActive aboveActive=activeDao.GetEntity(TWfProccessActive.class, aboveActId);
				aboveActive.setFCompleteTime(DateUtil.format());
				aboveActive.setFState(4);
				activeDao.Update(aboveActive);
			}
			id=SEQHelper.getCurrentVal("SEQ_WF_ACTIVE");
			CurrentUser currentUser =SystemCurrentUser.getCurrentUser();
			TWfProccessActive activeObj = new TWfProccessActive();
			activeObj.setFId(id);
			activeObj.setFProcessId(processId);
			activeObj.setFAboveActId(aboveActId);
			activeObj.setFSendUser(currentUser.getUserID());
			activeObj.setFSendStation(currentUser.getUnitStation());
			activeObj.setFSendTime(DateUtil.format());
			activeObj.setFAcceptStation(acceptStationId);
			//jibinbin 2013-2-22 modify
			activeObj.setFAcceptUser(acceptUserId);
			activeObj.setFAcceptTime(null);
			activeObj.setFState(1);
			activeObj.setFRemark(remark);
			activeDao.Persist(activeObj);
			//更新主过程表中的当前办理人
			TWfProcess process =processDao.GetEntity(TWfProcess.class, processId);
			process.setFCurrentUserId(activeObj.getFAcceptUser());
			process.setFState(1);
			processDao.Update(process);
			messageService.addMessage("提交", process.getFTitle(),getUserForStationID(acceptStationId));
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return id;
	}
	/**
	 * 根据UserId获取人员岗位
	 * @param userID
	 * @return
	 */
	private int getUserStationForID(int userID) {
		TAppUser user =userDao.GetEntity(TAppUser.class, userID);
		return user.getFUnitStation();
	}
	
	private int getUserForStationID(int stationId) {
		List<TAppUser> users =userDao.FindByProperty(TAppUser.class, "FUnitStation", stationId, null);
		if(users.size()>0){
			return users.get(0).getFId();
		}else{
			return 0;
		}
	}
	/**
	 * 设置业务活动接收时间
	 * @param activeId
	 */
	public void setActiveAcceptTime(int activeId) {
		if(activeId>0){
			CurrentUser user = SystemCurrentUser.getCurrentUser();
			int userId = user.getUserID();
			TWfProccessActive active=activeDao.GetEntity(TWfProccessActive.class, activeId);
			if(StringUtil.isEmpty(active.getFAcceptTime())&&active.getFAcceptUser()==userId){
				active.setFAcceptTime(DateUtil.format());
				active.setFState(1);
				activeDao.Update(active);
			}
		}
	}
	/**
	 * 接收人如果为空设置接收人
	 * @param activeId
	 * @param acceptUserId
	 * @return
	 */
	public boolean setActiveAcceptUser(int activeId,int acceptUserId) {
		boolean flag;
		try {
			TWfProccessActive active=activeDao.GetEntity(TWfProccessActive.class, activeId);
			if(active.getFAcceptUser()==null){
				active.setFAcceptUser(acceptUserId);
				active.setFState(1);
				activeDao.Update(active);
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 打回业务
	 * @param activeId
	 */
	public boolean activeReturn(int activeId,String remark) {
		boolean flag=false;
		try {
			TWfProccessActive active=activeDao.GetEntity(TWfProccessActive.class, activeId);
			TWfProcess process =processDao.GetEntity(TWfProcess.class, active.getFProcessId());
			active.setFState(4);//当前已办完，
			active.setFCompleteTime(DateUtil.format());
			process.setFState(2);//打回
			process.setFCurrentUserId(active.getFSendUser());
			//新增打回业务活动
			TWfProccessActive activeObj =new TWfProccessActive();
			activeObj.setFId(SEQHelper.getCurrentVal("SEQ_WF_ACTIVE"));
			activeObj.setFProcessId(process.getFId());
			activeObj.setFAboveActId(active.getFId());
			activeObj.setFSendUser(active.getFAcceptUser());
			activeObj.setFSendStation(active.getFAcceptStation());
			activeObj.setFSendTime(DateUtil.format());
			activeObj.setFAcceptUser(active.getFSendUser());
			activeObj.setFAcceptStation(active.getFSendStation());
			activeObj.setFAcceptTime(null);
			activeObj.setFRemark(remark);
			activeObj.setFState(2);
			TWfActiveReturn activeReturn = new TWfActiveReturn();
			activeReturn.setFId(SEQHelper.getCurrentVal("SEQ_WF_ACTIVE"));
			activeReturn.setFActiveId(activeId);
			activeReturn.setFReturnUser(SystemCurrentUser.getCurrentUser().getUserID());
			activeReturn.setFReturnToUser(active.getFSendUser());
			activeReturn.setFRemark(remark);
			activeReturn.setFReturnTime(DateUtil.format());
			returnDao.Persist(activeReturn);
			activeDao.Update(active);
			processDao.Update(process);
			activeDao.Persist(activeObj);
			messageService.addMessage("打回", process.getFTitle(),active.getFSendUser());
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;		
	}
	/**
	 * 催办业务，催办下一步	 * @param activeId
	 * @return
	 */
	public boolean activeUrge(int activeId,String remark) {
		boolean flag=false;
		try {	
			CurrentUser user=SystemCurrentUser.getCurrentUser();
			String time =DateUtil.format();
			
			String sql = "select a.* from dbo.T_WF_PROCESS a,dbo.T_WF_PROCCESS_ACTIVE b"
						+" where a.F_ID=b.F_PROCESS_ID and b.F_ID=?1";
			javax.persistence.Query query =processDao.CreateNativeSQL(sql, TWfProcess.class);
			query.setMaxResults(1);
			query.setParameter(1, activeId);			
			TWfProcess process = (TWfProcess)query.getSingleResult();
			
			List<TWfProccessActive> nextActives=getNextActive(activeId);			
			for (TWfProccessActive tWfProccessActive : nextActives) {
				tWfProccessActive.setFIsurge(1);
				activeDao.Update(tWfProccessActive);
				TWfActiveUrge urge=new TWfActiveUrge();
				urge.setFId(SEQHelper.getCurrentVal("SEQ_WF_URGE"));
				urge.setFUrgeUser(user.getUserID());
				urge.setFActiveId(tWfProccessActive.getFId());
				urge.setFUrgeTime(time);
				urge.setFRemark(remark);
				urgeDao.Persist(urge);
				messageService.addMessage("催办", process.getFTitle(),tWfProccessActive.getFAcceptUser());
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	private List<TWfProccessActive> getNextActive(int activeId) {
		List<TWfProccessActive> obj;
		javax.persistence.Query query;
		String sql="select o from TWfProccessActive o where o.FAboveActId=?1 and FState in(0,1,2)";
		query =activeDao.CreateQuery(sql);
		query.setParameter(1, activeId);
		obj= query.getResultList();
		//如果取不到下一步骤，则判断当前步骤发送人是不是当前发起催办的人，
		//如果是则向当前步骤发起催办
		if(null==obj||obj.size()==0){
			sql="select o from TWfProccessActive o where o.FId=?1 and o.FSendUser=?2 and FState in(0,1,2)";
			query =activeDao.CreateQuery(sql);
			query.setParameter(1, activeId);
			query.setParameter(2, SystemCurrentUser.getCurrentUser().getUserID());
			obj= query.getResultList();
		}
		return obj;
	}
	/**
	 * 所有业务活动完成	 * @param acticeId
	 */
	public boolean activeComplet(int activeId){	
		boolean flag=false;
		try {
			TWfProccessActive active=activeDao.GetEntity(TWfProccessActive.class, activeId);
			TWfProcess process =processDao.GetEntity(TWfProcess.class, active.getFProcessId());
			String timeString =DateUtil.format();
			active.setFState(4);
			active.setFCompleteTime(timeString);
			activeDao.Update(active);
			process.setFState(4);
			process.setFCompleteTime(timeString);
			processDao.Update(process);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 业务过程归档,检查是否还有未处理完成的业务活动，若有则不允许归档。	 * @param activeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResultMessage archivedProcess(int processId) {
		ResultMessage message =new ResultMessage();
		try {
			String sql="select o from TWfProccessActive o where o.FState in(0,1,2) and o.FProcessId=?1";
			javax.persistence.Query query = activeDao.CreateQuery(sql);
			query.setParameter(1, processId);			
			List<TWfProccessActive> active =query.getResultList();
			if(active.size()>0){
				message.setSuccess(false);
				message.setMessage("还有未办理完的业务，不允许归档！");
			}
			else{
				message.setSuccess(true);
				message.setMessage("业务过程归档成功！");
				TWfProcess process =processDao.GetEntity(TWfProcess.class, processId);
				String timeString =DateUtil.format();				
				process.setFState(5);
				process.setFArchiveTime(timeString);
				process.setFArchiveUser(SystemCurrentUser.getCurrentUser().getUserID());
				processDao.Update(process);
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * 判断业务过程是否可执行撤销操作
	 * @param processId
	 * @return
	 */
	public boolean isCanDestroy(int processId) {
		String sql="select count(o.FId) from TWfProccessActive o where o.FState not in(0,3) and o.FProcessId=?1";
		javax.persistence.Query query =processDao.CreateQuery(sql);
		query.setParameter(1, processId);
		int count=Integer.parseInt(query.getSingleResult().toString());
		return !(count>0);
	}
	/**
	 * 删除业务过程及业务活动	 * @param processId
	 * @return
	 */
	public boolean destroyProcess(int processId) {
		boolean flag=false;
		String sql;
		try {
			sql ="delete from TWfProcess o where o.FId=?1";
			javax.persistence.Query query =processDao.CreateQuery(sql);
			query.setParameter(1, processId);
			query.executeUpdate();
			sql ="delete from TWfProccessActive o where o.FProcessId=?1";
			javax.persistence.Query query1 =processDao.CreateQuery(sql);
			query1.setParameter(1, processId);
			query1.executeUpdate();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 业务办理情况列表
	 * @param processId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfActive> getWfActive(int processId) {
		String SQL="select o from ViewWfActive o where o.processId=?1 order by FId";
		javax.persistence.Query query=activeDao.CreateQuery(SQL, ViewWfActive.class);
		query.setParameter(1, processId);
		return query.getResultList();
	}
	
	/**
	 * 业务催办列表
	 * @param processId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfActiveUrge> getWfActiveUrges(int processId) {
		String SQL="select o from ViewWfActiveUrge o where o.processId=?1 order by FId";
		javax.persistence.Query query=activeDao.CreateQuery(SQL, ViewWfActiveUrge.class);
		query.setParameter(1, processId);
		return query.getResultList();
	}
	
	/**
	 * 业务打回记录
	 * @param processId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfActiveReturn> getWfActiveReturns(int processId) {
		String SQL="select o from ViewWfActiveReturn o where o.processId=?1 order by FId";
		javax.persistence.Query query=activeDao.CreateQuery(SQL, ViewWfActiveReturn.class);
		query.setParameter(1, processId);
		return query.getResultList();
	}
	/**
	 * @Description	: 更新抄送件的阅读状态和阅读时间
	 * @Author		: liwx
	 * @Date		: 2013-04-01 09-58
	 * @param fId
	 * @return
	 */
	public boolean setReadState(int fId) {
		TWfProcessSend obj= processSendDao.GetEntity(TWfProcessSend.class, fId);
		obj.setFAcceptTime(DateUtil.format());
		obj.setFState(1);
		return processSendDao.Update(obj);
	}
}
