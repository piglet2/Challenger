/******************************************************************************
 * @File name   :      UserCasePriseBo.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-7 上午11:39:37
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
 * 2016-1-7 上午11:39:37    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @ClassName UserCasePriseBo
 * @author guowei.wang
 * @date 2016-1-7
 */
public class UserCasePriseBo {
	
	@JsonProperty("case_id")
	private Integer caseId;

	@JsonProperty("prise_will")
	private boolean priseWill;
	
	@JsonProperty("prise_wisdom")
	private boolean priseWisdom;
	
	@JsonProperty("prise_love")
	private boolean priseLove;

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public boolean isPriseWill() {
		return priseWill;
	}

	public void setPriseWill(boolean priseWill) {
		this.priseWill = priseWill;
	}

	public boolean isPriseWisdom() {
		return priseWisdom;
	}

	public void setPriseWisdom(boolean priseWisdom) {
		this.priseWisdom = priseWisdom;
	}

	public boolean isPriseLove() {
		return priseLove;
	}

	public void setPriseLove(boolean priseLove) {
		this.priseLove = priseLove;
	}
}
