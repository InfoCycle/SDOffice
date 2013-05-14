package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCommonOpinion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CommonOpinion", schema = "dbo", catalog = "SDOffice")
public class TCommonOpinion implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FUserId;
	private String FContent;
	private String FAddDate;

	// Constructors

	/** default constructor */
	public TCommonOpinion() {
	}

	/** minimal constructor */
	public TCommonOpinion(Integer FId, Integer FUserId) {
		this.FId = FId;
		this.FUserId = FUserId;
	}

	/** full constructor */
	public TCommonOpinion(Integer FId, Integer FUserId, String FContent,
			String FAddDate) {
		this.FId = FId;
		this.FUserId = FUserId;
		this.FContent = FContent;
		this.FAddDate = FAddDate;
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

	@Column(name = "F_UserID", nullable = false)
	public Integer getFUserId() {
		return this.FUserId;
	}

	public void setFUserId(Integer FUserId) {
		this.FUserId = FUserId;
	}

	@Column(name = "F_Content", length = 800)
	public String getFContent() {
		return this.FContent;
	}

	public void setFContent(String FContent) {
		this.FContent = FContent;
	}

	@Column(name = "F_AddDate", length = 30)
	public String getFAddDate() {
		return this.FAddDate;
	}

	public void setFAddDate(String FAddDate) {
		this.FAddDate = FAddDate;
	}

}