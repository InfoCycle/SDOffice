package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfState entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_STATE", schema = "dbo", catalog = "SDOffice")
public class TWfState implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FStateId;
	private String FStateText;

	// Constructors

	/** default constructor */
	public TWfState() {
	}

	/** minimal constructor */
	public TWfState(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TWfState(Integer FId, Integer FStateId, String FStateText) {
		this.FId = FId;
		this.FStateId = FStateId;
		this.FStateText = FStateText;
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

	@Column(name = "F_State_ID")
	public Integer getFStateId() {
		return this.FStateId;
	}

	public void setFStateId(Integer FStateId) {
		this.FStateId = FStateId;
	}

	@Column(name = "F_State_Text", length = 50)
	public String getFStateText() {
		return this.FStateText;
	}

	public void setFStateText(String FStateText) {
		this.FStateText = FStateText;
	}

}