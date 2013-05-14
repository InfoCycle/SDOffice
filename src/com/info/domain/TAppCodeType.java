package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppCodeType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_CODE_TYPE", schema = "dbo", catalog = "SDOffice")
public class TAppCodeType implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FName;
	private String FContent;
	private Integer FSort;
	private Integer FState;

	// Constructors

	/** default constructor */
	public TAppCodeType() {
	}

	/** minimal constructor */
	public TAppCodeType(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppCodeType(Integer FId, String FName, String FContent,
			Integer FSort, Integer FState) {
		this.FId = FId;
		this.FName = FName;
		this.FContent = FContent;
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

	@Column(name = "F_CONTENT", length = 400)
	public String getFContent() {
		return this.FContent;
	}

	public void setFContent(String FContent) {
		this.FContent = FContent;
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