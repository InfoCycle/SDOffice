package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TExtractionDetailPm entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_ExtractionDetail_PM", schema = "dbo", catalog = "SDOffice")
public class TExtractionDetailPm implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkEfficiencyWageId;
	private Integer FPmId;
	private String FPmName;
	private String FPmworkRatio;
	private Double FPmManageWage;
	private Double FPmadjustedMgtWage;
	private Double FPmrealWage;
	private Double FPmretainedWage;

	// Constructors

	/** default constructor */
	public TExtractionDetailPm() {
	}

	/** minimal constructor */
	public TExtractionDetailPm(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TExtractionDetailPm(Integer FId, Integer fkEfficiencyWageId,
			Integer FPmId, String FPmName, String FPmworkRatio,
			Double FPmManageWage, Double FPmadjustedMgtWage,
			Double FPmrealWage, Double FPmretainedWage) {
		this.FId = FId;
		this.fkEfficiencyWageId = fkEfficiencyWageId;
		this.FPmId = FPmId;
		this.FPmName = FPmName;
		this.FPmworkRatio = FPmworkRatio;
		this.FPmManageWage = FPmManageWage;
		this.FPmadjustedMgtWage = FPmadjustedMgtWage;
		this.FPmrealWage = FPmrealWage;
		this.FPmretainedWage = FPmretainedWage;
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

	@Column(name = "F_PM_ID")
	public Integer getFPmId() {
		return this.FPmId;
	}

	public void setFPmId(Integer FPmId) {
		this.FPmId = FPmId;
	}

	@Column(name = "F_PM_Name", length = 30)
	public String getFPmName() {
		return this.FPmName;
	}

	public void setFPmName(String FPmName) {
		this.FPmName = FPmName;
	}

	@Column(name = "F_PMWorkRatio", length = 20)
	public String getFPmworkRatio() {
		return this.FPmworkRatio;
	}

	public void setFPmworkRatio(String FPmworkRatio) {
		this.FPmworkRatio = FPmworkRatio;
	}

	@Column(name = "F_PM_ManageWage", scale = 4)
	public Double getFPmManageWage() {
		return this.FPmManageWage;
	}

	public void setFPmManageWage(Double FPmManageWage) {
		this.FPmManageWage = FPmManageWage;
	}

	@Column(name = "F_PMAdjustedMgtWage", scale = 4)
	public Double getFPmadjustedMgtWage() {
		return this.FPmadjustedMgtWage;
	}

	public void setFPmadjustedMgtWage(Double FPmadjustedMgtWage) {
		this.FPmadjustedMgtWage = FPmadjustedMgtWage;
	}

	@Column(name = "F_PMRealWage", scale = 4)
	public Double getFPmrealWage() {
		return this.FPmrealWage;
	}

	public void setFPmrealWage(Double FPmrealWage) {
		this.FPmrealWage = FPmrealWage;
	}

	@Column(name = "F_PMRetainedWage", scale = 4)
	public Double getFPmretainedWage() {
		return this.FPmretainedWage;
	}

	public void setFPmretainedWage(Double FPmretainedWage) {
		this.FPmretainedWage = FPmretainedWage;
	}

}