/******************************************************************************
 * @File name   :      UserGroupDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-16 下午5:21:58
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
 * 2015-12-16 下午5:21:58    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.UserGroup;

/**
 * @ClassName UserGroupDao
 * @author guowei.wang
 * @date 2015-12-16
 */
public interface UserGroupDao {

	public List<UserGroup> queryByCode(@Param("code") String code, @Param("codeTwo") String codeTwo);
	
	public List<UserGroup> queryByCodeAndDep(@Param("code") String code, @Param("codeTwo") String codeTwo, @Param("depCode") String depCode);
	
	public List<UserGroup> queryByUserId(@Param("userId") String userId);

}
