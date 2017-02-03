/******************************************************************************
 * @File name   :      SpiritResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午6:20:16
 *
 * @Description : 	   远景精神
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
 * 2015年10月19日 下午6:20:16    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.service.SpiritService;

/**
 * 远景精神接口
 * @ClassName SpiritResource
 * @author xuyang.li
 * @date 2015年10月19日
 */
@Path("spirit")
@Component
public class SpiritResource {

	@Resource
	private  SpiritService spiritService;
	
	public static final String TARGET_USER_ID = "target_user_id";
	public static final String TYPE = "type";
	public static final String OPERATION = "operation";
	public static final String DIMENSIONALITY = "dimensionality";
	public static final String ITEM_ID = "item_id";
	public static final String PRAISE_TYPE_ALL = "all";

	/**
	 * 获取用户的远景精神
	* @Title: spirit 
	* @param userId 用户id
	* @return Response     
	* @Date 2015年10月20日
	 */
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response spirit(@PathParam("userId") String userId, @QueryParam("from") String from){
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(spiritService.spirit(userId,from));
		
		return Response.status(status.value()).entity(response).build(); 
	}
}
