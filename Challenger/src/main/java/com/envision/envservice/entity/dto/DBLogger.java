/******************************************************************************
 * @File name   :      DBLogger.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月22日 上午10:59:25
 *
 * @Description : 	   数据库中记录的日志
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
 * 2015年10月22日 上午10:59:25    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

import java.util.Date;

/**
 * 数据库中记录的日志
 * @ClassName DBLogger
 * @Description 数据库中记录的日志
 * @author xuyang.li
 * @date 2015年10月22日
 */
public class DBLogger {

	private int id;
	
	/** 主机 */
	private String host;
	
	/** 用户 id*/
	private String userId;
	
	/** 事件 */
	private String events;
	
	/** 目标用户Id */
	private String userIdTarget;
	
	/** 成功与否 */
	private boolean succeed;

	/** 备注 */
	private String remark;
	
	/** 创建时间 */
	private Date cts;
	
	/** 标识 */
	private String earmark;
	
	/** 操作的表名 */
	private String tableName;
	
	/** 操作数据在表中的Id */
	private String tableId;
	
	/** 是否已读 */
	private boolean hasRead;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getUserIdTarget() {
		return userIdTarget;
	}

	public void setUserIdTarget(String userIdTarget) {
		this.userIdTarget = userIdTarget;
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

	public String getEarmark() {
		return earmark;
	}

	public void setEarmark(String earmark) {
		this.earmark = earmark;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public boolean isHasRead() {
		return hasRead;
	}

	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
}
