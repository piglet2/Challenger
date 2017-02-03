/******************************************************************************
 * @File name   :      UserCasePriseRecordDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-14 上午10:18:03
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
 * 2016-1-14 上午10:18:03    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.UserCasePriseRecord;

/**
 * @ClassName UserCasePriseRecordDao
 * @author guowei.wang
 * @date 2016-1-14
 */
public interface UserCasePriseRecordDao {

	public void createPriseDetail(UserCasePriseRecord priseRecord);
	
	public List<UserCasePriseRecord> queryByInterval(@Param("caseId") int caseId, 
													 @Param("userId") String userId, 
													 @Param("operation") String operation, 
													 @Param("start") Date start, 
													 @Param("end") Date end);
}
