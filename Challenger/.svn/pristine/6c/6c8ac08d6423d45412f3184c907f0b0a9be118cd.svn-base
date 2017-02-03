/******************************************************************************
 * @File name   :      UserCasePriseRankingService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-27 上午11:44:02
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
 * 2016-1-27 上午11:44:02    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.util.MapUtil;
import com.envision.envservice.entity.bo.PriseRankingBo;
import com.envision.envservice.entity.dto.UserCase;
import com.envision.envservice.entity.dto.UserCasePrise;

/**
 * @ClassName UserCasePriseRankingService
 * @author guowei.wang
 * @date 2016-1-27
 */
@Service
public class UserCasePriseRankingService {
	
	@Autowired
	private UserCaseService userCaseService;
	
	@Autowired
	private UserCasePriseService userCasePriseService;

	@Autowired
	private OrgStructureService orgStructureService;

	public List<PriseRankingBo> querySubordinatePriseRanking(String managerId) throws Exception {
		Map<String, Integer> userCaseGroupBySubordinate = new HashMap<String, Integer>();
		for (String lowerLevel : orgStructureService.queryLowerLevel(managerId)) {
			Integer priseCount = 0;
			for (UserCase userCase : userCaseService.queryUserCaseByUser(lowerLevel)) {
				UserCasePrise userCasePrise = userCasePriseService.queryByCaseIdAndUser(userCase.getId(), managerId);
				if (userCasePrise != null) {
					priseCount += userCasePriseService.calcUserCasePriseCount(userCasePrise);
				}
			}
			userCaseGroupBySubordinate.put(lowerLevel, priseCount);
		}
		
		return assemblyRanking(calcPriseRanking(userCaseGroupBySubordinate));
	}
	
	private List<PriseRankingBo> assemblyRanking(Map<String, BigDecimal> priseRanking) {
		List<PriseRankingBo> ranking = new ArrayList<PriseRankingBo>(priseRanking.size());
		for (Map.Entry<String, BigDecimal> me : priseRanking.entrySet()) {
			PriseRankingBo rankingBo = new PriseRankingBo();
			rankingBo.setUserId(me.getKey());
			rankingBo.setPercent(me.getValue().toString());
			
			ranking.add(rankingBo);
		}
		
		return ranking;
	}

	private Map<String, BigDecimal> calcPriseRanking(Map<String, Integer> userCaseGroupBySubordinate) {
		Map<String, BigDecimal> priseRanking = new HashMap<String, BigDecimal>();
		int priseCount = countPrise(userCaseGroupBySubordinate.values());
		if (priseCount == 0) {
			return buildEmptyRanking(userCaseGroupBySubordinate);
		}
		
		for (Map.Entry<String, Integer> me : userCaseGroupBySubordinate.entrySet()) {
			priseRanking.put(me.getKey(), new BigDecimal(me.getValue()).divide(new BigDecimal(priseCount), 4, RoundingMode.HALF_UP)
																	   .multiply(new BigDecimal(100))
																	   .setScale(2, RoundingMode.HALF_UP));
		}
		
		return MapUtil.sortByValue(priseRanking, false);
	}
	
	private Map<String, BigDecimal> buildEmptyRanking(Map<String, Integer> userCaseGroupBySubordinate) {
		Map<String, BigDecimal> priseRanking = new HashMap<String, BigDecimal>();
		for (Map.Entry<String, Integer> me : userCaseGroupBySubordinate.entrySet()) {
			priseRanking.put(me.getKey(), BigDecimal.ZERO);
		}
		
		return priseRanking;
	}

	private int countPrise(Collection<Integer> priseNums) {
		int count = 0;
		for (Integer num : priseNums) {
			count += num;
		}
		
		return count;
	}
}
