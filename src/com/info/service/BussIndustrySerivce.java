package com.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TIndustry;
import com.info.domain.ViewClientTree;

@Service
@Transactional
public class BussIndustrySerivce {
	@Autowired
	IBaseDao<TIndustry> industryDao;
	@Autowired
	IBaseDao<ViewClientTree> clientTreeDao;
	
	@Autowired
	AppSEQHelper SEQHelper;
	/**
	 * 返回所有的的行业
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TIndustry> all(){
		String sql="select o from TIndustry o order by o.FSort";
		List<TIndustry> industries =industryDao.Query(sql);
		List prientid =new ArrayList();
		//获取prientid的集合
		for (TIndustry industry : industries) {
				if(!prientid.contains(industry.getFParentId())){
					prientid.add(industry.getFParentId());
				}
		}
		//由buildIndustryTree()获取树
		System.out.println(industries.size());
		List list=buildIndustryTree(industries, prientid, 0);
		return list;
	}
	/**
	 * 根据ID返回Industry
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<TIndustry> getIndustryById(int id) {
		List<TIndustry> list=null;
		String sql="select * from T_Industry where F_Id=?";
		Query query=industryDao.CreateNativeSQL(sql);
		query.setParameter(1, id);
		list=query.getResultList();
		return list;
	}
	/**
	 * 添加行业
	 * @param industry
	 * @return
	 */
	public boolean add(TIndustry industry) {
		industry.setFId(SEQHelper.getCurrentVal("SEQ_TASK"));
		return industryDao.Persist(industry);
	}
	/**
	 * 更新行业
	 * @param industry
	 * @return
	 */
	public boolean updata(TIndustry industry) {
		return industryDao.Update(industry);
	}
	/**
	 * 删除分类
	 * @param industry
	 * @return
	 */
	public boolean delete(TIndustry industry) {
		return industryDao.Delete(industry);
	}
	/**
	 * 返回industry树
	 * @param industries 所有的industry
	 * @param prientid 所有的父节点
	 * @param rootid 根节点Id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildIndustryTree(List<TIndustry> industries,List<Integer> prientid,Integer rootid){
		List list=new ArrayList();
		//遍历所有的Industry
		for (int i = 0; i < industries.size(); i++) {
			Map<String,Object>map=new HashMap<String, Object>();
			TIndustry industry=(TIndustry)industries.get(i);
			//判断industry是否根节点
			if(industry.getFParentId().equals(rootid)){
				//判断industry 是否是父节点
				if(prientid.contains(industry.getFId())){
					map.put("id",industry.getFId().toString());
					map.put("text", industry.getFName().toString());
					map.put("parent", industry.getFParentId());
					map.put("sort", industry.getFSort());
					map.put("leaf", false);
					//用递归法把子节点添加到children
					map.put("children", buildIndustryTree(industries, prientid, industry.getFId()));
					list.add(map);
				}else{
					map.put("id",industry.getFId().toString());
					map.put("text", industry.getFName().toString());
					map.put("parent", industry.getFParentId());
					map.put("sort", industry.getFSort());
					map.put("leaf",true);
					list.add(map);
				}
			}
		}
		return list;
	}
	@SuppressWarnings({"unchecked"})
	public List<ViewClientTree> getClientFoPid(Integer pid) {
		String sql="SELECT a.* from View_ClientTree a WHERE a.F_Parent_Id=? order by F_Sort";
		Query query=clientTreeDao.CreateNativeSQL(sql, ViewClientTree.class);
		query.setParameter(1, pid);
		List<ViewClientTree> list=query.getResultList();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<ViewClientTree> getAllnode() {
		String sql="SELECT a.* from View_ClientTree a order by a.F_Sort";
		Query query=clientTreeDao.CreateNativeSQL(sql, ViewClientTree.class);
		List<ViewClientTree> list=query.getResultList();
		return list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public List<TIndustry> cbIndustries(int id) {
		String sql="select * from T_Industry where f_id!=? and F_Parent_ID!=? order by F_Sort";
		Query query=clientTreeDao.CreateNativeSQL(sql,TIndustry.class);
		query.setParameter(1, id);
		query.setParameter(2, id);
		List<TIndustry> industries=query.getResultList();
		List prientid=new ArrayList();
		for (TIndustry industry : industries) {
			if(!prientid.contains(industry.getFParentId())){
				prientid.add(industry.getFParentId());
			}
		}
		List list=buildIndustryTree(industries, prientid, 0);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getId() {
		String sql="select F_Id from T_Industry";
		Query query=clientTreeDao.CreateNativeSQL(sql);
		List<Integer> list=query.getResultList();
		System.out.println(list);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<String> getCode(int prientId) {
		String sql="select F_Code from T_Industry where F_Parent_Id=?";
		Query query=industryDao.CreateNativeSQL(sql);
		query.setParameter(1, prientId);
		return query.getResultList();
	}
}
