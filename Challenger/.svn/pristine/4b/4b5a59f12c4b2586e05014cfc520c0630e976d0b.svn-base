/******************************************************************************
 * @File name   :      SpiritService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午6:28:12
 *
 * @Description : 	   远景精神接口service
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
 * 2015年10月19日 下午6:28:12    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.dao.ChallengerItemDao;
import com.envision.envservice.dao.SpiritDao;
import com.envision.envservice.entity.dto.ChallengerItem;
import com.envision.envservice.entity.dto.PraiseDetail;

/**
 * @ClassName SpiritService
 * @author xuyang.li
 * @date 2015年10月19日
 */
@Service
public class SpiritService {

	private static final String PRAISETOTAL= "praise_total";
	private static final String ENCOURAGETOTAL= "encourage_total";
	private static final String PRAISECOUNT= "praise_count";
	private static final String PRAISED= "praised";
	private static final String ENCOURAGED= "encouraged";
	private static final String ENCOURAGECOUNT= "encourage_count";
	
	/** 评价*/
	public static final String OPERATION_ADD= "ADD";
	/** 取消评价*/
	public static final String OPERATION_DEL= "DELETE";
	public static final String DIMENSIONALITY_H= "H";
	public static final String DIMENSIONALITY_W= "W";
	
	@Resource
	private SpiritDao spiritDao;
	@Resource
	private ChallengerItemDao challengerItemDao;
	
	/**
	 * 
	* @Title: spirit 
	* @Description: 查询远景精神
	* @return JSONObject 远景精神信息 
	* @Date 2015年10月19日
	 */
	public JSONObject spirit(String userId, String from) {
		List<PraiseDetail> praiseDetails = spiritDao.queryByUserId(userId);
		// 计算点赞数<type_id:num>
		Map<String,Integer> praiseNum = calculatePraiseNum(praiseDetails);
		// 取所有的远景精神条目
		List<ChallengerItem> items = challengerItemDao.queryItems();
		
		Map<String,Map<String,String>> relationMap = getItemRelationMap(items);
		// 判断是否是自己查看自己
		boolean isOwn = (from==null||userId.equals(from)) ? true : false;
		
		// 查出所有某个主管今天评价的的item
		List<PraiseDetail> todayAppraisedPDFrom =  null;
		Map<String,Integer> todayPraiseNum = null;
		if( !isOwn ){
			todayAppraisedPDFrom =  getTodayAppraisedPDFrom(praiseDetails, from);
			// 今天的点赞数
			todayPraiseNum = calculatePraiseNum(todayAppraisedPDFrom);
		}
		return buildResult(praiseNum, relationMap, isOwn, todayPraiseNum);
	}
	
	/*
	 * 查询今天被managerId的所有评价信息
	 */
	private List<PraiseDetail> getTodayAppraisedPDFrom(
				List<PraiseDetail> praiseDetails, String managerId) {
		List<PraiseDetail> todayAppraised = new ArrayList<PraiseDetail>();
		for(PraiseDetail pd : praiseDetails){
			if( DateUtil.todayStartTime().getTime() < pd.getCts().getTime()
					&& pd.getCts().getTime() <DateUtil.todayEndTime().getTime()){
				// 当天
				if(pd.getUserId().equals(managerId)){
					// 同一个managerId
					todayAppraised.add(pd);
				}
			}
			
		}
		return todayAppraised;
	}

	/*
	 * 根据点赞数和点赞关系表，构建点赞结果json数据
	 */
	private JSONObject buildResult(Map<String, Integer> praiseNum,
			Map<String, Map<String, String>> relationMap, 
			boolean isOwn, Map<String,Integer> todayPraiseNum) {
		// 返回结果json数据
		JSONObject result = new JSONObject();
		
		
		for(Entry<String, Map<String, String>> topEntry : relationMap.entrySet()){
			int praiseTotal = 0;
			int encourageTotal = 0;
			JSONObject topJson = new JSONObject();
			
			String topkey = topEntry.getKey(); 
			Map<String,String> leveMap = topEntry.getValue();
			
			for(Entry<String,String> leveEntry : leveMap.entrySet()){
				JSONObject itemJson = new JSONObject();
				
				String levekey = leveEntry.getKey();
				String leveVaue = leveEntry.getValue();
				
				int pNum = getMapIntValue(praiseNum,getTakeKey(PraiseType.PRAISE.getType(), leveVaue));
				int eNum = getMapIntValue(praiseNum ,getTakeKey(PraiseType.ENCOURAGE.getType(), leveVaue));
				praiseTotal = praiseTotal+pNum;
				encourageTotal = encourageTotal+eNum;
				
				itemJson.put(PRAISECOUNT, String.valueOf(pNum));
				itemJson.put(ENCOURAGECOUNT, String.valueOf(eNum));
				
				// 不是自己查看，添加praised，和encouraged 字段
				if(!isOwn){
					int pTNum = getMapIntValue(todayPraiseNum,getTakeKey(PraiseType.PRAISE.getType(), leveVaue));
					int eTNum = getMapIntValue(todayPraiseNum ,getTakeKey(PraiseType.ENCOURAGE.getType(), leveVaue));
					
					itemJson.put(PRAISED, String.valueOf(pTNum>0 ? true : false));
					itemJson.put(ENCOURAGED, String.valueOf(eTNum>0 ? true : false));
					
				}
				topJson.put(levekey, itemJson);
			}
			
			topJson.put(PRAISETOTAL, String.valueOf(praiseTotal));
			topJson.put(ENCOURAGETOTAL, String.valueOf(encourageTotal));
			result.put(topkey, topJson);
			
		}
		return result;
	}

