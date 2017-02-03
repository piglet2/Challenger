/******************************************************************************
 * @File name   :      LeaveWordResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-22 上午11:51:30
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
 * 2015-10-22 上午11:51:30    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.common.enums.Operation;
import com.envision.envservice.common.util.IPUtil;
import com.envision.envservice.entity.bo.LeaveWordBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.DBLogger;
import com.envision.envservice.service.LeaveWordService;
import com.envision.envservice.service.LoggerService;

/**
 * @ClassName LeaveWordResource
 * @author guowei.wang
 * @date 2015-10-22
 */
@Path("leaveword")
@Component
public class LeaveWordResource {
	
	/**
	 * 留言正文长度上限
	 */
	private static final int CONTENTS_MAX_LENGTH = 500;
	
	@Autowired
	private LeaveWordService leaveWordService;
	
	@Autowired
	private LoggerService loggerService;
	
	@Autowired
	private HttpSession session;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryOwn() throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(leaveWordService.queryOwnLeaveWord(), JSONFilter.NULLFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryOther(@PathParam("id") String userId) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(leaveWordService.queryOtherLeaveWord(userId), JSONFilter.NULLFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNew(@Context HttpServletRequest request, LeaveWordBo leaveWord) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		
		if (!checkParam(leaveWord)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else if (!checkContentLength(leaveWord)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.CONTENT_TOO_LONG, "留言内容过长");
		} else {
			DBLogger logger = operationLog(request, leaveWord.getUserIdTarget());
			
			String leaveWordId = leaveWordService.addLeaveWord(leaveWord);
			
			loggerService.setSuccess(logger.getId(), true, leaveWordId);
		}
		
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 新增留言参数验证
	 * 
	 * @Title: checkLeaveWord 
	 * @param leaveWord
	 * @Date 2015-10-22
	 */
	private boolean checkParam(LeaveWordBo leaveWord) {
		boolean flag = true;
		if (leaveWord.getUserIdTarget() == null || leaveWord.getContents() == null) {
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * 正文长度检查
	 * 		最大允许长度: CONTENTS_MAX_LENGTH
	 * 
	 * @Title: checkContentLength 
	 * @param leaveWord
	 * @Date 2015-10-29
	 */
	private boolean checkContentLength(LeaveWordBo leaveWord) {
		boolean flag = true;
		if (leaveWord.getContents().length() > CONTENTS_MAX_LENGTH) {
			flag = false;
		}
		
		return flag;
	}
	
	private DBLogger operationLog(HttpServletRequest request, String userIdTarget) {
		UserBo user = (UserBo) session.getAttribute(Constants.SESSION_USER);
		
		return loggerService.addLog(IPUtil.getRemoteAddr(request), user.getUser_id(), userIdTarget, Operation.LEAVEWORD);
	}
}
