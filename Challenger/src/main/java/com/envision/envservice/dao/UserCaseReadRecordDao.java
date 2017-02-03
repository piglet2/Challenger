/******************************************************************************
 * @File name   :      UserCaseReadRecordDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2016-03-25 上午10:18:03
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
 * 2016-03-25 上午10:18:03    			xuyang.li    1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.UserCaseReadRecord;

/**
 * @ClassName UserCaseReadRecordDao
 * @author xuyang.li
 * @date 2016-03-25
 */
public interface UserCaseReadRecordDao {

	public void createReadRecords(List<UserCaseReadRecord> readRecord);
	
	public void updateHasReadByCaseId(UserCaseReadRecord readRecord);

	public List<UserCaseReadRecord> queryUnreadRecord(@Param("userId") String userId);

	public void resetUnreadRecord(@Param("userId") String userId);

}
