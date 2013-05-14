package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.common.dao.IBaseDao;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TConstructionData;
import com.info.domain.ViewWfProcessTemplete;
import com.info.web.CurrentUser;

@Service
public class WfDesk {
	@Autowired
	IBaseDao<TConstructionData> constructionDataDao;
	@Autowired
	IBaseDao<ViewWfProcessTemplete> processDao;
	@SuppressWarnings("unchecked")
	public List<TConstructionData> getConstructions(){
		CurrentUser user= SystemCurrentUser.getCurrentUser();
		int UserId = user.getUserID();
		StringBuilder str=new StringBuilder();
		str.append("select o from TConstructionData o where  FPersonId =?1 or FPublicType = 1 ");
		str.append(" order by o.FDate desc ");
		Query query= constructionDataDao.CreateQuery(str.toString());
		query.setParameter(1, UserId);
		query.setMaxResults(7);
		List<TConstructionData> list=query.getResultList();
		return list;
	}
	/**
	 * @Description	: 获取未阅读文件数量
	 * @Author		: liwx
	 * @Date		: 2013-03-28 10-23
	 * @return
	 */
	public int getUnReadCount() {
		String SQL="select count(*) f_count from T_WF_PROCESS a,T_WF_PROCESS_SEND b"
				  +" where a.F_ID=b.F_PROCESS_ID and b.F_STATE=0 and b.F_ACCEPT_USER=?1";
		Query query= processDao.CreateNativeSQL(SQL);
		query.setParameter(1, SystemCurrentUser.getCurrentUser().getUserID());
		return Integer.parseInt(query.getSingleResult().toString());
	}
	/**
	 * @Description	: 未读文件列表
	 * @Author		: liwx
	 * @Date		: 2013-03-28 16-01
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcessTemplete> getUnReadList() {
		String SQL="select a.*,b.F_ID ActiveId,c.F_NAME as F_SEND_USER, b.F_SEND_TIME"
				+" from View_wf_process a,T_WF_PROCESS_SEND b,T_APP_USER c"
				+" where a.F_ID=b.F_PROCESS_ID and b.F_SEND_USER=c.F_ID"
				+" and b.F_STATE=0 and b.F_ACCEPT_USER=?1 order by a.F_Title,a.F_Type_Name,b.F_SEND_TIME desc";
		javax.persistence.Query query =processDao.CreateNativeSQL(SQL,ViewWfProcessTemplete.class);
		query.setParameter(1, SystemCurrentUser.getCurrentUser().getUserID());		
		return query.getResultList();		
	}
	
}
