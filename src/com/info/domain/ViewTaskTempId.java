package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ViewTaskTempId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ViewTaskTempId implements java.io.Serializable {

	// Fields

	private Integer FId;
	private Integer FParentId;
	private String FName;
	private Integer isTask;
	private Integer FTaskId;
	private Integer FSort;

	// Constructors

	/** default constructor */
	public ViewTaskTempId() {
	}

	/** minimal constructor */
	public ViewTaskTempId(Integer FId, Integer isTask, Integer FTaskId) {
		this.FId = FId;
		this.isTask = isTask;
		this.FTaskId = FTaskId;
	}

	/** full constructor */
	public ViewTaskTempId(Integer FId, Integer FParentId, String FName,
			Integer isTask, Integer FTaskId, Integer FSort) {
		this.FId = FId;
		this.FParentId = FParentId;
		this.FName = FName;
		this.isTask = isTask;
		this.FTaskId = FTaskId;
		this.FSort = FSort;
	}

	// Property accessors

	@Column(name = "F_ID", nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_Parent_ID")
	public Integer getFParentId() {
		return this.FParentId;
	}

	public void setFParentId(Integer FParentId) {
		this.FParentId = FParentId;
	}

	@Column(name = "F_Name", length = 200)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "IsTask", nullable = false)
	public Integer getIsTask() {
		return this.isTask;
	}

	public void setIsTask(Integer isTask) {
		this.isTask = isTask;
	}

	@Column(name = "F_Task_ID", nullable = false)
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewTaskTempId))
			return false;
		ViewTaskTempId castOther = (ViewTaskTempId) other;

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
				&& ((this.getIsTask() == castOther.getIsTask()) || (this
						.getIsTask() != null && castOther.getIsTask() != null && this
						.getIsTask().equals(castOther.getIsTask())))
				&& ((this.getFTaskId() == castOther.getFTaskId()) || (this
						.getFTaskId() != null && castOther.getFTaskId() != null && this
						.getFTaskId().equals(castOther.getFTaskId())))
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
				+ (getIsTask() == null ? 0 : this.getIsTask().hashCode());
		result = 37 * result
				+ (getFTaskId() == null ? 0 : this.getFTaskId().hashCode());
		result = 37 * result
				+ (getFSort() == null ? 0 : this.getFSort().hashCode());
		return result;
	}

}