package com.info.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.info.domain.TAppLog;
import com.info.domain.TAppLogDetail;
import com.info.domain.TAppLogTemplate;
import com.info.domain.TAppLogTemplateDetail;
import com.info.common.dao.*;
/**
 * 审计日志
 * @author liwx
 *
 * @param <T> 日志模版
 */
@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ActionLog<T> {
	static String ActionName;
	static String TableName;
	static Type ClassType;
	static Integer LogID=0;
	@Autowired
	AppSEQHelper SEQHelper;
	@Autowired
	IBaseDao<TAppLogTemplate> templateDao;
	@Autowired
	IBaseDao<TAppLogTemplateDetail> templateDetailDao;
	@Autowired
	IBaseDao<T> entityDao;
	@Autowired
	IBaseDao<TAppLog> logDao;
	@Autowired
	IBaseDao<TAppLogDetail> DetailDao;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public void writeLog(Object obj,ActionEnum Action)
	{
		ClassType = obj.getClass();
		Annotation annotation = obj.getClass().getAnnotation(Table.class);
		TableName = ((Table) annotation).name();
		
		switch (Action){		 
		 case Insert:
			 ActionName="新增";
			 dataLog(obj,0);
			 break;
		 case Update:
			 ActionName="修改";
			 dataLog(obj,1);
			 break;
		 default:	
			 ActionName="新增";
			 dataLog(obj,0);
			 break;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void dataLog(Object obj,int type)
	{
		String ColumnName = null;
		List<TAppLogTemplate> Listtemplate = 
				templateDao.FindByProperty(TAppLogTemplate.class, "FTable", TableName,null);
		if(Listtemplate.size()>0)
		{
			//生成Log主键值
			LogID = SEQHelper.getCurrentVal("SEQ_APP_LOG");
			TAppLogTemplate template = Listtemplate.get(0);
			Method[] method = obj.getClass().getMethods();
			
			List<TAppLogTemplateDetail> ListDetail = templateDetailDao
					.FindByProperty(TAppLogTemplateDetail.class, 
							"fkTemplateId", template.getFId(),"Order by model.FSort");
			
			Map<String, Object> NewMap = new HashMap<String, Object>();
			Map<String, String> ColumnMap = new HashMap<String, String>();
			Map<String, Object> EditMap = new HashMap<String, Object>();
			
			T EditEntity=null;
			for (Method m : method) {
				Column annotationsColumn = m.getAnnotation(Column.class);
				if (annotationsColumn != null) {
					try {
						ColumnName = annotationsColumn.name();
						if (ColumnName.equals("F_ID")) {
							NewMap.put("F_ID", m.invoke(obj));
							if(type==1)
							{
								Integer PKId;
								PKId = (Integer)m.invoke(obj);									
								String QueryStr="select o from "+obj.getClass().getSimpleName()+" o where o.FId=?1";
								Query query = entityManager.createQuery(QueryStr);
								query.setParameter(1, PKId);
								EditEntity= (T)query.getSingleResult();
								EditMap.put("F_ID", m.invoke(EditEntity));								
							}
						}
						for (int i = 0; i < ListDetail.size(); i++) {
							TAppLogTemplateDetail detail = (TAppLogTemplateDetail) ListDetail
									.get(i);
							if (ColumnName.equals(detail.getFColumn())) {
								NewMap.put(ColumnName, m.invoke(obj));
								if(type==1)
								{
									EditMap.put(ColumnName, m.invoke(EditEntity));
								}
								ColumnMap.put(ColumnName, detail.getFColumnName());
							}
						}
					} catch (Exception e) {
						
					}
				}
			}
			
			TAppLog newlog = new TAppLog();
			newlog.setFId(LogID);
			newlog.setFkUserId(SystemCurrentUser.getCurrentUser().getUserID());
			newlog.setFUserName(SystemCurrentUser.getCurrentUser().getUserName());
			newlog.setFActionType(ActionName);
			newlog.setFActionObject(template.getFTableName());
			newlog.setFActionId((Integer) NewMap.get("F_ID"));
			newlog.setFActionTime(new Timestamp(new Date().getTime()));
			logDao.Persist(newlog);

			@SuppressWarnings("rawtypes")
			Iterator it = NewMap.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				if (!key.equals("F_ID")) {
					TAppLogDetail newDetail = new TAppLogDetail();
					newDetail.setFId(SEQHelper.getCurrentVal("SEQ_APP_LOG"));
					newDetail.setFColumn(key.toString());
					newDetail.setFColumnName(ColumnMap.get(key));
					if (type==0) {
						newDetail.setFSoruceValue("");
					}
					if (type==1) {
						newDetail.setFSoruceValue(EditMap.get(key).toString());
					}
					newDetail.setFCurrentValue(NewMap.get(key).toString());
					newDetail.setFkLogId(LogID);
					DetailDao.Persist(newDetail);
				}
			}
		}
	}
}
