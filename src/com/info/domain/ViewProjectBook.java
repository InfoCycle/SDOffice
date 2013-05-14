package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewProjectBook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_ProjectBook", schema = "dbo", catalog = "SDOffice")
public class ViewProjectBook implements java.io.Serializable {

    // Fields

    private Integer FId;
    private Integer FParentTaskId;
    private String FGiveTime;
    private String FContractNumbers;
    private String FTaskName;
    private String FEntrustUnitName;
    private String FServiceCategory;
    private String FContractFees;
    private Double FAccounts;
    private Double FOutstanding;
    private String FProjectCost;
    private String FCglroFinalCost;
    private String FDepartmentId;
    private String FTaskNumbers;
    private String FYjstartTime;
    private String FYjfinishTime;
    private String FGlosignTime;
    private String FTaskPm;
    private String FTaskPerson;
    private Double appraisalScoure;
    private String FArchiveTime;
    private Double FCarryTotal;

    // Constructors

    /** default constructor */
    public ViewProjectBook() {
    }

    /** minimal constructor */
    public ViewProjectBook(Integer FId, String FServiceCategory) {
	this.FId = FId;
	this.FServiceCategory = FServiceCategory;
    }

    /** full constructor */
    public ViewProjectBook(Integer FId, Integer FParentTaskId,
	    String FGiveTime, String FContractNumbers, String FTaskName,
	    String FEntrustUnitName, String FServiceCategory,
	    String FContractFees, Double FAccounts, Double FOutstanding,
	    String FProjectCost, String FCglroFinalCost, String FDepartmentId,
	    String FTaskNumbers, String FYjstartTime, String FYjfinishTime,
	    String FGlosignTime, String FTaskPm, String FTaskPerson,
	    Double appraisalScoure, String FArchiveTime, Double FCarryTotal) {
	this.FId = FId;
	this.FParentTaskId = FParentTaskId;
	this.FGiveTime = FGiveTime;
	this.FContractNumbers = FContractNumbers;
	this.FTaskName = FTaskName;
	this.FEntrustUnitName = FEntrustUnitName;
	this.FServiceCategory = FServiceCategory;
	this.FContractFees = FContractFees;
	this.FAccounts = FAccounts;
	this.FOutstanding = FOutstanding;
	this.FProjectCost = FProjectCost;
	this.FCglroFinalCost = FCglroFinalCost;
	this.FDepartmentId = FDepartmentId;
	this.FTaskNumbers = FTaskNumbers;
	this.FYjstartTime = FYjstartTime;
	this.FYjfinishTime = FYjfinishTime;
	this.FGlosignTime = FGlosignTime;
	this.FTaskPm = FTaskPm;
	this.FTaskPerson = FTaskPerson;
	this.appraisalScoure = appraisalScoure;
	this.FArchiveTime = FArchiveTime;
	this.FCarryTotal = FCarryTotal;
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

    @Column(name = "F_ParentTaskID")
    public Integer getFParentTaskId() {
	return this.FParentTaskId;
    }

    public void setFParentTaskId(Integer FParentTaskId) {
	this.FParentTaskId = FParentTaskId;
    }

    @Column(name = "F_Give_Time", length = 30)
    public String getFGiveTime() {
	return this.FGiveTime;
    }

    public void setFGiveTime(String FGiveTime) {
	this.FGiveTime = FGiveTime;
    }

    @Column(name = "F_ContractNumbers", length = 50)
    public String getFContractNumbers() {
	return this.FContractNumbers;
    }

    public void setFContractNumbers(String FContractNumbers) {
	this.FContractNumbers = FContractNumbers;
    }

    @Column(name = "F_Task_Name", length = 200)
    public String getFTaskName() {
	return this.FTaskName;
    }

    public void setFTaskName(String FTaskName) {
	this.FTaskName = FTaskName;
    }

    @Column(name = "F_EntrustUnit_Name", length = 200)
    public String getFEntrustUnitName() {
	return this.FEntrustUnitName;
    }

    public void setFEntrustUnitName(String FEntrustUnitName) {
	this.FEntrustUnitName = FEntrustUnitName;
    }

    @Column(name = "F_Service_Category", nullable = false, length = 200)
    public String getFServiceCategory() {
	return this.FServiceCategory;
    }

    public void setFServiceCategory(String FServiceCategory) {
	this.FServiceCategory = FServiceCategory;
    }

    @Column(name = "F_ContractFees", length = 50)
    public String getFContractFees() {
	return this.FContractFees;
    }

    public void setFContractFees(String FContractFees) {
	this.FContractFees = FContractFees;
    }

    @Column(name = "F_Accounts", scale = 4)
    public Double getFAccounts() {
	return this.FAccounts;
    }

    public void setFAccounts(Double FAccounts) {
	this.FAccounts = FAccounts;
    }

    @Column(name = "F_Outstanding", scale = 4)
    public Double getFOutstanding() {
	return this.FOutstanding;
    }

    public void setFOutstanding(Double FOutstanding) {
	this.FOutstanding = FOutstanding;
    }

    @Column(name = "F_ProjectCost", length = 200)
    public String getFProjectCost() {
	return this.FProjectCost;
    }

    public void setFProjectCost(String FProjectCost) {
	this.FProjectCost = FProjectCost;
    }

    @Column(name = "F_CGLRO_FinalCost", length = 200)
    public String getFCglroFinalCost() {
	return this.FCglroFinalCost;
    }

    public void setFCglroFinalCost(String FCglroFinalCost) {
	this.FCglroFinalCost = FCglroFinalCost;
    }

    @Column(name = "F_Department_ID", length = 100)
    public String getFDepartmentId() {
	return this.FDepartmentId;
    }

    public void setFDepartmentId(String FDepartmentId) {
	this.FDepartmentId = FDepartmentId;
    }

    @Column(name = "F_Task_Numbers", length = 300)
    public String getFTaskNumbers() {
	return this.FTaskNumbers;
    }

    public void setFTaskNumbers(String FTaskNumbers) {
	this.FTaskNumbers = FTaskNumbers;
    }

    @Column(name = "F_YJStart_Time", length = 10)
    public String getFYjstartTime() {
	return this.FYjstartTime;
    }

    public void setFYjstartTime(String FYjstartTime) {
	this.FYjstartTime = FYjstartTime;
    }

    @Column(name = "F_YJFinish_Time", length = 10)
    public String getFYjfinishTime() {
	return this.FYjfinishTime;
    }

    public void setFYjfinishTime(String FYjfinishTime) {
	this.FYjfinishTime = FYjfinishTime;
    }

    @Column(name = "F_GLOSignTime", length = 10)
    public String getFGlosignTime() {
	return this.FGlosignTime;
    }

    public void setFGlosignTime(String FGlosignTime) {
	this.FGlosignTime = FGlosignTime;
    }

    @Column(name = "F_TaskPM", length = 1000)
    public String getFTaskPm() {
	return this.FTaskPm;
    }

    public void setFTaskPm(String FTaskPm) {
	this.FTaskPm = FTaskPm;
    }

    @Column(name = "F_TaskPerson", length = 1000)
    public String getFTaskPerson() {
	return this.FTaskPerson;
    }

    public void setFTaskPerson(String FTaskPerson) {
	this.FTaskPerson = FTaskPerson;
    }

    @Column(name = "AppraisalScoure", precision = 6)
    public Double getAppraisalScoure() {
	return this.appraisalScoure;
    }

    public void setAppraisalScoure(Double appraisalScoure) {
	this.appraisalScoure = appraisalScoure;
    }

    @Column(name = "F_Archive_Time", length = 10)
    public String getFArchiveTime() {
	return this.FArchiveTime;
    }

    public void setFArchiveTime(String FArchiveTime) {
	this.FArchiveTime = FArchiveTime;
    }

    @Column(name = "F_CarryTotal", precision = 18)
    public Double getFCarryTotal() {
	return this.FCarryTotal;
    }

    public void setFCarryTotal(Double FCarryTotal) {
	this.FCarryTotal = FCarryTotal;
    }

}