package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppUserGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_USER_GROUP", schema = "dbo", catalog = "SDOffice")
public class TAppUserGroup implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkUserId;
	private Integer fkGroupId;

	// Constructors

	/** default constructor */
	public TAppUserGroup() {
	}

	/** minimal constructor */
	public TAppUserGroup(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppUserGroup(Integer FId, Integer fkUserId, Integer fkGroupId) {
		this.FId = FId;
		this.fkUserId = fkUserId;
		this.fkGroupId = fkGroupId;
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

	@Column(name = "FK_USER_ID", precision = 9, scale = 0)
	public Integer getFkUserId() {
		return this.fkUserId;
	}

	public void setFkUserId(Integer fkUserId) {
		this.fkUserId = fkUserId;
	}

	@Column(name = "FK_GROUP_ID", precision = 9, scale = 0)
	public Integer getFkGroupId() {
		return this.fkGroupId;
	}

	public void setFkGroupId(Integer fkGroupId) {
		this.fkGroupId = fkGroupId;
	}

}