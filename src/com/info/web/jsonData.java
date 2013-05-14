package com.info.web;

public class jsonData {
	public  Object  list;
	public Integer pageNo;
	public Integer pageSize;
	public Integer pageCount;
	public Integer total;
	public Object getList() {
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}	
}
