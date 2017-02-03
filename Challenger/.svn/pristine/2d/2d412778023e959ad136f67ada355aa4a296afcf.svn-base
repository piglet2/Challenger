/******************************************************************************
 * @File name   :      IPUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-30 上午11:37:24
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
 * 2015-10-30 上午11:37:24    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.envision.envservice.common.Constants;

/**
 * @ClassName IPUtil
 * @author guowei.wang
 * @date 2015-10-30
 */
public class IPUtil {
	
	/**
	 * 获取调用方IP地址
	 * 
	 * @Title: getRemoteAddr 
	 * @return IP 
	 * @Date 2015-10-30
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ipAddress = null;
		
		String proxyIP = request.getHeader(Constants.PROXY_IP);
		if (StringUtils.isNotEmpty(proxyIP)) {
			ipAddress = proxyIP;
		} else {
			ipAddress = request.getRemoteAddr();
		}
		
		return ipAddress;
	}
}
