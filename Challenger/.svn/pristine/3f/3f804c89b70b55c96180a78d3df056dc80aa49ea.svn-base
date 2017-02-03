/******************************************************************************
 * @File name   :      SAPCommonService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-16 下午2:21:20
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
 * 2015-12-16 下午2:21:20    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.service.SAPService.SAPQuery;
import com.envision.envservice.service.SAPService.SAPResponse;

/**
 * @ClassName SAPCommonService
 * @author guowei.wang
 * @date 2015-12-16
 */
@Service
public class SAPCommonService {
	
	@Autowired
	private SAPService sapService;

	/**
	 * 选择列表标签查询
	 */
	public String queryPickListLabel(String pickListId) throws Exception {
		SAPResponse pickListLabelResponse = sapService.queryEntity(buildPickListLabelQuery(pickListId));
		
		return parsePickListLabelResponse(pickListLabelResponse);
	}
	
	private SAPQuery buildPickListLabelQuery(String optionId) {
		return SAPQuery.newInstance("PicklistOption(" + optionId + "L)/picklistLabels", null, "$select=locale,label");
	}
	
	private String parsePickListLabelResponse(SAPResponse sapResponse) {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			for (Object obj : jsonArray) {
				JSONObject jsonObject = (JSONObject) obj;
				if ("zh_CN".equals(jsonObject.getString("locale"))) {
					return jsonObject.getString("label");
				}
			}
		}
		
		return null;
	}
}
