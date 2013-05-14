package com.info.common.util;

import com.info.web.CurrentUser;


public class SystemCurrentUser {
	private static ThreadLocal<CurrentUser> currentUser = 
			new ThreadLocal<CurrentUser>();
	/**
	 * 线程变量赋值
	 * @param session
	 */
	public static void setCurrentUser(CurrentUser session){
		currentUser.set(session);
	}
	public static CurrentUser getCurrentUser() {
		return currentUser.get();
	}
}
