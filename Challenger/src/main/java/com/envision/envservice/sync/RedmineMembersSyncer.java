/******************************************************************************
 * @File name   :      SyncSAPUser.java
 *
 * @Package 	:	   com.envision.envservice.sync
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-2 下午2:42:49
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
 * 2015-11-2 下午2:42:49    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.sync;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.dao.ProjectsDao;
import com.envision.envservice.dao.RedMineMembersDao;
import com.envision.envservice.entity.redmine.Members;
import com.envision.envservice.entity.redmine.Projects;
import com.envision.envservice.entity.sap.SAPUser;
import com.envision.envservice.service.SAPUserService;

/**
 * SAP User 同步器
 * 
 * @ClassName SyncSAPUser
 * @author guowei.wang
 * @date 2015-11-2
 */
@Component("redmineMembersSyncer")
public class RedmineMembersSyncer {
	
	
	@Autowired
	private RedMineMembersDao redMineMembersDao;
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private SAPUserService sapUserService;
	
	private static int num=0;
	
	/*
	 * 数据同步
	 * 
	 * */
	public int sync(){
		deleteMembers();
		num=0;
		num=addMembers();
//		checkMembers();
		return num;
	}
	public void deleteMembers(){
		redMineMembersDao.deleteMembers();
	}
	
	public int addMembers(){
		int i=redMineMembersDao.addMembers();
		return i;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int checkMembers(){
		int result=0;
		List<Projects> plist=projectsDao.queryDivision();
		List<Members> mlist=redMineMembersDao.queryMembers();
		for(int i=0;i<plist.size();i++){
			for(int j=0;j<mlist.size();j++){
				SAPUser sapUser=sapUserService.queryById(String.valueOf(mlist.get(j).getUserId()));
				if(plist.get(i).getName()==sapUser.getDivision()||plist.get(i).getName().equals(sapUser.getDivision())){
					Members members=new Members();
					Map map=new HashMap();
					map.put("name", plist.get(i).getName());
					List<Projects> projectsList=projectsDao.queryName(map);
					members.setId(mlist.get(j).getId());
					members.setCreatedOn(mlist.get(j).getCreatedOn());
					members.setMailNotification(mlist.get(j).getMailNotification());
					members.setUserId(mlist.get(j).getUserId());
					members.setProjectId(Integer.parseInt(projectsList.get(0).getName()));
					result=result+updateMembers(members);
				}
			}
		}
		return result;
	}
	
	public int updateMembers(Members m){
		int i=redMineMembersDao.updateMembers(m);
		return i;
	}
	
	
}
