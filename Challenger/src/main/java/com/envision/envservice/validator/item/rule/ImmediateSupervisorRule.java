/******************************************************************************
 * @File name   :      ManagerRule.java
 *
 * @Package 	:	   com.envision.envservice.validator.item
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月10日 下午6:19:58
 *
 * @Description : 	   校验点赞人是否是被点赞人的经理的校验规则
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
 * 2015年11月10日 下午6:19:58    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator.item.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.service.OrgStructureService;
import com.envision.envservice.validator.item.P2PRuleItem;

/**
 * 规则:
 * 		是否为直属上级
 * 
 * @ClassName immediateSupervisorRule
 * @date 2015年11月10日
 */
@Component("immediateSupervisorRule")
public class ImmediateSupervisorRule extends P2PRuleItem {

	@Autowired
	private OrgStructureService orgStructureService;

	@Override
	public boolean valid(String userId, String userIdTarget) throws Exception {
		return orgStructureService.isHigherLevel(userId, userIdTarget);
	}
}
