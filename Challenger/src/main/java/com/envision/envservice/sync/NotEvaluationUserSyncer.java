/******************************************************************************
 * @File name   :      SAPPhotoSyncer.java
 *
 * @Package 	:	   com.envision.envservice.sync
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-2 下午2:42:49
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
 * 2015-11-2 下午2:42:49    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.sync;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.NotEvaluationUserService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * SAP Photo 同步器
 * 
 * @ClassName notEvaluationUserSyncer
 * @author song.cai	
 * @date 2016-11-16
 */
@Component("notEvaluationUserSyncer")
public class NotEvaluationUserSyncer extends BaseClgSyncer{
	
	private static final Logger NotEvaluationUser_LOG = EnvLog.getNotEvaluationUser();
	
	@Autowired
	private NotEvaluationUserService notEvaluationUserService;
	
	
	/*
	 * 考勤类正式员工同步日志
	 * 
	 * */
	@Override
	public int syncData() throws Exception{
		Map map=notEvaluationUserService.replace();
		System.out.println("同步考勤类正式员工："+map.get("msg").toString());
		return 0;
	}
	
}
