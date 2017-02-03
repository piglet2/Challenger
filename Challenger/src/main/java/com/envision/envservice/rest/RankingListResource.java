/******************************************************************************
 * @File name   :      LowerSortResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月27日 下午3:10:59
 *
 * @Description : 	   下属点赞排行
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
 * 2015年10月27日 下午3:10:59    			     xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import java.util.List;

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

import com.envision.envservice.common.Code;
import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.entity.bo.SpiritSortBo;
import com.envision.envservice.service.RankingListService;
import com.envision.envservice.service.exception.ServiceException;

/**
 * 下属点赞排行
 * @ClassName LowerSortResource
 * @author xuyang.li
 * @date 2015年10月27日
 */
@Path("ranking_list")
@Component
public class RankingListResource {

	public static final String PRAISE_TYPE_ALL = "all";
	
	@Resource
	private RankingListService lowerSortService;
	
	/**
	 * 获取userId给下属评价的排行
	 * @Title: spiritSort 
	 * @param userId
	 * @return Response     
	 * @throws Exception 
	 * @Date 2015年10月26日
	 */
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response spiritSort(@PathParam("userId") String userId, @QueryParam("spirit_type") String spiritType) 
			throws Exception{
		HttpStatus status = HttpStatus.OK;
		checkSortParam(spiritType);
		List<SpiritSortBo> spiritSortBos = lowerSortService.spiritSort(userId,spiritType);
		
		return Response.status(status.value()).entity(spiritSortBos).build(); 
	}
	
	/*
	 * 校验类型。
	 * type 的值只有all、praise、encourage
	 */
	private void checkSortParam(String spiritType) throws ServiceException {
		if(!(PRAISE_TYPE_ALL.equalsIgnoreCase(spiritType) 
				|| PraiseType.PRAISE.getType().equals(spiritType)
				|| PraiseType.ENCOURAGE.getType().equals(spiritType))){
			throw new ServiceException(Code.PARAM_ERROR," paramter error:spirit_type");
		}
		
	}
}
