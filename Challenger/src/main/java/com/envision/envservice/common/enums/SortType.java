/******************************************************************************
 * @File name   :      SortType.java
 *
 * @Package 	:	   com.envision.envservice.common
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月26日 下午4:36:05
 *
 * @Description : 	   排序的类型
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
 * 2015年10月26日 下午4:36:05    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.enums;

/**
 * 排序的类型
 * @ClassName SortType
 * @author xuyang.li
 * @date 2015年10月26日
 */

public enum SortType {

	/**
	 * 升序
	 */
	ASC("asc"),
	
	/**
	 * 倒序
	 */
	DESC("desc");
	
	private String type;
	
	private SortType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
