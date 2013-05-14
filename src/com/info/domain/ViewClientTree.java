package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewClientTree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_ClientTree", schema = "dbo", catalog = "SDOffice")
public class ViewClientTree implements java.io.Serializable {

	// Fields

	private int rowid;
	private Integer FId;
	private String FName;
	private Integer FParentId;
	private String te;
	private Long orderId;
	private Boolean isleft;
	private Integer isClient;
	private Integer FClientId;
	private Integer FSort;
	private String FShortName;

	// Constructors

	/** default constructor */
	public ViewClientTree() {
	}

	/** minimal constructor */
	public ViewClientTree(int rowid) {
		this.rowid = rowid;
	}

	/** full constructor */
	public ViewClientTree(int rowid, Integer FId, String FName,
			Integer FParentId, String te, Long orderId, Boolean isleft,
			Integer isClient, Integer FClientId, Integer FSort,
			String FShortName) {
		this.rowid = rowid;
		this.FId = FId;
		this.FName = FName;
		this.FParentId = FParentId;
		this.te = te;
		this.orderId = orderId;
		this.isleft = isleft;
		this.isClient = isClient;
		this.FClientId = FClientId;
		this.FSort = FSort;
		this.FShortName = FShortName;
	}

	// Property accessors
	@Id
	@Column(name = "rowid")
	public int getRowid() {
		return this.rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	@Column(name = "F_ID")
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Name", length = 300)
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

	@Column(name = "IsClient")
	public Integer getIsClient() {
		return this.isClient;
	}

	public void setIsClient(Integer isClient) {
		this.isClient = isClient;
	}

	@Column(name = "F_Client_ID")
	public Integer getFClientId() {
		return this.FClientId;
	}

	public void setFClientId(Integer FClientId) {
		this.FClientId = FClientId;
	}

	@Column(name = "F_Sort")
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_Short_Name", length = 150)
	public String getFShortName() {
		return this.FShortName;
	}

	public void setFShortName(String FShortName) {
		this.FShortName = FShortName;
	}

}