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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.AssessmentCyclesService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * assessmentCycles 同步器
 * 
 * @ClassName AssessmentCyclesSyncer
 * @author song.cai	
 * @date 2016-09-01
 */
@Component("assessmentCyclesSyncer")
public class AssessmentCyclesSyncer extends BaseClgSyncer{
	
	private static final Logger SYNC_AssessmentCycles_LOGGER = EnvLog.getAssessmentCyclesSyncerLogger();
	
	@Autowired
	private AssessmentCyclesService acService;
	
	@Override
	public int syncData() throws Exception{
		String result = acService.addAssessmentCycles().toJSONString();
		Map map=new HashMap();
		map.put("msg", result);
		System.out.println("新增横向评价周期："+map.toString());
		return 0;
	}
}
