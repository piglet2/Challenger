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
import com.envision.envservice.entity.dto.Evaluation;
import com.envision.envservice.service.EvaluationService;
import com.envision.envservice.service.PicRequestService;

/**
 * 转发到redmine请求
 * 
 * @ClassName RedmineFilter
 * @author song.cai
 * @date 2016-05-26
 */
public class EvaluationFilter implements Filter {
	
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
			String periodId=jt.getString("periodId");
			String managerId=jt.getString("managerId");
			String userId=jt.getString("userId");
			String priseWill=jt.getString("priseWill");
			String priseWisdom=jt.getString("priseWisdom");
			String priseLove=jt.getString("priseLove");
			String prosWill=jt.getString("prosWill");
			String prosWisdom=jt.getString("prosWisdom");
			String prosLove=jt.getString("prosLove");
			String remark=jt.getString("remark");
			Evaluation eva=new Evaluation();
			eva.setPeriodId(periodId);
			eva.setManagerId(managerId);
			eva.setUserId(userId);
			eva.setPriseWill(Integer.parseInt(priseWill));
			eva.setPriseWisdom(Integer.parseInt(priseWisdom));
			eva.setPriseLove(Integer.parseInt(priseLove));
			eva.setProsWill(prosWill);
			eva.setProsWisdom(prosWisdom);
			eva.setProsLove(prosLove);
			eva.setRemark(remark);
			WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
			EvaluationService evaluationService = (EvaluationService) webAppContext.getBean("evaluationService");
			
			if(evaluationService.addEvaluationOfFilter(eva)>0){
				result="新增纵向评价数据成功";
			}else{
				result="新增纵向评价数据失败";
			}
		}else{
			return;
		}
		response.getWriter().write(result);
		return;
	}

	
}
