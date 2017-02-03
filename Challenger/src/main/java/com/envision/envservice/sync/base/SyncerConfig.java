/******************************************************************************
 * @File name   :      SyncerConfig.java
 *
 * @Package 	:	   com.envision.envservice.sync
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-3 下午1:58:46
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
 * 2015-11-3 下午1:58:46    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.sync.base;


/**
 * @ClassName SyncerConfig
 * @author guowei.wang
 * @date 2015-11-3
 */
public class SyncerConfig {

	/**
	 * 默认初始化同步配置
	 */
	private static final boolean DEFAULT_SYNC_ON_STARTUP = true;
	
	/**
	 * 是否开启初始化同步
	 */
	private boolean syncOnStartup = DEFAULT_SYNC_ON_STARTUP;

	public boolean isSyncOnStartup() {
		return syncOnStartup;
	}

	public void setSyncOnStartup(boolean syncOnStartup) {
		this.syncOnStartup = syncOnStartup;
	}
}
