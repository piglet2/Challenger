package com.envision.envservice.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.common.enums.Operation;
import com.envision.envservice.common.util.IPUtil;
import com.envision.envservice.entity.bo.LoginBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.DBLogger;
import com.envision.envservice.service.CacheService;
import com.envision.envservice.service.LoggerService;
import com.envision.envservice.service.MapConfigService;
import com.envision.envservice.service.SAPUserService;
import com.envision.envservice.service.UserService;

/**
 * 用户登录
 * @ClassName LoginResource
 * @author guowei.wang
 * @date 2015-10-22
 */
@Path("login")
@Component
public class LoginResource {
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private LoggerService loggerService;
	
	@Autowired
	private MapConfigService mapConfigService;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(@Context HttpServletRequest request, LoginBo loginBo) throws Exception {
		HttpStatus status = HttpStatus.OK;
		DBLogger logger = operationLog(request, loginBo.getUsername());
		UserBo user = userService.login(loginBo.getUsername(), loginBo.getPassword());
		//获取api_key
		String api_key=userService.queryRedmine(loginBo.getUsername(), loginBo.getPassword());
		user.setApi_key(api_key);
		String response = JSONObject.toJSONString(user, JSONFilter.NULLFILTER);
		loggerService.setSuccess(logger.getId(), true);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	
	private DBLogger operationLog(HttpServletRequest request, String username) {
		return loggerService.addLog(IPUtil.getRemoteAddr(request), username, null, Operation.LOGIN);
	}
	
}
