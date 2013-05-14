package com.info.web;

/**
 * EasyUI DataGrid json 数据格式
 */

public class EasyDataGrid {
	private Long total = 0L;
	private Object rows = new Object();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
}
