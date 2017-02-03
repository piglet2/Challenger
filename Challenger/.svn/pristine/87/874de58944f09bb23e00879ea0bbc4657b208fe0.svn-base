/******************************************************************************
 * @File name   :      MapConfigService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月2日 上午10:43:59
 *
 * @Description : 	     系统配置信息
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
 * 2015年11月2日 上午10:43:59    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.envision.envservice.dao.MapConfigDao;

/**
 * xuyang.li
 * @ClassName MapConfigService
 * @author xuyang.li
 * @date 2015年11月2日
 */
@Service
public class MapConfigService {
	
	private static final String DEFAULT_SPLIT_REGEX = ",";
	
	@Resource
	private MapConfigDao mapConfigDao;
	
	public String getValue(String propKey){
		return mapConfigDao.getValue(propKey);
	}
	
	public int getIntValue(String propKey){
		return Integer.valueOf(getValue(propKey));
	}
	
	public int getIntValue(String propKey, int defaultValue){
		String value = getValue(propKey);
		
		return value == null ? defaultValue : Integer.valueOf(value);
	}
	
	public String[] getSplitValue(String propKey) {
		return getSplitValue(propKey, DEFAULT_SPLIT_REGEX);
	}
	
	public String[] getSplitValue(String propKey, String regex) {
		String value = getValue(propKey);
		
		return value == null ? new String[0] : value.split(regex);
	}
}
