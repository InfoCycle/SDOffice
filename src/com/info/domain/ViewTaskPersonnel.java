package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTaskPersonnel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_TaskPersonnel", schema = "dbo", catalog = "SDOffice")
public class ViewTaskPersonnel implements java.io.Serializable {

	// Fields

	private Long orderId;
	private Integer taskId;
	private String FTaskName;
	private Integer FEntrustUnitId;
	private String FEntrustUnitName;
	private Integer FMainTaskId;
	private Integer FProjMgrId;
	private String FProjMgrName;
	private Integer FProjMgrViceId;
	private String FProjMgrViceName;
	private String FPlanNumbers;
	private Integer FPersonnelId;
	private String FPersonnelName;
	private Integer FEmployeeType;

	// Constructors

	/** default constructor */
	public ViewTaskPersonnel() {
	}

	/** minimal constructor */
	public ViewTaskPersonnel(Long orderId, Integer taskId) {
		this.orderId = orderId;
		this.taskId = taskId;
	}

	/** full constructor */
	public ViewTaskPersonnel(Long orderId, Integer taskId, String FTaskName,
			Integer FEntrustUnitId, String FEntrustUnitName,
			Integer FMainTaskId, Integer FProjMgrId, String FProjMgrName,
			Integer FProjMgrViceId, String FProjMgrViceName,
			String FPlanNumbers, Integer FPersonnelId, String FPersonnelName,
			Integer FEmployeeType) {
		this.orderId = orderId;
		this.taskId = taskId;
		this.FTaskName = FTaskName;
		this.FEntrustUnitId = FEntrustUnitId;
		this.FEntrustUnitName = FEntrustUnitName;
		this.FMainTaskId = FMainTaskId;
		this.FProjMgrId = FProjMgrId;
		this.FProjMgrName = FProjMgrName;
		this.FProjMgrViceId = FProjMgrViceId;
		this.FProjMgrViceName = FProjMgrViceName;
		this.FPlanNumbers = FPlanNumbers;
		this.FPersonnelId = FPersonnelId;
		this.FPersonnelName = FPersonnelName;
		this.FEmployeeType = FEmployeeType;
	}

	// Property accessors
	@Id
	@Column(name = "OrderID")
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

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

	@Column(name = "F_Personnel_ID")
	public Integer getFPersonnelId() {
		return this.FPersonnelId;
	}

	public void setFPersonnelId(Integer FPersonnelId) {
		this.FPersonnelId = FPersonnelId;
	}

	@Column(name = "F_Personnel_Name", length = 30)
	public String getFPersonnelName() {
		return this.FPersonnelName;
	}

	public void setFPersonnelName(String FPersonnelName) {
		this.FPersonnelName = FPersonnelName;
	}

	@Column(name = "F_EmployeeType")
	public Integer getFEmployeeType() {
		return this.FEmployeeType;
	}

	public void setFEmployeeType(Integer FEmployeeType) {
		this.FEmployeeType = FEmployeeType;
	}

}