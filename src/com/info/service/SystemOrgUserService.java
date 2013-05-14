package com.info.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAppOrgUser;
import com.info.domain.TAppUser;

@Service
@Transactional
public class SystemOrgUserService {
	@Autowired
	IBaseDao<TAppUser> userDao;
	
	@Autowired
	IBaseDao<TAppOrgUser> orgUserDao;
	@Autowired
	AppSEQHelper SEQHelper;
	
	public List<TAppUser> getUserByOrgId(Integer OrgId) {
		String SQLString = "select a.* from t_app_user a,t_app_org_user b where a.f_id=b.fk_user_id and b.fk_org_id=%1$d order by a.f_sort";
		SQLString =String.format(SQLString, OrgId);
		return userDao.ExecuteSQLResult(SQLString, TAppUser.class);
	}
	
	public TAppUser saveOrUpdate(TAppUser user,Integer OrgId) {
		try {
			if(user.getFId()>0){
				userDao.Update(user);
			}else {
				//跟TAppOrg取同样的序列
				Integer userID = SEQHelper.getCurrentVal("SEQ_ORG");
				user.setFId(userID);
				user.setFCreateTime(new Timestamp((new Date()).getTime()));
				TAppOrgUser orgUser=new TAppOrgUser();
				orgUser.setFId(SEQHelper.getCurrentVal("SEQ_ORG_USER"));
				orgUser.setFkOrgId(OrgId);
				orgUser.setFkUserId(userID);
				orgUserDao.Persist(orgUser);	
				userDao.Persist(user);
			}
			return user;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public boolean delete(Integer FId) {
		try {
			userDao.Delete(TAppUser.class, FId);
			userDao.ExecuteSQL("delete from t_app_org_user where fk_user_id="+FId.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TAppUser> findUserByName(String UserName) {
		String SQLString = "select a.* from t_app_user a where a.f_name like ? or a.f_user_code like ? order by a.f_sort";
		javax.persistence.Query query = userDao.CreateNativeSQL(SQLString, TAppUser.class);
		query.setParameter(1, "%"+UserName+"%");
		query.setParameter(2, "%"+UserName+"%");
		return (List<TAppUser>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAppUser> findUserByFId(Integer fid){
		String SQLString = "select a.* from t_app_user a where a.f_id = ? ";
		javax.persistence.Query query = userDao.CreateNativeSQL(SQLString, TAppUser.class);
		query.setParameter(1, fid);		
		return (List<TAppUser>)query.getResultList();		
	}
}
