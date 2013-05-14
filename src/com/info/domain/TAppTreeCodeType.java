package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppTreeCodeType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_TREE_CODE_TYPE", schema = "dbo", catalog = "SDOffice")
public class TAppTreeCodeType implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FName;
	private String FContent;
	private Integer FSort;
	private Integer FState;

	// Constructors

	/** default constructor */
	public TAppTreeCodeType() {
	}

	/** minimal constructor */
	public TAppTreeCodeType(Integer FId, String FName) {
		this.FId = FId;
		this.FName = FName;
	}

	/** full constructor */
	public TAppTreeCodeType(Integer FId, String FName, String FContent,
			Integer FSort, Integer FState) {
		this.FId = FId;
		this.FName = FName;
		this.FContent = FContent;
		this.FSort = FSort;
		this.FState = FState;
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

	@Column(name = "F_NAME", nullable = false, length = 100)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_CONTENT", length = 400)
	public String getFContent() {
		return this.FContent;
	}

	public void setFContent(String FContent) {
		this.FContent = FContent;
	}

	@Column(name = "F_SORT")
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