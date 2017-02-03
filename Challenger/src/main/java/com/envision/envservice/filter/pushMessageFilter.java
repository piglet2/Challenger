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
import java.nio.charset.Charset;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.entity.bo.PushBo;
import com.envision.envservice.entity.dto.PushConfig;
import com.envision.envservice.service.MapConfigService;
import com.envision.envservice.service.PushConfigService;
import com.envision.envservice.service.PushService;

/**
 * 转发到redmine请求
 * 
 * @ClassName RedmineFilter
 * @author song.cai
 * @date 2016-05-26
 */
public class pushMessageFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
		PushService pushService = (PushService) webAppContext.getBean("pushService");
		PushConfigService pushConfigService = (PushConfigService) webAppContext.getBean("pushConfigService");
		MapConfigService mapConfigService = (MapConfigService) webAppContext.getBean("mapConfigService");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Content-Type", "application/json; charset=utf-8");
		String requestType=request.getMethod();
		String result="";
		if(requestType=="POST"||"POST".equals(requestType)){
			StringBuilder sb = new StringBuilder();
	        InputStream inputStream = null;
	        BufferedReader reader = null;
	        try {
	            inputStream = request.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
	            String line = "";
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
			JSONObject jt=JSONObject.parseObject(sb.toString());
			String username=jt.getString("username");
			String code=jt.getString("code");
			String message=jt.getString("message");
			String title=jt.getString("title");
			PushBo pb=new PushBo();
			String pushCode=mapConfigService.getValue("clg_push_code");
			if(code==pushCode||pushCode.equals(code)){
				List<PushConfig> list=pushConfigService.queryByUsername(username);
				pb.setDeviceToken(list.get(0).getDeviceToken());
				pb.setText(message);
				pb.setTitle(title);
				try {
					pushService.sendMessageByAndroidToOpenApp(pb);
					pushService.sendMessageByIOSToOpenApp(pb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			return;
		}
		response.getWriter().write(result);
		return;
	}

	
}
