/******************************************************************************
 * @File name   :      LeaveWordBo.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-22 上午11:57:14
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
 * 2015-10-22 上午11:57:14    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;

import java.util.Date;


/**
 * @ClassName LeaveWordBo
 * @author guowei.wang
 * @date 2015-10-22
 */
public class LeaveWordBo {
	
	private String id;
	
	private String userId;
	
	private String username_cn;
	
	private String username_en;
	
	private String userId_target;
	
	private String username_cn_target;

	private String username_en_target;
	
	private String contents;
	
	private String photo;
	
	private String photo_target;
	
	// TODO 以下为过时字段，仅用于保证向下兼容性, 待正式上线应去除
	@Deprecated
	private String userIdTarget;
	@Deprecated
	private String username;
	@Deprecated
	private String usernameTarget;
	
	private Date cts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername_cn() {
		return username_cn;
	}

	public void setUsername_cn(String username_cn) {
		this.username_cn = username_cn;
	}

	public String getUsername_en() {
		return username_en;
	}

	public void setUsername_en(String username_en) {
		this.username_en = username_en;
	}

	public String getUserId_target() {
		return userId_target;
	}

	public void setUserId_target(String userId_target) {
		this.userId_target = userId_target;
	}

	public String getUsername_cn_target() {
		return username_cn_target;
	}

	public void setUsername_cn_target(String username_cn_target) {
		this.username_cn_target = username_cn_target;
	}

	public String getUsername_en_target() {
		return username_en_target;
	}

	public void setUsername_en_target(String username_en_target) {
		this.username_en_target = username_en_target;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

	public String getPhoto_target() {
		return photo_target;
	}

	public void setPhoto_target(String photo_target) {
		this.photo_target = photo_target;
	}

	public String getUserIdTarget() {
		return userIdTarget;
	}

	public void setUserIdTarget(String userIdTarget) {
		this.userIdTarget = userIdTarget;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernameTarget() {
		return usernameTarget;
	}

	public void setUsernameTarget(String usernameTarget) {
		this.usernameTarget = usernameTarget;
	}
}
