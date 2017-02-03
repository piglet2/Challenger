/******************************************************************************
 * @File name   :      UserCaseResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-4 下午5:42:18
 *
 * @Description : 	   
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
 * 2016-1-4 下午5:42:18    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.entity.bo.AsBo;
import com.envision.envservice.entity.bo.AsUserBo;
import com.envision.envservice.entity.bo.AssessmentAnnInfoBo;
import com.envision.envservice.entity.bo.AssessmentBo;
import com.envision.envservice.entity.bo.AssessmentInfoBo;
import com.envision.envservice.entity.dto.AssessmentCycles;
import com.envision.envservice.service.AssessmentCyclesService;
import com.envision.envservice.service.AssessmentService;
import com.envision.envservice.service.SAPEmpJobService;

/**
 * @ClassName EvaluationResource
 * @author caisong
 * @date 2016-5-11
 */
@Path("assessment")
@Component
public class AssessmentResource {
	
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private AssessmentCyclesService acService;
	
	
	/*
	 * 提交评论
	 * 
	 * */
	@POST
	@Path("/addAssessment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAssessment(AssessmentBo assessmentBo) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		if (!checkParam(assessmentBo)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			response=assessmentService.addAssessment(assessmentBo).toJSONString();
		}
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 新增评价参数验证
	 */
	private boolean checkParam(AssessmentBo assessmentBo) {
		String cycle_id=assessmentBo.getCycleId();
		int priseWill=assessmentBo.getPriseWill();
		int priseWisdom=assessmentBo.getPriseWisdom();
		int priseLove=assessmentBo.getPriseLove();
		return StringUtils.isNotEmpty(cycle_id)&&priseWill>0&&priseWisdom>0&&
				priseLove>0&&priseWill<10&&priseWisdom<10&&priseLove<10;
	}
	
	/**
	 * 评价人查看最新评价列表
	 * 
	 */
	@GET
	@Path("/recent")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryLast(){
		HttpStatus status = HttpStatus.OK;
		AssessmentCycles ac=acService.queryRecent();
		String cycleId=ac.getCycleId();
		AsBo ab=assessmentService.queryByCycleId(cycleId);
		String response = JSONObject.toJSONString(ab, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 上级查看下级评价列表
	 * 
	 */
	@GET
	@Path("/queryUnderlingList/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUnderlingList(@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		List<AssessmentInfoBo> list=assessmentService.queryUnderlingList(userId);
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 查看本期评价详情
	 * 三个参数，周期id，评价人id，被评价人id
	 * 
	 */
	@GET
	@Path("/queryAssessmentInfo/{cycleId}/{criticId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryAssessmentInfo(@PathParam("cycleId") String cycleId,@PathParam("criticId") String criticId,@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		AssessmentBo assessmentBo=assessmentService.queryAssessmentInfo(cycleId,criticId,userId);
		String response = JSONObject.toJSONString(assessmentBo, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 查看上一期期评价详情
	 * 三个参数，周期id，评价人id，被评价人id
	 * 
	 */
	@GET
	@Path("/queryLastAssessmentInfo/{cycleId}/{criticId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryLastAssessmentInfo(@PathParam("cycleId") String cycleId,@PathParam("criticId") String criticId,@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		AssessmentBo assessmentBo=assessmentService.queryLastAssessmentInfo(cycleId,criticId,userId);
		String response = JSONObject.toJSONString(assessmentBo, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 评价人查看上一篇评价列表
	 * 
	 */
	@GET
	@Path("queryLast/{cycleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryLast(@PathParam("cycleId") String cycleId){
		HttpStatus status = HttpStatus.OK;
		AsBo ab=assessmentService.queryLast(cycleId);
		String response = JSONObject.toJSONString(ab, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 评价人查看下一篇评价列表
	 * 
	 */
	@GET
	@Path("queryNext/{cycleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryNext(@PathParam("cycleId") String cycleId){
		HttpStatus status = HttpStatus.OK;
		AsBo ab=assessmentService.queryNext(cycleId);
		String response = JSONObject.toJSONString(ab, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 被评价人查看评价列表
	 * 
	 */
	@GET
	@Path("/queryByCycleIdAndUserId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByCycleIdAndUserId(){
		HttpStatus status = HttpStatus.OK;
		List<AssessmentAnnInfoBo> list=assessmentService.queryAssessmentList();
//		List<AssessmentInfoBo> list=assessmentService.queryAssessmentList();
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 评价人查看评论过那些人
	 */
	@GET
	@Path("/queryUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUsers(){
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		List<AsUserBo> list=assessmentService.queryUser();
		response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 评价人搜索员工
	 * 
	 */
	@GET
	@Path("/queryUsersByUsername/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUsersByUsername(@PathParam("username") String username){
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		List<AsUserBo> list=assessmentService.queryUserByUsername(username);
		response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 判断是否是登陆用户上级
	 * return boolean
	 * true 是上级
	 * false 不是上级
	 */
	@SuppressWarnings("rawtypes")
	@GET
	@Path("/isLower/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isLower(@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		Map map=assessmentService.isLower(userId);
		response = JSONObject.toJSONString(map, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
}
