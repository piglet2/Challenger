package com.envision.envservice.service;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.util.Base64Utils;
import com.envision.envservice.common.util.PicUtil;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;

/**
 * @ClassName HttpRequest
 * @author caisong
 * @date 2016-5-20
 */


@Service
public class PicRequestService {
	
	@Autowired
	private static HttpSession httpSession;
	
	private static final Logger logger = EnvLog.getLogger(UserDetailService.class);
	
	
	 /**
     * 获取图片
     * @param userId 员工id
	 * @throws IOException 
     */
     public  static String queryPic(String userId) throws IOException {
    	 String opt="/opt/";
    	 String picurl=PicUtil.getPicPath(userId);
    	 StringBuffer sb=new StringBuffer();
    	 sb.append(opt);
    	 sb.append(picurl);
    	 String url=sb.toString();
    	 byte[] picByte=PicUtil.readPic(url);
    	 String pic=Base64Utils.encode(picByte);
    	 return pic;
     }
	
}