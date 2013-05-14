package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTaskIndustrys entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Task_Industrys", schema = "dbo", catalog = "SDOffice")
public class TTaskIndustrys implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkTaskId;
	private Integer FIndustryCategoryId;
	private String FIndustryCategoryName;

	// Constructors

	/** default constructor */
	public TTaskIndustrys() {
	}

	/** minimal constructor */
	public TTaskIndustrys(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TTaskIndustrys(Integer FId, Integer fkTaskId,
			Integer FIndustryCategoryId, String FIndustryCategoryName) {
		this.FId = FId;
		this.fkTaskId = fkTaskId;
		this.FIndustryCategoryId = FIndustryCategoryId;
		this.FIndustryCategoryName = FIndustryCategoryName;
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

	@Column(name = "F_Industry_Category_ID")
	public Integer getFIndustryCategoryId() {
		return this.FIndustryCategoryId;
	}

	public void setFIndustryCategoryId(Integer FIndustryCategoryId) {
		this.FIndustryCategoryId = FIndustryCategoryId;
	}

	@Column(name = "F_Industry_Category_Name", length = 200)
	public String getFIndustryCategoryName() {
		return this.FIndustryCategoryName;
	}

	public void setFIndustryCategoryName(String FIndustryCategoryName) {
		this.FIndustryCategoryName = FIndustryCategoryName;
	}

}