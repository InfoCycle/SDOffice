package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppLogDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_LOG_DETAIL", schema = "dbo", catalog = "SDOffice")
public class TAppLogDetail implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkLogId;
	private String FColumn;
	private String FColumnName;
	private String FSoruceValue;
	private String FCurrentValue;

	// Constructors

	/** default constructor */
	public TAppLogDetail() {
	}

	/** minimal constructor */
	public TAppLogDetail(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppLogDetail(Integer FId, Integer fkLogId, String FColumn,
			String FColumnName, String FSoruceValue, String FCurrentValue) {
		this.FId = FId;
		this.fkLogId = fkLogId;
		this.FColumn = FColumn;
		this.FColumnName = FColumnName;
		this.FSoruceValue = FSoruceValue;
		this.FCurrentValue = FCurrentValue;
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

	@Column(name = "FK_LOG_ID", precision = 9, scale = 0)
	public Integer getFkLogId() {
		return this.fkLogId;
	}

	public void setFkLogId(Integer fkLogId) {
		this.fkLogId = fkLogId;
	}

	@Column(name = "F_COLUMN", length = 50)
	public String getFColumn() {
		return this.FColumn;
	}

	public void setFColumn(String FColumn) {
		this.FColumn = FColumn;
	}

	@Column(name = "F_COLUMN_NAME", length = 50)
	public String getFColumnName() {
		return this.FColumnName;
	}

	public void setFColumnName(String FColumnName) {
		this.FColumnName = FColumnName;
	}

	@Column(name = "F_SORUCE_VALUE", length = 200)
	public String getFSoruceValue() {
		return this.FSoruceValue;
	}

	public void setFSoruceValue(String FSoruceValue) {
		this.FSoruceValue = FSoruceValue;
	}

	@Column(name = "F_CURRENT_VALUE", length = 200)
	public String getFCurrentValue() {
		return this.FCurrentValue;
	}

	public void setFCurrentValue(String FCurrentValue) {
		this.FCurrentValue = FCurrentValue;
	}

}