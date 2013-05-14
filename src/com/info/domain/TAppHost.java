package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppHost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_HOST", schema = "dbo", catalog = "SDOffice")
public class TAppHost implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FAppName;
	private String FAppHost;

	// Constructors

	/** default constructor */
	public TAppHost() {
	}

	/** minimal constructor */
	public TAppHost(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppHost(Integer FId, String FAppName, String FAppHost) {
		this.FId = FId;
		this.FAppName = FAppName;
		this.FAppHost = FAppHost;
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

	@Column(name = "F_APP_NAME", length = 30)
	public String getFAppName() {
		return this.FAppName;
	}

	public void setFAppName(String FAppName) {
		this.FAppName = FAppName;
	}

	@Column(name = "F_APP_HOST", length = 200)
	public String getFAppHost() {
		return this.FAppHost;
	}

	public void setFAppHost(String FAppHost) {
		this.FAppHost = FAppHost;
	}

}