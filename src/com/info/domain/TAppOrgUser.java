package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppOrgUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_ORG_USER", schema = "dbo", catalog = "SDOffice")
public class TAppOrgUser implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkOrgId;
	private Integer fkUserId;

	// Constructors

	/** default constructor */
	public TAppOrgUser() {
	}

	/** minimal constructor */
	public TAppOrgUser(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppOrgUser(Integer FId, Integer fkOrgId, Integer fkUserId) {
		this.FId = FId;
		this.fkOrgId = fkOrgId;
		this.fkUserId = fkUserId;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "FK_ORG_ID", precision = 9, scale = 0)
	public Integer getFkOrgId() {
		return this.fkOrgId;
	}

	public void setFkOrgId(Integer fkOrgId) {
		this.fkOrgId = fkOrgId;
	}

	@Column(name = "FK_USER_ID", precision = 9, scale = 0)
	public Integer getFkUserId() {
		return this.fkUserId;
	}

	public void setFkUserId(Integer fkUserId) {
		this.fkUserId = fkUserId;
	}

}