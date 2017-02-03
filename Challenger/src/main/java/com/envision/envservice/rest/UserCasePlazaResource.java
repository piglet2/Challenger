/******************************************************************************
 * @File name   :      UserCasePlaza.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-26 下午6:29:17
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
 * 2016-1-26 下午6:29:17    			guowei.wang     1.0            Initial Version
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
import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.service.UserCaseService;

/**
 * @ClassName UserCasePlaza
 * @author guowei.wang
 * @date 2016-1-26
 */
@Path("user_case_plaza")
@Component
public class UserCasePlazaResource {

	@Autowired
	private UserCaseService userCaseService;
	
	@GET
	@Path("/top")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryPlazaTop() throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSON.toJSONString(userCaseService.queryPlazaTop(), JSONFilter.UNDERLINEFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@GET
	@Path("/{rows:[1-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryPlazaIndex(@PathParam("rows") int rows) throws Exception {
		String response = JSONObject.toJSONString(userCaseService.queryPlazaIndex(rows), JSONFilter.UNDERLINEFILTER);
		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/{since_id}/{rows:[1-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryPlaza(@PathParam("since_id") int sinceId, @PathParam("rows") int rows) throws Exception {
		String response = JSONObject.toJSONString(userCaseService.queryPlaza(sinceId, rows), JSONFilter.UNDERLINEFILTER);
		return Response.ok().entity(response).build();
	}
}
