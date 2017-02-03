/******************************************************************************
 * @File name   :      DemoAdvice.java
 *
 * @Package 	:	   com.envision.envservice.rest.advice
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-16 下午2:04:00
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
 * 2015-10-16 下午2:04:00    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest.advice;

import javax.ws.rs.core.Response;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.exception.ServiceException;

/**
 * Common AOP
 * 		异常统一处理AOP
 * 
 * @ClassName ExceptionAdvice
 * @author guowei.wang
 * @date 2015-10-16
 */
@Aspect
@Component
public class ExceptionAdvice implements Ordered {
	
	private static final Logger REST_ERROR_LOGGER = EnvLog.getRestErrorLogger();

	private static final Logger BUSINESS_FAIL_LOGGER = EnvLog.getBusinessFailLogger();

	@Around(value = "execution(public * com.envision.envservice.rest.*.*(..))")
	public Response around(ProceedingJoinPoint pjp) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Code code = Code.BUSINESS_FAIL;

		String response = null;
		try {
			return (Response) pjp.proceed();
		} catch (ServiceException e) {
			BUSINESS_FAIL_LOGGER.error(e.getMessage(), e);
			
			code = e.getCode() == null ? code : e.getCode();
			response = FailResult.toJson(code, e.getMessage());
		} catch (Throwable e) {
			REST_ERROR_LOGGER.error(e.getMessage(), e);

			response = FailResult.toJson(Code.ERROR, e.getMessage());
		}

		return Response.status(status.value()).entity(response).build();
	}

	@Override
	public int getOrder() {
		return 2;
	}
}
