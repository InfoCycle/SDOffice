package com.info.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAppUser;
import com.info.domain.TCommonOpinion;
import com.info.domain.TWfProccessActive;
import com.info.domain.ViewWfActive;
import com.info.domain.ViewWfActiveAll;

@Service
@Transactional
public class BussCommonOpinionService {
	@Autowired
	IBaseDao<TCommonOpinion> commonOpinionDao;
	
	@Autowired
	IBaseDao<TWfProccessActive> activeDao;
	
	@Autowired
	AppSEQHelper seqHelper;
	
	@SuppressWarnings({ "unchecked" })
	public List<TCommonOpinion> GetComOpinionsListForUserID(
			Integer UserID, Integer start, Integer length) {
		String SQL = "select a.* from T_CommonOpinion a where a.F_UserId= ? order by F_AddDate DESC";
		javax.persistence.Query query = commonOpinionDao.CreateNativeSQL(SQL,
				TCommonOpinion.class);
		query.setParameter(1, UserID);
		return (List<TCommonOpinion>) query.getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<TCommonOpinion> GetComOpinionsListForUserID(
			Integer UserID,String order,Integer page,Integer rows,String sort) {
		String SQL = "select a.* from T_CommonOpinion a where a.F_UserId= ? order by F_AddDate "+order;
		javax.persistence.Query query = commonOpinionDao.CreateNativeSQL(SQL,
				TCommonOpinion.class);
		query.setParameter(1, UserID);
		query.setFirstResult(page);
		query.setMaxResults(rows);		
		return (List<TCommonOpinion>) query.setFirstResult((page - 1) * rows).setMaxResults(rows).getResultList();
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<TCommonOpinion> Save(TCommonOpinion obj) {
		Integer fIdInteger = 0;
		List<TCommonOpinion> resultList = null;
		if (obj.getFId() == 0) {
			fIdInteger = seqHelper.getCurrentVal("SEQ_COMMONOPINION");
			obj.setFId(fIdInteger);
			if (commonOpinionDao.Persist(obj)) {
				String SQL = "select a.* from T_CommonOpinion a where a.f_id= ?";
				javax.persistence.Query query = commonOpinionDao.CreateNativeSQL(
						SQL, TCommonOpinion.class);
				query.setParameter(1, fIdInteger);
				resultList = (List<TCommonOpinion>) query.getResultList();
			} else {
				return null;
			}
		} else if (obj.getFId() > 0) {
			TCommonOpinion updateObj = commonOpinionDao.GetEntity(TCommonOpinion.class,
					obj.getFId());
			// updateObj.setFId(obj.getFId());
			updateObj.setFUserId(obj.getFUserId());
			updateObj.setFContent(obj.getFContent());
			Date currentTime=new Date();
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString=formatter.format(currentTime);
			updateObj.setFAddDate(dateString);
			if (commonOpinionDao.Update(updateObj)) {
				String SQL = "select a.* from T_CommonOpinion a where a.f_id= ?";
				javax.persistence.Query query = commonOpinionDao.CreateNativeSQL(
						SQL, TCommonOpinion.class);
				query.setParameter(1, obj.getFId());
				resultList = (List<TCommonOpinion>) query.getResultList();
			}
		}
		return resultList;
	}	

	public boolean Delete(Integer id) {
		try {
			commonOpinionDao.Delete(TCommonOpinion.class, id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean UpdateOpinion(Integer FormId,String TableName,String Field,String Opinion){
		String updateSQL="";
		try {		
			/*updateSQL="update ?1 u set u.?2=?3 where u.fid=?4";
			Query query = (Query)commonOpinionDao.CreateQuery(updateSQL); //.createQuery("update User u set u.userName = ?1 where u.id = ?2");
            query.setParameter(1,TableName);
            query.setParameter(2,Field);
            query.setParameter(3,Opinion);
            query.setParameter(4,FormId);
            return query.executeUpdate()>0;*/
			String [] fieldList=Field.split(",");
			if(fieldList.length==1)
				updateSQL="update "+TableName +" set "+Field+" ='"+Opinion+"'"+ " where F_ID="+FormId;
			else if(fieldList.length==2){
				updateSQL = "update "+TableName +" set "+
						fieldList[0]+" ='"+Opinion+"'"+
						" ,"+fieldList[1]+" = '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+
						"' where F_ID="+FormId;
			}
			return commonOpinionDao.ExecuteSQL(updateSQL)>0;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 业务办理情况列表
	 * @param processId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfActive> getWfActive(int processId,int toprecord) {
		String SQL="select top "+toprecord+" * from View_Wf_Active o where o.processId=?1  order by completetime desc";
		javax.persistence.Query query=activeDao.CreateNativeSQL(SQL, ViewWfActive.class);
		query.setParameter(1, processId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewWfActiveAll> getWfActiveAlls(int processId,int toprecord){
		String SQL="select top "+ toprecord+" * from View_wf_Active_All o where o.processId=?1 and o.f_style=1 order by F_SEND_TIME desc";
		javax.persistence.Query query=activeDao.CreateNativeSQL(SQL, ViewWfActiveAll.class);
		query.setParameter(1, processId);		
		List<ViewWfActiveAll> objList=query.getResultList();
		
		List<ViewWfActiveAll> resultList=new ArrayList<ViewWfActiveAll>();
		ViewWfActiveAll tempObj=new ViewWfActiveAll();
		tempObj.setRowid(-1);
		tempObj.setFId(Long.valueOf(-1));
		tempObj.setFStyle("-1");
		tempObj.setSendUser("发送人员");
		tempObj.setFSendTime("发送时间");
		tempObj.setAcceptUser("接收人");		
		tempObj.setFAcceptTime("接收时间");
		tempObj.setFStateText("办理状态");
		tempObj.setCompleteTime("完成时间");
		tempObj.setFRemark("备注");
		resultList.add(tempObj);
		for (ViewWfActiveAll o:objList) {
			resultList.add(o);
		}
		
		SQL="select top "+ toprecord+" * from View_wf_Active_All o where o.processId=?1 and o.f_style=2 order by F_SEND_TIME desc";
		query=activeDao.CreateNativeSQL(SQL, ViewWfActiveAll.class);
		query.setParameter(1, processId);		
		objList=query.getResultList();
		if(objList.size()>0){
			tempObj=new ViewWfActiveAll();
			tempObj.setRowid(-2);
			tempObj.setFId(Long.valueOf(-1));
			tempObj.setFStyle("-2");
			tempObj.setSendUser("催办人员");
			tempObj.setFSendTime("催办时间");
			tempObj.setAcceptUser("办理人员");		
			tempObj.setFAcceptTime("备注");
			tempObj.setFStateText("");
			tempObj.setCompleteTime("");
			tempObj.setFRemark("");
			
			resultList.add(tempObj);
			for (ViewWfActiveAll o:objList) {			
				//o.setFAcceptTime(o.getFRemark());
				resultList.add(o);
			}
		}
		SQL="select top "+ toprecord+" * from View_wf_Active_All o where o.processId=?1 and o.f_style=3 order by F_SEND_TIME desc";
		query=activeDao.CreateNativeSQL(SQL, ViewWfActiveAll.class);
		query.setParameter(1, processId);		
		objList=query.getResultList();
		if(objList.size()>0){
			tempObj=new ViewWfActiveAll();
			tempObj.setRowid(-3);
			tempObj.setFId(Long.valueOf(-1));
			tempObj.setFStyle("-3");
			tempObj.setSendUser("提交人员");
			tempObj.setFSendTime("打回时间");
			tempObj.setAcceptUser("办理人员");
			tempObj.setFAcceptTime("备注  ");
			tempObj.setFStateText(" ");
			tempObj.setCompleteTime(" ");
			tempObj.setFRemark("");
			resultList.add(tempObj);
			for (ViewWfActiveAll o:objList) {		
				//o.setFAcceptTime(o.getFRemark());
				resultList.add(o);
			}
		}
		SQL="select top "+ toprecord+" * from View_wf_Active_All o where o.processId=?1 and o.f_style=4 order by F_SEND_TIME desc";
        query=activeDao.CreateNativeSQL(SQL, ViewWfActiveAll.class);
        query.setParameter(1, processId);       
        objList=query.getResultList();
        if(objList.size()>0){
            tempObj=new ViewWfActiveAll();
            tempObj.setRowid(-4);
            tempObj.setFId(Long.valueOf(-1));
            tempObj.setFStyle("-4");
            tempObj.setSendUser("抄送人");
            tempObj.setFSendTime("抄送时间");
            tempObj.setAcceptUser("办理人员");
            tempObj.setFAcceptTime("办理时间  ");
            tempObj.setFStateText("办理状态");
            tempObj.setCompleteTime("备注");
            tempObj.setFRemark("");
            resultList.add(tempObj);
            for (ViewWfActiveAll o:objList) {       
                //o.setFAcceptTime(o.getFRemark());
                resultList.add(o);
            }
        }
		return resultList;
	}
	public Long getQueryTotalCount(Integer userid) {
		String SQL = "select count(*) from (select a.* from T_CommonOpinion a where a.F_UserId="+userid+") t";
		javax.persistence.Query query = commonOpinionDao.CreateNativeSQL(SQL);
		Long totalCount = Long.parseLong(query.getSingleResult().toString());//Integer.parseInt(query.getSingleResult().toString());
		return totalCount;
	}
	
}
