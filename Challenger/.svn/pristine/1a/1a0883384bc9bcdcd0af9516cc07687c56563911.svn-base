/******************************************************************************
 * @File name   :      LowerSortService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月27日 下午4:35:09
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
 * 2015年10月27日 下午4:35:09    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.common.enums.SortType;
import com.envision.envservice.dao.SpiritDao;
import com.envision.envservice.entity.bo.PraiseDetailBo;
import com.envision.envservice.entity.bo.SpiritSortBo;
import com.envision.envservice.entity.dto.PraiseDetail;
import com.envision.envservice.rest.RankingListResource;

/**
 * 下属点赞排行
 * @ClassName LowerSortService
 * @author xuyang.li
 * @date 2015年10月27日
 */
@Service
public class RankingListService {

	private  static final String TOTAL= "total";
	
	@Resource
	private SpiritDao spiritDao;
	@Resource
	private  SpiritService spiritService;
	@Autowired
	private OrgStructureService orgStructureService;
	
	/**
	 * 获取userId给下属评价的排行
	 * @Title: spiritSort 
	 * @param userId
	 * @param spiritType
	 * @return SpiritSortBo     
	 * @throws Exception 
	 * @Date 2015年10月26日
	 */
	public List<SpiritSortBo> spiritSort(String userId, String spiritType) throws Exception {
		// 查询出用户所有点赞信息
		PraiseDetailBo param = new PraiseDetailBo();
		param.setUserId(userId);
		List<PraiseDetail> praiseDetails = spiritDao.queryPraiseDetailByPdo(param);
		// 计算出点赞用户的点赞和鼓励数<targetUserId_type:num>
		Map<String,Integer> userPraiseNum = calculateUserPraiseNum(praiseDetails);
		// userId 的所有下属
		Set<String> lowers =  getLowerId(userId);
		// <userId : num>
		Map<String,Integer> lowerUserPraiseNum = calculateUserPraiseNumByType(userPraiseNum,lowers,spiritType);
		// 默认降序
		List<SpiritSortBo> Praise = sortuserPraiseNumSort(lowerUserPraiseNum,SortType.DESC);
		return Praise;
	}
	
	/*
	 * 根据spiritType 类型，计数出下属的评价数
	 */
	private Map<String,Integer> calculateUserPraiseNumByType(Map<String, Integer> userPraiseNum,
			Set<String> lowers, String spiritType) {
		Map<String,Integer> praiseNum = new HashMap<String, Integer>();
		
		for(String userId : lowers){
			int num = 0;
			if(RankingListResource.PRAISE_TYPE_ALL.endsWith(spiritType)){
				num = spiritService.getMapIntValue(userPraiseNum,getUserKey(userId,PraiseType.PRAISE.getType()))
						+ spiritService.getMapIntValue(userPraiseNum,getUserKey(userId,PraiseType.ENCOURAGE.getType()));
			}else{
				num = spiritService.getMapIntValue(userPraiseNum,getUserKey(userId,spiritType));
			}
			praiseNum.put(userId, num);
			int total = spiritService.getMapIntValue(praiseNum,TOTAL);
			praiseNum.put(TOTAL, total+num);
		}
		
		return praiseNum;
	}
	
	/*
	 * 将每一条点赞信息的数量保存到Map中。 
	 */
	private Map<String, Integer> calculateUserPraiseNum(List<PraiseDetail> praiseDetails) {
		Map<String,Integer> praiseNum = new HashMap<String, Integer>();
		
		for(PraiseDetail p : praiseDetails){
			calculateUserPraise(p,praiseNum);
		}
		
		return praiseNum;
	}
	
	/*
	 * 计算赞信息的数量。
	 */
	private void calculateUserPraise(PraiseDetail p,Map<String,Integer> praiseNum) {
		String key = getUserKey(p.getTargetUserId(), p.getpType());
		int num = spiritService.calculatePraiseByKey(p, praiseNum, key);
		
		praiseNum.put(key, num);
	}

	
	/*
	 * 得到用户的所有下属id
	 */
	private Set<String> getLowerId(String userId) throws Exception {
		Set<String> lowersSet = new HashSet<String>();
		JSONObject lowerJson = orgStructureService.queryOrgStructure(userId, OrgStructureService.MARK_LOWER_LEVEL);
		JSONArray lowersArray = lowerJson.getJSONArray(OrgStructureService.MARK_LOWER_LEVEL);
		
		for(Object obj:lowersArray){
			JSONObject json = (JSONObject)obj;
			if(json.containsKey("user_id") && StringUtils.isNotBlank(json.getString("user_id"))){
				lowersSet.add(json.getString("user_id"));
			}
		}
		return lowersSet;
	}
	
	/*
	 * 根据用户Id 和 类型，拼出保存的key
	 */
	private String getUserKey(String targetUserId, String getpType) {
		return targetUserId+ "_" + getpType;
	}
	
	private List<SpiritSortBo> sortuserPraiseNumSort(
			Map<String, Integer> lowerUserPraiseNum,final SortType sortType) {
		List<SpiritSortBo> sbos = new ArrayList<SpiritSortBo>();
		
		for( Entry<String, Integer> entry: lowerUserPraiseNum.entrySet()){
			if( !TOTAL.equals(entry.getKey()) ){
				SpiritSortBo sbo = new SpiritSortBo(); 
				sbo.setUserId(entry.getKey());
				BigDecimal fz  =new BigDecimal(entry.getValue());		
				BigDecimal fm  = new BigDecimal(lowerUserPraiseNum.get(TOTAL)) ;
				if(fm.intValue()==0){
					sbo.setPercent("0");
				}else{
					DecimalFormat df=new DecimalFormat("0.00");
					String pe = df.format(fz.multiply(new BigDecimal(100)).divide(fm, 2, RoundingMode.HALF_UP));
					sbo.setPercent(pe);
				}
				sbos.add(sbo);
			}
		}
		// 排序
		// Collections.sort(sbos, new SpiritSortBoComparator(sortType));
		Collections.sort(sbos, new Comparator<SpiritSortBo>() {

			public int compare(SpiritSortBo o1, SpiritSortBo o2) {
				BigDecimal o1Percent = new BigDecimal(o1.getPercent());
				BigDecimal o2Percent = new BigDecimal(o2.getPercent());
				
				int multiplier = -1;
				if(sortType.equals(SortType.ASC)){
					multiplier = 1;
				}
				return o1Percent.compareTo(o2Percent)*multiplier;
			}
			
		});
		return sbos;
	}

	

}
