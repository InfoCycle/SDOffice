package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfProcessSend entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_PROCESS_SEND", schema = "dbo", catalog = "SDOffice")
public class TWfProcessSend implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer FProcessId;

    private Integer FSendUser;

    private String FSendTime;

    private Integer FAcceptUser;

    private String FAcceptTime;

    private Integer FState;

    private String FRemark;

    // Constructors

    /** default constructor */
    public TWfProcessSend() {
    }

    /** minimal constructor */
    public TWfProcessSend(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TWfProcessSend(Integer FId, Integer FProcessId, Integer FSendUser, String FSendTime, Integer FAcceptUser,
            String FAcceptTime, Integer FState, String FRemark) {
        this.FId = FId;
        this.FProcessId = FProcessId;
        this.FSendUser = FSendUser;
        this.FSendTime = FSendTime;
        this.FAcceptUser = FAcceptUser;
        this.FAcceptTime = FAcceptTime;
        this.FState = FState;
        this.FRemark = FRemark;
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

    @Column(name = "F_PROCESS_ID")
    public Integer getFProcessId() {
        return this.FProcessId;
    }

    public void setFProcessId(Integer FProcessId) {
        this.FProcessId = FProcessId;
    }

    @Column(name = "F_SEND_USER")
    public Integer getFSendUser() {
        return this.FSendUser;
    }

    public void setFSendUser(Integer FSendUser) {
        this.FSendUser = FSendUser;
    }

    @Column(name = "F_SEND_TIME", length = 30)
    public String getFSendTime() {
        return this.FSendTime;
    }

    public void setFSendTime(String FSendTime) {
        this.FSendTime = FSendTime;
    }

    @Column(name = "F_ACCEPT_USER")
    public Integer getFAcceptUser() {
        return this.FAcceptUser;
    }

    public void setFAcceptUser(Integer FAcceptUser) {
        this.FAcceptUser = FAcceptUser;
    }

    @Column(name = "F_ACCEPT_TIME", length = 30)
    public String getFAcceptTime() {
        return this.FAcceptTime;
    }

    public void setFAcceptTime(String FAcceptTime) {
        this.FAcceptTime = FAcceptTime;
    }

    @Column(name = "F_STATE")
    public Integer getFState() {
        return this.FState;
    }

    public void setFState(Integer FState) {
        this.FState = FState;
    }

    @Column(name = "F_REMARK", length = 800)
    public String getFRemark() {
        return this.FRemark;
    }

    public void setFRemark(String FRemark) {
        this.FRemark = FRemark;
    }

}