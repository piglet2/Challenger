/******************************************************************************
 * @File name   :      ServiceException.java
 *
 * @Package 	:	   com.envision.envservice.common
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-16 上午11:53:00
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
 * 2015-10-16 上午11:53:00    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service.exception;

import com.envision.envservice.common.Code;

/**
 * 业务异常
 * @ClassName ServiceException
 * @author guowei.wang
 * @date 2015-10-16
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Code code;

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Code code, String message) {
		super(message);
		this.setCode(code);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(Code code, String message, Throwable cause) {
		super(message, cause);
		this.setCode(code);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
