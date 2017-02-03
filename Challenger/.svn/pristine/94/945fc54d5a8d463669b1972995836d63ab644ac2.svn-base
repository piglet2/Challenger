/******************************************************************************
 * @File name   :      UserCaseResource.java
 *
 * @Package 	:	   com.envision.envservice.rest
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-4 下午5:42:18
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
 * 2016-1-4 下午5:42:18    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.FailResult;
import com.envision.envservice.common.JSONFilter;
import com.envision.envservice.entity.bo.ClgPictureBo;
import com.envision.envservice.entity.dto.ClgPicture;
import com.envision.envservice.service.ClgPictureService;
import com.envision.envservice.service.MapConfigService;
import com.qiniu.util.Auth;

/**
 * @ClassName PictureUploadResource
 * @author caisong
 * @date 2016-4-28
 */
@Path("picture_upload")
@Component
public class PictureUploadResource {
	
	@Autowired
	private ClgPictureService ClgPictureService;
	
	@Autowired
	private MapConfigService mapConfigService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNew(ClgPictureBo clgPicture) throws Exception {
		HttpStatus status = HttpStatus.CREATED;
		String response = StringUtils.EMPTY;
		if (!checkParam(clgPicture)) {
			status = HttpStatus.BAD_REQUEST;
			response = FailResult.toJson(Code.PARAM_ERROR, "参数有误");
		} else {
			List<ClgPicture> list=ClgPictureService.addClgPicture(clgPicture);
			List<Map> maplist=new ArrayList<Map>();
			for(int i=0;i<list.size();i++){
				Map map=new HashMap();
				map.put("id", list.get(i).getId().toString());
				maplist.add(map);
			}
			response = JSONObject.toJSONString(maplist, JSONFilter.UNDERLINEFILTER);
			return Response.status(status.value()).entity(response).build();
		}
		
		return null;
	}
	
	/**
	 * 新增事件参数验证
	 */
	private boolean checkParam(ClgPictureBo clgPicture) {
		String[] pic = clgPicture.getPic();
		String type = clgPicture.getType();
		int type_id=clgPicture.getType_id();
		return StringUtils.isNotEmpty(type) && pic != null &&  pic.length > 0&&type_id!=0;
	}
	
	/**
	 * 获取Token
	 */
	@GET
	@Path("/get_token")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToken(){
		  HttpStatus status = HttpStatus.CREATED;
		  String response = StringUtils.EMPTY;
		  //设置好账号的ACCESS_KEY和SECRET_KEY
		  String ACCESS_KEY=mapConfigService.getValue("ACCESS_KEY");
		  String SECRET_KEY=mapConfigService.getValue("SECRET_KEY");
		  //要上传的空间
		  String bucketname = "challenger";
		  //密钥配置
		  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		  Map map=new HashMap();
		  map.put("token", auth.uploadToken(bucketname).toString());
		  response = JSONObject.toJSONString(map, JSONFilter.UNDERLINEFILTER);
		  return Response.status(status.value()).entity(response).build();
	}
}
