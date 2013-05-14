package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewProcessCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_Process_Customer", schema = "dbo", catalog = "SDOffice")
public class ViewProcessCustomer implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTaskName;
	private String FTaskNumbers;
	private String FEntrustUnitName;
	private String FService;
	private String FProjectScale;
	private String FYjstartTime;
	private String FYjfinishTime;
	private String FContractYjCharge;
	private String taskPm;
	private String taskPerson;

	// Constructors

	/** default constructor */
	public ViewProcessCustomer() {
	}

	/** minimal constructor */
	public ViewProcessCustomer(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewProcessCustomer(Integer FId, String FTaskName,
			String FTaskNumbers, String FEntrustUnitName, String FService,
			String FProjectScale, String FYjstartTime, String FYjfinishTime,
			String FContractYjCharge, String taskPm, String taskPerson) {
		this.FId = FId;
		this.FTaskName = FTaskName;
		this.FTaskNumbers = FTaskNumbers;
		this.FEntrustUnitName = FEntrustUnitName;
		this.FService = FService;
		this.FProjectScale = FProjectScale;
		this.FYjstartTime = FYjstartTime;
		this.FYjfinishTime = FYjfinishTime;
		this.FContractYjCharge = FContractYjCharge;
		this.taskPm = taskPm;
		this.taskPerson = taskPerson;
	}

	// Property accessors
	@Id
	@Column(name = "f_id", nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "f_task_name", length = 300)
	public String getFTaskName() {
		return this.FTaskName;
	}

	public void setFTaskName(String FTaskName) {
		this.FTaskName = FTaskName;
	}

	@Column(name = "f_task_numbers", length = 300)
	public String getFTaskNumbers() {
		return this.FTaskNumbers;
	}

	public void setFTaskNumbers(String FTaskNumbers) {
		this.FTaskNumbers = FTaskNumbers;
	}

	@Column(name = "F_EntrustUnit_Name", length = 200)
	public String getFEntrustUnitName() {
		return this.FEntrustUnitName;
	}

	public void setFEntrustUnitName(String FEntrustUnitName) {
		this.FEntrustUnitName = FEntrustUnitName;
	}

	@Column(name = "F_Service", length = 1000)
	public String getFService() {
		return this.FService;
	}

	public void setFService(String FService) {
		this.FService = FService;
	}

	@Column(name = "F_Project_Scale", length = 100)
	public String getFProjectScale() {
		return this.FProjectScale;
	}

	public void setFProjectScale(String FProjectScale) {
		this.FProjectScale = FProjectScale;
	}

	@Column(name = "F_YJStart_Time", length = 30)
	public String getFYjstartTime() {
		return this.FYjstartTime;
	}

	public void setFYjstartTime(String FYjstartTime) {
		this.FYjstartTime = FYjstartTime;
	}

	@Column(name = "F_YJFinish_Time", length = 30)
	public String getFYjfinishTime() {
		return this.FYjfinishTime;
	}

	public void setFYjfinishTime(String FYjfinishTime) {
		this.FYjfinishTime = FYjfinishTime;
	}

	@Column(name = "F_ContractYJ_Charge", length = 100)
	public String getFContractYjCharge() {
		return this.FContractYjCharge;
	}

	public void setFContractYjCharge(String FContractYjCharge) {
		this.FContractYjCharge = FContractYjCharge;
	}

	@Column(name = "TaskPM", length = 1000)
	public String getTaskPm() {
		return this.taskPm;
	}

	public void setTaskPm(String taskPm) {
		this.taskPm = taskPm;
	}

	@Column(name = "TaskPerson", length = 1000)
	public String getTaskPerson() {
		return this.taskPerson;
	}

	public void setTaskPerson(String taskPerson) {
		this.taskPerson = taskPerson;
	}

}