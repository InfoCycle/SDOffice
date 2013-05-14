package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.domain.TCheckReview;
import com.info.domain.TCustomerEvaluation;
import com.info.domain.TEfficiencyWage;
import com.info.domain.TImplementPlan;
import com.info.domain.TMajorMattersReport;
import com.info.domain.TProjectAppraisalScoure;
import com.info.domain.TProjectCheck;
import com.info.domain.TProjectProcess;
import com.info.domain.TWfProcess;
import com.info.domain.ViewWfProcess;

@Service
@Transactional
public class BussArchiveService {
	@Autowired
	IBaseDao<ViewWfProcess> processDao;//完成的项目
	@Autowired
	IBaseDao<TImplementPlan> implementDao;//实施计划
	@Autowired
	IBaseDao<TProjectProcess> projectDao;//过程记录
	@Autowired
	IBaseDao<TProjectCheck> checkDao;//检查记录
	@Autowired
	IBaseDao<TMajorMattersReport> majorDao;//重大事项报告
	@Autowired
	IBaseDao<TCheckReview> reviewDao;//复核申报
	@Autowired
	IBaseDao<TCustomerEvaluation> customerDao;//顾客评价
	@Autowired
	IBaseDao<TProjectAppraisalScoure> scoureDao;//项目考评打分
	@Autowired
	IBaseDao<TEfficiencyWage> efficiencyDao;//效益工资提取
	@Autowired
	IBaseDao<TWfProcess> tprocessDao;
	
