package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewWfActiveUrge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_wf_active_urge", schema = "dbo", catalog = "SDOffice")
public class ViewWfActiveUrge implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer processId;
	private Integer activeId;
	private String urgeUser;
	private String urgeToUser;
	private String FUrgeTime;
	private String FRemark;

	// Constructors

	/** default constructor */
	public ViewWfActiveUrge() {
	}

	/** minimal constructor */
	public ViewWfActiveUrge(Integer FId, Integer processId, Integer activeId) {
		this.FId = FId;
		this.processId = processId;
		this.activeId = activeId;
	}

	/** full constructor */
	public ViewWfActiveUrge(Integer FId, Integer processId, Integer activeId,
			String urgeUser, String urgeToUser, String FUrgeTime, String FRemark) {
		this.FId = FId;
		this.processId = processId;
		this.activeId = activeId;
		this.urgeUser = urgeUser;
		this.urgeToUser = urgeToUser;
		this.FUrgeTime = FUrgeTime;
		this.FRemark = FRemark;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID")
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "ProcessId", nullable = false)
	public Integer getProcessId() {
		return this.processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	@Column(name = "ActiveId", nullable = false)
	public Integer getActiveId() {
		return this.activeId;
	}

	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}

	@Column(name = "UrgeUser", length = 20)
	public String getUrgeUser() {
		return this.urgeUser;
	}

	public void setUrgeUser(String urgeUser) {
		this.urgeUser = urgeUser;
	}

	@Column(name = "UrgeToUser", length = 20)
	public String getUrgeToUser() {
		return this.urgeToUser;
	}

	public void setUrgeToUser(String urgeToUser) {
		this.urgeToUser = urgeToUser;
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