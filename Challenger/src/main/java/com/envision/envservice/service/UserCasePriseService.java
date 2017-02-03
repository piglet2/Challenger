/******************************************************************************
 * @File name   :      UserCasePriseService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-7 下午2:23:57
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
 * 2016-1-7 下午2:23:57    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.enums.Operation;
import com.envision.envservice.common.enums.PriseItem;
import com.envision.envservice.common.util.IPUtil;
import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.dao.UserCasePriseDao;
import com.envision.envservice.entity.bo.UserBaseBo;
import com.envision.envservice.entity.bo.UserCasePriseBo;
import com.envision.envservice.entity.dto.UserCasePrise;
import com.envision.envservice.entity.dto.UserCasePriseRecord;
import com.envision.envservice.service.exception.ServiceException;

/**
 * @ClassName UserCasePriseService
 * @author guowei.wang
 * @date 2016-1-7
 */
@Service
public class UserCasePriseService {
	
	public static final String FIELD_PRISE_WILL = "prise_will";
	public static final String FIELD_PRISE_WISDOM = "prise_wisdom";
	public static final String FIELD_PRISE_LOVE = "prise_love";
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired  
	private HttpServletRequest request;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoggerService loggerService;
	
	@Autowired
	private UserCaseService userCaseService;
	
	@Autowired
	private UserCasePriseDao userCasePriseDao;
	
	@Autowired
	private UserCasePriseRecordService userCasePriseRecordService;
	
	@Autowired
	private UserCaseReadRecordService userCaseReadRecordService;
 	
	public JSONObject queryPriseCollect(String userId) {
		List<UserCasePrise> userCasePrises = userCasePriseDao.queryByUserId(userId);
		
		return collectWWL(userCasePrises);
	}

	public synchronized void prise(UserCasePriseBo userCasePrise) throws Exception {
		Integer caseId = userCasePrise.getCaseId();
		
		checkCaseExsit(caseId);
		checkPermission(caseId);

		priseCase(userCasePrise);
		
		userCasePriseRecordService.recordNewPrise(SessionUtil.getUserId(httpSession), userCasePrise);
		
		userCaseReadRecordService.updateHasReadByCaseId(false, caseId);
		
		loggerService.addLog(IPUtil.getRemoteAddr(request), SessionUtil.getUserId(httpSession), String.valueOf(caseId), Operation.PRAISE, true, null);
	}

