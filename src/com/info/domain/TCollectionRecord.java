package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCollectionRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Collection_Record", schema = "dbo", catalog = "SDOffice")
public class TCollectionRecord implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FInvoiceId;
	private Double FMoney;
	private String FData;
	private String FMan;
	private Integer FRecordUserId;
	private String FRecordUserName;
	private String FRecordUserTime;

	// Constructors

	/** default constructor */
	public TCollectionRecord() {
	}

	/** minimal constructor */
	public TCollectionRecord(Integer FId, Integer FInvoiceId) {
		this.FId = FId;
		this.FInvoiceId = FInvoiceId;
	}

	/** full constructor */
	public TCollectionRecord(Integer FId, Integer FInvoiceId, Double FMoney,
			String FData, String FMan, Integer FRecordUserId,
			String FRecordUserName, String FRecordUserTime) {
		this.FId = FId;
		this.FInvoiceId = FInvoiceId;
		this.FMoney = FMoney;
		this.FData = FData;
		this.FMan = FMan;
		this.FRecordUserId = FRecordUserId;
		this.FRecordUserName = FRecordUserName;
		this.FRecordUserTime = FRecordUserTime;
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

	@Column(name = "F_InvoiceId", nullable = false)
	public Integer getFInvoiceId() {
		return this.FInvoiceId;
	}

	public void setFInvoiceId(Integer FInvoiceId) {
		this.FInvoiceId = FInvoiceId;
	}

	@Column(name = "F_Money", scale = 4)
	public Double getFMoney() {
		return this.FMoney;
	}

	public void setFMoney(Double FMoney) {
		this.FMoney = FMoney;
	}

	@Column(name = "F_Data", length = 30)
	public String getFData() {
		return this.FData;
	}

	public void setFData(String FData) {
		this.FData = FData;
	}

	@Column(name = "F_Man", length = 30)
	public String getFMan() {
		return this.FMan;
	}

	public void setFMan(String FMan) {
		this.FMan = FMan;
	}

	@Column(name = "F_Record_UserId")
	public Integer getFRecordUserId() {
		return this.FRecordUserId;
	}

	public void setFRecordUserId(Integer FRecordUserId) {
		this.FRecordUserId = FRecordUserId;
	}

	@Column(name = "F_Record_UserName", length = 30)
	public String getFRecordUserName() {
		return this.FRecordUserName;
	}

	public void setFRecordUserName(String FRecordUserName) {
		this.FRecordUserName = FRecordUserName;
	}

	@Column(name = "F_Record_UserTime", length = 30)
	public String getFRecordUserTime() {
		return this.FRecordUserTime;
	}

	public void setFRecordUserTime(String FRecordUserTime) {
		this.FRecordUserTime = FRecordUserTime;
	}

}