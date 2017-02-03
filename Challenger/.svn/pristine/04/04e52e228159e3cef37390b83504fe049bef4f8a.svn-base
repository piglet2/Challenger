/******************************************************************************
 * @File name   :      SpiritDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午6:55:37
 *
 * @Description : 	   点赞详情信息
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
 * 2015年10月19日 下午6:55:37    			 xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import com.envision.envservice.entity.bo.PraiseDetailBo;
import com.envision.envservice.entity.dto.PraiseDetail;


/**
 * 点赞详情信息
 * @ClassName SpiritDao
 * @author  xuyang.li
 * @date 2015年10月19日
 */

public interface SpiritDao {

	/**
	 * 根据userId查询改用户的所有点赞信息
	 * @Title: queryByuserId 
	 * @param userId
	 * @return List<PraiseDetail>   点赞信息 
	 * @Date 2015年10月19日
	 */
	public List<PraiseDetail> queryByUserId(String userId);

	/**
	 * @Title: addPraiseDetail 
	 * @Description: 添加点赞信息
	 * @param pd    点赞信息
	 * @Date 2015年10月21日
	 */
	public void addPraiseDetail(PraiseDetail pd);

	/**
	 * 查询当天给用户的赞或鼓励
	 * PraiseDetailBo.type==null 是查询出鼓励和点赞
	 * 不为空是查询出对应的信息
	 * @Title: queryTodayPraiseDetail 
	 * @param param
	 * @return    
	 * @return List<PraiseDetail>     
	 * @throws 
	 * @Date 2015年10月21日
	 */
	public List<PraiseDetail> queryTodayPraiseDetail(PraiseDetailBo param);

	/**
	 * 根据不同的条件查询评价信息
	 * @Title: queryTodayPraiseDetail 
	 * @param param
	 * @return    
	 * @return List<PraiseDetail>     
	 * @throws 
	 * @Date 2015年10月21日
	 */
	public List<PraiseDetail> queryPraiseDetailByPdo(PraiseDetailBo param);

	/**
	 * 批量查询点赞记录
	 * 
	 * @Title: queryByIds 
	 * @param ids
	 * @return
	 * @Date 2015-11-20
	 */
	public List<PraiseDetail> queryByIds(String... ids);
	
	
	public List<PraiseDetail> queryMatch(PraiseDetail match);
}
