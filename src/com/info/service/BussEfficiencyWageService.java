package com.info.service;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.StringUtil;
import com.info.domain.TAppGroup;
import com.info.domain.TAppOrgUser;
import com.info.domain.TAppUser;
import com.info.domain.TEfficiencyWage;
import com.info.domain.TExtractionDetailIs;
import com.info.domain.TExtractionDetailOp;
import com.info.domain.TExtractionDetailPm;
import com.info.domain.ViewTaskPersonnel;
import com.info.domain.ViewTaskPm;
import com.info.domain.ViewTaskQuery;
import com.info.domain.ViewWagePage;

@Service
@Transactional
public class BussEfficiencyWageService {

	@Autowired
	IBaseDao<ViewTaskPm> vProDao;
	
	@Autowired
	IBaseDao<ViewTaskPersonnel> vPersonnelDao;
	
	@Autowired
	IBaseDao<ViewTaskQuery> vQueryDao;
	
	@Autowired
	AppSEQHelper seqHelper;
	
	@Autowired
	IBaseDao<ViewWagePage> wageDao;
	
	@Autowired
	IBaseDao<TEfficiencyWage> wDao;
	
	@Autowired
	IBaseDao<TExtractionDetailPm> pmDao;
	
	@Autowired
	IBaseDao<TExtractionDetailIs> isDao;
	
	@Autowired
	IBaseDao<TExtractionDetailOp> opDao;

	@Autowired
	WfProcessUtils processService;
	
	@Autowired
	IBaseDao<TAppUser> appDao;
	
	@Autowired
	IBaseDao<TAppOrgUser> orgDao;
	
	@Autowired
	IBaseDao<TAppGroup> groupDao;
	
	/**
	 * 根据任务ID，获取任务信息
	 * @param Id
	 * @return
	 */
	public ViewTaskQuery getTask(int Id){
		ViewTaskQuery entitys= vQueryDao.GetEntity(ViewTaskQuery.class, Id);
		return entitys;
	}
	
	/**
	 * 根据任务ID，获取项目经理信息
	 * @param Id
	 * @return
	 */
	public List<ViewTaskPm> getProjectManage(int Id){
		String command="select o from ViewTaskPm o where 1=1 and o.taskId in("+Id+")";
		List<ViewTaskPm> entitys=vProDao.Query(command);
		return entitys;
	}
	
	/**
	 * 根据任务ID，获取项目人员信息
	 * @param Id
	 * @param eType (1：内部，0：外协)
	 * @return
	 */
	public List<ViewTaskPersonnel> getProjectPersonnelByType(int Id,int eType){
		String command="select o from ViewTaskPersonnel o where 1=1 and o.FEmployeeType="+eType+" and o.taskId in("+Id+")";
		List<ViewTaskPersonnel> entitys=vPersonnelDao.Query(command);
		return entitys;
	}
	
