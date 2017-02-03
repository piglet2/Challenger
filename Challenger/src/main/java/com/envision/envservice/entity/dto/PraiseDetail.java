/******************************************************************************
 * @File name   :      PraiseDetail.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午6:57:40
 *
 * @Description : 	   点赞详情
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
 * 2015年10月19日 下午6:57:40    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @ClassName PraiseDetail
 * @Description  点赞详情
 * @author xuyang.li
 * @date 2015年10月19日
 */

public class PraiseDetail {
	
	private String id;
	
	/**被点赞人id*/
	@JsonProperty("target_user_id")
	private String targetUserId;
	
	/**点赞人id*/
	private String userId;
	
	/**点赞条目id*/
	@JsonProperty("item_id")
	private String itemId;
	
	/**点赞类型*/
	@JsonProperty("type")
	private String pType;
	
	/**点赞维度*/
	private String dimensionality;
	
	/**操作（add/delete）*/
	private String operation;
	
	/**用户剩余点赞数*/
	private String userPSurplus;
	
	/**记录标志*/
	private String earmark;
	
	private Date cts;
	
	/**来源*/
	private int from;
	
	/** 被点人部门id*/
	private String targetDeptId;
	/** 点赞人部门id*/
	private String userDeptId;
	/** 点赞和被点人关系*/
	private int levelRelation;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public String getDimensionality() {
		return dimensionality;
	}
	public void setDimensionality(String dimensionality) {
		this.dimensionality = dimensionality;
	}
	public String getUserPSurplus() {
		return userPSurplus;
	}
	public void setUserPSurplus(String userPSurplus) {
		this.userPSurplus = userPSurplus;
	}
	public String getEarmark() {
		return earmark;
	}
	public void setEarmark(String earmark) {
		this.earmark = earmark;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getCts() {
		return cts;
	}
	public void setCts(Date cts) {
		this.cts = cts;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public String getTargetDeptId() {
		return targetDeptId;
	}
	public void setTargetDeptId(String targetDeptId) {
		this.targetDeptId = targetDeptId;
	}
	public String getUserDeptId() {
		return userDeptId;
	}
	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}
	public int getLevelRelation() {
		return levelRelation;
	}
	public void setLevelRelation(int levelRelation) {
		this.levelRelation = levelRelation;
	}
}
