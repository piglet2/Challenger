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
import com.envision.envservice.service.EvaluationRateService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * SAP Photo 同步器
 * 
 * @ClassName MTQSyncer
 * @author song.cai	
 * @date 2016-07-12
 */
@Component("evaluationRateSyncer")
public class EvaluationRateSyncer extends BaseClgSyncer{
	
	private static final Logger EvaluationRateSyncer_LOG = EnvLog.getEvaluationRate();
	
	@Autowired
	private EvaluationRateService evaluationRateService;
	
	@Override
	public int syncData() throws Exception{
		//更新最新一期纵向评价完成率
		Map map=evaluationRateService.updateRecentRate();
		System.out.println("最新纵向评价完成率更新："+map.toString());
		return 0;
	}
	
}
