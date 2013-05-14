package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfActiveReturn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_ACTIVE_RETURN", schema = "dbo", catalog = "SDOffice")
public class TWfActiveReturn implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FActiveId;
	private Integer FReturnUser;
	private Integer FReturnToUser;
	private String FReturnTime;
	private String FRemark;

	// Constructors

	/** default constructor */
	public TWfActiveReturn() {
	}

	/** minimal constructor */
	public TWfActiveReturn(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TWfActiveReturn(Integer FId, Integer FActiveId, Integer FReturnUser,
			Integer FReturnToUser, String FReturnTime, String FRemark) {
		this.FId = FId;
		this.FActiveId = FActiveId;
		this.FReturnUser = FReturnUser;
		this.FReturnToUser = FReturnToUser;
		this.FReturnTime = FReturnTime;
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

	@Column(name = "F_ActiveId")
	public Integer getFActiveId() {
		return this.FActiveId;
	}

	public void setFActiveId(Integer FActiveId) {
		this.FActiveId = FActiveId;
	}

	@Column(name = "F_ReturnUser")
	public Integer getFReturnUser() {
		return this.FReturnUser;
	}

	public void setFReturnUser(Integer FReturnUser) {
		this.FReturnUser = FReturnUser;
	}

	@Column(name = "F_ReturnToUser")
	public Integer getFReturnToUser() {
		return this.FReturnToUser;
	}

	public void setFReturnToUser(Integer FReturnToUser) {
		this.FReturnToUser = FReturnToUser;
	}

	@Column(name = "F_ReturnTime", length = 30)
	public String getFReturnTime() {
		return this.FReturnTime;
	}

	public void setFReturnTime(String FReturnTime) {
		this.FReturnTime = FReturnTime;
	}

	@Column(name = "F_Remark", length = 800)
	public String getFRemark() {
		return this.FRemark;
	}

	public void setFRemark(String FRemark) {
		this.FRemark = FRemark;
	}

}