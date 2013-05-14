package com.info.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.StringUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TImplementPlan;
import com.info.domain.TTask;
import com.info.domain.TTaskIndustrys;
import com.info.domain.TTaskPm;
import com.info.web.CurrentUser;

@Service
@Transactional
public class BussTaskService {
	@Autowired
	IBaseDao<TTask> taskDao;
	
	@Autowired
	IBaseDao<TTaskIndustrys> taskIndustrys;
	
	@Autowired
	IBaseDao<TTaskPm> taskTaskPm;	 
	

	@Autowired
	AppSEQHelper SEQHelper;
    
	@Autowired
	WfProcessUtils processUtils;
	
	@Autowired	
	BussCommonUtils commUtils;
	
	@Autowired
	BussTaskPlanService bTPService;
	
	public TTask GetTaskForID(Integer id) {
		return taskDao.GetEntity(TTask.class, id);
	}

	@SuppressWarnings({ "unchecked" })
	public List<TTask> GetTaskListForID(Integer id,Integer activeId) {
		try {
			//设置业务活动接收时间
			//取消，在签收时设置。
			//processUtils.setActiveAcceptTime(activeId);
			String SQL = "select a.* from T_Task a where a.f_id= ?";
			javax.persistence.Query query = taskDao.CreateNativeSQL(SQL,
					TTask.class);
			query.setParameter(1, id);		
			return (List<TTask>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<TTaskPm> GetTaskPmListForID(Integer id) {
		try {			
			String SQL = "select a.* from T_Task_PM a where a.fk_Task_Id= ?";
			javax.persistence.Query query = taskTaskPm.CreateNativeSQL(SQL,
					TTaskPm.class);
			query.setParameter(1, id);		
			return (List<TTaskPm>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @Description	: 保存已有任务信息
	 * @Author		: jibb
	 * @Date		: 2013-03-20 18-10
	 * @param objTask
	 * @param processId
	 * @return
	 */
	public Result Save(TTask objTask,Integer processId) {
		Result resultObj = new Result();
		//以下代码不用 2013-3-20
		/*if (objTask.getFId() == null) {
			Integer FId = SEQHelper.getCurrentVal("SEQ_TASK");
			objTask.setFId(FId);
			
			Calendar cal = Calendar.getInstance();
			objTask.setFYear(cal.get(Calendar.YEAR));
			//新增任务时将下达人及时间初始化为当前登录用户及当前系统时间
			CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
			objTask.setFGivePersonId(loginUser.getUserID());
			objTask.setFGivePersonName(loginUser.getUserName());			
			objTask.setFGiveTime(GetCurrentTime());
			
			resultObj.setSuccess(taskDao.Persist(objTask));
			resultObj.setId(FId);
			
			processUtils.setProcessTitle(processId,objTask.getFId(),objTask.getFTaskName());
		} else
		*/if (objTask.getFId() > 0) {
			TTask updateObjTask=taskDao.GetEntity(TTask.class, objTask.getFId());
			updateObjTask.setFTaskNumbers(objTask.getFTaskNumbers());
			updateObjTask.setFTaskName(objTask.getFTaskName());
			updateObjTask.setFTaskShortName(objTask.getFTaskShortName());
			updateObjTask.setFEntrustUnitId(objTask.getFEntrustUnitId());
			updateObjTask.setFEntrustUnitName(objTask.getFEntrustUnitName());
			updateObjTask.setFProjectScale(objTask.getFProjectScale());
			updateObjTask.setFProjectScaleUnit(objTask.getFProjectScaleUnit());
			updateObjTask.setFStructureTypeId(objTask.getFStructureTypeId());
			updateObjTask.setFStructureType(objTask.getFStructureType());			
			updateObjTask.setFIndustryCategoryId(objTask.getFIndustryCategoryId());
			updateObjTask.setFIndustryCategoryName(objTask.getFIndustryCategoryName());
			updateObjTask.setFServiceCategory(objTask.getFServiceCategory());
			updateObjTask.setFServiceCategoryId(objTask.getFServiceCategoryId());
			updateObjTask.setFEntrustUnitShortName(objTask.getFEntrustUnitShortName());
			updateObjTask.setFContractYjCharge(objTask.getFContractYjCharge());
			updateObjTask.setFDepartmentId(objTask.getFDepartmentId());
			updateObjTask.setFDepartmentName(objTask.getFDepartmentName());
			updateObjTask.setFYjstartTime(objTask.getFYjstartTime());
			updateObjTask.setFYjfinishTime(objTask.getFYjfinishTime());
			updateObjTask.setFGivePersonId(objTask.getFGivePersonId());
			updateObjTask.setFGivePersonName(objTask.getFGivePersonName());
			updateObjTask.setFGiveTime(objTask.getFGiveTime());
			updateObjTask.setFBusinessCategory(objTask.getFBusinessCategory());
			updateObjTask.setFBusinessCategoryId(objTask.getFBusinessCategoryId());
			resultObj.setSuccess(taskDao.Update(updateObjTask));
			resultObj.setId(updateObjTask.getFId());
			//保存行业类别他为子表
			//先删除
			taskIndustrys.ExecuteSQL("delete from T_Task_Industrys where FK_TASK_ID="
					+ updateObjTask.getFId().toString());			
			//再插入新记录
			if(null!=updateObjTask.getFIndustryCategoryId()){
				String[] icIdList = updateObjTask.getFIndustryCategoryId().split(",");
				String[] icNameList = updateObjTask.getFIndustryCategoryName().split(",");
				try {
					for(int i=0;i<icIdList.length;i++){
						TTaskIndustrys tiObj=new TTaskIndustrys();
						Integer FId = SEQHelper.getCurrentVal("SEQ_TASK_INDUSTRYS");
						tiObj.setFId(FId);
						tiObj.setFkTaskId(updateObjTask.getFId());
						tiObj.setFIndustryCategoryId(Integer.parseInt(icIdList[i]));
						tiObj.setFIndustryCategoryName(icNameList[i]);
						taskIndustrys.Persist(tiObj);
					}
				} catch (Exception e) {
					return null;
				}
			}
			if(resultObj.getSuccess())
			    //更新活动标题和FormPKID
				if(processUtils.setProcessTitle(processId,updateObjTask.getFId(),updateObjTask.getFTaskName()))
					resultObj.setSuccess(true);
				else
					resultObj.setSuccess(false);
		}else {
		    resultObj.setSuccess(false);
		    resultObj.setId(0);
        }
		return resultObj;
	}

	/**
	 * 
	 * @Description	:撤销任务，在任务还没有提交前
	 * @Author		: jibb
	 * @Date		: 2013-03-20 18-13
	 * @param id
	 * @return
	 */
	public boolean Cancle(Integer id,Integer processId,Integer activeId) {
		try {
		    //撤销任务时删除业务过程及业务活动
		    processUtils.destroyProcess(processId);
		    //taskDao.ExecuteSQL("delete from TWF_PROCESS where F_ID="+processId);   
		    //taskDao.ExecuteSQL("delete from T_Wf_Proccess_Active  where F_Process_Id="+processId);
			taskDao.Delete(TTask.class, id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    /**
     * 
     * @Description	: 提交任务到部门经理
     * @Author		: jibb
     * @Date		: 2013-03-20 20-46
     * @param processId
     * @param acceptUserId
     * @param aboveActId
     * @param remark
     * @param formPKID
     * @param PFDepartmentId
     * @param PFDepartmentName
     * @param PFDeptMgrName
     * @return
     */
	public boolean Post(Integer processId,Integer acceptUserId,Integer aboveActId,String remark,
			Integer formPKID,Integer PFDepartmentId,String PFDepartmentName,String PFDeptMgrName){
		boolean flag=false;
		try {
			TTask objTask = taskDao.GetEntity(TTask.class, formPKID);
			if(objTask!=null){
			    CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
				objTask.setFDepartmentId(PFDepartmentId);
				objTask.setFDepartmentName(PFDepartmentName);
				objTask.setFDeptMgrId(acceptUserId);
				objTask.setFDeptMgrName(PFDeptMgrName);
				objTask.setFGivePersonId(loginUser.getUserID());
				objTask.setFGivePersonName(loginUser.getUserName());
				objTask.setFGiveTime(GetCurrentTime());
				//“项目任务书”任命项目经理
				objTask.setFCurrentStep("20");
				objTask.setFLastStep("10");
                objTask.setFRecordStep(objTask.getFRecordStep()+"{\"CurrentStep\":20,\"ActiveId\":"+aboveActId+"},");
				//发送到指定人acceptUserId
				if(taskDao.Update(objTask)){
				    processUtils.setActiveAcceptTime(aboveActId);
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
	 * @Description	: 打回上一步
	 * @Author		: jibb
	 * @Date		: 2013-3-19 下午3:10:56
	 * @param aboveActId
	 * @param remark
	 * @param formPKID
	 * @return
	 */
	public boolean Return(Integer aboveActId,String remark,Integer formPKID){
		try {
		    //不能更新任务信息,注释掉
		    /*TTask objTask = taskDao.GetEntity(TTask.class, formPKID);
		    //清空接收日期
            if(null!=objTask){
                //objTask.setFReceivingTaskTime("");
                taskDao.Update(objTask);
            }*/
		    //更新任务的FCurrentStep为上一步状态
		    TTask objTask = taskDao.GetEntity(TTask.class, formPKID);
		    if(null!=objTask){
		        String CurrentStep="";
		        String LastStep="";
		        CurrentStep=objTask.getFCurrentStep();
		        LastStep= objTask.getFLastStep();		                
		        /*if(Integer.parseInt(objTask.getFCurrentStep())>Integer.parseInt(objTask.getFLastStep()))
		            objTask.setFCurrentStep(String.valueOf(Integer.parseInt(CurrentStep)-10));
		        else {
		            objTask.setFCurrentStep(String.valueOf(Integer.parseInt(CurrentStep)+10));
                }*/
		        objTask.setFCurrentStep(LastStep);
		        objTask.setFLastStep(CurrentStep);
		        objTask.setFRecordStep(objTask.getFRecordStep()+"{\"CurrentStep\":"+CurrentStep+",\"ActiveId\":"+aboveActId+"},");
		        taskDao.Update(objTask);
		    }
			return processUtils.activeReturn(aboveActId,remark);			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @Description	: 催办下一步
	 * @Author		: jibb
	 * @Date		: 2013-1-19 上午12:02:38
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
	/**
	 * 签收
	 * @param aboveActId
	 * @param remark
	 * @return
	 */
	public boolean Acceptance(Integer aboveActId,String remark,Integer FormPKID,Integer isUpdateOther){
		try {
			//是否更新其他信息
			if(isUpdateOther==1){
				//更新接收时间
				TTask objTask=taskDao.GetEntity(TTask.class, FormPKID);
				objTask.setFReceivingTaskTime(GetCurrentTime());
				taskDao.Update(objTask);
			}
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
	 * 接收任务
	 */
	public boolean AcceptTask(Integer taskId){
		boolean flag=false;
		try {
			TTask obj=taskDao.GetEntity(TTask.class, taskId);
			if(obj!=null){
				obj.setFReceivingTaskTime(new Date().toString());
				CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
				obj.setFDeptMgrId(loginUser.getUserID());
				obj.setFDeptMgrName(loginUser.getUserName());
				flag=taskDao.Update(obj);
			}
			else {
				flag=false;
			}
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	/*
	 * 指派项目经理
	 * 
	 */
	public boolean SetTaskPM(Integer taskId,Integer FProjMgrId,
			String FProjMgrName,String FProjMgrViceId,String FProjMgrViceName)
	{
		boolean flag=false;
		try {
			TTask obj=taskDao.GetEntity(TTask.class, taskId);
			if(obj!=null){
				//接收时间及接收人信息更新
				obj.setFReceivingTaskTime(GetCurrentTime());
				CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
				obj.setFDeptMgrId(loginUser.getUserID());
				obj.setFDeptMgrName(loginUser.getUserName());
				//主项目经理及副项目经理信息更新
				obj.setFProjMgrId(FProjMgrId);
				obj.setFProjMgrName(FProjMgrName);
				obj.setFProjMgrViceId(FProjMgrViceId);
				obj.setFProjMgrViceName(FProjMgrViceName);
				flag=taskDao.Update(obj);
			}
			else {
				flag=false;
			}			 
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	public boolean AppointPM(Integer ProcessId,Integer AboveActId,String Remark,Integer formPKID,Integer FProjMgrId,
			String FProjMgrName,String FProjMgrViceId,String FProjMgrViceName){
		boolean flag=false;
		try {
			//1指派项目经理
			TTask objTask=taskDao.GetEntity(TTask.class, formPKID);
			if(objTask!=null){
				//接收时间及接收人信息更新
				objTask.setFReceivingTaskTime(GetCurrentTime());
				CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
				objTask.setFDeptMgrId(loginUser.getUserID());
				objTask.setFDeptMgrName(loginUser.getUserName());
				//“项目任务书”项目经理签收
				objTask.setFCurrentStep("30");
				objTask.setFLastStep("20");
                objTask.setFRecordStep(objTask.getFRecordStep()+"{\"CurrentStep\":30,\"ActiveId\":"+AboveActId+"},");
				//项目经理存储为子  表，不在存储在主表中
				objTask.setFProjMgrId(FProjMgrId);
                objTask.setFProjMgrName(FProjMgrName);
                objTask.setFProjMgrViceId(FProjMgrViceId);
                objTask.setFProjMgrViceName(FProjMgrViceName);
				flag=taskDao.Update(objTask);
				//主项目经理及副项目经理信息更新
				//插入项目经理信息										
				//清空项目经理
				taskTaskPm.ExecuteSQL("delete from T_Task_PM where FK_TASK_ID="
						+ objTask.getFId().toString());			
				//再插入新记录
				String[] pmvIdList = FProjMgrViceId.split(",");
				String[] pmvNameList = FProjMgrViceName.split(",");
				try {
					TTaskPm pmObj=new TTaskPm();
					Integer FId = SEQHelper.getCurrentVal("SEQ_TASK_PM");
					pmObj.setFId(FId);
					pmObj.setFkTaskId(objTask.getFId());
					pmObj.setFPmId(FProjMgrId);
					pmObj.setFPmName(FProjMgrName);
					pmObj.setFPmType(1);
					taskTaskPm.Persist(pmObj);
					for(int i=0;i<pmvIdList.length;i++){
					    if(Integer.parseInt(pmvIdList[i])==-1)
					        break;
						TTaskPm tiObj=new TTaskPm();
						FId = SEQHelper.getCurrentVal("SEQ_TASK_PM");
						tiObj.setFId(FId);
						tiObj.setFkTaskId(objTask.getFId());
						tiObj.setFPmId(Integer.parseInt(pmvIdList[i]));
						tiObj.setFPmName(pmvNameList[i]);
						tiObj.setFPmType(0);
						taskTaskPm.Persist(tiObj);
					}
					flag=true;
				} catch (Exception e) {
					flag=false;
				}
			}					
			//if(SetTaskPM(formPKID,FProjMgrId,FProjMgrName, FProjMgrViceId, FProjMgrViceName)){
			if(flag){
			    flag=false;			    
			    processUtils.setProcessState(ProcessId, 4); //办理完成
			    if(processUtils.addProcessActiveItem(ProcessId, FProjMgrId, AboveActId, Remark)>0)
			        flag=true;
			    else 
                    flag=false;                
			    /* 注销
				flag=false;
				//2新建任务计划书
				TImplementPlan objImplementPlan=bTPService.iniTImplementPlan(formPKID);
				//3新建业务过程
				if(objImplementPlan!=null){
					Integer processId=processUtils.addNewProcess(10002);
					//4更新业务过程formID,标题为空
					//int processId,int acceptUserId,int aboveActId,String remark
					if(processUtils.addProcessActiveItem(processId,FProjMgrId,AboveActId,Remark)>0){
						processUtils.setProcessState(ProcessId, 4); //办理完成
						flag=processUtils.setProcessTitle(processId, objImplementPlan.getFId(),objTask.getFTaskName());
					}
					else {
						flag=false;
					}
				}else {
					flag=false;
				}
				*/
			}else flag=false;
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
	/**
	 * 起草任务
	 * @Description	: TODO(这里用一句话描述这个方法的作用)
	 * @Author		: jibb
	 * @Date		: 2013-3-20 上午11:05:34
	 * @param TaskNumber 任务编号
	 * @param TaskID   任务ID
	 * @param TaskName 任务名称
	 * @return
	 */
	public boolean AddTaskPlan(String TaskNumber,Integer TaskID,String TaskName,Integer ProcessId,Integer AboveActId,String JHNmubers){
	    boolean flag=false;
	    try {                   
    	    //1新建任务计划书	                   
            TTask objTask=taskDao.GetEntity(TTask.class, TaskID);
            if(null!=objTask){
                TImplementPlan objImplementPlan=bTPService.iniTImplementPlan(TaskID,JHNmubers);
                //“项目任务书”起草计划
                objTask.setFCurrentStep("40");
                objTask.setFLastStep("30");
                objTask.setFRecordStep(objTask.getFRecordStep()+"{\"CurrentStep\":40,\"ActiveId\":"+AboveActId+"},");
                taskDao.Update(objTask);
                //2新建业务过程
                if(null!=objImplementPlan){
                    //当前业务办理完成
                    processUtils.setProcessState(ProcessId, 4);
                    processUtils.setActiveState(AboveActId, 4);
                    //新增计划业务
                    Integer processId=processUtils.addNewProcess(10002);
                    //3更新业务过程formID,标题为任务名称
                    flag=processUtils.setProcessTitle(processId, objImplementPlan.getFId(), TaskName);               
                }
            }
            else {
                flag=false;
            }
	    } catch (Exception e) {
	        return false;
        }
	    return flag;
	}
	/**
	 * 方法被替换Insert
	 * @param processId
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<TTask> InsertNotUser(Integer processId) {
		TTask objTask = new TTask();
		CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
		Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_TASK");
		objTask.setFId(fIdInteger);
		/*新增任务时将下达人及时间初始化为当前登录用户及当前系统时间*/
		objTask.setFGivePersonId(loginUser.getUserID());
		objTask.setFGivePersonName(loginUser.getUserName());			
		objTask.setFGiveTime(GetCurrentTime());
		//setFYear		 
		Calendar cal = Calendar.getInstance();
		objTask.setFYear(cal.get(Calendar.YEAR));
		if (taskDao.Persist(objTask)) {
			//更新processId
			processUtils.setProcessTitle(processId, fIdInteger, "起草任务书");
			String SQL = "select a.* from T_Task a where a.f_id= ?";
			javax.persistence.Query query = taskDao.CreateNativeSQL(SQL,
					TTask.class);
			query.setParameter(1, fIdInteger);		
			return (List<TTask>) query.getResultList();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @Description	: 起草项目任务
	 * @Author		: jibb
	 * @Date		: 2013-03-20 18-17
	 * @param processId 活动ID
	 * @return
	 */
	public Result Insert(Integer processId,Integer activeId) {
		Result resultObj = new Result();
		resultObj.setId(0);
		resultObj.setSuccess(false);
		TTask objTask = new TTask();
		try {
		    //判断活动中是否已经存在项目任务ID
			Integer FormPKID=commUtils.getFormPKIDForProcess(processId);
			if(null==FormPKID){
				CurrentUser loginUser= SystemCurrentUser.getCurrentUser();
				Integer fidInteger = SEQHelper.getCurrentVal("SEQ_TASK");					
				objTask.setFId(fidInteger);
				/*新增任务时将下达人及时间初始化为当前登录用户及当前系统时间*/
				objTask.setFGivePersonId(loginUser.getUserID());
				objTask.setFGivePersonName(loginUser.getUserName());			
				objTask.setFGiveTime(GetCurrentTime());
				//设置年度为系统当前年度		 
				Calendar cal = Calendar.getInstance();
				objTask.setFYear(cal.get(Calendar.YEAR));
				//初始化FCurrentStep为：10 “项目任务书”起草
				objTask.setFCurrentStep("10");
				objTask.setFLastStep("10");
				objTask.setFRecordStep((objTask.getFRecordStep()==null?"":objTask.getFRecordStep())+"{\"CurrentStep\":10,\"ActiveId\":"+activeId+"},");
				
				resultObj.setSuccess(taskDao.Persist(objTask));
				resultObj.setId(fidInteger);
				//更新活动表中FormPKID和标题			
				processUtils.setProcessTitle(processId, fidInteger, "起草任务书");				
			}else
			{
				resultObj.setSuccess(true);
				resultObj.setId(FormPKID);
			}
			return resultObj;
		} catch (Exception e) {
			return resultObj;
		}
	}
	/**
	 * 
	 * @Description	: 判断任务名称是否存在
	 * @Author		: jibb
	 * @Date		: 2013-03-28 14-17
	 * @param taskname 任务名称
	 * @return
	 */
	public boolean IsHaveTaskForTaskName(String taskname,Integer fIdInteger){
	    boolean flag=false;
	    String SQL = "select count(f_id) as rows from T_Task a where a.f_task_name= ? and a.f_id <> ?";
        javax.persistence.Query query = taskDao.CreateNativeSQL(SQL);
        query.setParameter(1, taskname); 
        query.setParameter(2, fIdInteger);
        Integer countInteger=(Integer)query.getSingleResult();
        if(countInteger>0)
            flag=true;
        return flag;
	}
	/*
	 * 获得系统当期时间
	 */
	public String GetCurrentTime(){
		Date currentTime=new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  formatter.format(currentTime);
	}
	/**********************************************修改记录登记-2013-4**********************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public String objectCompare(TTask CurrentObj) {
        //比对差异集合
        List list = new ArrayList();        
        //此处用任务对象实现

        //TTask currentObj  = (TTask)CurrentObj;
        TTask originalObj = taskDao.GetEntity(TTask.class, CurrentObj.getFId());
        
        //判断任务编号差异
        if(null!=originalObj.getFTaskNumbers()
                &&!originalObj.getFTaskNumbers().equals(CurrentObj.getFTaskNumbers())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "任务编号");
            diffMap.put("originalValue", originalObj.getFTaskNumbers());
            diffMap.put("currentValue", CurrentObj.getFTaskNumbers());
            list.add(diffMap);
        }
        
      //判断任务名称差异
        if(null!=originalObj.getFTaskName()
                &&!originalObj.getFTaskName().equals(CurrentObj.getFTaskName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "任务名称");
            diffMap.put("originalValue", originalObj.getFTaskName());
            diffMap.put("currentValue", CurrentObj.getFTaskName());
            list.add(diffMap);
        }
        
      //判断任务简称差异
        if(null!=originalObj.getFTaskShortName()
                &&!originalObj.getFTaskShortName().equals(CurrentObj.getFTaskShortName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "任务简称");
            diffMap.put("originalValue", originalObj.getFTaskShortName());
            diffMap.put("currentValue", CurrentObj.getFTaskShortName());
            list.add(diffMap);
        }
        
      //判断委托单位差异
        if(null!=originalObj.getFEntrustUnitName()
                &&!originalObj.getFEntrustUnitName().equals(CurrentObj.getFEntrustUnitName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "委托单位");
            diffMap.put("originalValue", originalObj.getFEntrustUnitName());
            diffMap.put("currentValue", CurrentObj.getFEntrustUnitName());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFProjectScale()
                &&!originalObj.getFProjectScale().equals(CurrentObj.getFProjectScale())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "项目规模");
            diffMap.put("originalValue", originalObj.getFProjectScale());
            diffMap.put("currentValue", CurrentObj.getFProjectScale());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFStructureType()
                &&!originalObj.getFStructureType().equals(CurrentObj.getFStructureType())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "结构类型");
            diffMap.put("originalValue", originalObj.getFStructureType());
            diffMap.put("currentValue", CurrentObj.getFStructureType());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFIndustryCategoryName()
                &&!originalObj.getFIndustryCategoryName().equals(CurrentObj.getFIndustryCategoryName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "行业类别");
            diffMap.put("originalValue", originalObj.getFIndustryCategoryName());
            diffMap.put("currentValue", CurrentObj.getFIndustryCategoryName());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFServiceCategory()
                &&!originalObj.getFServiceCategory().equals(CurrentObj.getFServiceCategory())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "服务类别");
            diffMap.put("originalValue", originalObj.getFServiceCategory());
            diffMap.put("currentValue", CurrentObj.getFServiceCategory());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFBusinessCategory()
                &&!originalObj.getFBusinessCategory().equals(CurrentObj.getFBusinessCategory())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "业务类别");
            diffMap.put("originalValue", originalObj.getFBusinessCategory());
            diffMap.put("currentValue", CurrentObj.getFBusinessCategory());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFContractYjCharge()
                &&!originalObj.getFContractYjCharge().equals(CurrentObj.getFContractYjCharge())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "合同预计收费");
            diffMap.put("originalValue", originalObj.getFContractYjCharge());
            diffMap.put("currentValue", CurrentObj.getFContractYjCharge());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFDepartmentName()
                &&!originalObj.getFDepartmentName().equals(CurrentObj.getFDepartmentName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "承接部门");
            diffMap.put("originalValue", originalObj.getFDepartmentName());
            diffMap.put("currentValue", CurrentObj.getFDepartmentName());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFDeptMgrName()
                &&!originalObj.getFDeptMgrName().equals(CurrentObj.getFDeptMgrName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "部门经理");
            diffMap.put("originalValue", originalObj.getFDeptMgrName());
            diffMap.put("currentValue", CurrentObj.getFDeptMgrName());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFYjstartTime()
                &&!originalObj.getFYjstartTime().equals(CurrentObj.getFYjstartTime())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "预计开始时间");
            diffMap.put("originalValue", originalObj.getFYjstartTime());
            diffMap.put("currentValue", CurrentObj.getFYjstartTime());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFYjfinishTime()
                &&!originalObj.getFYjfinishTime().equals(CurrentObj.getFYjfinishTime())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "预计结束时间");
            diffMap.put("originalValue", originalObj.getFYjfinishTime());
            diffMap.put("currentValue", CurrentObj.getFYjfinishTime());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFGivePersonName()
                &&!originalObj.getFGivePersonName().equals(CurrentObj.getFGivePersonName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "下达人");
            diffMap.put("originalValue", originalObj.getFGivePersonName());
            diffMap.put("currentValue", CurrentObj.getFGivePersonName());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFGiveTime()
                &&!originalObj.getFGiveTime().equals(CurrentObj.getFGiveTime())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "下达时间");
            diffMap.put("originalValue", originalObj.getFGiveTime());
            diffMap.put("currentValue", CurrentObj.getFGiveTime());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFReceivingTaskTime()
                &&!originalObj.getFReceivingTaskTime().equals(CurrentObj.getFReceivingTaskTime())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "任务接受时间");
            diffMap.put("originalValue", originalObj.getFReceivingTaskTime());
            diffMap.put("currentValue", CurrentObj.getFReceivingTaskTime());
            list.add(diffMap);
        }
        if(null!=originalObj.getFProjMgrName()
                &&!originalObj.getFProjMgrName().equals(CurrentObj.getFProjMgrName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "项目经理");
            diffMap.put("originalValue", originalObj.getFProjMgrName());
            diffMap.put("currentValue", CurrentObj.getFProjMgrName());
            list.add(diffMap);
        }
        if(null!=originalObj.getFProjMgrViceName()
                &&!originalObj.getFProjMgrViceName().equals(CurrentObj.getFProjMgrViceName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "项目副经理");
            diffMap.put("originalValue", originalObj.getFProjMgrViceName());
            diffMap.put("currentValue", CurrentObj.getFProjMgrViceName());
            list.add(diffMap);
        }
        return wrapCompareInfo(list);
        
    }
    /**
     * @Description : 包装比对差异描述
     * @Author      : liwx
     * @Date        : 2013-04-02 11-30
     * @param list
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked"})
    private String wrapCompareInfo(List list) {
        String result="";
        StringBuilder wrap = new StringBuilder();
        CurrentUser user= SystemCurrentUser.getCurrentUser();
        for (Object object : list) {
            Map<String, String> info =(Map<String, String>)object;
            if(StringUtil.isEmpty(wrap.toString())){
                wrap.append(user.getUserName()+"于"+DateUtil.format());
                wrap.append("修改了"+info.get("propertyField")+",");
                wrap.append("从原来的\""+info.get("originalValue")+"\"修改为\"");
                wrap.append(info.get("currentValue")+"\";");
            }else{
                wrap.append("\n同时");
                wrap.append("修改了"+info.get("propertyField")+",");
                wrap.append("从原来的\""+info.get("originalValue")+"\"修改为\"");
                wrap.append(info.get("currentValue")+"\";");
            }
        }
        if(StringUtil.isNotEmpty(wrap.toString()))
            result = wrap.toString()+"\n原因:(自填)";
        return result;
    }
          
    /**********************************************修改记录登记**********************************************************************/
}
