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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.ClgPictureDao;
import com.envision.envservice.entity.bo.ClgPictureBo;
import com.envision.envservice.entity.dto.ClgPicture;


/**
 * @ClassName UserCaseService
 * @author guowei.wang
 * @date 2016-1-5
 */
@Service
public class ClgPictureService {
	
	@Autowired
	private ClgPictureDao ClgPictureDao;
	
	public List<ClgPicture> addClgPicture(ClgPictureBo ClgPictureBo) {
		List<ClgPicture> cplist = newClgPicture(ClgPictureBo);
		for(int i=0;i<cplist.size();i++){
			ClgPictureDao.add(cplist.get(i));
		}
		return cplist;
	}
	
	private List<ClgPicture> newClgPicture(ClgPictureBo ClgPictureBo) {
		List<ClgPicture> cplist=new ArrayList<ClgPicture>();
		String[] pics=ClgPictureBo.getPic();
		for(int i=0;i<pics.length;i++){
			ClgPicture cp = new ClgPicture();
			cp.setType(ClgPictureBo.getType());
			cp.setType_id(ClgPictureBo.getType_id());
			cp.setPic(pics[i]);
			cp.setOrd(i+1);
			cplist.add(cp);
		}
		return cplist;
	}
	
	public List<String> queryPicByTypeAndTypeId(String type,int type_id){
		Map map=new HashMap();
		map.put("type", type);
		map.put("type_id",type_id);
		List<ClgPicture> cplist=ClgPictureDao.queryByTypeAndTypeId(map);
		List<String> pics=new ArrayList<String>();
		for(int i=0;i<cplist.size();i++){
			String pic=new String();
			pic=cplist.get(i).getPic();
			pics.add(pic);
		}
		return pics;
	}

}