	/**
	 * 根据条件查询，获取效应工资数据集
	 * @param FNumber 效益编号
	 * @param FTaskName 任务名称
	 * @param FEntrustUnitName 代理机构
	 * @param FContractNumbers 合同编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWagePage> getWageList(String FNumber,String FTaskName,String FEntrustUnitName,String FContractNumbers){
		StringBuilder command=new StringBuilder();
		command.append("select o from ViewWagePage o where 1=1");
		if(!StringUtil.isEmpty(FNumber)){
			command.append(" and o.FNumbers like '%"+FNumber+"%'");
		}
		if(!StringUtil.isEmpty(FTaskName)){
			command.append(" and o.FTaskName like '%"+FTaskName+"%'");
		}
		if(!StringUtil.isEmpty(FEntrustUnitName)){
			command.append(" and o.FEntrustUnitName like '%"+FEntrustUnitName+"%'");
		}
		if(!StringUtil.isEmpty(FContractNumbers)){
			command.append(" and o.FContractNumbers like '%"+FContractNumbers+"%'");
		}
		command.append("order by o.FEntrustUnitName desc");
		Query query= wageDao.CreateQuery(command.toString(), ViewWagePage.class);
		return query.getResultList();
	}
	
	/**
	 * 根据条件查询，获取效应工资数据集总数
	 * @param FNumber 效益编号
	 * @param FTaskName 任务名称
	 * @param FEntrustUnitName 代理机构
	 * @param FContractNumbers 合同编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getWageCount(String FNumber,String FTaskName,String FEntrustUnitName,String FContractNumbers){
		StringBuilder command=new StringBuilder();
		command.append("select o from ViewWagePage o where 1=1");
		if(!StringUtil.isEmpty(FNumber)){
			command.append(" and o.FNumbers like '%"+FNumber+"%'");
		}
		if(!StringUtil.isEmpty(FTaskName)){
			command.append(" and o.FTaskName like '%"+FTaskName+"%'");
		}
		if(!StringUtil.isEmpty(FEntrustUnitName)){
			command.append(" and o.FEntrustUnitName like '%"+FEntrustUnitName+"%'");
		}
		if(!StringUtil.isEmpty(FContractNumbers)){
			command.append(" and o.FContractNumbers like '%"+FContractNumbers+"%'");
		}
		Query query= wageDao.CreateQuery(command.toString(), ViewWagePage.class);
		List<ViewWagePage> list= query.getResultList();
		return list.size();
	}
	
	/**
	 * 保存或者更新效益工资
	 * @param entityWage 效益工资主实体对象
	 * @param entityP 效益工资项目经理实体对象
	 * @param entityInside 效益工资内部员工实体对象
	 * @param entityOutside 效益工资外协人员实体对象
	 * @return
	 */
	public int saveorupdate(int ProcessID,String ProcessTitle,TEfficiencyWage entityWage,List<TExtractionDetailPm> entityP,
			List<TExtractionDetailIs> entityInside,List<TExtractionDetailOp> entityOutside){
		try{
			int wageID=0;
			if(entityWage!=null){
				if(entityWage.getFId()!=null){
					wageID=entityWage.getFId();
					UpdateEntity(entityWage,entityP,entityInside,entityOutside);
				}else{
					wageID= seqHelper.getCurrentVal(TEfficiencyWage.class.getSimpleName());
					InsertEntity(wageID,entityWage,entityP,entityInside,entityOutside);
					processService.setProcessTitle(ProcessID, wageID, ProcessTitle);
				}
			}else{
				return 0;
			}
			
			return wageID;
		}catch(Exception ex){
			return 0;
		}		
	}
	
	/**
	 * 添加效益工资
	 * @param entityWage 效益工资主实体对象
	 * @param entityP 效益工资项目经理实体对象
	 * @param entityInside 效益工资内部员工实体对象
	 * @param entityOutside 效益工资外协人员实体对象
	 * @return 成功&失败
	 */
	private boolean InsertEntity(int wageID,TEfficiencyWage entityWage,List<TExtractionDetailPm> entityP,
			List<TExtractionDetailIs> entityInside,List<TExtractionDetailOp> entityOutside){
		try{
			entityWage.setFId(wageID);
		    boolean entitys= wDao.Persist(entityWage);
		    if(entitys){
		    	for(TExtractionDetailPm pm:entityP){
		    	    int pmID=seqHelper.getCurrentVal(TExtractionDetailPm.class.getSimpleName());
		    	    pm.setFId(pmID);
		    		pm.setFkEfficiencyWageId(wageID);
		    		pmDao.Persist(pm);
		    	}
		    	
		    	for(TExtractionDetailIs inside:entityInside){
		    		 int insideID=seqHelper.getCurrentVal(TExtractionDetailIs.class.getSimpleName());
		    		 inside.setFId(insideID);
		    		 inside.setFkEfficiencyWageId(wageID);
		    		 isDao.Persist(inside);
		    	}
		    	
		    	for(TExtractionDetailOp outside: entityOutside){
		    		int outsideID=seqHelper.getCurrentVal(TExtractionDetailOp.class.getSimpleName());
		    		outside.setFId(outsideID);
		    		outside.setFkEfficiencyWageId(wageID);
		    		opDao.Persist(outside);
		    	}
		    	return true;
		    }else{
		    	return false;
		    }			
		}catch(Exception ex){
			return false;
		}
	}
	
