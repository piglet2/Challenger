/******************************************************************************
 * @File name   :      SyncBase.java
 *
 * @Package 	:	   com.envision.envservice.sync
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-2 下午3:19:24
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
 * 2015-11-2 下午3:19:24    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.sync.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.MapConfigService;
import com.envision.envservice.service.SAPService;

/**
 * 挑战者定时任务基础类
 * 
 * @ClassName BaseClgSyncer
 * @author song.cai	
 * @date 2016-12-7
 */
public abstract class BaseClgSyncer {
	
	private static final Logger SYNC_SAP_LOGGER = EnvLog.getSAPSyncerLogger();
	
	@Autowired
	private SAPService sapService;
	
	@Autowired
	private SyncerConfig syncerConfig;
	
	@Autowired
	private MapConfigService mapConfigService;
	
	private static final int DEFAULT_SYNC_LIMIT = 100;
	
	private static int syncLimit;
	
	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
		syncLimit = mapConfigService.getIntValue("sync_limit", DEFAULT_SYNC_LIMIT);
		
		if (syncerConfig.isSyncOnStartup()) {
			sync();
		}
	}
	
	/**
	 * 同步SAP数据
	 */
	public void sync() {
		SYNC_SAP_LOGGER.info(this.getClass().getSimpleName() + ": Start synchronizing SAP data...");

		try {
			long startTime = System.currentTimeMillis();
			int syncCount = syncData();
			long syncTime = System.currentTimeMillis() - startTime;
			
			SYNC_SAP_LOGGER.info(this.getClass().getSimpleName() + ": Sync Data: " + syncCount + ", Sync Time: " + syncTime + "ms");
		} catch (Exception e) {
			SYNC_SAP_LOGGER.error(this.getClass().getSimpleName() + ": Sync Fail.", e);
		}
	}
	
	/**
	 * 转换SAP响应
	 * 		SAP.JSON --> SAP.Bo 
	 * @throws Exception 
	 */
	protected abstract int syncData() throws Exception;
	
}
