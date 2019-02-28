package com.zxl.util;

import com.alibaba.fastjson.JSONObject;

public class Result extends JSONObject {
//	private boolean success = false;// 是否成功
//	private String msg = "";// 提示信息
//	private Object obj = null;// 其他信息
	
	public Result(){
		put("success", true);
		put("msg", "");
	}
	
/*	public Result(boolean success){
		put("success", success);
		put("msg", "");
	}*/
	
	public String getMsg() {
		return getString("msg");
	}

	public void setMsg(String msg) {
		put("msg", msg);
	}

	public Object getObj() {
		return get("obj");
	}

	public void setObj(Object obj) {
		put("obj", obj);
	}

	public boolean isSuccess() {
		return 	getBooleanValue("success");
	}

	public void setSuccess(boolean success) {
		put("success", success);
	}
}
