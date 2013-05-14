package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_Task", schema = "dbo", catalog = "SDOffice")
public class ViewTask implements java.io.Serializable {

    // Fields

    private Integer rowid;

    private Integer FId;

    private String FName;

    private Integer FParentId;

    private String te;

    private Long orderId;

    private Boolean isleft;

    private Integer isTask;

    private Integer FTaskId;

    private String FTaskNumbers;

    private String FTaskName;

    private Integer FEntrustUnitId;

    private String FEntrustUnitName;

    private String FProjectScale;

    private String FProjectScaleUnit;

    private String FStructureType;

    private String FIndustryCategoryId;

    private String FIndustryCategoryName;

    private String FBusinessCategory;

    private String FServiceCategory;

    private String FServiceCategoryName;

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

    private Integer FMainTaskId;

    private Integer FSort;

    private Integer FIshistory;

    // Constructors

    /** default constructor */
    public ViewTask() {
    }

    /** minimal constructor */
    public ViewTask(Integer rowid) {
        this.rowid = rowid;
    }

    /** full constructor */
    public ViewTask(Integer rowid, Integer FId, String FName, Integer FParentId, String te, Long orderId,
            Boolean isleft, Integer isTask, Integer FTaskId, String FTaskNumbers, String FTaskName,
            Integer FEntrustUnitId, String FEntrustUnitName, String FProjectScale, String FProjectScaleUnit,
            String FStructureType, String FIndustryCategoryId, String FIndustryCategoryName, String FBusinessCategory,
            String FServiceCategory, String FServiceCategoryName, String FContractYjCharge, Integer FDepartmentId,
            String FDepartmentName, String FYjstartTime, String FYjfinishTime, Integer FGivePersonId,
            String FGivePersonName, String FGiveTime, Integer FDeptMgrId, String FDeptMgrName,
            String FReceivingTaskTime, Integer FProjMgrId, String FProjMgrName, String FProjMgrViceId,
            String FProjMgrViceName, String FNote, Integer FMainTaskId, Integer FSort, Integer FIshistory) {
        this.rowid = rowid;
        this.FId = FId;
        this.FName = FName;
        this.FParentId = FParentId;
        this.te = te;
        this.orderId = orderId;
        this.isleft = isleft;
        this.isTask = isTask;
        this.FTaskId = FTaskId;
        this.FTaskNumbers = FTaskNumbers;
        this.FTaskName = FTaskName;
        this.FEntrustUnitId = FEntrustUnitId;
        this.FEntrustUnitName = FEntrustUnitName;
        this.FProjectScale = FProjectScale;
        this.FProjectScaleUnit = FProjectScaleUnit;
        this.FStructureType = FStructureType;
        this.FIndustryCategoryId = FIndustryCategoryId;
        this.FIndustryCategoryName = FIndustryCategoryName;
        this.FBusinessCategory = FBusinessCategory;
        this.FServiceCategory = FServiceCategory;
        this.FServiceCategoryName = FServiceCategoryName;
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
        this.FMainTaskId = FMainTaskId;
        this.FSort = FSort;
        this.FIshistory = FIshistory;
    }

    // Property accessors
    @Id
    @Column(name = "rowid")
    public Integer getRowid() {
        return this.rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    @Column(name = "F_ID")
    public Integer getFId() {
        return this.FId;
    }

    public void setFId(Integer FId) {
        this.FId = FId;
    }

    @Column(name = "F_Name", length = 300)
    public String getFName() {
        return this.FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    @Column(name = "F_Parent_ID")
    public Integer getFParentId() {
        return this.FParentId;
    }

    public void setFParentId(Integer FParentId) {
        this.FParentId = FParentId;
    }

    @Column(name = "TE")
    public String getTe() {
        return this.te;
    }

    public void setTe(String te) {
        this.te = te;
    }

    @Column(name = "OrderID")
    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "isleft")
    public Boolean getIsleft() {
        return this.isleft;
    }

    public void setIsleft(Boolean isleft) {
        this.isleft = isleft;
    }

    @Column(name = "IsTask")
    public Integer getIsTask() {
        return this.isTask;
    }

    public void setIsTask(Integer isTask) {
        this.isTask = isTask;
    }

    @Column(name = "F_Task_ID")
    public Integer getFTaskId() {
        return this.FTaskId;
    }

    public void setFTaskId(Integer FTaskId) {
        this.FTaskId = FTaskId;
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

    @Column(name = "F_Project_Scale", length = 100)
    public String getFProjectScale() {
        return this.FProjectScale;
    }

    public void setFProjectScale(String FProjectScale) {
        this.FProjectScale = FProjectScale;
    }

    @Column(name = "F_Project_scale_Unit", length = 1000)
    public String getFProjectScaleUnit() {
        return this.FProjectScaleUnit;
    }

    public void setFProjectScaleUnit(String FProjectScaleUnit) {
        this.FProjectScaleUnit = FProjectScaleUnit;
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

    @Column(name = "F_Business_Category", length = 100)
    public String getFBusinessCategory() {
        return this.FBusinessCategory;
    }

    public void setFBusinessCategory(String FBusinessCategory) {
        this.FBusinessCategory = FBusinessCategory;
    }

    @Column(name = "F_Service_Category", length = 100)
    public String getFServiceCategory() {
        return this.FServiceCategory;
    }

    public void setFServiceCategory(String FServiceCategory) {
        this.FServiceCategory = FServiceCategory;
    }

    @Column(name = "F_Service_CategoryName", length = 1000)
    public String getFServiceCategoryName() {
        return this.FServiceCategoryName;
    }

    public void setFServiceCategoryName(String FServiceCategoryName) {
        this.FServiceCategoryName = FServiceCategoryName;
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

    @Column(name = "F_MainTask_ID")
    public Integer getFMainTaskId() {
        return this.FMainTaskId;
    }

    public void setFMainTaskId(Integer FMainTaskId) {
        this.FMainTaskId = FMainTaskId;
    }

    @Column(name = "F_Sort")
    public Integer getFSort() {
        return this.FSort;
    }

    public void setFSort(Integer FSort) {
        this.FSort = FSort;
    }

    @Column(name = "f_ishistory")
    public Integer getFIshistory() {
        return this.FIshistory;
    }

    public void setFIshistory(Integer FIshistory) {
        this.FIshistory = FIshistory;
    }

}