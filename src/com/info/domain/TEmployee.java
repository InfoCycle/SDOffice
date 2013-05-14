package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TEmployee entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Employee", schema = "dbo", catalog = "SDOffice")
public class TEmployee implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FNumbers;
	private Integer FEmployeeType;
	private String FBelongsDeptId;
	private String FBelongsDept;
	private String FStaffState;
	private String FStaffName;
	private String FSex;
	private String FBirthDay;
	private String FNational;
	private String FNativePlace;
	private String FCultureDegree;
	private String FMajor;
	private String FPolitical;
	private String FToWorkTime;
	private String FProfessional;
	private String FTechnical;
	private String FMaritalStatus;
	private String FIntoCompanyTime;
	private String FTheLaborContractTime;
	private String FLaborContractExpiresTime;
	private String FContractRs;
	private String FLaborIs;
	private String FIdcardNumber;
	private String FContact;
	private String FFamilyDetailedAddress;
	private String FNowRpr;

	// Constructors

	/** default constructor */
	public TEmployee() {
	}

	/** minimal constructor */
	public TEmployee(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TEmployee(Integer FId, String FNumbers, Integer FEmployeeType,
			String FBelongsDeptId, String FBelongsDept, String FStaffState,
			String FStaffName, String FSex, String FBirthDay, String FNational,
			String FNativePlace, String FCultureDegree, String FMajor,
			String FPolitical, String FToWorkTime, String FProfessional,
			String FTechnical, String FMaritalStatus, String FIntoCompanyTime,
			String FTheLaborContractTime, String FLaborContractExpiresTime,
			String FContractRs, String FLaborIs, String FIdcardNumber,
			String FContact, String FFamilyDetailedAddress, String FNowRpr) {
		this.FId = FId;
		this.FNumbers = FNumbers;
		this.FEmployeeType = FEmployeeType;
		this.FBelongsDeptId = FBelongsDeptId;
		this.FBelongsDept = FBelongsDept;
		this.FStaffState = FStaffState;
		this.FStaffName = FStaffName;
		this.FSex = FSex;
		this.FBirthDay = FBirthDay;
		this.FNational = FNational;
		this.FNativePlace = FNativePlace;
		this.FCultureDegree = FCultureDegree;
		this.FMajor = FMajor;
		this.FPolitical = FPolitical;
		this.FToWorkTime = FToWorkTime;
		this.FProfessional = FProfessional;
		this.FTechnical = FTechnical;
		this.FMaritalStatus = FMaritalStatus;
		this.FIntoCompanyTime = FIntoCompanyTime;
		this.FTheLaborContractTime = FTheLaborContractTime;
		this.FLaborContractExpiresTime = FLaborContractExpiresTime;
		this.FContractRs = FContractRs;
		this.FLaborIs = FLaborIs;
		this.FIdcardNumber = FIdcardNumber;
		this.FContact = FContact;
		this.FFamilyDetailedAddress = FFamilyDetailedAddress;
		this.FNowRpr = FNowRpr;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", unique = true, nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Numbers", length = 30)
	public String getFNumbers() {
		return this.FNumbers;
	}

	public void setFNumbers(String FNumbers) {
		this.FNumbers = FNumbers;
	}

	@Column(name = "F_EmployeeType")
	public Integer getFEmployeeType() {
		return this.FEmployeeType;
	}

	public void setFEmployeeType(Integer FEmployeeType) {
		this.FEmployeeType = FEmployeeType;
	}

	@Column(name = "F_BelongsDept_ID", length = 10)
	public String getFBelongsDeptId() {
		return this.FBelongsDeptId;
	}

	public void setFBelongsDeptId(String FBelongsDeptId) {
		this.FBelongsDeptId = FBelongsDeptId;
	}

	@Column(name = "F_BelongsDept", length = 30)
	public String getFBelongsDept() {
		return this.FBelongsDept;
	}

	public void setFBelongsDept(String FBelongsDept) {
		this.FBelongsDept = FBelongsDept;
	}

	@Column(name = "F_StaffState", length = 100)
	public String getFStaffState() {
		return this.FStaffState;
	}

	public void setFStaffState(String FStaffState) {
		this.FStaffState = FStaffState;
	}

	@Column(name = "F_StaffName", length = 30)
	public String getFStaffName() {
		return this.FStaffName;
	}

	public void setFStaffName(String FStaffName) {
		this.FStaffName = FStaffName;
	}

	@Column(name = "F_Sex", length = 2)
	public String getFSex() {
		return this.FSex;
	}

	public void setFSex(String FSex) {
		this.FSex = FSex;
	}

	@Column(name = "F_BirthDay", length = 8)
	public String getFBirthDay() {
		return this.FBirthDay;
	}

	public void setFBirthDay(String FBirthDay) {
		this.FBirthDay = FBirthDay;
	}

	@Column(name = "F_National", length = 100)
	public String getFNational() {
		return this.FNational;
	}

	public void setFNational(String FNational) {
		this.FNational = FNational;
	}

	@Column(name = "F_NativePlace", length = 100)
	public String getFNativePlace() {
		return this.FNativePlace;
	}

	public void setFNativePlace(String FNativePlace) {
		this.FNativePlace = FNativePlace;
	}

	@Column(name = "F_CultureDegree", length = 100)
	public String getFCultureDegree() {
		return this.FCultureDegree;
	}

	public void setFCultureDegree(String FCultureDegree) {
		this.FCultureDegree = FCultureDegree;
	}

	@Column(name = "F_Major", length = 100)
	public String getFMajor() {
		return this.FMajor;
	}

	public void setFMajor(String FMajor) {
		this.FMajor = FMajor;
	}

	@Column(name = "F_Political", length = 100)
	public String getFPolitical() {
		return this.FPolitical;
	}

	public void setFPolitical(String FPolitical) {
		this.FPolitical = FPolitical;
	}

	@Column(name = "F_ToWorkTime", length = 100)
	public String getFToWorkTime() {
		return this.FToWorkTime;
	}

	public void setFToWorkTime(String FToWorkTime) {
		this.FToWorkTime = FToWorkTime;
	}

	@Column(name = "F_Professional", length = 100)
	public String getFProfessional() {
		return this.FProfessional;
	}

	public void setFProfessional(String FProfessional) {
		this.FProfessional = FProfessional;
	}

	@Column(name = "F_Technical", length = 100)
	public String getFTechnical() {
		return this.FTechnical;
	}

	public void setFTechnical(String FTechnical) {
		this.FTechnical = FTechnical;
	}

	@Column(name = "F_MaritalStatus", length = 30)
	public String getFMaritalStatus() {
		return this.FMaritalStatus;
	}

	public void setFMaritalStatus(String FMaritalStatus) {
		this.FMaritalStatus = FMaritalStatus;
	}

	@Column(name = "F_IntoCompanyTime", length = 30)
	public String getFIntoCompanyTime() {
		return this.FIntoCompanyTime;
	}

	public void setFIntoCompanyTime(String FIntoCompanyTime) {
		this.FIntoCompanyTime = FIntoCompanyTime;
	}

	@Column(name = "F_TheLaborContractTime", length = 30)
	public String getFTheLaborContractTime() {
		return this.FTheLaborContractTime;
	}

	public void setFTheLaborContractTime(String FTheLaborContractTime) {
		this.FTheLaborContractTime = FTheLaborContractTime;
	}

	@Column(name = "F_LaborContractExpiresTime", length = 30)
	public String getFLaborContractExpiresTime() {
		return this.FLaborContractExpiresTime;
	}

	public void setFLaborContractExpiresTime(String FLaborContractExpiresTime) {
		this.FLaborContractExpiresTime = FLaborContractExpiresTime;
	}

	@Column(name = "F_ContractRS", length = 300)
	public String getFContractRs() {
		return this.FContractRs;
	}

	public void setFContractRs(String FContractRs) {
		this.FContractRs = FContractRs;
	}

	@Column(name = "F_LaborIS", length = 300)
	public String getFLaborIs() {
		return this.FLaborIs;
	}

	public void setFLaborIs(String FLaborIs) {
		this.FLaborIs = FLaborIs;
	}

	@Column(name = "F_IDCardNumber", length = 30)
	public String getFIdcardNumber() {
		return this.FIdcardNumber;
	}

	public void setFIdcardNumber(String FIdcardNumber) {
		this.FIdcardNumber = FIdcardNumber;
	}

	@Column(name = "F_Contact", length = 100)
	public String getFContact() {
		return this.FContact;
	}

	public void setFContact(String FContact) {
		this.FContact = FContact;
	}

	@Column(name = "F_FamilyDetailedAddress", length = 300)
	public String getFFamilyDetailedAddress() {
		return this.FFamilyDetailedAddress;
	}

	public void setFFamilyDetailedAddress(String FFamilyDetailedAddress) {
		this.FFamilyDetailedAddress = FFamilyDetailedAddress;
	}

	@Column(name = "F_NowRPR", length = 100)
	public String getFNowRpr() {
		return this.FNowRpr;
	}

	public void setFNowRpr(String FNowRpr) {
		this.FNowRpr = FNowRpr;
	}

}