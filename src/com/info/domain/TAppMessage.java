package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAppMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APP_MESSAGE", schema = "dbo", catalog = "SDOffice")
public class TAppMessage implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FTitle;
	private String FContent;
	private Timestamp FSendTime;
	private String FSendUser;
	private Integer FSendUserId;

	// Constructors

	/** default constructor */
	public TAppMessage() {
	}

	/** minimal constructor */
	public TAppMessage(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public TAppMessage(Integer FId, String FTitle, String FContent,
			Timestamp FSendTime, String FSendUser, Integer FSendUserId) {
		this.FId = FId;
		this.FTitle = FTitle;
		this.FContent = FContent;
		this.FSendTime = FSendTime;
		this.FSendUser = FSendUser;
		this.FSendUserId = FSendUserId;
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

	@Column(name = "F_TITLE", length = 100)
	public String getFTitle() {
		return this.FTitle;
	}

	public void setFTitle(String FTitle) {
		this.FTitle = FTitle;
	}

	@Column(name = "F_CONTENT", length = 2000)
	public String getFContent() {
		return this.FContent;
	}

	public void setFContent(String FContent) {
		this.FContent = FContent;
	}

	@Column(name = "F_SEND_TIME", length = 23)
	public Timestamp getFSendTime() {
		return this.FSendTime;
	}

	public void setFSendTime(Timestamp FSendTime) {
		this.FSendTime = FSendTime;
	}

	@Column(name = "F_SEND_USER", length = 50)
	public String getFSendUser() {
		return this.FSendUser;
	}

	public void setFSendUser(String FSendUser) {
		this.FSendUser = FSendUser;
	}

	@Column(name = "F_SEND_USER_ID", precision = 9, scale = 0)
	public Integer getFSendUserId() {
		return this.FSendUserId;
	}

	public void setFSendUserId(Integer FSendUserId) {
		this.FSendUserId = FSendUserId;
	}

}