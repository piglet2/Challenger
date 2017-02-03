/******************************************************************************
 * @File name   :      SAPPhotoSyncer.java
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

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.util.PicUtil;
import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.entity.sap.SAPPhoto;
import com.envision.envservice.sync.base.BaseSyncer;

/**
 * SAP Photo 同步器
 * 
 * @ClassName SAPPhotoSyncer
 * @author guowei.wang
 * @date 2015-11-2
 */
@Component("sapPhotoSyncer")
public class SAPPhotoSyncer extends BaseSyncer<SAPPhoto> {
	
	private static final String ENTITY_NAME = "Photo";

	/**
	 * 仅获取头像图片
	 * 		photoType=1
	 */
	private static final String EXTRA_PARAM_FILTER = "$filter=photoType eq 1";

	private static final String EXTRA_PARAM_SELECT = "$select=userId,mimeType,photo";
	
	private static int sync_limit = 10;

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	protected void cacheEntity(List<SAPPhoto> entitys) {
		for (SAPPhoto sapPhoto : entitys) {
			PicUtil.storePic(sapPhoto.getUserId(), sapPhoto.getBytes());
		}
	}

	@Override
	protected SAPPhoto toBo(JSONObject entityJson) {
		SAPPhoto bo = new SAPPhoto();
		
		bo.setUserId(entityJson.getString("userId"));
		bo.setBytes(entityJson.getBytes("photo"));
		
		return bo;
	}

	@Override
	protected int syncLimit() {
		return sync_limit;
	}
	
	@Override
	protected String extraParam() {
		StringBuilder buf = new StringBuilder();
		buf.append(SAPUtil.PARAM_CONNECTOR);
		buf.append(EXTRA_PARAM_SELECT);
		buf.append(SAPUtil.PARAM_CONNECTOR);
		buf.append(EXTRA_PARAM_FILTER);
		
		return buf.toString();
	}
}
