package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewWfActiveReturn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_wf_active_return", schema = "dbo", catalog = "SDOffice")
public class ViewWfActiveReturn implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer processId;
	private Integer activeId;
	private String returnUser;
	private String returnToUser;
	private String FReturnTime;
	private String FRemark;

	// Constructors

	/** default constructor */
	public ViewWfActiveReturn() {
	}

	/** minimal constructor */
	public ViewWfActiveReturn(Integer FId, Integer processId, Integer activeId) {
		this.FId = FId;
		this.processId = processId;
		this.activeId = activeId;
	}

	/** full constructor */
	public ViewWfActiveReturn(Integer FId, Integer processId, Integer activeId,
			String returnUser, String returnToUser, String FReturnTime,
			String FRemark) {
		this.FId = FId;
		this.processId = processId;
		this.activeId = activeId;
		this.returnUser = returnUser;
		this.returnToUser = returnToUser;
		this.FReturnTime = FReturnTime;
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

	@Column(name = "ReturnUser", length = 20)
	public String getReturnUser() {
		return this.returnUser;
	}

	public void setReturnUser(String returnUser) {
		this.returnUser = returnUser;
	}

	@Column(name = "ReturnToUser", length = 20)
	public String getReturnToUser() {
		return this.returnToUser;
	}

	public void setReturnToUser(String returnToUser) {
		this.returnToUser = returnToUser;
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