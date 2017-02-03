/******************************************************************************
 * @File name   :      SessionUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-1 下午12:01:01
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
 * 2015-12-1 下午12:01:01    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import javax.servlet.http.HttpSession;

import com.envision.envservice.common.Constants;
import com.envision.envservice.entity.bo.UserBo;


public class SessionUtil {
	
	public static String getUserId(HttpSession session) {
		UserBo user = (UserBo) session.getAttribute(Constants.SESSION_USER);
		
		return user.getUser_id();
	}
}
