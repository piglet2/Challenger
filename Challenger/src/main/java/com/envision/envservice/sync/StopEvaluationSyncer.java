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
import com.envision.envservice.service.EvaluationService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * 暂停纵向评价定时任务
 * 
 * @ClassName StopEvaluationSyncer
 * @author song.cai	
 * @date 2016-12-07
 */
@Component("stopEvaluationSyncer")
public class StopEvaluationSyncer extends BaseClgSyncer{
	
	private static final Logger STOP_EVALUATION_LOG = EnvLog.getStopEvaluation();
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Override
	public int syncData() throws Exception{
		//暂停纵向评价
		Map map=evaluationService.stopEvaluation();
		System.out.println("暂停纵向评价："+map.toString());
		return 0;
	}
	
}
