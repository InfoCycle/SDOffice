package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAppTreeCode;
import com.info.domain.TAppTreeCodeType;
import com.info.domain.ViewCodeTree;

@Service
@Transactional
public class SystemTreeCodeService {
	@Autowired
	IBaseDao<TAppTreeCodeType> treeCodeTypeDao;
	@Autowired
	IBaseDao<TAppTreeCode> treeCodeDao;	
	@Autowired
	IBaseDao<ViewCodeTree> viewCodeTreeDao;
	@Autowired
	AppSEQHelper SEQHelper;
	
	public boolean SaveOrUpdate(TAppTreeCodeType obj){
		if(obj.getFId()>0)
		{
			return treeCodeTypeDao.Update(obj);
		}
		else {			
			obj.setFId(SEQHelper.getCurrentVal("SEQ_TREECODE"));//getCurrentVal());
			return treeCodeTypeDao.Persist(obj);
		}
	}
	
	public boolean SaveOrUpdate(TAppTreeCode obj){
		if(obj.getFId()>0)
		{
			return treeCodeDao.Update(obj);
		}
		else {			
			obj.setFId(SEQHelper.getCurrentVal("SEQ_TREECODE"));
			return treeCodeDao.Persist(obj);
		}
	}
	
	//删除代码
	public boolean deleteTreeCode(Integer fid){
		try {
			return treeCodeDao.Delete(TAppTreeCode.class, fid);
		} catch (Exception e) {
			return false;
		}
	}
	public List<TAppTreeCodeType> getAppTreeCodeTypes() {
		return treeCodeTypeDao.Query("select o from TAppTreeCodeType o order by o.FSort");
	}
	
	public List<TAppTreeCode> getTreeCodeByType(Integer codeTypeId) {
		return treeCodeDao.Query("select o from TAppTreeCode o where o.fkTreeCodeTypeId="+codeTypeId+ " order by o.FSort");
	}
	public List<TAppTreeCode> getValidTreeCodeByType(Integer codeTypeId) {
		return treeCodeDao.Query("select o from TAppTreeCode o where o.fkTreeCodeTypeId="+codeTypeId+ " and o.FState=1 order by o.FSort");
	}
	public List<TAppTreeCode> getAllTreeCode(Integer fkTreeCodeTypeId) {
		return treeCodeDao.Query("select o from TAppTreeCode o where o.FState=1 and fkTreeCodeTypeId="+fkTreeCodeTypeId.toString()+" order by o.fkTreeCodeTypeId,o.FSort");
	}
	@SuppressWarnings({"unchecked"})
	public List<ViewCodeTree> getAllTreeCodeForView(Integer fkTreeCodeTypeId){
		String  SQL="select a.* from View_CodeTree a where a.FK_TREE_CODE_TYPE_ID= ?";//"and a.F_State = 1";
		javax.persistence.Query query=viewCodeTreeDao.CreateNativeSQL(SQL, ViewCodeTree.class);
		query.setParameter(1,fkTreeCodeTypeId);
		return (List<ViewCodeTree>)query.getResultList();
	}
	
	@SuppressWarnings({"unchecked"})
	public List<ViewCodeTree> GetListSystemTreeCodeForPID(Integer pid){
		String  SQL="select a.* from View_CodeTree a where a.F_Parent_Id= ?  order by f_sort";
		javax.persistence.Query query=viewCodeTreeDao.CreateNativeSQL(SQL, ViewCodeTree.class);
		query.setParameter(1,pid);
		return (List<ViewCodeTree>)query.getResultList();
	}
	
}
