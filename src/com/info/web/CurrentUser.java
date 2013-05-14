package com.info.web;

import java.io.Serializable;
import java.util.Date;
import com.info.common.util.DataOption;

public class CurrentUser implements Serializable {
	/**
	 * 记录登录用的状态，专做session用。
	 */
	private static final long serialVersionUID = 1970037804367397430L;
	private int UserID;
	private String UserName;
	private int UserOrgID;
	private int UnitStation;
	private DataOption DataOption;
	public DataOption getDataOption() {
		return DataOption;
	}
	public void setDataOption(DataOption dataOption) {
		DataOption = dataOption;
	}
	public int getUnitStation() {
		return UnitStation;
	}
	public void setUnitStation(int unitStation) {
		UnitStation = unitStation;
	}
	private String UserGroupID;
	private String UserOrgName;
	private Date LoginDate;
	private String LoginClientIP;
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public int getUserOrgID() {
		return UserOrgID;
	}
	public void setUserOrgID(int userOrgID) {
		UserOrgID = userOrgID;
	}
	public String getUserGroupID() {
		return UserGroupID;
	}
	public void setUserGroupID(String userGroupID) {
		UserGroupID = userGroupID;
	}
	public String getUserOrgName() {
		return UserOrgName;
	}
	public void setUserOrgName(String userOrgName) {
		UserOrgName = userOrgName;
	}
	public Date getLoginDate() {
		return LoginDate;
	}
	public void setLoginDate(Date loginDate) {
		LoginDate = loginDate;
	}
	public String getLoginClientIP() {
		return LoginClientIP;
	}
	public void setLoginClientIP(String loginClientIP) {
		LoginClientIP = loginClientIP;
	}
	
	@Override
	public String toString(){
		return UserOrgName +" "+UserName;
	}
}
