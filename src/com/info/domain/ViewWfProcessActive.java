package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewWfProcessActive entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_wf_process_active", schema = "dbo", catalog = "SDOffice")
public class ViewWfProcessActive implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FProcessId;
	private String FTitle;
	private Integer FCreateUserId;
	private Integer FCurrentUserId;
	private String FCurrentUserName;
	private String FCreateTime;
	private String FTypeName;
	private String FFormUrl;
	private Integer FFormPkid;
	private Integer FState;
	private String FStateText;
	private Integer activeId;
	private Integer FSendUser;
	private Integer FSendStation;
	private String FSendTime;
	private Integer FAcceptUser;
	private Integer FAcceptStation;
	private String FAcceptTime;
	private Integer FIsurge;
	private Integer activeState;

	// Constructors

	/** default constructor */
	public ViewWfProcessActive() {
	}

	/** minimal constructor */
	public ViewWfProcessActive(Integer FId, Integer FProcessId) {
		this.FId = FId;
		this.FProcessId = FProcessId;
	}

	/** full constructor */
	public ViewWfProcessActive(Integer FId, Integer FProcessId, String FTitle,
			Integer FCreateUserId, Integer FCurrentUserId,
			String FCurrentUserName, String FCreateTime, String FTypeName,
			String FFormUrl, Integer FFormPkid, Integer FState,
			String FStateText, Integer activeId, Integer FSendUser,
			Integer FSendStation, String FSendTime, Integer FAcceptUser,
			Integer FAcceptStation, String FAcceptTime, Integer FIsurge,
			Integer activeState) {
		this.FId = FId;
		this.FProcessId = FProcessId;
		this.FTitle = FTitle;
		this.FCreateUserId = FCreateUserId;
		this.FCurrentUserId = FCurrentUserId;
		this.FCurrentUserName = FCurrentUserName;
		this.FCreateTime = FCreateTime;
		this.FTypeName = FTypeName;
		this.FFormUrl = FFormUrl;
		this.FFormPkid = FFormPkid;
		this.FState = FState;
		this.FStateText = FStateText;
		this.activeId = activeId;
		this.FSendUser = FSendUser;
		this.FSendStation = FSendStation;
		this.FSendTime = FSendTime;
		this.FAcceptUser = FAcceptUser;
		this.FAcceptStation = FAcceptStation;
		this.FAcceptTime = FAcceptTime;
		this.FIsurge = FIsurge;
		this.activeState = activeState;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID")
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_PROCESS_ID", nullable = false)
	public Integer getFProcessId() {
		return this.FProcessId;
	}

	public void setFProcessId(Integer FProcessId) {
		this.FProcessId = FProcessId;
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

	@Column(name = "ActiveId")
	public Integer getActiveId() {
		return this.activeId;
	}

	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}

	@Column(name = "F_SEND_USER")
	public Integer getFSendUser() {
		return this.FSendUser;
	}

	public void setFSendUser(Integer FSendUser) {
		this.FSendUser = FSendUser;
	}

	@Column(name = "F_SEND_STATION")
	public Integer getFSendStation() {
		return this.FSendStation;
	}

	public void setFSendStation(Integer FSendStation) {
		this.FSendStation = FSendStation;
	}

	@Column(name = "F_SEND_TIME", length = 30)
	public String getFSendTime() {
		return this.FSendTime;
	}

	public void setFSendTime(String FSendTime) {
		this.FSendTime = FSendTime;
	}

	@Column(name = "F_ACCEPT_USER")
	public Integer getFAcceptUser() {
		return this.FAcceptUser;
	}

	public void setFAcceptUser(Integer FAcceptUser) {
		this.FAcceptUser = FAcceptUser;
	}

	@Column(name = "F_ACCEPT_STATION")
	public Integer getFAcceptStation() {
		return this.FAcceptStation;
	}

	public void setFAcceptStation(Integer FAcceptStation) {
		this.FAcceptStation = FAcceptStation;
	}

	@Column(name = "F_ACCEPT_TIME", length = 30)
	public String getFAcceptTime() {
		return this.FAcceptTime;
	}

	public void setFAcceptTime(String FAcceptTime) {
		this.FAcceptTime = FAcceptTime;
	}

	@Column(name = "F_ISURGE")
	public Integer getFIsurge() {
		return this.FIsurge;
	}

	public void setFIsurge(Integer FIsurge) {
		this.FIsurge = FIsurge;
	}

	@Column(name = "ActiveState")
	public Integer getActiveState() {
		return this.activeState;
	}

	public void setActiveState(Integer activeState) {
		this.activeState = activeState;
	}

}