/******************************************************************************
 * @File name   :      PageUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-19 下午5:00:05
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
 * 2015-11-19 下午5:00:05    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

public class PageUtil {
	
	public static boolean validate(int pageIndex, int pageSize) {
		return (pageIndex > 0) && (pageSize > 0);
	}
	
	public static void checkPage(int pageIndex, int pageSize) {
		if (pageIndex < 1) {
			throw new IllegalArgumentException("PageIndex error: " + pageIndex);
		}
		if (pageSize < 1) {
			throw new IllegalArgumentException("PageSize error: " + pageSize);
		}
	}
	
	public static int calcOffset(int pageIndex, int pageSize) {
		return pageIndex == 1 ? 0 : (pageIndex - 1) * pageSize;
	}
}
