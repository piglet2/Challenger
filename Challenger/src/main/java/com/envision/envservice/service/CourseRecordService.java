/******************************************************************************
 * @File name   :      CourseRecordService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-20 下午5:26:39
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
 * 2016-1-20 下午5:26:39    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.CourseRecordDao;
import com.envision.envservice.entity.bo.CourseFeedbackBo;
import com.envision.envservice.entity.dto.CourseRecord;

/**
 * @ClassName CourseRecordService
 * @author guowei.wang
 * @date 2016-1-20
 */
@Service
public class CourseRecordService {
	
	@Autowired
	private CourseRecordDao courseRecordDao;

	public boolean hasArrange(int courseId, String userId) {
		return queryRecordByCourseIdAndUserId(userId, courseId) != null;
	}

	public boolean hasFeedback(int courseId, String userId) {
		CourseRecord courseRecord = queryRecordByCourseIdAndUserId(userId, courseId);
		
		return courseRecord != null && courseRecord.getFeedbackStars() != null && courseRecord.getFeedbackMessage() != null;
	}

	public void arrange(String userId, int courseId) {
		CourseRecord courseRecord = new CourseRecord();
		courseRecord.setCourseId(courseId);
		courseRecord.setUserId(userId);
		courseRecord.setArrangeTime(new Date());
		
		courseRecordDao.create(courseRecord);
	}

	public void feedback(String userId, int courseId, CourseFeedbackBo feedback) {
		CourseRecord courseRecord = queryRecordByCourseIdAndUserId(userId, courseId);
		courseRecord.setFeedbackStars(feedback.getStars());
		courseRecord.setFeedbackMessage(feedback.getFeedback());
		courseRecord.setFeedbackTime(new Date());
		
		courseRecordDao.update(courseRecord);
	}
	
	public CourseRecord queryRecordByCourseIdAndUserId(String userId, int courseId) {
		return courseRecordDao.queryByCourseIdAndUserId(userId, courseId);
	}
}
