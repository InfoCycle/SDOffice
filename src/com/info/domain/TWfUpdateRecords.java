package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfUpdateRecords entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_UPDATE_RECORDS", schema = "dbo", catalog = "SDOffice")
public class TWfUpdateRecords implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer fkProcessId;

    private String FModifyInfo;

    // Constructors

    /** default constructor */
    public TWfUpdateRecords() {
    }

    /** minimal constructor */
    public TWfUpdateRecords(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TWfUpdateRecords(Integer FId, Integer fkProcessId, String FModifyInfo) {
        this.FId = FId;
        this.fkProcessId = fkProcessId;
        this.FModifyInfo = FModifyInfo;
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

    @Column(name = "FK_PROCESS_ID")
    public Integer getFkProcessId() {
        return this.fkProcessId;
    }

    public void setFkProcessId(Integer fkProcessId) {
        this.fkProcessId = fkProcessId;
    }

    @Column(name = "F_MODIFY_INFO", length = 3000)
    public String getFModifyInfo() {
        return this.FModifyInfo;
    }

    public void setFModifyInfo(String FModifyInfo) {
        this.FModifyInfo = FModifyInfo;
    }

}