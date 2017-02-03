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

import java.util.List;



/**
 * @ClassName AssessmentInfoBo
 * @author caisong
 * @date 2016-008-23
 * 被评价人收到的评价列表
 * 
 */
public class AssessmentAnnInfoBo {

	/*
	 * 周期id
	*/
	private String cycleId;
	
	/*
	 * 年度
	*/
	private String year;
	
	private List<AssessmentInfoBo> AssessmentList;

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<AssessmentInfoBo> getAssessmentList() {
		return AssessmentList;
	}

	public void setAssessmentList(List<AssessmentInfoBo> assessmentList) {
		AssessmentList = assessmentList;
	}
	
	
	
}
