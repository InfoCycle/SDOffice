package com.info.common.util;

public enum DataOption {
	ALL,DEPT,OWNER; //全部，本部门，自己
	public static DataOption getOption(String option){
		return valueOf(option.toUpperCase());
	}
}
