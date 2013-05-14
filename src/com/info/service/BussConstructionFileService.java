package com.info.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aj.org.objectweb.asm.Type;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TConstructionAccessories;
import com.info.domain.TConstructionFile;

@Service
@Transactional
public class BussConstructionFileService {

	@Autowired
	IBaseDao<TConstructionFile> constructionFileDao;
	
	@Autowired
	IBaseDao<TConstructionAccessories> accessoriesDao;
	
	@Autowired
	AppSEQHelper SEQHelper;
	
	@SuppressWarnings("unchecked")
	public List<TConstructionFile> GetProjectResultsFileList(Integer fkId,Integer page,Integer rows){
		String SQL = "select a.* from T_ConstructionFile a where a.FK_ID= ? order by F_ID ";
		javax.persistence.Query query = constructionFileDao.CreateNativeSQL(SQL,
				TConstructionFile.class);
		query.setParameter(1, fkId);
		return (List<TConstructionFile>) query.getResultList();
	}
	
	public boolean Insert(TConstructionFile obj,byte[] filebyte){
		boolean flag=false;		
		try {
			
			Integer fIdInteger = SEQHelper.getCurrentVal("SEQ_CONSTRUCTIONFILE");			
			obj.setFId(fIdInteger);	
			flag=constructionFileDao.Persist(obj);
			//插入附件
			Integer afIdInteger= SEQHelper.getCurrentVal("SEQ_CONSTRUCTIONACCESSORIES");
			TConstructionAccessories AccessoriesObj=new TConstructionAccessories();
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
	 * 
	 * @Description	: 视频文件用的添加事件
	 * @Author		: chunlei
	 * @Date		: 2013-03-22 11-32
	 * @param obj 文件信息
	 * @return
	 */
	public boolean intsert(TConstructionFile obj) {
	    Integer fIdInteger=SEQHelper.getCurrentVal("SEQ_CONSTRUCTIONFILE");
	    obj.setFId(fIdInteger);
	    return constructionFileDao.Persist(obj);
	}
	
	public boolean Delete(Integer id){
		boolean flag=false;
		try {
		    TConstructionFile filed=constructionFileDao.GetEntity(TConstructionFile.class, id);
		    if(filed.getFkId()==13){
			flag=constructionFileDao.Delete(filed);
			return flag;
		    }
		    //flag=constructionFileDao.Delete(TConstructionFile.class, id);
		    TConstructionAccessories accessories = accessoriesDao.GetBy(TConstructionAccessories.class, "fkId", id);
		    flag=constructionFileDao.Delete(TConstructionFile.class, id) && accessoriesDao.Delete(accessories);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return flag;
	}
	
	public TConstructionAccessories getAccessories(Integer fkid){
		TConstructionAccessories objAccessories=new TConstructionAccessories();
		String SQL = "select a.* from T_ConstructionAccessories a where a.fk_Id= ? ";
		javax.persistence.Query query = accessoriesDao.CreateNativeSQL(SQL,
				TConstructionAccessories.class);
		query.setParameter(1, fkid);
		objAccessories = (TConstructionAccessories)query.getSingleResult();
		return objAccessories;
	}
	
	public TConstructionFile getProjectResultsFile(Integer id){
		return constructionFileDao.GetEntity(TConstructionFile.class, id);
	}
}
