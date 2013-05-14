package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfProccessActive entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_PROCCESS_ACTIVE", schema = "dbo", catalog = "SDOffice")
public class TWfProccessActive implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FProcessId;
	private Integer FSendStation;
	private Integer FSendUser;
	private String FSendTime;
	private Integer FAcceptStation;
	private Integer FAcceptUser;
	private String FAcceptTime;
	private Integer FState;
	private Integer FIsurge;
	private Integer FAboveActId;
	private String FCompleteTime;
	private String FRemark;

	// Constructors

	/** default constructor */
	public TWfProccessActive() {
	}

	/** minimal constructor */
	public TWfProccessActive(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TWfProccessActive(Integer FId, Integer FProcessId,
			Integer FSendStation, Integer FSendUser, String FSendTime,
			Integer FAcceptStation, Integer FAcceptUser, String FAcceptTime,
			Integer FState, Integer FIsurge, Integer FAboveActId,
			String FCompleteTime, String FRemark) {
		this.FId = FId;
		this.FProcessId = FProcessId;
		this.FSendStation = FSendStation;
		this.FSendUser = FSendUser;
		this.FSendTime = FSendTime;
		this.FAcceptStation = FAcceptStation;
		this.FAcceptUser = FAcceptUser;
		this.FAcceptTime = FAcceptTime;
		this.FState = FState;
		this.FIsurge = FIsurge;
		this.FAboveActId = FAboveActId;
		this.FCompleteTime = FCompleteTime;
		this.FRemark = FRemark;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", unique = true, nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_PROCESS_ID")
	public Integer getFProcessId() {
		return this.FProcessId;
	}

	public void setFProcessId(Integer FProcessId) {
		this.FProcessId = FProcessId;
	}

	@Column(name = "F_SEND_STATION")
	public Integer getFSendStation() {
		return this.FSendStation;
	}

	public void setFSendStation(Integer FSendStation) {
		this.FSendStation = FSendStation;
	}

	@Column(name = "F_SEND_USER")
	public Integer getFSendUser() {
		return this.FSendUser;
	}

	public void setFSendUser(Integer FSendUser) {
		this.FSendUser = FSendUser;
	}

	@Column(name = "F_SEND_TIME", length = 30)
	public String getFSendTime() {
		return this.FSendTime;
	}

	public void setFSendTime(String FSendTime) {
		this.FSendTime = FSendTime;
	}

	@Column(name = "F_ACCEPT_STATION")
	public Integer getFAcceptStation() {
		return this.FAcceptStation;
	}

	public void setFAcceptStation(Integer FAcceptStation) {
		this.FAcceptStation = FAcceptStation;
	}

	@Column(name = "F_ACCEPT_USER")
	public Integer getFAcceptUser() {
		return this.FAcceptUser;
	}

	public void setFAcceptUser(Integer FAcceptUser) {
		this.FAcceptUser = FAcceptUser;
	}

	@Column(name = "F_ACCEPT_TIME", length = 30)
	public String getFAcceptTime() {
		return this.FAcceptTime;
	}

	public void setFAcceptTime(String FAcceptTime) {
		this.FAcceptTime = FAcceptTime;
	}

	@Column(name = "F_STATE")
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_ISURGE")
	public Integer getFIsurge() {
		return this.FIsurge;
	}

	public void setFIsurge(Integer FIsurge) {
		this.FIsurge = FIsurge;
	}

	@Column(name = "F_Above_ActId")
	public Integer getFAboveActId() {
		return this.FAboveActId;
	}

	public void setFAboveActId(Integer FAboveActId) {
		this.FAboveActId = FAboveActId;
	}

	@Column(name = "F_Complete_Time", length = 30)
	public String getFCompleteTime() {
		return this.FCompleteTime;
	}

	public void setFCompleteTime(String FCompleteTime) {
		this.FCompleteTime = FCompleteTime;
	}

	@Column(name = "F_Remark", length = 800)
	public String getFRemark() {
		return this.FRemark;
	}

	public void setFRemark(String FRemark) {
		this.FRemark = FRemark;
	}

}