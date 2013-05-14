package com.info.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewGroupUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_GroupUser", schema = "dbo", catalog = "SDOffice")
public class ViewGroupUser implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FUserCode;
	private String FName;
	private String FPassword;
	private Integer FState;
	private Integer FSort;
	private Timestamp FCreateTime;
	private String FCardid;
	private String FPhone;
	private Integer FUnitStation;
	private String FGroupname;
	private String FContent;
	private Integer FGroupstate;

	// Constructors

	/** default constructor */
	public ViewGroupUser() {
	}

	/** minimal constructor */
	public ViewGroupUser(Integer FId) {
		this.FId = FId;
	}

	/** full constructor */
	public ViewGroupUser(Integer FId, String FUserCode, String FName,
			String FPassword, Integer FState, Integer FSort,
			Timestamp FCreateTime, String FCardid, String FPhone,
			Integer FUnitStation, String FGroupname, String FContent,
			Integer FGroupstate) {
		this.FId = FId;
		this.FUserCode = FUserCode;
		this.FName = FName;
		this.FPassword = FPassword;
		this.FState = FState;
		this.FSort = FSort;
		this.FCreateTime = FCreateTime;
		this.FCardid = FCardid;
		this.FPhone = FPhone;
		this.FUnitStation = FUnitStation;
		this.FGroupname = FGroupname;
		this.FContent = FContent;
		this.FGroupstate = FGroupstate;
	}

	// Property accessors
	@Id
	@Column(name = "F_ID", nullable = false, precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "F_USER_CODE", length = 30)
	public String getFUserCode() {
		return this.FUserCode;
	}

	public void setFUserCode(String FUserCode) {
		this.FUserCode = FUserCode;
	}

	@Column(name = "F_NAME", length = 20)
	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	@Column(name = "F_PASSWORD", length = 80)
	public String getFPassword() {
		return this.FPassword;
	}

	public void setFPassword(String FPassword) {
		this.FPassword = FPassword;
	}

	@Column(name = "F_STATE", precision = 6, scale = 0)
	public Integer getFState() {
		return this.FState;
	}

	public void setFState(Integer FState) {
		this.FState = FState;
	}

	@Column(name = "F_SORT", precision = 6, scale = 0)
	public Integer getFSort() {
		return this.FSort;
	}

	public void setFSort(Integer FSort) {
		this.FSort = FSort;
	}

	@Column(name = "F_CREATE_TIME", length = 23)
	public Timestamp getFCreateTime() {
		return this.FCreateTime;
	}

	public void setFCreateTime(Timestamp FCreateTime) {
		this.FCreateTime = FCreateTime;
	}

	@Column(name = "F_CARDID", length = 18)
	public String getFCardid() {
		return this.FCardid;
	}

	public void setFCardid(String FCardid) {
		this.FCardid = FCardid;
	}

	@Column(name = "F_PHONE", length = 30)
	public String getFPhone() {
		return this.FPhone;
	}

	public void setFPhone(String FPhone) {
		this.FPhone = FPhone;
	}

	@Column(name = "F_UNIT_STATION")
	public Integer getFUnitStation() {
		return this.FUnitStation;
	}

	public void setFUnitStation(Integer FUnitStation) {
		this.FUnitStation = FUnitStation;
	}

	@Column(name = "F_GROUPNAME", length = 60)
	public String getFGroupname() {
		return this.FGroupname;
	}

	public void setFGroupname(String FGroupname) {
		this.FGroupname = FGroupname;
	}

	@Column(name = "F_CONTENT", length = 200)
	public String getFContent() {
		return this.FContent;
	}

	public void setFContent(String FContent) {
		this.FContent = FContent;
	}

	@Column(name = "F_GROUPSTATE", precision = 6, scale = 0)
	public Integer getFGroupstate() {
		return this.FGroupstate;
	}

	public void setFGroupstate(Integer FGroupstate) {
		this.FGroupstate = FGroupstate;
	}

}