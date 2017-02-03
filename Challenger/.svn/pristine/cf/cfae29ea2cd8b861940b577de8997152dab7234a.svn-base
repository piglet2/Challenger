/******************************************************************************
 * @File name   :      DBLoggerDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月22日 上午11:21:41
 *
 * @Description : 	   数据库中记录的日志
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
 * 2015年10月22日 上午11:21:41    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.DBLogger;

public interface DBLoggerDao {
	/**
	 * 新增Logger
	 */
	public void addLogger(DBLogger dbLogger);
	
	/**
	 * 修改Logger
	 */
	public void updateLogger(DBLogger dbLogger);
	
	/**
	 * 查询未读Logger
	 */
	public List<DBLogger> queryUnread(@Param("userId") String userId, @Param("events") Set<String> events);
	
	/**
	 * 分页查询Logger
	 */
	public List<DBLogger> queryPage(@Param("userId") String userId, @Param("events") Set<String> events,
									@Param("offset") int offset, @Param("rows") int rows);

	/** 
	 * 批量设置已读
	 */
	public void setRead(@Param("hasRead") boolean hasRead, @Param("ids") int[] ids);
}
