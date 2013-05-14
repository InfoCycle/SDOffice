package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TExtractionDetailOp entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_ExtractionDetail_OP", schema = "dbo", catalog = "SDOffice")
public class TExtractionDetailOp implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkEfficiencyWageId;
	private Integer FOutsourcingNameId;
	private String FOutsourcingName;
	private String FOutsourcingWorkRatio;
	private Double FOutsourcingExtractionWage;
	private String FOutsourcingConfirm;

	// Constructors

	/** default constructor */
	public TExtractionDetailOp() {
	}

	/** minimal constructor */
	public TExtractionDetailOp(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TExtractionDetailOp(Integer FId, Integer fkEfficiencyWageId,
			Integer FOutsourcingNameId, String FOutsourcingName,
			String FOutsourcingWorkRatio, Double FOutsourcingExtractionWage,
			String FOutsourcingConfirm) {
		this.FId = FId;
		this.fkEfficiencyWageId = fkEfficiencyWageId;
		this.FOutsourcingNameId = FOutsourcingNameId;
		this.FOutsourcingName = FOutsourcingName;
		this.FOutsourcingWorkRatio = FOutsourcingWorkRatio;
		this.FOutsourcingExtractionWage = FOutsourcingExtractionWage;
		this.FOutsourcingConfirm = FOutsourcingConfirm;
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

	@Column(name = "F_OutsourcingName_ID")
	public Integer getFOutsourcingNameId() {
		return this.FOutsourcingNameId;
	}

	public void setFOutsourcingNameId(Integer FOutsourcingNameId) {
		this.FOutsourcingNameId = FOutsourcingNameId;
	}

	@Column(name = "F_OutsourcingName", length = 30)
	public String getFOutsourcingName() {
		return this.FOutsourcingName;
	}

	public void setFOutsourcingName(String FOutsourcingName) {
		this.FOutsourcingName = FOutsourcingName;
	}

	@Column(name = "F_OutsourcingWorkRatio", length = 20)
	public String getFOutsourcingWorkRatio() {
		return this.FOutsourcingWorkRatio;
	}

	public void setFOutsourcingWorkRatio(String FOutsourcingWorkRatio) {
		this.FOutsourcingWorkRatio = FOutsourcingWorkRatio;
	}

	@Column(name = "F_OutsourcingExtractionWage", scale = 4)
	public Double getFOutsourcingExtractionWage() {
		return this.FOutsourcingExtractionWage;
	}

	public void setFOutsourcingExtractionWage(Double FOutsourcingExtractionWage) {
		this.FOutsourcingExtractionWage = FOutsourcingExtractionWage;
	}

	@Column(name = "F_OutsourcingConfirm", length = 50)
	public String getFOutsourcingConfirm() {
		return this.FOutsourcingConfirm;
	}

	public void setFOutsourcingConfirm(String FOutsourcingConfirm) {
		this.FOutsourcingConfirm = FOutsourcingConfirm;
	}

}