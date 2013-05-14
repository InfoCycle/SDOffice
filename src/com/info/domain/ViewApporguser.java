package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewApporguser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "view_apporguser", schema = "dbo", catalog = "SDOffice")
public class ViewApporguser implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FParentId;
	private String FName;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public ViewApporguser() {
	}

	/** minimal constructor */
	public ViewApporguser(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewApporguser(Integer FId, Integer FParentId, String FName,
			Integer FSort) {
		this.FId = FId;
		this.FParentId = FParentId;
		this.FName = FName;
		this.FSort = FSort;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", nullable = false, precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_PARENT_ID", precision = 9, scale = 0)
	public Integer getFParentId() {
		return this.FParentId;
	}

	public void setFParentId(Integer FParentId) {
		this.FParentId = FParentId;
	}

	@Column(name = "F_NAME", length = 100)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

}