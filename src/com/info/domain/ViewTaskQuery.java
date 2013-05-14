package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTaskQuery entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_TaskQuery", schema = "dbo", catalog = "SDOffice")
public class ViewTaskQuery implements java.io.Serializable {

	// Fields

	private Integer taskId;
	private String FTaskName;
	private Integer FEntrustUnitId;
	private String FEntrustUnitName;
	private Integer FMainTaskId;
	private String agecnyName;
	private Integer FContractId;
	private String FContractNumbers;
	private String FServiceMode;
	private String FServicePerion;
	private Integer FMainContractId;
	private String FProjectType;
	private Integer FProjMgrId;
	private String FProjMgrName;
	private Integer FProjMgrViceId;
	private String FProjMgrViceName;
	private String FPlanNumbers;

	// Constructors

	/** default constructor */
	public ViewTaskQuery() {
	}

	/** minimal constructor */
	public ViewTaskQuery(Integer taskId) {
		this.taskId = taskId;
	}

	/** full constructor */
	public ViewTaskQuery(Integer taskId, String FTaskName,
			Integer FEntrustUnitId, String FEntrustUnitName,
			Integer FMainTaskId, String agecnyName, Integer FContractId,
			String FContractNumbers, String FServiceMode,
			String FServicePerion, Integer FMainContractId,
			String FProjectType, Integer FProjMgrId, String FProjMgrName,
			Integer FProjMgrViceId, String FProjMgrViceName, String FPlanNumbers) {
		this.taskId = taskId;
		this.FTaskName = FTaskName;
		this.FEntrustUnitId = FEntrustUnitId;
		this.FEntrustUnitName = FEntrustUnitName;
		this.FMainTaskId = FMainTaskId;
		this.agecnyName = agecnyName;
		this.FContractId = FContractId;
		this.FContractNumbers = FContractNumbers;
		this.FServiceMode = FServiceMode;
		this.FServicePerion = FServicePerion;
		this.FMainContractId = FMainContractId;
		this.FProjectType = FProjectType;
		this.FProjMgrId = FProjMgrId;
		this.FProjMgrName = FProjMgrName;
		this.FProjMgrViceId = FProjMgrViceId;
		this.FProjMgrViceName = FProjMgrViceName;
		this.FPlanNumbers = FPlanNumbers;
	}

	// Property accessors
	@Id
	@Column(name = "Task_ID", nullable = false)
	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	@Column(name = "F_MainTask_ID")
	public Integer getFMainTaskId() {
		return this.FMainTaskId;
	}

	public void setFMainTaskId(Integer FMainTaskId) {
		this.FMainTaskId = FMainTaskId;
	}

	@Column(name = "AgecnyName", length = 50)
	public String getAgecnyName() {
		return this.agecnyName;
	}

	public void setAgecnyName(String agecnyName) {
		this.agecnyName = agecnyName;
	}

	@Column(name = "F_Contract_ID")
	public Integer getFContractId() {
		return this.FContractId;
	}

	public void setFContractId(Integer FContractId) {
		this.FContractId = FContractId;
	}

	@Column(name = "F_ContractNumbers", length = 50)
	public String getFContractNumbers() {
		return this.FContractNumbers;
	}

	public void setFContractNumbers(String FContractNumbers) {
		this.FContractNumbers = FContractNumbers;
	}

	@Column(name = "F_ServiceMode", length = 50)
	public String getFServiceMode() {
		return this.FServiceMode;
	}

	public void setFServiceMode(String FServiceMode) {
		this.FServiceMode = FServiceMode;
	}

	@Column(name = "F_ServicePerion", length = 50)
	public String getFServicePerion() {
		return this.FServicePerion;
	}

	public void setFServicePerion(String FServicePerion) {
		this.FServicePerion = FServicePerion;
	}

	@Column(name = "F_MainContract_ID")
	public Integer getFMainContractId() {
		return this.FMainContractId;
	}

	public void setFMainContractId(Integer FMainContractId) {
		this.FMainContractId = FMainContractId;
	}

	@Column(name = "F_ProjectType", length = 50)
	public String getFProjectType() {
		return this.FProjectType;
	}

	public void setFProjectType(String FProjectType) {
		this.FProjectType = FProjectType;
	}

	@Column(name = "F_ProjMgr_ID")
	public Integer getFProjMgrId() {
		return this.FProjMgrId;
	}

	public void setFProjMgrId(Integer FProjMgrId) {
		this.FProjMgrId = FProjMgrId;
	}

	@Column(name = "F_ProjMgr_Name", length = 50)
	public String getFProjMgrName() {
		return this.FProjMgrName;
	}

	public void setFProjMgrName(String FProjMgrName) {
		this.FProjMgrName = FProjMgrName;
	}

	@Column(name = "F_ProjMgr_Vice_ID")
	public Integer getFProjMgrViceId() {
		return this.FProjMgrViceId;
	}

	public void setFProjMgrViceId(Integer FProjMgrViceId) {
		this.FProjMgrViceId = FProjMgrViceId;
	}

	@Column(name = "F_ProjMgr_Vice_Name", length = 50)
	public String getFProjMgrViceName() {
		return this.FProjMgrViceName;
	}

	public void setFProjMgrViceName(String FProjMgrViceName) {
		this.FProjMgrViceName = FProjMgrViceName;
	}

	@Column(name = "F_Plan_Numbers", length = 100)
	public String getFPlanNumbers() {
		return this.FPlanNumbers;
	}

	public void setFPlanNumbers(String FPlanNumbers) {
		this.FPlanNumbers = FPlanNumbers;
	}

}