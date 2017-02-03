/******************************************************************************
 * @File name   :      PraiseDetailService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月11日 下午2:12:40
 *
 * @Description : 	   点赞详情Service
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
 * 2015年11月11日 下午2:12:40    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.dao.SpiritDao;
import com.envision.envservice.entity.bo.PraiseDetailBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.PraiseDetail;
import com.envision.envservice.entity.vo.PraiseDetailVo;

/**
 * @ClassName PraiseDetailService
 * @Description 点赞详情Service
 * @author xuyang.li
 * @date 2015年11月11日
 */
@Service
public class PraiseDetailService {

	@Resource
	private SpiritDao spiritDao;
	@Autowired
	private UserService userService;
	
	/**
	 *  根据点赞条目和userId 查询出点赞详情
	 * @Title: queryByItemIdAndUserID 
	 * @param userId 人员ID
	 * @param itemId 远景精神条目ID
	 * @return PraiseDetailVo     
	 * @throws Exception 
	 * @Date 2015年11月11日
	 */
	public PraiseDetailVo  queryByItemIdAndUserID(String userId, String itemId) throws Exception {
		PraiseDetailBo param = bulidQueryParamter(userId, itemId);
		List<PraiseDetail> praiseDetails = spiritDao.queryPraiseDetailByPdo(param);
		// 保存赞统计结果<userId:num>
		Map<String,Integer> praiseResult = new HashMap<String,Integer>();
		// 保存鼓励统计结果<userId:num>
		Map<String,Integer> encourageResult = new HashMap<String,Integer>();
		calculatePraiseNum(praiseDetails, praiseResult, encourageResult);
		
		return bulidResult(praiseResult,encourageResult);
	}
	
	/*
	 * 构建返回的结果
	 */
	private PraiseDetailVo bulidResult(Map<String, Integer> praiseResult,Map<String, Integer> encourageResult) 
								throws Exception {
		PraiseDetailVo result = new PraiseDetailVo();
		List<PraiseDetailVo.UserCountVo> praiseCount = new ArrayList<PraiseDetailVo.UserCountVo>();
		List<PraiseDetailVo.UserCountVo> encourageCount = new ArrayList<PraiseDetailVo.UserCountVo>();

		int praiseTotal = bulidCountList(praiseResult, result, praiseCount);
		int encourageTotal = bulidCountList(encourageResult, result, encourageCount);
		
		result.setPraise_total(String.valueOf(praiseTotal));
		result.setEncourage_total(String.valueOf(encourageTotal));
		result.setPraise(praiseCount);
		result.setEncourage(encourageCount);
		
		return result;
	}

	
	private int bulidCountList(Map<String, Integer> praiseResult,PraiseDetailVo result, 
					List<PraiseDetailVo.UserCountVo> praiseCount) throws Exception {
		int total = 0;
		for(Map.Entry<String, Integer> entry : praiseResult.entrySet()){
			String userId = entry.getKey();
			int num = entry.getValue();
			if(num>0){
				total = total+num;
				
				UserBo userBo = userService.queryUser(true, userId);
				PraiseDetailVo.UserCountVo ucv = result.new UserCountVo();
				ucv.setCn_name(userBo.getCn_name());
				ucv.setPhoto(userBo.getPhoto());
				ucv.setCount(String.valueOf(num));
				ucv.setEn_name(userBo.getEn_name());
				ucv.setUser_id(userId);
				praiseCount.add(ucv);
			}
		}
		
		return total;
	}

	/*
	 * 计算点赞和鼓励的数据
	 * 并将结果保存到对应的Map中
	 * praiseDetails 点赞详情
	 * praiseResult 保存点赞结果Map
	 * encourageResult 保存鼓励结果Map
	 */
	private void calculatePraiseNum(List<PraiseDetail> praiseDetails,
					Map<String, Integer> praiseResult,Map<String, Integer> encourageResult) {
		
		for(PraiseDetail pd:praiseDetails){
			// 点赞
			if(PraiseType.PRAISE.getType().equals(pd.getpType())){
				calculate(pd, praiseResult);
			}else{
				// 鼓励
				calculate(pd, encourageResult);
			}
		}
	}
	
	/*
	 * 具体的计算
	 * pd 要统计的点赞信息
	 * praiseResult 保存统计的结果集合
	 */
	private void calculate(PraiseDetail pd, Map<String, Integer> praiseResult) {
		int num = getMapIntValue(praiseResult, pd.getUserId());;
		if(SpiritService.OPERATION_ADD.equals(pd.getOperation())){
			num++;
		}else{
			num--;
		}
		praiseResult.put(pd.getUserId(), num);
	}
	
	/*
	 * 重Map中取Key的值，如果没有，返回0
	 */
	private int getMapIntValue(Map<String,Integer> sourceMap, String key){
		int num = 0;
		if(sourceMap.get(key) != null){
			num = sourceMap.get(key);
		}
		
		return num;
	}

	/*
	 * 构建查询的参数
	 */
	private PraiseDetailBo bulidQueryParamter(String userId, String itemId) {
		PraiseDetailBo param = new PraiseDetailBo();
		param.setTargetUserId(userId);
		param.setItemId(itemId);
		
		return param;
	}
	
	/**
	 * 批量ID查询点赞记录
	 */
	public List<PraiseDetail> queryPraiseDetails(String... ids) throws Exception {
		Objects.requireNonNull(ids);
		
		List<PraiseDetail> lstPraiseDetail = new LinkedList<PraiseDetail>();
		if (ids.length != 0) {
			lstPraiseDetail = spiritDao.queryByIds(ids);
		}
		
		return lstPraiseDetail;
	}
	
	/**
	 * 批量ID查询点赞记录(Map)
	 */
	public Map<String, PraiseDetail> queryLeavewordMap(String... ids) throws Exception {
		Map<String, PraiseDetail> praiseDetailMapping = new HashMap<String, PraiseDetail>();
		
		List<PraiseDetail> lstPraiseDetail = queryPraiseDetails(ids);
		for (PraiseDetail praiseDetail : lstPraiseDetail) {
			praiseDetailMapping.put(praiseDetail.getId(), praiseDetail);
		}
		
		return praiseDetailMapping;
	}
}