	public synchronized void cancelPrise(int caseId) throws ServiceException {
		if (userCasePriseDao.queryByCaseIdAndUser(caseId, SessionUtil.getUserId(httpSession)) != null) {
			cancelPriseCount(caseId);
			
			userCasePriseRecordService.recordCancelPrise(SessionUtil.getUserId(httpSession), caseId);
				
			loggerService.addLog(IPUtil.getRemoteAddr(request), SessionUtil.getUserId(httpSession), String.valueOf(caseId), Operation.PRAISE_CANCLE, true, null);
		} else {
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, "不存在相关点赞记录");
		}
	}
	
	public UserCasePrise queryByCaseIdAndUser(int caseId, String userId) {
		return userCasePriseDao.queryByCaseIdAndUser(caseId, userId);
	}
	
	public Integer calcUserCasePriseCount(UserCasePrise userCasePrise) {
		return userCasePrise.getPriseWill() + userCasePrise.getPriseWisdom() + userCasePrise.getPriseLove();
	}
	
	public Integer calcUserCasePriseCount(int caseId) {
		int count = 0;
		List<UserCasePrise> userCasePrises = userCasePriseDao.queryByCaseId(caseId);
		for (UserCasePrise userCasePrise : userCasePrises) {
			count = count + calcUserCasePriseCount(userCasePrise);
		}
		
		return count;
	}
	
	private void cancelPriseCount(int caseId) {
		List<UserCasePriseRecord> validPriseToday = userCasePriseRecordService.calcValidPriseToday(SessionUtil.getUserId(httpSession), caseId);
		if (validPriseToday.isEmpty()) {
			return;
		}
		
		int priseWill = 0;
		int priseWisdom = 0;
		int priseLove = 0;
		for (UserCasePriseRecord priseRecord : validPriseToday) {
			PriseItem item = PriseItem.valueOf(priseRecord.getItem().toUpperCase());
			switch (item) {
			case WILL:
				priseWill++;
				break;
			case WISDOM:
				priseWisdom++;
				break;
			case LOVE:
				priseLove++;
				break;
			}
		}
		
		UserCasePrise userCasePriseRecord = userCasePriseDao.queryByCaseIdAndUser(caseId, SessionUtil.getUserId(httpSession));
		userCasePriseRecord.setPriseWill(userCasePriseRecord.getPriseWill() - priseWill);
		userCasePriseRecord.setPriseWisdom(userCasePriseRecord.getPriseWisdom() - priseWisdom);
		userCasePriseRecord.setPriseLove(userCasePriseRecord.getPriseLove() - priseLove);
		userCasePriseRecord.setTime(new Date());
		
		userCasePriseDao.updateCasePrise(userCasePriseRecord);
	}
	
	private void priseCase(UserCasePriseBo userCasePrise) throws Exception {
		Integer caseId = userCasePrise.getCaseId();
		
		UserCasePrise userCasePriseRecord = userCasePriseDao.queryByCaseIdAndUser(caseId, SessionUtil.getUserId(httpSession));
		if (userCasePriseRecord == null) {
			createPrise(userCasePrise);
		} else {
			int newPriseWill = userCasePrise.isPriseWill() ? 1 : 0;
			int newPriseWisdom = userCasePrise.isPriseWisdom() ? 1 : 0;
			int newPriseLove = userCasePrise.isPriseLove() ? 1 : 0;
			
			userCasePriseRecord.setPriseWill(userCasePriseRecord.getPriseWill() + newPriseWill);
			userCasePriseRecord.setPriseWisdom(userCasePriseRecord.getPriseWisdom() + newPriseWisdom);
			userCasePriseRecord.setPriseLove(userCasePriseRecord.getPriseLove() + newPriseLove);
			userCasePriseRecord.setTime(new Date());
			
			userCasePriseDao.updateCasePrise(userCasePriseRecord);
		}
		
		userCaseService.incrementNewPrise(caseId);
	}
	
	private boolean checkAlreadyPriseToday(Integer caseId) {
		return userCasePriseRecordService.checkExistValidPriseToday(SessionUtil.getUserId(httpSession), caseId);
	}
	
	private void checkPermission(Integer caseId) throws Exception {
		if (checkAlreadyPriseToday(caseId)) {
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, "今日已点赞");
		}
	}
	
	private void checkCaseExsit(Integer caseId) throws ServiceException {
		userCaseService.checkCaseExsit(caseId, true);
	}
	
	public boolean checkCanPrise(Integer caseId) throws Exception {
		return !checkAlreadyPriseToday(caseId);
	}
	
	private JSONObject collectWWL(List<UserCasePrise> userCasePrises) {
		JSONObject collect = new JSONObject();
		
		int priseWill = 0;
		int priseWisdom = 0;
		int priseLove = 0;
		for (UserCasePrise userCasePrise : userCasePrises) {
			priseWill += userCasePrise.getPriseWill();
			priseWisdom += userCasePrise.getPriseWisdom();
			priseLove += userCasePrise.getPriseLove();
		}
		
		collect.put(FIELD_PRISE_WILL, priseWill);
		collect.put(FIELD_PRISE_WISDOM, priseWisdom);
		collect.put(FIELD_PRISE_LOVE, priseLove);
		
		return collect;
	}

	private void createPrise(UserCasePriseBo bo) {
		UserCasePrise userCasePrise = new UserCasePrise();
		userCasePrise.setCaseId(bo.getCaseId());
		userCasePrise.setPriseLove(bo.isPriseLove() ? 1 : 0);
		userCasePrise.setPriseWill(bo.isPriseWill() ? 1 : 0);
		userCasePrise.setPriseWisdom(bo.isPriseWisdom() ? 1 : 0);
		userCasePrise.setUserId(SessionUtil.getUserId(httpSession));
		userCasePrise.setTime(new Date());
		
		createPrise(userCasePrise);
	}

	public List<UserBaseBo> queryPriseUserByCaseId(Integer caseId) throws Exception {
		List<UserCasePrise> casePrises = userCasePriseDao.queryByCaseId(caseId);
		
		Set<String> ids = new HashSet<String>();
		for (UserCasePrise userCasePrise : casePrises) {
			int priseCount = userCasePrise.getPriseWill() + userCasePrise.getPriseWisdom() + userCasePrise.getPriseLove();
			if (priseCount > 0) {
				ids.add(userCasePrise.getUserId());
			}
		}
		
		return userService.queryUserBaseByIds(ids.toArray(new String[ids.size()]));
	}
	
	private void createPrise(UserCasePrise userCasePrise) {
		userCasePriseDao.createPrise(userCasePrise);
	}
}
