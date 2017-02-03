/******************************************************************************
 * @File name   :      UserCasePriseDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-6 下午4:12:09
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
 * 2016-1-6 下午4:12:09    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.UserCasePrise;

/**
 * @ClassName UserCasePriseDao
 * @author guowei.wang
 * @date 2016-1-6
 */
public interface UserCasePriseDao {

	public List<UserCasePrise> queryByCaseId(@Param("caseId") int caseId);

	public List<UserCasePrise> queryByUserId(@Param("userId") String userId);

	public void createPrise(UserCasePrise userCasePrise);

	public void updateCasePrise(UserCasePrise userCasePrise);

	public UserCasePrise queryByCaseIdAndUser(@Param("caseId") int caseId, @Param("userId") String userId);
	
}