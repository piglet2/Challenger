/******************************************************************************
 * @File name   :      UserCaseCommentResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-29 下午2:20:55
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
 * 2016-1-29 下午2:20:55    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.service.AwardService;
import com.envision.envservice.service.EvaluationPeriodService;
import com.envision.envservice.service.EvaluationService;
import com.envision.envservice.service.NotEvaluationUserService;

/**
 * @ClassName TestResource
 * @author song.cai	
 * @date 2016-08-10
 */
@Path("test")
@Component
public class TestResource {
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private AwardService awardService;
	
	@Autowired
	private NotEvaluationUserService notEvaluationUserService;
	
	@Autowired
	private EvaluationPeriodService evaluationPeriodService;
	
	
	/**
	 * 上级查看最新评价列表
	 * 
	 */
	@GET
	@Path("/queryByPeriodIdAndEvaluationId")
	@Produces(MediaType.TEXT_PLAIN) 
	public String queryByPeriodIdAndEvaluationId(){
		return "Got it!";
	}
	
	
	
}
