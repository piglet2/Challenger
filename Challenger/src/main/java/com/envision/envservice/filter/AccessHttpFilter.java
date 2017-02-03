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
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envision.envservice.common.util.IPUtil;
import com.envision.envservice.logging.EnvLog;

/**
 * 以Http报文形式记录所有来访
 * 
 * @ClassName AccessHttpFilter
 * @author guowei.wang
 * @date 2015-10-30
 */
public class AccessHttpFilter implements Filter {
	
	/**
	 * Space: ' '
	 */
	private static final char SPACE = ' ';
	
	/**
	 * Param separator: '?'
	 */
	private static final char PARAM_SEPARATOR = '?';

	/**
	 * Header separator: ': '
	 */
	private static final String HEADER_SEPARATOR = ": ";
	
	/**
	 * CRLF: '\r\n'
	 * 		因记录方式要求一请求一行, 故此类CRLF由SPACE代替
	 */
	private static final String CRLF = String.valueOf(SPACE);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		accessHttp(request);
		
		chain.doFilter(request, response);
	}

	/*
	 * 记录访问信息
	 */
	private void accessHttp(HttpServletRequest request) {
		try {
			StringBuilder buf = new StringBuilder();
			
			// Socket IP
			buf.append('[' + IPUtil.getRemoteAddr(request) + ']' + SPACE);
	
			// Request line
			buf.append(request.getMethod());
			buf.append(SPACE);
			buf.append(request.getRequestURI());
			if (request.getQueryString() != null) {
				buf.append(PARAM_SEPARATOR);
				buf.append(request.getQueryString());
			}
			buf.append(SPACE);
			buf.append(request.getProtocol());
			buf.append(CRLF);
			
			// Request header
			Enumeration<?> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				Enumeration<?> headers = request.getHeaders(headerName);
				while (headers.hasMoreElements()) {
					String headerValue = (String) headers.nextElement();
					
					buf.append(headerName);
					buf.append(HEADER_SEPARATOR);
					buf.append(headerValue);
					buf.append(CRLF);
				}
			}
			
			EnvLog.getAccessAPIHttpLogger().info(buf.toString());
		} catch (Exception e) {
			EnvLog.getAccessAPIHttpLogger().warn("AccessAPIHttp logger error: ", e);
		}
	}
}
