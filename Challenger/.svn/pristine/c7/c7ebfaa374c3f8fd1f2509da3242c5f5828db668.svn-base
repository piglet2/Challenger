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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.common.util.PicUtil;
import com.envision.envservice.dao.RedMineUserDao;
import com.envision.envservice.dao.SAPUserDao;
import com.envision.envservice.entity.redmine.Users;
import com.envision.envservice.entity.sap.SAPUser;

/**
 * SAP User 同步器
 * 
 * @ClassName SyncSAPUser
 * @author guowei.wang
 * @date 2015-11-2
 */
@Component("redmineUserSyncer")
public class RedmineUserSyncer {
	
	
	@Autowired
	private SAPUserDao sapUserDao;
	
	@Autowired
	private RedMineUserDao redMineUserDao;
	
	private static int num=0;
	
	/*
	 * 数据同步
	 * 
	 * */
	public int sync(){
		num=0;
		int j=0;
		List<SAPUser> list=sapUserDao.queryByRedmine();
		for(int i=0;i<list.size();i++){
			if(checkSAPUser(list.get(i))){
				j=redMineUserDao.replace(sapUserToUsers(list.get(i)));
				if(j>0){
					num++;
				}else{
					continue;
				}
			}else{
				continue;
			}
		}
		
		List<SAPUser> lst=sapUserDao.queryAllByStatus();
		for(int i=0;i<lst.size();i++){
			if(checkSAPUser(lst.get(i))){
				j=redMineUserDao.replace(sapUserToUsersByStatus(lst.get(i)));
				if(j>0){
					num++;
				}else{
					continue;
				}
			}else{
				continue;
			}
		}
		
		return num;
	}
	
	/*
	 * SAPUser数据效验
	 * 
	 * */
	public boolean checkSAPUser(SAPUser su){
		boolean flag=true;
		if(su.getUserId().matches("[0-9]+")){
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}
	
	/*
	 * 在职状态SAPUser转换成Users
	 * 
	 * */
	public Users sapUserToUsers(SAPUser su){
		Users users=new Users();
		users.setId(Integer.parseInt(su.getUserId()));
		if(su.getUsername()!=null&&!"".equals(su.getUsername())){
			users.setLogin(su.getUsername());
		}else{
			users.setLogin("null");
		}
		if(su.getFirstName()!=null&&!"".equals(su.getFirstName())){
			users.setFirstname(su.getFirstName());
		}else{
			users.setFirstname("null");
		}
		if(su.getLastName()!=null&&!"".equals(su.getLastName())){
			users.setLastname(su.getLastName());
		}else{
			users.setLastname("null");
		}
		if(su.getEmail()!=null&&!"".equals(su.getEmail())){
			users.setMail(su.getEmail());
		}else{
			users.setMail("null");
		}
		if(su.getUts()!=null&&!"".equals(su.getUts())){
			users.setCreated_on(su.getUts());
		}else{
			users.setCreated_on(new Date());
		}
		if(PicUtil.getPicPath(su.getUserId())!=null&&!"".equals(PicUtil.getPicPath(su.getUserId()))){
			users.setIdentity_url(PicUtil.getPicPath(su.getUserId()));
		}else{
			users.setIdentity_url("null");
		}
		users.setAuth_source_id(1);
		users.setStatus(1);
		users.setLanguage("zh");
		users.setType("User");
		if(su.getDivision()!=null&&!"".equals(su.getDivision())){
			users.setDivision(su.getDivision());
		}else{
			users.setDivision("null");
		}
		if(su.getDepartment()!=null&&!"".equals(su.getDepartment())){
			users.setDepartment(su.getDepartment());
		}else{
			users.setDepartment("null");
		}
		return users;
	}
	
	/*
	 * 离职状态SAPUser转换成Users
	 * 
	 * */
	public Users sapUserToUsersByStatus(SAPUser su){
		Users users=new Users();
		users.setId(Integer.parseInt(su.getUserId()));
		if(su.getUsername()!=null&&!"".equals(su.getUsername())){
			users.setLogin(su.getUsername());
		}else{
			users.setLogin("null");
		}
		if(su.getFirstName()!=null&&!"".equals(su.getFirstName())){
			users.setFirstname(su.getFirstName());
		}else{
			users.setFirstname("null");
		}
		if(su.getLastName()!=null&&!"".equals(su.getLastName())){
			users.setLastname(su.getLastName());
		}else{
			users.setLastname("null");
		}
		if(su.getEmail()!=null&&!"".equals(su.getEmail())){
			users.setMail(su.getEmail());
		}else{
			users.setMail("null");
		}
		if(su.getUts()!=null&&!"".equals(su.getUts())){
			users.setCreated_on(su.getUts());
		}else{
			users.setCreated_on(new Date());
		}
		if(PicUtil.getPicPath(su.getUserId())!=null&&!"".equals(PicUtil.getPicPath(su.getUserId()))){
			users.setIdentity_url(PicUtil.getPicPath(su.getUserId()));
		}else{
			users.setIdentity_url("null");
		}
		users.setAuth_source_id(1);
		users.setStatus(0);
		users.setLanguage("zh");
		users.setType("User");
		if(su.getDivision()!=null&&!"".equals(su.getDivision())){
			users.setDivision(su.getDivision());
		}else{
			users.setDivision("null");
		}
		if(su.getDepartment()!=null&&!"".equals(su.getDepartment())){
			users.setDepartment(su.getDepartment());
		}else{
			users.setDepartment("null");
		}
		return users;
	}
	
	
}
