package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TConsultingResultsFiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ConsultingResultsFiles", schema = "dbo", catalog = "SDOffice")
public class TConsultingResultsFiles implements java.io.Serializable {

    // Fields

    private Integer FId;
    private Integer fkTaskId;
    private String FFileNumbers;
    private String FCompletionTime;
    private Integer FProjAchtId;
    private String FProjAchtType;
    private String FFnpvp;
    private String FFilingContents;
    private String FPmdcro;
    private Integer FFilingPeopleId;
    private String FFilingPeople;
    private String FFilingTime;
    private Integer FReceivedId;
    private String FReceived;
    private String FReceivingTime;
    private String FFilingAddress;

    // Constructors

    /** default constructor */
    public TConsultingResultsFiles() {
    }

    /** minimal constructor */
    public TConsultingResultsFiles(Integer FId) {
	this.FId = FId;
    }

    /** full constructor */
    public TConsultingResultsFiles(Integer FId, Integer fkTaskId,
	    String FFileNumbers, String FCompletionTime, Integer FProjAchtId,
	    String FProjAchtType, String FFnpvp, String FFilingContents,
	    String FPmdcro, Integer FFilingPeopleId, String FFilingPeople,
	    String FFilingTime, Integer FReceivedId, String FReceived,
	    String FReceivingTime, String FFilingAddress) {
	this.FId = FId;
	this.fkTaskId = fkTaskId;
	this.FFileNumbers = FFileNumbers;
	this.FCompletionTime = FCompletionTime;
	this.FProjAchtId = FProjAchtId;
	this.FProjAchtType = FProjAchtType;
	this.FFnpvp = FFnpvp;
	this.FFilingContents = FFilingContents;
	this.FPmdcro = FPmdcro;
	this.FFilingPeopleId = FFilingPeopleId;
	this.FFilingPeople = FFilingPeople;
	this.FFilingTime = FFilingTime;
	this.FReceivedId = FReceivedId;
	this.FReceived = FReceived;
	this.FReceivingTime = FReceivingTime;
	this.FFilingAddress = FFilingAddress;
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

    @Column(name = "FK_Task_ID")
    public Integer getFkTaskId() {
	return this.fkTaskId;
    }

    public void setFkTaskId(Integer fkTaskId) {
	this.fkTaskId = fkTaskId;
    }

    @Column(name = "F_FileNumbers", length = 50)
    public String getFFileNumbers() {
	return this.FFileNumbers;
    }

    public void setFFileNumbers(String FFileNumbers) {
	this.FFileNumbers = FFileNumbers;
    }

    @Column(name = "F_CompletionTime", length = 50)
    public String getFCompletionTime() {
	return this.FCompletionTime;
    }

    public void setFCompletionTime(String FCompletionTime) {
	this.FCompletionTime = FCompletionTime;
    }

    @Column(name = "F_ProjAcht_Id")
    public Integer getFProjAchtId() {
	return this.FProjAchtId;
    }

    public void setFProjAchtId(Integer FProjAchtId) {
	this.FProjAchtId = FProjAchtId;
    }

    @Column(name = "F_ProjAcht_Type", length = 50)
    public String getFProjAchtType() {
	return this.FProjAchtType;
    }

    public void setFProjAchtType(String FProjAchtType) {
	this.FProjAchtType = FProjAchtType;
    }

    @Column(name = "F_FNPVP", length = 30)
    public String getFFnpvp() {
	return this.FFnpvp;
    }

    public void setFFnpvp(String FFnpvp) {
	this.FFnpvp = FFnpvp;
    }

    @Column(name = "F_FilingContents", length = 500)
    public String getFFilingContents() {
	return this.FFilingContents;
    }

    public void setFFilingContents(String FFilingContents) {
	this.FFilingContents = FFilingContents;
    }

    @Column(name = "F_PMDCRO", length = 800)
    public String getFPmdcro() {
	return this.FPmdcro;
    }

    public void setFPmdcro(String FPmdcro) {
	this.FPmdcro = FPmdcro;
    }

    @Column(name = "F_FilingPeople_Id")
    public Integer getFFilingPeopleId() {
	return this.FFilingPeopleId;
    }

    public void setFFilingPeopleId(Integer FFilingPeopleId) {
	this.FFilingPeopleId = FFilingPeopleId;
    }

    @Column(name = "F_FilingPeople", length = 50)
    public String getFFilingPeople() {
	return this.FFilingPeople;
    }

    public void setFFilingPeople(String FFilingPeople) {
	this.FFilingPeople = FFilingPeople;
    }

    @Column(name = "F_FilingTime", length = 30)
    public String getFFilingTime() {
	return this.FFilingTime;
    }

    public void setFFilingTime(String FFilingTime) {
	this.FFilingTime = FFilingTime;
    }

    @Column(name = "F_Received_Id")
    public Integer getFReceivedId() {
	return this.FReceivedId;
    }

    public void setFReceivedId(Integer FReceivedId) {
	this.FReceivedId = FReceivedId;
    }

    @Column(name = "F_Received", length = 30)
    public String getFReceived() {
	return this.FReceived;
    }

    public void setFReceived(String FReceived) {
	this.FReceived = FReceived;
    }

    @Column(name = "F_ReceivingTime", length = 30)
    public String getFReceivingTime() {
	return this.FReceivingTime;
    }

    public void setFReceivingTime(String FReceivingTime) {
	this.FReceivingTime = FReceivingTime;
    }

    @Column(name = "F_FilingAddress", length = 800)
    public String getFFilingAddress() {
	return this.FFilingAddress;
    }

    public void setFFilingAddress(String FFilingAddress) {
	this.FFilingAddress = FFilingAddress;
    }

}