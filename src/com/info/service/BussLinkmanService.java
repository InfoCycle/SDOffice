package com.info.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TLinkman;
import com.info.domain.ViewTclientLinkman;

@Service
@Transactional
public class BussLinkmanService {
	@Autowired
	IBaseDao<TLinkman> linkmanDao;
	@Autowired
	AppSEQHelper seqHelper;
	/**
	 * 返回所有的联系人
	 * @return
	 */
	public List<TLinkman> getAllLinkman() {
		String jpql="SELECT * FROM T_Linkman order by F_Id desc";
		return linkmanDao.Query(jpql);
	}
	/**
	 * 根据clientId查询联系人信息
	 * @param linkman
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "null", "rawtypes" })
	public List<TLinkman> getLinkmansBY(Class<TLinkman> linkman,int id) {
		Collection ids=null;
		ids.add(id);
		return linkmanDao.Query(linkman, "F_Client_Id=?1",ids);
	}
	/**
	 * 添加联系人
	 * @param linkman
	 * @return
	 */
	public boolean add(TLinkman linkman) {
		linkman.setFId(seqHelper.getCurrentVal("SEQ_LINKMAN"));
		return linkmanDao.Persist(linkman);
	}
	/**
	 * 修改联系人
	 * @param linkman
	 * @return
	 */
	public boolean update(TLinkman linkman) {
		return linkmanDao.Update(linkman);
	}
	/**
	 * 删除联系人
	 * @param linkman
	 * @return
	 */
	public boolean delete(TLinkman linkman) {
		return linkmanDao.Delete(linkman);
	}
	/**
	 * 根据公司Id和用户Id返回联系人
	 * @param id 联系人所属公司Id
	 * @param userId 用户id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewTclientLinkman> getByClientId(int id,int userId,int org,int station) {
		String sql="select * from View_Tclient_Linkman where F_Client_Id="+id;
		if(station==1009){
			sql+=" and F_User_Id="+userId;
		}else if (station==1007) {
			sql+=" and FK_ORG_ID="+org;
		}
		sql+="order by F_Id desc";
		Query query=linkmanDao.CreateNativeSQL(sql,ViewTclientLinkman.class);
		 
		return (List<ViewTclientLinkman>)query.getResultList();
	}
	/**
	 * 根据公司Id返回联系人
	 * @param id 公司Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewTclientLinkman> getByClientId(int id){
		String sql="select * from View_Tclient_Linkman where F_Client_Id=?";
		Query query=linkmanDao.CreateNativeSQL(sql,ViewTclientLinkman.class);
		query.setParameter(1, id);
		List<ViewTclientLinkman> list=query.getResultList();
		return list;
	}
	/**
	 * 返回所有联系人的ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getAllId() {
		String sql="select F_Id from T_linkman";
		Query query=linkmanDao.CreateNativeSQL(sql);
		List<Integer> list=query.getResultList();
		return list;
	}
	/**
	 * 根据关键字返回所有姓名包含的数据
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewTclientLinkman> select(String name,int clientId,int userId) {
		String sql="select * from View_Tclient_Linkman where F_Client_Id=? and F_User_Id=? and F_Name like ? ";
		Query query=linkmanDao.CreateNativeSQL(sql,ViewTclientLinkman.class);
		query.setParameter(1, clientId);
		query.setParameter(2, userId);
		query.setParameter(3, "%"+name+"%");
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getClient(String cname,String lname) {
		String sql="select F_Client_Id from View_Tclient_Linkman where F_Client_Name like ?";
		List<Integer> list=null;
		if(lname.equals("")&&!cname.equals("")){
			Query query=linkmanDao.CreateNativeSQL(sql);
			query.setParameter(1, "%"+cname+"%");
			list=query.getResultList();
		}else {
			sql+="and F_Name like ?";
			Query query=linkmanDao.CreateNativeSQL(sql);
			query.setParameter(1, "%"+cname+"%");
			query.setParameter(2, "%"+lname+"%");
			list=query.getResultList();
		}
		return list;
		
	}
}
