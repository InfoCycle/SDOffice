package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AppOrgUserId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class AppOrgUserId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8152514550255709064L;
	private Integer FId;
	private Integer FParentId;
	private String FName;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public AppOrgUserId() {
	}

	/** full constructor */
	public AppOrgUserId(Integer FId, Integer FParentId, String FName,
			Integer FSort) {
		this.FId = FId;
		this.FParentId = FParentId;
		this.FName = FName;
		this.FSort = FSort;
	}

	// Property accessors

	@Column(name = "F_ID", precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_PARENT_ID", precision = 9, scale = 0)
	public Integer getFParentId() {
		return this.FParentId;
	}

	public void setFParentId(Integer FParentId) {
		this.FParentId = FParentId;
	}

	@Column(name = "F_NAME")
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AppOrgUserId))
			return false;
		AppOrgUserId castOther = (AppOrgUserId) other;

		return ((this.getFId() == castOther.getFId()) || (this.getFId() != null
				&& castOther.getFId() != null && this.getFId().equals(
				castOther.getFId())))
				&& ((this.getFParentId() == castOther.getFParentId()) || (this
						.getFParentId() != null
						&& castOther.getFParentId() != null && this
						.getFParentId().equals(castOther.getFParentId())))
				&& ((this.getFName() == castOther.getFName()) || (this
						.getFName() != null && castOther.getFName() != null && this
						.getFName().equals(castOther.getFName())))
				&& ((this.getFSort() == castOther.getFSort()) || (this
						.getFSort() != null && castOther.getFSort() != null && this
						.getFSort().equals(castOther.getFSort())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFId() == null ? 0 : this.getFId().hashCode());
		result = 37 * result
				+ (getFParentId() == null ? 0 : this.getFParentId().hashCode());
		result = 37 * result
				+ (getFName() == null ? 0 : this.getFName().hashCode());
		result = 37 * result
				+ (getFSort() == null ? 0 : this.getFSort().hashCode());
		return result;
	}

}