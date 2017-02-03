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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.UserCaseRemindsDao;
import com.envision.envservice.entity.dto.UserCaseReminds;


/**
 * @ClassName UserCaseService
 * @author guowei.wang
 * @date 2016-1-5
 */
@Service
public class UserCaseRemindsService {
	
	@Autowired
	private UserCaseRemindsDao userCaseRemindsDao;
	
	public void addUserCaseReminds(List<UserCaseReminds> list) {
		for(int i=0;i<list.size();i++){
			UserCaseReminds ucr=list.get(i);
			userCaseRemindsDao.add(ucr);
		}
		
	}
	
	
	public List<UserCaseReminds> queryById(int id){
		List<UserCaseReminds> list=userCaseRemindsDao.queryById(id);
		return list;
	}

}
