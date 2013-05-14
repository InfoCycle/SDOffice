package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCompaniesInChapter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CompaniesInChapter", schema = "dbo", catalog = "SDOffice")
public class TCompaniesInChapter implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTitle;
	private String FType;
	private String FCounterparts;
	private String FApprover;
	private String FApproverTime;
	private String FProposer;
	private String FAgent;
	private String FAgentTime;
	private String FNote;

	// Constructors

	/** default constructor */
	public TCompaniesInChapter() {
	}

	/** minimal constructor */
	public TCompaniesInChapter(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TCompaniesInChapter(Integer FId, String FTitle, String FType,
			String FCounterparts, String FApprover, String FApproverTime,
			String FProposer, String FAgent, String FAgentTime, String FNote) {
		this.FId = FId;
		this.FTitle = FTitle;
		this.FType = FType;
		this.FCounterparts = FCounterparts;
		this.FApprover = FApprover;
		this.FApproverTime = FApproverTime;
		this.FProposer = FProposer;
		this.FAgent = FAgent;
		this.FAgentTime = FAgentTime;
		this.FNote = FNote;
	}

	// Property accessors
	@Id
	@Column(name = "F_Id", unique = true, nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Title", length = 50)
	public String getFTitle() {
		return this.FTitle;
	}

	public void setFTitle(String FTitle) {
		this.FTitle = FTitle;
	}

	@Column(name = "F_Type", length = 50)
	public String getFType() {
		return this.FType;
	}

	public void setFType(String FType) {
		this.FType = FType;
	}

	@Column(name = "F_Counterparts", length = 50)
	public String getFCounterparts() {
		return this.FCounterparts;
	}

	public void setFCounterparts(String FCounterparts) {
		this.FCounterparts = FCounterparts;
	}

	@Column(name = "F_Approver", length = 50)
	public String getFApprover() {
		return this.FApprover;
	}

	public void setFApprover(String FApprover) {
		this.FApprover = FApprover;
	}

	@Column(name = "F_ApproverTime", length = 50)
	public String getFApproverTime() {
		return this.FApproverTime;
	}

	public void setFApproverTime(String FApproverTime) {
		this.FApproverTime = FApproverTime;
	}

	@Column(name = "F_Proposer", length = 50)
	public String getFProposer() {
		return this.FProposer;
	}

	public void setFProposer(String FProposer) {
		this.FProposer = FProposer;
	}

	@Column(name = "F_Agent", length = 50)
	public String getFAgent() {
		return this.FAgent;
	}

	public void setFAgent(String FAgent) {
		this.FAgent = FAgent;
	}

	@Column(name = "F_AgentTime", length = 50)
	public String getFAgentTime() {
		return this.FAgentTime;
	}

	public void setFAgentTime(String FAgentTime) {
		this.FAgentTime = FAgentTime;
	}

	@Column(name = "F_Note", length = 50)
	public String getFNote() {
		return this.FNote;
	}

	public void setFNote(String FNote) {
		this.FNote = FNote;
	}

}