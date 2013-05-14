package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TContract;

@Service
@Transactional
public class BussContractService {

	@Autowired
	IBaseDao<TContract> contractDao;
	@Autowired
	AppSEQHelper SEQHelper;
	
	public TContract getContractByID(Integer id){
		return contractDao.GetEntity(TContract.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TContract> getContracts(int unitid, int startIndex,int pageSize){
		StringBuilder str=new StringBuilder();
		str.append("select o from TContract o where FState <> 1 and FEntrustUnitId ="+unitid);
		str.append(" order by o.FId desc");
		Query query= contractDao.CreateQuery(str.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<TContract> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unused")
	public int getContractsCount(int unitid){
		StringBuilder str=new StringBuilder();
		str.append("select o from TContract o where FState <> 1 and FEntrustUnitId ="+unitid);
		str.append(" order by o.FId desc");
		Query query= contractDao.CreateQuery(str.toString());
		List<TContract> list=contractDao.Query(str.toString());
		return list.size();
	}
	
	public TContract save(TContract contract) {
		Integer id = SEQHelper.getCurrentVal("SEQ_CONTRACT");
		contract.setFId(id);
		if(contractDao.Save(contract)){
			return contract;
		}else{
			return null;
		}
	}
	
	public TContract update(TContract contract){
		if(contractDao.Update(contract)){
			return contract;
		}else{
			return null;
		}
	}
	
	public boolean delete(Integer id){
		if(contractDao.Delete(TContract.class, id)){
			return true;
		}else{
			return false;
		}
	}
	//合同作废
	public boolean cancel(TContract contract){
		contract.setFState(1);
		if(contractDao.Update(contract)){
			return true;
		}else{
			return false;
		}
	}
	//合同锁定
	public boolean lock(TContract contract){
		contract.setFState(2);
		if(contractDao.Update(contract)){
			return true;
		}else{
			return false;
		}
	}
	//合同解锁
	public boolean deblock(TContract contract){
		contract.setFState(0);
		if(contractDao.Update(contract)){
			return true;
		}else{
			return false;
		}
	}
}
