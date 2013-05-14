package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppFunction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_FUNCTION", schema = "dbo", catalog = "SDOffice")
public class TAppFunction implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FName;
	private String FFunctionUrl;
	private Integer FParentId;
	private Integer FSort;
	private Integer FState;

	// Constructors

	/** default constructor */
	public TAppFunction() {
	}

	/** minimal constructor */
	public TAppFunction(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppFunction(Integer FId, String FName, String FFunctionUrl,
			Integer FParentId, Integer FSort, Integer FState) {
		this.FId = FId;
		this.FName = FName;
		this.FFunctionUrl = FFunctionUrl;
		this.FParentId = FParentId;
		this.FSort = FSort;
		this.FState = FState;
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

	@Column(name = "F_NAME", length = 30)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_FUNCTION_URL", length = 300)
	public String getFFunctionUrl() {
		return this.FFunctionUrl;
	}

	public void setFFunctionUrl(String FFunctionUrl) {
		this.FFunctionUrl = FFunctionUrl;
	}

	@Column(name = "F_PARENT_ID", precision = 9, scale = 0)
	public Integer getFParentId() {
		return this.FParentId;
	}

	public void setFParentId(Integer FParentId) {
		this.FParentId = FParentId;
	}

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

}