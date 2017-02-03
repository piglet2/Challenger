/******************************************************************************
 * @File name   :      Operation.java
 *
 * @Package 	:	   com.envision.envservice.common.enums
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-16 下午2:26:57
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
 * 2015-11-16 下午2:26:57    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.enums;

/**
 * 操作类型, 用于记录于操作日志
 * 
 * @ClassName Operation
 * @author guowei.wang
 * @date 2015-11-16
 */
public enum Operation {
	
	LOGIN("login"),
	
	/**
	 * 点赞
	 */
	PRAISE("praise"),
	
	/**
	 * 鼓励
	 */
	ENCOURAGE("encourage"),
	
	/**
	 * 取消点赞
	 */
	PRAISE_CANCLE("praise_cancle"),
	
	/**
	 * 取消鼓励
	 */
	ENCOURAGE_CANCLE("encourage_cancle"),
	
	/**
	 * 留言
	 */
	LEAVEWORD("leaveword");
	
	private String value;
	
	private Operation(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
	
}
