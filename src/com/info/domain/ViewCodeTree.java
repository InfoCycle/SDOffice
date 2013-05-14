package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewCodeTree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_CodeTree", schema = "dbo", catalog = "SDOffice")
public class ViewCodeTree implements java.io.Serializable {

	// Fields

	private Integer rowid;
	private Integer FId;
	private String FCode;
	private String FCodeText;
	private Integer FParentId;
	private Integer fkTreeCodeTypeId;
	private String te;
	private Long orderId;
	private Boolean isleft;
	private Integer FState;
	private Long FSort;
	private String FCreateTime;

	// Constructors

	/** default constructor */
	public ViewCodeTree() {
	}

	/** minimal constructor */
	public ViewCodeTree(Integer rowid) {
		this.rowid = rowid;
	}

	/** full constructor */
	public ViewCodeTree(Integer rowid, Integer FId, String FCode,
			String FCodeText, Integer FParentId, Integer fkTreeCodeTypeId,
			String te, Long orderId, Boolean isleft, Integer FState,
			Long FSort, String FCreateTime) {
		this.rowid = rowid;
		this.FId = FId;
		this.FCode = FCode;
		this.FCodeText = FCodeText;
		this.FParentId = FParentId;
		this.fkTreeCodeTypeId = fkTreeCodeTypeId;
		this.te = te;
		this.orderId = orderId;
		this.isleft = isleft;
		this.FState = FState;
		this.FSort = FSort;
		this.FCreateTime = FCreateTime;
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

	@Column(name = "F_Code", length = 50)
	public String getFCode() {
		return this.FCode;
	}

	public void setFCode(String FCode) {
		this.FCode = FCode;
	}

	@Column(name = "F_Code_Text", length = 50)
	public String getFCodeText() {
		return this.FCodeText;
	}

	public void setFCodeText(String FCodeText) {
		this.FCodeText = FCodeText;
	}

	@Column(name = "F_Parent_ID")
	public Integer getFParentId() {
		return this.FParentId;
	}

	public void setFParentId(Integer FParentId) {
		this.FParentId = FParentId;
	}

	@Column(name = "FK_Tree_Code_Type_Id")
	public Integer getFkTreeCodeTypeId() {
		return this.fkTreeCodeTypeId;
	}

	public void setFkTreeCodeTypeId(Integer fkTreeCodeTypeId) {
		this.fkTreeCodeTypeId = fkTreeCodeTypeId;
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

	@Column(name = "F_State", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_Sort", precision = 10, scale = 0)
	public Long getFSort() {
		return this.FSort;
	}

	public void setFSort(Long FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_Create_Time", length = 50)
	public String getFCreateTime() {
		return this.FCreateTime;
	}

	public void setFCreateTime(String FCreateTime) {
		this.FCreateTime = FCreateTime;
	}

}