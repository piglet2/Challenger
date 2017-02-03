/******************************************************************************
 * @File name   :      Evaluation.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      caisong
 *
 * @Date        :      2016-5-11 
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
 * Date                   						Who         Version        Comments
 * 2016-4-27 下午5:12:09    			caisong     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

import java.util.Date;



/**
 * @ClassName Award
 * @author caisong
 * @date 2016-11-14
 */
public class Award {
	
	/*
	 * 主键自增长
	*/
	private int id;
	
	/*
	 * 员工id
	*/
	private String userId;
	
	/*
	 * 事件概述
	*/
	private String comment;
	
	/*
	 * 奖惩类型
	*/
	private String type;
	
	/*
	 * 通报范围
	*/
	private String scope;
	
	/*
	 * 时间
	*/
	private String issuedate;
	
	private Date cts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}
	
	
	
}
