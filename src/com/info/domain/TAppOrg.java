package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppOrg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_ORG", schema = "dbo", catalog = "SDOffice")
public class TAppOrg implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FParentId;
	private String FName;
	private Integer FSort;
	private Integer FState;
	private String FAno;

	// Constructors

	/** default constructor */
	public TAppOrg() {
	}

	/** minimal constructor */
	public TAppOrg(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppOrg(Integer FId, Integer FParentId, String FName, Integer FSort,
			Integer FState, String FAno) {
		this.FId = FId;
		this.FParentId = FParentId;
		this.FName = FName;
		this.FSort = FSort;
		this.FState = FState;
		this.FAno = FAno;
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

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_ANO", length = 40)
	public String getFAno() {
		return this.FAno;
	}

	public void setFAno(String FAno) {
		this.FAno = FAno;
	}

}