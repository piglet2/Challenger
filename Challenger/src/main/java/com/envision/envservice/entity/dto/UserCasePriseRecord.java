/******************************************************************************
 * @File name   :      UserCasePriseRecord.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-14 上午10:13:38
 *
 * @Description : 	   
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
 * 2016-1-14 上午10:13:38    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

import java.util.Date;

/**
 * @ClassName UserCasePriseRecord
 * @author guowei.wang
 * @date 2016-1-14
 */
public class UserCasePriseRecord {
	
	private int id;
	
	private Integer caseId;

	private String userId;
	
	private String operation;
	
	private String item;
	
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
