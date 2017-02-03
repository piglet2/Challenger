/******************************************************************************
 * @File name   :      CodeInforResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午3:17:54
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
 * 2015年10月19日 下午3:17:54    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.envision.envservice.service.CodeInforService;

/**
 * @ClassName CodeInforResource
 * @author xuyang.li
 * @date 2015年10月19日
 */
@Path("codes")
@Component
public class CodeInforResource {
	
	@Resource
	private CodeInforService codeInforService;
	
	/**
	 * 查询说有错误码信息
	 * @Title: codes
	 * @return 错误码信息
	 * @return Response
	 * @Date 2015年10月19日
	 */
	@GET
	@Produces("application/json")
	public Response codes() {
		// 设置http 返回头状态
		HttpStatus status = HttpStatus.OK;
		String repsonse = JSONArray.toJSONString(codeInforService.codes());
			
		return Response.status(status.value()).entity(repsonse).build();
	}
}
