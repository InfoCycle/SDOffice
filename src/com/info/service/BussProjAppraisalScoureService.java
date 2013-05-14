package com.info.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TAppUser;
import com.info.domain.TProjectAppraisalScoure;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.web.CurrentUser;

@Service
@Transactional
public class BussProjAppraisalScoureService {
	@Autowired
	IBaseDao<TProjectAppraisalScoure> pasDao;	
 
	@Autowired
	IBaseDao<TAppUser> userDao;
	
	@Autowired
	AppSEQHelper SEQHelper;
    
	@Autowired
	WfProcessUtils processUtils;
	
	@Autowired
	IBaseDao<TWfProccessActive> wfProccessActiveDao;
	
	@Autowired
	IBaseDao<TWfProcess> wfProcessDao;
		
	@SuppressWarnings({ "unchecked" })
	public List<TProjectAppraisalScoure> GetProjAppraisalScoureForId(Integer id,Integer activeId){
		try {
			//设置业务活动接收接收人
			processUtils.setActiveAcceptUser(activeId, SystemCurrentUser.getCurrentUser().getUserID());
			//设置业务活动接收时间
			//processUtils.setActiveAcceptTime(activeId);
			String SQL = "select a.* from T_ProjectAppraisalScoure a where a.F_Pas_Id= ? order by f_sort asc ";
			javax.persistence.Query query = pasDao.CreateNativeSQL(SQL,
					TProjectAppraisalScoure.class);
			query.setParameter(1, id);		
			return (List<TProjectAppraisalScoure>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	public Result Save(String TaskID,String FPasId,Integer processId,String Title,String FNumber) {
		Result resultObj = new Result();			
		String UpdateSQL="update T_ProjectAppraisalScoure set fk_Task_Id="+TaskID+",F_Number='"+FNumber+
				"' where F_Pas_Id="+FPasId;
		pasDao.ExecuteSQL(UpdateSQL);
		resultObj.setId(Integer.parseInt(FPasId));
		if(processUtils.setProcessTitle(processId,Integer.parseInt(FPasId),Title))
			resultObj.setSuccess(true);
		else
			resultObj.setSuccess(false);
		return resultObj;
	}
	
	private String[][] AppraisalContent= new String[][] {
			{"项目沟通协调","20"},
			{"内部协调","10"},
			{"外部协调","10"},
			{"计划执行情况","15"},
			{"质量控制","60"},
			{"成果文件完整性","5"},
			{"工作底稿","5"},
			{"成果质量","30"},
			{"报告（说明）","10"},
			{"加分奖励","10"},
			{"扣分情况","0"},
			{"归档情况","15"},
			{"合计","110"}
	};
	
	@SuppressWarnings("unchecked")
	public List<TProjectAppraisalScoure> Insert(Integer processId,Integer activeId){
		//Result resultObj = new Result();
		//resultObj.setId(0);
		//resultObj.setSuccess(false);
		Integer PASID=0;
		try {				
			CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
			if(loginUser.getUserID()>0){
				PASID = SEQHelper.getCurrentVal("SEQ_PASID");
				for(int i=1;i<=13;i++){
					TProjectAppraisalScoure objInsert= new TProjectAppraisalScoure();
					Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_PROJECTAS");
					objInsert.setFId(fIdInteger);
					objInsert.setFPasId(PASID);
					objInsert.setFSort(i);				
					objInsert.setFAppraisalContent(AppraisalContent[i-1][0]);
					objInsert.setFScore(Integer.parseInt(AppraisalContent[i-1][1]));
					objInsert.setFCurrentStep("10"); 
					objInsert.setFLastStep("10");
		            if(null==objInsert.getFRecordStep())
		                objInsert.setFRecordStep("");
		            objInsert.setFRecordStep(objInsert.getFRecordStep()+"{\"CurrentStep\":10,\"ActiveId\":"+activeId+"},");
					pasDao.Persist(objInsert);
				}
				//更新processId
				processUtils.setProcessTitle(processId, PASID, "起草考评打分");											
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String SQL = "select a.* from T_ProjectAppraisalScoure a where a.F_Pas_Id= ? order by f_sort asc ";
		javax.persistence.Query query = pasDao.CreateNativeSQL(SQL,
				TProjectAppraisalScoure.class);
		query.setParameter(1, PASID);		
		return (List<TProjectAppraisalScoure>) query.getResultList();
	}
	
	public boolean Cancle(Integer id) {
		try {
			pasDao.Delete(TProjectAppraisalScoure.class, id);			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//查询项目组成员和项目经理及项目名称
	@SuppressWarnings("unchecked")
	public List<String> getTaskPMAndPerson(Integer taskid){
		//List<String> objlist= new ArrayList<String>();
		String SQLString = "select a.f_id,a.f_task_name,dbo.FN_getTaskPM(a.f_id) as pmlist,dbo.FN_getTaskPerson(a.f_id) as personlist from dbo.T_Task a where a.f_id= ?";
		javax.persistence.Query query = pasDao.CreateNativeSQL(SQLString);			
		query.setParameter(1, taskid);		
		return (List<String>) query.getResultList();
		//return daoViewGroupUser.ExecuteSQLResult(SQLString, ViewGroupUser.class);		
	}
	
	////////////////////////////////////////////////////////////提交////////////////////////////////////////////////////////////////////////////////////////
	public boolean Post1007(Integer processId,String acceptUserId,Integer aboveActId,String remark,Integer formPKID,
			String FID0,String FDepartmentScore,String FDeptScoreThat,boolean isPost){
		boolean flag=false;
		try {				
			String[] fidList=FID0.split(",");
			String[] dsList= FDepartmentScore.split(",");
			String[] dstList= FDeptScoreThat.split(",");
			String UpdateSQL="";
			
			for(int i=0;i<fidList.length;i++){
				UpdateSQL+="update T_ProjectAppraisalScoure set F_DepartmentScore="+dsList[i]+",F_DeptScoreThat='"+dstList[i]+"' where F_ID="+fidList[i]+";";
				        //"' ,F_Current_Step='20',F_Last_Step='10',F_Record_Step=F_Record_Step+'"+recordStep+"' where F_ID="+fidList[i]+";";
			}
			flag=pasDao.ExecuteSQL(UpdateSQL)>0?true:false;
			if(isPost)
			{
			    //设置当前获得办理完成
			    processUtils.setActiveState(aboveActId, 4);              
                processUtils.setActiveAcceptTime(aboveActId);
				//发送给接收者,多个接收者
			    String recordStep="{\"CurrentStep\":10,\"ActiveId\":"+aboveActId.toString()+"},";
			    String updateStep="update T_ProjectAppraisalScoure set F_Current_Step='20',F_Last_Step='10',F_Record_Step=F_Record_Step+'"+recordStep+"' where F_PAS_ID="+formPKID.toString();
			    pasDao.ExecuteSQL(updateStep);
				String[] acceptUserIdStrings=acceptUserId.split(",");		
				for(int i=0;i<acceptUserIdStrings.length;i++){				
					//processUtils.addProcessActiveItem(processId,Integer.parseInt(acceptUserIdStrings[i]), aboveActId, remark);				
					processUtils.addProcessActiveItemByStation(processId, 
							getUserStationForID(Integer.parseInt(acceptUserIdStrings[i])), Integer.parseInt(acceptUserIdStrings[i]),aboveActId, remark);				
				}
				//更新当前办理人				
				processUtils.setProcessCurrentUser(processId,Integer.parseInt(acceptUserIdStrings[0]));
				//UpdateProcessState(processId);
			}
			//更新考评组成员
			UpdateAppraisalGroup(formPKID);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean Post1002(Integer processId,String acceptUserId,Integer aboveActId,String remark,Integer formPKID,
			String FID0,String FCompanyScore,String FCTS,boolean isPost){
		boolean flag=false;
		try {				
			String[] fidList=FID0.split(",");
			String[] csList= FCompanyScore.split(",");
			String[] ctstList= FCTS.split(",");
			String UpdateSQL="";
			for(int i=0;i<fidList.length;i++){
				UpdateSQL+="update T_ProjectAppraisalScoure set F_CompanyScore="+csList[i]+",F_CompanyThatScore='"+ctstList[i]+"' where F_ID="+fidList[i]+";";
			}
			flag=pasDao.ExecuteSQL(UpdateSQL)>0?true:false;
			if(isPost){
				//更新活动状态
				processUtils.setActiveState(aboveActId, 4); //办理完成。
				UpdateProcessState(processId);
			}
			//更新考评组成员
			UpdateAppraisalGroup(formPKID);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean Post1003(Integer processId,String acceptUserId,Integer aboveActId,String remark,Integer formPKID,
			String FID0,String FCompanyScore,String FCTS,boolean isPost){
		boolean flag=false;
		try {				
			String[] fidList=FID0.split(",");
			String[] csList= FCompanyScore.split(",");
			String[] ctstList= FCTS.split(",");
			String UpdateSQL="";
			for(int i=0;i<fidList.length;i++){
				UpdateSQL+="update T_ProjectAppraisalScoure set F_CompanyScore="+csList[i]+",F_CompanyThatScore='"+ctstList[i]+"' where F_ID="+fidList[i]+";";
			}
			flag=pasDao.ExecuteSQL(UpdateSQL)>0?true:false;
			if(isPost){
				//更新活动状态
				processUtils.setActiveState(aboveActId, 4); //办理完成。
				UpdateProcessState(processId);
			}
			//更新考评组成员
			UpdateAppraisalGroup(formPKID);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	//更新考评组成员
	public void UpdateAppraisalGroup(int pasid){
		String UpdateSQL="";
			String userNameString=SystemCurrentUser.getCurrentUser().getUserName();
			UpdateSQL="update T_ProjectAppraisalScoure set "+
					" F_AppraisalGroup=case isnull(F_AppraisalGroup,'') when '' then '"+userNameString+
					"' else F_AppraisalGroup+',"+userNameString+
					"' end where f_pas_id="+String.valueOf(pasid)+ " and charindex('"+userNameString+"',isnull(F_AppraisalGroup,''))=0";
			pasDao.ExecuteSQL(UpdateSQL);
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
	public boolean UpdateProcessState(int processid){
		//wfProccessActiveDao
		//查询TWFProcessActive
		String QUERYSQL="";
		QUERYSQL = "select a.* from T_WF_PROCCESS_ACTIVE a where a.F_PROCESS_ID= ? order by F_ID asc ";
		try {
			//如果TWFProcessActive 中 F_PROCESS_ID==processid的记录都为4,则将TWFProcess中F_ID==processid的F_State=4完成状态。
			javax.persistence.Query query = wfProccessActiveDao.CreateNativeSQL(QUERYSQL,
					TWfProccessActive.class);
			query.setParameter(1, processid);
			List<TWfProccessActive> wfProccessActivesList=(List<TWfProccessActive>)query.getResultList();		
			TWfProcess wfProcessUpdate=wfProcessDao.GetEntity(TWfProcess.class, processid);
			boolean isOk=true;
			for (TWfProccessActive tWfProccessActive : wfProccessActivesList) {
				isOk = isOk && (tWfProccessActive.getFState() ==4 ? true : false);
			}
			if(isOk)
			{
				if(wfProcessUpdate.getFId()>0)
				{
					//更新状体为：完成
					wfProcessUpdate.setFState(4);
					//更新完成时间为：系统当前时间
					wfProcessUpdate.setFCompleteTime(DateUtil.format());
					//更新当前办理人为，创建者
					wfProcessUpdate.setFCurrentUserId(wfProcessUpdate.getFCreateUserId());
					return wfProcessDao.Update(wfProcessUpdate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return (List<TProjectAppraisalScoure>) query.getResultList();		
		return false;
	}
	//////////////////////////////////////////////////////////提交////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 根据UserId获取人员岗位
	 * @param userID
	 * @return
	 */
	private int getUserStationForID(int userID) {
		TAppUser user =userDao.GetEntity(TAppUser.class, userID);
		return user.getFUnitStation();
	}
	
	@SuppressWarnings("unused")
    private int getUserForStationID(int stationId) {
		List<TAppUser> users =userDao.FindByProperty(TAppUser.class, "FUnitStation", stationId, null);
		if(users.size()>0){
			return users.get(0).getFId();
		}else{
			return 0;
		}
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
	/*
	 * 获得系统当期时间
	 */
	public String GetCurrentTime(){
		Date currentTime=new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  formatter.format(currentTime);
	}
}
