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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.DomesticLocationDao;
import com.envision.envservice.entity.dto.DomesticLocation;
import com.envision.envservice.entity.sap.SAPUser;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class DomesticLocationService {
	
	@Autowired
	private DomesticLocationDao domesticLocationDao;
	
	/*
	 * 效验工作地点是否在国内
	 * 
	 * */
	public boolean checkLocation(SAPUser user){
		Boolean flag=false;
		List <DomesticLocation> list=domesticLocationDao.queryAll();
		String location=user.getLocation();
		for(int i=0;i<list.size();i++){
			if(location!=null&&!"".equals(location)){
				if(list.get(i).getLocation()==location||list.get(i).getLocation().equals(location)){
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	
	
}
