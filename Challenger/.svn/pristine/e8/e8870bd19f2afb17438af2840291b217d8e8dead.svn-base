/******************************************************************************
 * @File name   :      UserCaseService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-5 上午10:11:11
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
 * 2016-1-5 上午10:11:11    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.NotEvaluationUserDao;
import com.envision.envservice.entity.dto.NotEvaluationUser;
import com.envision.envservice.service.SAPService.SAPResponse;


/**
 * @ClassName NotEvaluationUserService
 * @author caisong
 * @date 2016-11-7
 */
@Service
public class NotEvaluationUserService {
	
	@Autowired
	private NotEvaluationUserDao notEvaluationUserDao;
	
	@Autowired
	private SAPService sapService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	
	/*
	 * 查询所有考勤类正式员工
	 * 
	 * */
	public List<NotEvaluationUser> queryAll(){
		List<NotEvaluationUser> list=notEvaluationUserDao.queryAll();
		return list;
	}
    
	
	/*
	 * 更新考勤类正式员工
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map replace() throws Exception{
		SAPResponse[] sapResponses = sapService.queryEntity(userDetailService.buildNotEvaluationUserQuery());
		List<NotEvaluationUser> list=userDetailService.parseResponseNotEvaluationUser(sapResponses[0]);
		if(list!=null&&list.size()>0){
			notEvaluationUserDao.replace(list);
		}
		String msg="更新考勤类正式员工："+list.size()+"人";
		Map map=new HashMap();
		map.put("msg", msg);
		return map;
	}
}
