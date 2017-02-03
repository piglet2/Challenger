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
import com.envision.envservice.service.AwardService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * SAP Photo 同步器
 * 
 * @ClassName awardSyncer
 * @author song.cai	
 * @date 2016-11-16
 */
@Component("awardSyncer")
public class AwardSyncer extends BaseClgSyncer{
	
	private static final Logger Award_LOG = EnvLog.getAward();
	
	@Autowired
	private AwardService awardService;
	
	@Override
	public int syncData() throws Exception{
		//同步员工奖惩记录
		Map map=awardService.addAllAward();
		System.out.println("同步员工奖惩记录："+map.get("msg").toString());
		return 0;
	}
	
}
