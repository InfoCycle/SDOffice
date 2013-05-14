package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TWfStationProcess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WF_STATION_PROCESS", schema = "dbo", catalog = "SDOffice")
public class TWfStationProcess implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FUnitStationId;
	private Integer FProcessTypeId;

	// Constructors

	/** default constructor */
	public TWfStationProcess() {
	}

	/** minimal constructor */
	public TWfStationProcess(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TWfStationProcess(Integer FId, Integer FUnitStationId,
			Integer FProcessTypeId) {
		this.FId = FId;
		this.FUnitStationId = FUnitStationId;
		this.FProcessTypeId = FProcessTypeId;
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

	@Column(name = "F_UnitStation_Id")
	public Integer getFUnitStationId() {
		return this.FUnitStationId;
	}

	public void setFUnitStationId(Integer FUnitStationId) {
		this.FUnitStationId = FUnitStationId;
	}

	@Column(name = "F_ProcessType_Id")
	public Integer getFProcessTypeId() {
		return this.FProcessTypeId;
	}

	public void setFProcessTypeId(Integer FProcessTypeId) {
		this.FProcessTypeId = FProcessTypeId;
	}

}