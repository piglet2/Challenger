/******************************************************************************
 * @File name   :      CommentTopResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2016年4月6日 上午10:41:25
 *
 * @Description : 	   对评论顶功能
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
 * 2016年4月6日 上午10:41:25    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.envision.envservice.entity.vo.CommentTopVo;
import com.envision.envservice.service.CommentTopService;
import com.envision.envservice.service.exception.ServiceException;

/**
 * @ClassName CommentTopResource
 * @Description 对评论顶功能
 * @author xuyang.li
 * @date 2016年4月6日
 */

@Path("comment_top")
@Component
public class CommentTopResource {

	@Autowired
	private CommentTopService commentTopService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response commentTop(CommentTopVo commentTopVo) throws ServiceException {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		commentTopService.commentTop(commentTopVo.getComment_id());
		
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 取消顶
	 * @Title: cancelCommentTop 
	 * @param commentId
	 * @return Response     
	 * @throws ServiceException 
	 * @Date 2016年4月6日
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancel/{comment_id}")
	public Response cancelCommentTop(@PathParam("comment_id") int commentId) throws ServiceException{
		HttpStatus status = HttpStatus.OK;
		String response = StringUtils.EMPTY;
		commentTopService.cancelCommentTop(commentId);
		
		return Response.status(status.value()).entity(response).build();
	}
}
