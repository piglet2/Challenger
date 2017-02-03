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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.entity.dto.ClgApplication;
import com.envision.envservice.service.ClgApplicationService;

/**
 * @ClassName ClgApplicationResource
 * @author caisong
 * @date 2016-12-8
 */
@Path("clgApplication")
@Component
public class ClgApplicationResource {
	
	@Autowired
	private ClgApplicationService clgApplicationService;
	
	/**
	 * 上级暂不评价权限判断
	 * 
	 * */
	@GET
	@Path("/queryAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryAll() throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		List<ClgApplication> list=clgApplicationService.queryClgApplication();
		response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	
}
