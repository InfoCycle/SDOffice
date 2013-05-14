package com.info.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IBaseService<T> {
	/**
	 * 把对象保存到持久层
	 * 
	 * @param Entity
	 * @return 若保存成功，则返回true，否则返回false
	 */
	
	boolean save(T Entity);

	/**
	 * 数据持久化，与Save是相同的功能，只是在Jpa中称持久化
	 * 
	 * @param Entity
	 * @return 若保存成功，则返回true，否则返回false
	 */
	
	boolean persist(T Entity);

	/**
	 * 更新持久层中的对象
	 * 
	 * @param Entity
	 * @return 若修改成功，则返回true，否则返回false
	 */
	
	boolean update(T Entity);

	/**
	 * 删除持久层中的对象
	 * 
	 * @param Entity
	 * @return 若删除操作成功，则返回true，否则返回false
	 */
	
	boolean delete(T Entity);

	/**
	 * 根据类及主键加载对象
	 * 
	 * @param EntityName
	 * @param id
	 * @return 若查找到指定主键值的持久对象，则返回该对象，否则返回null
	 */
	T getEntity(Class<T> EntityName, Serializable id);

	/**
	 * 根据类、字段名及字段值加载对象，只加载一条符合条件的对象。
	 * 
	 * @param EntityName
	 * @param fieldName
	 * @param value
	 * @return 若查询到指定属性及值的持久对象，则返回该对象，否则返回null
	 */
	T getBy(Class<T> EntityName, String fieldName, Serializable value);

	List<T> findByProperty(Class<T> entityClass,String propertyName, 
			final Object value,String OrderBY);
	/**
	 * 根据条件查询对象
	 * 
	 * @param EntityName
	 *            类名
	 * @param scope
	 *            查询条件
	 * @return 返回查询的记录结果记录
	 */
	List<T> query(Class<T> EntityName, String scope);

	/**
	 * 根据条件、条件参数查询对象
	 * 
	 * @param EntityName
	 *            类名
	 * @param scope
	 *            查询条件
	 * @param paras
	 *            查询参数
	 * @return 返回查询的记录结果集
	 */
	@SuppressWarnings("rawtypes")
	List<T> query(Class<T> EntityName, String scope, Collection paras);

	/**
	 * 查询符合条件的对象，从start开始取limit条记录
	 * 
	 * @param EntityName
	 *            Java类
	 * @param scope
	 *            查询条件
	 * @param paras
	 *            查询参数
	 * @param start
	 *            返回有效结果开始记录数
	 * @param limit
	 *            返回的最多记录数
	 * @return 返回查询的记录结果集
	 */
	@SuppressWarnings("rawtypes")
	List<T> query(Class<T> EntityName, String scope, Collection paras,
			int start, int limit);

	/**
	 * 执行sql语句，并返回一个对象,如select count(*) from tableName等
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回查询结果，若没有结果则返回null
	 */
	Object uniqueResult(String sql);

	/**
	 * 根据sql语句及查询参数执行查询，并返回一个唯一对象，如select count(*) from tableName where filed=?
	 * 
	 * @param sql
	 * @param paras
	 * @return 返回单一的查询结果，若没有结果则返回null
	 */
	@SuppressWarnings("rawtypes")
	Object uniqueResult(String sql, Collection paras);

	/**
	 * 执行任意sql语句，返回受影响的记录数
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回受影响的记录数
	 */
	int executeSQL(String sql);

	/**
	 * 根据sql语句及参数执行数据库操作，返回受影响的记录数
	 * 
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数
	 * @return 返回受影响的记录数
	 */
	@SuppressWarnings("rawtypes")
	int executeSQL(String sql, Collection paras);
}
