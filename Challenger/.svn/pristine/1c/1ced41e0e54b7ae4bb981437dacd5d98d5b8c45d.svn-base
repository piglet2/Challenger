/******************************************************************************
 * @File name   :      AccessHttpFilter.java
 *
 * @Package 	:	   com.envision.envservice.filter
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-30 下午3:57:30
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
 * 2015-10-30 下午3:57:30    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.envision.envservice.service.PicRequestService;

/**
 * 转发到redmine请求
 * 
 * @ClassName RedmineFilter
 * @author song.cai
 * @date 2016-05-26
 */
public class PicFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Content-Type", "application/json; charset=utf-8");
		String requestType=request.getMethod();
		String userId="";
		String result="";
		if(requestType=="GET"||"GET".equals(requestType)){
			userId=getPicUrl(request.getRequestURL().toString());
			WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
			PicRequestService picRequestService = (PicRequestService) webAppContext.getBean("picRequestService");
			result=picRequestService.queryPic(userId);
		}else{
			return;
		}
		response.getWriter().write(result);
		return;
	}


	/*
	 * 查询头像的员工id
	 * 
	 * */
	public String getPicUrl(String str){
		String userId=str.substring(str.indexOf("/Challenger/pic/")+16,str.indexOf(".json"));
		return userId;
	}
	
}
