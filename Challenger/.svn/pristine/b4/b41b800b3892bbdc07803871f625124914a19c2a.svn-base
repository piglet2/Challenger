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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.common.Constants;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.MapConfigService;
import com.envision.envservice.sync.base.BaseClgSyncer;

/**
 * SAP Photo 同步器
 * 
 * @ClassName MTQSyncer
 * @author song.cai	
 * @date 2016-07-12
 */
@Component("mtqSyncer")
public class MTQSyncer extends BaseClgSyncer{
	
	private static final Logger SYNC_MTQ_LOGGER = EnvLog.getMTQSyncerLogger();
	
	@Autowired
	private MapConfigService mapConfigService;
	
	@Override
	public int syncData() throws MalformedURLException, SmbException{
	  	String remoteUrl=mapConfigService.getValue("MTQ_URL");
        String localDir=Constants.PIC_BASE+Constants.MTQ_PIC_PATH;
        smbGet(remoteUrl,localDir);
		return 0;
	}
	
    /** 
     * 从共享目录下载文件 存到本地 
     * @param remoteUrl 
     *            远程路径 
     * @param localDir 
     *            要写入的本地路径 
     */  
	public static void smbGet(String remoteUrl, String localDir) {  
        InputStream in = null;  
        OutputStream out = null;
        try {  
            SmbFile smbFile = new SmbFile(remoteUrl);  
            if(smbFile.exists()){
            	SmbFile[] files = smbFile.listFiles();
            	 for (SmbFile  remoteSmbFile : files) {
            		 String remoteSmbFileName = remoteSmbFile.getName();
            		 File localFile = new File(localDir+File.separator+remoteSmbFileName);
            		 File local = new File(localDir);
    			     if (!localFile.exists()) {
    			    	 local.mkdir();
    			    	 localFile.createNewFile();
    			     }
            		 in= new BufferedInputStream(new SmbFileInputStream(remoteSmbFile));
            		 out = new BufferedOutputStream(new FileOutputStream(localFile));
            		 byte[] buffer = new byte[1024];
	                     while (in.read(buffer) != -1){
	                         out.write(buffer);
	                     }
	                     out.close();  
                         in.close();  
                 }
            }else{
            	SYNC_MTQ_LOGGER.warn("MTQ共享文件不存在："+remoteUrl);
                return;
            }
        } catch (Exception e) {  
            SYNC_MTQ_LOGGER.error(e.getMessage()+":"+e);
        } 
	}
}
