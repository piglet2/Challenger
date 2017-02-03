/******************************************************************************
 * @File name   :      SAPEmpJobService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-12 上午10:59:09
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
 * 2016-1-12 上午10:59:09    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.SAPEmpJobDao;
import com.envision.envservice.entity.sap.SAPEmpJob;

/**
 * @ClassName SAPEmpJobService
 * @author guowei.wang
 * @date 2016-1-12
 */
@Service
public class SAPEmpJobService {
	
	@Autowired
	private SAPEmpJobDao sapEmpJobDao;

	public List<String> queryUserIdsByDep(String depCode) {
		return buildIds(queryEMPJobByDep(depCode));
	}
	
	public List<SAPEmpJob> queryAll() {
		return sapEmpJobDao.queryAll();
	}

	public List<SAPEmpJob> queryEMPJobByDep(String depCode) {
		return sapEmpJobDao.queryMatch(buildQueryUserIdsByDepMatch(depCode));
	}
	
	/**
	 * 根据ManagerId 查询EMPJob表
	 * @Title: queryEMPJobByManagerId 
	 * @param userId 用户Id
	 * @return SAPEmpJob     
	 * @Date 2016年6月1日
	 * 
	 * caisong
	 */
	public List<SAPEmpJob> queryByManagerId(String managerId){
		return sapEmpJobDao.queryByManagerId(managerId);
	}
	
	/**
	 * 根据UserId 查询EMPJob表
	 * @Title: queryEMPJobById 
	 * @param userId 用户Id
	 * @return SAPEmpJob     
	 * @Date 2016年3月21日
	 */
	public SAPEmpJob queryEMPJobById(String userId){
		return sapEmpJobDao.queryById(userId);
	}
	
	/**
	 * 查询所有上级
	 * @Title: queryManager 
	 * @return List<SAPEmpJob>     
	 * @Date 2016年8月12日
	 */
	public List<SAPEmpJob> queryManager(){
		return sapEmpJobDao.queryManager();
	}

	private List<String> buildIds(List<SAPEmpJob> empJobs) {
		List<String> userIds = new ArrayList<String>(empJobs.size());
		for (SAPEmpJob empJob : empJobs) {
			userIds.add(empJob.getUserId());
		}
		
		return userIds;
	}
	
	private SAPEmpJob buildQueryUserIdsByDepMatch(String depCode) {
		SAPEmpJob empJob = new SAPEmpJob();
		empJob.setDepartment(depCode);
		
		return empJob;
	}
	
}
