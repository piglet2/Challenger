/******************************************************************************
 * @File name   :      PraisePType.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月20日 上午9:55:32
 *
 * @Description : 	   点赞类型的枚举类
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
 * 2015年10月20日 上午9:55:32    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.enums;

/**
 * 点赞类型的枚举类
 * @ClassName PraiseType
 * @author xuyang.li
 * @date 2015年10月20日
 */
public enum PraiseType {
	
	/**
	 * 赞
	 */
	PRAISE("praise"),
	
	/**
	 * 鼓励
	 */
	ENCOURAGE("encourage");
	
	private String type;
	
	private PraiseType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

}
