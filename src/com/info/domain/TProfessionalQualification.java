package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProfessionalQualification entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ProfessionalQualification", schema = "dbo", catalog = "SDOffice")
public class TProfessionalQualification implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkEmployeeId;
	private String FQualificationName;
	private String FGetTime;
	private String FNumber;
	private String FValidity;
	private String FRegisteredUnit;
	private String FRenewalEduSituation;

	// Constructors

	/** default constructor */
	public TProfessionalQualification() {
	}

	/** minimal constructor */
	public TProfessionalQualification(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TProfessionalQualification(Integer FId, Integer fkEmployeeId,
			String FQualificationName, String FGetTime, String FNumber,
			String FValidity, String FRegisteredUnit,
			String FRenewalEduSituation) {
		this.FId = FId;
		this.fkEmployeeId = fkEmployeeId;
		this.FQualificationName = FQualificationName;
		this.FGetTime = FGetTime;
		this.FNumber = FNumber;
		this.FValidity = FValidity;
		this.FRegisteredUnit = FRegisteredUnit;
		this.FRenewalEduSituation = FRenewalEduSituation;
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

	@Column(name = "FK_Employee_ID")
	public Integer getFkEmployeeId() {
		return this.fkEmployeeId;
	}

	public void setFkEmployeeId(Integer fkEmployeeId) {
		this.fkEmployeeId = fkEmployeeId;
	}

	@Column(name = "F_QualificationName", length = 100)
	public String getFQualificationName() {
		return this.FQualificationName;
	}

	public void setFQualificationName(String FQualificationName) {
		this.FQualificationName = FQualificationName;
	}

	@Column(name = "F_GetTime", length = 30)
	public String getFGetTime() {
		return this.FGetTime;
	}

	public void setFGetTime(String FGetTime) {
		this.FGetTime = FGetTime;
	}

	@Column(name = "F_Number", length = 50)
	public String getFNumber() {
		return this.FNumber;
	}

	public void setFNumber(String FNumber) {
		this.FNumber = FNumber;
	}

	@Column(name = "F_Validity", length = 50)
	public String getFValidity() {
		return this.FValidity;
	}

	public void setFValidity(String FValidity) {
		this.FValidity = FValidity;
	}

	@Column(name = "F_RegisteredUnit", length = 200)
	public String getFRegisteredUnit() {
		return this.FRegisteredUnit;
	}

	public void setFRegisteredUnit(String FRegisteredUnit) {
		this.FRegisteredUnit = FRegisteredUnit;
	}

	@Column(name = "F_RenewalEduSituation", length = 800)
	public String getFRenewalEduSituation() {
		return this.FRenewalEduSituation;
	}

	public void setFRenewalEduSituation(String FRenewalEduSituation) {
		this.FRenewalEduSituation = FRenewalEduSituation;
	}

}