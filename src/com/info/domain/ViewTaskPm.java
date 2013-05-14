package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTaskPm entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "View_TaskPM", schema = "dbo", catalog = "SDOffice")
public class ViewTaskPm implements java.io.Serializable {

	// Fields

	private Long orderId;
	private Integer taskId;
	private Integer FPmId;
	private String FPmName;
	private Integer FPmType;

	// Constructors

	/** default constructor */
	public ViewTaskPm() {
	}

	/** minimal constructor */
	public ViewTaskPm(Long orderId, Integer taskId) {
		this.orderId = orderId;
		this.taskId = taskId;
	}

	/** full constructor */
	public ViewTaskPm(Long orderId, Integer taskId, Integer FPmId,
			String FPmName, Integer FPmType) {
		this.orderId = orderId;
		this.taskId = taskId;
		this.FPmId = FPmId;
		this.FPmName = FPmName;
		this.FPmType = FPmType;
	}

	// Property accessors
	@Id
	@Column(name = "OrderID")
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "Task_ID", nullable = false)
	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "F_PM_ID")
	public Integer getFPmId() {
		return this.FPmId;
	}

	public void setFPmId(Integer FPmId) {
		this.FPmId = FPmId;
	}

	@Column(name = "F_PM_NAME", length = 20)
	public String getFPmName() {
		return this.FPmName;
	}

	public void setFPmName(String FPmName) {
		this.FPmName = FPmName;
	}

	@Column(name = "F_PM_TYPE")
	public Integer getFPmType() {
		return this.FPmType;
	}

	public void setFPmType(Integer FPmType) {
		this.FPmType = FPmType;
	}

}