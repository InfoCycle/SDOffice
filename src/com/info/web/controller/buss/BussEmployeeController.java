package com.info.web.controller.buss;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.domain.TAppCode;
import com.info.domain.TAppOrg;
import com.info.domain.TEmployee;
import com.info.domain.TFamilyMembers;
import com.info.domain.TProfessionalQualification;
import com.info.service.BussEmployeeService;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Scope("prototype")
@Controller
public class BussEmployeeController extends BaseController{
	ResultMessage message;
	EasyDataGrid dataGrid;
	@Autowired
	BussEmployeeService employeeService;
	
	String[] ways={"select * from T_Employee where F_BelongsDept like ?",
			"select * from T_Employee where F_StaffName like ?",
			"select * from T_Employee where F_StaffState like ?",
			"select * from T_Employee where F_IDCardNumber like ?",
			"select * from T_Employee where F_EmployeeType like ?"};
	@RequestMapping(value="/buss/employee/{action}")
	public void employeecl(@PathVariable("action")Integer action,HttpServletResponse response) {
		CurrentResponse = response;
		message=new ResultMessage();
		dataGrid=new EasyDataGrid();
		String result="";
		switch (action) {
				case 0:
					if(getString("Iterm", "").equals("")){
						result=getAll(getInt("limit", 0),getInt("start", 0));
					}else {
						result=getByWay(ways[getInt("Way", 0)],getString("Iterm", ""),getInt("start", 0),getInt("limit", 0));
					}
					break;
				case 1:
					result=add(getEmployee());
					break;
				case 2:
					result=addFamily(getFamily());
					break;
				case 3:
					result=addProfessional(getProfessional());
					break;
				case 4:
					result=update(getEmployee());
					break;
				case 5:
					result=updateFamily(getFamily());
					break;
				case 6:
					result=updateProfessional(getProfessional());
					break;
				case 7:
					result=delete(getEmployee());
					break;
				case 8:
					result=getEmployeeBy(getInt("FId", -1));
					break;
				case 9:
					result=getFamily(getInt("FId", -1));
					break;
				case 10:
					result=getProfessional(getInt("FId", -1));
					break;
				case 11:
					result=deleteFamily(getFamily());
					break;
				case 12:
					result=deleteProfessional(getProfessional());
					break;
				case 13:
				    result=getCode(getInt("codeId", -1));
				    break;
				case 14:
				    result=getORG();
				    break;
		}
		//System.out.println(result);
		writeJsonString(result);
	}
	/**
	 * 
	 * @Description	: 返回部门
	 * @Author		: chunlei
	 * @Date		: 2013-04-02 17-37
	 * @return
	 */
	private String getORG() {
	    List<TAppOrg> list = employeeService.getAppOrgs();
	    String root="[";
	    for (TAppOrg tAppOrg : list) {
		root+="{\"id\":\""+tAppOrg.getFId()+"\",\"text\":\""+tAppOrg.getFName()+"\"},";
	    }
	    root+="{\"id\":-1,\"text\":\"请选择\",\"selected\":true}]";
	    return root;
	}
	/**
	 * 
	 * @Description	: 返回代码，根据codeId
	 * @Author		: chunlei
	 * @Date		: 2013-04-02 16-45
	 * @return
	 */
	private String getCode(int codeId) {
	    List<TAppCode> list = employeeService.getAppCodesByTypeId(codeId);
	    String root="[";
	    for (TAppCode tAppCode : list) {
		root+="{\"id\":\""+tAppCode.getFId()+"\",\"text\":\""+tAppCode.getFCodeText()+"\"},";
	    }
	    //root=root.substring(0,root.length()-1);
	    root+="{\"id\":-1,\"text\":\"请选择\",\"selected\":true}]";
	    return root;
	}