	/*
	 * 取所有条目的关系Map
	 */
	private Map<String, Map<String,String>> getItemRelationMap(List<ChallengerItem> items) {
		List<ChallengerItem> topItems = getLevelItems(items,"0");
		Map<String, Map<String,String>> relationMap = new HashMap<String, Map<String,String>>();
		
		for(ChallengerItem ci : topItems){
			List<ChallengerItem> leveItems = getLevelItems(items,ci.getId());
			
			for(ChallengerItem lt : leveItems){
				Map<String,String> levelRelationMap = relationMap.get(ci.getItemKey());
				if(levelRelationMap == null){
					levelRelationMap = new HashMap<String, String>();
					relationMap.put(ci.getItemKey(), levelRelationMap);
				}
				
				levelRelationMap.put(lt.getItemKey(), lt.getId());
			}
		}
		
		return relationMap;
	}

	/*
	 * 取出同级条目
	 */
	private List<ChallengerItem> getLevelItems(List<ChallengerItem> items,String level) {
		List<ChallengerItem> levelItems = new ArrayList<ChallengerItem>();
		
		for(ChallengerItem ci : items){
			if(level.equals(ci.getItemPId())){
				levelItems.add(ci);
			}
		}
		
		return levelItems;
	}

	/**
	 * 计算点赞条目数以<type_id:num>的格式存入Map中返回
	 * @Title: calculatePraiseNum 
	 * @param praiseDetails 点赞信息 
	 * @return Map<String,Integer>   <type_id:num>格式的Map数据  
	 * @Date 2015年10月27日
	 */
	public Map<String, Integer> calculatePraiseNum(List<PraiseDetail> praiseDetails) {
		Map<String,Integer> praiseNum = new HashMap<String, Integer>();
		
		for(PraiseDetail p : praiseDetails){
			calculatePraise(p,praiseNum);
		}
		
		return praiseNum;
	}

	/*
	 * 计算赞信息的数量。
	 */
	private void calculatePraise(PraiseDetail p,Map<String,Integer> praiseNum) {
		String key = getSaveKey(p);
		int num = calculatePraiseByKey(p, praiseNum, key);
		
		praiseNum.put(key, num);
	}
	
	/**
	 * 从sourceMap 中取特定的key 的值
	 * 如果sourceMap中不包括Key 返回 0
	 * @Title: getMapIntValue 
	 * @param sourceMap 
	 * @param key
	 * @return int     
	 * @Date 2015年10月27日
	 */
	public int getMapIntValue(Map<String,Integer> sourceMap,String key){
		int num = 0;
		if(sourceMap.get(key) != null){
			num = sourceMap.get(key);
		}
		
		return num;
	}
	
	/**
	 * 根据点赞信息 拼接要将缓存的key
	 * key 的拼接方式为pType_itemId
	 * @Title: getSaveKey 
	 * @return String  pType_itemId 格式的字符串   
	 * @Date 2015年10月27日
	 */
	public String getSaveKey(PraiseDetail p){
		return p.getpType()+"_"+p.getItemId();
	}
	
	/**
	 * 拼接要将缓存的key
	 * key 的拼接方式为 PraiseDetailType_itemId
	 * @param PraiseDetailType 
	 * @param itemId
	 * @return String  PraiseDetailType_itemId 格式的字符串   
	 * @Date 2015年10月27日
	 */
	public String getTakeKey(String PraiseDetailType,String itemId){
		return PraiseDetailType+"_"+itemId;
	}

	/**
	 * 根据key 和点赞信息 p 计算 要 缓存到 praiseNum 中的数
	 * @Title: calculatePraiseByKey 
	 * @param PraiseDetail p 点赞信息
	 * @param Map<String, Integer> praiseNum 缓存数据的集合
	 * @param String key 保存的key
	 * @return int  要缓存的数据
	 * @Date 2015年10月27日
	 */
	public int calculatePraiseByKey(PraiseDetail p,
			Map<String, Integer> praiseNum, String key) {
		int num = getMapIntValue(praiseNum,key);
		
		if (OPERATION_ADD.equals(p.getOperation())) {
			num++;
		} else if (OPERATION_DEL.equals(p.getOperation())) {
			num--;
		} else {
			throw new RuntimeException("praise detail opertion error: "+p.getOperation());
		}
		return num;
	}

	public List<PraiseDetail> queryMatch(PraiseDetail match) {
		Objects.requireNonNull(match);
		
		return spiritDao.queryMatch(match);
	}
}
