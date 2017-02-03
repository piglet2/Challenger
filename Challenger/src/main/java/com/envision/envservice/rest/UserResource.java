/******************************************************************************
 * @File name   :      UserResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-19 下午4:47:59
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
 * 2015-10-19 下午4:47:59    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.common.util.PicUtil;
import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.vo.UserDetailVo;
import com.envision.envservice.service.UserDetailService;
import com.envision.envservice.service.UserService;
import com.envision.envservice.service.exception.ServiceException;
import com.envision.envservice.validator.LimitFieldValidator;
import com.envision.envservice.validator.item.P2PRuleItem;

/**
 * @ClassName UserResource
 * @author guowei.wang
 * @date 2015-10-19
 */
@Path("user")
@Component
public class UserResource {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private LimitFieldValidator limitFieldValidator;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUser(@PathParam("id") String id) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String repsonse = null;
		
		paramCheck(id);
		
		UserBo user = userService.queryUser(id);
		if (user != null) {
			repsonse = JSONObject.toJSONString(user, buildLimitFieldFilter());
		} else {
			status = HttpStatus.NOT_FOUND;
			repsonse = FailResult.toJson(Code.USER_NOT_FOUND, "没有找到该用户");
		}
		
		return Response.status(status.value()).entity(repsonse).build();
	}

	@GET
	@Path("/detail/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUserDetail(@PathParam("id") String id) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String repsonse = null;
		
		paramCheck(id);
		
		UserDetailVo userDetail = userDetailService.queryUserDetail(id);
		if (userDetail != null) {
			repsonse = JSONObject.toJSONString(userDetail, JSONFilter.NULLFILTER);
		} else {
			status = HttpStatus.NOT_FOUND;
			repsonse = FailResult.toJson(Code.USER_NOT_FOUND, "没有找到该用户");
		}
		
		return Response.status(status.value()).entity(repsonse).build();
	}
	
	/**
	 * 根据用户名或中文名查询用户
	 * @Title: searchByUserName 
	 * @return Response     
	 * @Date 2015年11月4日
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByUsernameOrLastName(@QueryParam("username") String username) throws Exception {
		paramCheck(username);
		
		String repsonse = JSONObject.toJSONString(userService.queryByUsernameOrLastName(username.trim()));
		
		return Response.status(HttpStatus.OK.value()).entity(repsonse).build();
	}
	
	private ValueFilter buildLimitFieldFilter() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(P2PRuleItem.PARAM_USERID_TARGET, SessionUtil.getUserId(session));
		
		Map<String, String> jsonValueParams = new HashMap<String, String>();
		jsonValueParams.put(P2PRuleItem.PARAM_USERID, "user_id");
		
		return JSONFilter.buildLimitFieldFilter(limitFieldValidator, params, jsonValueParams);
	}
	
	private void paramCheck(String id) throws ServiceException {
		if(StringUtils.isEmpty(id)){
			throw new ServiceException(Code.PARAM_ERROR,"参数有误");
		}
	}
}
