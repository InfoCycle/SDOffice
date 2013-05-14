package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewAppOrgTree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_App_Org_Tree", schema = "dbo", catalog = "SDOffice")
public class ViewAppOrgTree implements java.io.Serializable {

	// Fields

	private Integer rowid;
	private Integer FId;
	private String FName;
	private Integer FParentId;
	private String te;
	private Long orderId;
	private Boolean isleft;
	private Integer FSort;
	private Integer FState;

	// Constructors

	/** default constructor */
	public ViewAppOrgTree() {
	}

	/** minimal constructor */
	public ViewAppOrgTree(Integer rowid) {
		this.rowid = rowid;
	}

	/** full constructor */
	public ViewAppOrgTree(Integer rowid, Integer FId, String FName,
			Integer FParentId, String te, Long orderId, Boolean isleft,
			Integer FSort, Integer FState) {
		this.rowid = rowid;
		this.FId = FId;
		this.FName = FName;
		this.FParentId = FParentId;
		this.te = te;
		this.orderId = orderId;
		this.isleft = isleft;
		this.FSort = FSort;
		this.FState = FState;
	}

	// Property accessors
	@Id
	@Column(name = "rowid")
	public Integer getRowid() {
		return this.rowid;
	}

	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}

	@Column(name = "F_ID", precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Name", length = 100)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_Parent_ID", precision = 9, scale = 0)
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

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

}