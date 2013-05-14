package com.info.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TBussNumberRule;

@Service
@Transactional
public class BussNumberRuleService {
	@Autowired
	IBaseDao<TBussNumberRule> daoBussNumberRule;
	
	@Autowired
	AppSEQHelper SEQHelper;
	
	/**
	 * 
	 * @function:
	 * @data: 2013-1-5下午5:39:09
	 * @author jibinbin
	 * @param code 编码规则Code
	 * @param Title 用户输入标题
	 * @param isHaveSub 是否存在子项
	 * @return
	 *
	 */
	public String getBussNumber(String code,String Title,boolean isHaveSub){
		TBussNumberRule objBNR=new TBussNumberRule();
		String result="";
		try {					
			String SQL = "select a.* from T_Buss_Number_Rule a where a.f_numberrule_code = ?";
			javax.persistence.Query query = daoBussNumberRule.CreateNativeSQL(SQL,
					TBussNumberRule.class);
			query.setParameter(1, code);		
			objBNR =(TBussNumberRule) query.getSingleResult();
			if(objBNR.getFId()>0){			
				result=objBNR.getFItem1()+Title+"-"+ 
						getNumberForInt(SEQHelper.getCurrentVal(objBNR.getFItem2()),4)+
						(isHaveSub ==true ? "-"+getNumberForInt(SEQHelper.getCurrentVal(objBNR.getFItem3()),2) : "")+
						"-"+GetCurrentTime().substring(0,4);
			}else {
				result="-1"; //生成失败
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
 
	/**
	 * 获得编号
	 * @param year
	 * @param taskname 编号的前几位: 如：编号为KMSD-昆供电办-修记任务-任务-01-2013只取“KMSD-昆供电办-修记任务”去除“-任务-01-2013”
	 * @param numbercode
	 * @return
	 */
	public String getBussNumberForFun(String year,String taskname,String numbercode,Integer fid){
		String result="";
		//select dbo.FN_GetNumber('2012','KMSD——昆供电办——通泰新城锦绣园工程','RW')
		try {	
			String SQL = "select dbo.FN_GetNumber(?,?,?,?) as number ";
			javax.persistence.Query query = daoBussNumberRule.CreateNativeSQL(SQL);//daoBussNumberRule.CreateNativeSQL(SQL,TBussNumberRule.class);
			query.setParameter(1, year);					
			query.setParameter(2, taskname);
			query.setParameter(3, numbercode);
			query.setParameter(4, fid);
			result=(String)query.getSingleResult();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @function:
	 * @data:2013-1-5下午5:36:28
	 * @author jibinbin
	 * @param numberInteger 需要补位的整数
	 * @param bitInteger	补位后返回的字符串长的
	 * @return 返回补位后的字符串
	 *
	 */
	private String getNumberForInt(Integer numberInteger,Integer bitInteger){
		String result="";
		for(int i=0;i<bitInteger-numberInteger.toString().length();i++)
			result+="0";
		return result+numberInteger.toString();
	}	
	/**
	 * 
	 * @function: 获得系统当期时间
	 * @data: 2013-1-5下午5:38:36
	 * @author jibinbin
	 * @return
	 *
	 */
	private String GetCurrentTime(){
		Date currentTime=new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  formatter.format(currentTime);
	}
	
}
