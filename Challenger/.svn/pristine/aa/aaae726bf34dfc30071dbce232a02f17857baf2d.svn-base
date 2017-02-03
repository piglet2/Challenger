/******************************************************************************
 * @File name   :      LeaveWordDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-22 上午11:39:54
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
 * 2015-10-22 上午11:39:54    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import com.envision.envservice.entity.dto.LeaveWord;

/**
 * @ClassName LeaveWordDao
 * @author guowei.wang
 * @date 2015-10-22
 */
public interface LeaveWordDao {
	
	/**
	 * 新增留言
	 * 
	 * @Title: addLeaveWord 
	 * @param leaveWord
	 * @Date 2015-10-22
	 */
	public void addLeaveWord(LeaveWord leaveWord);

	/**
	 * 按条件查询留言
	 * 
	 * @Title: queryLeaveWord 
	 * @param leaveWord
	 * @return
	 * @Date 2015-10-22
	 */
	public List<LeaveWord> queryLeaveWord(LeaveWord leaveWord);
	
	/**
	 * 批量查询留言
	 * 
	 * @Title: queryByIds 
	 * @param ids
	 * @return
	 * @Date 2015-11-20
	 */
	public List<LeaveWord> queryByIds(String... ids);
}
