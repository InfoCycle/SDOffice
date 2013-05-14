package com.info.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TFiles;

@Service
@Transactional
public class BussFileService {
	@Autowired
	IBaseDao<TFiles> dao;
	
	@Autowired
	AppSEQHelper seqHelper;
	
	public TFiles saveFiles(TFiles entity){
		TFiles entitys=new TFiles();
		if(entity.getFId()!=null && entity.getFId()>0){
			if(editFile(entity)){
				entitys=entity;
			}
		}else{
		    if(insertFile(entity)){
				entitys=entity;
			}
		}
		return entitys;
	}
	
	private boolean insertFile(TFiles entity){
		boolean result=true;
		if(entity!=null){
			int FId=seqHelper.getCurrentVal(TFiles.class.getSimpleName());
			entity.setFId(FId);
			result= dao.Persist(entity);
		}else{
			result=false;	
		}
		
		return result;
	}
	
	private boolean editFile(TFiles entity){
		boolean result=true;
		if(entity!=null){
			TFiles entityFiles=dao.GetEntity(TFiles.class, entity.getFId());
			entityFiles=entity;
			result= dao.Update(entityFiles);
		}else{
			result=false;	
		}
		
		return result;
	}
	
	public List<TFiles> getFiles(int FTypeId,String FType){
		String command="select o from TFiles o where o.FType='"+FType+"'  and o.FTypeId in("+FTypeId+")";
		List<TFiles> list=dao.Query(command);
		return list;
	}
	
	public boolean isFiles(int FTypeId,String FType){
		String command="select o from TFiles o where o.FType='"+FType+"'  and o.FTypeId in("+FTypeId+")";
		List<TFiles> list=dao.Query(command);
		return list.size()>0;
	}
	
	public boolean deleteFile(int FId){
		return dao.Delete(TFiles.class,FId);
	}
}
