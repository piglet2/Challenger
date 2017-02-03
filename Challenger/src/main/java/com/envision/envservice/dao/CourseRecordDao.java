/******************************************************************************
 * @File name   :      CourseRecordDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-21 下午12:04:20
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
 * 2016-1-21 下午12:04:20    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.CourseRecord;

/**
 * @ClassName CourseRecordDao
 * @author guowei.wang
 * @date 2016-1-21
 */
public interface CourseRecordDao {
	
	public CourseRecord queryByCourseIdAndUserId(@Param("userId") String userId, @Param("courseId") int courseId);

	public void create(CourseRecord courseRecord);
	
	public void update(CourseRecord courseRecord);
}
