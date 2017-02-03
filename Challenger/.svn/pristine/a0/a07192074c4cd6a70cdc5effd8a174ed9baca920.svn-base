/******************************************************************************
 * @File name   :      Logger.java
 *
 * @Package 	:	   com.envision.envservice.logging
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-9 上午11:57:52
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
 * 2015-11-9 上午11:57:52    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.logging;

import org.slf4j.LoggerFactory;

public class Logger {
	
	private org.slf4j.Logger logger;
	
	private Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	public static Logger newInstance(String logger) {
		return new Logger(LoggerFactory.getLogger(logger));
	}

	public static Logger newInstance(Class<?> logger) {
		return newInstance(logger.getName());
	}
	
	public void info(String message) {
		logger.info(message);
	}

	public void info(String message, Throwable t) {
		logger.info(message, t);
	}

	public void debug(String message) {
		logger.debug(message);
	}
	
	public void debug(String message, Throwable t) {
		logger.debug(message, t);
	}
	
	public void warn(String message) {
		logger.warn(message);
	}
	
	public void warn(String message, Throwable t) {
		logger.warn(message, t);
	}
	
	public void error(String message) {
		logger.error(message);
	}
	
	public void error(String message, Throwable t) {
		logger.error(message, t);
	}
}