	/**
	 * 更新效益工资
	 * @param entityWage 效益工资主实体对象
	 * @param entityP 效益工资项目经理实体对象
	 * @param entityInside 效益工资内部员工实体对象
	 * @param entityOutside 效益工资外协人员实体对象
	 * @return 成功&失败
	 */
	private boolean UpdateEntity(TEfficiencyWage entityWage,List<TExtractionDetailPm> entityP,
			List<TExtractionDetailIs> entityInside,List<TExtractionDetailOp> entityOutside){
		try{
			TEfficiencyWage entityW=wDao.GetEntity(TEfficiencyWage.class, entityWage.getFId());
			if(entityW!=null){
				entityW=entityWage;
				boolean entitys= wDao.Update(entityW);
				if(entitys){
					String command="select o from TExtractionDetailPm o where o.fkEfficiencyWageId="+entityWage.getFId();
					List<TExtractionDetailPm> pmList=pmDao.Query(command);
					for(TExtractionDetailPm pm :pmList){
						for(TExtractionDetailPm p :entityP){
							if(p.getFId() == pm.getFId()){
								TExtractionDetailPm entityPM=p;
								pmDao.Update(entityPM);
							}
						}
					}
					
					command="select o from TExtractionDetailIs o where o.fkEfficiencyWageId="+entityWage.getFId();
					List<TExtractionDetailIs> isList=isDao.Query(command);
					for(TExtractionDetailIs is :isList){
						for(TExtractionDetailIs inside :entityInside){
							if(inside.getFId() == is.getFId()){
								TExtractionDetailIs entityIs=inside;
								isDao.Update(entityIs);
							}
						}
					}
					
					command="select o from TExtractionDetailOp o where o.fkEfficiencyWageId="+entityWage.getFId();
					List<TExtractionDetailOp> opList=opDao.Query(command);
					for(TExtractionDetailOp op :opList){
						for(TExtractionDetailOp outside :entityOutside){
							if(outside.getFId() == op.getFId()){
								TExtractionDetailOp entityOP=outside;
								opDao.Update(entityOP);
							}
						}
					}
				}
			}else{
				return false;
			}
		   return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	/**
	 * 根据效益ID，获取基本信息，显示或者修改操作
	 * @param FId 效益ID
	 * @return
	 */
	public ViewWagePage getWagePage(int FId){
		ViewWagePage entitys=wageDao.GetEntity(ViewWagePage.class, FId);
		return entitys;
	}
	
	/**
	 * 根据效益ID，获取项目经理数据集
	 * @param wageID
	 * @return
	 */
	public List<TExtractionDetailPm> getPM(int wageID){
		String command="select o from TExtractionDetailPm o where 1=1 and o.fkEfficiencyWageId="+wageID;
		List<TExtractionDetailPm> list=pmDao.Query(command);
		return list;
	}
	
	/**
	 * 根据任务ID和人员ID，获取主要的项目经理
	 * @param taskId
	 * @param pmId
	 * @return
	 */
	public boolean getMostlyPM(int taskId,int pmId){
		String command="select o from ViewTaskPm o where o.taskId="+taskId+" and o.FPmId="+pmId+" and o.FPmType=1";
		List<ViewTaskPm> query= vProDao.Query(command);
		if(query.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据任务ID，获取主要的项目经理
	 * @param taskId
	 * @param pmId
	 * @return
	 */
	public List<ViewTaskPm> getMostlyPMByTaskID(int taskId){
		String command="select o from ViewTaskPm o where o.taskId="+taskId+" and o.FPmType=1";
		List<ViewTaskPm> query= vProDao.Query(command);
		return query;
	}
	
	/**
	 * 根据效益ID，获取内部员工数据集
	 * @param wageID
	 * @return
	 */
	public List<TExtractionDetailIs> getInside(int wageID){
		String command="select o from TExtractionDetailIs o where 1=1 and o.fkEfficiencyWageId="+wageID;
		List<TExtractionDetailIs> list=isDao.Query(command);
		return list;
	}
	
	/**
	 * 根据效益ID，获取外协人员数据集
	 * @param wageID
	 * @return
	 */
	public List<TExtractionDetailOp> getOutside(int wageID){
		String command="select o from TExtractionDetailOp o where 1=1 and o.fkEfficiencyWageId="+wageID;
		List<TExtractionDetailOp> list=opDao.Query(command);
		return list;
	}
	
	/**
	 * 根据效益工资ID，修改图片
	 * @param FId
	 * @param files
	 * @return
	 */
	public boolean editFile(int FId,String files){
		boolean entitys=true;
		TEfficiencyWage entityW=wDao.GetEntity(TEfficiencyWage.class, FId);
		if(entityW!=null){
			entityW.setFFile(files);
			entitys= wDao.Update(entityW);
		}
		return entitys;
	}
	
	/**
	 * 根据效益ID，删除效益工资,并同时删除业务流程
	 * @param FId
	 * @param processId
	 * @return
	 */
	public boolean deleteAll(int FId,int processId){
		String command="delete T_ExtractionDetail_PM  where FK_EfficiencyWage_ID in("+FId+")";
		String inside="delete T_ExtractionDetail_IS  where FK_EfficiencyWage_ID in("+FId+")";
		String outside="delete T_ExtractionDetail_OP  where FK_EfficiencyWage_ID in("+FId+")";
		try{
			boolean entity= processService.isCanDestroy(processId);
			System.out.println(entity);
			if(entity){
				wDao.Delete(TEfficiencyWage.class, FId);
				pmDao.ExecuteSQL(command);
				isDao.ExecuteSQL(inside);
				opDao.ExecuteSQL(outside);
				processService.destroyProcess(processId);
			}
			
		    return entity;
		}catch(Exception ex){
		   return false;
	    }
	}
	
	/**
	 * 打回
	 * @param activeId
	 * @param remark
	 * @return
	 */
	public boolean editPass(int activeId,String remark){
		try {
			
		  return processService.activeReturn(activeId, remark);
		
		}catch(Exception ex){
		  return false;
	   }
	}
	
	/**
	 * 根据岗位Ids，获取人员信息
	 * @param Ids
	 * @return
	 */
	public List<TAppUser> getAll(String Ids){
		String query="select o from TAppUser o where o.FUnitStation in("+Ids+")";
		List<TAppUser> entitys=appDao.Query(query);
		return entitys;
	}
	
	/**
	 * 根据stationID,获取岗位名称
	 * @param ids
	 * @return
	 */
	public List<TAppGroup> getAppGroups(String ids){
		String command="select o from TAppGroup o where o.FId in("+ids+")";
		List<TAppGroup> entitys= groupDao.Query(command);
		return entitys;
	}
	
	/**
	 * 根据填写人（项目经理）的ID获取部门经理信息,如果部门经理信息为空，则会异常
	 * @param orgID
	 * @param stationID
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TAppUser> getUserByStationID(String orgID,int userId){ 
		//TAppUser entity=appDao.GetEntity(TAppUser.class, userId);
		//if(entity!=null){
			StringBuilder str=new StringBuilder();
			str.append("select b.F_ID,b.F_USER_CODE,b.F_NAME,b.F_PASSWORD,b.F_STATE,b.F_SORT,");
			str.append("b.F_CREATE_TIME,b.F_CARDID,b.F_PHONE,b.F_UNIT_STATION ");
			str.append("from T_APP_ORG_USER  a ,T_APP_USER  b ");
			str.append(" WHERE a.fk_user_id = b.f_id and b.f_unit_station=1007 and a.fk_org_id="+orgID);
			Query query= appDao.CreateNativeSQL(str.toString(), TAppUser.class);
			return (List<TAppUser>)query.getResultList();
		//}
		
	}
	
	/*public List<TAppUser> getUserByStationID(String orgID,String stationID){
		List<TAppUser> entitys=new ArrayList<TAppUser>();
		String command="select o from TAppUser o where o.FUnitStation in("+stationID+")";
		List<TAppUser> users=appDao.Query(command);
		List<Integer> Ids=new ArrayList<Integer>();
		for(TAppUser user :users){
			Ids.add(user.getFId());
		}
		
		StringBuilder str=new StringBuilder();
		str.append("select o from TAppOrgUser o where o.fkUserId in("+StringUtils.join(Ids,",")+")");
		str.append(" and o.fkOrgId in("+orgID+")");
		List<TAppOrgUser> orgs= orgDao.Query(str.toString());
		for(TAppUser user :users){
			for(TAppOrgUser org :orgs){
				if(user.getFId() == org.getFkUserId()){
					entitys.add(user);
					return entitys;
				}
			}
		}
		
		return new ArrayList<TAppUser>();
	}*/
	
	/**
	 * 根据效益ID，编辑领导的签字
	 * @param columnName
	 * @param wId
	 * @param columnValue
	 * @return
	 */
	public boolean editBellwether(String columnName,int wId,String columnValue){
		boolean entitys=true;
		try{
		TEfficiencyWage entityW=wDao.GetEntity(TEfficiencyWage.class, wId);
		if(entityW!=null){
			TEfficiencyWage.class.getMethod(columnName,String.class).invoke(entityW,columnValue);
			entitys= wDao.Update(entityW);
		}
		}catch(Exception ex){
			ex.printStackTrace();
			entitys=false;
		}
		return entitys;
	}
	
	/**
	 * 根据效益ID，查询是否都签字（财务部、综合部、部门经理、生产副总）
	 * @return
	 */
	public List<String> existSignature(int FId){
		List<String> str=new ArrayList<String>();
		TEfficiencyWage entity=wDao.GetEntity(TEfficiencyWage.class, FId);
		if(entity!=null){
			if(StringUtil.isEmpty(entity.getFFinanceDept())){
				str.add("财务部");
			}
			if(StringUtil.isEmpty(entity.getFIntegratedDept())){
				str.add("综合部");
			}
			if(StringUtil.isEmpty(entity.getFDeptMgr())){
				str.add("部门经理");
			}
			if(StringUtil.isEmpty(entity.getFProductionChief())){
				str.add("生产副总");
			}
		}
		return str;
	}
}
