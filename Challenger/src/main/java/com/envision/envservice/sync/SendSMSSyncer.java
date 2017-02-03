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
import com.envision.envservice.service.AssessmentCyclesService;
import com.envision.envservice.service.AssessmentService;
import com.envision.envservice.service.EvaluationPeriodService;
import com.envision.envservice.service.EvaluationRateService;
import com.envision.envservice.service.EvaluationService;
import com.envision.envservice.service.SAPEmpJobService;
import com.envision.envservice.service.SAPUserService;
import com.envision.envservice.service.SendSMSService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * SAP Photo 同步器
 * 
 * @ClassName MTQSyncer
 * @author song.cai	
 * @date 2016-07-12
 */
@Component("sendSMSSyncer")
public class SendSMSSyncer extends BaseClgSyncer{
	
	private static final Logger SENDSMS_LOG = EnvLog.getSendSMS();
	
	@Autowired
	private EvaluationRateService evaluationRateService;
	
	@Override
	public int syncData() throws Exception{
		//生产环境
		//提醒纵向评价主管
		evaluationRateService.sendMessageToManager("SMS");
		//提醒下级
		evaluationRateService.sendMessageToUser("SMS");
		//横向评价提醒
		evaluationRateService.sendMessageOfAssessment("SMS");
		
//		//测试环境
//		//提醒纵向评价主管
//		evaluationRateService.sendMessageToManagerOfTest("SMS");
//		//提醒下级
//		evaluationRateService.sendMessageToUserOfTest("SMS");
//		//横向评价提醒
//		evaluationRateService.sendMessageOfAssessmentOfTest("SMS");
		return 0;
	}
	
}
