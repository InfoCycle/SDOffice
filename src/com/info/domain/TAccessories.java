package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAccessories entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_Accessories", schema = "dbo", catalog = "SDOffice")
public class TAccessories implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkId;
	private byte[] FAccessories;

	// Constructors

	/** default constructor */
	public TAccessories() {
	}

	/** minimal constructor */
	public TAccessories(Integer FId, Integer fkId) {
		this.FId = FId;
		this.fkId = fkId;
	}

	/** full constructor */
	public TAccessories(Integer FId, Integer fkId, byte[] FAccessories) {
		this.FId = FId;
		this.fkId = fkId;
		this.FAccessories = FAccessories;
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

	@Column(name = "FK_ID", nullable = false)
	public Integer getFkId() {
		return this.fkId;
	}

	public void setFkId(Integer fkId) {
		this.fkId = fkId;
	}

	@Column(name = "F_Accessories")
	public byte[] getFAccessories() {
		return this.FAccessories;
	}

	public void setFAccessories(byte[] FAccessories) {
		this.FAccessories = FAccessories;
	}

}