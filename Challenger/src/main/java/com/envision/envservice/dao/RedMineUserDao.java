/******************************************************************************
 * @File name   :      SAPUserDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-3 上午10:45:19
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
 * 2015-11-3 上午10:45:19    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import com.envision.envservice.entity.redmine.Users;

/**
 * SAP.User Dao
 * 
 * @ClassName SAPUserDao
 * @author caisong
 * @date 2016-05-16
 */
public interface RedMineUserDao {

	/**
	 * 同步users表
	 * 
	 */
	public int replace(Users user);
	
	public List<Users> queryAll();
	
}
