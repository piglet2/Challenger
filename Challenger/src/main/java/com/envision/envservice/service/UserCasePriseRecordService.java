/******************************************************************************
 * @File name   :      UserCasePriseDetailService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-14 上午10:24:57
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
 * 2016-1-14 上午10:24:57    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.enums.Operation;
import com.envision.envservice.common.enums.PriseItem;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.dao.UserCasePriseRecordDao;
import com.envision.envservice.entity.bo.UserCasePriseBo;
import com.envision.envservice.entity.dto.UserCasePriseRecord;

/**
 * @ClassName UserCasePriseRecordService
 * @author guowei.wang
 * @date 2016-1-14
 */
@Service
public class UserCasePriseRecordService {
	
	@Autowired
	private UserCasePriseRecordDao userCasePriseRecordDao;
	
	public void recordNewPrise(String userId, UserCasePriseBo userCasePrise) {
		Integer caseId = userCasePrise.getCaseId();
		
		recordIfTrue(userCasePrise.isPriseWill(), userId, caseId, PriseItem.WILL);
		recordIfTrue(userCasePrise.isPriseWisdom(), userId, caseId, PriseItem.WISDOM);
		recordIfTrue(userCasePrise.isPriseLove(), userId, caseId, PriseItem.LOVE);
	}
	
	/**
	 * 取消当天点赞记录
	 * 	1. 获取当天有效的点赞集
	 * 	2. 逐条取消
	 */
	public void recordCancelPrise(String userId, int caseId) {
		List<UserCasePriseRecord> validPriseToday = calcValidPriseToday(userId, caseId);
		for (UserCasePriseRecord priseRecord : validPriseToday) {
			recordPrise(userId, caseId, Operation.PRAISE_CANCLE, PriseItem.valueOf(priseRecord.getItem().toUpperCase()));
		}
	}
	
	public boolean checkExistValidPriseToday(String userId, int caseId) {
		return !calcValidPriseToday(userId, caseId).isEmpty();
	}
	
	/**
	 * 计算当天有效的点赞集
	 */
	public List<UserCasePriseRecord> calcValidPriseToday(String userId, int caseId) {
		List<UserCasePriseRecord> todayNewPrise = userCasePriseRecordDao.queryByInterval(caseId, userId, Operation.PRAISE.value(), DateUtil.todayStartTime(), DateUtil.todayEndTime());
		List<UserCasePriseRecord> todayCancelPrise = userCasePriseRecordDao.queryByInterval(caseId, userId, Operation.PRAISE_CANCLE.value(), DateUtil.todayStartTime(), DateUtil.todayEndTime());

		int validPriseNum = todayNewPrise.size() - todayCancelPrise.size();
		if (validPriseNum > 0) {
			return todayNewPrise.subList(0, validPriseNum);
		} else {
			return Collections.emptyList();
		}
	}
	
	private void recordIfTrue(boolean condition, String userId, int caseId, PriseItem item) {
		if (condition) {
			recordPrise(userId, caseId, Operation.PRAISE, item);
		}
	}
	
	private void recordPrise(String userId, int caseId, Operation operation, PriseItem item) {
		userCasePriseRecordDao.createPriseDetail(buildPriseRecord(userId, caseId, operation, item));
	}
	
	private UserCasePriseRecord buildPriseRecord(String userId, int caseId, Operation operation, PriseItem item) {
		UserCasePriseRecord priseRecord = new UserCasePriseRecord();
		priseRecord.setCaseId(caseId);
		priseRecord.setUserId(userId);
		priseRecord.setOperation(operation.value());
		priseRecord.setItem(item.value());
		priseRecord.setTime(new Date());
		
		return priseRecord;
	}
}
