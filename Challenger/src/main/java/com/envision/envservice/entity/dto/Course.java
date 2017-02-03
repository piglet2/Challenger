/******************************************************************************
 * @File name   :      Course.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-20 下午3:54:53
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
 * 2016-1-20 下午3:54:53    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

import java.util.Date;

/**
 * @ClassName Course
 * @author guowei.wang
 * @date 2016-1-20
 */
public class Course {
	
	private int id;
	
	private String name;
	
	private String userId;
	
	private Date startTime;
	
	private Date endTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	} 
}
