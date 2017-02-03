/******************************************************************************
 * @File name   :      CommentTipRecord.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2016年4月5日 下午4:05:31
 *
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
 * 2016年4月5日 下午4:05:31    			admin     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

import java.util.Date;

/**
 * @ClassName CommentTipRecord
 * @author xuyang.li
 * @date 2016年4月5日
 */

public class CommentTopRecord {

	private int id;
	
	private int commentId;
	
	private String userId;
	
	private String type;
	
	private Date time;
	
	private Date cts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}
	
}
