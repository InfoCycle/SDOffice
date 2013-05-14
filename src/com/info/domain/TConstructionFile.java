package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TConstructionFile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ConstructionFile", schema = "dbo", catalog = "SDOffice")
public class TConstructionFile implements java.io.Serializable {

    // Fields

    private Integer FId;
    private Integer fkId;
    private String FFileType;
    private String FFileName;
    private Double FFileSize;
    private String FState;
    private String FLoadDate;
    private String FFilePath;

    // Constructors

    /** default constructor */
    public TConstructionFile() {
    }

    /** minimal constructor */
    public TConstructionFile(Integer FId, Integer fkId) {
	this.FId = FId;
	this.fkId = fkId;
    }

    /** full constructor */
    public TConstructionFile(Integer FId, Integer fkId, String FFileType,
	    String FFileName, Double FFileSize, String FState,
	    String FLoadDate, String FFilePath) {
	this.FId = FId;
	this.fkId = fkId;
	this.FFileType = FFileType;
	this.FFileName = FFileName;
	this.FFileSize = FFileSize;
	this.FState = FState;
	this.FLoadDate = FLoadDate;
	this.FFilePath = FFilePath;
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

    @Column(name = "FK_ID", nullable = false)
    public Integer getFkId() {
	return this.fkId;
    }

    public void setFkId(Integer fkId) {
	this.fkId = fkId;
    }

    @Column(name = "F_File_Type", length = 50)
    public String getFFileType() {
	return this.FFileType;
    }

    public void setFFileType(String FFileType) {
	this.FFileType = FFileType;
    }

    @Column(name = "F_File_Name", length = 200)
    public String getFFileName() {
	return this.FFileName;
    }

    public void setFFileName(String FFileName) {
	this.FFileName = FFileName;
    }

    @Column(name = "F_File_Size", precision = 53, scale = 0)
    public Double getFFileSize() {
	return this.FFileSize;
    }

    public void setFFileSize(Double FFileSize) {
	this.FFileSize = FFileSize;
    }

    @Column(name = "F_State", length = 10)
    public String getFState() {
	return this.FState;
    }

    public void setFState(String FState) {
	this.FState = FState;
    }

    @Column(name = "F_Load_Date", length = 30)
    public String getFLoadDate() {
	return this.FLoadDate;
    }

    public void setFLoadDate(String FLoadDate) {
	this.FLoadDate = FLoadDate;
    }

    @Column(name = "F_File_Path", length = 300)
    public String getFFilePath() {
	return this.FFilePath;
    }

    public void setFFilePath(String FFilePath) {
	this.FFilePath = FFilePath;
    }

}