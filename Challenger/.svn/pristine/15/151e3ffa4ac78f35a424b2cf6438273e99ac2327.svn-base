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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.EvaluationPeriodService;
import com.envision.envservice.service.PushService;
import com.envision.envservice.service.SapAssessmentUserService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * SAP Photo 同步器
 * 
 * @ClassName MTQSyncer
 * @author song.cai	
 * @date 2016-07-12
 */
@Component("pushMessage")
public class pushMessage extends BaseClgSyncer{
	
	private static final Logger PUSHMESSAGE_LOGGER = EnvLog.getPushMessage();
	
	@Autowired
	private SapAssessmentUserService sauService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private EvaluationPeriodService epService;
	
	@Override	
	public int syncData() throws Exception{
		pushEvaluationByStart();
		pushEvaluationByEnd();
		pushAssessmentByStart();
		pushAssessmentByEnd();
		return 0;
	}
	
	/*
	 * 纵向评价周期生成时
	 * 消息推送
	 * 
	 * */
    public void pushEvaluationByStart() throws Exception{
    	pushService.EvaluationPeriodByStart();
    }
    
    /*
	 * 纵向评价周期结束时
	 * 消息推送
	 * 
	 * */
    public void pushEvaluationByEnd() throws Exception{
    	pushService.EvaluationPeriodByEnd();
    	
    }
    
    /*
	 * 横向评价周期生成时
	 * 消息推送
	 * 
	 * */
    public void pushAssessmentByStart() throws Exception{
    	pushService.AssessmentCyclesByStart();
    	
    }
    
    /*
	 * 横向评价周期结束时
	 * 消息推送
	 * 
	 * */
    public void pushAssessmentByEnd() throws Exception{
    	pushService.AssessmentCyclesByEnd();
    	
    }
    
    
}
