package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppLogTemplateDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_LOG_TEMPLATE_DETAIL", schema = "dbo", catalog = "SDOffice")
public class TAppLogTemplateDetail implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkTemplateId;
	private String FColumn;
	private String FColumnName;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public TAppLogTemplateDetail() {
	}

	/** minimal constructor */
	public TAppLogTemplateDetail(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppLogTemplateDetail(Integer FId, Integer fkTemplateId,
			String FColumn, String FColumnName, Integer FSort) {
		this.FId = FId;
		this.fkTemplateId = fkTemplateId;
		this.FColumn = FColumn;
		this.FColumnName = FColumnName;
		this.FSort = FSort;
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

	@Column(name = "FK_TEMPLATE_ID", precision = 9, scale = 0)
	public Integer getFkTemplateId() {
		return this.fkTemplateId;
	}

	public void setFkTemplateId(Integer fkTemplateId) {
		this.fkTemplateId = fkTemplateId;
	}

	@Column(name = "F_COLUMN", length = 100)
	public String getFColumn() {
		return this.FColumn;
	}

	public void setFColumn(String FColumn) {
		this.FColumn = FColumn;
	}

	@Column(name = "F_COLUMN_NAME", length = 200)
	public String getFColumnName() {
		return this.FColumnName;
	}

	public void setFColumnName(String FColumnName) {
		this.FColumnName = FColumnName;
	}

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

}