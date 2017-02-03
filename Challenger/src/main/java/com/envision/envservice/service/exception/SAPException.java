/******************************************************************************
 * @File name   :      SAPException.java
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

/**
 * SAP业务异常
 * @ClassName SAPException
 * @author guowei.wang
 * @date 2015-10-16
 */
public class SAPException extends Exception {

	private static final long serialVersionUID = 1L;

	public SAPException() {
		super();
	}

	public SAPException(String message) {
		super(message);
	}

	public SAPException(Throwable cause) {
		super(cause);
	}
	
	public SAPException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SAPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
