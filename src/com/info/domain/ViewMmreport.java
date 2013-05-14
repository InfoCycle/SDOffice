package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewMmreport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_MMReport", schema = "dbo", catalog = "SDOffice")
public class ViewMmreport implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer fkTaskId;

    private Integer FEakerId;

    private String FNumbers;

    private String FEakerName;

    private Integer FProgressId;

    private String FProgress;

    private String FMattersBriefly;

    private String FApplicationSupport;

    private String FQpopionion;

    private String FDeptPrgOpinion;

    private String FTgmgrOpinion;

    private String FNote;

    private String FTaskName;

    private String FServiceCategory;

    private Integer FDeptMgrId;

    private String FDeptMgrName;

    private String FCurrentStep;

    private String FLastStep;

    private String FRecordStep;

    // Constructors

    /** default constructor */
    public ViewMmreport() {
    }

    /** minimal constructor */
    public ViewMmreport(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public ViewMmreport(Integer FId, Integer fkTaskId, Integer FEakerId, String FNumbers, String FEakerName,
            Integer FProgressId, String FProgress, String FMattersBriefly, String FApplicationSupport,
            String FQpopionion, String FDeptPrgOpinion, String FTgmgrOpinion, String FNote, String FTaskName,
            String FServiceCategory, Integer FDeptMgrId, String FDeptMgrName, String FCurrentStep, String FLastStep,
            String FRecordStep) {
        this.FId = FId;
        this.fkTaskId = fkTaskId;
        this.FEakerId = FEakerId;
        this.FNumbers = FNumbers;
        this.FEakerName = FEakerName;
        this.FProgressId = FProgressId;
        this.FProgress = FProgress;
        this.FMattersBriefly = FMattersBriefly;
        this.FApplicationSupport = FApplicationSupport;
        this.FQpopionion = FQpopionion;
        this.FDeptPrgOpinion = FDeptPrgOpinion;
        this.FTgmgrOpinion = FTgmgrOpinion;
        this.FNote = FNote;
        this.FTaskName = FTaskName;
        this.FServiceCategory = FServiceCategory;
        this.FDeptMgrId = FDeptMgrId;
        this.FDeptMgrName = FDeptMgrName;
        this.FCurrentStep = FCurrentStep;
        this.FLastStep = FLastStep;
        this.FRecordStep = FRecordStep;
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

    @Column(name = "FK_Task_ID")
    public Integer getFkTaskId() {
        return this.fkTaskId;
    }

    public void setFkTaskId(Integer fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    @Column(name = "F_Eaker_ID")
    public Integer getFEakerId() {
        return this.FEakerId;
    }

    public void setFEakerId(Integer FEakerId) {
        this.FEakerId = FEakerId;
    }

    @Column(name = "F_Numbers", length = 300)
    public String getFNumbers() {
        return this.FNumbers;
    }

    public void setFNumbers(String FNumbers) {
        this.FNumbers = FNumbers;
    }

    @Column(name = "F_Eaker_Name", length = 30)
    public String getFEakerName() {
        return this.FEakerName;
    }

    public void setFEakerName(String FEakerName) {
        this.FEakerName = FEakerName;
    }

    @Column(name = "F_Progress_ID")
    public Integer getFProgressId() {
        return this.FProgressId;
    }

    public void setFProgressId(Integer FProgressId) {
        this.FProgressId = FProgressId;
    }

    @Column(name = "F_Progress", length = 300)
    public String getFProgress() {
        return this.FProgress;
    }

    public void setFProgress(String FProgress) {
        this.FProgress = FProgress;
    }

    @Column(name = "F_MattersBriefly", length = 800)
    public String getFMattersBriefly() {
        return this.FMattersBriefly;
    }

    public void setFMattersBriefly(String FMattersBriefly) {
        this.FMattersBriefly = FMattersBriefly;
    }

    @Column(name = "F_ApplicationSupport", length = 800)
    public String getFApplicationSupport() {
        return this.FApplicationSupport;
    }

    public void setFApplicationSupport(String FApplicationSupport) {
        this.FApplicationSupport = FApplicationSupport;
    }

    @Column(name = "F_QPOpionion", length = 800)
    public String getFQpopionion() {
        return this.FQpopionion;
    }

    public void setFQpopionion(String FQpopionion) {
        this.FQpopionion = FQpopionion;
    }

    @Column(name = "F_DeptPrgOpinion", length = 800)
    public String getFDeptPrgOpinion() {
        return this.FDeptPrgOpinion;
    }

    public void setFDeptPrgOpinion(String FDeptPrgOpinion) {
        this.FDeptPrgOpinion = FDeptPrgOpinion;
    }

    @Column(name = "F_TGMgrOpinion", length = 800)
    public String getFTgmgrOpinion() {
        return this.FTgmgrOpinion;
    }

    public void setFTgmgrOpinion(String FTgmgrOpinion) {
        this.FTgmgrOpinion = FTgmgrOpinion;
    }

    @Column(name = "F_Note", length = 500)
    public String getFNote() {
        return this.FNote;
    }

    public void setFNote(String FNote) {
        this.FNote = FNote;
    }

    @Column(name = "F_Task_Name", length = 300)
    public String getFTaskName() {
        return this.FTaskName;
    }

    public void setFTaskName(String FTaskName) {
        this.FTaskName = FTaskName;
    }

    @Column(name = "F_Service_Category", length = 200)
    public String getFServiceCategory() {
        return this.FServiceCategory;
    }

    public void setFServiceCategory(String FServiceCategory) {
        this.FServiceCategory = FServiceCategory;
    }

    @Column(name = "F_DeptMgr_ID")
    public Integer getFDeptMgrId() {
        return this.FDeptMgrId;
    }

    public void setFDeptMgrId(Integer FDeptMgrId) {
        this.FDeptMgrId = FDeptMgrId;
    }

    @Column(name = "F_DeptMgr_Name", length = 50)
    public String getFDeptMgrName() {
        return this.FDeptMgrName;
    }

    public void setFDeptMgrName(String FDeptMgrName) {
        this.FDeptMgrName = FDeptMgrName;
    }

    @Column(name = "F_Current_Step", length = 6)
    public String getFCurrentStep() {
        return this.FCurrentStep;
    }

    public void setFCurrentStep(String FCurrentStep) {
        this.FCurrentStep = FCurrentStep;
    }

    @Column(name = "F_Last_Step", length = 6)
    public String getFLastStep() {
        return this.FLastStep;
    }

    public void setFLastStep(String FLastStep) {
        this.FLastStep = FLastStep;
    }

    @Column(name = "F_Record_Step", length = 500)
    public String getFRecordStep() {
        return this.FRecordStep;
    }

    public void setFRecordStep(String FRecordStep) {
        this.FRecordStep = FRecordStep;
    }

}