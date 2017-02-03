/******************************************************************************
 * @File name   :      UserCaseService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-5 上午10:11:11
 *
 * @Description : 	   
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
 * 2016-1-5 上午10:11:11    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @ClassName UserCaseService
 * @author guowei.wang
 * @date 2016-1-5
 */
@Service
public class CacheService {
	
	@Autowired
	private MapConfigService mapConfigService;
	
	private static Map map;
	
	/*
	 *从数据库取值
	 * 
	 * */
	public Map queryMap(String key){
		String result="";
		if(map==null || map.size()<1){
			map=new HashMap();
			result=mapConfigService.getValue(key);
			map.put(key, result);
		}
		return map;
	}
	
	/*
	 * 清空缓存
	 * 
	 * */
	public void cleanMap(){
		if(map==null || map.size()<1){
			return;
		}else{
			map.clear();
		}
	}
	
	
}
