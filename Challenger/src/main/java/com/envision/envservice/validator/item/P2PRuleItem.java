/******************************************************************************
 * @File name   :      P2PRuleItem.java
 *
 * @Package 	:	   com.envision.envservice.validator.item
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-17 上午10:51:19
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
 * 2015-12-17 上午10:51:19    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator.item;

import java.util.Map;

/**
 * 个人对个人验证规则
 * 		
 * 
 * @ClassName P2PRuleItem
 * @author guowei.wang
 * @date 2015-12-17
 */
public abstract class P2PRuleItem implements IRuleItem {

	/**
	 * userId
	 */
	public static final String PARAM_USERID = "P2PRuleItem.userId";
	
	/**
	 * userIdTarger
	 */
	public static final String PARAM_USERID_TARGET = "P2PRuleItem.userIdTarger";

	@Override
	public boolean valid(Map<String, Object> params) throws Exception {
		String userId = (String) params.get(PARAM_USERID);
		String userIdTarget = (String) params.get(PARAM_USERID_TARGET);
		
		return valid(userId, userIdTarget);
	}
	
	public abstract boolean valid(String userId, String userIdTarget) throws Exception;
	
}
