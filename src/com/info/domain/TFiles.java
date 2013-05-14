package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TFiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Files", schema = "dbo", catalog = "SDOffice")
public class TFiles implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FTypeId;
	private String FType;
	private String FFileName;
	private String FPath;
	private Long FSize;
	private String FContentType;
	private String FDate;

	// Constructors

	/** default constructor */
	public TFiles() {
	}

	/** minimal constructor */
	public TFiles(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TFiles(Integer FId, Integer FTypeId, String FType, String FFileName,
			String FPath, Long FSize, String FContentType, String FDate) {
		this.FId = FId;
		this.FTypeId = FTypeId;
		this.FType = FType;
		this.FFileName = FFileName;
		this.FPath = FPath;
		this.FSize = FSize;
		this.FContentType = FContentType;
		this.FDate = FDate;
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

	@Column(name = "F_TypeID")
	public Integer getFTypeId() {
		return this.FTypeId;
	}

	public void setFTypeId(Integer FTypeId) {
		this.FTypeId = FTypeId;
	}

	@Column(name = "F_Type", length = 50)
	public String getFType() {
		return this.FType;
	}

	public void setFType(String FType) {
		this.FType = FType;
	}

	@Column(name = "F_FileName", length = 100)
	public String getFFileName() {
		return this.FFileName;
	}

	public void setFFileName(String FFileName) {
		this.FFileName = FFileName;
	}

	@Column(name = "F_Path", length = 200)
	public String getFPath() {
		return this.FPath;
	}

	public void setFPath(String FPath) {
		this.FPath = FPath;
	}

	@Column(name = "F_Size")
	public Long getFSize() {
		return this.FSize;
	}

	public void setFSize(Long FSize) {
		this.FSize = FSize;
	}

	@Column(name = "F_ContentType", length = 20)
	public String getFContentType() {
		return this.FContentType;
	}

	public void setFContentType(String FContentType) {
		this.FContentType = FContentType;
	}

	@Column(name = "F_Date", length = 30)
	public String getFDate() {
		return this.FDate;
	}

	public void setFDate(String FDate) {
		this.FDate = FDate;
	}

}