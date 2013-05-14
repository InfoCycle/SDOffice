package com.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.domain.TAppFunction;
import com.info.domain.TAppFunctionGroup;
import com.info.domain.TAppGroup;
import com.info.domain.TAppOrg;
import com.info.domain.TAppUserGroup;
import com.info.domain.TWfStationProcess;
import com.info.domain.ViewApporguser;

@Service
@Transactional
public class SystemOrgUserFunctionService {
	@Autowired
	AppSEQHelper SEQHelper;
	@Autowired
	IBaseDao<TAppGroup> groupDao;
	@Autowired
	IBaseDao<TAppFunction> functionDao;
	@Autowired
	IBaseDao<TAppOrg> orgDao;
	@Autowired
	IBaseDao<TAppUserGroup> userGroupDao;
	@Autowired
	IBaseDao<TAppFunctionGroup> functionGroupDao;
	
	@Autowired
	IBaseDao<TWfStationProcess> processDao;
	@Autowired
	IBaseDao<TWfStationProcess> stationProcessDao;
	public List<TAppGroup> getAppGroupList() {
		return groupDao.Query("select o from TAppGroup o order by o.FSort");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getFunctionTreeData() {
		List<TAppFunction> listFunction = functionDao
				.Query("select o from TAppFunction o order by o.FSort");
		List parentid = new ArrayList();
		for (int i = 0; i < listFunction.size(); i++) {
			TAppFunction function = (TAppFunction) listFunction.get(i);
			if (!parentid.contains(function.getFParentId())) {
				parentid.add(function.getFParentId());
			}
		}
		List tree = buildFunctionTree(listFunction, parentid, 0);
		return tree;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getOrgUserTreeData() {
		List<TAppOrg> orgUserList=new ArrayList();
		String SQL ="select * from view_apporguser order by f_parent_id,f_sort";
		javax.persistence.Query query = orgDao.CreateNativeSQL(SQL, ViewApporguser.class);
		List<ViewApporguser> list =query.getResultList();
		for(ViewApporguser o:list){
			TAppOrg org=new TAppOrg();
			org.setFId(o.getFId());
			org.setFParentId(o.getFParentId());
			org.setFName(o.getFName());
			org.setFSort(o.getFSort());
			orgUserList.add(org);
		}
		List parentid = new ArrayList();
		for (int i = 0; i < orgUserList.size(); i++) {
			TAppOrg orgUser = (TAppOrg) orgUserList.get(i);
			if (!parentid.contains(orgUser.getFParentId())) {
				parentid.add(orgUser.getFParentId());
			}
		}
		List tree = buildOrgUserTree(orgUserList, parentid, 0);
		return tree;
	}

	public boolean saveOrUpdate(TAppGroup appGroup) {
		try {
			if (appGroup.getFId() > 0) {
				groupDao.Update(appGroup);
			} else {
				appGroup.setFId(SEQHelper.getCurrentVal("SEQ_Function_Group"));
				appGroup.setFCreatetime(DateUtil.getTime());
				appGroup.setFState(1);
				groupDao.Persist(appGroup);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @author liwx at 2013-1-5下午5:38:23
	 * @param groupId
	 * @param functionIdList
	 * @param orgUserIdList
	 * @return
	 */
	public boolean insertUserFunctionGroup(Integer groupId,
			List<String> functionIdList, List<String> orgUserIdList) {
		try {
			// 删除组对应的用户
			//deleteOrgUserByGroupId(groupId);
			deleteProcessTypeByGroupId(groupId);
			// 删除组对应的功能菜单
			deleteFunctionByGroupId(groupId);
			//增加人员对应组
			/*for(String userId:orgUserIdList){
				TAppUserGroup userGroup =new TAppUserGroup();
				userGroup.setFId(SEQHelper.getCurrentVal("SEQ_User_Group"));
				userGroup.setFkGroupId(groupId);
				userGroup.setFkUserId(Integer.parseInt(userId));
				userGroupDao.Persist(userGroup);
			}*/
			for(String stationId:orgUserIdList){
				TWfStationProcess process = new TWfStationProcess();
				process.setFId(SEQHelper.getCurrentVal("SEQ_Station_Process"));
				process.setFProcessTypeId(Integer.parseInt(stationId));
				process.setFUnitStationId(groupId);
				stationProcessDao.Persist(process);
			}
			//增加功能对应组
			for(String functionId:functionIdList){
				TAppFunctionGroup functionGroup=new TAppFunctionGroup();
				functionGroup.setFId(SEQHelper.getCurrentVal("SEQ_Function_Group"));
				functionGroup.setFkGroupId(groupId);
				functionGroup.setFkFunctionId(Integer.parseInt(functionId));
				functionGroupDao.Persist(functionGroup);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 删除权限组
	public boolean deleteAppGroup(Integer FId) {
		try {
			// 删除权限组
			groupDao.Delete(TAppGroup.class, FId);
			// 删除组对应的用户
			deleteOrgUserByGroupId(FId);
			// 删除组对应的功能菜单
			deleteFunctionByGroupId(FId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 
	 * 根据权限组Id获取对应的功能菜单
	 * @author liwx at 2013-1-5
	 * @param groupId
	 * @return
	 */
	public String getFunctionByGroupId(Integer groupId) {
		List<TAppFunctionGroup> functionGroups = 
				functionGroupDao.FindByProperty(
						TAppFunctionGroup.class, "fkGroupId", groupId, null);
		String resultString="-1";
		for(TAppFunctionGroup functionGroup:functionGroups){
			resultString+=","+functionGroup.getFkFunctionId();
		}
		return resultString;
	}
	/**
	 * 
	 *根据权限组Id获取对应的机构人员
	 * @author liwx at 2013-1-5
	 * @param groupId
	 * @return
	 */
	public String getOrgUserByGroupId(Integer groupId) {
		/*List<TAppUserGroup> userGroups=
				userGroupDao.FindByProperty(
						TAppUserGroup.class, "fkGroupId", groupId, null);
		String resultString="-1";
		for(TAppUserGroup userGroup:userGroups){
			resultString+=","+userGroup.getFkUserId();
		}*/
		List<TWfStationProcess> processes =processDao.FindByProperty(
				TWfStationProcess.class,"FUnitStationId",groupId,null);
		String resultString="-1";
		for(TWfStationProcess processe:processes){
			resultString+=","+processe.getFProcessTypeId();
		}
		return resultString;
	}
	
	private boolean deleteProcessTypeByGroupId(Integer groupId) {
		userGroupDao
		.ExecuteSQL("delete from T_WF_STATION_PROCESS where F_UnitStation_Id="
				+ groupId.toString());
		return true;
	}
	private boolean deleteOrgUserByGroupId(Integer groupId) {
		userGroupDao
				.ExecuteSQL("delete from t_app_user_group where fk_group_id="
						+ groupId.toString());
		return true;
	}

	private boolean deleteFunctionByGroupId(Integer groupId) {
		functionGroupDao
				.ExecuteSQL("delete from t_app_function_group where fk_group_id="
						+ groupId.toString());
		return true;
	}
	/**
	 * @author liwx at 2013-1-5
	 * @param FunctionMenu
	 * @param parentid
	 * @param rootid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildFunctionTree(List<TAppFunction> FunctionMenu,
			List parentid, Integer rootid) {
		List list = new ArrayList();
		for (int i = 0; i < FunctionMenu.size(); i++) {
			Map<String, Object> map = new HashMap();
			TAppFunction appFunction = (TAppFunction) FunctionMenu.get(i);
			if (appFunction.getFParentId().equals(rootid)) {
				if (parentid.contains(appFunction.getFId())) {
					map.put("id", appFunction.getFId().toString());
					map.put("parent", appFunction.getFParentId());
					map.put("text", appFunction.getFName());
					map.put("expanded", true);
					map.put("checked", false);
					map.put("leaf", false);
					map.put("children",
							buildFunctionTree(FunctionMenu, parentid,
									appFunction.getFId()));
					list.add(map);
				} else {
					map.put("id", appFunction.getFId().toString());
					map.put("parent", appFunction.getFParentId());
					map.put("text", appFunction.getFName());
					map.put("expanded", true);
					map.put("checked", false);
					map.put("leaf", true);
					list.add(map);
				}
			}
		}
		return list;
	}
	/**
	 * 生成机构人员树
	 * @author liwx at 2013-1-5
	 * @param orgUserList
	 * @param parentid
	 * @param rootid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildOrgUserTree(List<TAppOrg> orgUserList,
			List parentid, Integer rootid) {
		List list = new ArrayList();
		for (int i = 0; i < orgUserList.size(); i++) {
			Map<String, Object> map = new HashMap();
			TAppOrg orgUser = (TAppOrg) orgUserList.get(i);
			if (orgUser.getFParentId().equals(rootid)) {
				if (parentid.contains(orgUser.getFId())) {
					map.put("id", orgUser.getFId().toString());
					map.put("parent", orgUser.getFParentId());
					map.put("text", orgUser.getFName());
					map.put("expanded", true);
					map.put("checked", false);
					map.put("leaf", false);
					map.put("children",
							buildOrgUserTree(orgUserList, parentid,
									orgUser.getFId()));
					list.add(map);
				} else {
					map.put("id", orgUser.getFId().toString());
					map.put("parent", orgUser.getFParentId());
					map.put("text", orgUser.getFName());
					map.put("expanded", true);
					map.put("checked", false);
					map.put("leaf", true);
					list.add(map);
				}
			}
		}
		return list;
	}
}
