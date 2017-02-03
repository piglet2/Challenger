/******************************************************************************
 * @File name   :      ChallengerItemDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月20日 上午10:41:09
 *
 * @Description : 	   远景精神条目Dao
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
 * 2015年10月20日 上午10:41:09    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import com.envision.envservice.entity.dto.ChallengerItem;

/**
 *  远景精神条目Dao
 * @ClassName ChallengerItemDao
 * @author xuyang.li
 * @date 2015年10月20日
 */

public interface ChallengerItemDao {

	/**
	 * 查询说有的Item
	 * @Title: queryItems 
	 * @return List<ChallengerItem>     
	 * @throws 
	 * @Date 2015年10月22日
	 */
	public List<ChallengerItem> queryItems();
	/**
	 * 
	 * @param itemId 
	 * @Title: queryItemById 
	 * @return ChallengerItem     
	 * @throws 
	 * @Date 2015年10月22日
	 */
	public ChallengerItem queryClickItemById(String itemId);
	
	

}
