/******************************************************************************
 * @File name   :      UserCasePriseDetailService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2016-03-25 上午10:24:57
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
 * 2016-03-25上午10:24:57    			xuyang.li   1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.util.StringUtil;
import com.envision.envservice.dao.UserCaseReadRecordDao;
import com.envision.envservice.entity.dto.UserCase;
import com.envision.envservice.entity.dto.UserCaseReadRecord;

/**
 * @ClassName UserCaseReadRecordService
 * @author xuyang.li
 * @date 2016-03-25
 */
@Service
public class UserCaseReadRecordService {
	
	@Autowired
	private UserCaseReadRecordDao userCaseReadRecordDao;
	
	/**
	 * 添加 阅读记录数据
	 * @param userCase 
	 * @Title: addReadRecord 
	 * @return void     
	 * @Date 2016年3月25日
	 */
	public void addReadRecordByCase(UserCase userCase){
		List<UserCaseReadRecord> readRecords = bulidReadRecord(userCase);
		
		userCaseReadRecordDao.createReadRecords(readRecords);
	}

	/*
	 * 根据UserCase 创建ReadRecord
	 */
	private List<UserCaseReadRecord> bulidReadRecord(UserCase userCase) {
		List<UserCaseReadRecord> readReadRecords = new ArrayList<UserCaseReadRecord>();
	
		for(String members : StringUtil.parseSplice(userCase.getMembers())){
			UserCaseReadRecord  readRecord = new UserCaseReadRecord();
			readRecord.setCaseId(userCase.getId());
			readRecord.setUserId(members);
			readRecord.setHasRead(false);
			
			readReadRecords.add(readRecord);
		}
		
		return readReadRecords;
	}
	
	/**
	 * 更新事件是否已读的标志
	 * @Title: updateHasReadByCaseId 
	 * @param hasRead 是否已读
	 * @param caseId   需要更新的事件Id
	 * @Date 2016年3月25日
	 */
	public void updateHasReadByCaseId(boolean hasRead, int caseId){
		
		UserCaseReadRecord readRecord = new UserCaseReadRecord();
		readRecord.setHasRead(hasRead);
		readRecord.setCaseId(caseId);
		
		userCaseReadRecordDao.updateHasReadByCaseId(readRecord);
	}

	public List<UserCaseReadRecord> queryUnreadRecord(String userId) {
		return userCaseReadRecordDao.queryUnreadRecord(userId);
	}

	public void resetUnreadRecord(String userId) {
		userCaseReadRecordDao.resetUnreadRecord(userId);
	}
}
