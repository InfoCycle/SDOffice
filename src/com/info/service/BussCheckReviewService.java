package com.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.StringUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TAccessories;
import com.info.domain.TCheckReview;
import com.info.domain.TProjectResultsFile;
import com.info.domain.TTask;
import com.info.domain.TWfProcess;
import com.info.domain.ViewWfProcess;
import com.info.web.CurrentUser;

@Service
@Transactional
public class BussCheckReviewService {

	@Autowired
	IBaseDao<TCheckReview> CheckReviewDao;

	@Autowired
    IBaseDao<TTask> TaskDao;
	
	@Autowired
	WfProcessUtils processUtils;

	@Autowired
	IBaseDao<TWfProcess> processDao;
	
	@Autowired
    IBaseDao<TProjectResultsFile> ProjectResultsFileDao;
    
    @Autowired
    IBaseDao<TAccessories> accessoriesDao;
    
	@Autowired
	AppSEQHelper seqHelper;
	
	

	/**
	 * 通过FId获得 信息
	 * 
	 * @function:
	 * @data: 2013-1-14下午9:32:59
	 * @author jibinbin
	 * @param fid
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<TCheckReview> getCheckReviewsForFID(Integer fid,
			Integer activeId) {
		try {
			/*if (activeId != -1) {
				// 设置业务活动接收接收人
				processUtils.setActiveAcceptUser(activeId, SystemCurrentUser
						.getCurrentUser().getUserID());
				// 设置业务活动接收时间
				processUtils.setActiveAcceptTime(activeId);
			}*/
			String SQL = "select  * from T_CheckReview o where o.f_id=?1 ";
			javax.persistence.Query query = CheckReviewDao.CreateNativeSQL(SQL,
					TCheckReview.class);
			query.setParameter(1, fid);
			return query.getResultList();
		} catch (Exception e) {
			return null;
			// e.printStackTrace();
		}

	}

	/**
	 * 通过ID 获得信息
	 * 
	 * @function:
	 * @data: 2013-1-17上午9:51:08
	 * @author jibinbin
	 * @param id
	 * @return
	 * 
	 */
	public TCheckReview getCheckReviewByID(Integer id) {
		return CheckReviewDao.GetEntity(TCheckReview.class, id);
	}

	/**
	 * 插入记录
	 * 
	 * @function:
	 * @data: 2013-1-14下午9:46:04
	 * @author jibinbin
	 * @return
	 * 
	 */
	public Result Insert(Integer processId, String Title,Integer activeId) {
		Result resultObj = new Result();
		resultObj.setId(0);
		resultObj.setSuccess(false);
		try {
			Integer fidInteger = seqHelper.getCurrentVal("SEQ_CHECKREVIEW");
			processUtils.setProcessTitle(processId, fidInteger, Title);
			TCheckReview objCheckReview = new TCheckReview();
			objCheckReview.setFId(fidInteger);
			objCheckReview.setFCurrentStep("10"); //项目经理起草
			objCheckReview.setFLastStep("10");
			if(null==objCheckReview.getFRecordStep())
			    objCheckReview.setFRecordStep("");
			objCheckReview.setFRecordStep(objCheckReview.getFRecordStep()+"{\"CurrentStep\":10,\"ActiveId\":"+activeId+"},");
			resultObj.setSuccess(CheckReviewDao.Persist(objCheckReview));
			resultObj.setId(fidInteger);			
			return resultObj;
		} catch (Exception e) {
			return resultObj;
		}
	}

	
	/**
	 * 保存记录
	 * 
	 * @function:
	 * @data: 2013-1-14下午9:51:11
	 * @author jibinbin
	 * @param objSave
	 * @return
	 * 
	 */
	public boolean Save(TCheckReview objSave, Integer processId, String Title) {
		try {
			processUtils.setProcessTitle(processId, objSave.getFId(), Title);
			return CheckReviewDao.Update(objSave);
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 
	 * @Description	: 历史任务保存
	 * @Author		: jibb
	 * @Date		: 2013-05-09 13-32
	 * @param objHistory
	 * @param TaskName
	 * @return
	 */
	public TCheckReview SaveHistory(TCheckReview objHistory,String TaskName){
	    TCheckReview resultCheckReview=null;
	    try {
            if(objHistory.getFId()>0){
                if(CheckReviewDao.Update(objHistory))
                    resultCheckReview=objHistory;
            }else {
                Integer fidInteger = seqHelper.getCurrentVal("SEQ_CHECKREVIEW");
                //新增过程及活动
                Integer processId= processUtils.addNewProcess(10006);
                processUtils.setProcessTitle(processId, fidInteger, TaskName,1);
                //新增复核记录                
                               
                objHistory.setFId(fidInteger);
                objHistory.setFCurrentStep("10"); //项目经理起草
                objHistory.setFLastStep("10");
                if(CheckReviewDao.Persist(objHistory))
                {
                    resultCheckReview=objHistory;
                }
            }
        } catch (Exception e) {
            resultCheckReview= null;
        }
	    return resultCheckReview;
	}
	/**
	 * 
	 * @Description	: 取消历史
	 * @Author		: jibb
	 * @Date		: 2013-05-10 12-35
	 * @param formPKID
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public boolean CancleHistory(Integer formPKID){
	   boolean flag=false;
	   String sQLString="";
	   String sDeleteString="";
	   flag=CheckReviewDao.Delete(TCheckReview.class, formPKID);
	   if(flag){
	       sQLString="select a.* from T_Project_ResultsFile a where a.FK_CheckReview_ID= ? ";
	       javax.persistence.Query query = ProjectResultsFileDao.CreateNativeSQL(sQLString,TProjectResultsFile.class);
	       query.setParameter(1, formPKID);
	       List<TProjectResultsFile> prfList= (List<TProjectResultsFile>) query.getResultList();
	       String idList="";
	       for (TProjectResultsFile tProjectResultsFile : prfList) {
	            ProjectResultsFileDao.Delete(TProjectResultsFile.class, tProjectResultsFile.getFId());
	            idList+=tProjectResultsFile.getFId().toString()+",";
           }
	       if(prfList.size()>0 && !idList.isEmpty()){
    	       idList=idList.substring(0,idList.length()-1);
               sDeleteString="delete from T_Accessories where FK_ID in ("+idList+")";           
               javax.persistence.Query queryA = accessoriesDao.CreateNativeSQL(sDeleteString,TAccessories.class);
               flag=(Integer)queryA.executeUpdate()>0?true:false;	
	       }
	       
	   }
	   sQLString="select F_ID from T_WF_PROCESS  where F_Form_PKID="+formPKID+" and F_isHistory=1";             
       int processId=(Integer)processDao.CreateNativeSQL(sQLString).getSingleResult();
       if(flag==true && processId>0)
       {
           flag=processUtils.destroyProcess(processId);
       }
       return flag;
	}
	/**
	 * 获得任务信息，包括：项目id，项目名称，委托单位，服务类别，项目规模
	 * 
	 * @function:
	 * @data: 2013-1-15下午4:41:51
	 * @author jibinbin
	 * @param id
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<String> getTaskInfo(Integer id) {
		String SQLString = "select t.f_id,t.f_name,t.f_entrustunit_name,t.f_service_categoryname,t.f_project_scale,t.f_project_scale_unit,f_task_numbers,t.f_deptmgr_id,t.f_department_id from dbo.View_Task t  where t.f_id= ?";
		javax.persistence.Query query = CheckReviewDao
				.CreateNativeSQL(SQLString);
		query.setParameter(1, id);
		return (List<String>) query.getResultList();
	}

	/**
	 * 复核申报提交
	 * @function:
	 * @data: 2013-1-17下午4:49:13
	 * @author jibinbin
	 * @param objPost
	 * @param processId
	 * @param acceptUserId
	 * @param aboveActId
	 * @param remark
	 * @param formPKID
	 * @return
	 *
	 */
	public boolean Post(TCheckReview objPost, Integer processId,
			String acceptUserId, Integer aboveActId, String remark,
			Integer formPKID,Integer WhoPost) {	
		try {
			if (objPost.getFId() > 0) {
				if (CheckReviewDao.Update(objPost)) {
				    if(WhoPost==1)
				        processUtils.setActiveAcceptTime(aboveActId);
				    String[] acceptUserIdArray=acceptUserId.split(",");
				    if(acceptUserIdArray.length==1){
				        processUtils.setActiveAcceptTime(aboveActId);
    					//添加活动
    					processUtils.addProcessActiveItem(processId,
    							Integer.parseInt(acceptUserIdArray[0]), aboveActId, remark);
    					//添加活动当前办理人
    					processUtils.setProcessCurrentUser(processId,
    							Integer.parseInt(acceptUserIdArray[0]));
				    }else if(acceptUserIdArray.length>1)
				    {
				        processUtils.setActiveAcceptTime(aboveActId);
				        for(int i=0;i<acceptUserIdArray.length;i++) {
				          //添加活动
	                        processUtils.addProcessActiveItem(processId,
	                                Integer.parseInt(acceptUserIdArray[i]), aboveActId, remark); 
                        }
				      //添加活动当前办理人
                        processUtils.setProcessCurrentUser(processId,
                                Integer.parseInt(acceptUserIdArray[0]));
				    }
				}else {
					return false;
				}
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 
	 * @function:
	 * @data: 2013-1-18下午1:55:25
	 * @author jibinbin
	 * @return
	 *
	 */
	public List<String> GetUsrIdForStation(Integer activeIdInteger)
	{
		List<String> resultList=new ArrayList<String>();
		String SQL="select f_id from dbo.View_GroupUserAll where f_unit_station in(1000,1001,1002,1003,1004,1005,1006,1007,1008,1009) and f_state=1";
		javax.persistence.Query query=CheckReviewDao.CreateNativeSQL(SQL);
		resultList.add(query.getResultList().toString().replace("[", "").replace("]", ""));
		SQL="select f_id from dbo.View_GroupUserAll where f_unit_station =1007 and f_state=1";
		query=CheckReviewDao.CreateNativeSQL(SQL);
		resultList.add(query.getResultList().toString().replace("[", "").replace("]", ""));
		SQL="select f_id from dbo.View_GroupUserAll where f_unit_station in(1003,1006) and f_state=1";
		query=CheckReviewDao.CreateNativeSQL(SQL);
		resultList.add(query.getResultList().toString().replace("[", "").replace("]", ""));
		SQL="select f_accept_user from dbo.T_WF_PROCCESS_ACTIVE where f_id="+activeIdInteger.toString();
		query=CheckReviewDao.CreateNativeSQL(SQL);
		resultList.add(query.getResultList().toString().replace("[", "").replace("]", ""));
		return resultList;
	}
	
	/**
	 * 登录人是否是当前办理人
	 * @function:
	 * @data: 2013-1-18下午3:03:10
	 * @author jibinbin
	 * @param processId
	 * @return
	 *
	 */
	public boolean getLoginIsCurrentUser(Integer processId) {
		boolean flag=false;
		try {
			TWfProcess obj= processDao.GetEntity(TWfProcess.class, processId);
			if(null!=obj){
				if(obj.getFCurrentUserId()==SystemCurrentUser.getCurrentUser().getUserID())
					flag= true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * banjie
	 * @function:
	 * @data: 2013-2-19下午1:40:53
	 * @author jibinbin
	 * @param processId
	 * @param aboveActId
	 * @param formPKID
	 * @return
	 *
	 */
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
	//获得任务信息
	public TTask getTaskForID(Integer id){
	    return TaskDao.GetEntity(TTask.class, id);
	}
	/**
	 * 
	 * @Description	: 签字，如果发送到总工办的人都签字完成，就将此过程设置为完成。
	 * @Author		: jibb
	 * @Date		: 2013-03-28 10-50
	 * @param objPost
	 * @param processId
	 * @param aboveActId
	 * @param remark
	 * @param formPKID
	 * @return
	 */
	public boolean Cuntersign(TCheckReview objPost, Integer processId,
            Integer aboveActId, String remark,Integer formPKID){
	    try {
            if (objPost.getFId() > 0) {
                if (CheckReviewDao.Update(objPost)) {                    
                    String sQLString="select count(f_id) as rows from T_WF_PROCCESS_ACTIVE where F_PROCESS_ID="+processId+" and f_state<>4";
                    boolean flag= processUtils.setActiveState(aboveActId, 4);           
                    int rows=(Integer)processDao.CreateNativeSQL(sQLString).getSingleResult();
                    if(flag==true && rows==0)
                    {
                        processUtils.setProcessState(processId, 4);
                        //TODO2013-5-2 更新当前办理人
                        
                        objPost.setFCurrentStep("50");//会签完成，结束
                        objPost.setFLastStep("40");                        
                        objPost.setFRecordStep(objPost.getFRecordStep()+"{\"CurrentStep\":50,\"ActiveId\":"+aboveActId+"},");
                        CheckReviewDao.Update(objPost);
                    }                
                }else {
                    return false;
                }
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
	}
	/**
     * 
     * @Description : 打回上一步
     * @Author      : jibb
     * @Date        : 2013-3-19 下午3:10:56
     * @param aboveActId
     * @param remark
     * @param formPKID
     * @return
     */
    public boolean Return(Integer aboveActId,String remark,Integer formPKID){
        try {             
            //更新任务的FCurrentStep为上一步状态
            TCheckReview objCheckReview = CheckReviewDao.GetEntity(TCheckReview.class, formPKID);
            if(null!=objCheckReview){
                String CurrentStep="";
                String LastStep="";
                CurrentStep=objCheckReview.getFCurrentStep();
                LastStep= objCheckReview.getFLastStep();                       
                objCheckReview.setFCurrentStep(LastStep);
                objCheckReview.setFLastStep(CurrentStep);
                objCheckReview.setFRecordStep(objCheckReview.getFRecordStep()+"{\"CurrentStep\":"+CurrentStep+",\"ActiveId\":"+aboveActId+"},");
                CheckReviewDao.Update(objCheckReview);
            }
            return processUtils.activeReturn(aboveActId,remark);            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**********************************************修改记录登记-2013-4**********************************************************************/
    /**
     * 
     * @Description	: 检查修改情况
     * @Author		: jibb
     * @Date		: 2013-04-18 14-24
     * @param CurrentObj
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String objectCompare(TCheckReview CurrentObj) {
        //比对差异集合
        List list = new ArrayList();        
        //此处用任务对象实现

        //TTask currentObj  = (TTask)CurrentObj;
        TCheckReview originalObj = CheckReviewDao.GetEntity(TCheckReview.class, CurrentObj.getFId());
        
        if(null!=originalObj.getFConstructionUnit()
                &&!originalObj.getFConstructionUnit().equals(CurrentObj.getFConstructionUnit())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "施工单位");
            diffMap.put("originalValue", originalObj.getFConstructionUnit());
            diffMap.put("currentValue", CurrentObj.getFConstructionUnit());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFResultTypeName()
                &&!originalObj.getFResultTypeName().equals(CurrentObj.getFResultTypeName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "成果类型");
            diffMap.put("originalValue", originalObj.getFResultTypeName());
            diffMap.put("currentValue", CurrentObj.getFResultTypeName());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFSubmitMaterialsName()
                &&!originalObj.getFSubmitMaterialsName().equals(CurrentObj.getFSubmitMaterialsName())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "提交材料");
            diffMap.put("originalValue", originalObj.getFSubmitMaterialsName());
            diffMap.put("currentValue", CurrentObj.getFSubmitMaterialsName());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFProjectCost()
                &&!originalObj.getFProjectCost().equals(CurrentObj.getFProjectCost())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "项目造价金额");
            diffMap.put("originalValue", originalObj.getFProjectCost());
            diffMap.put("currentValue", CurrentObj.getFProjectCost());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFUnitCost()
                &&!originalObj.getFUnitCost().equals(CurrentObj.getFUnitCost())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "单位造价");
            diffMap.put("originalValue", originalObj.getFUnitCost());
            diffMap.put("currentValue", CurrentObj.getFUnitCost());
            list.add(diffMap);
        }
        
        if(null!=originalObj.getFCglroFinalCost()
                &&!originalObj.getFCglroFinalCost().equals(CurrentObj.getFCglroFinalCost())){
            Map<String, String> diffMap = new HashMap();
            diffMap.put("propertyField", "最终造价");
            diffMap.put("originalValue", originalObj.getFCglroFinalCost());
            diffMap.put("currentValue", CurrentObj.getFCglroFinalCost());
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
