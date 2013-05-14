package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProjectResultsFile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Project_ResultsFile", schema = "dbo", catalog = "SDOffice")
public class TProjectResultsFile implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkCheckReviewId;
	private String FResultsFileType;
	private String FFileName;
	private String FFileType;
	private Double FSize;
	private String FState;
	private Integer FLoadUserId;
	private String FLoadUserName;
	private String FLoadDateTime;

	// Constructors

	/** default constructor */
	public TProjectResultsFile() {
	}

	/** minimal constructor */
	public TProjectResultsFile(Integer FId, Integer fkCheckReviewId) {
		this.FId = FId;
		this.fkCheckReviewId = fkCheckReviewId;
	}

	/** full constructor */
	public TProjectResultsFile(Integer FId, Integer fkCheckReviewId,
			String FResultsFileType, String FFileName, String FFileType,
			Double FSize, String FState, Integer FLoadUserId,
			String FLoadUserName, String FLoadDateTime) {
		this.FId = FId;
		this.fkCheckReviewId = fkCheckReviewId;
		this.FResultsFileType = FResultsFileType;
		this.FFileName = FFileName;
		this.FFileType = FFileType;
		this.FSize = FSize;
		this.FState = FState;
		this.FLoadUserId = FLoadUserId;
		this.FLoadUserName = FLoadUserName;
		this.FLoadDateTime = FLoadDateTime;
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

	@Column(name = "FK_CheckReview_ID", nullable = false)
	public Integer getFkCheckReviewId() {
		return this.fkCheckReviewId;
	}

	public void setFkCheckReviewId(Integer fkCheckReviewId) {
		this.fkCheckReviewId = fkCheckReviewId;
	}

	@Column(name = "F_ResultsFile_Type", length = 50)
	public String getFResultsFileType() {
		return this.FResultsFileType;
	}

	public void setFResultsFileType(String FResultsFileType) {
		this.FResultsFileType = FResultsFileType;
	}

	@Column(name = "F_File_Name", length = 200)
	public String getFFileName() {
		return this.FFileName;
	}

	public void setFFileName(String FFileName) {
		this.FFileName = FFileName;
	}

	@Column(name = "F_File_Type", length = 30)
	public String getFFileType() {
		return this.FFileType;
	}

	public void setFFileType(String FFileType) {
		this.FFileType = FFileType;
	}

	@Column(name = "F_Size", precision = 53, scale = 0)
	public Double getFSize() {
		return this.FSize;
	}

	public void setFSize(Double FSize) {
		this.FSize = FSize;
	}

	@Column(name = "F_State", length = 10)
	public String getFState() {
		return this.FState;
	}

	public void setFState(String FState) {
		this.FState = FState;
	}

	@Column(name = "F_Load_User_ID")
	public Integer getFLoadUserId() {
		return this.FLoadUserId;
	}

	public void setFLoadUserId(Integer FLoadUserId) {
		this.FLoadUserId = FLoadUserId;
	}

	@Column(name = "F_Load_User_Name", length = 30)
	public String getFLoadUserName() {
		return this.FLoadUserName;
	}

	public void setFLoadUserName(String FLoadUserName) {
		this.FLoadUserName = FLoadUserName;
	}

	@Column(name = "F_Load_DateTime", length = 30)
	public String getFLoadDateTime() {
		return this.FLoadDateTime;
	}

	public void setFLoadDateTime(String FLoadDateTime) {
		this.FLoadDateTime = FLoadDateTime;
	}

}