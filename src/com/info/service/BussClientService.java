package com.info.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TClient;
import com.info.domain.ViewClientIndustry;

@Service
@Transactional
public class BussClientService {
	@Autowired
	IBaseDao<TClient> clientDao;
	@Autowired
	AppSEQHelper SEQHelper;
	/**
	 *返回委托公司信息
	 * @return 
	 */
	@SuppressWarnings({"unchecked"})
	public List<ViewClientIndustry> getClient(int id) {
		String sql="select * from View_Client_Industry where F_Id = ?";
		Query query=clientDao.CreateNativeSQL(sql,ViewClientIndustry.class);
		query.setParameter(1, id);
		List<ViewClientIndustry> list =query.getResultList();
		return list;
	}
	/**
	 * 委托公司信息变更
	 * @param client
	 * @return
	 */
	public boolean updateClient(TClient client) {
		return clientDao.Update(client);
	}
	/**
	 * 委托公司添加
	 * @param client
	 * @return
	 */
	public boolean addClient(TClient client) {
		client.setFId(SEQHelper.getCurrentVal("SEQ_TASK"));
		return clientDao.Persist(client);
	}
	/**
	 * 返回所有的委托公司
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewClientIndustry> getAllClient(int start,int limit) {
		String sql="select * from View_Client_Industry order by F_EntryTime desc";
		Query query=clientDao.CreateNativeSQL(sql,ViewClientIndustry.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		List<ViewClientIndustry> list =query.getResultList();
		return list;
	}
	@SuppressWarnings("unchecked")
	public int getcon(){
		String sql="select count(*) from View_Client_Industry";
		Query query=clientDao.CreateNativeSQL(sql);
		int con=Integer.parseInt(query.getSingleResult().toString());
		return con;
	}
	@SuppressWarnings("unchecked")
	public List<ViewClientIndustry> getByWay(String iterm,String way,int start, int limit) {
		List<ViewClientIndustry> list=null;
		try {
			Query query=clientDao.CreateNativeSQL(way,ViewClientIndustry.class);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			query.setParameter(1,"%"+iterm+"%");
			list=query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}
	/**
	 * 返回所有ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getAllId() {
		String sq="select F_Id from T_Client";
		Query query=clientDao.CreateNativeSQL(sq);
		List<Integer> list=query.getResultList();
		return list;
	}
	public int updataStart(String ids) {
		String sql="update T_Client set F_Possition='启用' where F_Id in ("+ids+")";
		return clientDao.ExecuteSQL(sql);
	}
	public int updataStope(String ids) {
		String sql="update T_Client set F_Possition='停用' where F_Id in ("+ids+")";
		return clientDao.ExecuteSQL(sql);
	}
	/**
	 * 返回所有编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllCord(){
		List<String> list=null;
		SimpleDateFormat sm=new SimpleDateFormat("yyyy");
		String ss=sm.format(new Date());
		ss="'%"+ss+"%'";
		String sql="select F_Cord from T_Client where F_Cord like"+ss;
		try {
		Query query=clientDao.CreateNativeSQL(sql);
		
		//query.setParameter(1,ss);
		
			list=(List<String>)query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TClient> getById(String ids,int start,int limit) {	
		String sql="select * from T_Client where F_Id in ("+ids+") order by F_EntryTime desc";
		Query query = clientDao.CreateNativeSQL(sql,TClient.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	public int getByIdcon(String ids){
		String sql="select count( *) from T_Client where F_Id in ("+ids+")";
		Query query = clientDao.CreateNativeSQL(sql);
		int con=Integer.parseInt(query.getSingleResult().toString());
		return con;
	}
}
