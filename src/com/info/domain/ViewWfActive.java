package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewWfActive entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_wf_active", schema = "dbo", catalog = "SDOffice")
public class ViewWfActive implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer activeId;
	private Integer processId;
	private String sendUser;
	private String FSendTime;
	private String acceptUser;
	private String FAcceptTime;
	private String completeTime;
	private String FStateText;
	private String FRemark;

	// Constructors

	/** default constructor */
	public ViewWfActive() {
	}

	/** minimal constructor */
	public ViewWfActive(Integer FId, Integer activeId, Integer processId) {
		this.FId = FId;
		this.activeId = activeId;
		this.processId = processId;
	}

	/** full constructor */
	public ViewWfActive(Integer FId, Integer activeId, Integer processId,
			String sendUser, String FSendTime, String acceptUser,
			String FAcceptTime, String completeTime, String FStateText,
			String FRemark) {
		this.FId = FId;
		this.activeId = activeId;
		this.processId = processId;
		this.sendUser = sendUser;
		this.FSendTime = FSendTime;
		this.acceptUser = acceptUser;
		this.FAcceptTime = FAcceptTime;
		this.completeTime = completeTime;
		this.FStateText = FStateText;
		this.FRemark = FRemark;
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

	@Column(name = "ActiveId", nullable = false)
	public Integer getActiveId() {
		return this.activeId;
	}

	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}

	@Column(name = "ProcessId", nullable = false)
	public Integer getProcessId() {
		return this.processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	@Column(name = "SendUser", length = 20)
	public String getSendUser() {
		return this.sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	@Column(name = "F_SEND_TIME", length = 30)
	public String getFSendTime() {
		return this.FSendTime;
	}

	public void setFSendTime(String FSendTime) {
		this.FSendTime = FSendTime;
	}

	@Column(name = "AcceptUser", length = 20)
	public String getAcceptUser() {
		return this.acceptUser;
	}

	public void setAcceptUser(String acceptUser) {
		this.acceptUser = acceptUser;
	}

	@Column(name = "F_ACCEPT_TIME", length = 30)
	public String getFAcceptTime() {
		return this.FAcceptTime;
	}

	public void setFAcceptTime(String FAcceptTime) {
		this.FAcceptTime = FAcceptTime;
	}

	@Column(name = "CompleteTime", length = 30)
	public String getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "F_State_Text", length = 50)
	public String getFStateText() {
		return this.FStateText;
	}

	public void setFStateText(String FStateText) {
		this.FStateText = FStateText;
	}

	@Column(name = "F_Remark", length = 800)
	public String getFRemark() {
		return this.FRemark;
	}

	public void setFRemark(String FRemark) {
		this.FRemark = FRemark;
	}

}