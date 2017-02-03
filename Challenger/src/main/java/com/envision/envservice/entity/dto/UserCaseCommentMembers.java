/******************************************************************************
 * @File name   :      UserCaseComment.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-29 下午2:28:25
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
 * 2016-1-29 下午2:28:25    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;


/**
 * @ClassName UserCaseComment
 * @author guowei.wang
 * @date 2016-1-29
 */
public class UserCaseCommentMembers {

	private int id;
	
	/*
	 * 评论id
	 * 
	 * */
	private int commentId;
	
	/*
	 * 相关人id
	 * 
	 * */
	private String memberId;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
