/******************************************************************************
 * @File name   :      TXMinisterRule.java
 *
 * @Package 	:	   com.envision.envservice.validator.item.rule
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-17 上午11:46:35
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
 * 2015-12-17 上午11:46:35    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator.item.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.service.SAPEmpJobService;
import com.envision.envservice.service.SAPUserService;
import com.envision.envservice.service.UserGroupService;
import com.envision.envservice.validator.item.P2PRuleItem;

/**
 * 规则:
 * 		体系部长
 * 
 * @ClassName WhiteListRule
 * @author guowei.wang
 * @date 2015-11-25
 */
@Component("txMinisterRule")
public class TXMinisterRule extends P2PRuleItem {
	
	@Autowired
	private SAPUserService sapUserService;

	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private SAPEmpJobService sapUserJobService;

	@Override
	public boolean valid(String userId, String userIdTarget) throws Exception {
		SAPEmpJob empJob = sapUserJobService.queryEMPJobById(userId);
		return userGroupService.isTXMinister(empJob.getDivision(), empJob.getDepartment(), userIdTarget);
	}
}