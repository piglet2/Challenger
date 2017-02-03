/******************************************************************************
 * @File name   :      RestAuthenticationFilter.java
 *
 * @Package 	:	   com.envision.envservice.filter
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-15 上午10:08:20
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
 * 2015-10-15 上午10:08:20    			guowei.wang     1.0            Initial Version
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
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.entity.bo.UserBo;

/**
 * 登录验证过滤器
 * 
 * @ClassName LoginFilter
 * @author guowei.wang
 * @date 2015-10-27
 */
public class LoginFilter implements Filter {
	
	public void init(FilterConfig arg0) throws ServletException {}

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		if (loginCheck(request, response)) {
			chain.doFilter(request, response);
		}
	}
	
	/**
	 * 登录验证
	 */
	private boolean loginCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean flag = true;
		
		HttpSession session = request.getSession(false);
		if (request.getRequestURI().endsWith(Constants.URL_LOGIN)) {
			if (session != null) {
				session.invalidate();
			}
		} else {
			// 判断登录状态
			if (session != null) {
				UserBo user = (UserBo) session.getAttribute(Constants.SESSION_USER);
				if (user == null) {
					flag = false;
					
					response.setStatus(HttpStatus.SC_BAD_REQUEST);
					response.setContentType(MediaType.APPLICATION_JSON);
					response.getWriter().print(FailResult.toJson(Code.UNLOGIN, "用户未登录"));
				}
			} else {
				flag = false;
				
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				response.setContentType(MediaType.APPLICATION_JSON);
				response.getWriter().print(FailResult.toJson(Code.SESSION_TIMEOUT, "SESSION已过期"));
			}
		}
		
		return flag;
	}
}
