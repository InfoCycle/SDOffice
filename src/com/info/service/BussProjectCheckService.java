package com.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAppCode;
import com.info.domain.TAppCodeType;
import com.info.domain.TAppTreeCode;
import com.info.domain.TAppUser;
import com.info.domain.TProjectCheck;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;

@Service
@Transactional
public class BussProjectCheckService {
	@Autowired
	IBaseDao<TProjectCheck> projectCheckDao;
	@Autowired
	AppSEQHelper SEQHelper;
	@Autowired
	WfProcessUtils wfUtils;
	@Autowired
	IBaseDao<TWfProccessActive> activeDao;
	@Autowired
	IBaseDao<TWfProcess> processDao;
	@Autowired
	IBaseDao<TAppCode> codeDao;
	@Autowired
	IBaseDao<TAppTreeCode> treeCodeDao;
	@Autowired
	IBaseDao<TAppUser> userDao;
	
	
	public TAppUser getuserById(int id) {
		TAppUser user=userDao.GetEntity(TAppUser.class, id);
		return user;
	}
	
	public TAppTreeCode getTreeCodeById(Integer id) {
		return treeCodeDao.GetEntity(TAppTreeCode.class, id);
	}
	
	public TAppCode getCodeByID(Integer id){
		return codeDao.GetEntity(TAppCode.class, id);
	}
	
	public TWfProcess getWfProcessByID(Integer processId) {
		return processDao.GetEntity(TWfProcess.class, processId);
	}
	
	public TWfProccessActive getWfProccessActiveByID(Integer activeId) {
		return activeDao.GetEntity(TWfProccessActive.class, activeId);
	}
	
	public TProjectCheck getProjectCheckForID(Integer id){
		return projectCheckDao.GetEntity(TProjectCheck.class, id);
	}
	
	public TProjectCheck update(TProjectCheck projectCheck,int processId,String title){
		if(projectCheckDao.Update(projectCheck) && wfUtils.setProcessTitle(processId, projectCheck.getFId(), title)){
			return projectCheck;
		}else{
			return null;
		}
	}
	
	public TProjectCheck post(TProjectCheck projectCheck,int processId,int type){
		if(projectCheckDao.Update(projectCheck) && wfUtils.setProcessState(processId,type)){
			return projectCheck;
		}else{
			return null;
		}
	}
	
	public TProjectCheck save(TProjectCheck projectCheck,int processId,String title,int personId,String personName){
		Integer id = SEQHelper.getCurrentVal("SEQ_PROJECTCHECK");
		projectCheck.setFId(id);
		projectCheck.setFCheckPersonId(personId);
		projectCheck.setFCheckPersonName(personName);
		if(projectCheckDao.Save(projectCheck) && wfUtils.setProcessTitle(processId, id, title)){
			return projectCheck;
		}else{
			return null;
		}
	}
	
	public boolean isCanDestroy(Integer processId){
		if(wfUtils.isCanDestroy(processId)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean delete(Integer id, Integer activeId, Integer processId) {
		if (projectCheckDao.Delete(TProjectCheck.class, id)
				&& activeDao.Delete(TWfProccessActive.class, activeId)
				&& processDao.Delete(TWfProcess.class, processId)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean activeReturn(int activeId,String remark) {
		return wfUtils.activeReturn(activeId, remark);
	}
	
	public boolean activeUrge(int activeId,String remark) {
		return wfUtils.activeUrge(activeId, remark);
	}
}
