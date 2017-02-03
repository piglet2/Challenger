/******************************************************************************
 * @File name   :      CourseResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-20 下午3:14:18
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
 * 2016-1-20 下午3:14:18    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

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
import com.envision.envservice.entity.bo.CourseFeedbackBo;
import com.envision.envservice.service.CourseService;
import com.envision.envservice.service.exception.ServiceException;

/**
 * @ClassName CourseResource
 * @author guowei.wang
 * @date 2016-1-20
 */
@Path("course")
@Component
public class CourseResource {
	
	private static final int FEEDFACK_MAX_LENGTH = 500;
	
	@Autowired
	private CourseService courseService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryAll() throws Exception {
		HttpStatus status = HttpStatus.OK;
		String response = JSON.toJSONString(courseService.queryAll(), JSONFilter.NULL_UNDERLINE_FILTERS);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@POST
	@Path("/arrange/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response arrange(@PathParam("courseId") int courseId) throws ServiceException {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		
		courseService.arrange(courseId);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	@POST
	@Path("/feedback/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response feedback(@PathParam("courseId") int courseId, CourseFeedbackBo feedback) throws ServiceException {
		HttpStatus status = HttpStatus.ACCEPTED;
		String response = StringUtils.EMPTY;

		if (checkParam(feedback)) {
			courseService.feedback(courseId, feedback);
		} else {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "反馈过长或star数有误");
		}
		
		return Response.status(status.value()).entity(response).build();
	}

	@GET
	@Path("/feedback/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryFeedback(@PathParam("courseId") int courseId) throws ServiceException {
		HttpStatus status = HttpStatus.OK;
		String response = JSON.toJSONString(courseService.feedback(courseId), JSONFilter.NULLFILTER);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	private boolean checkParam(CourseFeedbackBo feedback) {
		int stars = feedback.getStars();
		String message = feedback.getFeedback();
		
		return stars > 0 && stars <= 5 && StringUtils.isNotEmpty(message) && message.length() < FEEDFACK_MAX_LENGTH;
	}
}
