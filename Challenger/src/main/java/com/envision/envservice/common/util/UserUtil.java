/******************************************************************************
 * @File name   :      UserUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-22 下午3:21:48
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
 * 2016-1-22 下午3:21:48    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.envision.envservice.entity.bo.UserBo;

/**
 * @ClassName UserUtil
 * @author guowei.wang
 * @date 2016-1-22
 */
public class UserUtil {

	public static void sortByName(List<UserBo> users) {
		Collections.sort(users, new Comparator<UserBo>() {

			@Override
			public int compare(UserBo u1, UserBo u2) {
				if (StringUtils.isEmpty(u1.getName())) {
					return 1;
				}
				if (StringUtils.isEmpty(u2.getName())) {
					return -1;
				}
				
				String u1Name = u1.getName();
				if (u1Name.contains(".")) {
					String[] names = u1Name.split("\\.");
					u1Name = names[1] + names[0]; 
				}

				String u2Name = u2.getName();
				if (u2Name.contains(".")) {
					String[] names = u2Name.split("\\.");
					u2Name = names[1] + names[0]; 
				}
				
				return u1Name.compareTo(u2Name);
			}
		});
	}
}
