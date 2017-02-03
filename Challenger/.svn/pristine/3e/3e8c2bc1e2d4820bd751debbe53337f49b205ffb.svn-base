/******************************************************************************
 * @File name   :      OrgStructureResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-20 下午4:27:51
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
 * 2015-10-20 下午4:27:51    			guowei.wang     1.0            Initial Version
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

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.service.OrgStructureService;

/**
 * @ClassName OrgStructureResource
 * @author guowei.wang
 * @date 2015-10-20
 */
@Path("org_structure")
@Component
public class OrgStructureResource {
	
	@Autowired
	private OrgStructureService orgStructureService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryOrgStructure(@PathParam("id") String id) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(orgStructureService.queryOrgStructure(id), JSONFilter.NULLFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@GET
	@Path("/{id}/{level}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryOrgStructure(@PathParam("id") String id, @PathParam("level") String level) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = null;
		
		if (!checkParam(level)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			response = JSONObject.toJSONString(orgStructureService.queryOrgStructure(id, level), JSONFilter.NULLFILTER);
		}
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@GET
	@Path("/dep/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response querySameDepUsers(@PathParam("userId") String userId) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(orgStructureService.querySameDepUsers(userId), JSONFilter.NULLFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	private boolean checkParam(String level) {
		return orgStructureService.checkLevel(level, false);
	}
}
