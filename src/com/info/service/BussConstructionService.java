package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TConstructionData;
import com.info.domain.ViewCodeTree;


@Service
@Transactional
public class BussConstructionService {
	@Autowired
	IBaseDao<TConstructionData> constructionDataDao;
	//@Autowired
	//IBaseDao<TConstructionFile> constructionFileDao;	
	@Autowired
	AppSEQHelper SEQHelper;
	
	public TConstructionData getConstructionByID(Integer id){
		return constructionDataDao.GetEntity(TConstructionData.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TConstructionData> getConstructions(int type, int personid,int startIndex,int pageSize){
		StringBuilder str=new StringBuilder();
		str.append("select o from TConstructionData o where  (FPersonId ="+personid+" and FType ="+type+" and FPublicType = 0 ) or ( FType ="+type+" and FPublicType = 1  )");
		str.append(" order by o.FSort ");
		Query query= constructionDataDao.CreateQuery(str.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<TConstructionData> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unused")
	public int getConstructionsCount(int type,int personid){
		StringBuilder str=new StringBuilder();
		str.append("select o from TConstructionData o where FType ="+type+" and FPersonId ="+personid+" or FPublicType = 1");
		str.append(" order by o.FSort");
		Query query= constructionDataDao.CreateQuery(str.toString());
		List<TConstructionData> list=constructionDataDao.Query(str.toString());
		return list.size();
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewCodeTree> GetConstructionTree(Integer ID) {
		String SQL = "select a.* from View_CodeTree a where a.F_Id ="+ID+" or FK_Tree_Code_Type_Id ="+ID+" order by F_Sort";
		javax.persistence.Query query = constructionDataDao.CreateNativeSQL(SQL,
				ViewCodeTree.class);
		return (List<ViewCodeTree>) query.getResultList();
	}
	
	public TConstructionData save(TConstructionData construction) {
		Integer id = SEQHelper.getCurrentVal("SEQ_CONSTRUCTION");
		construction.setFId(id);
		construction.setFSort(id);
		if(constructionDataDao.Save(construction)){
			return construction;
		}else{
			return null;
		}
	}
	
	public TConstructionData update(TConstructionData construction){
		if(constructionDataDao.Update(construction)){
			return construction;
		}else{
			return null;
		}
	}
	
	public boolean delete(Integer id){
		if(constructionDataDao.Delete(TConstructionData.class, id)){
			return true;
		}else{
			return false;
		}
	}	
}
