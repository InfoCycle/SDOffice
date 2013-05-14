package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TContractScanning entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ContractScanning", schema = "dbo", catalog = "SDOffice")
public class TContractScanning implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer fkContractId;
	private String FScanningName;
	private String FScanning;

	// Constructors

	/** default constructor */
	public TContractScanning() {
	}

	/** minimal constructor */
	public TContractScanning(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TContractScanning(Integer FId, Integer fkContractId,
			String FScanningName, String FScanning) {
		this.FId = FId;
		this.fkContractId = fkContractId;
		this.FScanningName = FScanningName;
		this.FScanning = FScanning;
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

	@Column(name = "FK_Contract_ID")
	public Integer getFkContractId() {
		return this.fkContractId;
	}

	public void setFkContractId(Integer fkContractId) {
		this.fkContractId = fkContractId;
	}

	@Column(name = "F_ScanningName", length = 300)
	public String getFScanningName() {
		return this.FScanningName;
	}

	public void setFScanningName(String FScanningName) {
		this.FScanningName = FScanningName;
	}

	@Column(name = "F_Scanning")
	public String getFScanning() {
		return this.FScanning;
	}

	public void setFScanning(String FScanning) {
		this.FScanning = FScanning;
	}

}