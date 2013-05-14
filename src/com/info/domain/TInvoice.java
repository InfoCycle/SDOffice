package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TInvoice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Invoice", schema = "dbo", catalog = "SDOffice")
public class TInvoice implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FCollectionId;
	private String FInvoiceNumber;
	private Double FInvoiceMoney;
	private String FInvoiceTime;
	private Double FReceived;
	private Double FDifference;
	private String FRecordUserName;
	private String FRecordUserTime;

	// Constructors

	/** default constructor */
	public TInvoice() {
	}

	/** minimal constructor */
	public TInvoice(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TInvoice(Integer FId, Integer FCollectionId, String FInvoiceNumber,
			Double FInvoiceMoney, String FInvoiceTime, Double FReceived,
			Double FDifference, String FRecordUserName, String FRecordUserTime) {
		this.FId = FId;
		this.FCollectionId = FCollectionId;
		this.FInvoiceNumber = FInvoiceNumber;
		this.FInvoiceMoney = FInvoiceMoney;
		this.FInvoiceTime = FInvoiceTime;
		this.FReceived = FReceived;
		this.FDifference = FDifference;
		this.FRecordUserName = FRecordUserName;
		this.FRecordUserTime = FRecordUserTime;
	}

	// Property accessors
	@Id
	@Column(name = "F_Id", unique = true, nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Collection_Id")
	public Integer getFCollectionId() {
		return this.FCollectionId;
	}

	public void setFCollectionId(Integer FCollectionId) {
		this.FCollectionId = FCollectionId;
	}

	@Column(name = "F_Invoice_Number", length = 10)
	public String getFInvoiceNumber() {
		return this.FInvoiceNumber;
	}

	public void setFInvoiceNumber(String FInvoiceNumber) {
		this.FInvoiceNumber = FInvoiceNumber;
	}

	@Column(name = "F_Invoice_Money", scale = 4)
	public Double getFInvoiceMoney() {
		return this.FInvoiceMoney;
	}

	public void setFInvoiceMoney(Double FInvoiceMoney) {
		this.FInvoiceMoney = FInvoiceMoney;
	}

	@Column(name = "F_Invoice_Time", length = 30)
	public String getFInvoiceTime() {
		return this.FInvoiceTime;
	}

	public void setFInvoiceTime(String FInvoiceTime) {
		this.FInvoiceTime = FInvoiceTime;
	}

	@Column(name = "F_Received", scale = 4)
	public Double getFReceived() {
		return this.FReceived;
	}

	public void setFReceived(Double FReceived) {
		this.FReceived = FReceived;
	}

	@Column(name = "F_Difference", scale = 4)
	public Double getFDifference() {
		return this.FDifference;
	}

	public void setFDifference(Double FDifference) {
		this.FDifference = FDifference;
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