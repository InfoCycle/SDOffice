package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.common.util.DateUtil;
import com.info.common.util.StringUtil;
import com.info.domain.TAppHost;
import com.info.domain.TAppReport;

@Service
@Transactional
public class SystemReportService {
	@Autowired
	IBaseDao<TAppHost> hostDao;
	@Autowired
	IBaseDao<TAppReport> reportDao;
	@Autowired
	AppSEQHelper seqHelper;
	StringBuilder SQL;
	public String getReportDomain() {
		TAppHost host=hostDao.GetEntity(TAppHost.class, 2);
		return host.getFAppHost();
	}
	
	public String getReportletById(int Id) {
		TAppReport report = reportDao.GetEntity(TAppReport.class, Id);
		return report.getFReportlet();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAppReport> getTemplate(int start,int limit,String reportName) {
		SQL = new StringBuilder();
		SQL.append("select * from T_App_Report where 1=1 ");
		if(!StringUtil.isEmpty(reportName)){
			SQL.append(" and F_REPORT_NAME like '%"+reportName+"%'");
		}
		//SQL.append(" order by F_Id desc");
		javax.persistence.Query query= reportDao.CreateNativeSQL(SQL.toString()+" order by F_Id desc",TAppReport.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return (List<TAppReport>)query.getResultList();
	}
	
	public int getTotalCount() {
		String sSQL ="select count(*) from ("+SQL.toString()+") as a";
		javax.persistence.Query query=reportDao.CreateNativeSQL(sSQL);
		int totalCount =Integer.parseInt(query.getSingleResult().toString());
		return totalCount;
	}
	
	public void saveOrUpdate(TAppReport report) {
		if(report.getFId()>0){
			report.setFCreatedate(DateUtil.getTime());
			reportDao.Update(report);
		}else{
			report.setFId(seqHelper.getCurrentVal());
			report.setFCreatedate(DateUtil.getTime());
			reportDao.Persist(report);
		}
	}
	
	public void delete(int id) {
		reportDao.Delete(TAppReport.class, id);
	}
}
