package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TMajorMattersReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MajorMattersReport", schema = "dbo", catalog = "SDOffice")
public class TMajorMattersReport implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer fkTaskId;

    private String FNumbers;

    private Integer FEakerId;

    private String FEakerName;

    private Integer FProgressId;

    private String FProgress;

    private String FMattersBriefly;

    private String FApplicationSupport;

    private String FQpopionion;

    private String FDeptPrgOpinion;

    private Integer FDeptPrgOpinionManId;

    private String FDeptPrgOpinionMan;

    private String FDeptPrgOpinionTime;

    private String FTgmgrOpinion;

    private Integer FTgmgrOpinionManId;

    private String FTgmgrOpinionMan;

    private String FTgmgrOpinionTime;

    private String FNote;

    private String FCurrentStep;

    private String FLastStep;

    private String FRecordStep;

    // Constructors

    /** default constructor */
    public TMajorMattersReport() {
    }

    /** minimal constructor */
    public TMajorMattersReport(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TMajorMattersReport(Integer FId, Integer fkTaskId, String FNumbers, Integer FEakerId, String FEakerName,
            Integer FProgressId, String FProgress, String FMattersBriefly, String FApplicationSupport,
            String FQpopionion, String FDeptPrgOpinion, Integer FDeptPrgOpinionManId, String FDeptPrgOpinionMan,
            String FDeptPrgOpinionTime, String FTgmgrOpinion, Integer FTgmgrOpinionManId, String FTgmgrOpinionMan,
            String FTgmgrOpinionTime, String FNote, String FCurrentStep, String FLastStep, String FRecordStep) {
        this.FId = FId;
        this.fkTaskId = fkTaskId;
        this.FNumbers = FNumbers;
        this.FEakerId = FEakerId;
        this.FEakerName = FEakerName;
        this.FProgressId = FProgressId;
        this.FProgress = FProgress;
        this.FMattersBriefly = FMattersBriefly;
        this.FApplicationSupport = FApplicationSupport;
        this.FQpopionion = FQpopionion;
        this.FDeptPrgOpinion = FDeptPrgOpinion;
        this.FDeptPrgOpinionManId = FDeptPrgOpinionManId;
        this.FDeptPrgOpinionMan = FDeptPrgOpinionMan;
        this.FDeptPrgOpinionTime = FDeptPrgOpinionTime;
        this.FTgmgrOpinion = FTgmgrOpinion;
        this.FTgmgrOpinionManId = FTgmgrOpinionManId;
        this.FTgmgrOpinionMan = FTgmgrOpinionMan;
        this.FTgmgrOpinionTime = FTgmgrOpinionTime;
        this.FNote = FNote;
        this.FCurrentStep = FCurrentStep;
        this.FLastStep = FLastStep;
        this.FRecordStep = FRecordStep;
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

    @Column(name = "F_Numbers", length = 300)
    public String getFNumbers() {
        return this.FNumbers;
    }

    public void setFNumbers(String FNumbers) {
        this.FNumbers = FNumbers;
    }

    @Column(name = "F_Eaker_ID")
    public Integer getFEakerId() {
        return this.FEakerId;
    }

    public void setFEakerId(Integer FEakerId) {
        this.FEakerId = FEakerId;
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

    @Column(name = "F_MattersBriefly", length = 2000)
    public String getFMattersBriefly() {
        return this.FMattersBriefly;
    }

    public void setFMattersBriefly(String FMattersBriefly) {
        this.FMattersBriefly = FMattersBriefly;
    }

    @Column(name = "F_ApplicationSupport", length = 2000)
    public String getFApplicationSupport() {
        return this.FApplicationSupport;
    }

    public void setFApplicationSupport(String FApplicationSupport) {
        this.FApplicationSupport = FApplicationSupport;
    }

    @Column(name = "F_QPOpionion", length = 2000)
    public String getFQpopionion() {
        return this.FQpopionion;
    }

    public void setFQpopionion(String FQpopionion) {
        this.FQpopionion = FQpopionion;
    }

    @Column(name = "F_DeptPrgOpinion", length = 2000)
    public String getFDeptPrgOpinion() {
        return this.FDeptPrgOpinion;
    }

    public void setFDeptPrgOpinion(String FDeptPrgOpinion) {
        this.FDeptPrgOpinion = FDeptPrgOpinion;
    }

    @Column(name = "F_DeptPrgOpinion_ManID")
    public Integer getFDeptPrgOpinionManId() {
        return this.FDeptPrgOpinionManId;
    }

    public void setFDeptPrgOpinionManId(Integer FDeptPrgOpinionManId) {
        this.FDeptPrgOpinionManId = FDeptPrgOpinionManId;
    }

    @Column(name = "F_DeptPrgOpinion_Man", length = 50)
    public String getFDeptPrgOpinionMan() {
        return this.FDeptPrgOpinionMan;
    }

    public void setFDeptPrgOpinionMan(String FDeptPrgOpinionMan) {
        this.FDeptPrgOpinionMan = FDeptPrgOpinionMan;
    }

    @Column(name = "F_DeptPrgOpinion_Time", length = 50)
    public String getFDeptPrgOpinionTime() {
        return this.FDeptPrgOpinionTime;
    }

    public void setFDeptPrgOpinionTime(String FDeptPrgOpinionTime) {
        this.FDeptPrgOpinionTime = FDeptPrgOpinionTime;
    }

    @Column(name = "F_TGMgrOpinion", length = 2000)
    public String getFTgmgrOpinion() {
        return this.FTgmgrOpinion;
    }

    public void setFTgmgrOpinion(String FTgmgrOpinion) {
        this.FTgmgrOpinion = FTgmgrOpinion;
    }

    @Column(name = "F_TGMgrOpinion_ManID")
    public Integer getFTgmgrOpinionManId() {
        return this.FTgmgrOpinionManId;
    }

    public void setFTgmgrOpinionManId(Integer FTgmgrOpinionManId) {
        this.FTgmgrOpinionManId = FTgmgrOpinionManId;
    }

    @Column(name = "F_TGMgrOpinion_Man", length = 50)
    public String getFTgmgrOpinionMan() {
        return this.FTgmgrOpinionMan;
    }

    public void setFTgmgrOpinionMan(String FTgmgrOpinionMan) {
        this.FTgmgrOpinionMan = FTgmgrOpinionMan;
    }

    @Column(name = "F_TGMgrOpinion_Time", length = 50)
    public String getFTgmgrOpinionTime() {
        return this.FTgmgrOpinionTime;
    }

    public void setFTgmgrOpinionTime(String FTgmgrOpinionTime) {
        this.FTgmgrOpinionTime = FTgmgrOpinionTime;
    }

    @Column(name = "F_Note", length = 500)
    public String getFNote() {
        return this.FNote;
    }

    public void setFNote(String FNote) {
        this.FNote = FNote;
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

    @Column(name = "F_Record_Step", length = 1000)
    public String getFRecordStep() {
        return this.FRecordStep;
    }

    public void setFRecordStep(String FRecordStep) {
        this.FRecordStep = FRecordStep;
    }

}