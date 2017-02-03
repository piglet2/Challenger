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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.envision.envservice.common.Constants;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.service.CacheService;
import com.envision.envservice.service.RedmineHttpRequestService;

/**
 * 转发到redmine请求
 * 
 * @ClassName RedmineFilter
 * @author song.cai
 * @date 2016-05-26
 */
public class RedmineFilter implements Filter {
	
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
		String url="";
		String api_key="";
		String param="";
		String result="";
		if(requestType=="GET"||"GET".equals(requestType)){
			url=getRedmineUrl(request.getRequestURL().toString());
			api_key=getSessionApiKey(request);
			if(api_key==null||"".equals(api_key)){
				return;
			}
			WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
			RedmineHttpRequestService redmineHttpRequestService = (RedmineHttpRequestService) webAppContext.getBean("redmineHttpRequestService");
			result=redmineHttpRequestService.doGET(url,api_key);
		}else if(requestType=="POST"||"POST".equals(requestType)){
			url=getRedmineUrl(request.getRequestURL().toString());
			api_key=getSessionApiKey(request);
			if(api_key==null||"".equals(api_key)){
				return;
			}
		    param=getParam(request.getInputStream());
			WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
			RedmineHttpRequestService redmineHttpRequestService = (RedmineHttpRequestService) webAppContext.getBean("redmineHttpRequestService");
			result=redmineHttpRequestService.doPost(url,param,api_key);
		}else if(requestType=="PUT"||"PUT".equals(requestType)){
			url=getRedmineUrl(request.getRequestURL().toString());
			api_key=getSessionApiKey(request);
			if(api_key==null||"".equals(api_key)){
				return;
			}
		    param=getParam(request.getInputStream());
			WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
			RedmineHttpRequestService redmineHttpRequestService = (RedmineHttpRequestService) webAppContext.getBean("redmineHttpRequestService");
			result=redmineHttpRequestService.doPut(url,param,api_key);
		}else{
			return;
		}
		
		response.getWriter().write(result);
		return;
		
	}


	/*
	 * 获取redmine请求转发路径
	 * 
	 * */
	public String getRedmineUrl(String str){
		String RedmineUrl=str.substring(str.indexOf("/Challenger/redmine/")+20);
		WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
		CacheService cacheService = (CacheService) webAppContext.getBean("cacheService");
		Map map=cacheService.queryMap("redmine_url");
		String redmine_url=map.get("redmine_url").toString();
		String newUrl=RedmineUrl.replace(RedmineUrl, redmine_url+RedmineUrl);
		return newUrl;
	}
	
	/*
	 * 获取api_key
	 * 
	 * */
	private  String getSessionApiKey(HttpServletRequest request) {
		return ((UserBo) request.getSession().getAttribute(Constants.SESSION_USER)).getApi_key();
    }
	
    /*
     * 中文转义
     * 
     * */
    public static String unescapeJS(String str) {      
        String result=StringEscapeUtils.unescapeJava(str);
        return result;      
    }  
    
    /*
     * 获取参数
     * 
     * */
    public static String getParam(InputStream inputStream) throws IOException {      
    	String result="";
    	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));  
		StringBuffer sb=new StringBuffer();
	    String line;  
	    while ((line = in.readLine()) != null)  {
	        System.out.println(line);  
	        sb.append(line);
	    }  
	    result=sb.toString();
        return result;      
    }  
    
}
