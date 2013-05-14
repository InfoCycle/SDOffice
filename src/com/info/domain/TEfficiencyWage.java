package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TEfficiencyWage entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_EfficiencyWage", schema = "dbo", catalog = "SDOffice")
public class TEfficiencyWage implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FNumbers;
	private Integer fkTaskId;
	private Integer fkContractId;
	private Double FReceivables;
	private Double FAlreadyCollection;
	private Double FExtractionBase;
	private String FBasicProportion;
	private String FShouldProportion;
	private String FRealProportion;
	private String FRetainedProportion;
	private Double FAppraisalScore;
	private Double FCarryTotal;
	private Double FRealCarryTotal;
	private String FRetainedTotal;
	private String FAtoasbapCivil;
	private String FAtoasbapInstallation;
	private String FProjMgr;
	private String FFinanceDept;
	private String FIntegratedDept;
	private String FDeptMgr;
	private String FProductionChief;
	private String FGeneralMgr;
	private Double FAtdowmpmeb;
	private String FNote;
	private String FFile;

	// Constructors

	/** default constructor */
	public TEfficiencyWage() {
	}

	/** minimal constructor */
	public TEfficiencyWage(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TEfficiencyWage(Integer FId, String FNumbers, Integer fkTaskId,
			Integer fkContractId, Double FReceivables,
			Double FAlreadyCollection, Double FExtractionBase,
			String FBasicProportion, String FShouldProportion,
			String FRealProportion, String FRetainedProportion,
			Double FAppraisalScore, Double FCarryTotal, Double FRealCarryTotal,
			String FRetainedTotal, String FAtoasbapCivil,
			String FAtoasbapInstallation, String FProjMgr, String FFinanceDept,
			String FIntegratedDept, String FDeptMgr, String FProductionChief,
			String FGeneralMgr, Double FAtdowmpmeb, String FNote, String FFile) {
		this.FId = FId;
		this.FNumbers = FNumbers;
		this.fkTaskId = fkTaskId;
		this.fkContractId = fkContractId;
		this.FReceivables = FReceivables;
		this.FAlreadyCollection = FAlreadyCollection;
		this.FExtractionBase = FExtractionBase;
		this.FBasicProportion = FBasicProportion;
		this.FShouldProportion = FShouldProportion;
		this.FRealProportion = FRealProportion;
		this.FRetainedProportion = FRetainedProportion;
		this.FAppraisalScore = FAppraisalScore;
		this.FCarryTotal = FCarryTotal;
		this.FRealCarryTotal = FRealCarryTotal;
		this.FRetainedTotal = FRetainedTotal;
		this.FAtoasbapCivil = FAtoasbapCivil;
		this.FAtoasbapInstallation = FAtoasbapInstallation;
		this.FProjMgr = FProjMgr;
		this.FFinanceDept = FFinanceDept;
		this.FIntegratedDept = FIntegratedDept;
		this.FDeptMgr = FDeptMgr;
		this.FProductionChief = FProductionChief;
		this.FGeneralMgr = FGeneralMgr;
		this.FAtdowmpmeb = FAtdowmpmeb;
		this.FNote = FNote;
		this.FFile = FFile;
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

	@Column(name = "F_Numbers", length = 30)
	public String getFNumbers() {
		return this.FNumbers;
	}

	public void setFNumbers(String FNumbers) {
		this.FNumbers = FNumbers;
	}

	@Column(name = "FK_Task_ID")
	public Integer getFkTaskId() {
		return this.fkTaskId;
	}

	public void setFkTaskId(Integer fkTaskId) {
		this.fkTaskId = fkTaskId;
	}

	@Column(name = "FK_Contract_ID")
	public Integer getFkContractId() {
		return this.fkContractId;
	}

	public void setFkContractId(Integer fkContractId) {
		this.fkContractId = fkContractId;
	}

	@Column(name = "F_Receivables", scale = 4)
	public Double getFReceivables() {
		return this.FReceivables;
	}

	public void setFReceivables(Double FReceivables) {
		this.FReceivables = FReceivables;
	}

	@Column(name = "F_AlreadyCollection", scale = 4)
	public Double getFAlreadyCollection() {
		return this.FAlreadyCollection;
	}

	public void setFAlreadyCollection(Double FAlreadyCollection) {
		this.FAlreadyCollection = FAlreadyCollection;
	}

	@Column(name = "F_ExtractionBase", precision = 18)
	public Double getFExtractionBase() {
		return this.FExtractionBase;
	}

	public void setFExtractionBase(Double FExtractionBase) {
		this.FExtractionBase = FExtractionBase;
	}

	@Column(name = "F_BasicProportion", length = 20)
	public String getFBasicProportion() {
		return this.FBasicProportion;
	}

	public void setFBasicProportion(String FBasicProportion) {
		this.FBasicProportion = FBasicProportion;
	}

	@Column(name = "F_ShouldProportion", length = 20)
	public String getFShouldProportion() {
		return this.FShouldProportion;
	}

	public void setFShouldProportion(String FShouldProportion) {
		this.FShouldProportion = FShouldProportion;
	}

	@Column(name = "F_RealProportion", length = 20)
	public String getFRealProportion() {
		return this.FRealProportion;
	}

	public void setFRealProportion(String FRealProportion) {
		this.FRealProportion = FRealProportion;
	}

	@Column(name = "F_RetainedProportion", length = 20)
	public String getFRetainedProportion() {
		return this.FRetainedProportion;
	}

	public void setFRetainedProportion(String FRetainedProportion) {
		this.FRetainedProportion = FRetainedProportion;
	}

	@Column(name = "F_AppraisalScore", precision = 18)
	public Double getFAppraisalScore() {
		return this.FAppraisalScore;
	}

	public void setFAppraisalScore(Double FAppraisalScore) {
		this.FAppraisalScore = FAppraisalScore;
	}

	@Column(name = "F_CarryTotal", precision = 18)
	public Double getFCarryTotal() {
		return this.FCarryTotal;
	}

	public void setFCarryTotal(Double FCarryTotal) {
		this.FCarryTotal = FCarryTotal;
	}

	@Column(name = "F_RealCarryTotal", precision = 18)
	public Double getFRealCarryTotal() {
		return this.FRealCarryTotal;
	}

	public void setFRealCarryTotal(Double FRealCarryTotal) {
		this.FRealCarryTotal = FRealCarryTotal;
	}

	@Column(name = "F_RetainedTotal", length = 20)
	public String getFRetainedTotal() {
		return this.FRetainedTotal;
	}

	public void setFRetainedTotal(String FRetainedTotal) {
		this.FRetainedTotal = FRetainedTotal;
	}

	@Column(name = "F_ATOASBAP_Civil", length = 20)
	public String getFAtoasbapCivil() {
		return this.FAtoasbapCivil;
	}

	public void setFAtoasbapCivil(String FAtoasbapCivil) {
		this.FAtoasbapCivil = FAtoasbapCivil;
	}

	@Column(name = "F_ATOASBAP_Installation", length = 20)
	public String getFAtoasbapInstallation() {
		return this.FAtoasbapInstallation;
	}

	public void setFAtoasbapInstallation(String FAtoasbapInstallation) {
		this.FAtoasbapInstallation = FAtoasbapInstallation;
	}

	@Column(name = "F_ProjMgr", length = 50)
	public String getFProjMgr() {
		return this.FProjMgr;
	}

	public void setFProjMgr(String FProjMgr) {
		this.FProjMgr = FProjMgr;
	}

	@Column(name = "F_FinanceDept", length = 50)
	public String getFFinanceDept() {
		return this.FFinanceDept;
	}

	public void setFFinanceDept(String FFinanceDept) {
		this.FFinanceDept = FFinanceDept;
	}

	@Column(name = "F_IntegratedDept", length = 50)
	public String getFIntegratedDept() {
		return this.FIntegratedDept;
	}

	public void setFIntegratedDept(String FIntegratedDept) {
		this.FIntegratedDept = FIntegratedDept;
	}

	@Column(name = "F_DeptMgr", length = 50)
	public String getFDeptMgr() {
		return this.FDeptMgr;
	}

	public void setFDeptMgr(String FDeptMgr) {
		this.FDeptMgr = FDeptMgr;
	}

	@Column(name = "F_ProductionChief", length = 50)
	public String getFProductionChief() {
		return this.FProductionChief;
	}

	public void setFProductionChief(String FProductionChief) {
		this.FProductionChief = FProductionChief;
	}

	@Column(name = "F_GeneralMgr", length = 50)
	public String getFGeneralMgr() {
		return this.FGeneralMgr;
	}

	public void setFGeneralMgr(String FGeneralMgr) {
		this.FGeneralMgr = FGeneralMgr;
	}

	@Column(name = "F_ATDOWMPMEB", precision = 18)
	public Double getFAtdowmpmeb() {
		return this.FAtdowmpmeb;
	}

	public void setFAtdowmpmeb(Double FAtdowmpmeb) {
		this.FAtdowmpmeb = FAtdowmpmeb;
	}

	@Column(name = "F_Note", length = 800)
	public String getFNote() {
		return this.FNote;
	}

	public void setFNote(String FNote) {
		this.FNote = FNote;
	}

	@Column(name = "F_File", length = 100)
	public String getFFile() {
		return this.FFile;
	}

	public void setFFile(String FFile) {
		this.FFile = FFile;
	}

}