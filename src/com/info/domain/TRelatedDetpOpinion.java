package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TRelatedDetpOpinion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RelatedDetpOpinion", schema = "dbo", catalog = "SDOffice")
public class TRelatedDetpOpinion implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer fkMajorMattersReportId;

    private Integer FDeptId;

    private String FDeptName;

    private String FDeptOpinion;

    private Integer FUserId;

    private String FUserName;

    private String FNote;

    // Constructors

    /** default constructor */
    public TRelatedDetpOpinion() {
    }

    /** minimal constructor */
    public TRelatedDetpOpinion(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TRelatedDetpOpinion(Integer FId, Integer fkMajorMattersReportId, Integer FDeptId, String FDeptName,
            String FDeptOpinion, Integer FUserId, String FUserName, String FNote) {
        this.FId = FId;
        this.fkMajorMattersReportId = fkMajorMattersReportId;
        this.FDeptId = FDeptId;
        this.FDeptName = FDeptName;
        this.FDeptOpinion = FDeptOpinion;
        this.FUserId = FUserId;
        this.FUserName = FUserName;
        this.FNote = FNote;
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

    @Column(name = "FK_MajorMattersReport_ID")
    public Integer getFkMajorMattersReportId() {
        return this.fkMajorMattersReportId;
    }

    public void setFkMajorMattersReportId(Integer fkMajorMattersReportId) {
        this.fkMajorMattersReportId = fkMajorMattersReportId;
    }

    @Column(name = "F_Dept_ID")
    public Integer getFDeptId() {
        return this.FDeptId;
    }

    public void setFDeptId(Integer FDeptId) {
        this.FDeptId = FDeptId;
    }

    @Column(name = "F_Dept_Name", length = 100)
    public String getFDeptName() {
        return this.FDeptName;
    }

    public void setFDeptName(String FDeptName) {
        this.FDeptName = FDeptName;
    }

    @Column(name = "F_Dept_Opinion", length = 2000)
    public String getFDeptOpinion() {
        return this.FDeptOpinion;
    }

    public void setFDeptOpinion(String FDeptOpinion) {
        this.FDeptOpinion = FDeptOpinion;
    }

    @Column(name = "F_User_ID")
    public Integer getFUserId() {
        return this.FUserId;
    }

    public void setFUserId(Integer FUserId) {
        this.FUserId = FUserId;
    }

    @Column(name = "F_User_Name", length = 50)
    public String getFUserName() {
        return this.FUserName;
    }

    public void setFUserName(String FUserName) {
        this.FUserName = FUserName;
    }

    @Column(name = "F_Note", length = 500)
    public String getFNote() {
        return this.FNote;
    }

    public void setFNote(String FNote) {
        this.FNote = FNote;
    }

}