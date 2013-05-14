package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAccessories;
import com.info.domain.TProjectResultsFile;

@Service
@Transactional
public class BussResultsFileService {
	@Autowired
	IBaseDao<TProjectResultsFile> ProjectResultsFileDao;
	
	@Autowired
	IBaseDao<TAccessories> accessoriesDao;
	
	@Autowired
	AppSEQHelper SEQHelper;
	
	
	@SuppressWarnings("unchecked")
	public List<TProjectResultsFile> GetProjectResultsFileList(Integer fkCheckReviewId,String order,Integer page,Integer rows){
		String SQL = "select a.* from T_Project_ResultsFile a where a.FK_CheckReview_ID= ? order by F_ResultsFile_Type "+order;
		javax.persistence.Query query = ProjectResultsFileDao.CreateNativeSQL(SQL,
				TProjectResultsFile.class);
		query.setParameter(1, fkCheckReviewId);
		return (List<TProjectResultsFile>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
    public List<TProjectResultsFile> GetProjectResultsFileListForTaskId(Integer taskid,String order,Integer page,Integer rows){
        String SQL = "select a.* from T_Project_ResultsFile a where a.FK_CheckReview_ID= (select top 1 f_id from dbo.T_CheckReview where FK_Task_ID=?) order by F_ResultsFile_Type "+order;
        javax.persistence.Query query = ProjectResultsFileDao.CreateNativeSQL(SQL,
                TProjectResultsFile.class);
        query.setParameter(1, taskid);
        return (List<TProjectResultsFile>) query.getResultList();
    }
	/**
	 * insert obj
	 * @function:
	 * @data: 2013-1-31下午5:19:38
	 * @author jibinbin
	 * @param obj
	 * @return
	 *
	 */
	
	public boolean Insert(TProjectResultsFile obj,byte[] filebyte){
		boolean flag=false;		
		try {
			
			Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_RESULTSFILE");			
			obj.setFId(fIdInteger);	
			flag=ProjectResultsFileDao.Persist(obj);
			//插入附件
			Integer afIdInteger= SEQHelper.getCurrentVal("SEQ_ACCESSORIES");
			TAccessories AccessoriesObj=new TAccessories();
			AccessoriesObj.setFId(afIdInteger);
			AccessoriesObj.setFkId(fIdInteger);
			AccessoriesObj.setFAccessories(filebyte);
			flag=accessoriesDao.Persist(AccessoriesObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * delete obj
	 * @function:
	 * @data: 2013-1-31下午5:19:38
	 * @author jibinbin
	 * @param obj
	 * @return
	 *
	 */
	public boolean DeleteProjectResultsFile(Integer id){
		boolean flag=false;		 
		flag=ProjectResultsFileDao.Delete(TProjectResultsFile.class, id);				
		return flag;
	}
	/**
	 * 
	 * @Description	: 删除附件
	 * @Author		: jibb
	 * @Date		: 2013-03-26 16-58
	 * @param id
	 * @return
	 */
	public boolean DeleteAccessories(Integer id){
	    boolean flag=false;      
        flag=accessoriesDao.Delete(TAccessories.class, id);               
        return flag; 
	}
	/**
	 * get Accessories
	 * @function:
	 * @data: 2013-2-2下午9:30:25
	 * @author jibinbin
	 * @param fkid
	 * @return
	 *
	 */
	public TAccessories getAccessories(Integer fkid){
		TAccessories objAccessories=new TAccessories();
		String SQL = "select a.* from T_Accessories a where a.fk_Id= ? ";
		javax.persistence.Query query = accessoriesDao.CreateNativeSQL(SQL,
				TAccessories.class);
		query.setParameter(1, fkid);
		objAccessories = (TAccessories)query.getSingleResult();
		return objAccessories;
	}
	
	/**
	 * 
	 * @function:
	 * @data: 2013-2-2下午9:52:13
	 * @author jibinbin
	 * @param id
	 * @return
	 *
	 */
	public TProjectResultsFile getProjectResultsFile(Integer id){
		return ProjectResultsFileDao.GetEntity(TProjectResultsFile.class, id);
	}
	
}
