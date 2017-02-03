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
import com.envision.envservice.entity.bo.AssessmentBo;
import com.envision.envservice.entity.bo.EvaluationAnnInfoBo;
import com.envision.envservice.entity.bo.EvaluationBo;
import com.envision.envservice.entity.bo.EvaluationInfoBo;
import com.envision.envservice.entity.bo.UserPhoto;
import com.envision.envservice.entity.dto.EvaluationPeriod;
import com.envision.envservice.service.EvaluationPeriodService;
import com.envision.envservice.service.EvaluationService;
import com.envision.envservice.service.SAPEmpJobService;

/**
 * @ClassName EvaluationResource
 * @author caisong
 * @date 2016-5-11
 */
@Path("evaluation")
@Component
public class EvaluationResource {
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private EvaluationPeriodService evaluationPeriodService;
	
	
	/**
	 * 提交评论
	 * 
	 * */
	@POST
	@Path("/addEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEvaluation(EvaluationBo evaluationBo) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		if (!checkParam(evaluationBo)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			response=evaluationService.addEvaluation(evaluationBo).toJSONString();
		}
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 对下级暂不评价
	 * 
	 * */
	@POST
	@Path("/addNullEvaluationForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNullEvaluationForUser(EvaluationBo evaluationBo) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		if (!checkParamForUser(evaluationBo)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			response=evaluationService.addNullEvaluationForUser(evaluationBo).toJSONString();
		}
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 上级暂不评价
	 * 
	 * */
	@POST
	@Path("/addNullEvaluationForManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNullEvaluationForManager(EvaluationBo evaluationBo) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		if (!checkParamForManager(evaluationBo.getRemark())) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			response=evaluationService.addNullEvaluationForManager(evaluationBo.getRemark()).toJSONString();
		}
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 上级暂不评价权限判断
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	@GET
	@Path("/checkManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkManager() throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		Map map=evaluationService.checkForManager();
		response = JSONObject.toJSONString(map, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 上级查看最新评价列表
	 * 
	 */
	@GET
	@Path("/queryByPeriodIdAndEvaluationId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByPeriodIdAndEvaluationId(){
		HttpStatus status = HttpStatus.OK;
		EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		String periodId=lastEP.getPeriodId();
		List<EvaluationBo> list=evaluationService.queryByPeriodIdAndEvaluationId(periodId);
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 上级查看上一篇评价列表
	 * 
	 */
	@GET
	@Path("queryLast/{periodId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryLast(@PathParam("periodId") String periodId){
		HttpStatus status = HttpStatus.OK;
		List<EvaluationBo> list=evaluationService.queryLast(periodId);
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 上级查看下一篇评价列表
	 * 
	 */
	@GET
	@Path("queryNext/{periodId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryNext(@PathParam("periodId") String periodId){
		HttpStatus status = HttpStatus.OK;
		List<EvaluationBo> list=evaluationService.queryNext(periodId);
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 查看员工评价详情
	 */
	@GET
	@Path("/queryEvInfo/{periodId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryEvInfo(@PathParam("periodId") String periodId,@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		EvaluationInfoBo evaluationInfoBo=evaluationService.queryEvInfo(periodId,userId);
		String response = JSONObject.toJSONString(evaluationInfoBo, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 上级查看员工评价详情
	 * 上一期
	 */
	@GET
	@Path("/queryEvInfoLast/{periodId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryEvInfoLast(@PathParam("periodId") String periodId,@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		EvaluationInfoBo evaluationInfoBo=evaluationService.queryEvInfoLast(periodId,userId);
		String response = JSONObject.toJSONString(evaluationInfoBo, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 查看员工评价详情
	 * 下一期
	 */
	@GET
	@Path("/queryEvInfoNext/{periodId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryEvInfoNext(@PathParam("periodId") String periodId,@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		EvaluationInfoBo evaluationInfoBo=evaluationService.queryEvInfoNext(periodId,userId);
		String response = JSONObject.toJSONString(evaluationInfoBo, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 上级查看下属评价列表
	 * @throws Exception 
	 * 
	 */
	@GET
	@Path("/queryUnderlingList/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUnderlingList(@PathParam("userId") String userId) throws Exception{
		HttpStatus status = HttpStatus.OK;
		List<EvaluationInfoBo> list=evaluationService.queryUnderlingList(userId);
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 下属查看评价列表
	 * @throws Exception 
	 * 
	 */
	@GET
	@Path("/queryByPeriodIdAndUserId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByPeriodIdAndUserIdList() throws Exception{
		HttpStatus status = HttpStatus.OK;
		List<EvaluationAnnInfoBo> list=evaluationService.queryEvaluationList();
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 是否需要评价
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@GET
	@Path("/queryByPeriodId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByPeriodId(){
		HttpStatus status = HttpStatus.OK;
		Map map=evaluationService.judgeEvaluation();
		String response = JSONObject.toJSONString(map, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 查看个人平均分
	 */
	@SuppressWarnings("rawtypes")
	@GET
	@Path("/queryUserAverage/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryUserAverage(@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		Map map=evaluationService.queryUserAverage(userId);
		String response = JSONObject.toJSONString(map, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	/**
	 * 查看部门平均分
	 */
	@SuppressWarnings("rawtypes")
	@GET
	@Path("/queryDepAverage/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryAverage(@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.OK;
		Map map=evaluationService.queryDepAverage(userId);
		String response = JSONObject.toJSONString(map, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 提交反馈
	 * 
	 * */
	@POST
	@Path("/addFeedback")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFeedback(EvaluationBo evaluationBo) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		if (!checkFeedback(evaluationBo)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			response=evaluationService.addFeedback(evaluationBo).toJSONString();
		}
		return Response.status(status.value()).entity(response).build();
	}
	
	
	/**
	 * 新增评价参数验证
	 */
	private boolean checkParam(EvaluationBo evaluationBo) {
		String period_id=evaluationBo.getPeriodId();
		int priseWill=evaluationBo.getPriseWill();
		int priseWisdom=evaluationBo.getPriseWisdom();
		int priseLove=evaluationBo.getPriseLove();
		return StringUtils.isNotEmpty(period_id)&&priseWill>0&&priseWisdom>0&&
				priseLove>0&&priseWill<10&&priseWisdom<10&&priseLove<10;
	}
	
	/**
	 * 对下级暂不评价参数验证
	 */
	private boolean checkParamForUser(EvaluationBo evaluationBo) {
		String userId=evaluationBo.getUserId();
		String remark=evaluationBo.getRemark();
		return StringUtils.isNotEmpty(remark)&&StringUtils.isNotEmpty(userId);
	}
	
	/**
	 * 对非空参数验证
	 */
	private boolean checkParamForManager(String str) {
		return StringUtils.isNotEmpty(str);
	}
	
	
	/**
	 * 新增评价反馈参数验证
	 */
	private boolean checkFeedback(EvaluationBo evaluationBo) {
		String period_id=evaluationBo.getPeriodId();
		int feedback=evaluationBo.getFeedback();
		return StringUtils.isNotEmpty(period_id)&&feedback>0&&feedback<4;
	}
	
	
	/**
	 * 获取所有challenger用户照片路径
	 */
	@GET
	@Path("/getAllUserPhoto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUserPhoto(){
		HttpStatus status = HttpStatus.OK;
		List<UserPhoto> list=evaluationService.getAllUserPhoto();
		String response = JSONObject.toJSONString(list, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}

	/**
	 * 上级查看最新一期的上一期评论详情
	 * 
	 */
	@GET
	@Path("/queryLastEvaByRecent/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryLastEvaByRecent(@PathParam("userId") String userId){
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		EvaluationInfoBo ab=evaluationService.queryLastEvaByRecent(userId);
		response = JSONObject.toJSONString(ab, JSONFilter.UNDERLINEFILTER);
		return Response.status(status.value()).entity(response).build();
	}
	
}
