package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProjectAppraisalScoure entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ProjectAppraisalScoure", schema = "dbo", catalog = "SDOffice")
public class TProjectAppraisalScoure implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer FPasId;

    private String FNumber;

    private Integer fkTaskId;

    private Integer FSort;

    private String FAppraisalContent;

    private Integer FScore;

    private Integer FDepartmentScore;

    private Integer FCompanyScore;

    private Integer FComprehensiveScore;

    private String FDeptScoreThat;

    private String FCompanyThatScore;

    private String FAppraisalGroup;

    private String FCurrentStep;

    private String FLastStep;

    private String FRecordStep;

    // Constructors

    /** default constructor */
    public TProjectAppraisalScoure() {
    }

    /** minimal constructor */
    public TProjectAppraisalScoure(Integer FId, Integer FPasId) {
        this.FId = FId;
        this.FPasId = FPasId;
    }

    /** full constructor */
    public TProjectAppraisalScoure(Integer FId, Integer FPasId, String FNumber, Integer fkTaskId, Integer FSort,
            String FAppraisalContent, Integer FScore, Integer FDepartmentScore, Integer FCompanyScore,
            Integer FComprehensiveScore, String FDeptScoreThat, String FCompanyThatScore, String FAppraisalGroup,
            String FCurrentStep, String FLastStep, String FRecordStep) {
        this.FId = FId;
        this.FPasId = FPasId;
        this.FNumber = FNumber;
        this.fkTaskId = fkTaskId;
        this.FSort = FSort;
        this.FAppraisalContent = FAppraisalContent;
        this.FScore = FScore;
        this.FDepartmentScore = FDepartmentScore;
        this.FCompanyScore = FCompanyScore;
        this.FComprehensiveScore = FComprehensiveScore;
        this.FDeptScoreThat = FDeptScoreThat;
        this.FCompanyThatScore = FCompanyThatScore;
        this.FAppraisalGroup = FAppraisalGroup;
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

    @Column(name = "F_PAS_ID", nullable = false)
    public Integer getFPasId() {
        return this.FPasId;
    }

    public void setFPasId(Integer FPasId) {
        this.FPasId = FPasId;
    }

    @Column(name = "F_Number", length = 300)
    public String getFNumber() {
        return this.FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }

    @Column(name = "FK_Task_ID")
    public Integer getFkTaskId() {
        return this.fkTaskId;
    }

    public void setFkTaskId(Integer fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    @Column(name = "F_Sort")
    public Integer getFSort() {
        return this.FSort;
    }

    public void setFSort(Integer FSort) {
        this.FSort = FSort;
    }

    @Column(name = "F_AppraisalContent", length = 300)
    public String getFAppraisalContent() {
        return this.FAppraisalContent;
    }

    public void setFAppraisalContent(String FAppraisalContent) {
        this.FAppraisalContent = FAppraisalContent;
    }

    @Column(name = "F_Score")
    public Integer getFScore() {
        return this.FScore;
    }

    public void setFScore(Integer FScore) {
        this.FScore = FScore;
    }

    @Column(name = "F_DepartmentScore")
    public Integer getFDepartmentScore() {
        return this.FDepartmentScore;
    }

    public void setFDepartmentScore(Integer FDepartmentScore) {
        this.FDepartmentScore = FDepartmentScore;
    }

    @Column(name = "F_CompanyScore")
    public Integer getFCompanyScore() {
        return this.FCompanyScore;
    }

    public void setFCompanyScore(Integer FCompanyScore) {
        this.FCompanyScore = FCompanyScore;
    }

    @Column(name = "F_ComprehensiveScore")
    public Integer getFComprehensiveScore() {
        return this.FComprehensiveScore;
    }

    public void setFComprehensiveScore(Integer FComprehensiveScore) {
        this.FComprehensiveScore = FComprehensiveScore;
    }

    @Column(name = "F_DeptScoreThat", length = 800)
    public String getFDeptScoreThat() {
        return this.FDeptScoreThat;
    }

    public void setFDeptScoreThat(String FDeptScoreThat) {
        this.FDeptScoreThat = FDeptScoreThat;
    }

    @Column(name = "F_CompanyThatScore", length = 800)
    public String getFCompanyThatScore() {
        return this.FCompanyThatScore;
    }

    public void setFCompanyThatScore(String FCompanyThatScore) {
        this.FCompanyThatScore = FCompanyThatScore;
    }

    @Column(name = "F_AppraisalGroup", length = 500)
    public String getFAppraisalGroup() {
        return this.FAppraisalGroup;
    }

    public void setFAppraisalGroup(String FAppraisalGroup) {
        this.FAppraisalGroup = FAppraisalGroup;
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