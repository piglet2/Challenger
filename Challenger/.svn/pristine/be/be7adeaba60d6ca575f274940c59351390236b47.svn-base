/******************************************************************************
 * @File name   :      CourseService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-20 下午3:54:39
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
 * 2016-1-20 下午3:54:39    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.enums.CourseStatus;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.dao.CourseDao;
import com.envision.envservice.entity.bo.CourseBo;
import com.envision.envservice.entity.bo.CourseFeedbackBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.Course;
import com.envision.envservice.entity.dto.CourseRecord;
import com.envision.envservice.service.exception.ServiceException;

/**
 * @ClassName CourseService
 * @author guowei.wang
 * @date 2016-1-20
 */
@Service
public class CourseService {
	
	@Autowired
	private CourseDao courseDao;

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CourseRecordService courseRecordService;
	
	public CourseBo queryById(int id) throws Exception {
		Course course = courseDao.queryById(id);
		
		return course == null ? null : toBo(course);
	}
	
	public List<CourseBo> queryAll() throws Exception {
		List<Course> allCourse = courseDao.queryAll();
		
		return toBos(allCourse);
	}
	

	public void arrange(int courseId) throws ServiceException {
		checkExsitEx(courseId);
		if (CourseStatus.ARRANGEABLE == calcStatus(courseId)) {
			courseRecordService.arrange(SessionUtil.getUserId(httpSession), courseId);
		} else {
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, "非ARRANGEABLE状态");
		}
	}
	
	public void feedback(int courseId, CourseFeedbackBo feedback) throws ServiceException {
		checkExsitEx(courseId);
		if (CourseStatus.FEEDBACKABLE == calcStatus(courseId)) {
			courseRecordService.feedback(SessionUtil.getUserId(httpSession), courseId, feedback);
		} else {
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, "非FEEDBACKABLE状态");
		}
	}

	public CourseFeedbackBo feedback(int courseId) throws ServiceException {
		checkExsitEx(courseId);
		if (CourseStatus.ALREAY_FEEDBACK == calcStatus(courseId)) {
			CourseRecord courseRecord = courseRecordService.queryRecordByCourseIdAndUserId(SessionUtil.getUserId(httpSession), courseId);
			
			CourseFeedbackBo courseFeedback = new CourseFeedbackBo();
			courseFeedback.setStars(courseRecord.getFeedbackStars());
			courseFeedback.setFeedback(courseRecord.getFeedbackMessage());
			
			return courseFeedback;
		} else {
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, "非ALREAY_FEEDBACK状态");
		}
	}
	
	public boolean checkExsit(int id) {
		return courseDao.queryById(id) != null;
	}
	
	private void checkExsitEx(int id) throws ServiceException {
		if (!checkExsit(id)) {
			throw new ServiceException(Code.COURSE_NOT_EXSIT, "Course不存在");
		}
	}

	private List<CourseBo> toBos(List<Course> allCourse) throws Exception {
		List<CourseBo> bos = new ArrayList<CourseBo>(allCourse.size());
		for (Course course : allCourse) {
			bos.add(toBo(course));
		}
		
		return bos;
	}

	private CourseBo toBo(Course course) throws Exception {
		CourseBo bo = new CourseBo();
		bo.setId(course.getId());
		bo.setName(course.getName());
		bo.setUserId(course.getUserId());
		bo.setStartTime(course.getStartTime());
		bo.setEndTime(course.getEndTime());
		
		UserBo user = userService.queryUser(bo.getUserId());
		if (user != null) {
			bo.setNameCn(user.getCn_name());
			bo.setNameEn(user.getEn_name());
			bo.setTitle(user.getTitle());
			bo.setDepartment(user.getDepartment());
			bo.setDivision(user.getDivision());
			bo.setPhoto(user.getPhoto());
		}
		
		bo.setStatus(calcStatus(course).value());
		
		return bo;
	}
	
	private CourseStatus calcStatus(int courseId) {
		return calcStatus(courseDao.queryById(courseId));
	}
	
	private CourseStatus calcStatus(Course course) {
		String userId = SessionUtil.getUserId(httpSession);
		
		boolean hasExpire = DateUtil.hasExpire(course.getEndTime());
		boolean hasArrange = courseRecordService.hasArrange(course.getId(), userId);
		if (hasExpire) {
			if (hasArrange) {
				if (courseRecordService.hasFeedback(course.getId(), userId)) {
					return CourseStatus.ALREAY_FEEDBACK;
				} else {
					return CourseStatus.FEEDBACKABLE;
				}
			} else {
				return CourseStatus.FINISH;
			}
		} else {
			if (hasArrange) {
				return CourseStatus.ALREAY_ARRANGE;
			} else {
				return CourseStatus.ARRANGEABLE;
			}
		}
	}
}
