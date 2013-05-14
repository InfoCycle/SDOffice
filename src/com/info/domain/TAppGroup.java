package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_GROUP", schema = "dbo", catalog = "SDOffice")
public class TAppGroup implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FName;
	private String FContent;
	private Integer FState;
	private Integer FSort;
	private String FDataOption;
	private Timestamp FCreatetime;

	// Constructors

	/** default constructor */
	public TAppGroup() {
	}

	/** minimal constructor */
	public TAppGroup(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppGroup(Integer FId, String FName, String FContent,
			Integer FState, Integer FSort, String FDataOption,
			Timestamp FCreatetime) {
		this.FId = FId;
		this.FName = FName;
		this.FContent = FContent;
		this.FState = FState;
		this.FSort = FSort;
		this.FDataOption = FDataOption;
		this.FCreatetime = FCreatetime;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_NAME", length = 60)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_CONTENT", length = 200)
	public String getFContent() {
		return this.FContent;
	}

	public void setFContent(String FContent) {
		this.FContent = FContent;
	}

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_DataOption", length = 20)
	public String getFDataOption() {
		return this.FDataOption;
	}

	public void setFDataOption(String FDataOption) {
		this.FDataOption = FDataOption;
	}

	@Column(name = "F_CREATETIME", length = 23)
	public Timestamp getFCreatetime() {
		return this.FCreatetime;
	}

	public void setFCreatetime(Timestamp FCreatetime) {
		this.FCreatetime = FCreatetime;
	}

}