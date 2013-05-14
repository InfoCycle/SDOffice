package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TIndustry entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Industry", schema = "dbo", catalog = "SDOffice")
public class TIndustry implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FParentId;
	private String FName;
	private String FCode;
	private String FNote;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public TIndustry() {
	}

	/** minimal constructor */
	public TIndustry(Integer FId, Integer FParentId, String FName,
			String FCode, Integer FSort) {
		this.FId = FId;
		this.FParentId = FParentId;
		this.FName = FName;
		this.FCode = FCode;
		this.FSort = FSort;
	}

	/** full constructor */
	public TIndustry(Integer FId, Integer FParentId, String FName,
			String FCode, String FNote, Integer FSort) {
		this.FId = FId;
		this.FParentId = FParentId;
		this.FName = FName;
		this.FCode = FCode;
		this.FNote = FNote;
		this.FSort = FSort;
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

	@Column(name = "F_Parent_ID", nullable = false)
	public Integer getFParentId() {
		return this.FParentId;
	}

	public void setFParentId(Integer FParentId) {
		this.FParentId = FParentId;
	}

	@Column(name = "F_Name", nullable = false, length = 200)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_Code", nullable = false, length = 10)
	public String getFCode() {
		return this.FCode;
	}

	public void setFCode(String FCode) {
		this.FCode = FCode;
	}

	@Column(name = "F_Note", length = 300)
	public String getFNote() {
		return this.FNote;
	}

	public void setFNote(String FNote) {
		this.FNote = FNote;
	}

	@Column(name = "F_Sort", nullable = false)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

}