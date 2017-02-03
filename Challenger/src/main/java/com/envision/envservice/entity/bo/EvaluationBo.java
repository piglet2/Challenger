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
 * @ClassName EvaluationBo
 * @author caisong
 * @date 2016-5-11
 */
public class EvaluationBo {

	private Integer id;
	
	/*
	 * 周期id
	*/
	private String periodId;
	
	/*
	 * 下级id
	*/
	private String userId;
	
	/*
	 * 上级id
	*/
	private String managerId;
	
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
	 * 意志评分
	*/
	private String prosWill;
	
	/*
	 * 智慧评分
	*/
	private String prosWisdom;
	
	/*
	 * 博爱评分
	*/
	private String prosLove;
	
	/*
	 * 照片
	*/
	private String photo;
	
	/*
	 * 中文名
	*/
	private String usernameCn;
	
	/*
	 * 反馈
	*/
	private int feedback;
	
	/*
	 * 开始周期
	*/
	private String periodDateFrom;
	
	/*
	 * 结束周期
	*/
	private String periodDateTo;
	
	/*
	 * 描述第几期
	*/
	private String des;
	
	/*
	 *是否已经评论过，能否评论
	 * true 没有评论过
	 * false 已经评论过
	*/
	private boolean flag;
	
	/*
	 * 评论时间是否过期
	 * true 未过期 可以评论
	 * false 已经过期 不能评论
	*/
	private  boolean prescription;
	
	/*
	 * 是否是第一个
	 * true : 是第一个
	 * false : 不是第一个
	 * */
	private boolean isFirst;
	
	/*
	 * 是否是最后一个
	 * true : 是最后一个
	 * false : 不是最后一个
	 * */
	private boolean isLast;
	
	//备注
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUsernameCn() {
		return usernameCn;
	}

	public void setUsernameCn(String usernameCn) {
		this.usernameCn = usernameCn;
	}

	public int getFeedback() {
		return feedback;
	}

	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}

	public String getPeriodDateFrom() {
		return periodDateFrom;
	}

	public void setPeriodDateFrom(String periodDateFrom) {
		this.periodDateFrom = periodDateFrom;
	}

	public String getPeriodDateTo() {
		return periodDateTo;
	}

	public void setPeriodDateTo(String periodDateTo) {
		this.periodDateTo = periodDateTo;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isPrescription() {
		return prescription;
	}

	public void setPrescription(boolean prescription) {
		this.prescription = prescription;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	
}
