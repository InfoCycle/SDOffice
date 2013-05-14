package com.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.SystemCurrentUser;
import com.info.domain.TAppMessage;
import com.info.domain.TAppUserMessage;
import com.info.web.CurrentUser;

@Service
@Transactional
public class WfProcessMessageService {

	@Autowired
	IBaseDao<TAppMessage> appMessageDao;
	@Autowired
	IBaseDao<TAppUserMessage> appUserMessageDao;
	@Autowired
	AppSEQHelper SEQHelper;
	CurrentUser user;
	public boolean addMessage(String type,String content, int acceptUser) {
		boolean flag;
		user = SystemCurrentUser.getCurrentUser();
		int MsgId;
		try {
			MsgId = SEQHelper.getCurrentVal("SEQ_MESSAGE");
			TAppMessage message = new TAppMessage();
			message.setFId(MsgId);
			message.setFTitle("您有新的"+type+"业务需要办理");
			content +="。于"+DateUtil.format()+type+"。"+"请尽快办理。";
			message.setFContent(content);
			message.setFSendTime(DateUtil.getTime());
			message.setFSendUser(user.getUserName());
			message.setFSendUserId(user.getUserID());
			TAppUserMessage userMessage = new TAppUserMessage();
			userMessage.setFId(MsgId);
			userMessage.setFkMessageId(MsgId);
			userMessage.setFkUserId(acceptUser);
			userMessage.setFState(0);
			appMessageDao.Persist(message);
			appUserMessageDao.Persist(userMessage);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public TAppMessage show(int MsgId) {		
		user = SystemCurrentUser.getCurrentUser();
		TAppMessage message = appMessageDao.GetEntity(TAppMessage.class, MsgId);
		String updateSQL="update TAppUserMessage o set o.FState=1,o.FReadTime=?1 where o.fkMessageId=?2 and o.fkUserId=?3";
		javax.persistence.Query query= appUserMessageDao.CreateQuery(updateSQL);
		query.setParameter(1, DateUtil.getTime());
		query.setParameter(2, MsgId);
		query.setParameter(3, user.getUserID());
		query.executeUpdate();
		return message;
	}
	
	public TAppMessage getUnReadMessage() {
		try {
			user = SystemCurrentUser.getCurrentUser();
			String SQL="select a.* from T_APP_MESSAGE a,T_APP_USER_MESSAGE b"
						+" where a.F_ID = b.FK_MESSAGE_ID and b.FK_USER_ID =?1 and b.F_STATE=0 ";
			javax.persistence.Query countQuery=appMessageDao.CreateNativeSQL("select count(*) from ("+SQL+") as t");
			countQuery.setParameter(1, user.getUserID());
			int count=Integer.parseInt(countQuery.getSingleResult().toString());
			if(count>0){
				javax.persistence.Query query=appMessageDao.CreateNativeSQL(SQL+" order by a.F_SEND_TIME desc", TAppMessage.class);
				query.setMaxResults(1);
				query.setParameter(1, user.getUserID());		
				return (TAppMessage)query.getSingleResult();
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
