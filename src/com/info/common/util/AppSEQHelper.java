package com.info.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.AppSEQDao;
import com.info.domain.TAppSeq;
/**
 * 获取实体自增序列号
 * @author by liwx at 2012-03-10
 */
@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class AppSEQHelper {	
	private String defaultSeqName = "SEQ_Application";
	@Autowired
	AppSEQDao seqDAO;
	/**
	 * 获取序列名称默认为SEQ_Application的自增号
	 */
	public Integer getCurrentVal() {
		return getCurrentVal(defaultSeqName);
	}

	/**
	 * 获取序列号
	 * 
	 * @param SeqName 序列名称
	 * 
	 * @return 成功返回序号值，失败返回0;
	 */	
	
	public Integer getCurrentVal(String SeqName) {
		Integer result = 0;
		try {
			TAppSeq obj = seqDAO.getBy(SeqName);
			if (obj == null) {
				TAppSeq newobj = new TAppSeq(getCurrentVal(), SeqName, 1);
				seqDAO.persist(newobj);
				result = 1;
			} else {
				result = obj.getFCurrentval().intValue()+1;
				obj.setFCurrentval(result);				
				seqDAO.update(obj);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}