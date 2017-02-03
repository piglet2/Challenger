/******************************************************************************
 * @File name   :      StringUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-25 上午11:58:28
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
 * 2015-11-25 上午11:58:28    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.util.Objects;

public class StringUtil {
	
	public static final String DEFAUL_SEPARATOR = "|";
	
	public static final String DEFAUL_SEPARATOR_REGEX = "\\|";
	
	public static String buildString(String... strs) {
		Objects.requireNonNull(strs);
		
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			buf.append(strs[i]);
		}
		
		return buf.toString();
	}
	
	
	public static String splice(String... arr) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			buf.append(arr[i]);

			if (i != (arr.length -1)) {
				buf.append(DEFAUL_SEPARATOR);
			}
		}
		
		return buf.toString();
	}
	
	public static String[] parseSplice(String string) {
		return string.split(DEFAUL_SEPARATOR_REGEX);
	}
}
