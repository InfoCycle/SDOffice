package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TExtractionDetailIs entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_ExtractionDetail_IS", schema = "dbo", catalog = "SDOffice")
public class TExtractionDetailIs implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkEfficiencyWageId;
	private Integer FIsnameId;
	private String FIsname;
	private String FIsworkRatio;
	private Double FIsshouldTotal;
	private Double FIsadjustedTotal;
	private Double FIsrealWage;
	private Double FIsretainedWage;
	private String FIsconfirm;

	// Constructors

	/** default constructor */
	public TExtractionDetailIs() {
	}

	/** minimal constructor */
	public TExtractionDetailIs(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TExtractionDetailIs(Integer FId, Integer fkEfficiencyWageId,
			Integer FIsnameId, String FIsname, String FIsworkRatio,
			Double FIsshouldTotal, Double FIsadjustedTotal, Double FIsrealWage,
			Double FIsretainedWage, String FIsconfirm) {
		this.FId = FId;
		this.fkEfficiencyWageId = fkEfficiencyWageId;
		this.FIsnameId = FIsnameId;
		this.FIsname = FIsname;
		this.FIsworkRatio = FIsworkRatio;
		this.FIsshouldTotal = FIsshouldTotal;
		this.FIsadjustedTotal = FIsadjustedTotal;
		this.FIsrealWage = FIsrealWage;
		this.FIsretainedWage = FIsretainedWage;
		this.FIsconfirm = FIsconfirm;
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

	@Column(name = "FK_EfficiencyWage_ID")
	public Integer getFkEfficiencyWageId() {
		return this.fkEfficiencyWageId;
	}

	public void setFkEfficiencyWageId(Integer fkEfficiencyWageId) {
		this.fkEfficiencyWageId = fkEfficiencyWageId;
	}

	@Column(name = "F_ISName_ID")
	public Integer getFIsnameId() {
		return this.FIsnameId;
	}

	public void setFIsnameId(Integer FIsnameId) {
		this.FIsnameId = FIsnameId;
	}

	@Column(name = "F_ISName", length = 30)
	public String getFIsname() {
		return this.FIsname;
	}

	public void setFIsname(String FIsname) {
		this.FIsname = FIsname;
	}

	@Column(name = "F_ISWorkRatio", length = 20)
	public String getFIsworkRatio() {
		return this.FIsworkRatio;
	}

	public void setFIsworkRatio(String FIsworkRatio) {
		this.FIsworkRatio = FIsworkRatio;
	}

	@Column(name = "F_ISShouldTotal", scale = 4)
	public Double getFIsshouldTotal() {
		return this.FIsshouldTotal;
	}

	public void setFIsshouldTotal(Double FIsshouldTotal) {
		this.FIsshouldTotal = FIsshouldTotal;
	}

	@Column(name = "F_ISAdjustedTotal", scale = 4)
	public Double getFIsadjustedTotal() {
		return this.FIsadjustedTotal;
	}

	public void setFIsadjustedTotal(Double FIsadjustedTotal) {
		this.FIsadjustedTotal = FIsadjustedTotal;
	}

	@Column(name = "F_ISRealWage", scale = 4)
	public Double getFIsrealWage() {
		return this.FIsrealWage;
	}

	public void setFIsrealWage(Double FIsrealWage) {
		this.FIsrealWage = FIsrealWage;
	}

	@Column(name = "F_ISRetainedWage", scale = 4)
	public Double getFIsretainedWage() {
		return this.FIsretainedWage;
	}

	public void setFIsretainedWage(Double FIsretainedWage) {
		this.FIsretainedWage = FIsretainedWage;
	}

	@Column(name = "F_ISConfirm", length = 50)
	public String getFIsconfirm() {
		return this.FIsconfirm;
	}

	public void setFIsconfirm(String FIsconfirm) {
		this.FIsconfirm = FIsconfirm;
	}

}