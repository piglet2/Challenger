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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.PushConfigDao;
import com.envision.envservice.entity.bo.PushConfigBo;
import com.envision.envservice.entity.dto.PushConfig;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class PushConfigService {
	
	@Autowired
	private PushConfigDao pcDao;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SAPUserService sapUserService;
	
	
	/*
	 * 添加用户和手机设备信息
	 * 
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map addPushConfig(PushConfigBo  pushConfigBo) {
		int num=checkPushConfig(pushConfigBo);
		System.out.println("已登陆过的手机数量："+num);
		int i=pcDao.addPushConfig(pushConfigBo);
		int id=pushConfigBo.getId();
		boolean result=false;
		Map map=new HashMap();
		if(i>0){
			result=true;
			map.put("result", result);
		}else{
			result=false;
			map.put("result", result);
		}
    	return map;
	}
	
	/*
	 * 检查员工手机信息是否存在
	 * 存在则删除
	 * 
	 * */
	public int checkPushConfig(PushConfigBo  pushConfigBo) {
		//如果登陆员工账号在其他手机上登陆过，删除之前的登陆手机信息
		List<PushConfig> list=pcDao.queryByUsername(pushConfigBo.getUsername());
		int result=0;
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				pcDao.delByUsername(list.get(i).getUsername());
				result++;
			}
		}
		//如果这个手机登陆过别的账号，删除之前的登陆手机信息
		List<PushConfig> lst=pcDao.queryByToken(pushConfigBo.getDeviceToken());
		if(lst!=null&&lst.size()>0){
			for(int i=0;i<lst.size();i++){
				pcDao.delByToken(lst.get(i).getDeviceToken());
				result++;
			}
		}
		return result;
	}
	
	/*
	 * 获取所有登陆用户手机信息
	 * 
	 * */
	public List<PushConfig> queryAll(){
		List<PushConfig> list=pcDao.queryAll();
		return list;
	}
	
	/*
	 * 根据username获取用户手机信息
	 * 
	 * */
	public List<PushConfig> queryByUsername(String username){
		List<PushConfig> list=new ArrayList<PushConfig>();
		list=pcDao.queryByUsername(username);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}
	
}
