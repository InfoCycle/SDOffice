package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_CODE", schema = "dbo", catalog = "SDOffice")
public class TAppCode implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FCode;
	private String FCodeText;
	private Integer fkCodeTypeId;
	private Integer FState;
	private Integer FSort;
	private Timestamp FCreateTime;

	// Constructors

	/** default constructor */
	public TAppCode() {
	}

	/** minimal constructor */
	public TAppCode(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppCode(Integer FId, String FCode, String FCodeText,
			Integer fkCodeTypeId, Integer FState, Integer FSort,
			Timestamp FCreateTime) {
		this.FId = FId;
		this.FCode = FCode;
		this.FCodeText = FCodeText;
		this.fkCodeTypeId = fkCodeTypeId;
		this.FState = FState;
		this.FSort = FSort;
		this.FCreateTime = FCreateTime;
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

	@Column(name = "F_CODE", length = 20)
	public String getFCode() {
		return this.FCode;
	}

	public void setFCode(String FCode) {
		this.FCode = FCode;
	}

	@Column(name = "F_CODE_TEXT", length = 50)
	public String getFCodeText() {
		return this.FCodeText;
	}

	public void setFCodeText(String FCodeText) {
		this.FCodeText = FCodeText;
	}

	@Column(name = "FK_CODE_TYPE_ID", precision = 9, scale = 0)
	public Integer getFkCodeTypeId() {
		return this.fkCodeTypeId;
	}

	public void setFkCodeTypeId(Integer fkCodeTypeId) {
		this.fkCodeTypeId = fkCodeTypeId;
	}

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_CREATE_TIME", length = 23)
	public Timestamp getFCreateTime() {
		return this.FCreateTime;
	}

	public void setFCreateTime(Timestamp FCreateTime) {
		this.FCreateTime = FCreateTime;
	}

}