/******************************************************************************
 * @File name   :      UserCasePriseResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-6 下午3:11:12
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
 * 2016-1-6 下午3:11:12    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

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

import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.entity.bo.UserCasePriseBo;
import com.envision.envservice.service.UserCasePriseService;

/**
 * @ClassName UserCasePriseResource
 * @author guowei.wang
 * @date 2016-1-6
 */
@Path("user_case_prise")
@Component
public class UserCasePriseResource {
	
	@Autowired
	private UserCasePriseService userCasePriseService;
	
	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryPriseCollect(@PathParam("userId") String userId) {
		HttpStatus status = HttpStatus.CREATED;
		String response = userCasePriseService.queryPriseCollect(userId).toJSONString();
		
		return Response.status(status.value()).entity(response).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response prise(UserCasePriseBo userCasePrise) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		
		if (!checkParam(userCasePrise)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			userCasePriseService.prise(userCasePrise);
		}
		
		return Response.status(status.value()).entity(response).build();
	}

	@POST
	@Path("/cancel/{caseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelPrise(@PathParam("caseId") int caseId) throws Exception {
		HttpStatus status = HttpStatus.ACCEPTED;
		String response = StringUtils.EMPTY;
		
		userCasePriseService.cancelPrise(caseId);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 点赞参数验证
	 */
	private boolean checkParam(UserCasePriseBo userCasePrise) {
		return (userCasePrise.getCaseId() != null) && (userCasePrise.isPriseWill() || userCasePrise.isPriseWisdom() || userCasePrise.isPriseLove());
	}
}
