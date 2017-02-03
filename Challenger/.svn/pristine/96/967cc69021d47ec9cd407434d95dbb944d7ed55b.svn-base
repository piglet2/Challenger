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

import com.envision.envservice.entity.dto.AssessmentCycles;
import com.envision.envservice.entity.dto.EvaluationPeriod;


/**
 * @ClassName AssessmentCyclesDao
 * @author caisong
 */

@SuppressWarnings("rawtypes")
public interface AssessmentCyclesDao {
	
		public int addAssessmentCycles(AssessmentCycles ac);
	
		public List<AssessmentCycles> queryBycts();
	
		public List<String> queryManagerId();
		
		public AssessmentCycles queryLastAssessmentCycles();
		
		public AssessmentCycles queryLast(Map map);
		
		public AssessmentCycles queryNext(Map map);
		
		public List<AssessmentCycles> queryByCycleId(Map map);
		
		public List<AssessmentCycles> queryFirst();
		
		public List<AssessmentCycles> queryLastOne();
		
		public List<AssessmentCycles> queryAll();
		
}
