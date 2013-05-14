package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_LOG", schema = "dbo", catalog = "SDOffice")
public class TAppLog implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkUserId;
	private String FActionType;
	private String FActionObject;
	private Timestamp FActionTime;
	private Integer FActionId;
	private String FUserName;

	// Constructors

	/** default constructor */
	public TAppLog() {
	}

	/** minimal constructor */
	public TAppLog(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppLog(Integer FId, Integer fkUserId, String FActionType,
			String FActionObject, Timestamp FActionTime, Integer FActionId,
			String FUserName) {
		this.FId = FId;
		this.fkUserId = fkUserId;
		this.FActionType = FActionType;
		this.FActionObject = FActionObject;
		this.FActionTime = FActionTime;
		this.FActionId = FActionId;
		this.FUserName = FUserName;
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

	@Column(name = "F_ACTION_TYPE", length = 4)
	public String getFActionType() {
		return this.FActionType;
	}

	public void setFActionType(String FActionType) {
		this.FActionType = FActionType;
	}

	@Column(name = "F_ACTION_OBJECT", length = 100)
	public String getFActionObject() {
		return this.FActionObject;
	}

	public void setFActionObject(String FActionObject) {
		this.FActionObject = FActionObject;
	}

	@Column(name = "F_ACTION_TIME", length = 23)
	public Timestamp getFActionTime() {
		return this.FActionTime;
	}

	public void setFActionTime(Timestamp FActionTime) {
		this.FActionTime = FActionTime;
	}

	@Column(name = "F_ACTION_ID", precision = 9, scale = 0)
	public Integer getFActionId() {
		return this.FActionId;
	}

	public void setFActionId(Integer FActionId) {
		this.FActionId = FActionId;
	}

	@Column(name = "F_USER_NAME", length = 20)
	public String getFUserName() {
		return this.FUserName;
	}

	public void setFUserName(String FUserName) {
		this.FUserName = FUserName;
	}

}