/******************************************************************************
 * @File name   :      PraiseTotalDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      total
 *
 * @Date        :      2015年10月21日 上午11:21:29
 *
 * @Description : 	   用户剩余点赞数
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
 * 2015年10月21日 上午11:21:29    			total     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import com.envision.envservice.entity.dto.PraiseTotal;

/**
 * 用户剩余点赞数
 * @ClassName PraiseTotalDao
 * @author xuyang.li
 * @date 2015年10月21日
 */

public interface PraiseTotalDao {

	public PraiseTotal queryTotal(PraiseTotal praiseTotal);

	/**
	 * 添加一条用户剩余点赞数
	* @Title: addTotal 
	* @param param    
	* @return void     
	* @throws 
	* @Date 2015年10月21日
	 */
	public void addTotal(PraiseTotal param);

	/**
	 * 更新用户剩余点赞数点赞数加一
	* @Title: updateTotalIncrease 
	* @param param    
	* @Date 2015年10月21日
	 */
	public void updateTotalIncrease(PraiseTotal param);
	
	/**
	 * 更新用户剩余点赞数点赞数减一
	* @Title: updateTotalDecrease 
	* @param param    
	* @Date 2015年10月21日
	 */
	public void updateTotalDecrease(PraiseTotal param);
	
	
}
