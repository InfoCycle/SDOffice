package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TContractRelevanceTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ContractRelevanceTask", schema = "dbo", catalog = "SDOffice")
public class TContractRelevanceTask implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FContractId;
	private Integer FTaskId;

	// Constructors

	/** default constructor */
	public TContractRelevanceTask() {
	}

	/** minimal constructor */
	public TContractRelevanceTask(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TContractRelevanceTask(Integer FId, Integer FContractId,
			Integer FTaskId) {
		this.FId = FId;
		this.FContractId = FContractId;
		this.FTaskId = FTaskId;
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

	@Column(name = "F_Contract_ID")
	public Integer getFContractId() {
		return this.FContractId;
	}

	public void setFContractId(Integer FContractId) {
		this.FContractId = FContractId;
	}

	@Column(name = "F_Task_ID")
	public Integer getFTaskId() {
		return this.FTaskId;
	}

	public void setFTaskId(Integer FTaskId) {
		this.FTaskId = FTaskId;
	}

}