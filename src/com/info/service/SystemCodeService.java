package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAppCode;
import com.info.domain.TAppCodeType;

@Service
@Transactional
public class SystemCodeService {
	@Autowired
	IBaseDao<TAppCodeType> codeTypeDao;
	@Autowired
	IBaseDao<TAppCode> codeDao;	
	@Autowired
	AppSEQHelper SEQHelper;
	
	public boolean SaveOrUpdate(TAppCodeType obj){
		if(obj.getFId()>0)
		{
			return codeTypeDao.Update(obj);
		}
		else {			
			obj.setFId(SEQHelper.getCurrentVal());
			return codeTypeDao.Persist(obj);
		}
	}
	
	public boolean SaveOrUpdate(TAppCode obj){
		if(obj.getFId()>0)
		{
			return codeDao.Update(obj);
		}
		else {			
			obj.setFId(SEQHelper.getCurrentVal());
			return codeDao.Persist(obj);
		}
	}
	
	public List<TAppCodeType> getAppCodeTypes() {
		return codeTypeDao.Query("select o from TAppCodeType o order by o.FSort");
	}
	
	public List<TAppCode> getCodeByType(Integer codeTypeId) {
		return codeDao.Query("select o from TAppCode o where o.fkCodeTypeId="+codeTypeId+ " order by o.FSort");
	}
	public List<TAppCode> getValidCodeByType(Integer codeTypeId) {
		return codeDao.Query("select o from TAppCode o where o.fkCodeTypeId="+codeTypeId+ " and o.FState=1 order by o.FSort");
	}
	public List<TAppCode> getAllCode() {
		return codeDao.Query("select o from TAppCode o where o.FState=1 order by o.fkCodeTypeId,o.FSort");
	}
}
