package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProjectCheck entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ProjectCheck", schema = "dbo", catalog = "SDOffice")
public class TProjectCheck implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer FId;
	private String FNumbers;
	private Integer fkTaskId;
	private String FExecutePhase;
	private String FInspectionRecord;
	private String FEpapo;
	private String FTcwcr;
	private Integer FBcheckPersonId;
	private String FBcheckPersonName;
	private String FBeCheckedTime;
	private Integer FCheckPersonId;
	private String FCheckPersonName;
	private String FCheckPersonTime;
	private String FEpr;
	private Integer FEprCheckPersonId;
	private String FEprCheckPersonName;
	private String FEprCheckPersonTime;

	// Constructors

	/** default constructor */
	public TProjectCheck() {
	}

	/** minimal constructor */
	public TProjectCheck(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TProjectCheck(Integer FId, String FNumbers, Integer fkTaskId,
			String FExecutePhase, String FInspectionRecord, String FEpapo,
			String FTcwcr, Integer FBcheckPersonId, String FBcheckPersonName,
			String FBeCheckedTime, Integer FCheckPersonId,
			String FCheckPersonName, String FCheckPersonTime, String FEpr,
			Integer FEprCheckPersonId, String FEprCheckPersonName,
			String FEprCheckPersonTime) {
		this.FId = FId;
		this.FNumbers = FNumbers;
		this.fkTaskId = fkTaskId;
		this.FExecutePhase = FExecutePhase;
		this.FInspectionRecord = FInspectionRecord;
		this.FEpapo = FEpapo;
		this.FTcwcr = FTcwcr;
		this.FBcheckPersonId = FBcheckPersonId;
		this.FBcheckPersonName = FBcheckPersonName;
		this.FBeCheckedTime = FBeCheckedTime;
		this.FCheckPersonId = FCheckPersonId;
		this.FCheckPersonName = FCheckPersonName;
		this.FCheckPersonTime = FCheckPersonTime;
		this.FEpr = FEpr;
		this.FEprCheckPersonId = FEprCheckPersonId;
		this.FEprCheckPersonName = FEprCheckPersonName;
		this.FEprCheckPersonTime = FEprCheckPersonTime;
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

	@Column(name = "F_Numbers", length = 30)
	public String getFNumbers() {
		return this.FNumbers;
	}

	public void setFNumbers(String FNumbers) {
		this.FNumbers = FNumbers;
	}

	@Column(name = "FK_Task_ID")
	public Integer getFkTaskId() {
		return this.fkTaskId;
	}

	public void setFkTaskId(Integer fkTaskId) {
		this.fkTaskId = fkTaskId;
	}

	@Column(name = "F_Execute_Phase", length = 300)
	public String getFExecutePhase() {
		return this.FExecutePhase;
	}

	public void setFExecutePhase(String FExecutePhase) {
		this.FExecutePhase = FExecutePhase;
	}

	@Column(name = "F_Inspection_Record", length = 800)
	public String getFInspectionRecord() {
		return this.FInspectionRecord;
	}

	public void setFInspectionRecord(String FInspectionRecord) {
		this.FInspectionRecord = FInspectionRecord;
	}

	@Column(name = "F_EPAPO", length = 800)
	public String getFEpapo() {
		return this.FEpapo;
	}

	public void setFEpapo(String FEpapo) {
		this.FEpapo = FEpapo;
	}

	@Column(name = "F_TCWCR", length = 800)
	public String getFTcwcr() {
		return this.FTcwcr;
	}

	public void setFTcwcr(String FTcwcr) {
		this.FTcwcr = FTcwcr;
	}

	@Column(name = "F_BCheckPerson_ID")
	public Integer getFBcheckPersonId() {
		return this.FBcheckPersonId;
	}

	public void setFBcheckPersonId(Integer FBcheckPersonId) {
		this.FBcheckPersonId = FBcheckPersonId;
	}

	@Column(name = "F_BCheckPerson_Name", length = 30)
	public String getFBcheckPersonName() {
		return this.FBcheckPersonName;
	}

	public void setFBcheckPersonName(String FBcheckPersonName) {
		this.FBcheckPersonName = FBcheckPersonName;
	}

	@Column(name = "F_BeChecked_Time", length = 30)
	public String getFBeCheckedTime() {
		return this.FBeCheckedTime;
	}

	public void setFBeCheckedTime(String FBeCheckedTime) {
		this.FBeCheckedTime = FBeCheckedTime;
	}

	@Column(name = "F_CheckPerson_ID")
	public Integer getFCheckPersonId() {
		return this.FCheckPersonId;
	}

	public void setFCheckPersonId(Integer FCheckPersonId) {
		this.FCheckPersonId = FCheckPersonId;
	}

	@Column(name = "F_CheckPerson_Name", length = 30)
	public String getFCheckPersonName() {
		return this.FCheckPersonName;
	}

	public void setFCheckPersonName(String FCheckPersonName) {
		this.FCheckPersonName = FCheckPersonName;
	}

	@Column(name = "F_CheckPerson_Time", length = 30)
	public String getFCheckPersonTime() {
		return this.FCheckPersonTime;
	}

	public void setFCheckPersonTime(String FCheckPersonTime) {
		this.FCheckPersonTime = FCheckPersonTime;
	}

	@Column(name = "F_EPR", length = 800)
	public String getFEpr() {
		return this.FEpr;
	}

	public void setFEpr(String FEpr) {
		this.FEpr = FEpr;
	}

	@Column(name = "F_EPR_CheckPerson_ID")
	public Integer getFEprCheckPersonId() {
		return this.FEprCheckPersonId;
	}

	public void setFEprCheckPersonId(Integer FEprCheckPersonId) {
		this.FEprCheckPersonId = FEprCheckPersonId;
	}

	@Column(name = "F_EPR_CheckPerson_Name", length = 30)
	public String getFEprCheckPersonName() {
		return this.FEprCheckPersonName;
	}

	public void setFEprCheckPersonName(String FEprCheckPersonName) {
		this.FEprCheckPersonName = FEprCheckPersonName;
	}

	@Column(name = "F_EPR_CheckPerson_Time", length = 30)
	public String getFEprCheckPersonTime() {
		return this.FEprCheckPersonTime;
	}

	public void setFEprCheckPersonTime(String FEprCheckPersonTime) {
		this.FEprCheckPersonTime = FEprCheckPersonTime;
	}

}