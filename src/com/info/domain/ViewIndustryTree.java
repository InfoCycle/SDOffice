package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewIndustryTree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_IndustryTree", schema = "dbo", catalog = "SDOffice")
public class ViewIndustryTree implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Long rowid;
	private String FName;
	private Integer FParentId;
	private String te;
	private Long orderId;
	private Boolean isleft;

	// Constructors

	/** default constructor */
	public ViewIndustryTree() {
	}

	/** minimal constructor */
	public ViewIndustryTree(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewIndustryTree(Integer FId, Long rowid, String FName,
			Integer FParentId, String te, Long orderId, Boolean isleft) {
		this.FId = FId;
		this.rowid = rowid;
		this.FName = FName;
		this.FParentId = FParentId;
		this.te = te;
		this.orderId = orderId;
		this.isleft = isleft;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID")
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "rowid")
	public Long getRowid() {
		return this.rowid;
	}

	public void setRowid(Long rowid) {
		this.rowid = rowid;
	}

	@Column(name = "F_Name", length = 200)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_Parent_ID")
	public Integer getFParentId() {
		return this.FParentId;
	}

	public void setFParentId(Integer FParentId) {
		this.FParentId = FParentId;
	}

	@Column(name = "TE")
	public String getTe() {
		return this.te;
	}

	public void setTe(String te) {
		this.te = te;
	}

	@Column(name = "OrderID")
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "isleft")
	public Boolean getIsleft() {
		return this.isleft;
	}

	public void setIsleft(Boolean isleft) {
		this.isleft = isleft;
	}

}