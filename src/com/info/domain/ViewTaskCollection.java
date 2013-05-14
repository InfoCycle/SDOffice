package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTaskCollection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_Task_Collection", schema = "dbo", catalog = "SDOffice")
public class ViewTaskCollection implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FTaskId;
	private String FTaskName;
	private String FTaskNumbers;
	private String taskPm;
	private Double FReceivable;
	private Double FAccounts;
	private Double FOutstanding;
	private Double FSupporterReceipt;
	private String FRecordUser;
	private String FRecordTime;
	private Integer FState;
	private Double FContractYjCharge;

	// Constructors

	/** default constructor */
	public ViewTaskCollection() {
	}

	/** minimal constructor */
	public ViewTaskCollection(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewTaskCollection(Integer FId, Integer FTaskId, String FTaskName,
			String FTaskNumbers, String taskPm, Double FReceivable,
			Double FAccounts, Double FOutstanding, Double FSupporterReceipt,
			String FRecordUser, String FRecordTime, Integer FState,
			Double FContractYjCharge) {
		this.FId = FId;
		this.FTaskId = FTaskId;
		this.FTaskName = FTaskName;
		this.FTaskNumbers = FTaskNumbers;
		this.taskPm = taskPm;
		this.FReceivable = FReceivable;
		this.FAccounts = FAccounts;
		this.FOutstanding = FOutstanding;
		this.FSupporterReceipt = FSupporterReceipt;
		this.FRecordUser = FRecordUser;
		this.FRecordTime = FRecordTime;
		this.FState = FState;
		this.FContractYjCharge = FContractYjCharge;
	}

	// Property accessors
	@Id
	@Column(name = "F_Id", nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Task_Id")
	public Integer getFTaskId() {
		return this.FTaskId;
	}

	public void setFTaskId(Integer FTaskId) {
		this.FTaskId = FTaskId;
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

	@Column(name = "TaskPM", length = 1000)
	public String getTaskPm() {
		return this.taskPm;
	}

	public void setTaskPm(String taskPm) {
		this.taskPm = taskPm;
	}

	@Column(name = "F_Receivable", scale = 4)
	public Double getFReceivable() {
		return this.FReceivable;
	}

	public void setFReceivable(Double FReceivable) {
		this.FReceivable = FReceivable;
	}

	@Column(name = "F_Accounts", scale = 4)
	public Double getFAccounts() {
		return this.FAccounts;
	}

	public void setFAccounts(Double FAccounts) {
		this.FAccounts = FAccounts;
	}

	@Column(name = "F_Outstanding", scale = 4)
	public Double getFOutstanding() {
		return this.FOutstanding;
	}

	public void setFOutstanding(Double FOutstanding) {
		this.FOutstanding = FOutstanding;
	}

	@Column(name = "F_Supporter_Receipt", scale = 4)
	public Double getFSupporterReceipt() {
		return this.FSupporterReceipt;
	}

	public void setFSupporterReceipt(Double FSupporterReceipt) {
		this.FSupporterReceipt = FSupporterReceipt;
	}

	@Column(name = "F_Record_User", length = 30)
	public String getFRecordUser() {
		return this.FRecordUser;
	}

	public void setFRecordUser(String FRecordUser) {
		this.FRecordUser = FRecordUser;
	}

	@Column(name = "F_Record_Time", length = 30)
	public String getFRecordTime() {
		return this.FRecordTime;
	}

	public void setFRecordTime(String FRecordTime) {
		this.FRecordTime = FRecordTime;
	}

	@Column(name = "F_State")
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_ContractYJ_Charge", scale = 4)
	public Double getFContractYjCharge() {
		return this.FContractYjCharge;
	}

	public void setFContractYjCharge(Double FContractYjCharge) {
		this.FContractYjCharge = FContractYjCharge;
	}

}