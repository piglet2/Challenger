/******************************************************************************
 * @File name   :      BaseInfoObserverRule.java
 *
 * @Package 	:	   com.envision.envservice.validator.item.rule
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-28 上午11:37:02
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
 * 2015-12-28 上午11:37:02    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator.item.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.service.UserGroupService;
import com.envision.envservice.validator.item.P2PRuleItem;

/**
 * 规则:
 * 		基础用户信息查看组
 * 
 * @ClassName BaseInfoObserverRule
 * @author guowei.wang
 * @date 2015-11-25
 */
@Component("baseInfoObserverRule")
public class BaseInfoObserverRule extends P2PRuleItem {
	
	@Autowired
	private UserGroupService userGroupService;

	@Override
	public boolean valid(String userId, String userIdTarget) throws Exception {
		return userGroupService.isBaseinfoObserver(userIdTarget);
	}

}