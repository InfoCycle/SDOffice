package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewProcessProjectSeal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_Process_ProjectSeal", schema = "dbo", catalog = "SDOffice")
public class ViewProcessProjectSeal implements java.io.Serializable {

    // Fields

    private Integer FId;
    private Integer fkTaskId;
    private String FNumbers;
    private String FBidForReason;
    private String FSealContent;
    private Integer FTheApplicantId;
    private String FTheApplicant;
    private String FTheApplicantTime;
    private String FDeptOpinion;
    private String FProductionAco;
    private String FTheGeneralMo;

    // Constructors

    /** default constructor */
    public ViewProcessProjectSeal() {
    }

    /** minimal constructor */
    public ViewProcessProjectSeal(Integer FId) {
	this.FId = FId;
    }

    /** full constructor */
    public ViewProcessProjectSeal(Integer FId, Integer fkTaskId,
	    String FNumbers, String FBidForReason, String FSealContent,
	    Integer FTheApplicantId, String FTheApplicant,
	    String FTheApplicantTime, String FDeptOpinion,
	    String FProductionAco, String FTheGeneralMo) {
	this.FId = FId;
	this.fkTaskId = fkTaskId;
	this.FNumbers = FNumbers;
	this.FBidForReason = FBidForReason;
	this.FSealContent = FSealContent;
	this.FTheApplicantId = FTheApplicantId;
	this.FTheApplicant = FTheApplicant;
	this.FTheApplicantTime = FTheApplicantTime;
	this.FDeptOpinion = FDeptOpinion;
	this.FProductionAco = FProductionAco;
	this.FTheGeneralMo = FTheGeneralMo;
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

    @Column(name = "FK_TaskId")
    public Integer getFkTaskId() {
	return this.fkTaskId;
    }

    public void setFkTaskId(Integer fkTaskId) {
	this.fkTaskId = fkTaskId;
    }

    @Column(name = "F_Numbers", length = 300)
    public String getFNumbers() {
	return this.FNumbers;
    }

    public void setFNumbers(String FNumbers) {
	this.FNumbers = FNumbers;
    }

    @Column(name = "F_BidForReason", length = 800)
    public String getFBidForReason() {
	return this.FBidForReason;
    }

    public void setFBidForReason(String FBidForReason) {
	this.FBidForReason = FBidForReason;
    }

    @Column(name = "F_SealContent", length = 800)
    public String getFSealContent() {
	return this.FSealContent;
    }

    public void setFSealContent(String FSealContent) {
	this.FSealContent = FSealContent;
    }

    @Column(name = "F_TheApplicant_Id")
    public Integer getFTheApplicantId() {
	return this.FTheApplicantId;
    }

    public void setFTheApplicantId(Integer FTheApplicantId) {
	this.FTheApplicantId = FTheApplicantId;
    }

    @Column(name = "F_TheApplicant", length = 30)
    public String getFTheApplicant() {
	return this.FTheApplicant;
    }

    public void setFTheApplicant(String FTheApplicant) {
	this.FTheApplicant = FTheApplicant;
    }

    @Column(name = "F_TheApplicant_Time", length = 30)
    public String getFTheApplicantTime() {
	return this.FTheApplicantTime;
    }

    public void setFTheApplicantTime(String FTheApplicantTime) {
	this.FTheApplicantTime = FTheApplicantTime;
    }

    @Column(name = "F_DeptOpinion", length = 800)
    public String getFDeptOpinion() {
	return this.FDeptOpinion;
    }

    public void setFDeptOpinion(String FDeptOpinion) {
	this.FDeptOpinion = FDeptOpinion;
    }

    @Column(name = "F_ProductionACO", length = 800)
    public String getFProductionAco() {
	return this.FProductionAco;
    }

    public void setFProductionAco(String FProductionAco) {
	this.FProductionAco = FProductionAco;
    }

    @Column(name = "F_TheGeneralMO", length = 800)
    public String getFTheGeneralMo() {
	return this.FTheGeneralMo;
    }

    public void setFTheGeneralMo(String FTheGeneralMo) {
	this.FTheGeneralMo = FTheGeneralMo;
    }

}