package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TReceipt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Receipt", schema = "dbo", catalog = "SDOffice")
public class TReceipt implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FInvoiceId;
	private Integer FOrder;
	private Double FReceiptMoney;
	private String FReceiptTime;
	private String FReceiptPayee;
	private String FRecordUserName;
	private String FRecordUserTime;

	// Constructors

	/** default constructor */
	public TReceipt() {
	}

	/** minimal constructor */
	public TReceipt(Integer FId, Integer FInvoiceId) {
		this.FId = FId;
		this.FInvoiceId = FInvoiceId;
	}

	/** full constructor */
	public TReceipt(Integer FId, Integer FInvoiceId, Integer FOrder,
			Double FReceiptMoney, String FReceiptTime, String FReceiptPayee,
			String FRecordUserName, String FRecordUserTime) {
		this.FId = FId;
		this.FInvoiceId = FInvoiceId;
		this.FOrder = FOrder;
		this.FReceiptMoney = FReceiptMoney;
		this.FReceiptTime = FReceiptTime;
		this.FReceiptPayee = FReceiptPayee;
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

	@Column(name = "F_Invoice_Id", nullable = false)
	public Integer getFInvoiceId() {
		return this.FInvoiceId;
	}

	public void setFInvoiceId(Integer FInvoiceId) {
		this.FInvoiceId = FInvoiceId;
	}

	@Column(name = "F_Order")
	public Integer getFOrder() {
		return this.FOrder;
	}

	public void setFOrder(Integer FOrder) {
		this.FOrder = FOrder;
	}

	@Column(name = "F_Receipt_Money", scale = 4)
	public Double getFReceiptMoney() {
		return this.FReceiptMoney;
	}

	public void setFReceiptMoney(Double FReceiptMoney) {
		this.FReceiptMoney = FReceiptMoney;
	}

	@Column(name = "F_Receipt_Time", length = 30)
	public String getFReceiptTime() {
		return this.FReceiptTime;
	}

	public void setFReceiptTime(String FReceiptTime) {
		this.FReceiptTime = FReceiptTime;
	}

	@Column(name = "F_Receipt_Payee", length = 30)
	public String getFReceiptPayee() {
		return this.FReceiptPayee;
	}

	public void setFReceiptPayee(String FReceiptPayee) {
		this.FReceiptPayee = FReceiptPayee;
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