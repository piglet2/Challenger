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
 * @date 2016-5-19
 */
public class EvaluationPeriod {
	
	/*
	 * 主键自增长
	*/
	private int id;
	
	/*
	 * 期唯一标识
	*/
	private String periodId;
	
	/*
	 * 周期开始
	 * */
	private Date periodDateFrom;
	
	/*
	 * 周期结束
	 * */
	private Date periodDateTo;
	
	/*
	 * 描述
	 * */
	private String des;
	
	/*
	 * 创建时间
	 * */
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

	public Date getPeriodDateFrom() {
		return periodDateFrom;
	}

	public void setPeriodDateFrom(Date periodDateFrom) {
		this.periodDateFrom = periodDateFrom;
	}

	public Date getPeriodDateTo() {
		return periodDateTo;
	}

	public void setPeriodDateTo(Date periodDateTo) {
		this.periodDateTo = periodDateTo;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

}
