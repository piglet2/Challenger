package com.envision.envservice.service;
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

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SendSMSService {
	// 短信发送地址
	private final static String SMS_ROUTE = "http://notice.envisioncn.com/api/message";
	// 短信模板
	private final static String SMS_TEMPLATE = "{\"users\":[\"%s\"],\"msg\":\"%s\"}";
	// 参数编码
	private final static String encoding = "UTF-8";
	// Content-Type
	private final static String CONTENT_TYPE = "application/json";

	/*
	 * 三种使用方法
	 */ 
	public static void main(String[] args) throws Exception {
		// 发送单条
		 sendMoblieMessage("挑战者短信推送测试","15121051049");
		// 发送不定长数量的手机号码
		// sendMoblieMessage("秦小波发送多条OA" +new
		// Random().nextDouble(),"13801614264","13788943218");
		// 发送Lists
		//List<String> phones = Arrays.asList("13801614264", "13788943218");
		//sendMoblieMessage("秦小波发送多条OA,通过List方式", phones);
	}

	// 重载1：通过List发送短信
	public static void sendMoblieMessage(String msg, List<String> phones) {
		sendMoblieMessage(msg, phones.toArray(new String[phones.size()]));
	}

	// 重载2：通过变长参数发送短信
	public static void sendMoblieMessage(String msg, String... phones) {
		// 入口判断
		List<String> phoneList = new ArrayList<String>();
		for (String phone : phones) {
			if (phone != null && phone.length() == 11) {
				phoneList.add("86" + phone);
			}
		}
		try {
			sendMsg(msg, phoneList.toArray(new String[phoneList.size()]));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void sendMsg(String msg, String... phones) throws Exception {
		byte[] params = String.format(SMS_TEMPLATE, joinString("\",\"", phones), msg).getBytes(encoding);
		URL url = new URL(SMS_ROUTE);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", CONTENT_TYPE);
		conn.setRequestProperty("Content-Length", String.valueOf(params.length));
		// 5秒超时
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		OutputStream outStream = conn.getOutputStream();
		outStream.write(params);
		outStream.flush();
		outStream.close();
		conn.getResponseCode();

	}

	/*
	 * Stirng.join的实现，为兼容JDK 1.8以下版本
	 */
	private static String joinString(String delimiter, String... strs) {
		String result = "";
		for (int i = 0; i < strs.length; i++) {
			result = result +  (i != 0? delimiter : "") + strs[i];
		}		
		return result;
	}

}
