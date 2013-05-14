package com.info.service;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAppCode;
import com.info.domain.TAppOrg;
import com.info.domain.TEmployee;
import com.info.domain.TFamilyMembers;
import com.info.domain.TProfessionalQualification;

@Service
@Transactional
public class BussEmployeeService {
	@Autowired
	IBaseDao<TEmployee> employeeDao;
	@Autowired
	IBaseDao<TFamilyMembers> familyMembersDao;
	@Autowired
	IBaseDao<TProfessionalQualification> professionalQualificationDao;
	@Autowired
	IBaseDao<TAppCode> codeDao;
	@Autowired
	IBaseDao<TAppOrg> orgDao;
	@Autowired
	AppSEQHelper seqHelper;
	
	/**
	 * 
	 * @Description	: 返回部门选项
	 * @Author		: chunlei
	 * @Date		: 2013-04-02 18-15
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TAppOrg> getAppOrgs() {
	    String sql="select * from T_App_org where f_state=1 order by F_SORT";
	    Query query=orgDao.CreateNativeSQL(sql,TAppOrg.class);
	    return query.getResultList();
	}
	/**
	 * 
	 * @Description	: 根据类型Id返回code的值
	 * @Author		: chunlei
	 * @Date		: 2013-04-02 16-51
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TAppCode> getAppCodesByTypeId(int typeId) {
	    String sql="select * from T_App_Code where FK_CODE_TYPE_ID=?";
	    Query query=codeDao.CreateNativeSQL(sql,TAppCode.class);
	    query.setParameter(1, typeId);
	    return ( List<TAppCode> )query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TEmployee> getAll(int limit,int start) {
		String sql="select * from T_Employee";
		Query query=employeeDao.CreateNativeSQL(sql,TEmployee.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	public int getcon() {
		String sql="select count(*) from T_Employee";
		javax.persistence.Query query = employeeDao.CreateNativeSQL(sql);
		int totalCount = Integer.parseInt(query.getSingleResult().toString());
		return totalCount;

	}
	public TEmployee add(TEmployee employee) {
		int id=seqHelper.getCurrentVal("SEQ_EMPLOYEE");
		String sid=id+"";
		employee.setFId(id);
		String number="00000";
		employee.setFNumbers(number.substring(0,5-sid.length())+id);
		employeeDao.Persist(employee);
		return employee;
	}
	public boolean addFamily(TFamilyMembers familyMembers) {
		familyMembers.setFId(seqHelper.getCurrentVal("SEQ_FAMILY"));
		return familyMembersDao.Persist(familyMembers);
	}
	public boolean addProfessio(TProfessionalQualification professionalQualification) {
		professionalQualification.setFId(seqHelper.getCurrentVal("SEQ_PROFESSIONAL"));
		return professionalQualificationDao.Persist(professionalQualification);
	}
	public boolean delete(TEmployee employee) {
		deleteFamilys(employee.getFId());
		deleteProfessionals(employee.getFId());
		return employeeDao.Delete(employee);
	}
	public boolean deleteFamilys(int id) {
		String sql="delete T_FamilyMembers where FK_Employee_ID="+id;
		familyMembersDao.ExecuteSQL(sql);
		return true;
	}
	public boolean deleteProfessionals(int id) {
		String sql="delete T_ProfessionalQualification where FK_Employee_ID="+id;
		professionalQualificationDao.ExecuteSQL(sql);
		return true;
	}
	public  boolean deleteFamily(TFamilyMembers family) {
		return familyMembersDao.Delete(family);
	}
	public boolean deleteProfessional(TProfessionalQualification professional) {
		return professionalQualificationDao.Delete(professional);
	}
	public boolean update(TEmployee employee) {
		return employeeDao.Update(employee);
	}
	public boolean upataProfessional(TProfessionalQualification professionalQualification) {
		return professionalQualificationDao.Update(professionalQualification);
	}
	public boolean updataFamily(TFamilyMembers familyMembers) {
		return familyMembersDao.Update(familyMembers);
	}
	@SuppressWarnings("unchecked")
	public List<TEmployee> getByItem(String way,String item,int start,int limit) {
		Query query=employeeDao.CreateNativeSQL(way,TEmployee.class);
		query.setParameter(1, "%"+item+"%");
		return (List<TEmployee>)query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getAllId() {
		String sql="select F_Id from T_Employee";
		Query query=employeeDao.CreateNativeSQL(sql,TEmployee.class);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getFamilyId() {
		String sql="select F_Id from T_FamilyMembers";
		Query query=familyMembersDao.CreateNativeSQL(sql);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getProjectId() {
		String sql="select F_Id from T_ProfessionalQualification";
		Query query=familyMembersDao.CreateNativeSQL(sql);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<TFamilyMembers> getFamilyBy(int id) {
		String sql="select * from T_FamilyMembers where FK_Employee_ID=?";
		List<TFamilyMembers> list=null;
		try {
			Query query=familyMembersDao.CreateNativeSQL(sql,TFamilyMembers.class);
			query.setParameter(1, id);
			list=query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TProfessionalQualification> getProfessionalBy(int id){
		String sql="select * from T_ProfessionalQualification where FK_Employee_ID=?";
		List<TProfessionalQualification> list=null;
		try {
			Query query=familyMembersDao.CreateNativeSQL(sql,TProfessionalQualification.class);
			query.setParameter(1, id);
			list=query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TEmployee> getEmployeeBy(int id) {
		String sql="select * from T_Employee where F_Id=?";
		List<TEmployee> list=null;
		try {
			Query query=employeeDao.CreateNativeSQL(sql,TEmployee.class);
			query.setParameter(1, id);
			list=query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}
}
