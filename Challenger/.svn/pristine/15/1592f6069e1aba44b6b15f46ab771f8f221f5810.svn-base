/******************************************************************************
 * @File name   :      AppraiseResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :     xuyang.li
 *
 * @Date        :      2015年10月27日 下午1:56:53
 *
 * @Description : 	   对下属点赞或鼓励
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
 * 2015年10月27日 下午1:56:53    			admin     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.common.util.IPUtil;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.PraiseDetail;
import com.envision.envservice.service.AppraiseServiceV2;
import com.envision.envservice.service.SpiritService;
import com.envision.envservice.service.exception.ServiceException;

/**
 * 对下属点赞或鼓励
 * @ClassName AppraiseResource
 * @author xuyang.li
 * @date 2015年10月27日
 */
@Path("appraise")
@Component
public class AppraiseResource {
	
	@Resource(name="appraiseServiceV2")
	private  AppraiseServiceV2 appraiseService;
	
	/**
	 * 对下属点赞或鼓励
	 * @Title: appraise 
	 * @return Response     
	 * @throws Exception 
	 * @Date 2015年10月20日
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response appraise(@Context HttpServletRequest request, PraiseDetail praiseDetail) throws Exception{
		HttpStatus status = HttpStatus.OK;
		String response = null;
		
		// 校验参数
		checkPraiseDetail(praiseDetail);
		UserBo user = (UserBo) request.getSession().getAttribute(Constants.SESSION_USER);
		String userId = user.getUser_id();
		String host = IPUtil.getRemoteAddr(request);
		appraiseService.appraise(host, userId,praiseDetail);
		
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/*
	 * 对点赞参数进行校验
	 */
	private void checkPraiseDetail(PraiseDetail praiseDetail) throws ServiceException {
		if(praiseDetail == null){
			throw new ServiceException(Code.PARAM_ERROR," paramter error");
		}
		if(StringUtils.isBlank(praiseDetail.getTargetUserId())){
			throw new ServiceException(Code.PARAM_ERROR," paramter error:target_user_id");
		}
		if(StringUtils.isBlank(praiseDetail.getItemId())){
			throw new ServiceException(Code.PARAM_ERROR," paramter error:item_id");
		}
		if(!(PraiseType.PRAISE.getType().equals(praiseDetail.getpType())
			|| PraiseType.ENCOURAGE.getType().equals(praiseDetail.getpType()))){
			throw new ServiceException(Code.PARAM_ERROR," paramter error:type");
		}
		if(  !(SpiritService.OPERATION_ADD.equals(praiseDetail.getOperation())
				|| SpiritService.OPERATION_DEL.equals(praiseDetail.getOperation()))){
			throw new ServiceException(Code.PARAM_ERROR," paramter error:operation");
		}
		if(!(SpiritService.DIMENSIONALITY_H.equals(praiseDetail.getDimensionality())
				|| SpiritService.DIMENSIONALITY_W.equals(praiseDetail.getDimensionality()))){
			throw new ServiceException(Code.PARAM_ERROR," paramter error:dimensionality");
		}
	}


}
