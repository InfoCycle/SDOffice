package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTaskItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_TaskItem", schema = "dbo", catalog = "SDOffice")
public class ViewTaskItem implements java.io.Serializable {

	// Fields

	private Integer FProcessId;
	private Integer FTypeId;
	private String FFormUrl;
	private String FStateText;
	private Integer FState;
	private String FCurrentUserName;
	private String FCreateTime;
	private Integer FFormPkid;
	private Integer fkTaskId;
	private String FName;
	private String FNumbers;

	// Constructors

	/** default constructor */
	public ViewTaskItem() {
	}

	/** minimal constructor */
	public ViewTaskItem(Integer FProcessId, Integer FTypeId) {
		this.FProcessId = FProcessId;
		this.FTypeId = FTypeId;
	}

	/** full constructor */
	public ViewTaskItem(Integer FProcessId, Integer FTypeId, String FFormUrl,
			String FStateText, Integer FState, String FCurrentUserName,
			String FCreateTime, Integer FFormPkid, Integer fkTaskId,
			String FName, String FNumbers) {
		this.FProcessId = FProcessId;
		this.FTypeId = FTypeId;
		this.FFormUrl = FFormUrl;
		this.FStateText = FStateText;
		this.FState = FState;
		this.FCurrentUserName = FCurrentUserName;
		this.FCreateTime = FCreateTime;
		this.FFormPkid = FFormPkid;
		this.fkTaskId = fkTaskId;
		this.FName = FName;
		this.FNumbers = FNumbers;
	}

	// Property accessors
	@Id
	@Column(name = "F_process_id", nullable = false)
	public Integer getFProcessId() {
		return this.FProcessId;
	}

	public void setFProcessId(Integer FProcessId) {
		this.FProcessId = FProcessId;
	}

	@Column(name = "F_type_id", nullable = false)
	public Integer getFTypeId() {
		return this.FTypeId;
	}

	public void setFTypeId(Integer FTypeId) {
		this.FTypeId = FTypeId;
	}

	@Column(name = "F_FormUrl", length = 850)
	public String getFFormUrl() {
		return this.FFormUrl;
	}

	public void setFFormUrl(String FFormUrl) {
		this.FFormUrl = FFormUrl;
	}

	@Column(name = "F_State_Text", length = 50)
	public String getFStateText() {
		return this.FStateText;
	}

	public void setFStateText(String FStateText) {
		this.FStateText = FStateText;
	}

	@Column(name = "F_STATE")
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_CurrentUserName", length = 20)
	public String getFCurrentUserName() {
		return this.FCurrentUserName;
	}

	public void setFCurrentUserName(String FCurrentUserName) {
		this.FCurrentUserName = FCurrentUserName;
	}

	@Column(name = "F_CreateTime", length = 30)
	public String getFCreateTime() {
		return this.FCreateTime;
	}

	public void setFCreateTime(String FCreateTime) {
		this.FCreateTime = FCreateTime;
	}

	@Column(name = "F_Form_PKID")
	public Integer getFFormPkid() {
		return this.FFormPkid;
	}

	public void setFFormPkid(Integer FFormPkid) {
		this.FFormPkid = FFormPkid;
	}

	@Column(name = "FK_Task_ID")
	public Integer getFkTaskId() {
		return this.fkTaskId;
	}

	public void setFkTaskId(Integer fkTaskId) {
		this.fkTaskId = fkTaskId;
	}

	@Column(name = "F_Name", length = 200)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_Numbers", length = 300)
	public String getFNumbers() {
		return this.FNumbers;
	}

	public void setFNumbers(String FNumbers) {
		this.FNumbers = FNumbers;
	}

}