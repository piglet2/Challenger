/******************************************************************************
 * @File name   :      ChallengerItem.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      远景精神条目
 *
 * @Date        :      2015年10月20日 上午10:45:11
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
 * 2015年10月20日 上午10:45:11    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

/**
 * 远景精神条目
 * @ClassName ChallengerItem
 * @author xuyang.li
 * @date 2015年10月20日
 */

public class ChallengerItem {
	
	private String id;
	/**item的key*/
	private String itemKey;
	/**item上级ID*/
	private String itemPid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemKey() {
		return itemKey;
	}
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
	public String getItemPId() {
		return itemPid;
	}
	public void setItemPId(String itemPId) {
		this.itemPid = itemPId;
	}
}
