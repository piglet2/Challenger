/******************************************************************************
 * @File name   :      ReportResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-30 上午11:14:04
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
 * 2015-11-30 上午11:14:04    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.common.util.StringUtil;
import com.envision.envservice.report.PraiseReporter;
import com.envision.envservice.service.MapConfigService;

/**
 * @ClassName ReportResource
 * @author guowei.wang
 * @date 2015-11-30
 */
@Path("report")
@Component
public class ReportResource {
	
	private static final String REPORT_PREFIX = "Challenger_";
	
	private static final String REPORT_SUFFIX = ".xlsx";
	
	private static final String NAME_LIST = "report_user";
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private PraiseReporter praiseReport;
	
	@Autowired
	private MapConfigService mapConfigService;
	
	@GET
	public Response report(@Context HttpServletResponse httpResponse) throws Exception {
		HttpStatus status = HttpStatus.OK;
		Object response = null;
		if (checkUser()) {
			setContentTypeHeader(httpResponse, MediaType.APPLICATION_OCTET_STREAM);
			setContentDispositionHeader(httpResponse);
			
			response = praiseReport.report();
		} else {
			setContentTypeHeader(httpResponse, MediaType.APPLICATION_JSON);
			response = FailResult.toJson(Code.OPERATION_IS_NOT_ALLOWED, "无权限");
		}
		
		return Response.status(status.value()).entity(response).build();
	}
	
	private boolean checkUser() {
		String userId = SessionUtil.getUserId(session);
		
		return Arrays.asList(mapConfigService.getSplitValue(NAME_LIST)).contains(userId);
	}

	private void setContentDispositionHeader(HttpServletResponse response) {
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(reportName(), Constants.CHARSET));
		} catch (UnsupportedEncodingException e) {
			response.setHeader("Content-Disposition", "attachment; filename=" + reportName());
		}
	}

	private void setContentTypeHeader(HttpServletResponse response, String contentType) {
		response.setContentType(contentType);
	}
	
	private String reportName() {
		return StringUtil.buildString(REPORT_PREFIX, DateUtil.today("yyyyMMdd"), REPORT_SUFFIX);
	}
}
