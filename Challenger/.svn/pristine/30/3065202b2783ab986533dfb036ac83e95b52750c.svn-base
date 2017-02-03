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

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.envision.envservice.entity.bo.PushConfigBo;
import com.envision.envservice.service.PushConfigService;

/**
 * @ClassName AssessmentCyclesResource
 * @author caisong
 * @date 2016-8-2
 */
@Path("pushConfig")
@Component
public class PushConfigResource {
	
	@Autowired
	private PushConfigService pcService;
	
	/*
	 * 新增
	 * */
	@SuppressWarnings("rawtypes")
	@POST
	@Path("/addPushConfig")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPushConfig(PushConfigBo  pushConfigBo) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		Map response = pcService.addPushConfig(pushConfigBo);
		return Response.status(status.value()).entity(response).build();
		
	}
	
}
