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

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.sap.SAPUser;

/**
 * SAP.User Dao
 * 
 * @ClassName SAPUserDao
 * @author guowei.wang
 * @date 2015-11-3
 */
public interface SAPUserDao {
	
	/**
	 * queryAll
	 */
	public List<SAPUser> test();

	/**
	 * queryAll
	 */
	public List<SAPUser> queryAll();
	
	/**
	 * queryByRedmine
	 */
	public List<SAPUser> queryByRedmine();
	
	/**
	 * queryAllByStatus
	 */
	public List<SAPUser> queryAllByStatus();
	
	/**
	 * queryAll
	 */
	public List<SAPUser> queryAllUsers();
	
	/**
	 * queryByUserId
	 * *@Title: queryByUserId 
	 * @param id
	 * @return SAPUser
	 * @Date 2016-5-10
	 */
	public SAPUser queryByUserId(@Param("userId") String userId);

	/**
	 * queryByIds
	 * 
	 * @Title: queryByIds 
	 * @param ids
	 * @return list
	 * @Date 2015-11-4
	 */
	public List<SAPUser> queryByIds(String... ids);

	/**
	 * queryByIds(All)
	 * 
	 * @Title: queryAllByIds 
	 * @param ids
	 * @return list
	 * @Date 2015-11-4
	 */
	public List<SAPUser> queryAllByIds(String... ids);

	/**
	 * queryByUsernames
	 * 
	 * @Title: queryByUsernames 
	 * @param usernames
	 * @return list
	 * @Date 2015-11-4
	 */
	public List<SAPUser> queryByUsernames(String... usernames);
	
	/**
	 * queryByUsername
	 * 
	 * @Title: queryByUsername 
	 * @param username
	 * @return SAPUser
	 * @Date 2016-05-20
	 */
	public SAPUser queryByUsername(String username);

	/**
	 * queryAllByUsernames
	 * 
	 * @Title: queryByUsernames 
	 * @param usernames
	 * @return list
	 * @Date 2015-11-4
	 */
	public List<SAPUser> queryAllByUsernames(String... usernames);

	/**
	 * Replace
	 * 
	 * @Title: replace 
	 * @param sapUser
	 * @Date 2015-11-3
	 */
	public void replace(List<SAPUser> lstSAPUser);

	/**
	 * queryByUsernames Or LastNames
	 * 
	 * @Title: queryByUsernamesOrLastNames 
	 * @param usernames
	 * @return list
	 * @Date 2015-11-4
	 */
	public List<SAPUser> queryByUsernamesOrLastNames(String username);
	
	/**
	 * 查看是否是正式员工
	 * */
	public List<SAPUser> queryByCustom06();
	
	
}
