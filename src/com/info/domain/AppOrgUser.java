package com.info.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AppOrgUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "VIEW_APPORGUSER", schema = "dbo")
public class AppOrgUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6528505867413992640L;
	private AppOrgUserId id;

	// Constructors

	/** default constructor */
	public AppOrgUser() {
	}

	/** full constructor */
	public AppOrgUser(AppOrgUserId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "FId", column = @Column(name = "F_ID", precision = 9, scale = 0)),
			@AttributeOverride(name = "FParentId", column = @Column(name = "F_PARENT_ID", precision = 9, scale = 0)),
			@AttributeOverride(name = "FName", column = @Column(name = "F_NAME")),
			@AttributeOverride(name = "FSort", column = @Column(name = "F_SORT", precision = 6, scale = 0)) })
	public AppOrgUserId getId() {
		return this.id;
	}

	public void setId(AppOrgUserId id) {
		this.id = id;
	}

}