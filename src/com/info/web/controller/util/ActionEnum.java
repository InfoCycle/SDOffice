package com.info.web.controller.util;

public enum ActionEnum {
	add,edit,save,delete,query,querywhere,other;
	public static ActionEnum getActionEnumType(String action){
		return valueOf(action.toLowerCase());
	}
}
