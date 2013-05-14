package com.info.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.info.common.util.ActionEnum;
import com.info.common.util.ActionLog;

@Repository
public class BaseDaoImp<T> implements IBaseDao<T> {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ActionLog<T> actionLog;
	
	private String EntityName;
	private static final Logger logger;

	static {
		logger = Logger.getLogger("infoLogger");
		logger.setLevel(Level.ALL);
	}

	@Override
	public boolean Save(T Entity) {
		boolean result;
		try {
			EntityName = Entity.getClass().getSimpleName();
			entityManager.persist(Entity);
			//插入日志
			actionLog.writeLog(Entity, ActionEnum.Insert);
			result = true;
		} catch (Exception e) {
			logger.error("持久化" + EntityName + "失败", e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean Persist(T Entity) {
		return Save(Entity);
	}

	@Override
	public boolean Update(T Entity) {
		boolean result;
		try {
			EntityName = Entity.getClass().getSimpleName();
			entityManager.merge(Entity);
			//插入日志
			actionLog.writeLog(Entity, ActionEnum.Update);
			result = true;
		} catch (Exception e) {
			logger.error("更新" + EntityName + "失败", e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean Delete(T Entity) {
		boolean result;
		try {
			if (!entityManager.contains(Entity))
				Entity = entityManager.merge(Entity);
			entityManager.remove(Entity);
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result = false;
		}
		return result;
	}

	@Override
	public T GetEntity(Class<T> entityClass, Serializable id) {
		try {
			return entityManager.find(entityClass, id);
		} catch (Exception e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public T GetBy(Class<T> entityClass, String fieldName, Serializable value) {
		try {
			EntityName = getEntityName(entityClass);
			Query query = entityManager.createQuery("select model from "
					+ EntityName + " model where model." + fieldName + "=?1");
			query.setParameter(1, value);
			query.setMaxResults(1);
			return (T) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> FindByProperty(Class<T> entityClass, String propertyName,
			final Object value,String OrderBY) {
		EntityName = getEntityName(entityClass);
		List<T> list = null;
		try {
			String queryString = "select model from " + EntityName
					+ " model where model." + propertyName + "= :propertyValue";
			if(OrderBY!=null)
				queryString=queryString +" "+ OrderBY;
			Query query = entityManager.createQuery(queryString);
			query.setParameter("propertyValue", value);
			list = query.getResultList();
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
			list=null;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> Query(String JPQL){
		try
		{
			Query query=entityManager.createQuery(JPQL);
			return query.getResultList();
		}catch(Exception e){
			return null;
		}
		
	}
	@Override
	public List<T> Query(Class<T> entityClass, String scope) {
		return Query(entityClass, scope, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<T> Query(Class<T> entityClass, String scope, Collection paras) {
		return Query(entityClass, scope, null, -1, -1);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> Query(Class<T> entityClass, String scope, Collection paras,
			int start, int limit) {
		try {
			EntityName = getEntityName(entityClass);
			Query query = entityManager.createQuery("select o from "
					+ EntityName + "o where " + scope);
			int parameterIndex = 0;
			if (paras != null && paras.size() > 0) {
				for (Object obj : paras) {
					parameterIndex++;
					query.setParameter(parameterIndex, obj);
				}
			}
			if (start >= 0 && limit > 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Object UniqueResult(String sql) {
		return UniqueResult(sql, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object UniqueResult(String sql, Collection paras) {
		try
		{
		Query query = entityManager.createQuery(sql);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				parameterIndex++;
				query.setParameter(parameterIndex, obj);
			}
		}
		query.setMaxResults(1);
		return query.getSingleResult();
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public int ExecuteSQL(String sql) {
		return ExecuteSQL(sql, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int ExecuteSQL(String sql, Collection paras) {
		try {
			Query query = entityManager.createNativeQuery(sql);
			int parameterIndex = 0;
			if (paras != null && paras.size() > 0) {
				for (Object obj : paras) {
					parameterIndex++;
					query.setParameter(parameterIndex, obj);
				}
			}
			return query.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}		
	}

	@Override
	public boolean Delete(Class<T> entityClass, Object entityid) {
		boolean result;
		try {
			Delete(entityClass, new Object[] { entityid });
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public boolean Delete(Class<T> entityClass, Object[] entityids) {
		boolean result;
		try {
			for (Object id : entityids) {
				entityManager.remove(entityManager
						.getReference(entityClass, id));
			}
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public int getCount(Class<T> entityClass, String scope,
			@SuppressWarnings("rawtypes") Collection paras) {
		EntityName = getEntityName(entityClass);
		Query query = entityManager.createQuery("select count(*) from "
				+ EntityName + " where " + scope);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				parameterIndex++;
				query.setParameter(parameterIndex, obj);
			}
		}
		return ((Integer) query.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<T> ExecuteSQLResult(String sql,Class<T> entityClass) {
		try
		{
		Query query = entityManager.createNativeQuery(sql,entityClass);
		return (List<T>) query.getResultList();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public javax.persistence.Query CreateNativeSQL(String NativeSQL,Class<?> entityClass){
		return entityManager.createNativeQuery(NativeSQL,entityClass);
	}
	
	public javax.persistence.Query CreateNativeSQL(String NativeSQL) {
		return entityManager.createNativeQuery(NativeSQL);
	}
	
	public javax.persistence.Query CreateQuery(String JPQL,Class<?> entityClass) {
		return entityManager.createQuery(JPQL,entityClass);
	}
	public javax.persistence.Query CreateQuery(String JPQL){
		return entityManager.createQuery(JPQL);
	}
	protected String getEntityName(Class<T> entityClass) {
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}

}
