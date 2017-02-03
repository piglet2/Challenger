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

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.MapConfigService;
import com.envision.envservice.service.SAPService;
import com.envision.envservice.service.SAPService.SAPResponse;
import com.envision.envservice.service.exception.SAPException;

/**
 * SAP数据同步基础类
 * 
 * @ClassName SyncBase
 * @author guowei.wang
 * @date 2015-11-2
 */
public abstract class BaseSyncer<T> {
	
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
	 * 同步SAP数据
	 * 		分批次查询SAP数据, 经转换后进行本地存储
	 */
	@Transactional(rollbackFor = Exception.class)
	private int syncData() throws Exception {
		int limit = syncLimit();
		int position = 0;
		int size = -1;
		while (true) {
			List<T> entitys = queryEntity(getEntityName(), position, limit);
			cacheEntity(entitys);
			
			size = entitys.size();
			position += size;

			if (size < limit) {
				break;
			}
		}
		
		return position;
	}
	
	/**
	 * 分页查询SAP Entity
	 */
	private List<T> queryEntity(String entityName, int start, int limit) throws Exception {
		List<T> entityList = new LinkedList<T>();
		
		SAPResponse sapResponse = sapService.queryEntity(entityName, null, limitParam(start, limit) + extraParam());
		if (sapResponse.getHttpStatus() == HttpStatus.OK) {
			JSONArray jsonEntitys = SAPUtil.getEntitys(sapResponse.getEntity());
			
			entityList.addAll(toBos(jsonEntitys));
		} else {
			throw new SAPException(sapResponse.getEntity().toJSONString());
		}
		
		return entityList;
	}
	
	/**
	 * 拼接SAP分页查询参数
	 */
	private String limitParam(int start, int limit) {
		StringBuilder buf = new StringBuilder();
		buf.append(SAPUtil.PARAM_SKIP + SAPUtil.PARAM_KV_CONNECTOR + start);
		buf.append(SAPUtil.PARAM_CONNECTOR);
		buf.append(SAPUtil.PARAM_TOP + SAPUtil.PARAM_KV_CONNECTOR + limit);
		
		return buf.toString();
	}
	
	/**
	 * To bos
	 */
	private List<T> toBos(JSONArray sapJsonEntitys) {
		List<T> lstT = new LinkedList<T>();
		for (Object entityObj : sapJsonEntitys) {
			JSONObject entityJson = (JSONObject) entityObj;
			
			lstT.add(toBo(entityJson));
		}
		
		return lstT;
	}
	
	/**
	 * 查询SAP时补充参数
	 * 		仅当需要增加过滤条件时重写该方法
	 */
	protected String extraParam() {
		return StringUtils.EMPTY;
	}
	
	/**
	 * 每次同步数据量
	 * 		如需不采用配置同步数据量可重写该方法
	 */
	protected int syncLimit() {
		return syncLimit;
	}
	
	/**
	 * 获取同步SAP实体名
	 */
	protected abstract String getEntityName();
	
	/**
	 * 缓存SAP实体
	 */
	protected abstract void cacheEntity(List<T> entitys);
	
	/**
	 * 转换SAP响应
	 * 		SAP.JSON --> SAP.Bo 
	 */
	protected abstract T toBo(JSONObject entityJson);
}
