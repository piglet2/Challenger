/******************************************************************************
 * @File name   :      UserCaseInfoVo.java
 *
 * @Package 	:	   com.envision.envservice.entity.vo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-5 下午2:42:32
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
 * 2016-1-5 下午2:42:32    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;

import java.util.List;

/**
 * @ClassName UserCaseInfoVo
 * @author guowei.wang
 * @date 2016-1-5
 */
public class UserCaseInfoBo {

	private String id;
	
	private List<UserBaseBo> members;
	
	private String message;
	
	private String priseCount;
	
	private String hasRead;
	
	private String newPrise;
	
	private String canPrise;
	
	private List<UserBaseBo> followers;

	private String time;
	
	private String anonymous;

	private UserBaseBo creator;
	
	private String commentCount;
	
	private List<String> piclist;
	
	private List<UserBaseBo> reminds;
	
	
	public List<UserBaseBo> getReminds() {
		return reminds;
	}

	public void setReminds(List<UserBaseBo> reminds) {
		this.reminds = reminds;
	}

	public List<String> getPiclist() {
		return piclist;
	}

	public void setPiclist(List<String> piclist) {
		this.piclist = piclist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<UserBaseBo> getMembers() {
		return members;
	}

	public void setMembers(List<UserBaseBo> members) {
		this.members = members;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHasRead() {
		return hasRead;
	}

	public void setHasRead(String hasRead) {
		this.hasRead = hasRead;
	}

	public String getNewPrise() {
		return newPrise;
	}

	public String getCanPrise() {
		return canPrise;
	}

	public void setCanPrise(String canPrise) {
		this.canPrise = canPrise;
	}

	public void setNewPrise(String newPrise) {
		this.newPrise = newPrise;
	}

	public List<UserBaseBo> getFollowers() {
		return followers;
	}

	public void setFollowers(List<UserBaseBo> followers) {
		this.followers = followers;
	}

	public String getPriseCount() {
		return priseCount;
	}

	public void setPriseCount(String priseCount) {
		this.priseCount = priseCount;
	}

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public UserBaseBo getCreator() {
		return creator;
	}

	public void setCreator(UserBaseBo creator) {
		this.creator = creator;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
}
