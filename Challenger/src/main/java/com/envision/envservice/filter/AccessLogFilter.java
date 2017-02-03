/******************************************************************************
 * @File name   :      AccessLogFilter.java
 *
 * @Package 	:	   com.envision.envservice.filter
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-27 上午1:51:42
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
 * 2015-10-27 上午1:51:42    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.envision.envservice.common.Constants;
import com.envision.envservice.common.util.IPUtil;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.logging.EnvLog;

/**
 * 访问日志过滤器
 * 
 * @ClassName AccessLogFilter
 * @author guowei.wang
 * @date 2015-10-27
 */
public class AccessLogFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		logAccessAPI(request);
		
		chain.doFilter(request, response);
	}

	/*
	 * 记录访问信息
	 */
	private void logAccessAPI(HttpServletRequest request) {
		try {
			UserBo user = (UserBo) request.getSession().getAttribute(Constants.SESSION_USER);
			String userId = user != null ? user.getUser_id() : "NOT_LOGIN";
			String remoteAddr = IPUtil.getRemoteAddr(request);
			String method = request.getMethod(); 
			String requestURI = request.getRequestURI();
			String userAgent = StringUtils.defaultString(request.getHeader("User-Agent"));
			
			String queryString = request.getQueryString();
			if (queryString != null) {
				queryString = URLDecoder.decode(request.getQueryString(), Constants.CHARSET);
			}
			requestURI = requestURI + (StringUtils.isNotEmpty(queryString) ? ("?" + queryString) : StringUtils.EMPTY);
			
			EnvLog.getAccessAPILogger().info(String.format("[%s] [%s] [%s] %s [%s]", userId, remoteAddr, method, requestURI, userAgent)); 
		} catch (Exception e) {
			EnvLog.getAccessAPILogger().warn("AccessAPI logger error: " + e.getMessage(), e);
		}
	}
}
