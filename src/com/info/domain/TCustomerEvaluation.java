package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCustomerEvaluation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CustomerEvaluation", schema = "dbo", catalog = "SDOffice")
public class TCustomerEvaluation implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FNumbers;
	private Integer fkTaskId;
	private String FUploadAttachment;
	private Integer FLegendOnId;
	private String FLegendOnName;
	private String FLegendOnTime;
	private String FNote;

	// Constructors

	/** default constructor */
	public TCustomerEvaluation() {
	}

	/** minimal constructor */
	public TCustomerEvaluation(Integer FId, String FNumbers) {
		this.FId = FId;
		this.FNumbers = FNumbers;
	}

	/** full constructor */
	public TCustomerEvaluation(Integer FId, String FNumbers, Integer fkTaskId,
			String FUploadAttachment, Integer FLegendOnId,
			String FLegendOnName, String FLegendOnTime, String FNote) {
		this.FId = FId;
		this.FNumbers = FNumbers;
		this.fkTaskId = fkTaskId;
		this.FUploadAttachment = FUploadAttachment;
		this.FLegendOnId = FLegendOnId;
		this.FLegendOnName = FLegendOnName;
		this.FLegendOnTime = FLegendOnTime;
		this.FNote = FNote;
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

	@Column(name = "F_Numbers", nullable = false, length = 50)
	public String getFNumbers() {
		return this.FNumbers;
	}

	public void setFNumbers(String FNumbers) {
		this.FNumbers = FNumbers;
	}

	@Column(name = "FK_Task_ID")
	public Integer getFkTaskId() {
		return this.fkTaskId;
	}

	public void setFkTaskId(Integer fkTaskId) {
		this.fkTaskId = fkTaskId;
	}

	@Column(name = "F_UploadAttachment", length = 200)
	public String getFUploadAttachment() {
		return this.FUploadAttachment;
	}

	public void setFUploadAttachment(String FUploadAttachment) {
		this.FUploadAttachment = FUploadAttachment;
	}

	@Column(name = "F_LegendOnID")
	public Integer getFLegendOnId() {
		return this.FLegendOnId;
	}

	public void setFLegendOnId(Integer FLegendOnId) {
		this.FLegendOnId = FLegendOnId;
	}

	@Column(name = "F_LegendOnName", length = 30)
	public String getFLegendOnName() {
		return this.FLegendOnName;
	}

	public void setFLegendOnName(String FLegendOnName) {
		this.FLegendOnName = FLegendOnName;
	}

	@Column(name = "F_LegendOnTime", length = 30)
	public String getFLegendOnTime() {
		return this.FLegendOnTime;
	}

	public void setFLegendOnTime(String FLegendOnTime) {
		this.FLegendOnTime = FLegendOnTime;
	}

	@Column(name = "F_Note", length = 500)
	public String getFNote() {
		return this.FNote;
	}

	public void setFNote(String FNote) {
		this.FNote = FNote;
	}

}