/******************************************************************************
 * @File name   :      LinearSuperiorsRule.java
 *
 * @Package 	:	   com.envision.envservice.validator.item
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-11 下午3:21:58
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
 * 2015-11-11 下午3:21:58    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator.item.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.service.OrgStructureService;
import com.envision.envservice.validator.item.P2PRuleItem;

/**
 * 规则:
 * 		是否为直线上级(上级的上级的上级...)
 * 
 * @ClassName LinearSuperiorsRule
 * @author guowei.wang
 * @date 2015-11-11
 */
@Component("linearSuperiorsRule")
public class LinearSuperiorsRule extends P2PRuleItem {
	
	@Autowired
	private OrgStructureService orgStructureService;

	@Override
	public boolean valid(String userId, String userIdTarget) throws Exception {
		return orgStructureService.isLinearSuperiors(userId, userIdTarget);
	}
}
