package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppFunctionGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_FUNCTION_GROUP", schema = "dbo", catalog = "SDOffice")
public class TAppFunctionGroup implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkGroupId;
	private Integer fkFunctionId;

	// Constructors

	/** default constructor */
	public TAppFunctionGroup() {
	}

	/** minimal constructor */
	public TAppFunctionGroup(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppFunctionGroup(Integer FId, Integer fkGroupId,
			Integer fkFunctionId) {
		this.FId = FId;
		this.fkGroupId = fkGroupId;
		this.fkFunctionId = fkFunctionId;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "FK_GROUP_ID", precision = 9, scale = 0)
	public Integer getFkGroupId() {
		return this.fkGroupId;
	}

	public void setFkGroupId(Integer fkGroupId) {
		this.fkGroupId = fkGroupId;
	}

	@Column(name = "FK_FUNCTION_ID", precision = 9, scale = 0)
	public Integer getFkFunctionId() {
		return this.fkFunctionId;
	}

	public void setFkFunctionId(Integer fkFunctionId) {
		this.fkFunctionId = fkFunctionId;
	}

}