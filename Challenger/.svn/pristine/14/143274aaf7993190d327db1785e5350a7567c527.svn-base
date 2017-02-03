/******************************************************************************
 * @File name   :      HttpAdvice.java
 *
 * @Package 	:	   com.envision.envservice.rest.advice
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-2-2 上午11:13:19
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
 * 2016-2-2 上午11:13:19    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest.advice;

import javax.ws.rs.core.Response;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @ClassName HttpAdvice
 * @author guowei.wang
 * @date 2016-2-2
 */
@Aspect
@Component
public class HttpAdvice implements Ordered {
	
	private static final String SERVER_TIME = "Server-Time";
	
	@Around(value = "execution(public * com.envision.envservice.rest.*.*(..))")
	public Response around(ProceedingJoinPoint pjp) throws Throwable {
		Response response = (Response) pjp.proceed();
		setExtraHeaders(response);
		
		return response;
	}
	
	private void setExtraHeaders(Response response) {
		setServerTime(response);
	}
	
	private void setServerTime(Response response) {
		response.getMetadata().add(SERVER_TIME, String.valueOf(System.currentTimeMillis()));
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
