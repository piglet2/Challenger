/******************************************************************************
 * @File name   :      ChallengeLevelLTRule.java
 *
 * @Package 	:	   com.envision.envservice.validator.item
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-12 下午4:26:10
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
 * 2015-11-12 下午4:26:10    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator.item.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.common.util.ChallengeLevelUtil;
import com.envision.envservice.service.UserService;
import com.envision.envservice.validator.item.P2PRuleItem;

/**
 * 平级限定
 * 		规则: 前者挑战级别小于后者
 * 		返回: 通过:True
 * 
 * @ClassName ChallengeLevelLTRule
 * @author guowei.wang
 * @date 2015-11-10
 */
@Component("challengeLevelLTRule")
public class ChallengeLevelLTRule extends P2PRuleItem {
	
	@Autowired
	private UserService userService;

	@Override
	public boolean valid(String userId, String userIdTarget) throws Exception {
		String challengeLevel = userService.queryUser(userId).getChallenge_level();
		String challengeLevelTarget = userService.queryUser(userIdTarget).getChallenge_level();
		
		return ChallengeLevelUtil.isLT(challengeLevel, challengeLevelTarget);
	}
}