	/**
	 * 业务归档
	 * @param ids
	 * @return
	 */
	public boolean archivedProcess(String ids) {
		String sql="update T_WF_PROCESS set F_STATE=5 where F_ID in("+ids+")";
		int i=tprocessDao.ExecuteSQL(sql);
		if(i>0){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 根据taskId返回效益工资提取
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TEfficiencyWage> getTEfficiencyWages(int taskId) {
		String sql="select * from T_EfficiencyWage where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TEfficiencyWage.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> geteff(int taskId) {
		String effId="";
		List<TEfficiencyWage> list=getTEfficiencyWages(taskId);
		if (list.size()>0) {
			for (TEfficiencyWage tEfficiencyWage : list) {
				effId+=tEfficiencyWage.getFId()+",";
			}
			return getEfficiency(effId.substring(0, effId.length()-1));
		}
		return null;
	}
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getEfficiency(String effId){
		String sql="select * from View_Wf_Process where F_TYPE_ID=10009 and F_Form_PKID in ("+effId+")";
		Query query=processDao.CreateNativeSQL(sql,ViewWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据taskId返回考评打分
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProjectAppraisalScoure> getAppraisalScouresByTaskId(int taskId) {
		String sql="select * from T_ProjectAppraisalScoure where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TProjectAppraisalScoure.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> getSco(int taskId) {
		String scoureId="";
		List<TProjectAppraisalScoure> list=getAppraisalScouresByTaskId(taskId);
		if(list.size()>0){
			for (TProjectAppraisalScoure tProjectAppraisalScoure : list) {
				scoureId+=tProjectAppraisalScoure.getFId();
				scoureId+=",";
			}
			return getScoure(scoureId.substring(0, scoureId.length()-1));
		}
		return null;
	}
	/**
	 * 根据考评打分Id返回process
	 * @param customerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getScoure(String scoureId){
		String sql="select * from View_Wf_Process where F_TYPE_ID=10007 and F_Form_PKID in ("+scoureId+")";
		Query query=processDao.CreateNativeSQL(sql,ViewWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据taskId返回customer
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCustomerEvaluation> getTCustomerEvaluationsByTaskId(int taskId) {
		String sql="select * from T_CustomerEvaluation where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TCustomerEvaluation.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> getCus(int taskId) {
		String cusId="";
		List<TCustomerEvaluation> list=getTCustomerEvaluationsByTaskId(taskId);
		if (list.size()>0) {
			for (TCustomerEvaluation tCustomerEvaluation : list) {
				cusId+=tCustomerEvaluation.getFId()+",";
			}
			return getCustomer(cusId.substring(0,cusId.length()-1));
		}
		return null;
	}
	/**
	 * 根据顾客评价Id返回process
	 * @param customerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getCustomer(String customerId){
		String sql="select * from View_Wf_Process where F_TYPE_ID=10008 and F_Form_PKID in ("+customerId+")";
		Query query=processDao.CreateNativeSQL(sql,ViewWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据taskId返回review
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCheckReview> getTCheckReviewsByTaskId(int taskId) {
		String sql="select * from T_CheckReview where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TCheckReview.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> getReview(int taskId) {
		String reviewId="";
		List<TCheckReview> list=getTCheckReviewsByTaskId(taskId);
		if (list.size()>0) {
			for (TCheckReview tCheckReview : list) {
				reviewId+=tCheckReview.getFId()+",";
			}
			return getReviews(reviewId.substring(0,reviewId.length()-1));
		}
		return null;
	}
	/**
	 * 根据复核申报Id返回process
	 * @param reviewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getReviews(String reviewId){
		String sql="select * from View_Wf_Process where F_TYPE_ID=10006 and F_Form_PKID in ("+reviewId+")";
		Query query=processDao.CreateNativeSQL(sql,ViewWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据taskId返回major
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TMajorMattersReport> geTMajorMattersReportsByTaskId(int taskId) {
		String sql="select * from T_MajorMattersReport where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TMajorMattersReport.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> getMajo(int taskId) {
		String majorId="";
		List<TMajorMattersReport> list=geTMajorMattersReportsByTaskId(taskId);
		if(list.size()>0){
			for (TMajorMattersReport tMajorMattersReport : list) {
				majorId+=tMajorMattersReport.getFId()+",";
			}
			return getMajor(majorId.substring(0,majorId.length()-1));
		}
		return null;
	}
	/**
	 * 根据重大事项Id返回process
	 * @param majorId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getMajor(String majorId) {
		String sql="select * from View_Wf_Process where F_TYPE_ID=10005 and F_Form_PKID in ("+majorId+")";
		Query query=processDao.CreateNativeSQL(sql, ViewWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据taskId返回check
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProjectCheck> getTProjectChecksByTaskId(int taskId) {
		String sql="select * from T_ProjectCheck where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TProjectCheck.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> getCheckl(int taskId) {
		String checkId="";
		List<TProjectCheck> list=getTProjectChecksByTaskId(taskId);
		if (list.size()>0) {
			for (TProjectCheck tProjectCheck : list) {
				checkId+=tProjectCheck.getFId()+",";
			}
			return getCheck(checkId.substring(0, checkId.length()-1));
		}
		return null;
	}
	/**
	 * 根据检查记录Id返回check
	 * @param checkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getCheck(String checkId) {
		String sql="select * from View_Wf_Process where F_TYPE_ID=10004 and F_Form_PKID in ("+checkId+")";
		Query query=processDao.CreateNativeSQL(sql, ViewWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据taskId返回project
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProjectProcess> getProjectProcessesByTaskId(int taskId) {
		String sql="select * from T_ProjectProcess where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TProjectProcess.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> getProc(int taskId) {
		String proIds="";
		List<TProjectProcess> list=getProjectProcessesByTaskId(taskId);
		if(list.size()>0){
			for (TProjectProcess tProjectProcess : list) {
				proIds+=tProjectProcess.getFId()+",";
			}
			return getPro(proIds.substring(0,proIds.length()-1));
		}
		return null;
	}
	/**
	 * 根据过程记录Id返回process
	 * @param proId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getPro(String proId) {
		String sql="select * from View_Wf_Process where F_TYPE_ID=10003 and F_Form_PKID in("+proId+")";
		Query query=processDao.CreateNativeSQL(sql, ViewWfProcess.class);
		return query.getResultList();
	}
	/**
	 * 根据项目Id返回实施计划
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TImplementPlan> getTImplementPlansByTaskId(int taskId) {
		String sql="select * from T_ImplementPlan where FK_Task_ID=?";
		Query query=implementDao.CreateNativeSQL(sql,TImplementPlan.class);
		query.setParameter(1, taskId);
		return query.getResultList();
	}
	public List<ViewWfProcess> getImpl(int taskId) {
		String impId="";
		List<TImplementPlan> list=getTImplementPlansByTaskId(taskId);
		if(list.size()>0){
			for (TImplementPlan tImplementPlan : list) {
				impId+=tImplementPlan.getFId()+",";
			}
			impId+="-1";
			return getImp(impId);
		}
		return null;
	}
	/**
	 * 根据计划Id返回process
	 * @param impId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getImp(String impId) {
		String sql="select * from View_Wf_Process where F_TYPE_ID=10002 and F_Form_PKID in ("+impId+")";
		Query query=processDao.CreateNativeSQL(sql, ViewWfProcess.class);
		//query.setParameter(1, impId);
		return query.getResultList();
	}
	/**
	 * 返回所有完成的项目
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewWfProcess> getViewWfProcesses(String name) {
		String sql="select * from View_Wf_Process where F_TYPE_ID=10001 and F_STATE=4 and F_Title like ?";
		Query query=processDao.CreateNativeSQL(sql, ViewWfProcess.class);
		query.setParameter(1, "%"+name+"%");
		//query.setFirstResult(start);
		//query.setMaxResults(limit);
		return query.getResultList();
	}
	
	
}
