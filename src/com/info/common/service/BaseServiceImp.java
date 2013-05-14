package com.info.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;

@Service
@Transactional
public class BaseServiceImp<T> implements IBaseService<T> {	
	
	@Autowired
	IBaseDao<T> baseDAO;
	
	@Override	
	public boolean save(T Entity) {		
		return baseDAO.Persist(Entity);
	}

	@Override	
	public boolean persist(T Entity) {
		return baseDAO.Persist(Entity);
	}
		
	
	@Override
	public boolean update(T Entity) {
		return baseDAO.Update(Entity);
	}

	@Override
	public boolean delete(T Entity) {
		return baseDAO.Delete(Entity);
	}

	@Override
	public T getEntity(Class<T> EntityName, Serializable id) {
		return baseDAO.GetEntity(EntityName, id);
	}

	@Override
	public T getBy(Class<T> EntityName, String fieldName, Serializable value) {
		try
		{
			return (T)baseDAO.GetBy(EntityName, fieldName, value);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	@Override
	public List<T> findByProperty(Class<T> entityClass,String propertyName, 
			final Object value,String OrderBY)
	{
		return (List<T>)baseDAO.FindByProperty(entityClass, propertyName, value,OrderBY);
	}
	@Override
	public List<T> query(Class<T> EntityName, String scope) {
		return baseDAO.Query(EntityName, scope);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<T> query(Class<T> EntityName, String scope, Collection paras) {
		return baseDAO.Query(EntityName, scope, paras);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<T> query(Class<T> EntityName, String scope, Collection paras,
			int start, int limit) {
		return baseDAO.Query(EntityName, scope, paras, start, limit);
	}

	@Override
	public Object uniqueResult(String sql) {		
		return  baseDAO.UniqueResult(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object uniqueResult(String sql, Collection paras) {
		return  baseDAO.UniqueResult(sql, paras);
	}

	@Override
	public int executeSQL(String sql) {		
		return  baseDAO.ExecuteSQL(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int executeSQL(String sql, Collection paras) {
		return  baseDAO.ExecuteSQL(sql, paras);
	}
	
	
	@SuppressWarnings("rawtypes")
	public int getCount(Class<T> entityClass, String scope, Collection paras) {
		return baseDAO.getCount(entityClass, scope, paras);
	}
	
	public boolean delete(Class<T> entityClass, Object entityid) {
		return baseDAO.Delete(entityClass,entityid);
	}
	
	public boolean delete(Class<T> entityClass, Object[] entityids) {
		return baseDAO.Delete(entityClass,entityids);
	}	
}
