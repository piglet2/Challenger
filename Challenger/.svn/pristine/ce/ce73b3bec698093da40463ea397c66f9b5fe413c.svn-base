/******************************************************************************
 * @File name   :      ValidatorUtil.java
 *
 * @Package 	:	   com.envision.envservice.validator.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-17 上午11:06:46
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
 * 2015-12-17 上午11:06:46    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator.util;

import java.util.HashMap;
import java.util.Map;

import com.envision.envservice.validator.item.P2PRuleItem;

public class ValidatorUtil {
	
	public static Map<String, Object> buildP2PRuleItemParams(String userId, String userIdTarget) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put(P2PRuleItem.PARAM_USERID, userId);
		params.put(P2PRuleItem.PARAM_USERID_TARGET, userIdTarget);
		
		return params;
	}
}
