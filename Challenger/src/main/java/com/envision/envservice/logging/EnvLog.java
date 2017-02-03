/******************************************************************************
 * @File name   :      EnvLog.java
 *
 * @Package 	:	   com.envision.envservice.logging
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-15 上午10:13:59
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
 * 2015-10-15 上午10:13:59    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.logging;

/**
 * @ClassName EnvLog
 * @Description 统一日志工具类
 * @author guowei.wang
 * @date 2015-10-15
 */
public class EnvLog {

	/*
	 * RestApi日志
	 * 		接口访问日志
	 */
	private static final Logger ACCESS_API = Logger.newInstance("access_api");
	
	/*
	 * RestApiHttp日志
	 * 		接口访问日志
	 */
	private static final Logger ACCESS_API_HTTP = Logger.newInstance("access_api_http");
	
	/*
	 * RestError日志
	 * 		接口异常日志
	 */
	private static final Logger REST_ERROR = Logger.newInstance("rest_error");
	
	/*
	 * BusinessFail日志
	 * 		业务失败日志
	 */
	private static final Logger BUSINESS_FAIL = Logger.newInstance("business_fail");
	
	/*
	 * SAPSyncer日志
	 * 		SAP同步日志
	 */
	private static final Logger SAP_SYNCER = Logger.newInstance("sap_syncer");
	
	/*
	 * MTQSyncer日志
	 * 		MTQ同步日志
	 */
	private static final Logger MTQ_SYNCER = Logger.newInstance("mtq_syncer");
	
	/*
	 * 消息推送日志
	 * 		
	 */
	private static final Logger PUSH_MESSAGE = Logger.newInstance("push_message");
	
	/*
	 * 短信推送日志
	 * 		
	 */
	private static final Logger SEND_SMS = Logger.newInstance("send_SMS");
	
	/*
	 * 短信推送日志
	 * 		
	 */
	private static final Logger SEND_Email = Logger.newInstance("send_Email");
	
	/*
	 * 纵向评价完成率更新日志
	 * 		
	 */
	private static final Logger EVALUATION_RATE = Logger.newInstance("evaluation_rate");
	
	/*
	 * 暂停纵向评价日志
	 * 		
	 */
	private static final Logger STOP_EVALUATION = Logger.newInstance("stop_evaluation");
	
	/*
	 * 奖惩记录同步日志
	 * 		
	 */
	private static final Logger AWARD = Logger.newInstance("award");
	
	/*
	 * 考勤类正式员工同步日志
	 * 		
	 */
	private static final Logger NotEvaluationUser = Logger.newInstance("not_evaluation_user");
	
	/*
	 * EVALUATION_PERIOD_SYNCER日志
	 * 		evaluationPeriod同步日志
	 */
	private static final Logger EVALUATION_PERIOD_SYNCER = Logger.newInstance("evaluationPeriod_syncer");
	
	/*
	 * EVALUATION_PERIOD_SYNCER日志
	 * 		evaluationPeriod同步日志
	 */
	private static final Logger ASSESSMENT_CYCLES_SYNCER = Logger.newInstance("assessmentCycles_syncer");

	/*
	 * SAPQuery日志
	 * 		SAP查询日志
	 */
	private static final Logger SAP_QUERY = Logger.newInstance("sap_query");
	
	/**
	 * Get Logger
	 */
	public static Logger getLogger(Class<?> clazz) {
		return Logger.newInstance(clazz);
	}
	
	/**
	 * 获取RestApi Logger
	 */
	public static Logger getAccessAPILogger() {
		return ACCESS_API;
	}

	/**
	 * 获取RestApi Http Logger
	 */
	public static Logger getAccessAPIHttpLogger() {
		return ACCESS_API_HTTP;
	}
	
	/**
	 * 获取RestError Logger
	 */
	public static Logger getRestErrorLogger() {
		return REST_ERROR;
	}
	
	/**
	 * 获取BusinessFail Logger
	 */
	public static Logger getBusinessFailLogger() {
		return BUSINESS_FAIL;
	}

	/**
	 * 获取SAPSyncer Logger
	 */
	public static Logger getSAPSyncerLogger() {
		return SAP_SYNCER;
	}
	
	/**
	 * 获取MTQSyncer Logger
	 */
	public static Logger getMTQSyncerLogger() {
		return MTQ_SYNCER;
	}
	
	/**
	 * 获取PushMessage
	 */
	public static Logger getPushMessage() {
		return PUSH_MESSAGE;
	}
	
	/**
	 * 获取sendSMS
	 */
	public static Logger getSendSMS() {
		return SEND_SMS;
	}
	
	/**
	 * 获取sendEmail
	 */
	public static Logger getSendEmail() {
		return SEND_Email;
	}
	
	/**
	 * 获取evaluationRate
	 */
	public static Logger getEvaluationRate() {
		return EVALUATION_RATE;
	}
	
	/**
	 * 获取stop_evaluation
	 */
	public static Logger getStopEvaluation() {
		return STOP_EVALUATION;
	}
	
	/**
	 * 获取award
	 */
	public static Logger getAward() {
		return AWARD;
	}
	
	/**
	 * 获取award
	 */
	public static Logger getNotEvaluationUser() {
		return NotEvaluationUser;
	}
	
	/**
	 * 获取EvaluationPeriod Logger
	 */
	public static Logger getEvaluationPeriodSyncerLogger() {
		return EVALUATION_PERIOD_SYNCER;
	}
	
	/**
	 * 获取AssessmentCycles Logger
	 */
	public static Logger getAssessmentCyclesSyncerLogger() {
		return ASSESSMENT_CYCLES_SYNCER;
	}

	/**
	 * 获取SAPQuery Logger
	 */
	public static Logger getSAPQueryLogger() {
		return SAP_QUERY;
	}
	
	
}
