/******************************************************************************
 * @File name   :      PriseItem.java
 *
 * @Package 	:	   com.envision.envservice.common.enums
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-14 下午2:55:43
 *
 * @Description : 	   
 *
 * @Copyright Notice: 
 * Copyright (c) 2016 Envision, Inc. All  Rights Reserved.
 * This software is published under the terms of the Envision Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * 
 * ----------------------------------------------------------------------------
 * Date                   		Who         Version        Comments
 * 2016-1-14 下午2:55:43    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.enums;

/**
 * @ClassName PriseItem
 * @author guowei.wang
 * @date 2016-1-14
 */
public enum PriseItem {
	
	WILL("will"),

	WISDOM("wisdom"),
	
	LOVE("love");
	
	private String value;
	
	private PriseItem(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
