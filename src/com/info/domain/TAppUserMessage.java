package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppUserMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_USER_MESSAGE", schema = "dbo", catalog = "SDOffice")
public class TAppUserMessage implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkUserId;
	private Integer fkMessageId;
	private Integer FState;
	private Timestamp FReadTime;

	// Constructors

	/** default constructor */
	public TAppUserMessage() {
	}

	/** minimal constructor */
	public TAppUserMessage(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppUserMessage(Integer FId, Integer fkUserId, Integer fkMessageId,
			Integer FState, Timestamp FReadTime) {
		this.FId = FId;
		this.fkUserId = fkUserId;
		this.fkMessageId = fkMessageId;
		this.FState = FState;
		this.FReadTime = FReadTime;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "FK_USER_ID", precision = 9, scale = 0)
	public Integer getFkUserId() {
		return this.fkUserId;
	}

	public void setFkUserId(Integer fkUserId) {
		this.fkUserId = fkUserId;
	}

	@Column(name = "FK_MESSAGE_ID", precision = 9, scale = 0)
	public Integer getFkMessageId() {
		return this.fkMessageId;
	}

	public void setFkMessageId(Integer fkMessageId) {
		this.fkMessageId = fkMessageId;
	}

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_READ_TIME", length = 23)
	public Timestamp getFReadTime() {
		return this.FReadTime;
	}

	public void setFReadTime(Timestamp FReadTime) {
		this.FReadTime = FReadTime;
	}

}