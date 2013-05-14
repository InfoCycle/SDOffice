package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TFamilyMembers entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_FamilyMembers", schema = "dbo", catalog = "SDOffice")
public class TFamilyMembers implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkEmployeeId;
	private String FMainMembersName;
	private String FAndIrelationship;
	private String FPhone;

	// Constructors

	/** default constructor */
	public TFamilyMembers() {
	}

	/** minimal constructor */
	public TFamilyMembers(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TFamilyMembers(Integer FId, Integer fkEmployeeId,
			String FMainMembersName, String FAndIrelationship, String FPhone) {
		this.FId = FId;
		this.fkEmployeeId = fkEmployeeId;
		this.FMainMembersName = FMainMembersName;
		this.FAndIrelationship = FAndIrelationship;
		this.FPhone = FPhone;
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

	@Column(name = "F_MainMembersName", length = 30)
	public String getFMainMembersName() {
		return this.FMainMembersName;
	}

	public void setFMainMembersName(String FMainMembersName) {
		this.FMainMembersName = FMainMembersName;
	}

	@Column(name = "F_AndIRelationship", length = 30)
	public String getFAndIrelationship() {
		return this.FAndIrelationship;
	}

	public void setFAndIrelationship(String FAndIrelationship) {
		this.FAndIrelationship = FAndIrelationship;
	}

	@Column(name = "F_Phone", length = 30)
	public String getFPhone() {
		return this.FPhone;
	}

	public void setFPhone(String FPhone) {
		this.FPhone = FPhone;
	}

}