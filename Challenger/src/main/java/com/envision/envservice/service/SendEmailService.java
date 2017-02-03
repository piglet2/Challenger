package com.envision.envservice.service;

import org.springframework.stereotype.Service;

/******************************************************************************
 * @File name   :      SendSMSSyncer.java
 *
 * @Author      :      xiaobo.qin
 *
 * @Date        :      2016年8月24日 下午2:23:31
 *
 * @Copyright Notice: 
 * Copyright (c) 2016 Envision, Inc. All  Rights Reserved.
 * This software is published under the terms of the Envision Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 *****************************************************************************/

@Service
public class SendEmailService {
	// 邮件发送地址
	private final static String SMS_ROUTE = "http://notice.envisioncn.com/api/sendmail";
	// 邮件模板
//	private final static String mes= "{\"to\":\"song.cai@envisioncn.com\",\"subject\":\"邮件推送\",\"content\":\"<i>only a 测试</i>\"}";

	public String sendEmail(String email,String msg,String subject){
		//发送 POST 请求
		String SMS_TEMPLATE="{\"to\":\""+email+"\",\"subject\":\""+subject+"\",\"content\":\""+msg+"\"}";
		String sr=HttpRequestService.sendPost(SMS_ROUTE,SMS_TEMPLATE);
        System.out.println("邮件推送："+sr);
        return sr;
	}
		

}
