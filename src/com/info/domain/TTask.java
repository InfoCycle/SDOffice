package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Task", schema = "dbo", catalog = "SDOffice")
public class TTask implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer FParentTaskId;

    private String FTaskNumbers;

    private String FTaskName;

    private String FTaskShortName;

    private Integer FEntrustUnitId;

    private String FEntrustUnitName;

    private String FEntrustUnitShortName;

    private String FProjectScale;

    private String FProjectScaleUnit;

    private Integer FStructureTypeId;

    private String FStructureType;

    private String FIndustryCategoryId;

    private String FIndustryCategoryName;

    private Integer FServiceCategoryId;

    private String FServiceCategory;

    private Integer FBusinessCategoryId;

    private String FBusinessCategory;

    private String FContractYjCharge;

    private Integer FDepartmentId;

    private String FDepartmentName;

    private String FYjstartTime;

    private String FYjfinishTime;

    private Integer FGivePersonId;

    private String FGivePersonName;

    private String FGiveTime;

    private Integer FDeptMgrId;

    private String FDeptMgrName;

    private String FReceivingTaskTime;

    private Integer FProjMgrId;

    private String FProjMgrName;

    private String FProjMgrViceId;

    private String FProjMgrViceName;

    private String FNote;

    private Integer FYear;

    private Integer FMainTaskId;

    private String FCurrentStep;

    private String FLastStep;

    private String FRecordStep;

    // Constructors

    /** default constructor */
    public TTask() {
    }

    /** minimal constructor */
    public TTask(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TTask(Integer FId, Integer FParentTaskId, String FTaskNumbers, String FTaskName, String FTaskShortName,
            Integer FEntrustUnitId, String FEntrustUnitName, String FEntrustUnitShortName, String FProjectScale,
            String FProjectScaleUnit, Integer FStructureTypeId, String FStructureType, String FIndustryCategoryId,
            String FIndustryCategoryName, Integer FServiceCategoryId, String FServiceCategory,
            Integer FBusinessCategoryId, String FBusinessCategory, String FContractYjCharge, Integer FDepartmentId,
            String FDepartmentName, String FYjstartTime, String FYjfinishTime, Integer FGivePersonId,
            String FGivePersonName, String FGiveTime, Integer FDeptMgrId, String FDeptMgrName,
            String FReceivingTaskTime, Integer FProjMgrId, String FProjMgrName, String FProjMgrViceId,
            String FProjMgrViceName, String FNote, Integer FYear, Integer FMainTaskId, String FCurrentStep,
            String FLastStep, String FRecordStep) {
        this.FId = FId;
        this.FParentTaskId = FParentTaskId;
        this.FTaskNumbers = FTaskNumbers;
        this.FTaskName = FTaskName;
        this.FTaskShortName = FTaskShortName;
        this.FEntrustUnitId = FEntrustUnitId;
        this.FEntrustUnitName = FEntrustUnitName;
        this.FEntrustUnitShortName = FEntrustUnitShortName;
        this.FProjectScale = FProjectScale;
        this.FProjectScaleUnit = FProjectScaleUnit;
        this.FStructureTypeId = FStructureTypeId;
        this.FStructureType = FStructureType;
        this.FIndustryCategoryId = FIndustryCategoryId;
        this.FIndustryCategoryName = FIndustryCategoryName;
        this.FServiceCategoryId = FServiceCategoryId;
        this.FServiceCategory = FServiceCategory;
        this.FBusinessCategoryId = FBusinessCategoryId;
        this.FBusinessCategory = FBusinessCategory;
        this.FContractYjCharge = FContractYjCharge;
        this.FDepartmentId = FDepartmentId;
        this.FDepartmentName = FDepartmentName;
        this.FYjstartTime = FYjstartTime;
        this.FYjfinishTime = FYjfinishTime;
        this.FGivePersonId = FGivePersonId;
        this.FGivePersonName = FGivePersonName;
        this.FGiveTime = FGiveTime;
        this.FDeptMgrId = FDeptMgrId;
        this.FDeptMgrName = FDeptMgrName;
        this.FReceivingTaskTime = FReceivingTaskTime;
        this.FProjMgrId = FProjMgrId;
        this.FProjMgrName = FProjMgrName;
        this.FProjMgrViceId = FProjMgrViceId;
        this.FProjMgrViceName = FProjMgrViceName;
        this.FNote = FNote;
        this.FYear = FYear;
        this.FMainTaskId = FMainTaskId;
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

    @Column(name = "F_ParentTaskID")
    public Integer getFParentTaskId() {
        return this.FParentTaskId;
    }

    public void setFParentTaskId(Integer FParentTaskId) {
        this.FParentTaskId = FParentTaskId;
    }

    @Column(name = "F_Task_Numbers", length = 300)
    public String getFTaskNumbers() {
        return this.FTaskNumbers;
    }

    public void setFTaskNumbers(String FTaskNumbers) {
        this.FTaskNumbers = FTaskNumbers;
    }

    @Column(name = "F_Task_Name", length = 300)
    public String getFTaskName() {
        return this.FTaskName;
    }

    public void setFTaskName(String FTaskName) {
        this.FTaskName = FTaskName;
    }

    @Column(name = "F_Task_Short_Name", length = 150)
    public String getFTaskShortName() {
        return this.FTaskShortName;
    }

    public void setFTaskShortName(String FTaskShortName) {
        this.FTaskShortName = FTaskShortName;
    }

    @Column(name = "F_EntrustUnit_ID")
    public Integer getFEntrustUnitId() {
        return this.FEntrustUnitId;
    }

    public void setFEntrustUnitId(Integer FEntrustUnitId) {
        this.FEntrustUnitId = FEntrustUnitId;
    }

    @Column(name = "F_EntrustUnit_Name", length = 200)
    public String getFEntrustUnitName() {
        return this.FEntrustUnitName;
    }

    public void setFEntrustUnitName(String FEntrustUnitName) {
        this.FEntrustUnitName = FEntrustUnitName;
    }

    @Column(name = "F_EntrustUnit_ShortName", length = 200)
    public String getFEntrustUnitShortName() {
        return this.FEntrustUnitShortName;
    }

    public void setFEntrustUnitShortName(String FEntrustUnitShortName) {
        this.FEntrustUnitShortName = FEntrustUnitShortName;
    }

    @Column(name = "F_Project_Scale", length = 100)
    public String getFProjectScale() {
        return this.FProjectScale;
    }

    public void setFProjectScale(String FProjectScale) {
        this.FProjectScale = FProjectScale;
    }

    @Column(name = "F_Project_Scale_Unit", length = 50)
    public String getFProjectScaleUnit() {
        return this.FProjectScaleUnit;
    }

    public void setFProjectScaleUnit(String FProjectScaleUnit) {
        this.FProjectScaleUnit = FProjectScaleUnit;
    }

    @Column(name = "F_Structure_Type_ID")
    public Integer getFStructureTypeId() {
        return this.FStructureTypeId;
    }

    public void setFStructureTypeId(Integer FStructureTypeId) {
        this.FStructureTypeId = FStructureTypeId;
    }

    @Column(name = "F_Structure_Type", length = 100)
    public String getFStructureType() {
        return this.FStructureType;
    }

    public void setFStructureType(String FStructureType) {
        this.FStructureType = FStructureType;
    }

    @Column(name = "F_Industry_Category_ID", length = 100)
    public String getFIndustryCategoryId() {
        return this.FIndustryCategoryId;
    }

    public void setFIndustryCategoryId(String FIndustryCategoryId) {
        this.FIndustryCategoryId = FIndustryCategoryId;
    }

    @Column(name = "F_Industry_Category_Name", length = 500)
    public String getFIndustryCategoryName() {
        return this.FIndustryCategoryName;
    }

    public void setFIndustryCategoryName(String FIndustryCategoryName) {
        this.FIndustryCategoryName = FIndustryCategoryName;
    }

    @Column(name = "F_Service_Category_ID")
    public Integer getFServiceCategoryId() {
        return this.FServiceCategoryId;
    }

    public void setFServiceCategoryId(Integer FServiceCategoryId) {
        this.FServiceCategoryId = FServiceCategoryId;
    }

    @Column(name = "F_Service_Category", length = 100)
    public String getFServiceCategory() {
        return this.FServiceCategory;
    }

    public void setFServiceCategory(String FServiceCategory) {
        this.FServiceCategory = FServiceCategory;
    }

    @Column(name = "F_Business_Category_ID")
    public Integer getFBusinessCategoryId() {
        return this.FBusinessCategoryId;
    }

    public void setFBusinessCategoryId(Integer FBusinessCategoryId) {
        this.FBusinessCategoryId = FBusinessCategoryId;
    }

    @Column(name = "F_Business_Category", length = 100)
    public String getFBusinessCategory() {
        return this.FBusinessCategory;
    }

    public void setFBusinessCategory(String FBusinessCategory) {
        this.FBusinessCategory = FBusinessCategory;
    }

    @Column(name = "F_ContractYJ_Charge", length = 100)
    public String getFContractYjCharge() {
        return this.FContractYjCharge;
    }

    public void setFContractYjCharge(String FContractYjCharge) {
        this.FContractYjCharge = FContractYjCharge;
    }

    @Column(name = "F_Department_ID")
    public Integer getFDepartmentId() {
        return this.FDepartmentId;
    }

    public void setFDepartmentId(Integer FDepartmentId) {
        this.FDepartmentId = FDepartmentId;
    }

    @Column(name = "F_Department_Name", length = 100)
    public String getFDepartmentName() {
        return this.FDepartmentName;
    }

    public void setFDepartmentName(String FDepartmentName) {
        this.FDepartmentName = FDepartmentName;
    }

    @Column(name = "F_YJStart_Time", length = 30)
    public String getFYjstartTime() {
        return this.FYjstartTime;
    }

    public void setFYjstartTime(String FYjstartTime) {
        this.FYjstartTime = FYjstartTime;
    }

    @Column(name = "F_YJFinish_Time", length = 30)
    public String getFYjfinishTime() {
        return this.FYjfinishTime;
    }

    public void setFYjfinishTime(String FYjfinishTime) {
        this.FYjfinishTime = FYjfinishTime;
    }

    @Column(name = "F_GivePerson_ID")
    public Integer getFGivePersonId() {
        return this.FGivePersonId;
    }

    public void setFGivePersonId(Integer FGivePersonId) {
        this.FGivePersonId = FGivePersonId;
    }

    @Column(name = "F_GivePerson_Name", length = 50)
    public String getFGivePersonName() {
        return this.FGivePersonName;
    }

    public void setFGivePersonName(String FGivePersonName) {
        this.FGivePersonName = FGivePersonName;
    }

    @Column(name = "F_Give_Time", length = 30)
    public String getFGiveTime() {
        return this.FGiveTime;
    }

    public void setFGiveTime(String FGiveTime) {
        this.FGiveTime = FGiveTime;
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

    @Column(name = "F_ReceivingTask_Time", length = 30)
    public String getFReceivingTaskTime() {
        return this.FReceivingTaskTime;
    }

    public void setFReceivingTaskTime(String FReceivingTaskTime) {
        this.FReceivingTaskTime = FReceivingTaskTime;
    }

    @Column(name = "F_ProjMgr_ID")
    public Integer getFProjMgrId() {
        return this.FProjMgrId;
    }

    public void setFProjMgrId(Integer FProjMgrId) {
        this.FProjMgrId = FProjMgrId;
    }

    @Column(name = "F_ProjMgr_Name", length = 50)
    public String getFProjMgrName() {
        return this.FProjMgrName;
    }

    public void setFProjMgrName(String FProjMgrName) {
        this.FProjMgrName = FProjMgrName;
    }

    @Column(name = "F_ProjMgr_Vice_ID", length = 100)
    public String getFProjMgrViceId() {
        return this.FProjMgrViceId;
    }

    public void setFProjMgrViceId(String FProjMgrViceId) {
        this.FProjMgrViceId = FProjMgrViceId;
    }

    @Column(name = "F_ProjMgr_Vice_Name", length = 300)
    public String getFProjMgrViceName() {
        return this.FProjMgrViceName;
    }

    public void setFProjMgrViceName(String FProjMgrViceName) {
        this.FProjMgrViceName = FProjMgrViceName;
    }

    @Column(name = "F_Note", length = 500)
    public String getFNote() {
        return this.FNote;
    }

    public void setFNote(String FNote) {
        this.FNote = FNote;
    }

    @Column(name = "F_Year")
    public Integer getFYear() {
        return this.FYear;
    }

    public void setFYear(Integer FYear) {
        this.FYear = FYear;
    }

    @Column(name = "F_MainTask_ID")
    public Integer getFMainTaskId() {
        return this.FMainTaskId;
    }

    public void setFMainTaskId(Integer FMainTaskId) {
        this.FMainTaskId = FMainTaskId;
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