package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TCompaniesInChapter;

@Service
@Transactional
public class BussCompaniesInChapterService {
	@Autowired
	AppSEQHelper appSEQHelper;
	@Autowired
	IBaseDao<TCompaniesInChapter> companiesDao;
	/**
	 * 按标题返回
	 * @param start
	 * @param limit
	 * @param title
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCompaniesInChapter> getTCompaniesInChapters(int start,int limit,String title) {
		String sql="select * from T_CompaniesInChapter where F_Title like ?";
		Query query=companiesDao.CreateNativeSQL(sql,TCompaniesInChapter.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setParameter(1,"%"+title+"%");
		return query.getResultList();
	}
	/**
	 * 返回getTCompaniesInChapters查询到的行数
	 * @param start
	 * @param limit
	 * @param title
	 * @return
	 */
	public long getCountCic(int start,int limit,String title) {
		String sql="select count(*) from T_CompaniesInChapter where F_Title like ?";
		Query query=companiesDao.CreateNativeSQL(sql,TCompaniesInChapter.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setParameter(1,"%"+title+"%");
		return Long.valueOf(query.getSingleResult().toString());
	}
	/**
	 * 添加公司用章
	 * @param cic
	 * @return
	 */
	public boolean addCic(TCompaniesInChapter cic) {
		cic.setFId(appSEQHelper.getCurrentVal("SEQ_COMPIC"));
		return companiesDao.Persist(cic);
	}
	/**
	 * 修改公司用章
	 * @param cic
	 * @return
	 */
	public boolean updataCic(TCompaniesInChapter cic) {
		return companiesDao.Update(cic);
	}
	/**
	 * 删除公司用章
	 * @param cic
	 * @return
	 */
	public boolean deleteCic(TCompaniesInChapter cic) {
		return companiesDao.Delete(cic);
	}
}
