package com.info.web.controller.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PartExcelFile {
	private CommonsMultipartFile PartExcel;

	public CommonsMultipartFile getPartExcel() {
		return PartExcel;
	}

	public void setPartExcel(CommonsMultipartFile partExcel) {
		PartExcel = partExcel;
	}
}
