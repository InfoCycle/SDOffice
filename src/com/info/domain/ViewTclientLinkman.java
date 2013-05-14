package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTclientLinkman entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_TClient_Linkman", schema = "dbo", catalog = "SDOffice")
public class ViewTclientLinkman implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FClientName;
	private Integer FClientId;
	private String FName;
	private String FPhone;
	private String FTel;
	private String FEmail;
	private String FDepartment;
	private String FPosition;
	private Integer FUserId;
	private String FUserName;
	private Integer fkOrgId;

	// Constructors

	/** default constructor */
	public ViewTclientLinkman() {
	}

	/** minimal constructor */
	public ViewTclientLinkman(Integer FId, String FClientName,
			Integer FClientId, String FName, String FPhone) {
		this.FId = FId;
		this.FClientName = FClientName;
		this.FClientId = FClientId;
		this.FName = FName;
		this.FPhone = FPhone;
	}

	/** full constructor */
	public ViewTclientLinkman(Integer FId, String FClientName,
			Integer FClientId, String FName, String FPhone, String FTel,
			String FEmail, String FDepartment, String FPosition,
			Integer FUserId, String FUserName, Integer fkOrgId) {
		this.FId = FId;
		this.FClientName = FClientName;
		this.FClientId = FClientId;
		this.FName = FName;
		this.FPhone = FPhone;
		this.FTel = FTel;
		this.FEmail = FEmail;
		this.FDepartment = FDepartment;
		this.FPosition = FPosition;
		this.FUserId = FUserId;
		this.FUserName = FUserName;
		this.fkOrgId = fkOrgId;
	}

	// Property accessors
	@Id
	@Column(name = "F_Id", nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Client_Name", nullable = false, length = 50)
	public String getFClientName() {
		return this.FClientName;
	}

	public void setFClientName(String FClientName) {
		this.FClientName = FClientName;
	}

	@Column(name = "F_Client_Id", nullable = false)
	public Integer getFClientId() {
		return this.FClientId;
	}

	public void setFClientId(Integer FClientId) {
		this.FClientId = FClientId;
	}

	@Column(name = "F_Name", nullable = false, length = 50)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_Phone", nullable = false, length = 11)
	public String getFPhone() {
		return this.FPhone;
	}

	public void setFPhone(String FPhone) {
		this.FPhone = FPhone;
	}

	@Column(name = "F_Tel", length = 50)
	public String getFTel() {
		return this.FTel;
	}

	public void setFTel(String FTel) {
		this.FTel = FTel;
	}

	@Column(name = "F_Email", length = 50)
	public String getFEmail() {
		return this.FEmail;
	}

	public void setFEmail(String FEmail) {
		this.FEmail = FEmail;
	}

	@Column(name = "F_Department", length = 50)
	public String getFDepartment() {
		return this.FDepartment;
	}

	public void setFDepartment(String FDepartment) {
		this.FDepartment = FDepartment;
	}

	@Column(name = "F_Position", length = 10)
	public String getFPosition() {
		return this.FPosition;
	}

	public void setFPosition(String FPosition) {
		this.FPosition = FPosition;
	}

	@Column(name = "F_User_Id")
	public Integer getFUserId() {
		return this.FUserId;
	}

	public void setFUserId(Integer FUserId) {
		this.FUserId = FUserId;
	}

	@Column(name = "F_User_Name", length = 50)
	public String getFUserName() {
		return this.FUserName;
	}

	public void setFUserName(String FUserName) {
		this.FUserName = FUserName;
	}

	@Column(name = "FK_ORG_ID", precision = 9, scale = 0)
	public Integer getFkOrgId() {
		return this.fkOrgId;
	}

	public void setFkOrgId(Integer fkOrgId) {
		this.fkOrgId = fkOrgId;
	}

}