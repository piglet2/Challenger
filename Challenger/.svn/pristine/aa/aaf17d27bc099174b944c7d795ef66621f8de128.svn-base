/******************************************************************************
 * @File name   :      UserCasePriseRankingResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-27 上午11:09:32
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
 * 2016-1-27 上午11:09:32    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.service.UserCasePriseRankingService;

/**
 * @ClassName UserCasePriseRankingResource
 * @author guowei.wang
 * @date 2016-1-27
 */
@Path("user_case_prise_ranking")
@Component
public class UserCasePriseRankingResource {
	
	@Autowired
	private UserCasePriseRankingService userCasePriseRankingService;
	
	@GET
	@Path("/subordinate/{managerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response querySubordinatePriseRanking(@PathParam("managerId") String managerId) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSON.toJSONString(userCasePriseRankingService.querySubordinatePriseRanking(managerId), JSONFilter.UNDERLINEFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
}
