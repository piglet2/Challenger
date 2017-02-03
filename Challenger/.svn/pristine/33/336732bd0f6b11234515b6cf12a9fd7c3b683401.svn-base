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

import java.io.UnsupportedEncodingException;

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

import com.alibaba.fastjson.JSON;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.entity.bo.UserCaseCommentBo;
import com.envision.envservice.service.UserCaseCommentService;

/**
 * @ClassName UserCaseCommentResource
 * @author guowei.wang
 * @date 2016-1-29
 */
@Path("user_case_comment")
@Component
public class UserCaseCommentResource {
	
	@Autowired
	private UserCaseCommentService userCaseCommentService;
	
	/*
	 * 获取评论列表
	 * 
	 * */
	@GET
	@Path("{caseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByCaseId(@PathParam("caseId") int caseId) throws UnsupportedEncodingException {
		HttpStatus status = HttpStatus.OK;
		String response = JSON.toJSONString(userCaseCommentService.queryByCaseId(caseId), JSONFilter.NULL_UNDERLINE_FILTERS);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	/*
	 * 获取评论列表
	 * 分页
	 * 
	 * */
	@GET
	@Path("{caseId}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByCaseId(@PathParam("caseId") int caseId,@PathParam("id") int id) throws UnsupportedEncodingException {
		HttpStatus status = HttpStatus.OK;
		String response = JSON.toJSONString(userCaseCommentService.queryByCaseId(caseId,id), JSONFilter.NULL_UNDERLINE_FILTERS);
		
		return Response.status(status.value()).entity(response).build();
	}

	/*
	 * 提交评论
	 * 
	 * */
	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("id") int id, UserCaseCommentBo userCaseComment) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		
		if (!checkParam(userCaseComment)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			userCaseCommentService.add(id, userCaseComment);
		}
		return Response.status(status.value()).entity(response).build();
	}

	private boolean checkParam(UserCaseCommentBo userCaseComment) {
		return StringUtils.isNotEmpty(userCaseComment.getComment());
	}
}
