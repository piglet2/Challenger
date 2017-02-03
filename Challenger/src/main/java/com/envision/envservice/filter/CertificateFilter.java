/******************************************************************************
 * @File name   :      CertificateFilter.java
 *
 * @Package 	:	   com.envision.envservice.filter
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-27 上午1:57:45
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
 * 2015-10-27 上午1:57:45    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.FailResult;

/**
 * 系统密钥过滤器
 * 
 * @ClassName CertificateFilter
 * @author guowei.wang
 * @date 2015-10-27
 */
public class CertificateFilter implements Filter {
	
	private ServletContext servletContext;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
	}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		if (verifyCertificate()) {
			chain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON);
			response.getWriter().print(FailResult.toJson(Code.NO_CERTIFICATE, "未键入密钥"));
		}
	}
	
	private boolean verifyCertificate() {
		return servletContext.getAttribute(Constants.SYS_CERTIFICATE) != null;
	}
}
