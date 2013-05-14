package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewWagePage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_WagePage", schema = "dbo", catalog = "SDOffice")
public class ViewWagePage implements java.io.Serializable {

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
	private Double FRetainedTotal;
	private String FAtoasbapCivil;
	private String FAtoasbapInstallation;
	private String FProjMgr;
	private String FFinanceDept;
	private String FIntegratedDept;
	private String FDeptMgr;
	private String FProductionChief;
	private String FGeneralMgr;
	private String FTaskName;
	private String FEntrustUnitName;
	private String FContractNumbers;
	private String FProjMgrName;
	private String FProjMgrViceName;
	private String FServiceMode;

	// Constructors

	/** default constructor */
	public ViewWagePage() {
	}

	/** minimal constructor */
	public ViewWagePage(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewWagePage(Integer FId, String FNumbers, Integer fkTaskId,
			Integer fkContractId, Double FReceivables,
			Double FAlreadyCollection, Double FExtractionBase,
			String FBasicProportion, String FShouldProportion,
			String FRealProportion, String FRetainedProportion,
			Double FAppraisalScore, Double FCarryTotal, Double FRealCarryTotal,
			Double FRetainedTotal, String FAtoasbapCivil,
			String FAtoasbapInstallation, String FProjMgr, String FFinanceDept,
			String FIntegratedDept, String FDeptMgr, String FProductionChief,
			String FGeneralMgr, String FTaskName, String FEntrustUnitName,
			String FContractNumbers, String FProjMgrName,
			String FProjMgrViceName, String FServiceMode) {
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
		this.FTaskName = FTaskName;
		this.FEntrustUnitName = FEntrustUnitName;
		this.FContractNumbers = FContractNumbers;
		this.FProjMgrName = FProjMgrName;
		this.FProjMgrViceName = FProjMgrViceName;
		this.FServiceMode = FServiceMode;
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

	@Column(name = "F_RetainedTotal", precision = 18)
	public Double getFRetainedTotal() {
		return this.FRetainedTotal;
	}

	public void setFRetainedTotal(Double FRetainedTotal) {
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

	@Column(name = "F_Task_Name", length = 200)
	public String getFTaskName() {
		return this.FTaskName;
	}

	public void setFTaskName(String FTaskName) {
		this.FTaskName = FTaskName;
	}

	@Column(name = "F_EntrustUnit_Name", length = 200)
	public String getFEntrustUnitName() {
		return this.FEntrustUnitName;
	}

	public void setFEntrustUnitName(String FEntrustUnitName) {
		this.FEntrustUnitName = FEntrustUnitName;
	}

	@Column(name = "F_ContractNumbers", length = 50)
	public String getFContractNumbers() {
		return this.FContractNumbers;
	}

	public void setFContractNumbers(String FContractNumbers) {
		this.FContractNumbers = FContractNumbers;
	}

	@Column(name = "F_ProjMgr_Name", length = 50)
	public String getFProjMgrName() {
		return this.FProjMgrName;
	}

	public void setFProjMgrName(String FProjMgrName) {
		this.FProjMgrName = FProjMgrName;
	}

	@Column(name = "F_ProjMgr_Vice_Name", length = 50)
	public String getFProjMgrViceName() {
		return this.FProjMgrViceName;
	}

	public void setFProjMgrViceName(String FProjMgrViceName) {
		this.FProjMgrViceName = FProjMgrViceName;
	}

	@Column(name = "F_ServiceMode", length = 50)
	public String getFServiceMode() {
		return this.FServiceMode;
	}

	public void setFServiceMode(String FServiceMode) {
		this.FServiceMode = FServiceMode;
	}

}