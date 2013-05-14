package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewArchiveTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_Archive_Task", schema = "dbo", catalog = "SDOffice")
public class ViewArchiveTask implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTitle;
	private String FBusinessCategory;
	private String FArchiveTime;
	private Integer FUnitId;

	// Constructors

	/** default constructor */
	public ViewArchiveTask() {
	}

	/** minimal constructor */
	public ViewArchiveTask(Integer FId, String FBusinessCategory) {
		this.FId = FId;
		this.FBusinessCategory = FBusinessCategory;
	}

	/** full constructor */
	public ViewArchiveTask(Integer FId, String FTitle,
			String FBusinessCategory, String FArchiveTime, Integer FUnitId) {
		this.FId = FId;
		this.FTitle = FTitle;
		this.FBusinessCategory = FBusinessCategory;
		this.FArchiveTime = FArchiveTime;
		this.FUnitId = FUnitId;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_TITLE", length = 800)
	public String getFTitle() {
		return this.FTitle;
	}

	public void setFTitle(String FTitle) {
		this.FTitle = FTitle;
	}

	@Column(name = "F_Business_Category", nullable = false, length = 200)
	public String getFBusinessCategory() {
		return this.FBusinessCategory;
	}

	public void setFBusinessCategory(String FBusinessCategory) {
		this.FBusinessCategory = FBusinessCategory;
	}

	@Column(name = "F_Archive_Time", length = 30)
	public String getFArchiveTime() {
		return this.FArchiveTime;
	}

	public void setFArchiveTime(String FArchiveTime) {
		this.FArchiveTime = FArchiveTime;
	}

	@Column(name = "F_UnitId")
	public Integer getFUnitId() {
		return this.FUnitId;
	}

	public void setFUnitId(Integer FUnitId) {
		this.FUnitId = FUnitId;
	}

}