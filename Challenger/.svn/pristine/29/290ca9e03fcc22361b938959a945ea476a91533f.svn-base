/******************************************************************************
 * @File name   :      Result.java
 *
 * @Package 	:	   com.envision.envservice.common
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-16 上午11:31:03
 *
 * @Description : 	   
 *
 * @Copyright Notice: 
 * Copyright (c) 2015 Envision, Inc. All  Rights Reserved.
 * This software is published under the terms of the Envision Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * 
 * ----------------------------------------------------------------------------
 * Date                   		Who         Version        Comments
 * 2015-10-16 上午11:31:03    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common;

import com.alibaba.fastjson.JSONObject;

/**
 * 业务失败响应体
 * @ClassName Result
 * @author guowei.wang
 * @date 2015-10-16
 */
public class FailResult {

	private String code;
	
	private String msg;
	
	private FailResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public static String toJson(Code code, String msg) {
		return JSONObject.toJSONString(new FailResult(code.getCode(), msg), JSONFilter.NULLFILTER);
	}
}
