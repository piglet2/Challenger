/******************************************************************************
 * @File name   :      PraiseDetailResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月11日 上午10:24:51
 *
 * @Description : 	   点赞详情接口
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
 * 2015年11月11日 上午10:24:51    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.service.PraiseDetailService;

/**
 * @ClassName PraiseDetailResource
 * @Description 点赞详情接口
 * @author xuyang.li
 * @date 2015年11月11日
 */

@Component
@Path("praise_detail")
public class PraiseDetailResource {
	
	@Resource
	private PraiseDetailService praiseDetailService;
	/**
	 * 获取用户的远景精神
	* @Title: spirit 
	* @param userId 用户id
	* @return Response     
	 * @throws Exception 
	* @Date 2015年10月20日
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response spirit(@QueryParam("item_id") String itemId,@QueryParam("user_id") String userId) throws Exception{
		HttpStatus status = HttpStatus.OK;
		String response = JSONObject.toJSONString(praiseDetailService.queryByItemIdAndUserID(userId,itemId));
		
		return Response.status(status.value()).entity(response).build(); 
	}
}
