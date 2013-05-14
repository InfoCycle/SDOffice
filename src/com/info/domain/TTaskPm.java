package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTaskPm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Task_PM", schema = "dbo", catalog = "SDOffice")
public class TTaskPm implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkTaskId;
	private Integer FPmId;
	private String FPmName;
	private Integer FPmType;

	// Constructors

	/** default constructor */
	public TTaskPm() {
	}

	/** minimal constructor */
	public TTaskPm(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TTaskPm(Integer FId, Integer fkTaskId, Integer FPmId,
			String FPmName, Integer FPmType) {
		this.FId = FId;
		this.fkTaskId = fkTaskId;
		this.FPmId = FPmId;
		this.FPmName = FPmName;
		this.FPmType = FPmType;
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

	@Column(name = "FK_TASK_ID")
	public Integer getFkTaskId() {
		return this.fkTaskId;
	}

	public void setFkTaskId(Integer fkTaskId) {
		this.fkTaskId = fkTaskId;
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