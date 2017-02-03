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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.AwardDao;
import com.envision.envservice.entity.dto.Award;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.entity.vo.UserDetailVo;
import com.envision.envservice.entity.vo.UserDetailVo.Awards;
import com.envision.envservice.service.SAPService.SAPResponse;


/**
 * @ClassName AwardsService
 * @author caisong
 * @date 2016-11-15
 */
@Service
public class AwardService {
	
	@Autowired
	private SAPService sapService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private AwardDao awardDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map addAllAward() throws Exception{
		List<SAPEmpJob>	 list=sapEmpJobService.queryAll();
		int result=0;
		Date begin=new Date();
		for(int i=0;i<list.size();i++){
			String userId=list.get(i).getUserId();
			int add=addAwardByUser(userId);
			result+=add;
		}
		Date end=new Date();
		String msg="开始时间："+begin+"，结束时间："+end+"，已更新奖惩记录："+result+"条";
		Map map=new HashMap();
		map.put("msg", msg);
		return map;
	}
	
	
	/*
	 * 新增奖惩记录
	 * 
	 * */
	public int addAwardByUser(String userId) throws Exception{
		SAPResponse[] sapResponses = sapService.queryEntity(userDetailService.buildAwardQuery(userId));
		List<Awards> awards=userDetailService.parseResponseAwards(sapResponses[0]);
		int result=0;
		int del=awardDao.deleteByUserId(userId);
		if(awards!=null&&awards.size()>0){
			for(int i=0;i<awards.size();i++){
				Award award=new Award();
				award.setUserId(userId);
				award.setComment(awards.get(i).getInstitution());
				award.setScope(awards.get(i).getName());
				award.setType(awards.get(i).getVfld4());
				award.setIssuedate(awards.get(i).getIssueDate());
				int add=awardDao.addAward(award);
				result+=add;
			}
		}
		System.out.println("员工号："+userId+"，新增奖惩记录："+result+"条");
		return result;
	}
	
	
	public List<Awards> queryAwardByUserId(String userId){
		List<Award> list=awardDao.queryByUserId(userId);
		List<Awards> awards=new ArrayList<UserDetailVo.Awards>();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Awards  a=new Awards();
				a.setVfld4(list.get(i).getType());
				a.setInstitution(list.get(i).getComment());
				a.setName(list.get(i).getScope());
				a.setIssueDate(list.get(i).getIssuedate());
				awards.add(a);
			}
		}
		return awards;
	}
	
}
