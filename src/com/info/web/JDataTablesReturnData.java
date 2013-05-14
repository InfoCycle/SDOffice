package com.info.web;

/*
 * JQueryDataTablse
 */
public class JDataTablesReturnData {
	private long iTotalRecords;
	private long iTotalDisplayRecords;
	private String sEcho;
	private Object aaData;
	
	public long getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public Object getAaData() {
		return aaData;
	}
	public void setAaData(Object aaData) {
		this.aaData = aaData;
	}
	
}
