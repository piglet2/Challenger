/******************************************************************************
 * @File name   :      FilterAble.java
 *
 * @Package 	:	   com.envision.envservice.entity.base
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-11 上午11:35:21
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
 * 2015-11-11 上午11:35:21    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.base;


/**
 * 支持字段过滤接口
 * 
 * @ClassName FilterAble
 * @author guowei.wang
 * @date 2015-11-11
 */

public interface Filterable {
	
	public boolean isFilterField(String name);
	
}
