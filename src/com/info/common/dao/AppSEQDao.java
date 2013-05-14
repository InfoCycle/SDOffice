package com.info.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.info.domain.TAppSeq;

@Repository
public class AppSEQDao {	
	@PersistenceContext
	EntityManager entityManager;
	
	public boolean persist(Object obj)
	{
		try
		{
			entityManager.persist(obj);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean update(Object obj)
	{
		try
		{
			entityManager.merge(obj);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public TAppSeq getBy(String value) {
		try
		{
			String QueryStr="select o from TAppSeq o where o.FSeqName=?1";
			Query query = entityManager.createQuery(QueryStr);
			query.setLockMode(LockModeType.PESSIMISTIC_READ);
			query.setParameter(1, value);
			return (TAppSeq) query.getSingleResult();
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
