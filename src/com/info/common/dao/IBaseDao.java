/**
 * 通用dao接口
 */
package com.info.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author liwx at 2012-03-10
 * 
 */
public interface IBaseDao<T> {
	/**
	 * 把对象保存到持久层
	 * 
	 * @param Entity
	 * @return 若保存成功，则返回true，否则返回false
	 */
	boolean Save(T Entity);

	/**
	 * 数据持久化，与Save是相同的功能，只是在Jpa中称持久化
	 * 
	 * @param Entity
	 * @return 若保存成功，则返回true，否则返回false
	 */
	boolean Persist(T Entity);

	/**
	 * 更新持久层中的对象
	 * 
	 * @param Entity
	 * @return 若修改成功，则返回true，否则返回false
	 */
	boolean Update(T Entity);

	/**
	 * 删除持久层中的对象
	 * 
	 * @param Entity
	 * @return 若删除操作成功，则返回true，否则返回false
	 */
	boolean Delete(T Entity);

	boolean Delete(Class<T> EntityName, Object entityid);  
	boolean Delete(Class<T> EntityName, Object[] entityids);
	
	int getCount(Class<T> entityClass,String scope, @SuppressWarnings("rawtypes") Collection paras);  
	/**
	 * 根据类及主键加载对象
	 * 
	 * @param EntityName
	 * @param id
	 * @return 若查找到指定主键值的持久对象，则返回该对象，否则返回null
	 */
	T GetEntity(Class<T> EntityName, Serializable id);

	/**
	 * 根据类、字段名及字段值加载对象，只加载一条符合条件的对象。
	 * 
	 * @param EntityName
	 * @param fieldName
	 * @param value
	 * @return 若查询到指定属性及值的持久对象，则返回该对象，否则返回null
	 */
	T GetBy(Class<T> EntityName, String fieldName, Serializable value);
	
	/**
	 * 根据某一个实体字段查询	
	 * @return 返回查询的记录结果记录
	 */
	List<T> FindByProperty(Class<T> classType,String propertyName, 
			final Object value,String OrderBY);
	
	/**
	 * 根据JPQL查询数据集	
	 * @return 返回查询的记录结果记录
	 */
	List<T> Query(String JPQL);
	/**
	 * 根据条件查询对象
	 * 
	 * @param EntityName
	 *            类名
	 * @param scope
	 *            查询条件
	 * @return 返回查询的记录结果记录
	 */
	List<T> Query(Class<T> EntityName, String scope);
	
	
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
	List<T> Query(Class<T> EntityName, String scope, Collection paras);

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
	List<T> Query(Class<T> EntityName, String scope, Collection paras,
			int start, int limit);

	/**
	 * 执行sql语句，并返回一个对象,如select count(*) from tableName等
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回查询结果，若没有结果则返回null
	 */
	Object UniqueResult(String sql);

	/**
	 * 根据sql语句及查询参数执行查询，并返回一个唯一对象，如select count(*) from tableName where filed=?
	 * 
	 * @param sql
	 * @param paras
	 * @return 返回单一的查询结果，若没有结果则返回null
	 */
	@SuppressWarnings("rawtypes")
	Object UniqueResult(String sql, Collection paras);

	/**
	 * 执行update,delete sql语句，返回受影响的记录数,也可以调用存储过程
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回受影响的记录数
	 */
	int ExecuteSQL(String sql);

	/**
	 * 根据update,delete语句及参数执行数据库操作，返回受影响的记录数
	 * 
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数
	 * @return 返回受影响的记录数
	 */
	@SuppressWarnings("rawtypes")
	int ExecuteSQL(String sql, Collection paras);
	
	/**
	 * 根据select语句及参数执行数据库操作，返回记录集
	 * 
	 * @param sql
	 *            sql语句	
	 * @param entityClass           
	 * @return 记录集
	 */
	List<T> ExecuteSQLResult(String sql,Class<T> entityClass);
	
	/**
	 * 直接返回JPA的Query对象，便于设置SQL参数
	 * @param NativeSQL 原生SQL语句
	 * @param entityClass
	 * @return
	 */
	javax.persistence.Query CreateNativeSQL(String NativeSQL,Class<?> entityClass);
	
	javax.persistence.Query CreateNativeSQL(String NativeSQL);
	
	javax.persistence.Query CreateQuery(String JPQL,Class<?> entityClass);
	javax.persistence.Query CreateQuery(String JPQL);
}