	private String deleteProfessional(TProfessionalQualification professional) {
		if(employeeService.deleteProfessional(professional)){
			message.setMessage("删除成功!");
			message.setSuccess(true);
		}else {
			message.setMessage("删除失败!");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}



	private String deleteFamily(TFamilyMembers family) {
		if(employeeService.deleteFamily(family)){
			message.setMessage("删除成功!");
			message.setSuccess(true);
		}else {
			message.setMessage("删除失败!");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private String getEmployeeBy(int id) {
		message.setRoot(employeeService.getEmployeeBy(id));
		message.setSuccess(true);
		return getJsonFromObj(message);
	}
	/**
	 * 
	 * @Description	: 返回家庭成员
	 * @Author		: chunlei
	 * @Date		: 2013-04-07 17-35
	 * @param id
	 * @return
	 */
	private String getFamily(int id) {
		//message.setRoot(employeeService.getFamilyBy(id));
		dataGrid.setRows(employeeService.getFamilyBy(id));
		//message.setSuccess(true);
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 
	 * @Description	: 返回专业资格取得情况
	 * @Author		: chunlei
	 * @Date		: 2013-04-07 17-35
	 * @param id
	 * @return
	 */
	private String getProfessional(int id) {
		//message.setRoot(employeeService.getProfessionalBy(id));
		//message.setSuccess(true);
	    	dataGrid.setRows(employeeService.getProfessionalBy(id));
		return getJsonFromObj(dataGrid);
	}

	private String updateProfessional(TProfessionalQualification professional) {
		if(employeeService.upataProfessional(professional)){
			message.setMessage("修改成功!");
			message.setSuccess(true);
		}else{
			message.setMessage("修改失败!");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private String addProfessional(TProfessionalQualification professional) {
		if(employeeService.addProfessio(getProfessional())){
			message.setMessage("添加成功!");
			message.setSuccess(true);
		}else {
			message.setMessage("添加失败!");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private TProfessionalQualification getProfessional() {
		TProfessionalQualification professional=new TProfessionalQualification();
		professional.setFGetTime(getString("pFGetTime", ""));
		professional.setFId(getInt("pFId", -1));
		professional.setFkEmployeeId(getInt("pFkEmployeeId", -1));
		professional.setFNumber(getString("pFNumber", ""));
		professional.setFQualificationName(getString("pFQualificationName", ""));
		professional.setFRegisteredUnit(getString("pFRegisteredUnit", ""));
		professional.setFRenewalEduSituation(getString("pFRenewalEduSituation", ""));
		professional.setFValidity(getString("pFValidity", ""));
		return professional;
	}
	private String addFamily(TFamilyMembers family) {
		if(employeeService.addFamily(family)){
			message.setMessage("添加成功!");
			message.setSuccess(true);
		}else {
			message.setMessage("添加失败!");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private String updateFamily(TFamilyMembers family) {
		if(employeeService.updataFamily(family)){
			message.setSuccess(true);
			message.setMessage("修改成功!");
		}else {
			message.setSuccess(false);
			message.setMessage("修改失败!");
		}
		return getJsonFromObj(message);
	}
	private TFamilyMembers getFamily() {
		TFamilyMembers familyMembers=new TFamilyMembers();
		familyMembers.setFId(getInt("fFId", -1));
		familyMembers.setFAndIrelationship(getString("fFAndIrelationship", ""));
		familyMembers.setFkEmployeeId(getInt("fFkEmployeeId", -1));
		familyMembers.setFMainMembersName(getString("fFMainMembersName", ""));
		familyMembers.setFPhone(getString("fFPhone", ""));
		return familyMembers;
	}
	private String getByWay(String way, String item,int start,int limit) {
		List<TEmployee> list=employeeService.getByItem(way, item,start,limit);
		message.setRoot(list);
		message.setSuccess(true);
		message.setTotalProperty(list.size());
		return getJsonFromObj(message);
	}

	private String update(TEmployee employee) {
		if(employeeService.update(employee)){
			message.setMessage("修改成功！");
			message.setSuccess(true);
		}else {
			message.setMessage("修改失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	private String delete(TEmployee employee) {
		if(employeeService.delete(employee)){
			message.setSuccess(true);
			message.setMessage("删除成功！");
		}else {
			message.setSuccess(false);
			message.setMessage("删除失败！");
		}
		return getJsonFromObj(message);
	}
	private String add(TEmployee employee) {
			
			message.setRoot(employeeService.add(employee));
			message.setMessage("添加完成！");
			message.setSuccess(true);
			return getJsonFromObj(message);
	}
	private TEmployee getEmployee() {
		TEmployee employee=new TEmployee();
		employee.setFBelongsDept(getString("FBelongsDept", ""));
		employee.setFBelongsDeptId(getString("FBelongsDeptId", ""));
		employee.setFBirthDay(getString("FBirthDay", ""));
		employee.setFContact(getString("FContact", ""));
		employee.setFContractRs(getString("FContractRs", ""));
		employee.setFCultureDegree(getString("FCultureDegree", ""));
		employee.setFFamilyDetailedAddress(getString("FFamilyDetailedAddress", ""));
		employee.setFEmployeeType(getInt("FEmployeeType", 0));
		employee.setFId(getInt("FId", -1));
		employee.setFIdcardNumber(getString("FIdcardNumber", ""));
		employee.setFIntoCompanyTime(getString("FIntoCompanyTime", ""));
		employee.setFTheLaborContractTime(getString("FTheLaborContractTime", ""));
		employee.setFLaborContractExpiresTime(getString("FLaborContractExpiresTime", ""));
		employee.setFLaborIs(getString("FLaborIs", ""));
		employee.setFMajor(getString("FMajor", ""));
		employee.setFMaritalStatus(getString("FMaritalStatus", ""));
		employee.setFNational(getString("FNational", ""));
		employee.setFNativePlace(getString("FNativePlace", ""));
		employee.setFNowRpr(getString("FNowRpr", ""));
		employee.setFNumbers(getString("FNumbers", ""));
		employee.setFPolitical(getString("FPolitical", ""));
		employee.setFProfessional(getString("FProfessional", ""));
		employee.setFSex(getString("FSex", ""));
		employee.setFStaffName(getString("FStaffName", ""));
		employee.setFStaffState(getString("FStaffState", ""));
		employee.setFTechnical(getString("FTechnical", ""));
		employee.setFToWorkTime(getString("FToWorkTime", ""));
		return employee;
	}
	private String getAll(int limit,int start) {
		List<TEmployee> list=employeeService.getAll(limit,start);
		message.setSuccess(true);
		message.setRoot(list);
		message.setTotalProperty(employeeService.getcon());
		return getJsonFromObj(message);
	}
}
