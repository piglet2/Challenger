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
public class EvaluationRateBo {

	/*
	 * 上级id
	*/
	private String managerId;
	
	/*
	 * 完成率
	*/
	private Integer Rate;

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public Integer getRate() {
		return Rate;
	}

	public void setRate(Integer rate) {
		Rate = rate;
	}

}
