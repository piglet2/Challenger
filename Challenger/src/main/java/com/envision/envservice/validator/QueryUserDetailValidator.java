/******************************************************************************
 * @File name   :      QueryUserDetailValidator.java
 *
 * @Package 	:	   com.envision.envservice.validator
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-25 下午5:41:10
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
 * 2015-11-25 下午5:41:10    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator;

import org.springframework.stereotype.Component;

/**
 * 个人信息(更多)查询验证器
 * 
 * @ClassName QueryPraiseDetailValidator
 * @author guowei.wang
 * @date 2015-11-12
 */
@Component
public class QueryUserDetailValidator extends BaseValidator {

	private static final String NAME = "queryUserDetailValidator";
	
	@Override
	protected String name() {
		return NAME;
	}

}
