package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewWfProcess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_wf_process", schema = "dbo", catalog = "SDOffice")
public class ViewWfProcess implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTitle;
	private Integer FCreateUserId;
	private String FCreateUserName;
	private Integer FCurrentUserId;
	private String FCurrentUserName;
	private String FCreateTime;
	private String FTypeName;
	private String FFormUrl;
	private Integer FFormPkid;
	private Integer FState;
	private String FStateText;

	// Constructors

	/** default constructor */
	public ViewWfProcess() {
	}

	/** minimal constructor */
	public ViewWfProcess(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewWfProcess(Integer FId, String FTitle, Integer FCreateUserId,
			String FCreateUserName, Integer FCurrentUserId,
			String FCurrentUserName, String FCreateTime, String FTypeName,
			String FFormUrl, Integer FFormPkid, Integer FState,
			String FStateText) {
		this.FId = FId;
		this.FTitle = FTitle;
		this.FCreateUserId = FCreateUserId;
		this.FCreateUserName = FCreateUserName;
		this.FCurrentUserId = FCurrentUserId;
		this.FCurrentUserName = FCurrentUserName;
		this.FCreateTime = FCreateTime;
		this.FTypeName = FTypeName;
		this.FFormUrl = FFormUrl;
		this.FFormPkid = FFormPkid;
		this.FState = FState;
		this.FStateText = FStateText;
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

	@Column(name = "F_TITLE", length = 800)
	public String getFTitle() {
		return this.FTitle;
	}

	public void setFTitle(String FTitle) {
		this.FTitle = FTitle;
	}

	@Column(name = "F_CreateUserID")
	public Integer getFCreateUserId() {
		return this.FCreateUserId;
	}

	public void setFCreateUserId(Integer FCreateUserId) {
		this.FCreateUserId = FCreateUserId;
	}

	@Column(name = "F_CreateUserName", length = 20)
	public String getFCreateUserName() {
		return this.FCreateUserName;
	}

	public void setFCreateUserName(String FCreateUserName) {
		this.FCreateUserName = FCreateUserName;
	}

	@Column(name = "F_CurrentUserID")
	public Integer getFCurrentUserId() {
		return this.FCurrentUserId;
	}

	public void setFCurrentUserId(Integer FCurrentUserId) {
		this.FCurrentUserId = FCurrentUserId;
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

	@Column(name = "F_TYPE_NAME", length = 250)
	public String getFTypeName() {
		return this.FTypeName;
	}

	public void setFTypeName(String FTypeName) {
		this.FTypeName = FTypeName;
	}

	@Column(name = "F_FormUrl", length = 850)
	public String getFFormUrl() {
		return this.FFormUrl;
	}

	public void setFFormUrl(String FFormUrl) {
		this.FFormUrl = FFormUrl;
	}

	@Column(name = "F_Form_PKID")
	public Integer getFFormPkid() {
		return this.FFormPkid;
	}

	public void setFFormPkid(Integer FFormPkid) {
		this.FFormPkid = FFormPkid;
	}

	@Column(name = "F_STATE")
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_State_Text", length = 50)
	public String getFStateText() {
		return this.FStateText;
	}

	public void setFStateText(String FStateText) {
		this.FStateText = FStateText;
	}

}