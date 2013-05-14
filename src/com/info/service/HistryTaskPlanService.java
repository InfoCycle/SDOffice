package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TImplementPlan;
import com.info.domain.TTask;
import com.info.domain.TTeamPerson;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.domain.TWfProcessSend;
import com.info.domain.ViewImplementPlan;
import com.info.web.CurrentUser;

@Service
@Transactional
public class HistryTaskPlanService {
	@Autowired
	IBaseDao<TTask> taskDao;

	@Autowired
	IBaseDao<TImplementPlan> iPlanDao;

	@Autowired
	IBaseDao<ViewImplementPlan> iPlanViewDao;

	@Autowired
	IBaseDao<TTeamPerson> teamPersonDao;
	
	@Autowired
	IBaseDao<TWfProcessSend> processSendDao;
	
	@Autowired
	WfProcessUtils processUtils;
	
	@Autowired
	AppSEQHelper SEQHelper;
	
	@Autowired
	IBaseDao<TTask> serverTaskDao;
	
	@Autowired
	IBaseDao<TWfProccessActive> proccessActiveDao;
	
	@Autowired
    IBaseDao<TWfProcess> proccessDao;
	
	@Autowired
	BussCommonUtils commonUtils;
	
	
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
	
	@SuppressWarnings({ "unchecked" })
	public List<TTask> GetTaskListForID(Integer id) {
		String SQL = "select a.* from T_Task a where a.f_id= ?";
		javax.persistence.Query query = taskDao.CreateNativeSQL(SQL,
				TTask.class);
		query.setParameter(1, id);
		return (List<TTask>) query.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public List<ViewImplementPlan> GetTaskPlanForID(Integer id,Integer activeId) {
		//设置业务活动接收时间,修改：业务活动时间在签收时设置。
		//processUtils.setActiveAcceptTime(activeId);
		String SQL = "select a.* from View_ImplementPlan a where a.f_id= ?";
		javax.persistence.Query query = iPlanViewDao.CreateNativeSQL(SQL,
				ViewImplementPlan.class);
		query.setParameter(1, id);
		return (List<ViewImplementPlan>) query.getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public List<TImplementPlan> Insert(Integer taskId) {
		TImplementPlan objTaskPlan = new TImplementPlan();
		Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_TASKPLAN");
		objTaskPlan.setFId(fIdInteger);
		objTaskPlan.setFkTaskId(taskId);
		if (iPlanDao.Persist(objTaskPlan)) {
			String SQL = "select a.* from T_ImplementPlan a where a.f_id= ?";
			javax.persistence.Query query = iPlanDao.CreateNativeSQL(SQL,
					TImplementPlan.class);
			query.setParameter(1, fIdInteger);
			return (List<TImplementPlan>) query.getResultList();
		} else {
			return null;
		}
	}

	//初始化一个项目计划
	public  TImplementPlan iniTImplementPlan(Integer taskId,String JHNmubers) {
		TImplementPlan objTaskPlan = new TImplementPlan();
		Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_TASKPLAN");
		objTaskPlan.setFId(fIdInteger);
		objTaskPlan.setFkTaskId(taskId);
		objTaskPlan.setFPlanNumbers(JHNmubers);
		//初始化步骤
		objTaskPlan.setFCurrentStep("10");
		objTaskPlan.setFLastStep("10");
		objTaskPlan.setFRecordStep(objTaskPlan.getFRecordStep()+"{\"CurrentStep\":10,\"ActiveId\":"+"000"+"},");
		//保存项目计划
		if (iPlanDao.Persist(objTaskPlan)) {			 
			return iPlanDao.GetEntity(TImplementPlan.class, objTaskPlan.getFId());
		} else {
			return null;
		}
	}
	
	public Result Save(TImplementPlan objIPlan,Integer processId) {
		Result resultObj = new Result();
		if (objIPlan.getFId() > 0) {
			TImplementPlan updateObj = iPlanDao.GetEntity(TImplementPlan.class,
					objIPlan.getFId());
			updateObj.setFId(objIPlan.getFId());
			updateObj.setFPlanNumbers(objIPlan.getFPlanNumbers());
			updateObj.setFkTaskId(objIPlan.getFkTaskId());
			updateObj.setFCollectDataTime(objIPlan.getFCollectDataTime());
			updateObj.setFProcessImnTime(objIPlan.getFProcessImnTime());
			updateObj.setFSubmitRewTime(objIPlan.getFSubmitRewTime());
			updateObj.setFIssueResultsTime(objIPlan.getFIssueResultsTime());
			updateObj.setFOther(objIPlan.getFOther());
			// updateObj.setFDeptOpinion(objIPlan.getFDeptOpinion());
			// updateObj.setFDeptOpinionTime(objIPlan.getFDeptOpinionTime());
			updateObj.setFPlanningPerId(objIPlan.getFPlanningPerId());
			updateObj.setFPlanningPerName(objIPlan.getFPlanningPerName());
			updateObj.setFPlanningTime(objIPlan.getFProcessImnTime());
			resultObj.setSuccess(iPlanDao.Update(updateObj));
			resultObj.setId(updateObj.getFId());
			//更新标题
			processUtils.setProcessTitle(processId,updateObj.getFId(),getTaskName(updateObj.getFkTaskId()));//updateObj.getFPlanNumbers());
		}
		return resultObj;
	}

	public boolean Cancle(Integer id) {
		try {
			iPlanDao.Delete(TImplementPlan.class, id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
     * 
     * @Description : 催办下一步
     * @Author      : jibb
     * @Date        : 2013-1-19 上午12:02:38
     * @param aboveActId
     * @param remark
     * @return
     */
    public boolean UrgeSb(Integer aboveActId,String remark){
        try {
            return processUtils.activeUrge(aboveActId,remark);            
        } catch (Exception e) {
            return false;
        }
    }

	public boolean UpdateDeptOpinion(Integer FId,String FDeptOpinions,String FDeptOpinionTime) {
		boolean result=false;
		try {
			if (FId > 0) {
				TImplementPlan updateObj = iPlanDao.GetEntity(TImplementPlan.class,FId);				 
				updateObj.setFDeptOpinion(FDeptOpinions);
				updateObj.setFDeptOpinionTime(FDeptOpinionTime);
				result = iPlanDao.Update(updateObj);				 
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	// /////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 查询计划组员信息
	 */
	@SuppressWarnings({ "unchecked" })
	public List<TTeamPerson> GetTeamPersonListForIPlanID(
			Integer ImplementPlanId, Integer start, Integer length) {
		String SQL = "select a.* from T_TeamPerson a where a.FK_ImplementPlan_ID= ? order by F_Personnel_Name";
		javax.persistence.Query query = teamPersonDao.CreateNativeSQL(SQL,
				TTeamPerson.class);
		query.setParameter(1, ImplementPlanId);
		return (List<TTeamPerson>) query.getResultList();
	}

	/**
	 * 
	 * @Description	: 查询计划组员列表 
	 * @Author		: jibb
	 * @Date		: 2013-03-21 16-15
	 * @param pidInteger 计划ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<TTeamPerson> GetTeamPersonListForIPlanIDEasy(Integer pidInteger){
	    String SQL = "select a.* from T_TeamPerson a where a.FK_ImplementPlan_ID= ? order by F_Personnel_Name";
        javax.persistence.Query query = teamPersonDao.CreateNativeSQL(SQL,
                TTeamPerson.class);
        query.setParameter(1, pidInteger);
        return (List<TTeamPerson>) query.getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<TTeamPerson> SaveTeamPerson(TTeamPerson obj) {
		Integer fIdInteger = 0;
		List<TTeamPerson> resultList = null;
		if (obj.getFId() == 0) {
			fIdInteger = SEQHelper.getCurrentVal("SEQ_TEAMPERSON");
			obj.setFId(fIdInteger);
			if (teamPersonDao.Persist(obj)) {
				String SQL = "select a.* from T_TeamPerson a where a.f_id= ?";
				javax.persistence.Query query = teamPersonDao.CreateNativeSQL(
						SQL, TTeamPerson.class);
				query.setParameter(1, fIdInteger);
				resultList = (List<TTeamPerson>) query.getResultList();
			} else {
				return null;
			}
		} else if (obj.getFId() > 0) {
			TTeamPerson updateObj = teamPersonDao.GetEntity(TTeamPerson.class,
					obj.getFId());
			// updateObj.setFId(obj.getFId());
			updateObj.setFkImplementPlanId(obj.getFkImplementPlanId());
			updateObj.setFPersonnelId(obj.getFPersonnelId());
			updateObj.setFPersonnelName(obj.getFPersonnelName());
			updateObj.setFJobContent(obj.getFJobContent());
			updateObj.setFAsPosition(obj.getFAsPosition());
			updateObj.setFContactPhone(obj.getFContactPhone());
			updateObj.setFNote(obj.getFNote());
			if (teamPersonDao.Update(updateObj)) {
				String SQL = "select a.* from T_TeamPerson a where a.f_id= ?";
				javax.persistence.Query query = teamPersonDao.CreateNativeSQL(
						SQL, TTeamPerson.class);
				query.setParameter(1, obj.getFId());
				resultList = (List<TTeamPerson>) query.getResultList();
			}
		}
		return resultList;
	}

	public Result UpdateTeamPerson(TTeamPerson obj) {
		Result resultObj = new Result();
		if (obj.getFId() > 0) {
			TTeamPerson updateObj = teamPersonDao.GetEntity(TTeamPerson.class,
					obj.getFId());
			// updateObj.setFId(obj.getFId());
			updateObj.setFkImplementPlanId(obj.getFkImplementPlanId());
			updateObj.setFPersonnelId(obj.getFPersonnelId());
			updateObj.setFPersonnelName(obj.getFPersonnelName());
			updateObj.setFJobContent(obj.getFJobContent());
			updateObj.setFAsPosition(obj.getFAsPosition());
			updateObj.setFContactPhone(obj.getFContactPhone());
			updateObj.setFNote(obj.getFNote());
			resultObj.setSuccess(teamPersonDao.Update(updateObj));
			resultObj.setId(updateObj.getFId());
		}
		return resultObj;
	}

	public boolean DeleteTeamPerson(Integer id) {
		try {
			teamPersonDao.Delete(TTeamPerson.class, id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * 提交计划
	 * 过程id，接收人id,活动id,意见,表单id
	 */
	public boolean Post(Integer processId,Integer acceptUserId,Integer aboveActId,String remark,
			Integer formPKID){
		boolean flag=false;
		try {
			TImplementPlan objIPlan= iPlanDao.GetEntity(TImplementPlan.class, formPKID);
			if(objIPlan!=null){
			    //提交到部门20：为部门意见
			    objIPlan.setFCurrentStep("20");
			    objIPlan.setFLastStep("10");
			    objIPlan.setFRecordStep(objIPlan.getFRecordStep()+"{\"CurrentStep\":20,\"ActiveId\":"+aboveActId+"},");
				if(iPlanDao.Update(objIPlan)){
				    processUtils.setActiveAcceptTime(aboveActId);
					flag= processUtils.addProcessActiveItem(processId,acceptUserId,aboveActId,remark)>0 ? true : false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return flag;
	} 
	public boolean BanJie(Integer processId,Integer aboveActId,Integer formPKID){
		boolean flag=false;
		try {
			flag=processUtils.activeComplet(aboveActId);			
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	/**
	 * 抄送生产部及总工办
	 * @param processId
	 * @return
	 */	 
	public Result CopyAction(Integer processId,Integer aboveActId,Integer formPKID,String remark){
		Result resultObj = new Result();
		try {
		    //设置为办理完成
		    processUtils.setActiveState(aboveActId, 4);
		    processUtils.setProcessState(processId, 4);
		    TImplementPlan objIPlan= iPlanDao.GetEntity(TImplementPlan.class, formPKID);
            if(objIPlan!=null){
                //提交到部门20：为部门意见
                objIPlan.setFCurrentStep("50");
                objIPlan.setFLastStep("21");
                objIPlan.setFRecordStep(objIPlan.getFRecordStep()+"{\"CurrentStep\":50,\"ActiveId\":"+aboveActId+"},");
                iPlanDao.Update(objIPlan);
            }
			//生产副总
			String [] userSCList=commonUtils.getAppUserForStation(1002).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
			//技术负责人
			String [] userJSList=commonUtils.getAppUserForStation(1003).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
			//副总工
			String [] userFGList=commonUtils.getAppUserForStation(1006).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
			
			//抄送
			int i=0;
			for(i=0;i<userSCList.length;i++){				 
				//processUtils.addProcessActiveItemByStation(processId, 1002, Integer.parseInt(userSCList[i]), aboveActId, "抄送生产部");
			    addProcessSend(processId,  Integer.parseInt(userSCList[i]), remark);
			}		
			for (i=0;i<userJSList.length;i++) {
				//processUtils.addProcessActiveItemByStation(processId, 1003, Integer.parseInt(userJSList[i]), aboveActId, "抄送总工办");
			    addProcessSend(processId,  Integer.parseInt(userJSList[i]), remark);
			}
			for (i=0;i<userFGList.length;i++) {
				//processUtils.addProcessActiveItemByStation(processId, 1006, Integer.parseInt(userFGList[i]), aboveActId, "抄送总工办");
			    addProcessSend(processId,  Integer.parseInt(userFGList[i]), remark);
			}
			resultObj.setId(1);
			resultObj.setSuccess(true);
			resultObj.setMsg("已成功抄送到生产部和总工办。请注意办理情况。");
		} catch (Exception e) {
			resultObj.setId(0);
			resultObj.setSuccess(false);
		}
		return resultObj;
	}
	/**
	 * 签收
	 * @param aboveActId
	 * @param remark
	 * @return
	 */
	public boolean Acceptance(Integer aboveActId,String remark,Integer FormPKID,Integer isUpdateOther){
		try {
			//return processUtils.activeReturn(aboveActId,remark);	
			// 设置业务活动接收接收人		    
			processUtils.setActiveAcceptUser(aboveActId, SystemCurrentUser
					.getCurrentUser().getUserID());
			// 设置业务活动接收时间
			processUtils.setActiveAcceptTime(aboveActId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 
	 * @Description	: 阅读
	 * @Author		: jibb
	 * @Date		: 2013-03-22 16-07
	 * @param processId
	 * @param aboveActId
	 * @param FormPKID
	 * @return
	 */
    public boolean Read(Integer processId,Integer aboveActId,Integer FormPKID){
	    try {
	        //String sQLString="select count(f_id) as rows from T_WF_PROCCESS_ACTIVE where F_PROCESS_ID="+processId+" and f_state<>4";
	        String sQLString="select count(f_id) as rows from T_WF_PROCESS_SEND where F_PROCESS_ID="+processId+" and f_state<>1";	        
            //processUtils.setActiveState(aboveActId, 4); 
	        processUtils.setReadState(aboveActId);
            int rows=(Integer)proccessDao.CreateNativeSQL(sQLString).getSingleResult();
            if(rows==0)
            {
                processUtils.setProcessState(processId, 4);               
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 
     * @Description	: //更新抄送件的阅读状态和阅读时间 最新
     * @Author		: jibb
     * @Date		: 2013-04-02 10-06
     * @param aboveActId
     * @return
     */
    public boolean ReadExt(Integer aboveActId){
        try {
            
            return processUtils.setReadState(aboveActId);           
        } catch (Exception e) {
            return false;
        }
    }
	/**
	 * 
	 * @Description	: 审核通过、不通过
	 * @Author		: jibb
	 * @Date		: 2013-03-22 14-34
	 * @param processId
	 * @param acceptUserId
	 * @param aboveActId
	 * @param remark
	 * @param formPKID
	 * @param isApproval
	 * @return
	 */
	public boolean Approval(Integer processId,Integer acceptUserId,Integer aboveActId,String remark,
            Integer formPKID,Integer isApproval){
	    boolean flag=false;
	    try {
            TImplementPlan objIPlan= iPlanDao.GetEntity(TImplementPlan.class, formPKID);
            if(objIPlan!=null){
                if(isApproval==1){
                    objIPlan.setFCurrentStep("21");  //21通过                 
                }
                else{
                    objIPlan.setFCurrentStep("22");  //22不通过          
                }
                objIPlan.setFLastStep("20");
                objIPlan.setFRecordStep(objIPlan.getFRecordStep()+"{\"CurrentStep\":"+objIPlan.getFCurrentStep()+",\"ActiveId\":"+aboveActId+"},");
                if(iPlanDao.Update(objIPlan)){
                    processUtils.setActiveState(aboveActId, 4);
                    flag= processUtils.addProcessActiveItem(processId,acceptUserId,aboveActId,remark)>0 ? true : false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return flag;
	}
	/**
	 * 
	 * @Description	: 增加抄送活动
	 * @Author		: jibb
	 * @Date		: 2013-04-01 12-54
	 * @param processId 过程Id
	 * @param acceptUserId 接收人Id
	 * @param remark
	 * @return
	 */
	public Integer addProcessSend(int processId,int acceptUserId,String remark) {
	    Integer id=0;
	    TWfProcessSend objProcessSend=new TWfProcessSend();
	    try {
	        id=SEQHelper.getCurrentVal("SEQ_WF_SEND");
	        CurrentUser currentUser =SystemCurrentUser.getCurrentUser();
	        objProcessSend.setFId(id);
	        objProcessSend.setFProcessId(processId);
	        objProcessSend.setFSendUser(currentUser.getUserID());
	        objProcessSend.setFSendTime(DateUtil.format());
	        objProcessSend.setFAcceptUser(acceptUserId);
	        objProcessSend.setFRemark(remark);
	        objProcessSend.setFState(0);
	        processSendDao.Persist(objProcessSend);  
        } catch (Exception e) {
           e.printStackTrace();
        }
        return id;
    }
}
