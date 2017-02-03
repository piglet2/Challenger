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
 * @ClassName EvaluationAnnInfoBo
 * @author caisong
 * @date 2016-9-26
 */
public class EvaluationAnnInfoBo {
	
	private String periodId;

	private String year;
	
	private List<EvaluationInfoBo> EvaluationList;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<EvaluationInfoBo> getEvaluationList() {
		return EvaluationList;
	}

	public void setEvaluationList(List<EvaluationInfoBo> evaluationList) {
		EvaluationList = evaluationList;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	
}
