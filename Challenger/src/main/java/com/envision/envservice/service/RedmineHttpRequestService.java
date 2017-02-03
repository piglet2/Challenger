package com.envision.envservice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;

/**
 * @ClassName HttpRequest
 * @author caisong
 * @date 2016-5-20
 */


@Service
public class RedmineHttpRequestService {
	
	@Autowired
	private static HttpSession httpSession;
	
	private static final Logger logger = EnvLog.getLogger(UserDetailService.class);
	
	  /**
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            userId
     * @param api_key
     *            api_key
     * @return URL 所代表远程资源的响应结果
     * 
     */
    public static String sendGet(String url, String param,String api_key) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            connection.setRequestProperty("X-Redmine-API-Key",api_key);
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestMethod("GET");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
            	logger.debug(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	logger.warn( "发送GET请求出现异常！" + e.getMessage(), e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            	logger.warn("发送GET请求出现异常!!!" + e.getMessage(), e);
            }
        }
        return result;
    }
	
	
    /**
     * Redmine登陆接口
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            userId
     * @param username
     *            username
     * @param password
     *            password
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendLoginGet(String url, String param,String username,String password) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "/" + param+".json";
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            connection.setRequestProperty("Authorization", "Basic " + encode(username + ":"
            		+ password));
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
            	logger.debug(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	logger.warn("登陆转发到redmine的GET请求发送redmine出现异常！Exception：" + e.getMessage(),e);
            e.printStackTrace();
            return null;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                logger.warn("登陆转发到redmine的GET请求发送redmine出现异常!!!Exception：" + e.getMessage(),e);
                return null;
            }
        }
        return result;
    }

    
    
    /**
     * GET
     * @param url 请求地址
     * @param param 提交参数
     * @return api_key 请求验证参数
     */
     public  static String doGET(String url,String api_key) {
    	 CloseableHttpClient httpClient = HttpClients.createDefault();
 		HttpGet httpGet = new HttpGet(url);
 		httpGet.setHeader("X-Redmine-API-Key",api_key);
 		httpGet.setHeader("accept", "application/json");
 		httpGet.setHeader("connection", "Keep-Alive");
 		httpGet.setHeader("user-agent",
                 "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
 		httpGet.setHeader("Content-Type", "application/json; charset=utf-8");
 		try {
				HttpResponse response = httpClient.execute(httpGet);
				// 显示响应
				HttpEntity entity = response.getEntity();
				String content=EntityUtils.toString(entity);
				return content;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				 logger.warn("转发redmine的GET请求出现异常!!!ClientProtocolException" + e);
				 return null;
			} catch (IOException e) {
				e.printStackTrace();
				logger.warn("转发redmine的GET请求出现异常!!!IOException" + e);
				return null;
			}
     }
    
     /**
      * POST
      * @param url 请求地址
      * @param param 提交参数
      * @return api_key 请求验证参数
      */
      public  static String doPost(String url, String param,String api_key) {
      		CloseableHttpClient httpClient = HttpClients.createDefault();
      		HttpPost httpPost = new HttpPost(url);
      		httpPost.setHeader("X-Redmine-API-Key",api_key);
      		httpPost.setHeader("accept", "application/json");
      		httpPost.setHeader("connection", "Keep-Alive");
      		httpPost.setHeader("user-agent",
                      "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      		httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
      		try {
      			StringEntity paramEntity = new StringEntity(param);
      			httpPost.setEntity(paramEntity);
  				HttpResponse response = httpClient.execute(httpPost);
  				// 显示响应
  				HttpEntity entity = response.getEntity();
  				String content=EntityUtils.toString(entity);
  				return content;
  			} catch (ClientProtocolException e) {
  				e.printStackTrace();
  				logger.warn("转发redmine的POST请求出现异常!!!ClientProtocolException:" + e);
  				return null;
  			} catch (IOException e) {
  				e.printStackTrace();
  				logger.warn("转发redmine的请求出现异常!!!IOException:" + e);
  				return null;
  			}
      }
     
     
    /**
    * PUT
    * @param url 请求地址
    * @param param 提交参数
    * @return api_key 请求验证参数
    */
    public  static String doPut(String url, String param,String api_key) {
    		CloseableHttpClient httpClient = HttpClients.createDefault();
    		HttpPut httpPut = new HttpPut(url);
    		httpPut.setHeader("X-Redmine-API-Key",api_key);
    		httpPut.setHeader("accept", "application/json");
    		httpPut.setHeader("connection", "Keep-Alive");
    		httpPut.setHeader("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    		httpPut.setHeader("Content-Type", "application/json; charset=utf-8");
    		try {
    			StringEntity paramEntity = new StringEntity(param);
        		httpPut.setEntity(paramEntity);
				HttpResponse response = httpClient.execute(httpPut);
				// 显示响应
				HttpEntity entity = response.getEntity();
				String content=EntityUtils.toString(entity);
				return content;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				logger.warn("转发redmine的PUT请求出现异常!!!ClientProtocolException:" + e);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				logger.warn("转发redmine的发送PUT请求出现异常!!!IOException:" + e);
				return null;
			}
    }
    
    /*
     * Base64 加密
     * 
     * */
    @SuppressWarnings("restriction")
	public static String encode (String source) {
    	  BASE64Encoder enc = new sun.misc.BASE64Encoder();
    	  return(enc.encode(source.getBytes()));
    }
    
    /*
     * 中文转义
     * 
     * */
    public static String unescapeJS(String str) {      
        String result=StringEscapeUtils.unescapeJava(str);
        return result;      
    }  
}