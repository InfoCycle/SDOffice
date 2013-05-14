package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppSeq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_SEQ", schema = "dbo", catalog = "SDOffice")
public class TAppSeq implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FSeqName;
	private Integer FCurrentval;

	// Constructors

	/** default constructor */
	public TAppSeq() {
	}

	/** minimal constructor */
	public TAppSeq(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppSeq(Integer FId, String FSeqName, Integer FCurrentval) {
		this.FId = FId;
		this.FSeqName = FSeqName;
		this.FCurrentval = FCurrentval;
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

	@Column(name = "F_SEQ_NAME", length = 100)
	public String getFSeqName() {
		return this.FSeqName;
	}

	public void setFSeqName(String FSeqName) {
		this.FSeqName = FSeqName;
	}

	@Column(name = "F_CURRENTVAL", precision = 9, scale = 0)
	public Integer getFCurrentval() {
		return this.FCurrentval;
	}

	public void setFCurrentval(Integer FCurrentval) {
		this.FCurrentval = FCurrentval;
	}

}