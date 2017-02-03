/******************************************************************************
 * @File name   :      CourseDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-20 下午4:09:18
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
 * 2016-1-20 下午4:09:18    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.envision.envservice.entity.dto.Course;

/**
 * @ClassName CourseDao
 * @author guowei.wang
 * @date 2016-1-20
 */
public interface CourseDao {
	
	public Course queryById(@Param("id") int id);

	public List<Course> queryAll();
}
