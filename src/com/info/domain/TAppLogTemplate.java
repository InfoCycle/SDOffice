package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppLogTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_LOG_TEMPLATE", schema = "dbo", catalog = "SDOffice")
public class TAppLogTemplate implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTable;
	private String FTableName;
	private String FActionType;
	private Integer FState;
	private Timestamp FDate;

	// Constructors

	/** default constructor */
	public TAppLogTemplate() {
	}

	/** minimal constructor */
	public TAppLogTemplate(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppLogTemplate(Integer FId, String FTable, String FTableName,
			String FActionType, Integer FState, Timestamp FDate) {
		this.FId = FId;
		this.FTable = FTable;
		this.FTableName = FTableName;
		this.FActionType = FActionType;
		this.FState = FState;
		this.FDate = FDate;
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

	@Column(name = "F_TABLE", length = 100)
	public String getFTable() {
		return this.FTable;
	}

	public void setFTable(String FTable) {
		this.FTable = FTable;
	}

	@Column(name = "F_TABLE_NAME", length = 200)
	public String getFTableName() {
		return this.FTableName;
	}

	public void setFTableName(String FTableName) {
		this.FTableName = FTableName;
	}

	@Column(name = "F_ACTION_TYPE", length = 20)
	public String getFActionType() {
		return this.FActionType;
	}

	public void setFActionType(String FActionType) {
		this.FActionType = FActionType;
	}

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_DATE", length = 23)
	public Timestamp getFDate() {
		return this.FDate;
	}

	public void setFDate(Timestamp FDate) {
		this.FDate = FDate;
	}

}