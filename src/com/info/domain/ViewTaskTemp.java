package com.info.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ViewTaskTemp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_TaskTemp", schema = "dbo", catalog = "SDOffice")
public class ViewTaskTemp implements java.io.Serializable {

	// Fields

	private ViewTaskTempId id;

	// Constructors

	/** default constructor */
	public ViewTaskTemp() {
	}

	/** full constructor */
	public ViewTaskTemp(ViewTaskTempId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "FId", column = @Column(name = "F_ID", nullable = false)),
			@AttributeOverride(name = "FParentId", column = @Column(name = "F_Parent_ID")),
			@AttributeOverride(name = "FName", column = @Column(name = "F_Name", length = 200)),
			@AttributeOverride(name = "isTask", column = @Column(name = "IsTask", nullable = false)),
			@AttributeOverride(name = "FTaskId", column = @Column(name = "F_Task_ID", nullable = false)),
			@AttributeOverride(name = "FSort", column = @Column(name = "F_Sort")) })
	public ViewTaskTempId getId() {
		return this.id;
	}

	public void setId(ViewTaskTempId id) {
		this.id = id;
	}

}