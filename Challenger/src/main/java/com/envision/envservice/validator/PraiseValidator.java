/******************************************************************************
 * @File name   :      PraiseValidator.java
 *
 * @Package 	:	   com.envision.envservice.rule
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-9 下午3:12:24
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
 * 2015-11-9 下午3:12:24    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator;

import org.springframework.stereotype.Component;

/**
 * 点赞操作验证器
 * 
 * @ClassName PraiseValidator
 * @author guowei.wang
 * @date 2015-11-9
 */
@Component
public class PraiseValidator extends BaseValidator {
	
	private static final String NAME = "PraiseValidator";

	@Override
	protected String name() {
		return NAME;
	}
}
