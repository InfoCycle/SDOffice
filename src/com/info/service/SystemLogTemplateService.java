package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.StringUtil;
import com.info.domain.TAppLog;
import com.info.domain.TAppLogDetail;
import com.info.domain.TAppLogTemplate;
import com.info.domain.TAppLogTemplateDetail;

@Service
@Transactional
public class SystemLogTemplateService {
	@Autowired
	IBaseDao<TAppLogTemplate> logTemplateDao;
	@Autowired
	IBaseDao<TAppLogTemplateDetail> logTemplateDetailDao;
	
	@Autowired
	IBaseDao<TAppLog> logMasterDao;
	@Autowired
	IBaseDao<TAppLogDetail> logDetailDao;
	@Autowired
	AppSEQHelper SEQHelper;
	
	StringBuilder pageSQLString;
	public List<TAppLogTemplate> getLogTemplate() {
		String JPQL="select o from TAppLogTemplate o order by o.FTable";
		return logTemplateDao.Query(JPQL);
	}
	
	public List<TAppLogTemplateDetail> getLogTemplateDetailsByTempId(Integer templateId) {
		return logTemplateDetailDao.FindByProperty(
				TAppLogTemplateDetail.class, "fkTemplateId", templateId, " order by model.FSort");
	}
	
	public TAppLogTemplate saveOrUpdateTemplate(TAppLogTemplate object) {
		if(object.getFId()>0){
			object.setFDate(DateUtil.getTime());
			logTemplateDao.Update(object);
		}else {
			object.setFId(SEQHelper.getCurrentVal());
			object.setFDate(DateUtil.getTime());
			object.setFState(1);
			logTemplateDao.Persist(object);
		}
		return object;
	}
	public boolean saveOrUpdateTemplateDetail(TAppLogTemplateDetail object) {
		try {
			if(object.getFId()>0){
				logTemplateDetailDao.Update(object);
			}else {
				object.setFId(SEQHelper.getCurrentVal());
				logTemplateDetailDao.Persist(object);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TAppLog> queryLogData(String startActionDate,
			String endActionDate,String actionUserName,
			String actionObject,int start,int limit) {
		pageSQLString =new StringBuilder();
		pageSQLString.append("select * from t_app_log where 1=1");
		if(!StringUtil.isEmpty(startActionDate)){
			pageSQLString.append(" and to_char(f_action_time,'yyyy-MM-dd')>='"+startActionDate+"'");
		}
		if(!StringUtil.isEmpty(endActionDate)){
			pageSQLString.append(" and to_char(f_action_time,'yyyy-MM-dd')<='"+endActionDate+"'");
		}
		if(!StringUtil.isEmpty(actionUserName)){
			pageSQLString.append(" and f_user_name like '%"+actionUserName+"%'");
		}
		if(!StringUtil.isEmpty(actionObject)){
			pageSQLString.append(" and f_action_object like '%"+actionObject+"%'");
		}
		//pageSQLString.append(" order by f_action_time desc");
		javax.persistence.Query query=logMasterDao.CreateNativeSQL(pageSQLString.toString()+" order by f_action_time desc",TAppLog.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<TAppLog>)query.getResultList();
	} 
	public int getQueryTotalCount() {
		String SQL ="select count(*) from ("+pageSQLString.toString()+") as a";
		javax.persistence.Query query=logMasterDao.CreateNativeSQL(SQL);
		int totalCount =Integer.parseInt(query.getSingleResult().toString());
		return totalCount;
	}
	public List<TAppLogDetail> getLogDetailByLogId(Integer logMasterId) {
		return logDetailDao.FindByProperty(
				TAppLogDetail.class, "fkLogId", logMasterId, "order by model.FId");
	}
	
}
