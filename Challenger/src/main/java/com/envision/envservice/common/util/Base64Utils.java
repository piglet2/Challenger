/******************************************************************************
 * @File name   :      Base64Utils.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-19 下午2:23:03
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
 * 2015-10-19 下午2:23:03    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

/**
 * @ClassName Base64Utils
 * @Description Base64工具
 * @author guowei.wang
 * @date 2015-10-19
 */
public class Base64Utils {
	
	/**
	 * Base64 encode
	 */
	public static String encode(String str) {
		if (str == null) {
			throw new IllegalArgumentException("输入值为Null.");
		}

		return encode(str.getBytes());
	}
	
	/**
	 * Base64 encode
	 */
	public static String encode(byte[] bytes) {
		if (bytes == null) {
			throw new IllegalArgumentException("输入值为Null.");
		}
		
		return new String(Base64.encodeBase64(bytes));
	}

	/**
	 * Base64 decode
	 */
	public static String decode(String str) throws IOException {
		if (str == null) {
			throw new IllegalArgumentException("输入值为Null.");
		}
		
		return new String(Base64.decodeBase64(str));
	}
}
