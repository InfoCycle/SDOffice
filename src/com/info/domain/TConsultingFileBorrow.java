package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TConsultingFileBorrow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ConsultingFileBorrow", schema = "dbo", catalog = "SDOffice")
public class TConsultingFileBorrow implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FBorrowFiles;
	private String FBorrowReason;
	private String FBooks;
	private String FBorrower;
	private String FProductionChief;
	private String FReturning;
	private String FReturnedPeople;
	private String FReturnedPeopleTime;
	private String FReceivedBy;
	private String FReceivedByTime;
	private String FNote;

	// Constructors

	/** default constructor */
	public TConsultingFileBorrow() {
	}

	/** minimal constructor */
	public TConsultingFileBorrow(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TConsultingFileBorrow(Integer FId, String FBorrowFiles,
			String FBorrowReason, String FBooks, String FBorrower,
			String FProductionChief, String FReturning, String FReturnedPeople,
			String FReturnedPeopleTime, String FReceivedBy,
			String FReceivedByTime, String FNote) {
		this.FId = FId;
		this.FBorrowFiles = FBorrowFiles;
		this.FBorrowReason = FBorrowReason;
		this.FBooks = FBooks;
		this.FBorrower = FBorrower;
		this.FProductionChief = FProductionChief;
		this.FReturning = FReturning;
		this.FReturnedPeople = FReturnedPeople;
		this.FReturnedPeopleTime = FReturnedPeopleTime;
		this.FReceivedBy = FReceivedBy;
		this.FReceivedByTime = FReceivedByTime;
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

	@Column(name = "F_BorrowFiles", length = 500)
	public String getFBorrowFiles() {
		return this.FBorrowFiles;
	}

	public void setFBorrowFiles(String FBorrowFiles) {
		this.FBorrowFiles = FBorrowFiles;
	}

	@Column(name = "F_BorrowReason", length = 500)
	public String getFBorrowReason() {
		return this.FBorrowReason;
	}

	public void setFBorrowReason(String FBorrowReason) {
		this.FBorrowReason = FBorrowReason;
	}

	@Column(name = "F_Books", length = 500)
	public String getFBooks() {
		return this.FBooks;
	}

	public void setFBooks(String FBooks) {
		this.FBooks = FBooks;
	}

	@Column(name = "F_Borrower", length = 30)
	public String getFBorrower() {
		return this.FBorrower;
	}

	public void setFBorrower(String FBorrower) {
		this.FBorrower = FBorrower;
	}

	@Column(name = "F_ProductionChief", length = 500)
	public String getFProductionChief() {
		return this.FProductionChief;
	}

	public void setFProductionChief(String FProductionChief) {
		this.FProductionChief = FProductionChief;
	}

	@Column(name = "F_Returning", length = 500)
	public String getFReturning() {
		return this.FReturning;
	}

	public void setFReturning(String FReturning) {
		this.FReturning = FReturning;
	}

	@Column(name = "F_ReturnedPeople", length = 30)
	public String getFReturnedPeople() {
		return this.FReturnedPeople;
	}

	public void setFReturnedPeople(String FReturnedPeople) {
		this.FReturnedPeople = FReturnedPeople;
	}

	@Column(name = "F_ReturnedPeople_Time", length = 30)
	public String getFReturnedPeopleTime() {
		return this.FReturnedPeopleTime;
	}

	public void setFReturnedPeopleTime(String FReturnedPeopleTime) {
		this.FReturnedPeopleTime = FReturnedPeopleTime;
	}

	@Column(name = "F_ReceivedBy", length = 30)
	public String getFReceivedBy() {
		return this.FReceivedBy;
	}

	public void setFReceivedBy(String FReceivedBy) {
		this.FReceivedBy = FReceivedBy;
	}

	@Column(name = "F_ReceivedBy_Time", length = 30)
	public String getFReceivedByTime() {
		return this.FReceivedByTime;
	}

	public void setFReceivedByTime(String FReceivedByTime) {
		this.FReceivedByTime = FReceivedByTime;
	}

	@Column(name = "F_Note", length = 800)
	public String getFNote() {
		return this.FNote;
	}

	public void setFNote(String FNote) {
		this.FNote = FNote;
	}

}