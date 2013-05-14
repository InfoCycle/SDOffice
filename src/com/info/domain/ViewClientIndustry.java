package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewClientIndustry entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_Client_Industry", schema = "dbo", catalog = "SDOffice")
public class ViewClientIndustry implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FIndustry;
	private String FCord;
	private String FName;
	private String FShortName;
	private String FLegalPerson;
	private String FPrincipar;
	private String FAddress;
	private String FBank;
	private String FAccount;
	private String FZipCord;
	private String FTel;
	private String FFax;
	private String FEmail;
	private String FEntryPeople;
	private Timestamp FEntryTime;
	private String FPossition;
	private String FIndustryName;

	// Constructors

	/** default constructor */
	public ViewClientIndustry() {
	}

	/** minimal constructor */
	public ViewClientIndustry(Integer FId, Integer FIndustry, String FCord,
			String FName, String FLegalPerson, String FPrincipar,
			String FEntryPeople, Timestamp FEntryTime, String FPossition) {
		this.FId = FId;
		this.FIndustry = FIndustry;
		this.FCord = FCord;
		this.FName = FName;
		this.FLegalPerson = FLegalPerson;
		this.FPrincipar = FPrincipar;
		this.FEntryPeople = FEntryPeople;
		this.FEntryTime = FEntryTime;
		this.FPossition = FPossition;
	}

	/** full constructor */
	public ViewClientIndustry(Integer FId, Integer FIndustry, String FCord,
			String FName, String FShortName, String FLegalPerson,
			String FPrincipar, String FAddress, String FBank, String FAccount,
			String FZipCord, String FTel, String FFax, String FEmail,
			String FEntryPeople, Timestamp FEntryTime, String FPossition,
			String FIndustryName) {
		this.FId = FId;
		this.FIndustry = FIndustry;
		this.FCord = FCord;
		this.FName = FName;
		this.FShortName = FShortName;
		this.FLegalPerson = FLegalPerson;
		this.FPrincipar = FPrincipar;
		this.FAddress = FAddress;
		this.FBank = FBank;
		this.FAccount = FAccount;
		this.FZipCord = FZipCord;
		this.FTel = FTel;
		this.FFax = FFax;
		this.FEmail = FEmail;
		this.FEntryPeople = FEntryPeople;
		this.FEntryTime = FEntryTime;
		this.FPossition = FPossition;
		this.FIndustryName = FIndustryName;
	}

	// Property accessors
	@Id
	@Column(name = "F_Id", nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Industry", nullable = false)
	public Integer getFIndustry() {
		return this.FIndustry;
	}

	public void setFIndustry(Integer FIndustry) {
		this.FIndustry = FIndustry;
	}

	@Column(name = "F_Cord", nullable = false, length = 8)
	public String getFCord() {
		return this.FCord;
	}

	public void setFCord(String FCord) {
		this.FCord = FCord;
	}

	@Column(name = "F_Name", nullable = false, length = 300)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_Short_Name", length = 150)
	public String getFShortName() {
		return this.FShortName;
	}

	public void setFShortName(String FShortName) {
		this.FShortName = FShortName;
	}

	@Column(name = "F_LegalPerson", nullable = false, length = 8)
	public String getFLegalPerson() {
		return this.FLegalPerson;
	}

	public void setFLegalPerson(String FLegalPerson) {
		this.FLegalPerson = FLegalPerson;
	}

	@Column(name = "F_Principar", nullable = false, length = 8)
	public String getFPrincipar() {
		return this.FPrincipar;
	}

	public void setFPrincipar(String FPrincipar) {
		this.FPrincipar = FPrincipar;
	}

	@Column(name = "F_Address", length = 300)
	public String getFAddress() {
		return this.FAddress;
	}

	public void setFAddress(String FAddress) {
		this.FAddress = FAddress;
	}

	@Column(name = "F_Bank", length = 300)
	public String getFBank() {
		return this.FBank;
	}

	public void setFBank(String FBank) {
		this.FBank = FBank;
	}

	@Column(name = "F_Account", length = 20)
	public String getFAccount() {
		return this.FAccount;
	}

	public void setFAccount(String FAccount) {
		this.FAccount = FAccount;
	}

	@Column(name = "F_ZipCord", length = 6)
	public String getFZipCord() {
		return this.FZipCord;
	}

	public void setFZipCord(String FZipCord) {
		this.FZipCord = FZipCord;
	}

	@Column(name = "F_Tel", length = 20)
	public String getFTel() {
		return this.FTel;
	}

	public void setFTel(String FTel) {
		this.FTel = FTel;
	}

	@Column(name = "F_Fax", length = 20)
	public String getFFax() {
		return this.FFax;
	}

	public void setFFax(String FFax) {
		this.FFax = FFax;
	}

	@Column(name = "F_Email", length = 20)
	public String getFEmail() {
		return this.FEmail;
	}

	public void setFEmail(String FEmail) {
		this.FEmail = FEmail;
	}

	@Column(name = "F_EntryPeople", nullable = false, length = 8)
	public String getFEntryPeople() {
		return this.FEntryPeople;
	}

	public void setFEntryPeople(String FEntryPeople) {
		this.FEntryPeople = FEntryPeople;
	}

	@Column(name = "F_EntryTime", nullable = false, length = 23)
	public Timestamp getFEntryTime() {
		return this.FEntryTime;
	}

	public void setFEntryTime(Timestamp FEntryTime) {
		this.FEntryTime = FEntryTime;
	}

	@Column(name = "F_Possition", nullable = false, length = 10)
	public String getFPossition() {
		return this.FPossition;
	}

	public void setFPossition(String FPossition) {
		this.FPossition = FPossition;
	}

	@Column(name = "F_Industry_Name", length = 200)
	public String getFIndustryName() {
		return this.FIndustryName;
	}

	public void setFIndustryName(String FIndustryName) {
		this.FIndustryName = FIndustryName;
	}

}