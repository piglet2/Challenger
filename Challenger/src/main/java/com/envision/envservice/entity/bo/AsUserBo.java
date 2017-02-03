/******************************************************************************
 * @File name   :      EvaluationBo.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-7 上午10:35:54
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
 * 2016-1-7 上午10:35:54    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;



/**
 * @ClassName AsUserBo
 * @author caisong
 * @date 2016-8-17
 */
public class AsUserBo {

	private String userId;
	
	private String cnName;
	
	private String photo;
	
	private String cycleId;
	
	/*
	 * 是否是下属
	 * true是下属，false不是下属
	 * */
	private boolean isUnderling;
	
	/*
	 * 是否是上级
	 * true是上级，false不是上级
	 * */
	private boolean isLeader;
	
	/*
	 * 是否是正式员工
	 * */
	private boolean isFormal;
	
	/*
	 * 是否被评价过
	 * */
	private boolean isEvaluated;
	
	/*
	 * 是否被评价过
	 * */
	private boolean isSelf;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isUnderling() {
		return isUnderling;
	}

	public void setUnderling(boolean isUnderling) {
		this.isUnderling = isUnderling;
	}

	public boolean isFormal() {
		return isFormal;
	}

	public void setFormal(boolean isFormal) {
		this.isFormal = isFormal;
	}

	public boolean isEvaluated() {
		return isEvaluated;
	}

	public void setEvaluated(boolean isEvaluated) {
		this.isEvaluated = isEvaluated;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
	
}
