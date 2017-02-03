/******************************************************************************
 * @File name   :      SAPCallService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-19 下午2:33:21
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
 * 2015-10-19 下午2:33:21    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.util.Base64Utils;
import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;

/**
 * SAP访问Service
 * 
 * @ClassName SAPCallService
 * @author guowei.wang
 * @date 2015-10-19
 */
@Service
public class SAPService {
	
	private static final Logger logger = EnvLog.getSAPQueryLogger();
	
	@Autowired
	private MapConfigService mapConfigService;
	
	private static String SAP_ODATA_API_URL;
	
	private static String SAP_ODATA_API_USERNAME;
	
	private static String SAP_ODATA_API_PASSWORD;
	
	private static final String AUTH_BASIC = "Basic ";

	private static final String AUTHORIZATION = "Authorization";
	
	private static final String MARK_LOG = "SAPQuery: ";

	@PostConstruct
	@SuppressWarnings("unused")
	private void initService() {
		SAP_ODATA_API_URL = mapConfigService.getValue("sap_odata_api_url");
		SAP_ODATA_API_USERNAME = mapConfigService.getValue("sap_odata_api_username");
		SAP_ODATA_API_PASSWORD = mapConfigService.getValue("sap_odata_api_password");
	}
	
	/**
	 * 访问SAP查询实体
	 * 
	 * @Title: queryEntity
	 * @param entityName 实体名
	 * @param entityId 实体ID
	 * @param params 参数串
	 * @return SAPResponse
	 * @throws Exception
	 * @Date 2015-10-19
	 */
	public SAPResponse queryEntity(String entityName, String entityId, String params) throws Exception {
		return callSAP(spliceUrl(entityName, entityId, params));
	}

	public SAPResponse queryEntity(SAPQuery sapQuery) throws Exception {
		Objects.requireNonNull(sapQuery);
		
		SAPQuery[] sapQuerys = new SAPQuery[1];
		sapQuerys[0] = sapQuery;
		
		return callSAP(spliceUrl(sapQuerys))[0];
	}

	public SAPResponse[] queryEntity(SAPQuery[] sapQuerys) throws Exception {
		Objects.requireNonNull(sapQuerys);
		
		return callSAP(spliceUrl(sapQuerys));
	}
	
	/**
	 * Call SAP.
	 */
	private SAPResponse callSAP(String url) throws Exception {
		log(url);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpResponse response = httpClient.execute(buildHttpGet(url));

		return buildSAPResponse(response);
	}
	
	private SAPResponse[] callSAP(String[] urls) throws Exception {
		log(urls);
		
		SAPResponse[] lstSAPResponse = new SAPResponse[urls.length];
		
		CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault();
		httpAsyncClient.start();
		
		List<Future<HttpResponse>> lstFuture = new LinkedList<Future<HttpResponse>>();
		for (int i = 0; i < urls.length; i++) {
			lstFuture.add(httpAsyncClient.execute(buildHttpGet(urls[i]), null));
		}
		
		for (int i = 0; i < lstFuture.size(); i++) {
			lstSAPResponse[i] = buildSAPResponse(lstFuture.get(i).get()); 
		}
		
		httpAsyncClient.close();
		
		return lstSAPResponse;
	}
	
	private HttpGet buildHttpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader(AUTHORIZATION, generateAuth());
		
		return httpGet;
	}
	
	private SAPResponse buildSAPResponse(HttpResponse response) throws Exception {
		int statusCode = response.getStatusLine().getStatusCode();
		String content = IOUtils.toString(response.getEntity().getContent());
		
		return new SAPResponse(HttpStatus.valueOf(statusCode), content);
	}

	/**
	 * 生产身份验证码
	 */
	private String generateAuth() {
		return AUTH_BASIC + Base64Utils.encode(SAP_ODATA_API_USERNAME + ":" + SAP_ODATA_API_PASSWORD);
	}

	/**
	 * 拼接SAP完整请求路径
	 */
	private String spliceUrl(String entityName, String entityId, String params) {
		if (StringUtils.isEmpty(entityName)) {
			throw new NullPointerException("Field 'entityName' is Null");
		}

		StringBuilder buf = new StringBuilder();
		buf.append(SAP_ODATA_API_URL);
		buf.append(entityName);
		if (entityId != null) {
			buf.append("('" + entityId + "')");
		}

		buf.append(SAPUtil.PARAM_SEPARATOR);
		if (params != null) {
			buf.append(params);
			buf.append(SAPUtil.PARAM_CONNECTOR);
		}

		buf.append(SAPUtil.PARAM_FORMAT + SAPUtil.PARAM_KV_CONNECTOR + SAPUtil.DATA_FORMAT);

		return replaceSpace(buf.toString());
	}
	
	private String[] spliceUrl(SAPQuery[] sapQuerys) {
		String[] urls = new String[sapQuerys.length];
		for (int i = 0; i < sapQuerys.length; i++) {
			SAPQuery sapQuery = sapQuerys[i];
			
			urls[i] = spliceUrl(sapQuery.entityName, sapQuery.entityId, sapQuery.params);
		}
		
		return urls;
	}
	
	/**
	 * 空格替换为%20
	 */
	private static String replaceSpace(String url) {
		return url.replaceAll(" ", "%20");
	}
	
	private static void log(String... urls) {
		for (String url : urls) {
			log(url);
		}
	}

	private static void log(String url) {
		logger.info(MARK_LOG + url);
	}
	
	/**
	 * SAP响应消息
	 */
	public static class SAPResponse {
		
		private HttpStatus httpStatus;
		
		private JSONObject entity;

		public HttpStatus getHttpStatus() {
			return httpStatus;
		}

		public void setHttpStatus(HttpStatus httpStatus) {
			this.httpStatus = httpStatus;
		}

		public JSONObject getEntity() {
			return entity;
		}

		public void setEntity(JSONObject entity) {
			this.entity = entity;
		}

		public SAPResponse(HttpStatus httpStatus, String entity) {
			super();
			this.httpStatus = httpStatus;
			this.entity = JSONObject.parseObject(entity);
		}
	}
	
	/**
	 * SAPQuery
	 */
	public static class SAPQuery {
		
		private String entityName;
		
		private String entityId;
		
		private String params;
		
		private SAPQuery(String entityName, String entityId, String params) {
			super();
			this.entityName = entityName;
			this.entityId = entityId;
			this.params = params;
		}
		
		public static SAPQuery newInstance(String entityName, String entityId, String params) {
			return new SAPQuery(entityName, entityId, params);
		}

		public String getEntityName() {
			return entityName;
		}

		public void setEntityName(String entityName) {
			this.entityName = entityName;
		}

		public String getEntityId() {
			return entityId;
		}

		public void setEntityId(String entityId) {
			this.entityId = entityId;
		}

		public String getParams() {
			return params;
		}

		public void setParams(String params) {
			this.params = params;
		}
	}
}
