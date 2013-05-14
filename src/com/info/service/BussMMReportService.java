package com.info.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TMajorMattersReport;
import com.info.domain.TRelatedDetpOpinion;
import com.info.domain.TTask;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.domain.ViewDeptUsers;
import com.info.domain.ViewMmreport;

@Service
@Transactional
public class BussMMReportService {
	@Autowired
	IBaseDao<TMajorMattersReport> reportDao;
	
	@Autowired
	IBaseDao<TRelatedDetpOpinion> rdoDao;
	
	@Autowired
	IBaseDao<ViewDeptUsers> viewDUsersDao;
	
	@Autowired
	WfProcessUtils processUtils;
	
	@Autowired
	BussCommonUtils bussCommonUtils; 
	
	@Autowired
	IBaseDao<TWfProccessActive> wfProccessActiveDao;
	
	@Autowired
	IBaseDao<TWfProcess> wfProcessDao;
	
	@Autowired
	AppSEQHelper SEQHelper;
	
	@Autowired
	IBaseDao<TTask> serverTaskDao;
	

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
	
	public TMajorMattersReport GetReportForID(Integer id) {
		return reportDao.GetEntity(TMajorMattersReport.class, id);
	}

	@SuppressWarnings({ "unchecked" })
	public List<TMajorMattersReport> GetReportListForID(Integer id) {
		String SQL = "select a.* from T_MajorMattersReport a where a.f_id= ?";
		javax.persistence.Query query = reportDao.CreateNativeSQL(SQL,
				TMajorMattersReport.class);
		query.setParameter(1, id);
		return (List<TMajorMattersReport>) query.getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<ViewMmreport> GetViewReportListForID(Integer id,Integer activeId){
		String SQL = "select a.* from View_MMReport a where a.f_id= ?";
		javax.persistence.Query query = reportDao.CreateNativeSQL(SQL,
				ViewMmreport.class);
		query.setParameter(1, id);
		//processUtils.setActiveAcceptUser(activeId, SystemCurrentUser.getCurrentUser().getUserID());
		//processUtils.setActiveAcceptTime(activeId);
		return (List<ViewMmreport>) query.getResultList();
	}
	//相关部门意见
	@SuppressWarnings({ "unchecked" })
	public List<TRelatedDetpOpinion> GetRelateDOListForFKID(Integer fkid){
		String SQL = "select a.* from T_RelatedDetpOpinion a where a.FK_MajorMattersReport_Id= ?";
		javax.persistence.Query query = reportDao.CreateNativeSQL(SQL,
				TRelatedDetpOpinion.class);
		query.setParameter(1, fkid);
		return (List<TRelatedDetpOpinion>) query.getResultList();
	}
	
	public Result Save(TMajorMattersReport objReport,Integer processId) {
		Result resultObj = new Result();
		if (objReport.getFId() == null) {
			Integer FId = SEQHelper.getCurrentVal("SEQ_MMREPORT");
			objReport.setFId(FId);
			resultObj.setSuccess(reportDao.Persist(objReport));
			resultObj.setId(FId);
		} else if (objReport.getFId() > 0) {
			TMajorMattersReport updateObjReport=reportDao.GetEntity(TMajorMattersReport.class, objReport.getFId());
			updateObjReport.setFId(objReport.getFId());
			updateObjReport.setFkTaskId(objReport.getFkTaskId());
			updateObjReport.setFEakerId(SystemCurrentUser.getCurrentUser().getUserID());
			updateObjReport.setFNumbers(objReport.getFNumbers());
			updateObjReport.setFEakerName(SystemCurrentUser.getCurrentUser().getUserName());
			updateObjReport.setFProgress(objReport.getFProgress());
			updateObjReport.setFProgressId(objReport.getFProgressId());
			updateObjReport.setFMattersBriefly(objReport.getFMattersBriefly());
			updateObjReport.setFApplicationSupport(objReport.getFApplicationSupport());
			updateObjReport.setFQpopionion(objReport.getFQpopionion());
			//updateObjReport.setFDeptPrgOpinion(objReport.getFDeptPrgOpinion()); //部门意见
			//updateObjReport.setFTgmgrOpinion(objReport.getFTgmgrOpinion());//总经理意见
			updateObjReport.setFNote(objReport.getFNote());//备注
			resultObj.setSuccess(reportDao.Update(updateObjReport));
			resultObj.setId(updateObjReport.getFId());
			if(resultObj.getSuccess())
				if(processUtils.setProcessTitle(processId, updateObjReport.getFId(), getTaskName(updateObjReport.getFkTaskId())))//updateObjReport.getFNumbers()))
					resultObj.setSuccess(true);
				else
					resultObj.setSuccess(false);
		}
		return resultObj;
	}

	public boolean Cancle(Integer id) {
		try {
			reportDao.Delete(TMajorMattersReport.class, id);			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 
	 * @function: 此方法 不用
	 * @data: 2013-1-6下午5:49:00
	 * @author jibinbin
	 * @param processId
	 * @return
	 *
	 */
	@SuppressWarnings({ "unchecked" })
	public List<TMajorMattersReport> Insert(Integer processId) {
		TMajorMattersReport objReport = new TMajorMattersReport();
		Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_MMREPORT");
		objReport.setFId(fIdInteger);
		if (reportDao.Persist(objReport)) {
			//更新processId
			processUtils.setProcessTitle(processId, fIdInteger, "起草项目重大事项报告");			
			String SQL = "select a.* from T_MajorMattersReport a where a.f_id= ?";
			javax.persistence.Query query = reportDao.CreateNativeSQL(SQL,
					TMajorMattersReport.class);
			query.setParameter(1, fIdInteger);
			return (List<TMajorMattersReport>) query.getResultList();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @function:
	 * @data: 2013-1-6下午5:50:01
	 * @author jibinbin
	 * @param processId
	 * @return
	 *
	 */
	public Integer InsertExt(Integer processId,Integer activeId) {
		TMajorMattersReport objReport = new TMajorMattersReport();
		Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_MMREPORT");
		objReport.setFId(fIdInteger);
		//初始化步骤
		objReport.setFCurrentStep("10");
		objReport.setFLastStep("10");
		if(null==objReport.getFRecordStep())
		    objReport.setFRecordStep("");
		objReport.setFRecordStep(objReport.getFRecordStep()+"{\"CurrentStep\":10,\"ActiveId\":"+activeId+"},");
		if (reportDao.Persist(objReport)) {
			//更新processId
			processUtils.setProcessTitle(processId, fIdInteger, "起草项目重大事项报告");			
			return fIdInteger;
		} else {
			return -1;
		}
	}
	/**
	 * 
	 * @function: 提交相关部门
	 * @data: 2013-1-7下午2:48:28
	 * @author jibinbin
	 * @param processId
	 * @param acceptUserId
	 * @param aboveActId
	 * @param remark
	 * @param formPKID
	 * @return
	 *
	 */
	public boolean Post(Integer processId,String acceptUserId,Integer aboveActId,String remark,Integer formPKID,Integer postType){
		boolean flag=false;
		try {			
			//String[] acceptUserIdStrings=acceptUserId.split(",");
		    TMajorMattersReport objMMattersReport=reportDao.GetEntity(TMajorMattersReport.class, formPKID);
            if(null!=objMMattersReport)
            {
                String CurrentStep=objMMattersReport.getFCurrentStep();
                objMMattersReport.setFCurrentStep("30");
                objMMattersReport.setFLastStep(CurrentStep);
                objMMattersReport.setFRecordStep(objMMattersReport.getFRecordStep()+"{\"CurrentStep\":"+CurrentStep+",\"ActiveId\":"+aboveActId+"},");
                reportDao.Update(objMMattersReport);
            }
			List<ViewDeptUsers> acceptUserList=getUserInfo(acceptUserId);
			if(postType==1){				
				for(int i=0;i<acceptUserList.size();i++){
					TRelatedDetpOpinion objInsert= new TRelatedDetpOpinion();
					Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_RELATEDDETPOPINION");
					//List<String> userList=getUserInfo(Integer.parseInt(acceptUserIdStrings[i]));
					objInsert.setFId(fIdInteger);
					objInsert.setFkMajorMattersReportId(formPKID);				
					objInsert.setFDeptId(acceptUserList.get(i).getFkOrgId());
					objInsert.setFDeptName(acceptUserList.get(i).getFDeptname());
					objInsert.setFUserId(acceptUserList.get(i).getFkUserId());
					objInsert.setFUserName(acceptUserList.get(i).getFUsername());
					rdoDao.Persist(objInsert);
					//为接收者,添加获得
					processUtils.addProcessActiveItemByStation(processId,acceptUserList.get(i).getFUnitStation(), acceptUserList.get(i).getFkUserId(), aboveActId, remark);
				}			 
				//更新当前办理人
				processUtils.setProcessCurrentUser(processId,acceptUserList.get(0).getFkUserId());
			}else if(postType==2) { //部门提交，设置活动状态为办理完成
				processUtils.setActiveState(aboveActId, 4);				
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	/**
	 * 
	 * @Description	: 提交到部门经理
	 * @Author		: jibb
	 * @Date		: 2013-03-25 11-03
	 * @param processId
	 * @param acceptUserId
	 * @param aboveActId
	 * @param remark
	 * @param formPKID
	 * @return
	 */
	public boolean PostDeptMgr(Integer processId,Integer acceptUserId,Integer aboveActId,String remark,Integer formPKID){
        boolean flag=false;
        try { 
            TMajorMattersReport objMMattersReport=reportDao.GetEntity(TMajorMattersReport.class, formPKID);
            if(null!=objMMattersReport)
            {
                String CurrentStep=objMMattersReport.getFCurrentStep();
                objMMattersReport.setFCurrentStep("20");
                objMMattersReport.setFLastStep(CurrentStep);
                objMMattersReport.setFRecordStep(objMMattersReport.getFRecordStep()+"{\"CurrentStep\":"+CurrentStep+",\"ActiveId\":"+aboveActId+"},");
                reportDao.Update(objMMattersReport);
            }
            //设置获得接收日期
            processUtils.setActiveAcceptTime(aboveActId);
            //为接收者,添加获得
            processUtils.addProcessActiveItem(processId, acceptUserId, aboveActId, remark);
            //更新当前办理人
            processUtils.setProcessCurrentUser(processId,acceptUserId);
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
	/**
	 * banli wan cheng
	 * @function:
	 * @data: 2013-3-4上午9:43:36
	 * @author jibinbin
	 * @param processId
	 * @param aboveActId
	 * @param formPKID
	 * @return
	 *
	 */
	public boolean Complete(Integer processId,Integer aboveActId,Integer formPKID)
	{
		boolean flag=false;
		try {
			flag=processUtils.setActiveState(aboveActId, 4);
			//需要增加判断所有部门办理完成后，将TWfProcess表中的FState状态设置为4
			//修改，2013-3-25修改为所有部门办理完成后，提交到总经理。
			UpdateProcessState(processId,aboveActId,formPKID);			
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	/**
	 * 
	 * 
	 * @Description	: 总经理提交
	 * @Author		: jibb
	 * @Date		: 2013-03-25 16-44
	 * @param processId
	 * @param aboveActId
	 * @param formPKID
	 * @return
	 */
	public boolean PostGM(Integer processId,Integer aboveActId,Integer formPKID)
    {
        boolean flag=false;
        try {
           flag=processUtils.activeComplet(aboveActId);
           //更新重大事项报告步骤状态
           TMajorMattersReport objMMattersReport=reportDao.GetEntity(TMajorMattersReport.class, formPKID);
           if(null!=objMMattersReport)
           {
               String CurrentStep=objMMattersReport.getFCurrentStep();
               objMMattersReport.setFCurrentStep("50");
               objMMattersReport.setFLastStep(CurrentStep);
               objMMattersReport.setFRecordStep(objMMattersReport.getFRecordStep()+"{\"CurrentStep\":"+CurrentStep+",\"ActiveId\":"+aboveActId+"},");
               reportDao.Update(objMMattersReport);
           }
        } catch (Exception e) {
            return false;
        }
        return flag;
    }
	/**
	 * 通过Processid更新T_WF_PROCCESS
	 * @function:
	 * @data: 2013-2-22上午11:28:54
	 * @author jibinbin
	 * @param processid
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public boolean UpdateProcessState(int processid,int aboveActId,int formPKID){
		//wfProccessActiveDao
		//查询TWFProcessActive
		String QUERYSQL="";
		boolean flag=true;
		QUERYSQL = "select a.* from T_WF_PROCCESS_ACTIVE a where a.F_PROCESS_ID= ? order by F_ID asc ";		 
			//如果TWFProcessActive 中 F_PROCESS_ID==processid的记录都为4,
		    //老的处理则将TWFProcess中F_ID==processid的F_State=4完成状态。
		    //新的处理2013-3-25则提交到部门经理		    
			javax.persistence.Query query = wfProccessActiveDao.CreateNativeSQL(QUERYSQL,
					TWfProccessActive.class);
			query.setParameter(1, processid);
			List<TWfProccessActive> wfProccessActivesList=(List<TWfProccessActive>)query.getResultList();		
			//TWfProcess wfProcessUpdate=wfProcessDao.GetEntity(TWfProcess.class, processid);
			boolean isOk=true;
			for (TWfProccessActive tWfProccessActive : wfProccessActivesList) {
				isOk = isOk && (tWfProccessActive.getFState() ==4 ? true : false);
			}
			//如果相关部门都办理完成，则提交到部门经理
			if(isOk)
			{			    
			    String [] gmList=bussCommonUtils.getAppUserForStation(1000).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
			    if(null!=gmList){
			        if(gmList.length>0){
			            //更新重大事项报告步骤状态
			            TMajorMattersReport objMMattersReport=reportDao.GetEntity(TMajorMattersReport.class, formPKID);
			            if(null!=objMMattersReport)
			            {
			                String CurrentStep=objMMattersReport.getFCurrentStep();
			                objMMattersReport.setFCurrentStep("40");
			                objMMattersReport.setFLastStep(CurrentStep);
			                objMMattersReport.setFRecordStep(objMMattersReport.getFRecordStep()+"{\"CurrentStep\":"+CurrentStep+",\"ActiveId\":"+aboveActId+"},");
			                reportDao.Update(objMMattersReport);
			            }
			            //提交到总经理
			            Integer acceptUserId=Integer.parseInt(gmList[0]);			            
			            processUtils.addProcessActiveItemByStation(processid, 1000,acceptUserId ,aboveActId, "请总经理填写意见");
			            processUtils.setProcessCurrentUser(processid, Integer.parseInt(gmList[0]));
			            flag= true;
			        }
			    }
			    /*
				if(wfProcessUpdate.getFId()>0)
				{
					//更新状体为：完成
					wfProcessUpdate.setFState(4);
					//更新完成时间为：系统当前时间
					wfProcessUpdate.setFCompleteTime(DateUtil.format());
					//更新当前办理人为，自己
					//wfProcessUpdate.setFCurrentUserId(wfProcessUpdate.getFCreateUserId());
					return wfProcessDao.Update(wfProcessUpdate);
				}*/
			}		 
		//return (List<TProjectAppraisalScoure>) query.getResultList();		
		return flag;
	}
	/**
	 * 
	 * @Description	: 直接提交到总经理
	 * @Author		: jibb
	 * @Date		: 2013-03-26 10-23
	 * @param processId
	 * @param aboveActId
	 * @param remark
	 * @param formPKID
	 * @return
	 */
	public boolean PostToGM(Integer processId, Integer aboveActId,String remark,Integer  formPKID){
	    boolean flag=false;
        try {
            String [] gmList=bussCommonUtils.getAppUserForStation(1000).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
            if(null!=gmList){
                if(gmList.length>0){
                    processUtils.activeComplet(aboveActId);
                    //更新重大事项报告步骤状态
                    TMajorMattersReport objMMattersReport=reportDao.GetEntity(TMajorMattersReport.class, formPKID);
                    if(null!=objMMattersReport)
                    {
                        String CurrentStep=objMMattersReport.getFCurrentStep();
                        objMMattersReport.setFCurrentStep("40");
                        objMMattersReport.setFLastStep(CurrentStep);
                        objMMattersReport.setFRecordStep(objMMattersReport.getFRecordStep()+"{\"CurrentStep\":"+CurrentStep+",\"ActiveId\":"+aboveActId+"},");
                        reportDao.Update(objMMattersReport);
                    }
                    //提交到总经理
                    Integer acceptUserId=Integer.parseInt(gmList[0]);                       
                    processUtils.addProcessActiveItemByStation(processId, 1000,acceptUserId ,aboveActId, remark);
                    processUtils.setProcessCurrentUser(processId, Integer.parseInt(gmList[0]));
                    flag=true;
                }
            }else {
                flag=false;
            }           
        } catch (Exception e) {
            return false;
        }
        return flag;
	}
	
	/**
	 * 获得用户信息
	 * @function:
	 * @data: 2013-1-7下午2:32:23
	 * @author jibinbin
	 * @param userId 用户Id
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<ViewDeptUsers> getUserInfo(String userIdList){
		String[] tempList=userIdList.split(",");
		List<Integer> userIdL=new ArrayList<Integer>();
		for(String tString:tempList){
			userIdL.add(Integer.parseInt(tString));
		}
		String SQL="select a.* from View_Dept_Users a where a.fk_user_id in (:idlist)";
		javax.persistence.Query query = reportDao.CreateNativeSQL(SQL, ViewDeptUsers.class);		
		query.setParameter("idlist", userIdL);
		return (List<ViewDeptUsers>) query.getResultList();		
	}
	
	/**
	 * 签收
	 * @param aboveActId
	 * @param remark
	 * @return
	 */
	public boolean Acceptance(Integer aboveActId,String remark){
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
    /*
     * 获得系统当期时间
     */
    public String GetCurrentTime(){
        Date currentTime=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  formatter.format(currentTime);
    }
}
