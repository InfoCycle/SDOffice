package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TProjectChapterEg;
import com.info.domain.TProjectSealsBft;
import com.info.domain.TTask;
import com.info.domain.TWfProccessActive;
import com.info.domain.TWfProcess;
import com.info.domain.ViewProcessProjectSeal;
import com.sun.jmx.snmp.tasks.Task;

@Service
@Transactional
public class BussSealManagementService {
	//Id自动的方法
	@Autowired
	AppSEQHelper seqHelper;
	//刻章申请
	@Autowired
	IBaseDao<TProjectSealsBft> sealBidDao;
	//用章申领登记
	@Autowired
	IBaseDao<TProjectChapterEg> sealRegisterDao;
	//任务列表
	@Autowired
	IBaseDao<TTask> taskDao;
	@Autowired
	IBaseDao<TWfProcess> proceDao;
	@Autowired
	IBaseDao<TWfProccessActive> activeDao;
	@Autowired
	WfProcessUtils utils;
	@Autowired
	IBaseDao<ViewProcessProjectSeal> viewsealDao;
	
	public boolean activeReturn(int activeId,String remark) {
	    return utils.activeReturn(activeId, remark);
	}
	/**
	 * 
	 * @Description	: 设置任务完成
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 20-36
	 * @param activeId
	 * @return
	 */
	public boolean setActiveComplet(int activeId) {
	    return utils.activeComplet(activeId);
	}
	/**
	 * 
	 * @Description	: 提交的方法（添加新的过程）
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 20-34
	 * @param processId
	 * @param userId
	 * @param aboveActId
	 * @param remark
	 * @return
	 */
	public boolean addProcessActiveItem(int processId, int userId, int aboveActId, String remark) {
	    return utils.addProcessActiveItem(processId,userId, aboveActId, remark)>0?true:false;
	}
	/**
	 * @Description	: 添加催办信息
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 20-31
	 * @param activeId
	 * @param remark
	 * @return
	 */
	public boolean activeUrge(int activeId, String remark) {
	    return utils.activeUrge(activeId, remark);
	}
	/**
	 * 
	 * @Description	: 设置接收时间
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 20-27
	 * @param activeId
	 * @return
	 */
	public boolean setActiveAcceptTime(int activeId) {
	    utils.setActiveAcceptTime(activeId);
	    return true;
	}
	/**
	 * 
	 * @Description	: 设置ProsessTitle
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 20-22
	 * @param processId
	 * @param fromPKID
	 * @param title
	 * @return
	 */
	public boolean setProsessTitle(int processId, int fromPKID, String title) {
	   return utils.setProcessTitle(processId, fromPKID, title);
	}
	/////////////////用章申请/////////////
	public boolean uppictures(int FId,byte[] fileByte,boolean type,String fileType) {
		/*String sql="update T_ProjectChapterEg set F_PrintPictures=? where F_Id=?";
		Query query=sealRegisterDao.CreateNativeSQL(sql);
		query.setParameter(1, fileByte);
		query.setParameter(2, FId);
		int q=Integer.parseInt(query.);
		if (q>0) {
			return true;
		} else {
			return false;
		}*/
		TProjectChapterEg updateObj=sealRegisterDao.GetEntity(TProjectChapterEg.class, FId);
		if(type){
			updateObj.setFPrintPictures(fileByte);
			updateObj.setFPicturesType(fileType);
		}else {
			updateObj.setFProcessingImages(fileByte);
			updateObj.setFImageType(fileType);
		}
		return sealRegisterDao.Persist(updateObj);
	}
	/////////////////用章申领登记/////////
	public TWfProccessActive getTWfProccessActive(int activeId) {
	    return activeDao.GetEntity(TWfProccessActive.class, activeId);
	}
	/**
	 * 根据id返回用章登记
	 * @param id
	 * @return
	 */
	public TProjectChapterEg getCHapterById(int id) {
		return sealRegisterDao.GetEntity(TProjectChapterEg.class, id);
	}
	/**
	 * 根据条件返回用章登记
	 * @param sealName
	 * @param people
	 * @param recDate
	 * @param returnDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProjectChapterEg> getChapters(int start,int limit,String sealName,String people,String recDate,String returnDate) {
		try {
			String sql="select * from T_ProjectChapterEg where F_SealName like ? "
					+"and F_RecipientsPeople like ? and F_RecipientsDate like ? and F_ReturnDate like ?";
			Query query=sealRegisterDao.CreateNativeSQL(sql, TProjectChapterEg.class);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			query.setParameter(1, "%"+sealName+"%");
			query.setParameter(2, "%"+people+"%");
			query.setParameter(3, "%"+recDate+"%");
			query.setParameter(4, "%"+returnDate+"%");
			return query.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	/**
	 * 返回条件查询到的数据总数
	 * @param sealName
	 * @param people
	 * @param recDate
	 * @param returnDate
	 * @return
	 */
	public long getCountByChapter(String sealName,String people,String recDate,String returnDate) {
		try {
			String sql="select count(*) from T_ProjectChapterEg where F_SealName like ?"
					+"and F_RecipientsPeople like ? and F_RecipientsDate like ? and F_ReturnDate like ?";
			Query query=sealRegisterDao.CreateNativeSQL(sql);
			query.setParameter(1, "%"+sealName+"%");
			query.setParameter(2, "%"+people+"%");
			query.setParameter(3, "%"+recDate+"%");
			query.setParameter(4, "%"+returnDate+"%");
			return Long.valueOf(query.getSingleResult().toString());
		}catch (Exception e) {
			return 0;
		}
	}
	/**
	 * 删除登记
	 * @param chapter
	 * @return
	 */
	public boolean deleteChapter(TProjectChapterEg chapter) {
		return sealRegisterDao.Delete(chapter);
	}
	/**
	 *  修改登记
	 * @param chapter
	 * @return
	 */
	public boolean updateChapter(TProjectChapterEg chapter) {
		TProjectChapterEg ch=sealRegisterDao.GetEntity(TProjectChapterEg.class, chapter.getFId());
		chapter.setFPrintPictures(ch.getFPrintPictures());
		chapter.setFProcessingImages(ch.getFProcessingImages());
		return sealRegisterDao.Update(chapter);
	}
	/**
	 * 添加登记
	 * @param chapter
	 * @return
	 */
	public boolean addChapter(TProjectChapterEg chapter) {
		chapter.setFId(seqHelper.getCurrentVal("SEQ_CHAPTER"));
		return sealRegisterDao.Persist(chapter);
	}
	/**
	 * 
	 * @Description	: 返回可以领用的项目用章
	 * @Author		: chunlei
	 * @Date		: 2013-03-21 17-05
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewProcessProjectSeal> getProcessProjectSeals() {
	    String sql="select * from View_Process_ProjectSeal";
	    Query query=viewsealDao.CreateNativeSQL(sql,ViewProcessProjectSeal.class);
	    return query.getResultList();
	}
	/////////////////刻章申办////////////
	/**
	 * 返回所有任务
	 * @param start
	 * @param limit
	 * @param taskName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TTask> getTasks(int start,int limit,String taskName) {
		String sql="select * from T_Task where F_Task_Name like ?";
		Query query=taskDao.CreateNativeSQL(sql,TTask.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setParameter(1, "%"+taskName+"%");
		return query.getResultList();
	}
	/**
	 * 返回任务总数
	 * @param start
	 * @param limit
	 * @param taskName
	 * @return
	 */
	public long getTaskCount(int start,int limit,String taskName) {
		String sql="select count(*) from T_Task where F_Task_Name like ?";
		Query query=taskDao.CreateNativeSQL(sql);
		query.setParameter(1, "%"+taskName+"%");
		return Long.valueOf(query.getSingleResult().toString());
	}
	/**
	 * 根据Id返回刻章申报
	 * @param id
	 * @return
	 */
	public TProjectSealsBft geTProjectSealsBftById(int id) {
		return sealBidDao.GetEntity(TProjectSealsBft.class, id);
	}
	/**
	 * 撤销方法（删除process和active）
	 * @param processId
	 * @param activeId
	 * @return
	 */
	public boolean cancelSeal(int processId, int activeId) {
		boolean pb=proceDao.Delete(TWfProcess.class, processId);
		boolean cb=activeDao.Delete(TWfProccessActive.class, activeId);
		return pb&&cb;
	}
	public boolean deleteSeal(int FId) {
		return sealBidDao.Delete(TProjectSealsBft.class, FId);
	}
	/**
	 * 删除申请
	 * @param Seal
	 * @return
	 */
	public boolean deleteSeal(TProjectSealsBft Seal) {
		return sealBidDao.Delete(Seal);
	}
	/**
	 * 更新用章申请
	 * @param seal
	 * @return
	 */
	public TProjectSealsBft updateSeal(TProjectSealsBft seal) {
		if(sealBidDao.Update(seal)){
			return seal;
		}else {
			return null;
		}
	}
	/**
	 * 添加申请
	 * @param seal
	 * @return
	 */
	public TProjectSealsBft addSeal(TProjectSealsBft seal) {
		seal.setFId(seqHelper.getCurrentVal("SEQ_SEAL"));
		if(sealBidDao.Persist(seal)){
			return seal;
		}else {
			return null;
		}
	}
	
}
