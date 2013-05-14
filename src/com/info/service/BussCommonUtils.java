package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class BussCommonUtils {

	@Autowired
	BussCommonService commService;
	
	/**
	 * 获得任务名称通过系统ID
	 * @param id
	 * @return
	 */
	public String getTaskName(Integer id){
		return commService.getTaskName(id);
	}
	
	/**
	 * 获得FormPKID 从TWFProcess表中
	 * @param id
	 * @return
	 */
	public Integer getFormPKIDForProcess(Integer id){
		return commService.getFormPKIDForWFProcess(id);
	}
	
	/**
	 * 通过岗位查询用户
	 * @param uid
	 * @return
	 */
	public List<String> getAppUserForStation(Integer uid){
		return commService.getAppUserForStation(uid);
	}
}
