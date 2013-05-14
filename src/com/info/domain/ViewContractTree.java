package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewContractTree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_ContractTree", schema = "dbo", catalog = "SDOffice")
public class ViewContractTree implements java.io.Serializable {

	// Fields

	private Long rowid;
	private Integer FId;
	private String FName;
	private Integer FParentId;
	private String te;
	private Long orderId;
	private Boolean isleft;
	private Integer isContract;
	private Integer FContractId;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public ViewContractTree() {
	}

	/** minimal constructor */
	public ViewContractTree(Long rowid) {
		this.rowid = rowid;
	}

	/** full constructor */
	public ViewContractTree(Long rowid, Integer FId, String FName,
			Integer FParentId, String te, Long orderId, Boolean isleft,
			Integer isContract, Integer FContractId, Integer FSort) {
		this.rowid = rowid;
		this.FId = FId;
		this.FName = FName;
		this.FParentId = FParentId;
		this.te = te;
		this.orderId = orderId;
		this.isleft = isleft;
		this.isContract = isContract;
		this.FContractId = FContractId;
		this.FSort = FSort;
	}

	// Property accessors
	@Id
	@Column(name = "rowid")
	public Long getRowid() {
		return this.rowid;
	}

	public void setRowid(Long rowid) {
		this.rowid = rowid;
	}

	@Column(name = "F_ID")
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
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

	@Column(name = "IsContract")
	public Integer getIsContract() {
		return this.isContract;
	}

	public void setIsContract(Integer isContract) {
		this.isContract = isContract;
	}

	@Column(name = "F_Contract_ID")
	public Integer getFContractId() {
		return this.FContractId;
	}

	public void setFContractId(Integer FContractId) {
		this.FContractId = FContractId;
	}

	@Column(name = "F_Sort")
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

}