/******************************************************************************
 * @File name   :      PraiseReport.java
 *
 * @Package 	:	   com.envision.envservice.report.entity
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-30 下午3:44:49
 *
 * @Description : 	   
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
 * 2015-11-30 下午3:44:49    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.report.entity;

import java.util.List;
import java.util.Map;

import com.envision.envservice.entity.bo.PraisePoint;
import com.envision.envservice.entity.bo.TeamPoint;


public class PraiseReport {

	private List<PraisePoint> obtainPraises;
	
	private List<PraisePoint> obtainEncourages;

	private List<PraisePoint> givePraises;
	
	private List<PraisePoint> giveEncourages;

	private Map<String, TeamPoint> teamPoints;

	public List<PraisePoint> getObtainPraises() {
		return obtainPraises;
	}

	public void setObtainPraises(List<PraisePoint> obtainPraises) {
		this.obtainPraises = obtainPraises;
	}

	public List<PraisePoint> getObtainEncourages() {
		return obtainEncourages;
	}

	public void setObtainEncourages(List<PraisePoint> obtainEncourages) {
		this.obtainEncourages = obtainEncourages;
	}

	public List<PraisePoint> getGivePraises() {
		return givePraises;
	}

	public void setGivePraises(List<PraisePoint> givePraises) {
		this.givePraises = givePraises;
	}

	public List<PraisePoint> getGiveEncourages() {
		return giveEncourages;
	}

	public void setGiveEncourages(List<PraisePoint> giveEncourages) {
		this.giveEncourages = giveEncourages;
	}

	public Map<String, TeamPoint> getTeamPoints() {
		return teamPoints;
	}

	public void setTeamPoints(Map<String, TeamPoint> teamPoints) {
		this.teamPoints = teamPoints;
	}
}


