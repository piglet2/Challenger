/******************************************************************************
 * @File name   :      MessageResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-19 下午3:07:39
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
 * 2015-11-19 下午3:07:39    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.common.util.PageUtil;
import com.envision.envservice.service.MessageService;

/**
 * @ClassName MessageResource
 * @author guowei.wang
 * @date 2015-11-19
 */
@Path("message")
@Component
public class MessageResource {
	
	@Autowired
	private MessageService messageService;
	
	@GET
	@Path("unread/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUnreadCount() throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = messageService.queryUnreadBo().toJSONString();
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryAll(@QueryParam("page_index") int pageIndex, 
							 @QueryParam("page_size") int pageSize) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = null;
		
		if (PageUtil.validate(pageIndex, pageSize)) {
			response = JSONObject.toJSONString(messageService.pageMessageQuery(pageIndex, pageSize), JSONFilter.NULLFILTER);
		} else {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		}
		
		return Response.status(status.value()).entity(response).build();
	}
}
