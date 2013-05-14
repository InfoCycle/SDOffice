package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TAppTreeCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_APP_TREE_CODE"
    ,schema="dbo"
    ,catalog="SDOffice"
)

public class TAppTreeCode  implements java.io.Serializable {


    // Fields    

     private Integer FId;
     private Integer FParentId;
     private Integer fkTreeCodeTypeId;
     private String FCode;
     private String FCodeText;
     private Integer FState;
     private Integer FSort;
     private String FCreateTime;


    // Constructors

    /** default constructor */
    public TAppTreeCode() {
    }

	/** minimal constructor */
    public TAppTreeCode(Integer FId, Integer FParentId, Integer fkTreeCodeTypeId, String FCode, String FCodeText) {
        this.FId = FId;
        this.FParentId = FParentId;
        this.fkTreeCodeTypeId = fkTreeCodeTypeId;
        this.FCode = FCode;
        this.FCodeText = FCodeText;
    }
    
    /** full constructor */
    public TAppTreeCode(Integer FId, Integer FParentId, Integer fkTreeCodeTypeId, String FCode, String FCodeText, Integer FState, Integer FSort, String FCreateTime) {
        this.FId = FId;
        this.FParentId = FParentId;
        this.fkTreeCodeTypeId = fkTreeCodeTypeId;
        this.FCode = FCode;
        this.FCodeText = FCodeText;
        this.FState = FState;
        this.FSort = FSort;
        this.FCreateTime = FCreateTime;
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
    
    @Column(name="F_PARENT_ID", nullable=false)

    public Integer getFParentId() {
        return this.FParentId;
    }
    
    public void setFParentId(Integer FParentId) {
        this.FParentId = FParentId;
    }
    
    @Column(name="FK_TREE_CODE_TYPE_ID", nullable=false)

    public Integer getFkTreeCodeTypeId() {
        return this.fkTreeCodeTypeId;
    }
    
    public void setFkTreeCodeTypeId(Integer fkTreeCodeTypeId) {
        this.fkTreeCodeTypeId = fkTreeCodeTypeId;
    }
    
    @Column(name="F_CODE", nullable=false, length=20)

    public String getFCode() {
        return this.FCode;
    }
    
    public void setFCode(String FCode) {
        this.FCode = FCode;
    }
    
    @Column(name="F_CODE_TEXT", nullable=false, length=200)

    public String getFCodeText() {
        return this.FCodeText;
    }
    
    public void setFCodeText(String FCodeText) {
        this.FCodeText = FCodeText;
    }
    
    @Column(name="F_STATE", precision=6, scale=0)

    public Integer getFState() {
        return this.FState;
    }
    
    public void setFState(Integer FState) {
        this.FState = FState;
    }
    
    @Column(name="F_SORT")

    public Integer getFSort() {
        return this.FSort;
    }
    
    public void setFSort(Integer FSort) {
        this.FSort = FSort;
    }
    
    @Column(name="F_CREATE_TIME", length=30)

    public String getFCreateTime() {
        return this.FCreateTime;
    }
    
    public void setFCreateTime(String FCreateTime) {
        this.FCreateTime = FCreateTime;
    }
   








}