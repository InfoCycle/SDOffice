package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TImplementPlan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ImplementPlan", schema = "dbo", catalog = "SDOffice")
public class TImplementPlan implements java.io.Serializable {

    // Fields

    private Integer FId;

    private String FPlanNumbers;

    private Integer fkTaskId;

    private String FCollectDataTime;

    private String FProcessImnTime;

    private String FSubmitRewTime;

    private String FIssueResultsTime;

    private String FOther;

    private String FDeptOpinion;

    private String FDeptOpinionTime;

    private Integer FDeptOpinionManId;

    private String FDeptOpinionManName;

    private String FPlanningPerId;

    private String FPlanningPerName;

    private String FPlanningTime;

    private String FCurrentStep;

    private String FLastStep;

    private String FRecordStep;

    // Constructors

    /** default constructor */
    public TImplementPlan() {
    }

    /** minimal constructor */
    public TImplementPlan(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TImplementPlan(Integer FId, String FPlanNumbers, Integer fkTaskId, String FCollectDataTime,
            String FProcessImnTime, String FSubmitRewTime, String FIssueResultsTime, String FOther,
            String FDeptOpinion, String FDeptOpinionTime, Integer FDeptOpinionManId, String FDeptOpinionManName,
            String FPlanningPerId, String FPlanningPerName, String FPlanningTime, String FCurrentStep,
            String FLastStep, String FRecordStep) {
        this.FId = FId;
        this.FPlanNumbers = FPlanNumbers;
        this.fkTaskId = fkTaskId;
        this.FCollectDataTime = FCollectDataTime;
        this.FProcessImnTime = FProcessImnTime;
        this.FSubmitRewTime = FSubmitRewTime;
        this.FIssueResultsTime = FIssueResultsTime;
        this.FOther = FOther;
        this.FDeptOpinion = FDeptOpinion;
        this.FDeptOpinionTime = FDeptOpinionTime;
        this.FDeptOpinionManId = FDeptOpinionManId;
        this.FDeptOpinionManName = FDeptOpinionManName;
        this.FPlanningPerId = FPlanningPerId;
        this.FPlanningPerName = FPlanningPerName;
        this.FPlanningTime = FPlanningTime;
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

    @Column(name = "F_Plan_Numbers", length = 300)
    public String getFPlanNumbers() {
        return this.FPlanNumbers;
    }

    public void setFPlanNumbers(String FPlanNumbers) {
        this.FPlanNumbers = FPlanNumbers;
    }

    @Column(name = "FK_Task_ID")
    public Integer getFkTaskId() {
        return this.fkTaskId;
    }

    public void setFkTaskId(Integer fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    @Column(name = "F_CollectData_Time", length = 100)
    public String getFCollectDataTime() {
        return this.FCollectDataTime;
    }

    public void setFCollectDataTime(String FCollectDataTime) {
        this.FCollectDataTime = FCollectDataTime;
    }

    @Column(name = "F_ProcessImn_Time", length = 100)
    public String getFProcessImnTime() {
        return this.FProcessImnTime;
    }

    public void setFProcessImnTime(String FProcessImnTime) {
        this.FProcessImnTime = FProcessImnTime;
    }

    @Column(name = "F_SubmitRew_Time", length = 100)
    public String getFSubmitRewTime() {
        return this.FSubmitRewTime;
    }

    public void setFSubmitRewTime(String FSubmitRewTime) {
        this.FSubmitRewTime = FSubmitRewTime;
    }

    @Column(name = "F_IssueResults_Time", length = 100)
    public String getFIssueResultsTime() {
        return this.FIssueResultsTime;
    }

    public void setFIssueResultsTime(String FIssueResultsTime) {
        this.FIssueResultsTime = FIssueResultsTime;
    }

    @Column(name = "F_Other", length = 500)
    public String getFOther() {
        return this.FOther;
    }

    public void setFOther(String FOther) {
        this.FOther = FOther;
    }

    @Column(name = "F_DeptOpinion", length = 500)
    public String getFDeptOpinion() {
        return this.FDeptOpinion;
    }

    public void setFDeptOpinion(String FDeptOpinion) {
        this.FDeptOpinion = FDeptOpinion;
    }

    @Column(name = "F_DeptOpinion_Time", length = 100)
    public String getFDeptOpinionTime() {
        return this.FDeptOpinionTime;
    }

    public void setFDeptOpinionTime(String FDeptOpinionTime) {
        this.FDeptOpinionTime = FDeptOpinionTime;
    }

    @Column(name = "F_DeptOpinionMan_ID")
    public Integer getFDeptOpinionManId() {
        return this.FDeptOpinionManId;
    }

    public void setFDeptOpinionManId(Integer FDeptOpinionManId) {
        this.FDeptOpinionManId = FDeptOpinionManId;
    }

    @Column(name = "F_DeptOpinionMan_Name", length = 50)
    public String getFDeptOpinionManName() {
        return this.FDeptOpinionManName;
    }

    public void setFDeptOpinionManName(String FDeptOpinionManName) {
        this.FDeptOpinionManName = FDeptOpinionManName;
    }

    @Column(name = "F_PlanningPer_ID", length = 30)
    public String getFPlanningPerId() {
        return this.FPlanningPerId;
    }

    public void setFPlanningPerId(String FPlanningPerId) {
        this.FPlanningPerId = FPlanningPerId;
    }

    @Column(name = "F_PlanningPer_Name", length = 30)
    public String getFPlanningPerName() {
        return this.FPlanningPerName;
    }

    public void setFPlanningPerName(String FPlanningPerName) {
        this.FPlanningPerName = FPlanningPerName;
    }

    @Column(name = "F_Planning_Time", length = 100)
    public String getFPlanningTime() {
        return this.FPlanningTime;
    }

    public void setFPlanningTime(String FPlanningTime) {
        this.FPlanningTime = FPlanningTime;
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