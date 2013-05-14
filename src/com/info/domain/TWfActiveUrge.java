package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfActiveUrge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_ACTIVE_URGE", schema = "dbo", catalog = "SDOffice")
public class TWfActiveUrge implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FActiveId;
	private Integer FUrgeUser;
	private String FUrgeTime;
	private String FRemark;

	// Constructors

	/** default constructor */
	public TWfActiveUrge() {
	}

	/** minimal constructor */
	public TWfActiveUrge(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TWfActiveUrge(Integer FId, Integer FActiveId, Integer FUrgeUser,
			String FUrgeTime, String FRemark) {
		this.FId = FId;
		this.FActiveId = FActiveId;
		this.FUrgeUser = FUrgeUser;
		this.FUrgeTime = FUrgeTime;
		this.FRemark = FRemark;
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

	@Column(name = "F_ACTIVE_ID")
	public Integer getFActiveId() {
		return this.FActiveId;
	}

	public void setFActiveId(Integer FActiveId) {
		this.FActiveId = FActiveId;
	}

	@Column(name = "F_URGE_USER")
	public Integer getFUrgeUser() {
		return this.FUrgeUser;
	}

	public void setFUrgeUser(Integer FUrgeUser) {
		this.FUrgeUser = FUrgeUser;
	}

	@Column(name = "F_URGE_TIME", length = 30)
	public String getFUrgeTime() {
		return this.FUrgeTime;
	}

	public void setFUrgeTime(String FUrgeTime) {
		this.FUrgeTime = FUrgeTime;
	}

	@Column(name = "F_Remark", length = 800)
	public String getFRemark() {
		return this.FRemark;
	}

	public void setFRemark(String FRemark) {
		this.FRemark = FRemark;
	}

}