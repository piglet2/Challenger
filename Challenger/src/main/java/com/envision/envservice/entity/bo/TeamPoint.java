/******************************************************************************
 * @File name   :      TeamPoint.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-30 下午3:59:18
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
 * 2015-11-30 下午3:59:18    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;

import java.util.List;


/**
 * @ClassName TeamPoint
 * @author guowei.wang
 * @date 2015-11-30
 */
public class TeamPoint {
	
	private List<TeamPraisePoint> praisePointAll;
	
	private List<TeamPraisePoint> praisePointTeam;

	public List<TeamPraisePoint> getPraisePointAll() {
		return praisePointAll;
	}

	public void setPraisePointAll(List<TeamPraisePoint> praisePointAll) {
		this.praisePointAll = praisePointAll;
	}

	public List<TeamPraisePoint> getPraisePointTeam() {
		return praisePointTeam;
	}

	public void setPraisePointTeam(List<TeamPraisePoint> praisePointTeam) {
		this.praisePointTeam = praisePointTeam;
	}

	public static class TeamPraisePoint {
		private String userId;

		private String name;
		
		private Integer praiseCount;

		private Integer encourageCount;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getPraiseCount() {
			return praiseCount;
		}

		public void setPraiseCount(Integer praiseCount) {
			this.praiseCount = praiseCount;
		}

		public Integer getEncourageCount() {
			return encourageCount;
		}

		public void setEncourageCount(Integer encourageCount) {
			this.encourageCount = encourageCount;
		}
	}
}
