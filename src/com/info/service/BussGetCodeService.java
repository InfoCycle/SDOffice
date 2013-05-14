package com.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.common.dao.IBaseDao;
import com.info.domain.TAppCode;
import com.info.domain.TIndustry;
import com.info.domain.ViewAppOrgTree;
import com.info.domain.ViewClientTree;
import com.info.domain.ViewCodeTree;
import com.info.domain.ViewGroupUser;
import com.info.domain.ViewGroupUserAll;
import com.info.domain.ViewTask;

@Service
public class BussGetCodeService {

	@Autowired
	IBaseDao<TAppCode> daoAppCode;

	@Autowired
	IBaseDao<TIndustry> daoIndustry;

	@Autowired
	IBaseDao<ViewClientTree> daoClientTree;

	@Autowired
	IBaseDao<ViewAppOrgTree> daoAppOrgTree;

	@Autowired
	IBaseDao<ViewTask> daoTaskInfo;

	@Autowired
	IBaseDao<ViewGroupUser> daoViewGroupUser;
	
	@Autowired
	IBaseDao<ViewGroupUserAll> daoViewGroupUserAll;

	@Autowired
	IBaseDao<ViewCodeTree> daoViewCodeTree;

	// Get AppCode for fkCodeTypeId and FState
	public List<TAppCode> GetListAppCode(Integer fkCodeTypeId, Integer FState) {
		String sqlString = "";
		String whereString = "";
		whereString = " where o.fkCodeTypeId=" + fkCodeTypeId
				+ " and o.FState=" + FState;
		sqlString = "select o from TAppCode o " + whereString
				+ " order by o.FSort";
		return daoAppCode.Query(sqlString);
	}

	@SuppressWarnings({ "unchecked" })
	public List<TIndustry> GetListIndustryForPID(Integer pid) {
		// "select a.* from t_app_user a where a.f_name like ? or a.f_user_code like ? order by a.f_sort";
		String SQL = "select a.* from T_Industry a where a.f_parent_id= ?";
		javax.persistence.Query query = daoIndustry.CreateNativeSQL(SQL,
				TIndustry.class);
		query.setParameter(1, pid);
		return (List<TIndustry>) query.getResultList();
	}
  /**
   * 
   * @Description	: TODO(这里用一句话描述这个方法的作用)
   * @Author		: jibb
   * @Date		: 2013-3-19 下午2:43:15
   * @param pid
   * @return
   */
	@SuppressWarnings({ "unchecked" })
	public List<ViewClientTree> GetListClientForPID(Integer pid) {
		String SQL = "select a.* from View_ClientTree a where a.F_Parent_Id= ?  order by f_sort asc";
		javax.persistence.Query query = daoClientTree.CreateNativeSQL(SQL,
				ViewClientTree.class);
		query.setParameter(1, pid);
		return (List<ViewClientTree>) query.getResultList();
	}

