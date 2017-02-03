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
 * @ClassName Evaluation
 * @author caisong
 * @date 2016-5-11
 */
public class Evaluation {
	
	/*
	 * 主键自增长
	*/
	private int id;
	
	/*
	 * 期唯一标识
	*/
	private String periodId;
	
	/*
	 * 评论人id
	*/
	private String managerId;
	
	/*
	 * 被评论人id
	*/
	private String userId;
	
	/*
	 * 意志
	*/
	private int priseWill;
	
	/*
	 * 智慧
	*/
	private int priseWisdom;
	
	/*
	 * 博爱
	*/
	private int priseLove;
	
	/*
	 * 意志评价
	*/
	private String prosWill;
	
	/*
	 * 智慧评价
	*/
	private String prosWisdom;
	
	/*
	 * 博爱评价
	*/
	private String prosLove;
	
	
	/*
	 * 是否已读
	*/
	private int hasRead;
	
	/*
	 * 反馈
	*/
	private int feedback;
	
	/*
	 * 反馈时间
	*/
	private Date feedbackTime;
	
	/*
	 * 备注
	 * */
	private String remark;
	
	/*
	 * 评价时间
	*/
	private Date cts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPriseWill() {
		return priseWill;
	}

	public void setPriseWill(int priseWill) {
		this.priseWill = priseWill;
	}

	public int getPriseWisdom() {
		return priseWisdom;
	}

	public void setPriseWisdom(int priseWisdom) {
		this.priseWisdom = priseWisdom;
	}

	public int getPriseLove() {
		return priseLove;
	}

	public void setPriseLove(int priseLove) {
		this.priseLove = priseLove;
	}

	public String getProsWill() {
		return prosWill;
	}

	public void setProsWill(String prosWill) {
		this.prosWill = prosWill;
	}

	public String getProsWisdom() {
		return prosWisdom;
	}

	public void setProsWisdom(String prosWisdom) {
		this.prosWisdom = prosWisdom;
	}

	public String getProsLove() {
		return prosLove;
	}

	public void setProsLove(String prosLove) {
		this.prosLove = prosLove;
	}

	public int getHasRead() {
		return hasRead;
	}

	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}

	public int getFeedback() {
		return feedback;
	}

	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}

	public Date getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
