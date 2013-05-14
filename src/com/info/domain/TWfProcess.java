package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfProcess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_PROCESS", schema = "dbo", catalog = "SDOffice")
public class TWfProcess implements java.io.Serializable {

    // Fields

    private Integer FId;

    private String FTitle;

    private Integer FTypeId;

    private Integer FCreateUserId;

    private Integer FCreateStation;

    private Integer FCreateOrgId;

    private String FCreateTime;

    private Integer FCurrentUserId;

    private Integer FState;

    private Integer FFormPkid;

    private String FCompleteTime;

    private String FArchiveTime;

    private Integer FArchiveUser;

    private Integer FIsHistory;

    // Constructors

    /** default constructor */
    public TWfProcess() {
    }

    /** minimal constructor */
    public TWfProcess(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TWfProcess(Integer FId, String FTitle, Integer FTypeId, Integer FCreateUserId, Integer FCreateStation,
            Integer FCreateOrgId, String FCreateTime, Integer FCurrentUserId, Integer FState, Integer FFormPkid,
            String FCompleteTime, String FArchiveTime, Integer FArchiveUser, Integer FIsHistory) {
        this.FId = FId;
        this.FTitle = FTitle;
        this.FTypeId = FTypeId;
        this.FCreateUserId = FCreateUserId;
        this.FCreateStation = FCreateStation;
        this.FCreateOrgId = FCreateOrgId;
        this.FCreateTime = FCreateTime;
        this.FCurrentUserId = FCurrentUserId;
        this.FState = FState;
        this.FFormPkid = FFormPkid;
        this.FCompleteTime = FCompleteTime;
        this.FArchiveTime = FArchiveTime;
        this.FArchiveUser = FArchiveUser;
        this.FIsHistory = FIsHistory;
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

    @Column(name = "F_TITLE", length = 800)
    public String getFTitle() {
        return this.FTitle;
    }

    public void setFTitle(String FTitle) {
        this.FTitle = FTitle;
    }

    @Column(name = "F_TYPE_ID")
    public Integer getFTypeId() {
        return this.FTypeId;
    }

    public void setFTypeId(Integer FTypeId) {
        this.FTypeId = FTypeId;
    }

    @Column(name = "F_CreateUserID")
    public Integer getFCreateUserId() {
        return this.FCreateUserId;
    }

    public void setFCreateUserId(Integer FCreateUserId) {
        this.FCreateUserId = FCreateUserId;
    }

    @Column(name = "F_CreateStation")
    public Integer getFCreateStation() {
        return this.FCreateStation;
    }

    public void setFCreateStation(Integer FCreateStation) {
        this.FCreateStation = FCreateStation;
    }

    @Column(name = "F_CreateOrgId")
    public Integer getFCreateOrgId() {
        return this.FCreateOrgId;
    }

    public void setFCreateOrgId(Integer FCreateOrgId) {
        this.FCreateOrgId = FCreateOrgId;
    }

    @Column(name = "F_CreateTime", length = 30)
    public String getFCreateTime() {
        return this.FCreateTime;
    }

    public void setFCreateTime(String FCreateTime) {
        this.FCreateTime = FCreateTime;
    }

    @Column(name = "F_CurrentUserID")
    public Integer getFCurrentUserId() {
        return this.FCurrentUserId;
    }

    public void setFCurrentUserId(Integer FCurrentUserId) {
        this.FCurrentUserId = FCurrentUserId;
    }

    @Column(name = "F_STATE")
    public Integer getFState() {
        return this.FState;
    }

    public void setFState(Integer FState) {
        this.FState = FState;
    }

    @Column(name = "F_Form_PKID")
    public Integer getFFormPkid() {
        return this.FFormPkid;
    }

    public void setFFormPkid(Integer FFormPkid) {
        this.FFormPkid = FFormPkid;
    }

    @Column(name = "F_Complete_Time", length = 30)
    public String getFCompleteTime() {
        return this.FCompleteTime;
    }

    public void setFCompleteTime(String FCompleteTime) {
        this.FCompleteTime = FCompleteTime;
    }

    @Column(name = "F_Archive_Time", length = 30)
    public String getFArchiveTime() {
        return this.FArchiveTime;
    }

    public void setFArchiveTime(String FArchiveTime) {
        this.FArchiveTime = FArchiveTime;
    }

    @Column(name = "F_ArchiveUser")
    public Integer getFArchiveUser() {
        return this.FArchiveUser;
    }

    public void setFArchiveUser(Integer FArchiveUser) {
        this.FArchiveUser = FArchiveUser;
    }

    @Column(name = "F_isHistory")
    public Integer getFIsHistory() {
        return this.FIsHistory;
    }

    public void setFIsHistory(Integer FIsHistory) {
        this.FIsHistory = FIsHistory;
    }

}