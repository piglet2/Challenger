/******************************************************************************
 * @File name   :      UserCaseDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-4 下午5:36:35
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
 * 2016-1-4 下午5:36:35    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.UserCase;

/**
 * @ClassName UserCaseDao
 * @author guowei.wang
 * @date 2016-1-4
 */
public interface UserCaseDao {

	public UserCase queryById(@Param("id") int id);

	public List<UserCase> queryByUser(Map map);

	public UserCase queryDefaultCaseById(@Param("userId") String id);

	public int add(UserCase userCase);

	public void reset(@Param("ids") int... ids);

	public void incrementNewPrise(int id);
	
	public List<UserCase> queryPlazaTop();

	public List<UserCase> queryPlazaIndex(Map map);
	
	public List<UserCase> queryPlazaIndexByDivision(Map map);

	public List<UserCase> queryPlaza(Map map);
	
	public List<UserCase> queryPlazaByDivision(Map map);
	
}
