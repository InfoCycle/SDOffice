package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTeamPerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TeamPerson", schema = "dbo", catalog = "SDOffice")
public class TTeamPerson implements java.io.Serializable {

    // Fields

    private Integer FId;

    private Integer fkImplementPlanId;

    private Integer FPersonnelId;

    private String FPersonnelName;

    private String FJobContent;

    private String FAsPosition;

    private String FContactPhone;

    private String FNote;

    // Constructors

    /** default constructor */
    public TTeamPerson() {
    }

    /** minimal constructor */
    public TTeamPerson(Integer FId) {
        this.FId = FId;
    }

    /** full constructor */
    public TTeamPerson(Integer FId, Integer fkImplementPlanId, Integer FPersonnelId, String FPersonnelName,
            String FJobContent, String FAsPosition, String FContactPhone, String FNote) {
        this.FId = FId;
        this.fkImplementPlanId = fkImplementPlanId;
        this.FPersonnelId = FPersonnelId;
        this.FPersonnelName = FPersonnelName;
        this.FJobContent = FJobContent;
        this.FAsPosition = FAsPosition;
        this.FContactPhone = FContactPhone;
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

    @Column(name = "FK_ImplementPlan_ID")
    public Integer getFkImplementPlanId() {
        return this.fkImplementPlanId;
    }

    public void setFkImplementPlanId(Integer fkImplementPlanId) {
        this.fkImplementPlanId = fkImplementPlanId;
    }

    @Column(name = "F_Personnel_ID")
    public Integer getFPersonnelId() {
        return this.FPersonnelId;
    }

    public void setFPersonnelId(Integer FPersonnelId) {
        this.FPersonnelId = FPersonnelId;
    }

    @Column(name = "F_Personnel_Name", length = 30)
    public String getFPersonnelName() {
        return this.FPersonnelName;
    }

    public void setFPersonnelName(String FPersonnelName) {
        this.FPersonnelName = FPersonnelName;
    }

    @Column(name = "F_Job_Content", length = 500)
    public String getFJobContent() {
        return this.FJobContent;
    }

    public void setFJobContent(String FJobContent) {
        this.FJobContent = FJobContent;
    }

    @Column(name = "F_As_Position", length = 50)
    public String getFAsPosition() {
        return this.FAsPosition;
    }

    public void setFAsPosition(String FAsPosition) {
        this.FAsPosition = FAsPosition;
    }

    @Column(name = "F_Contact_Phone", length = 100)
    public String getFContactPhone() {
        return this.FContactPhone;
    }

    public void setFContactPhone(String FContactPhone) {
        this.FContactPhone = FContactPhone;
    }

    @Column(name = "F_Note", length = 500)
    public String getFNote() {
        return this.FNote;
    }

    public void setFNote(String FNote) {
        this.FNote = FNote;
    }

}