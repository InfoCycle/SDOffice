package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TContract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_Contract"
    ,schema="dbo"
    ,catalog="SDOffice"
)

public class TContract  implements java.io.Serializable {


    // Fields    

     private Integer FId;
     private Integer fkTaskId;
     private String FContractNumbers;
     private String FProjectType;
     private String FServiceMode;
     private String FServicePerion;
     private String FSigningDate;
     private String FInvesttScale;
     private String FContractFees;
     private String FOthers;
     private Integer FMainContractId;
     private Integer FEntrustUnitId;
     private String FEntrustUnitName;
     private String FContractName;
     private Integer FOperateId;
     private String FOperateName;
     private String FOperateDate;
     private Integer FState;


    // Constructors

    /** default constructor */
    public TContract() {
    }

	/** minimal constructor */
    public TContract(Integer FId) {
        this.FId = FId;
    }
    
    /** full constructor */
    public TContract(Integer FId, Integer fkTaskId, String FContractNumbers, String FProjectType, String FServiceMode, String FServicePerion, String FSigningDate, String FInvesttScale, String FContractFees, String FOthers, Integer FMainContractId, Integer FEntrustUnitId, String FEntrustUnitName, String FContractName, Integer FOperateId, String FOperateName, String FOperateDate, Integer FState) {
        this.FId = FId;
        this.fkTaskId = fkTaskId;
        this.FContractNumbers = FContractNumbers;
        this.FProjectType = FProjectType;
        this.FServiceMode = FServiceMode;
        this.FServicePerion = FServicePerion;
        this.FSigningDate = FSigningDate;
        this.FInvesttScale = FInvesttScale;
        this.FContractFees = FContractFees;
        this.FOthers = FOthers;
        this.FMainContractId = FMainContractId;
        this.FEntrustUnitId = FEntrustUnitId;
        this.FEntrustUnitName = FEntrustUnitName;
        this.FContractName = FContractName;
        this.FOperateId = FOperateId;
        this.FOperateName = FOperateName;
        this.FOperateDate = FOperateDate;
        this.FState = FState;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="F_ID", unique=true, nullable=false)

    public Integer getFId() {
        return this.FId;
    }
    
    public void setFId(Integer FId) {
        this.FId = FId;
    }
    
    @Column(name="FK_Task_ID")

    public Integer getFkTaskId() {
        return this.fkTaskId;
    }
    
    public void setFkTaskId(Integer fkTaskId) {
        this.fkTaskId = fkTaskId;
    }
    
    @Column(name="F_ContractNumbers", length=50)

    public String getFContractNumbers() {
        return this.FContractNumbers;
    }
    
    public void setFContractNumbers(String FContractNumbers) {
        this.FContractNumbers = FContractNumbers;
    }
    
    @Column(name="F_ProjectType", length=50)

    public String getFProjectType() {
        return this.FProjectType;
    }
    
    public void setFProjectType(String FProjectType) {
        this.FProjectType = FProjectType;
    }
    
    @Column(name="F_ServiceMode", length=50)

    public String getFServiceMode() {
        return this.FServiceMode;
    }
    
    public void setFServiceMode(String FServiceMode) {
        this.FServiceMode = FServiceMode;
    }
    
    @Column(name="F_ServicePerion", length=50)

    public String getFServicePerion() {
        return this.FServicePerion;
    }
    
    public void setFServicePerion(String FServicePerion) {
        this.FServicePerion = FServicePerion;
    }
    
    @Column(name="F_SigningDate", length=50)

    public String getFSigningDate() {
        return this.FSigningDate;
    }
    
    public void setFSigningDate(String FSigningDate) {
        this.FSigningDate = FSigningDate;
    }
    
    @Column(name="F_InvesttScale", length=50)

    public String getFInvesttScale() {
        return this.FInvesttScale;
    }
    
    public void setFInvesttScale(String FInvesttScale) {
        this.FInvesttScale = FInvesttScale;
    }
    
    @Column(name="F_ContractFees", length=50)

    public String getFContractFees() {
        return this.FContractFees;
    }
    
    public void setFContractFees(String FContractFees) {
        this.FContractFees = FContractFees;
    }
    
    @Column(name="F_Others", length=800)

    public String getFOthers() {
        return this.FOthers;
    }
    
    public void setFOthers(String FOthers) {
        this.FOthers = FOthers;
    }
    
    @Column(name="F_MainContract_ID")

    public Integer getFMainContractId() {
        return this.FMainContractId;
    }
    
    public void setFMainContractId(Integer FMainContractId) {
        this.FMainContractId = FMainContractId;
    }
    
    @Column(name="F_EntrustUnit_ID")

    public Integer getFEntrustUnitId() {
        return this.FEntrustUnitId;
    }
    
    public void setFEntrustUnitId(Integer FEntrustUnitId) {
        this.FEntrustUnitId = FEntrustUnitId;
    }
    
    @Column(name="F_EntrustUnit_Name", length=200)

    public String getFEntrustUnitName() {
        return this.FEntrustUnitName;
    }
    
    public void setFEntrustUnitName(String FEntrustUnitName) {
        this.FEntrustUnitName = FEntrustUnitName;
    }
    
    @Column(name="F_ContractName", length=200)

    public String getFContractName() {
        return this.FContractName;
    }
    
    public void setFContractName(String FContractName) {
        this.FContractName = FContractName;
    }
    
    @Column(name="F_Operate_ID")

    public Integer getFOperateId() {
        return this.FOperateId;
    }
    
    public void setFOperateId(Integer FOperateId) {
        this.FOperateId = FOperateId;
    }
    
    @Column(name="F_Operate_Name", length=50)

    public String getFOperateName() {
        return this.FOperateName;
    }
    
    public void setFOperateName(String FOperateName) {
        this.FOperateName = FOperateName;
    }
    
    @Column(name="F_Operate_Date", length=50)

    public String getFOperateDate() {
        return this.FOperateDate;
    }
    
    public void setFOperateDate(String FOperateDate) {
        this.FOperateDate = FOperateDate;
    }
    
    @Column(name="F_State")

    public Integer getFState() {
        return this.FState;
    }
    
    public void setFState(Integer FState) {
        this.FState = FState;
    }
   








}