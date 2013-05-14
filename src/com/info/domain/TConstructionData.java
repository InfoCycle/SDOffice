package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TConstructionData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ConstructionData", schema = "dbo", catalog = "SDOffice")
public class TConstructionData implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTitle;
	private Integer FPersonId;
	private String FPersonName;
	private Integer FType;
	private Integer FPublicType;
	private String FContent;
	private String FTypeName;
	private String FDate;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public TConstructionData() {
	}

	/** minimal constructor */
	public TConstructionData(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TConstructionData(Integer FId, String FTitle, Integer FPersonId,
			String FPersonName, Integer FType, Integer FPublicType,
			String FContent, String FTypeName, String FDate, Integer FSort) {
		this.FId = FId;
		this.FTitle = FTitle;
		this.FPersonId = FPersonId;
		this.FPersonName = FPersonName;
		this.FType = FType;
		this.FPublicType = FPublicType;
		this.FContent = FContent;
		this.FTypeName = FTypeName;
		this.FDate = FDate;
		this.FSort = FSort;
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

	@Column(name = "F_Title", length = 200)
	public String getFTitle() {
		return this.FTitle;
	}

	public void setFTitle(String FTitle) {
		this.FTitle = FTitle;
	}

	@Column(name = "F_Person_ID")
	public Integer getFPersonId() {
		return this.FPersonId;
	}

	public void setFPersonId(Integer FPersonId) {
		this.FPersonId = FPersonId;
	}

	@Column(name = "F_Person_Name", length = 30)
	public String getFPersonName() {
		return this.FPersonName;
	}

	public void setFPersonName(String FPersonName) {
		this.FPersonName = FPersonName;
	}

	@Column(name = "F_Type")
	public Integer getFType() {
		return this.FType;
	}

	public void setFType(Integer FType) {
		this.FType = FType;
	}

	@Column(name = "F_Public_Type")
	public Integer getFPublicType() {
		return this.FPublicType;
	}

	public void setFPublicType(Integer FPublicType) {
		this.FPublicType = FPublicType;
	}

	@Column(name = "F_Content", length = 2000)
	public String getFContent() {
		return this.FContent;
	}

	public void setFContent(String FContent) {
		this.FContent = FContent;
	}

	@Column(name = "F_Type_Name", length = 100)
	public String getFTypeName() {
		return this.FTypeName;
	}

	public void setFTypeName(String FTypeName) {
		this.FTypeName = FTypeName;
	}

	@Column(name = "F_Date", length = 30)
	public String getFDate() {
		return this.FDate;
	}

	public void setFDate(String FDate) {
		this.FDate = FDate;
	}

	@Column(name = "F_Sort")
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

}