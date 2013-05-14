package com.info.service;

import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TEfficiencyWage;
import com.info.domain.ViewEfficiencyWage;
import com.info.domain.ViewWageQuery;

@Service
@Transactional
public class EfficiencyWageService {

	@Autowired
	IBaseDao<ViewEfficiencyWage> vDao;
	
	@Autowired
	AppSEQHelper SEQHelper;
	
	@Autowired
	IBaseDao<ViewWageQuery> wageDao;
	
	/**
	 * 根据任务Id，获取项目部分信息、委托单位信息、合同信息、项目计划信息、项目组人员信息、人员信息(标识是否是外协、内部)
	 * @param Id
	 * @return 返回ViewEfficiencyWage结果集
	 */
	public List<ViewEfficiencyWage> getWage(Integer Id){
		String command="select o from ViewEfficiencyWage o where taskId="+Id;
		List<ViewEfficiencyWage> query=vDao.Query(command);
		return query;
	}
	
	public List<ViewWageQuery> getWageList(ViewWageQuery entity){
		String command="select o from ViewWageQuery o where 1=1";
		Query query= wageDao.CreateQuery(command, ViewWageQuery.class);
		return query.getResultList();
	}
}
