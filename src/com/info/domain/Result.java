package com.info.domain;

public class Result {
	private boolean Success;
	private int Id;
	private String Msg;
	
	public boolean getSuccess()
	{
		return this.Success;
	}
	
	public void setSuccess(boolean success){
		this.Success = success;
	}
	
	public Integer getId()
	{
		return this.Id;
	}
	
	public void setId(Integer id){
		this.Id = id;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}
	
}
