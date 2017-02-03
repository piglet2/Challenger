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

import com.envision.envservice.entity.dto.Evaluation;

/**
 * @ClassName EvaluationDao
 * @author caisong
 */
@SuppressWarnings("rawtypes")
public interface EvaluationDao {
	
	public int addEvaluation(Evaluation evaluation);
	
	public int addNullEvaluation(Evaluation evaluation);
	
	public int addFeedback(Map map);
	
	public List<Evaluation> queryByPeriodIdAndManagerId(Map map);
	
	public List<Evaluation> queryByPeriodIdAndUserId(Map map);
	
	public List<Evaluation> queryByUserId(String userId);
	
	public List<Evaluation> queryByUserIdAndDep(Map map);
	
	public List<Evaluation> queryManagerIdByPeriodId(String periodId);
	
	public List<Evaluation> queryByPeriodId(String periodId);
	
	public Evaluation queryByPeriodIdAndManagerIdAndUserId(Map map);
	
	
}
