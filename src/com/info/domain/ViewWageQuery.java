package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewWageQuery entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "View_WageQuery", schema = "dbo", catalog = "SDOffice")
public class ViewWageQuery implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FNumbers;
	private Integer fkTaskId;
	private Integer fkContractId;
	private Double FReceivables;
	private Double FAlreadyCollection;
	private Double FExtractionBase;
	private Double FBasicProportion;
	private Double FShouldProportion;
	private Double FRealProportion;
	private Double FRetainedProportion;
	private Double FAppraisalScore;
	private Double FCarryTotal;
	private Double FRealCarryTotal;
	private Double FRetainedTotal;
	private Double FAtoasbapCivil;
	private Double FAtoasbapInstallation;
	private String FProjMgr;
	private String FFinanceDept;
	private String FIntegratedDept;
	private String FDeptMgr;
	private String FProductionChief;
	private String FGeneralMgr;
	private Double FAtdowmpmeb;
	private String FNote;
	private String FTaskNumbers;
	private String FTaskName;
	private Integer FEntrustUnitId;
	private String FEntrustUnitName;
	private String TContractNumbers;

	// Constructors

	/** default constructor */
	public ViewWageQuery() {
	}

	/** minimal constructor */
	public ViewWageQuery(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewWageQuery(Integer FId, String FNumbers, Integer fkTaskId,
			Integer fkContractId, Double FReceivables,
			Double FAlreadyCollection, Double FExtractionBase,
			Double FBasicProportion, Double FShouldProportion,
			Double FRealProportion, Double FRetainedProportion,
			Double FAppraisalScore, Double FCarryTotal, Double FRealCarryTotal,
			Double FRetainedTotal, Double FAtoasbapCivil,
			Double FAtoasbapInstallation, String FProjMgr, String FFinanceDept,
			String FIntegratedDept, String FDeptMgr, String FProductionChief,
			String FGeneralMgr, Double FAtdowmpmeb, String FNote,
			String FTaskNumbers, String FTaskName, Integer FEntrustUnitId,
			String FEntrustUnitName, String TContractNumbers) {
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
		this.FTaskNumbers = FTaskNumbers;
		this.FTaskName = FTaskName;
		this.FEntrustUnitId = FEntrustUnitId;
		this.FEntrustUnitName = FEntrustUnitName;
		this.TContractNumbers = TContractNumbers;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", nullable = false)
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

	@Column(name = "F_BasicProportion", precision = 18)
	public Double getFBasicProportion() {
		return this.FBasicProportion;
	}

	public void setFBasicProportion(Double FBasicProportion) {
		this.FBasicProportion = FBasicProportion;
	}

	@Column(name = "F_ShouldProportion", precision = 18)
	public Double getFShouldProportion() {
		return this.FShouldProportion;
	}

	public void setFShouldProportion(Double FShouldProportion) {
		this.FShouldProportion = FShouldProportion;
	}

	@Column(name = "F_RealProportion", precision = 18)
	public Double getFRealProportion() {
		return this.FRealProportion;
	}

	public void setFRealProportion(Double FRealProportion) {
		this.FRealProportion = FRealProportion;
	}

	@Column(name = "F_RetainedProportion", precision = 18)
	public Double getFRetainedProportion() {
		return this.FRetainedProportion;
	}

	public void setFRetainedProportion(Double FRetainedProportion) {
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

	@Column(name = "F_RetainedTotal", precision = 18)
	public Double getFRetainedTotal() {
		return this.FRetainedTotal;
	}

	public void setFRetainedTotal(Double FRetainedTotal) {
		this.FRetainedTotal = FRetainedTotal;
	}

	@Column(name = "F_ATOASBAP_Civil", precision = 18)
	public Double getFAtoasbapCivil() {
		return this.FAtoasbapCivil;
	}

	public void setFAtoasbapCivil(Double FAtoasbapCivil) {
		this.FAtoasbapCivil = FAtoasbapCivil;
	}

	@Column(name = "F_ATOASBAP_Installation", precision = 18)
	public Double getFAtoasbapInstallation() {
		return this.FAtoasbapInstallation;
	}

	public void setFAtoasbapInstallation(Double FAtoasbapInstallation) {
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

	@Column(name = "F_Task_Numbers", length = 300)
	public String getFTaskNumbers() {
		return this.FTaskNumbers;
	}

	public void setFTaskNumbers(String FTaskNumbers) {
		this.FTaskNumbers = FTaskNumbers;
	}

	@Column(name = "F_Task_Name", length = 200)
	public String getFTaskName() {
		return this.FTaskName;
	}

	public void setFTaskName(String FTaskName) {
		this.FTaskName = FTaskName;
	}

	@Column(name = "F_EntrustUnit_ID")
	public Integer getFEntrustUnitId() {
		return this.FEntrustUnitId;
	}

	public void setFEntrustUnitId(Integer FEntrustUnitId) {
		this.FEntrustUnitId = FEntrustUnitId;
	}

	@Column(name = "F_EntrustUnit_Name", length = 200)
	public String getFEntrustUnitName() {
		return this.FEntrustUnitName;
	}

	public void setFEntrustUnitName(String FEntrustUnitName) {
		this.FEntrustUnitName = FEntrustUnitName;
	}

	@Column(name = "T_ContractNumbers", length = 50)
	public String getTContractNumbers() {
		return this.TContractNumbers;
	}

	public void setTContractNumbers(String TContractNumbers) {
		this.TContractNumbers = TContractNumbers;
	}

}