/******************************************************************************
 * @File name   :      PriseRankingBo.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-27 上午11:39:36
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
 * 2016-1-27 上午11:39:36    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;

/**
 * @ClassName PriseRankingBo
 * @author guowei.wang
 * @date 2016-1-27
 */
public class PriseRankingBo {

	private String userId;
	
	private String percent;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
}
