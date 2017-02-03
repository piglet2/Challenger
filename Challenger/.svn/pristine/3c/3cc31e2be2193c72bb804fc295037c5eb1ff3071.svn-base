/******************************************************************************
 * @File name   :      OperationPermission.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-12 上午11:27:42
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
 * 2015-11-12 上午11:27:42    			guowei.wang     1.0            Initial Version
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
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.service.OperationPermissionService;

/**
 * 查询当前登录人对指定人的操作权限集

 * @ClassName OperationPermissionResource
 * @author guowei.wang
 * @date 2015-11-12
 */
@Path("operation_permission")
@Component
public class OperationPermissionResource {
	
	@Autowired
	private OperationPermissionService operationPermissionService;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryOther(@PathParam("id") String targetUserId) throws Exception {
		HttpStatus status = HttpStatus.OK;
		
		JSONObject operationPermissions = operationPermissionService.queryOperationPermission(targetUserId);
		String response = JSONObject.toJSONString(operationPermissions, JSONFilter.NULLFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
}
