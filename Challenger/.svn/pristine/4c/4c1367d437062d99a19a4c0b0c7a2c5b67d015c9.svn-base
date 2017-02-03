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

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.entity.bo.UserBaseBo;
import com.envision.envservice.entity.bo.UserCaseBo;
import com.envision.envservice.entity.bo.UserCaseInfoBo;
import com.envision.envservice.service.UserCaseService;

/**
 * @ClassName UserCaseResource
 * @author guowei.wang
 * @date 2016-1-4
 */
@Path("user_case")
@Component
public class UserCaseResource {
	
	@Autowired
	private UserCaseService userCaseService;
	
	/*
	 * 事件详情
	 * 
	 * */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryOther(@PathParam("id") int id) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = StringUtils.EMPTY;
		UserCaseInfoBo userCaseInfo = userCaseService.queryUserCase(id);
		if (userCaseInfo != null) {
			response = JSONObject.toJSONString(userCaseInfo, JSONFilter.UNDERLINEFILTER);
		} else {
			status = HttpStatus.NOT_FOUND;
			response = FailResult.toJson(Code.CASE_NOT_EXSIT, "事件不存在");
		}
		
		return Response.status(status.value()).entity(response).build();
	}
	
	/*
	 * 员工事件列表
	 * 
	 * */
	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByUser(@PathParam("userId") String userId) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(userCaseService.queryUserCaseInfoByUser(userId), JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	@GET
	@Path("/has_unread/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response hasUnread(@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(userCaseService.queryUnread(userId), JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNew(UserCaseBo userCase) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		if (!checkParam(userCase)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			response = userCaseService.addUserCase(userCase).toJSONString();
		}
		
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 查询事件相关人
	 * 
	 * */
	@SuppressWarnings("unused")
	@GET
	@Path("/queryMembers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryMembers(@PathParam("id") int id) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = StringUtils.EMPTY;
		UserCaseInfoBo userCaseInfo = userCaseService.queryUserCase(id);
		List<UserBaseBo> list=userCaseInfo.getMembers();
		if (userCaseInfo != null) {
			response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		} else {
			status = HttpStatus.NOT_FOUND;
			response = FailResult.toJson(Code.CASE_NOT_EXSIT, "事件不存在");
		}
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 新增事件参数验证
	 */
	private boolean checkParam(UserCaseBo userCase) {
		String[] members = userCase.getMembers();
		String message = userCase.getMessage();
		
		return StringUtils.isNotEmpty(message) && members != null &&  members.length > 0;
	}
}
