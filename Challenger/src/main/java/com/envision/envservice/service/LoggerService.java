/******************************************************************************
 * @File name   :      LoggerService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-16 下午2:24:55
 *
 * @Description : 	   
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
 * 2015-11-16 下午2:24:55    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.enums.Operation;
import com.envision.envservice.dao.DBLoggerDao;
import com.envision.envservice.entity.dto.DBLogger;

/**
 * @ClassName LoggerService
 * @author guowei.wang
 * @date 2015-11-16
 */
@Service
public class LoggerService {
	
	@Autowired
	private DBLoggerDao dbLoggerDao;
	
	/**
	 * 日志新增
	 */
	public DBLogger addLog(String host, String userId, String userIdTarget, Operation operation) {
		return addLog(host, userId, userIdTarget, operation, false, null);
	}

	/**
	 * 日志新增
	 */
	public DBLogger addLog(String host, String userId, String userIdTarget, Operation operation, boolean isSuccess, String remark) {
		DBLogger logger = buildLogger(host, userId, userIdTarget, operation, isSuccess, remark);
		
		dbLoggerDao.addLogger(logger);
		
		return logger;
	}
	
	/**
	 * 设置操作状态
	 */
	public void setSuccess(int id, boolean isSuccess) {
		setSuccess(id, isSuccess, null);
	}
	
	/**
	 * 设置操作状态 & 数据ID
	 */
	public void setSuccess(int id, boolean isSuccess, String tableId) {
		DBLogger logger = new DBLogger();
		logger.setId(id);
		logger.setSucceed(isSuccess);
		logger.setTableId(tableId);
		
		dbLoggerDao.updateLogger(logger);
	}	

	/**
	 * 设置已读
	 */
	public void setRead(int id, boolean hasRead) {
		setRead(hasRead, id);
	}

	/**
	 * 设置已读
	 */
	public void setRead(boolean hasRead, int... ids) {
		Objects.requireNonNull(ids);
		
		if (ids.length != 0) {
			dbLoggerDao.setRead(hasRead, ids);
		}
	}
	
	/** 
	 * 设置已读
	 */
	public void setRead(List<DBLogger> lstLogger, boolean hasRead) {
		Objects.requireNonNull(lstLogger);
		
		if (!lstLogger.isEmpty()) {
			int[] ids = new int[lstLogger.size()];
			for (int i = 0; i < lstLogger.size(); i++) {
				ids[i] = lstLogger.get(i).getId();
			}
			
			setRead(hasRead, ids);
		}
	}
	
	/**
	 * 查询未读
	 */
	public List<DBLogger> queryUnread(String userId, Set<String> events) {
		Objects.requireNonNull(events);
		
		return dbLoggerDao.queryUnread(userId, events);
	}
	
	/**
	 * 分页查询
	 */
	public List<DBLogger> queryPage(String userId, Set<String> events, int pageIndex, int pageSize) {
		Objects.requireNonNull(events);
		if (pageIndex < 1) {
			throw new IllegalArgumentException("PageIndex error: " + pageIndex);
		}
		if (pageSize < 1) {
			throw new IllegalArgumentException("PageSize error: " + pageSize);
		}
		
		int offset = pageIndex == 1 ? 0 : (pageIndex - 1) * pageSize;

		return dbLoggerDao.queryPage(userId, events, offset, pageSize);
	}
	
	private DBLogger buildLogger(String host, String userId, String userIdTarget, Operation operation, boolean isSuccess, String remark) {
		DBLogger logger = new DBLogger();
		logger.setHost(host);
		logger.setUserId(userId);
		logger.setEvents(operation.value());
		logger.setUserIdTarget(userIdTarget);
		logger.setSucceed(isSuccess);
		logger.setRemark(remark);
		logger.setHasRead(false);
		logger.setCts(new Date());
		
		return logger;
	}
}
