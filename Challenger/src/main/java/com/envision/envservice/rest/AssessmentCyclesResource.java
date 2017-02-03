/******************************************************************************
 * @File name   :      UserCaseResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-4 下午5:42:18
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
 * 2016-1-4 下午5:42:18    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.envision.envservice.service.AssessmentCyclesService;
import com.envision.envservice.sync.RedmineUserSyncer;

/**
 * @ClassName AssessmentCyclesResource
 * @author caisong
 * @date 2016-8-2
 */
@Path("assessmentCycles")
@Component
public class AssessmentCyclesResource {
	
	@Autowired
	private AssessmentCyclesService acService;
	
	@Autowired
	private RedmineUserSyncer redmineUserSyncer;
	
	/*
	 * 新增横向评价周期
	 * */
	@GET
	@Path("/addAssessmentCycles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAssessmentCycles() throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		response=acService.addAssessmentCycles().toString();
		return Response.status(status.value()).entity(response).build();
		
	}
	
}