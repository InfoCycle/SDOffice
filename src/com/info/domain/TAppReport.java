package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_REPORT", schema = "dbo", catalog = "SDOffice")
public class TAppReport implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FReportName;
	private String FReportlet;
	private Timestamp FCreatedate;

	// Constructors

	/** default constructor */
	public TAppReport() {
	}

	/** minimal constructor */
	public TAppReport(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppReport(Integer FId, String FReportName, String FReportlet,
			Timestamp FCreatedate) {
		this.FId = FId;
		this.FReportName = FReportName;
		this.FReportlet = FReportlet;
		this.FCreatedate = FCreatedate;
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

	@Column(name = "F_REPORT_NAME", length = 100)
	public String getFReportName() {
		return this.FReportName;
	}

	public void setFReportName(String FReportName) {
		this.FReportName = FReportName;
	}

	@Column(name = "F_REPORTLET", length = 200)
	public String getFReportlet() {
		return this.FReportlet;
	}

	public void setFReportlet(String FReportlet) {
		this.FReportlet = FReportlet;
	}

	@Column(name = "F_CREATEDATE", length = 23)
	public Timestamp getFCreatedate() {
		return this.FCreatedate;
	}

	public void setFCreatedate(Timestamp FCreatedate) {
		this.FCreatedate = FCreatedate;
	}

}