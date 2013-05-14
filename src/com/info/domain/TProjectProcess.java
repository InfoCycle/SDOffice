package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProjectProcess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ProjectProcess", schema = "dbo", catalog = "SDOffice")
public class TProjectProcess implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkTaskId;
	private String FNumbers;
	private String FTheFirst;
	private String FTheSecond;
	private String FOther;
	private Integer FJiLuRenId;
	private String FJiLuRenName;
	private String FJiLuTime;
	private String FNote;

	// Constructors

	/** default constructor */
	public TProjectProcess() {
	}

	/** minimal constructor */
	public TProjectProcess(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TProjectProcess(Integer FId, Integer fkTaskId, String FNumbers,
			String FTheFirst, String FTheSecond, String FOther,
			Integer FJiLuRenId, String FJiLuRenName, String FJiLuTime,
			String FNote) {
		this.FId = FId;
		this.fkTaskId = fkTaskId;
		this.FNumbers = FNumbers;
		this.FTheFirst = FTheFirst;
		this.FTheSecond = FTheSecond;
		this.FOther = FOther;
		this.FJiLuRenId = FJiLuRenId;
		this.FJiLuRenName = FJiLuRenName;
		this.FJiLuTime = FJiLuTime;
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

	@Column(name = "FK_Task_ID")
	public Integer getFkTaskId() {
		return this.fkTaskId;
	}

	public void setFkTaskId(Integer fkTaskId) {
		this.fkTaskId = fkTaskId;
	}

	@Column(name = "F_Numbers", length = 200)
	public String getFNumbers() {
		return this.FNumbers;
	}

	public void setFNumbers(String FNumbers) {
		this.FNumbers = FNumbers;
	}

	@Column(name = "F_TheFirst", length = 800)
	public String getFTheFirst() {
		return this.FTheFirst;
	}

	public void setFTheFirst(String FTheFirst) {
		this.FTheFirst = FTheFirst;
	}

	@Column(name = "F_TheSecond", length = 800)
	public String getFTheSecond() {
		return this.FTheSecond;
	}

	public void setFTheSecond(String FTheSecond) {
		this.FTheSecond = FTheSecond;
	}

	@Column(name = "F_Other", length = 800)
	public String getFOther() {
		return this.FOther;
	}

	public void setFOther(String FOther) {
		this.FOther = FOther;
	}

	@Column(name = "F_JiLuRen_ID")
	public Integer getFJiLuRenId() {
		return this.FJiLuRenId;
	}

	public void setFJiLuRenId(Integer FJiLuRenId) {
		this.FJiLuRenId = FJiLuRenId;
	}

	@Column(name = "F_JiLuRen_Name", length = 30)
	public String getFJiLuRenName() {
		return this.FJiLuRenName;
	}

	public void setFJiLuRenName(String FJiLuRenName) {
		this.FJiLuRenName = FJiLuRenName;
	}

	@Column(name = "F_JiLuTime", length = 30)
	public String getFJiLuTime() {
		return this.FJiLuTime;
	}

	public void setFJiLuTime(String FJiLuTime) {
		this.FJiLuTime = FJiLuTime;
	}

	@Column(name = "F_Note", length = 500)
	public String getFNote() {
		return this.FNote;
	}

	public void setFNote(String FNote) {
		this.FNote = FNote;
	}

}