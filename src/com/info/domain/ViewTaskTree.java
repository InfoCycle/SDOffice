package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewTaskTree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_TaskTree", schema = "dbo", catalog = "SDOffice")
public class ViewTaskTree implements java.io.Serializable {

	// Fields

	private Integer rowid;
	private Integer FId;
	private String FName;
	private Integer FParentId;
	private String te;
	private Long orderId;
	private Boolean isleft;
	private Integer isTask;
	private Integer FTaskId;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public ViewTaskTree() {
	}

	/** minimal constructor */
	public ViewTaskTree(Integer rowid) {
		this.rowid = rowid;
	}

	/** full constructor */
	public ViewTaskTree(Integer rowid, Integer FId, String FName,
			Integer FParentId, String te, Long orderId, Boolean isleft,
			Integer isTask, Integer FTaskId, Integer FSort) {
		this.rowid = rowid;
		this.FId = FId;
		this.FName = FName;
		this.FParentId = FParentId;
		this.te = te;
		this.orderId = orderId;
		this.isleft = isleft;
		this.isTask = isTask;
		this.FTaskId = FTaskId;
		this.FSort = FSort;
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

	@Column(name = "IsTask")
	public Integer getIsTask() {
		return this.isTask;
	}

	public void setIsTask(Integer isTask) {
		this.isTask = isTask;
	}

	@Column(name = "F_Task_ID")
	public Integer getFTaskId() {
		return this.FTaskId;
	}

	public void setFTaskId(Integer FTaskId) {
		this.FTaskId = FTaskId;
	}

	@Column(name = "F_Sort")
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

}