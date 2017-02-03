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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.dao.SAPAssessmentUserDao;
import com.envision.envservice.entity.sap.SAPAssessmentUser;
import com.envision.envservice.sync.base.BaseSyncer;

/**
 * SAP cust_Authority 同步器
 * 
 * @ClassName SAPAssessmentUserSyncer
 * @author song.cai
 * @date 2016-08-29
 */
@Component("sapAssessmentUserSyncer")
public class SAPAssessmentUserSyncer extends BaseSyncer<SAPAssessmentUser> {
	
	@Autowired
	private SAPAssessmentUserDao sapAssessmentUserDao;
	
	private static final String ENTITY_NAME = "cust_Authority";
	
	/**
	 * 仅获取有横向评价权限的用户id
	 * 		cust_matrixpermission eq 'Yes'
	 */
	private static final String EXTRA_PARAM_FILTER = "$filter=cust_matrixpermission eq 'Yes'";

	private static final String EXTRA_PARAM_SELECT = "$select=externalCode";
	

	
	private static int sync_limit = 10;

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	protected void cacheEntity(List<SAPAssessmentUser> entitys) {
		sapAssessmentUserDao.replace(entitys);
	}

	@Override
	protected SAPAssessmentUser toBo(JSONObject entityJson) {
		SAPAssessmentUser bo = new SAPAssessmentUser();
		bo.setUserId(entityJson.getString("externalCode"));
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
