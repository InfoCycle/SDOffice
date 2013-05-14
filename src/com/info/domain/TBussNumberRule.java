package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TBussNumberRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Buss_Number_Rule", schema = "dbo", catalog = "SDOffice")
public class TBussNumberRule implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FNumberRuleType;
	private String FNumberRuleNmae;
	private String FNumberRuleCode;
	private String FItem1;
	private String FItem2;
	private String FItem3;
	private String FRemark;

	// Constructors

	/** default constructor */
	public TBussNumberRule() {
	}

	/** minimal constructor */
	public TBussNumberRule(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TBussNumberRule(Integer FId, String FNumberRuleType,
			String FNumberRuleNmae, String FNumberRuleCode, String FItem1,
			String FItem2, String FItem3, String FRemark) {
		this.FId = FId;
		this.FNumberRuleType = FNumberRuleType;
		this.FNumberRuleNmae = FNumberRuleNmae;
		this.FNumberRuleCode = FNumberRuleCode;
		this.FItem1 = FItem1;
		this.FItem2 = FItem2;
		this.FItem3 = FItem3;
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

	@Column(name = "F_NumberRule_Type", length = 20)
	public String getFNumberRuleType() {
		return this.FNumberRuleType;
	}

	public void setFNumberRuleType(String FNumberRuleType) {
		this.FNumberRuleType = FNumberRuleType;
	}

	@Column(name = "F_NumberRule_Nmae", length = 200)
	public String getFNumberRuleNmae() {
		return this.FNumberRuleNmae;
	}

	public void setFNumberRuleNmae(String FNumberRuleNmae) {
		this.FNumberRuleNmae = FNumberRuleNmae;
	}

	@Column(name = "F_NumberRule_Code", length = 20)
	public String getFNumberRuleCode() {
		return this.FNumberRuleCode;
	}

	public void setFNumberRuleCode(String FNumberRuleCode) {
		this.FNumberRuleCode = FNumberRuleCode;
	}

	@Column(name = "F_Item1", length = 50)
	public String getFItem1() {
		return this.FItem1;
	}

	public void setFItem1(String FItem1) {
		this.FItem1 = FItem1;
	}

	@Column(name = "F_Item2", length = 50)
	public String getFItem2() {
		return this.FItem2;
	}

	public void setFItem2(String FItem2) {
		this.FItem2 = FItem2;
	}

	@Column(name = "F_Item3", length = 50)
	public String getFItem3() {
		return this.FItem3;
	}

	public void setFItem3(String FItem3) {
		this.FItem3 = FItem3;
	}

	@Column(name = "F_Remark", length = 500)
	public String getFRemark() {
		return this.FRemark;
	}

	public void setFRemark(String FRemark) {
		this.FRemark = FRemark;
	}

}