	/**
	 * 
	 * @Description	: TODO(这里用一句话描述这个方法的作用)
	 * @Author		: jibb
	 * @Date		: 2013-3-19 下午2:30:24
	 * @param pid
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<ViewAppOrgTree> GetListAppOrgForPID(Integer pid) {
		String SQL = "select a.* from View_App_Org_Tree a where a.F_Parent_Id= ? and a.F_State = 1 order by f_sort asc";
		javax.persistence.Query query = daoAppOrgTree.CreateNativeSQL(SQL,
				ViewAppOrgTree.class);
		query.setParameter(1, pid);
		return (List<ViewAppOrgTree>) query.getResultList();
	}

	/**
	 * 
	 * @Description	: TODO(这里用一句话描述这个方法的作用)
	 * @Author		: jibb
	 * @Date		: 2013-05-09 12-48
	 * @param pid
	 * @param isHistory 参数撤销
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<ViewTask> GetListTaskTreeForPID(Integer pid,Integer isHistory) {
		String SQL = "select a.* from View_Task a where a.F_Parent_Id= ? order by F_Sort asc ";
		/*if(isHistory!=2)
		    SQL="select a.* from View_Task a where a.F_Parent_Id= ? and a.f_ishistory= ? order by F_Sort asc ";
		    */
		javax.persistence.Query query = daoTaskInfo.CreateNativeSQL(SQL,
				ViewTask.class);
		query.setParameter(1, pid);
		/*if(isHistory!=2)
		    query.setParameter(2, isHistory);*/
		return (List<ViewTask>) query.getResultList();
	}

	/*
	 * 根据部门ID，查询部门人员列表
	 */
	public List<ViewGroupUser> getUserByOrgId(Integer OrgId,String Station) {
		String SQLString="";
		String whereString="";		
		if(Station!="ALL"){
			String[] tempStation=Station.split(",");
			for(int i=0;i<tempStation.length;i++){
				whereString +=whereString+" f_unit_station="+tempStation[i]+" and ";
			}				
		}		
		SQLString= "select a.* from View_GroupUser a,t_app_org_user b where a.f_id=b.fk_user_id and %1$s b.fk_org_id=%2$d order by a.f_sort";
		SQLString = String.format(SQLString, whereString, OrgId);
		return daoViewGroupUser.ExecuteSQLResult(SQLString, ViewGroupUser.class);
	}
	//查询所有部门下人员列表
	public List<ViewGroupUserAll> getGroupUserAll() {
		String SQLString = "select a.* from View_GroupUserAll a order by a.f_sort";		
		return daoViewGroupUserAll.ExecuteSQLResult(SQLString, ViewGroupUserAll.class);
	}
	// ///根据ID，查询代码列表
	public List<ViewCodeTree> getCodeTree(Integer id) {
		String SQLString = "select a.* from View_CodeTree a where  a.F_Parent_Id=%1$d order by a.f_sort";
		SQLString = String.format(SQLString, id);
		return daoViewCodeTree.ExecuteSQLResult(SQLString, ViewCodeTree.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Hashtable> getCodeTreeToHashtable(Integer id) {
		String SQL = "select a.* from View_CodeTree a where  a.F_Parent_Id=? order by a.f_sort";
		javax.persistence.Query query = daoViewCodeTree.CreateNativeSQL(SQL,
				ViewCodeTree.class);
		query.setParameter(1, id);
		return (List<Hashtable>) query.getResultList();
	}

	// ////////////////////////代码树递归算法///////////////////////////////////////////////////
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCodeTreeData(String fktypeid) {
		// 通过id 获取第一层节点列表
		List<ViewCodeTree> objfirstlist = new ArrayList();
		String SQL = "select * from View_CodeTree where fk_tree_code_type_id=? ";
		javax.persistence.Query query = daoViewCodeTree.CreateNativeSQL(SQL,
				ViewCodeTree.class);
		query.setParameter(1, fktypeid);
		List<ViewCodeTree> list = query.getResultList();
		for (ViewCodeTree obj : list) {
			if (obj.getFParentId().toString().equals(fktypeid)) {
				objfirstlist.add(obj);
			}
		}
		List tree = buildCodeTree(list, objfirstlist);
		return tree;
	}

	/**
	 * Menu查询 递归 
	 * 2013-3-14
	 * @param fktypeid
	 * @param isMulti
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCodeTreeDataToMenu(String fktypeid,Boolean isMulti) {
		// 通过id 获取第一层节点列表
		List<ViewCodeTree> objfirstlist = new ArrayList();
		String SQL = "select * from View_CodeTree where f_state=1 and fk_tree_code_type_id=? ";
		javax.persistence.Query query = daoViewCodeTree.CreateNativeSQL(SQL,
				ViewCodeTree.class);
		query.setParameter(1, fktypeid);
		List<ViewCodeTree> list = query.getResultList();
		for (ViewCodeTree obj : list) {
			if (obj.getFParentId().toString().equals(fktypeid)) {
				objfirstlist.add(obj);
			}
		}
		List tree = buildCodeTreeToMenu(list, objfirstlist,isMulti);
		return tree;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildCodeTree(List<ViewCodeTree> objList,
			List<ViewCodeTree> objparentList) {
		List list = new ArrayList();
		for (int i = 0; i < objparentList.size(); i++) {
			Map<String, Object> map = new HashMap();
			ViewCodeTree obj = (ViewCodeTree) objparentList.get(i);
			// 1、为叶子节点
			if (obj.getIsleft()) {
				map.put("id", obj.getFId().toString());
				// map.put("state", "closed");
				map.put("text", obj.getFCodeText());
				map.put("iconCls", "icon-100");
				// map.put("checked", false);
				list.add(map);
			} else { // 2、有下级节点
				map.put("id", obj.getFId().toString());
				map.put("state", "open");
				map.put("text", obj.getFCodeText());
				map.put("iconCls", "icon-200");
				map.put("checked", false);
				map.put("children",
						buildCodeTree(objList,
								findChildCode(objList, obj.getFId())));
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 2013-3-14
	 * @param objList
	 * @param objparentList
	 * @param isMulti
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildCodeTreeToMenu(List<ViewCodeTree> objList,
			List<ViewCodeTree> objparentList,Boolean isMulti) {
		List list = new ArrayList();
		for (int i = 0; i < objparentList.size(); i++) {
			Map<String, Object> map = new HashMap();
			ViewCodeTree obj = (ViewCodeTree) objparentList.get(i);
			// 1、为叶子节点
			if (obj.getIsleft()) {
				map.put("id", obj.getFId().toString());
				map.put("isLeaf", "Y");
				map.put("disabled", false);
				map.put("text", obj.getFCodeText());
				map.put("iconCls", "icon-leaf");
				map.put("href", "#Y");
				if(isMulti)
					map.put("isCheck", "true");
				list.add(map);
			} else { // 2、有下级节点,父亲节点
				map.put("id", obj.getFId().toString());
				map.put("isLeaf", "N");
				map.put("disabled", false);
				map.put("text", obj.getFCodeText());
				map.put("iconCls", "icon-root");
				map.put("href", "#N");
				//map.put("onclick", "alert('大类不能选择！');");
				map.put("children",
						buildCodeTreeToMenu(objList,findChildCode(objList, obj.getFId()),isMulti));
				list.add(map);
			}
		}
		return list;
	}
	// 查找子节点
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ViewCodeTree> findChildCode(List<ViewCodeTree> objList,
			Integer id) {
		List<ViewCodeTree> objlist = new ArrayList();
		for (ViewCodeTree obj : objList) {
			if (obj.getFParentId() == id)
				objlist.add(obj);
		}
		return objlist;
	}

	// ////////////////////////代码树递归算法///////////////////////////////////////////////////
	// ////////////////////////组织机构用户树递归算法///////////////////////////////////////////////////
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getOrgUserTreeData(String parentid,String deptIdString) {
		// 通过id 获取第一层节点列表
		List<ViewAppOrgTree> objfirstlist = new ArrayList();
		String SQL ="";
		if(deptIdString.equals("-1"))
		    SQL="select * from View_App_Org_Tree where f_parent_id=? ";
		else {
		    SQL="select * from View_App_Org_Tree where f_id=? ";
		    parentid=deptIdString;
        }
		javax.persistence.Query query = daoAppOrgTree.CreateNativeSQL(SQL,
				ViewAppOrgTree.class);
		if(deptIdString.equals("-1"))
		    query.setParameter(1, 0);
		else
		    query.setParameter(1, parentid);
		List<ViewAppOrgTree> list = query.getResultList();
		for (ViewAppOrgTree obj : list) {
		    if(deptIdString.equals("-1")){
    			if (obj.getFParentId().toString().equals(parentid)) {
    				objfirstlist.add(obj);
    			}
		    }
    		else {
    		    if (obj.getFId().toString().equals(parentid)) {
                    objfirstlist.add(obj);
                }  
            }
		}
		List tree = buildOrgUserTree(list, objfirstlist);
		return tree;
	}

	//TODO
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildOrgUserTree(List<ViewAppOrgTree> objList,
			List<ViewAppOrgTree> objparentList) {
		List list = new ArrayList();
		List<ViewGroupUserAll> groupUserListAll= getGroupUserAll();
		for (int i = 0; i < objparentList.size(); i++) {
			Map<String, Object> map = new HashMap();
			ViewAppOrgTree obj = (ViewAppOrgTree) objparentList.get(i);
			// 1、为叶子节点
			if (obj.getIsleft()) {
				map.put("id", obj.getFId().toString());
				map.put("state", "open");
				map.put("text", obj.getFName());
				map.put("iconCls", "icon-100");
				
				map.put("attributes", "{\"isLeaf\":false}");
				// 添加用户节点
				map.put("children", bulidUserNodeList(groupUserListAll,obj.getFId()));
				// map.put("checked", false);
				list.add(map);
			} else { // 2、有下级节点
				map.put("id", obj.getFId().toString());
				map.put("state", "open");
				map.put("text", obj.getFName());
				map.put("iconCls", "icon-200");
				map.put("checked", false);
				map.put("attributes", "{\"isLeaf\":false}");
				map.put("children",
						buildOrgUserTree(objList,
								findChildOrgUser(objList, obj.getFId())));
				list.add(map);
			}
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List bulidUserNodeList(List<ViewGroupUserAll> userAllList, Integer orgid) {
		List list = new ArrayList();
		try {
			List<ViewGroupUserAll> objList = findGroupUsers(userAllList, orgid);
			for (ViewGroupUserAll obj : objList) {
				Map<String, Object> map = new HashMap();
				map.put("id", obj.getFId().toString());				
				map.put("text", obj.getFName());
				map.put("iconCls", "tree-user-pp");
				map.put("attributes", "{\"isLeaf\":true}");
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//easy tree grid
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public List getOrgUserTreeDataEasy(String deptid) {
        // 通过id 获取第一层节点列表
        String SQL ="";
        if(deptid.equals("-1"))
            SQL="select * from View_App_Org_Tree ";
        else {
            SQL="select * from View_App_Org_Tree where f_id in ("+deptid+")";             
        }
        javax.persistence.Query query = daoAppOrgTree.CreateNativeSQL(SQL,
                ViewAppOrgTree.class);        
        List<ViewAppOrgTree> list = query.getResultList();       
        List tree = buildOrgUserTreeEasy(list);
        return tree;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
    private List buildOrgUserTreeEasy(List<ViewAppOrgTree> objList) {
        List list = new ArrayList();
        List<ViewGroupUserAll> groupUserListAll= getGroupUserAll();
        for (int i = 0; i < objList.size(); i++) {
            Map<String, Object> map = new HashMap();
            ViewAppOrgTree obj = (ViewAppOrgTree) objList.get(i);         
                map.put("id", obj.getFId().toString());
                map.put("state", "close"); 
                map.put("iconCls", "icon-1000");
                map.put("deptname", obj.getFName());
                map.put("userid", 0);
                map.put("username", "");
                map.put("userunit", "");
                map.put("_parentId", obj.getFParentId());
                list.add(map);
                // 添加用户节点
                //map.put("children", bulidUserNodeListEasy(groupUserListAll,obj.getFId()));
                List<ViewGroupUserAll> objUserList = findGroupUsers(groupUserListAll, obj.getFId());
                for (ViewGroupUserAll objuser : objUserList) {
                    Map<String, Object> mapuser = new HashMap();
                    mapuser.put("id", objuser.getFId().toString());
                    mapuser.put("state", "open"); 
                    mapuser.put("iconCls", "icon-2000");
                    mapuser.put("deptname",obj.getFName());
                    mapuser.put("userid", objuser.getFId());
                    mapuser.put("username", objuser.getFName());
                    mapuser.put("userunit", objuser.getFGroupname());
                    mapuser.put("_parentId", obj.getFId());
                    list.add(mapuser);
                }            
        }
        return list;
    }
	 
	//查找部门用户，通过orgid
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ViewGroupUserAll> findGroupUsers(List<ViewGroupUserAll> objUserList,Integer orgid){
		List<ViewGroupUserAll> objlist=new ArrayList();
		for(ViewGroupUserAll obj : objUserList){
			if(obj.getFkOrgId()==orgid)
				objlist.add(obj);
		}
		return objlist;
	}
	// 查找子节点
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ViewAppOrgTree> findChildOrgUser(List<ViewAppOrgTree> objList,
			Integer id) {
		List<ViewAppOrgTree> objlist = new ArrayList();
		for (ViewAppOrgTree obj : objList) {
			if (obj.getFParentId() == id)
				objlist.add(obj);
		}
		return objlist;
	}
}
