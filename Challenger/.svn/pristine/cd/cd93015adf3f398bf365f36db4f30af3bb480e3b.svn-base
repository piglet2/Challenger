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

import com.envision.envservice.dao.SAPAssessmentUserDao;
import com.envision.envservice.entity.sap.SAPAssessmentUser;


/**
 * @ClassName SapAssessmentUserService
 * @author caisong
 * @date 2016-08-29
 */
@Service
public class SapAssessmentUserService {
	
	@Autowired
	private SAPAssessmentUserDao sapAssessmentUserDao;
	
	public List<SAPAssessmentUser> queryAll(){
		List<SAPAssessmentUser> list=sapAssessmentUserDao.queryAll();
		return list;
	}
}
