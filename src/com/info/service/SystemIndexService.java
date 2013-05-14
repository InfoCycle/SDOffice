package com.info.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.EncryptUtil;
import com.info.domain.TAppFunction;
import com.info.domain.TAppGroup;
import com.info.domain.TAppOrg;
import com.info.domain.TAppOrgUser;
import com.info.domain.TAppUser;
import com.info.domain.TAppUserGroup;

@Service
public class SystemIndexService {
	@Autowired
	IBaseDao<TAppUser> dao;

	@Autowired
	IBaseDao<TAppUserGroup> userGroupDao;

	@Autowired
	IBaseDao<TAppFunction> functionDao;
	@Autowired
	IBaseDao<TAppGroup> groupDao;
	
	// 检查用户编码是否存在
	public int findUserByCode(String usercode) {
		List<TAppUser> user = dao.FindByProperty(TAppUser.class, "FUserCode",
				usercode, null);
		return user.size();
	}

	public Object findUserByCodeAndPwd(String usercode, String pwd) {
		String Md5Pwd = EncryptUtil.md5Digest(pwd);
		String SQL = "select o from TAppUser o where o.FUserCode=? and o.FPassword=?";
		Collection<String> param = new ArrayList<String>();
		param.add(usercode);
		param.add(Md5Pwd);
		return dao.UniqueResult(SQL, param);
	}

	// 根据用户ID获取用户组ID，可能有多个组
	public String getGroupIDByUserID(Integer UserID) {
		String groupid = "-1";
		List<TAppUserGroup> usergroup = userGroupDao.FindByProperty(
				TAppUserGroup.class, "fkUserId", UserID, null);
		for (int i = 0; i < usergroup.size(); i++) {
			groupid += ","+usergroup.get(i).getFkGroupId();			
		}
		return groupid;
	}

	// 根据用户组获取权限菜单
	public List<TAppFunction> getFunctionMenuByUserGroup(String UserGroupID) {
		/*
		 * String SQL
		 * ="select o from TAppFunction o inner join fetch TAppFunctionGroup g "
		 * +"where o.FId=g.fkFunctionId and g.fkGroupId in("+UserGroupID+
		 * ") order by o.FSort"; return functionDao.Query(TAppFunction.class,
		 * SQL);
		 */
		// 使用原生SQL查询
		String NativeSql = "select distinct a.* from T_App_Function a,T_App_Function_Group b where a.f_id=b.fk_Function_Id"
				+ " and a.f_state=1 and b.fk_Group_Id in("
				+ UserGroupID
				+ ")  order by a.F_Sort";
		return functionDao.ExecuteSQLResult(NativeSql,TAppFunction.class);
	}
	
	public List<TAppFunction> getAllFunctionMenuData() {		
		 String SQL="select o from TAppFunction o order by o.FSort"; 
		 return functionDao.Query(SQL);		 
	}
	
	@SuppressWarnings("unchecked")
	public int getOrgIdByUserId(int userId) {
		String SQL= "select * from t_app_org_user where fk_user_id=? order by f_id";
		javax.persistence.Query query=dao.CreateNativeSQL(SQL, TAppOrgUser.class);
		query.setParameter(1, userId);
		query.setMaxResults(1);
		List<TAppOrgUser> orgUsers=query.getResultList();
		if(orgUsers.size()>0){
			return orgUsers.get(0).getFkOrgId();
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public String getOrgNameById(int orgId) {
		String SQL="select * from t_app_org where f_id=?";
		javax.persistence.Query query=dao.CreateNativeSQL(SQL, TAppOrg.class);
		query.setParameter(1, orgId);
		List<TAppOrg> org=query.getResultList();
		if(org.size()>0){
			return org.get(0).getFName();
		}
		return "";
	}
	
	public String getDataOption(int unitStationId) {
		TAppGroup group = groupDao.GetEntity(TAppGroup.class, unitStationId);		
		return null==group.getFDataOption()?"OWNER":group.getFDataOption();
	}
	/**
	 * 为设置权限构造所有功能菜单树形列表，
	 * @return 返回树形数据列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAllFunctionTree() {		
		List functionTreeData =getAllFunctionMenuData();
		List parentid = new ArrayList();
		for (int i = 0; i < functionTreeData.size(); i++) {
			TAppFunction function = (TAppFunction) functionTreeData.get(i);
			if (!parentid.contains(function.getFParentId())) {
				parentid.add(function.getFParentId());
			}
		}
		List tree = buildAllTree(functionTreeData, parentid, 0);
		return tree;
	}
	/**
	 * 根据登录用户组生成用户组权限对应的树形功能菜单
	 * 
	 * @param UserGroupID
	 *            用户组ID，可能对应多个，如：-1,10001,10002
	 * @return 返回用户组对应的树形数据列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getIndexTree(String UserGroupID) {
		List<TAppFunction> listFunction = getFunctionMenuByUserGroup(UserGroupID);
		List parentid = new ArrayList();
		for (int i = 0; i < listFunction.size(); i++) {
			TAppFunction function = (TAppFunction) listFunction.get(i);
			if (!parentid.contains(function.getFParentId())) {
				parentid.add(function.getFParentId());
			}
		}
		List tree = buildAllTree(listFunction, parentid, 0);
		return tree;
	}

	/**
	 * 利用递归方法构造Ext TreePanel所需的数据结构
	 * 
	 * @param FunctionMenu
	 *            所有功能菜单
	 * @param parentid
	 *            父ID列表
	 * @param rootid
	 *            根节点ID
	 * @return 返回构造好的树形数据列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildAllTree(List<TAppFunction> FunctionMenu, List parentid,
			Integer rootid) {
		List list = new ArrayList();
		for (int i = 0; i < FunctionMenu.size(); i++) {
			Map<String, Object> map = new HashMap();
			TAppFunction appFunction = (TAppFunction) FunctionMenu.get(i);
			if (appFunction.getFParentId().equals(rootid)) {
				if (parentid.contains(appFunction.getFId())) {
					map.put("id", appFunction.getFId().toString());
					map.put("text", appFunction.getFName());
					map.put("expanded", true);
					map.put("leaf", false);
					map.put("children",
							buildAllTree(FunctionMenu, parentid,
									appFunction.getFId()));
					list.add(map);
				} else {
					map.put("id", appFunction.getFId().toString());
					map.put("text", appFunction.getFName());
					map.put("expanded", true);
					map.put("link", appFunction.getFFunctionUrl());
					map.put("leaf", true);
					list.add(map);
				}
			}
		}
		return list;
	}
	@Transactional
	public boolean updateUserPwd(int FId,String pwd) {
		String Md5Pwd = EncryptUtil.md5Digest(pwd);
		TAppUser user=dao.GetEntity(TAppUser.class, FId);
		user.setFPassword(Md5Pwd);
		return dao.Update(user);
	}
}
