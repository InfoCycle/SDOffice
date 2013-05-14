package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCheckReview entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CheckReview", schema = "dbo", catalog = "SDOffice")
public class TCheckReview implements java.io.Serializable {

    // Fields

    private Integer FId;

    private String FNumbers;

    private Integer fkTaskId;

    private String FConstructionUnit;

    private String FResultsType;

    private String FResultTypeName;

    private String FSubmitMaterials;

    private String FSubmitMaterialsName;

    private String FProjectCost;

    private String FProjectScale;

    private String FUnitCost;

    private String FPmroReviewMan;

    private String FPmroReviewTime;

    private String FPmroProblems;

    private String FPmroRectification;

    private String FPmroDiscuss;

    private String FDmroReviewMan;

    private String FDmroReviewTime;

    private String FDmroProblems;

    private String FDmroRectification;

    private String FDmroDiscuss;

    private String FCglroFinalCost;

    private String FCglroProblems;

    private String FCglroRectification;

    private String FCglroDiscuss;

    private String FReviewMan;

    private String FReviewManTime;

    private String FGlosign;

    private String FGlosignTime;

    private String FCurrentStep;

    private String FLastStep;

    private String FRecordStep;

    // Constructors

    /** default constructor */
    public TCheckReview() {
    }

    /** minimal constructor */
    public TCheckReview(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TCheckReview(Integer FId, String FNumbers, Integer fkTaskId, String FConstructionUnit, String FResultsType,
            String FResultTypeName, String FSubmitMaterials, String FSubmitMaterialsName, String FProjectCost,
            String FProjectScale, String FUnitCost, String FPmroReviewMan, String FPmroReviewTime,
            String FPmroProblems, String FPmroRectification, String FPmroDiscuss, String FDmroReviewMan,
            String FDmroReviewTime, String FDmroProblems, String FDmroRectification, String FDmroDiscuss,
            String FCglroFinalCost, String FCglroProblems, String FCglroRectification, String FCglroDiscuss,
            String FReviewMan, String FReviewManTime, String FGlosign, String FGlosignTime, String FCurrentStep,
            String FLastStep, String FRecordStep) {
        this.FId = FId;
        this.FNumbers = FNumbers;
        this.fkTaskId = fkTaskId;
        this.FConstructionUnit = FConstructionUnit;
        this.FResultsType = FResultsType;
        this.FResultTypeName = FResultTypeName;
        this.FSubmitMaterials = FSubmitMaterials;
        this.FSubmitMaterialsName = FSubmitMaterialsName;
        this.FProjectCost = FProjectCost;
        this.FProjectScale = FProjectScale;
        this.FUnitCost = FUnitCost;
        this.FPmroReviewMan = FPmroReviewMan;
        this.FPmroReviewTime = FPmroReviewTime;
        this.FPmroProblems = FPmroProblems;
        this.FPmroRectification = FPmroRectification;
        this.FPmroDiscuss = FPmroDiscuss;
        this.FDmroReviewMan = FDmroReviewMan;
        this.FDmroReviewTime = FDmroReviewTime;
        this.FDmroProblems = FDmroProblems;
        this.FDmroRectification = FDmroRectification;
        this.FDmroDiscuss = FDmroDiscuss;
        this.FCglroFinalCost = FCglroFinalCost;
        this.FCglroProblems = FCglroProblems;
        this.FCglroRectification = FCglroRectification;
        this.FCglroDiscuss = FCglroDiscuss;
        this.FReviewMan = FReviewMan;
        this.FReviewManTime = FReviewManTime;
        this.FGlosign = FGlosign;
        this.FGlosignTime = FGlosignTime;
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

    @Column(name = "F_Numbers", length = 300)
    public String getFNumbers() {
        return this.FNumbers;
    }

    public void setFNumbers(String FNumbers) {
        this.FNumbers = FNumbers;
    }

    @Column(name = "FK_Task_ID")
    public Integer getFkTaskId() {
        return this.fkTaskId;
    }

    public void setFkTaskId(Integer fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    @Column(name = "F_ConstructionUnit", length = 300)
    public String getFConstructionUnit() {
        return this.FConstructionUnit;
    }

    public void setFConstructionUnit(String FConstructionUnit) {
        this.FConstructionUnit = FConstructionUnit;
    }

    @Column(name = "F_ResultsType", length = 200)
    public String getFResultsType() {
        return this.FResultsType;
    }

    public void setFResultsType(String FResultsType) {
        this.FResultsType = FResultsType;
    }

    @Column(name = "F_ResultType_Name", length = 800)
    public String getFResultTypeName() {
        return this.FResultTypeName;
    }

    public void setFResultTypeName(String FResultTypeName) {
        this.FResultTypeName = FResultTypeName;
    }

    @Column(name = "F_SubmitMaterials", length = 200)
    public String getFSubmitMaterials() {
        return this.FSubmitMaterials;
    }

    public void setFSubmitMaterials(String FSubmitMaterials) {
        this.FSubmitMaterials = FSubmitMaterials;
    }

    @Column(name = "F_SubmitMaterials_Name", length = 800)
    public String getFSubmitMaterialsName() {
        return this.FSubmitMaterialsName;
    }

    public void setFSubmitMaterialsName(String FSubmitMaterialsName) {
        this.FSubmitMaterialsName = FSubmitMaterialsName;
    }

    @Column(name = "F_ProjectCost", length = 100)
    public String getFProjectCost() {
        return this.FProjectCost;
    }

    public void setFProjectCost(String FProjectCost) {
        this.FProjectCost = FProjectCost;
    }

    @Column(name = "F_ProjectScale", length = 300)
    public String getFProjectScale() {
        return this.FProjectScale;
    }

    public void setFProjectScale(String FProjectScale) {
        this.FProjectScale = FProjectScale;
    }

    @Column(name = "F_UnitCost", length = 30)
    public String getFUnitCost() {
        return this.FUnitCost;
    }

    public void setFUnitCost(String FUnitCost) {
        this.FUnitCost = FUnitCost;
    }

    @Column(name = "F_PMRO_ReviewMan", length = 30)
    public String getFPmroReviewMan() {
        return this.FPmroReviewMan;
    }

    public void setFPmroReviewMan(String FPmroReviewMan) {
        this.FPmroReviewMan = FPmroReviewMan;
    }

    @Column(name = "F_PMRO_ReviewTime", length = 30)
    public String getFPmroReviewTime() {
        return this.FPmroReviewTime;
    }

    public void setFPmroReviewTime(String FPmroReviewTime) {
        this.FPmroReviewTime = FPmroReviewTime;
    }

    @Column(name = "F_PMRO_Problems", length = 2000)
    public String getFPmroProblems() {
        return this.FPmroProblems;
    }

    public void setFPmroProblems(String FPmroProblems) {
        this.FPmroProblems = FPmroProblems;
    }

    @Column(name = "F_PMRO_Rectification", length = 2000)
    public String getFPmroRectification() {
        return this.FPmroRectification;
    }

    public void setFPmroRectification(String FPmroRectification) {
        this.FPmroRectification = FPmroRectification;
    }

    @Column(name = "F_PMRO_Discuss", length = 2000)
    public String getFPmroDiscuss() {
        return this.FPmroDiscuss;
    }

    public void setFPmroDiscuss(String FPmroDiscuss) {
        this.FPmroDiscuss = FPmroDiscuss;
    }

    @Column(name = "F_DMRO_ReviewMan", length = 30)
    public String getFDmroReviewMan() {
        return this.FDmroReviewMan;
    }

    public void setFDmroReviewMan(String FDmroReviewMan) {
        this.FDmroReviewMan = FDmroReviewMan;
    }

    @Column(name = "F_DMRO_ReviewTime", length = 30)
    public String getFDmroReviewTime() {
        return this.FDmroReviewTime;
    }

    public void setFDmroReviewTime(String FDmroReviewTime) {
        this.FDmroReviewTime = FDmroReviewTime;
    }

    @Column(name = "F_DMRO_Problems", length = 2000)
    public String getFDmroProblems() {
        return this.FDmroProblems;
    }

    public void setFDmroProblems(String FDmroProblems) {
        this.FDmroProblems = FDmroProblems;
    }

    @Column(name = "F_DMRO_Rectification", length = 2000)
    public String getFDmroRectification() {
        return this.FDmroRectification;
    }

    public void setFDmroRectification(String FDmroRectification) {
        this.FDmroRectification = FDmroRectification;
    }

    @Column(name = "F_DMRO_Discuss", length = 2000)
    public String getFDmroDiscuss() {
        return this.FDmroDiscuss;
    }

    public void setFDmroDiscuss(String FDmroDiscuss) {
        this.FDmroDiscuss = FDmroDiscuss;
    }

    @Column(name = "F_CGLRO_FinalCost", length = 200)
    public String getFCglroFinalCost() {
        return this.FCglroFinalCost;
    }

    public void setFCglroFinalCost(String FCglroFinalCost) {
        this.FCglroFinalCost = FCglroFinalCost;
    }

    @Column(name = "F_CGLRO_Problems", length = 2000)
    public String getFCglroProblems() {
        return this.FCglroProblems;
    }

    public void setFCglroProblems(String FCglroProblems) {
        this.FCglroProblems = FCglroProblems;
    }

    @Column(name = "F_CGLRO_Rectification", length = 2000)
    public String getFCglroRectification() {
        return this.FCglroRectification;
    }

    public void setFCglroRectification(String FCglroRectification) {
        this.FCglroRectification = FCglroRectification;
    }

    @Column(name = "F_CGLRO_Discuss", length = 2000)
    public String getFCglroDiscuss() {
        return this.FCglroDiscuss;
    }

    public void setFCglroDiscuss(String FCglroDiscuss) {
        this.FCglroDiscuss = FCglroDiscuss;
    }

    @Column(name = "F_ReviewMan", length = 30)
    public String getFReviewMan() {
        return this.FReviewMan;
    }

    public void setFReviewMan(String FReviewMan) {
        this.FReviewMan = FReviewMan;
    }

    @Column(name = "F_ReviewManTime", length = 30)
    public String getFReviewManTime() {
        return this.FReviewManTime;
    }

    public void setFReviewManTime(String FReviewManTime) {
        this.FReviewManTime = FReviewManTime;
    }

    @Column(name = "F_GLOSign", length = 200)
    public String getFGlosign() {
        return this.FGlosign;
    }

    public void setFGlosign(String FGlosign) {
        this.FGlosign = FGlosign;
    }

    @Column(name = "F_GLOSignTime", length = 30)
    public String getFGlosignTime() {
        return this.FGlosignTime;
    }

    public void setFGlosignTime(String FGlosignTime) {
        this.FGlosignTime = FGlosignTime;
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