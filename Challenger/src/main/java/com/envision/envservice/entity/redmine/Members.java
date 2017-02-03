/******************************************************************************
 * @File name   :      users.java
 *
 * @Package 	:	   com.envision.envservice.entity.sap
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月2日 下午4:30:22
 *
 * @Description : 	   用户信息
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
 * 2015年11月2日 下午4:30:22    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.redmine;

import java.util.Date;

/**
 * @ClassName Members
 * @author caisong
 * @date 2016年05月18日
 */
public class Members {
	
	private int id;
	
	private int userId;
	
	private int projectId;
	
	private Date createdOn;
	
	private int mailNotification;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getMailNotification() {
		return mailNotification;
	}

	public void setMailNotification(int mailNotification) {
		this.mailNotification = mailNotification;
	}


}
