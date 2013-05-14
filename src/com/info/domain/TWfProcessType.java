package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfProcessType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_PROCESS_TYPE", schema = "dbo", catalog = "SDOffice")
public class TWfProcessType implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTypeName;
	private String FIcon;
	private String FFormUrl;
	private Integer FSort;
	private Integer FState;
	private String FRemark;

	// Constructors

	/** default constructor */
	public TWfProcessType() {
	}

	/** minimal constructor */
	public TWfProcessType(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TWfProcessType(Integer FId, String FTypeName, String FIcon,
			String FFormUrl, Integer FSort, Integer FState, String FRemark) {
		this.FId = FId;
		this.FTypeName = FTypeName;
		this.FIcon = FIcon;
		this.FFormUrl = FFormUrl;
		this.FSort = FSort;
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

	@Column(name = "F_TYPE_NAME", length = 250)
	public String getFTypeName() {
		return this.FTypeName;
	}

	public void setFTypeName(String FTypeName) {
		this.FTypeName = FTypeName;
	}

	@Column(name = "F_ICON", length = 350)
	public String getFIcon() {
		return this.FIcon;
	}

	public void setFIcon(String FIcon) {
		this.FIcon = FIcon;
	}

	@Column(name = "F_FormUrl", length = 850)
	public String getFFormUrl() {
		return this.FFormUrl;
	}

	public void setFFormUrl(String FFormUrl) {
		this.FFormUrl = FFormUrl;
	}

	@Column(name = "F_SORT")
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_State")
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_Remark", length = 550)
	public String getFRemark() {
		return this.FRemark;
	}

	public void setFRemark(String FRemark) {
		this.FRemark = FRemark;
	}

}