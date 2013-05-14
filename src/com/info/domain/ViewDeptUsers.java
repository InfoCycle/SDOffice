package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewDeptUsers entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_Dept_Users", schema = "dbo", catalog = "SDOffice")
public class ViewDeptUsers implements java.io.Serializable {

	// Fields

	private Integer rowid;
	private Integer FId;
	private String FDeptname;
	private Integer fkOrgId;
	private Integer fkUserId;
	private String FUsername;
	private Integer FUnitStation;
	private String FUnitStationName;

	// Constructors

	/** default constructor */
	public ViewDeptUsers() {
	}

	/** minimal constructor */
	public ViewDeptUsers(Integer rowid, Integer FId) {
		this.rowid = rowid;
		this.FId = FId;
	}

	/** full constructor */
	public ViewDeptUsers(Integer rowid, Integer FId, String FDeptname,
			Integer fkOrgId, Integer fkUserId, String FUsername,
			Integer FUnitStation, String FUnitStationName) {
		this.rowid = rowid;
		this.FId = FId;
		this.FDeptname = FDeptname;
		this.fkOrgId = fkOrgId;
		this.fkUserId = fkUserId;
		this.FUsername = FUsername;
		this.FUnitStation = FUnitStation;
		this.FUnitStationName = FUnitStationName;
	}

	// Property accessors
	@Id
	@Column(name = "rowid")
	public Integer getRowid() {
		return this.rowid;
	}

	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}

	@Column(name = "f_id", nullable = false, precision = 9, scale = 0)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "f_deptname", length = 100)
	public String getFDeptname() {
		return this.FDeptname;
	}

	public void setFDeptname(String FDeptname) {
		this.FDeptname = FDeptname;
	}

	@Column(name = "fk_org_id", precision = 9, scale = 0)
	public Integer getFkOrgId() {
		return this.fkOrgId;
	}

	public void setFkOrgId(Integer fkOrgId) {
		this.fkOrgId = fkOrgId;
	}

	@Column(name = "fk_user_id", precision = 9, scale = 0)
	public Integer getFkUserId() {
		return this.fkUserId;
	}

	public void setFkUserId(Integer fkUserId) {
		this.fkUserId = fkUserId;
	}

	@Column(name = "f_username", length = 20)
	public String getFUsername() {
		return this.FUsername;
	}

	public void setFUsername(String FUsername) {
		this.FUsername = FUsername;
	}

	@Column(name = "f_unit_station")
	public Integer getFUnitStation() {
		return this.FUnitStation;
	}

	public void setFUnitStation(Integer FUnitStation) {
		this.FUnitStation = FUnitStation;
	}

	@Column(name = "f_unit_station_name", length = 60)
	public String getFUnitStationName() {
		return this.FUnitStationName;
	}

	public void setFUnitStationName(String FUnitStationName) {
		this.FUnitStationName = FUnitStationName;
	}

}