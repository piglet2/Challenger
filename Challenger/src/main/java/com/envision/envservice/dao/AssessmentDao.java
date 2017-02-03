/******************************************************************************
 * @File name   :      EvaluationDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-4 下午5:36:35
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
 * 2016-1-4 下午5:36:35    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;
import java.util.Map;

import com.envision.envservice.entity.dto.Assessment;

/**
 * @ClassName AssessmentDao
 * @author caisong
 */
@SuppressWarnings("rawtypes")
public interface AssessmentDao {
	
	public int addAssessment(Assessment assessment);
	
	public int addFeedback(Map map);
	
	public List<Assessment> queryByCycleIdAndCriticId(Map map);
	
	public List<Assessment> queryByCycleIdAndUserId(Map map);
	
	public List<Assessment> queryByUserId(String userId);
	
	public List<Assessment> queryByUserIdAndDep(Map map);
	
	public List<Assessment> queryByCriticId(String criticId);
	
	public List<Assessment> queryByCycleId(String cycleId);
	
	public List<Assessment> queryByCycleIdAndUserIdAndCriticId(Map map);

}
