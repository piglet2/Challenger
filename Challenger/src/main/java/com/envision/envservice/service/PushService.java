/******************************************************************************
 * @File name   :      UserCaseService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-5 上午10:11:11
 *
 * @Description : 	   
 *
 * @Copyright Notice: 
 * Copyright (c) 2016 Envision, Inc. All  Rights Reserved.
 * This software is published under the terms of the Envision Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * 
 * ----------------------------------------------------------------------------
 * Date                   		Who         Version        Comments
 * 2016-1-5 上午10:11:11    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.entity.bo.PushBo;
import com.envision.envservice.entity.dto.Assessment;
import com.envision.envservice.entity.dto.AssessmentCycles;
import com.envision.envservice.entity.dto.Evaluation;
import com.envision.envservice.entity.dto.EvaluationPeriod;
import com.envision.envservice.entity.dto.PushConfig;
import com.envision.envservice.entity.sap.SAPAssessmentUser;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.entity.sap.SAPUser;
import com.envision.envservice.push.AndroidNotification;
import com.envision.envservice.push.AndroidNotification.AfterOpenAction;
import com.envision.envservice.push.PushClient;
import com.envision.envservice.push.android.AndroidBroadcast;
import com.envision.envservice.push.android.AndroidCustomizedcast;
import com.envision.envservice.push.android.AndroidFilecast;
import com.envision.envservice.push.android.AndroidGroupcast;
import com.envision.envservice.push.android.AndroidUnicast;
import com.envision.envservice.push.ios.IOSBroadcast;
import com.envision.envservice.push.ios.IOSCustomizedcast;
import com.envision.envservice.push.ios.IOSFilecast;
import com.envision.envservice.push.ios.IOSGroupcast;
import com.envision.envservice.push.ios.IOSUnicast;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class PushService {
	
	@Autowired
	private MapConfigService mapConfigService;
	
	@Autowired
	private PushConfigService pcService;
	
	@Autowired
	private SAPEmpJobService empjobService;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private EvaluationService evaService;
	
	@Autowired
	private EvaluationPeriodService epService;
	
	@Autowired
	private AssessmentCyclesService acService;
	
	@Autowired
	private AssessmentService asService;
	
	@Autowired
	private SapAssessmentUserService sauService;
	
	
	private String appkey =null;
	private String appMasterSecret = null;
	private String timestamp = null;
	private PushClient client = new PushClient();
	
	/*
	 * 新增纵向评价周期时推送消息
	 * 提醒上级评分
	 * 
	 * */
	public void EvaluationPeriodByStart() throws Exception{
		EvaluationPeriod lastEP=epService.queryRecent();
		if(lastEP!=null){
			Date lastDate=lastEP.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			//离最新一期创建时间有多少天
			long d = l / (1000 * 60 * 60 * 24);
			System.out.println("d:"+d);
		    if(d>1&&d<3){
					List<PushConfig> pclist=pcService.queryAll();
					List<SAPEmpJob> list= empjobService.queryManager();
					String[] ids=new String[list.size()];
					for(int i=0;i<list.size();i++){
						ids[i]=list.get(i).getUserId();
					}
					List<SAPUser> userList=sapUserService.queryAllByIds(ids);
					List<PushConfig> managerList=new ArrayList<PushConfig>();
					//获取所有上级的手机token
					for(int i=0;i<pclist.size();i++){
						for(int j=0;j<userList.size();j++){
							if(pclist.get(i).getUsername()==userList.get(j).getUsername()||
									pclist.get(i).getUsername().equals(userList.get(j).getUsername())){
								managerList.add(pclist.get(i));
							}
						}
					}
					if(managerList!=null&&managerList.size()>0){
						for(int i=0;i<managerList.size();i++){
							PushBo pb=new PushBo();
							pb.setCode("001");
							pb.setDeviceToken(managerList.get(i).getDeviceToken());
							pb.setTitle("新一期远景精神评价开始啦，请准备好对下属的评价。");
							pb.setText("最新一期的纵向评价已开始，快去评价吧");
							sendMessageByAndroid(pb);
							sendMessageByIOS(pb);
						}	
					}
		    }
		}
	}
	
	/*
	 * 新增纵向评价周期时推送消息
	 * 周期末尾提醒上级评分
	 * 
	 * */
	public void EvaluationPeriodByEnd() throws Exception{
		EvaluationPeriod lastEP=epService.queryRecent();
		if(lastEP!=null){
			Date lastDate=lastEP.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			//离最新一期创建时间有多少天
			long d = l / (1000 * 60 * 60 * 24);
			System.out.println("d:"+d);
		    if(d>8){
		    	List<PushConfig> pclist=pcService.queryAll();
				List<SAPEmpJob> list= empjobService.queryManager();
				String[] ids=new String[list.size()];
				for(int i=0;i<list.size();i++){
					ids[i]=list.get(i).getUserId();
				}
				List<SAPUser> userList=sapUserService.queryAllByIds(ids);
				List<PushConfig> managerList=new ArrayList<PushConfig>();
				//获取所有上级的手机token
				for(int i=0;i<pclist.size();i++){
					for(int j=0;j<userList.size();j++){
						if(pclist.get(i).getUsername()==userList.get(j).getUsername()||
								pclist.get(i).getUsername().equals(userList.get(j).getUsername())){
							managerList.add(pclist.get(i));
						}
					}
				}
				//查看最新一期纵向评价中，上级是否已经评论完
				EvaluationPeriod ep=epService.queryLastOne();
				List<Evaluation> evalist=evaService.queryByPeriodId(ep.getPeriodId());
				List<String>  Periodlist=new ArrayList<String>();
				for(int i=0;i<evalist.size();i++){
					//上级是否已经评价过所有下级
					String mId=evalist.get(i).getManagerId();
					List<SAPEmpJob> listse=empjobService.queryByManagerId(mId);
					List<SAPUser> listsu=new ArrayList<SAPUser>();
					for(int j=0;j<listse.size();j++){
						SAPUser su=sapUserService.queryById(listse.get(j).getUserId());
						if(su.getCustom06()=="正式员工"||"正式员工".equals(su.getCustom06())){
							listsu.add(su);
						}
					}
					List<Evaluation> ellist=evaService.queryByPeriodIdAndManagerId(ep.getPeriodId(),mId);
//					System.out.println("上级："+mId+"正式员工下属为"+listsu.size()+"人");
//					System.out.println("上级："+mId+"已纵向评价过"+ellist.size()+"人");
					if(ellist.size()==listsu.size()){
						Periodlist.add(mId);
					}
				}
				for(int i=0;i<managerList.size();i++){
					for(int j=0;j<Periodlist.size();j++){
						SAPUser user=sapUserService.queryById(Periodlist.get(j));
						if(user.getUsername()==managerList.get(i).getUsername()||
								user.getUsername().equals(managerList.get(i).getUsername())){
							managerList.remove(i);
						}
					}
				}
				for(int i=0;i<managerList.size();i++){
					PushBo pb=new PushBo();
					pb.setCode("001");
					pb.setDeviceToken(managerList.get(i).getDeviceToken());
					pb.setTitle("请不要忘记对下属进行远景精神评价哦。");
					pb.setText("最新一期的纵向评价快要结束了，快去评价吧。");
					sendMessageByAndroid(pb);
					sendMessageByIOS(pb);
				}
		    }
		}
		
	}
	
	/*
	 * 新增纵向评价
	 * 提醒下级查看评价
	 * 
	 * */
	public void addEvaluation(String userId) throws Exception{
		SAPUser  user=sapUserService.queryById(userId);
		List<PushConfig> list=pcService.queryByUsername(user.getUsername());
		if(list!=null&&list.size()>0){
			PushConfig pc=list.get(0);
			PushBo pb=new PushBo();
			pb.setCode("002");
			pb.setDeviceToken(pc.getDeviceToken());
			pb.setTitle("你收到了一条新的远景精神评价，请查看。");
			pb.setText("去看看你最新的纵向评价吧");
			sendMessageByAndroid(pb);
			sendMessageByIOS(pb);
		}
		
	}
	
	/*
	 * 新增横向周期时推送消息
	 * 横向评价周期开始
	 * 提醒评分
	 * 
	 * */
	public void AssessmentCyclesByStart() throws Exception{
		System.out.println("3");
		AssessmentCycles ac=acService.queryRecent();
		if(ac!=null){
			Date lastDate=ac.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			//离最新一期创建时间有多少天
			long d = l / (1000 * 60 * 60 * 24);
			System.out.println("d:"+d);
		    if(d>1&&d<3){
					List<PushConfig> pclist=pcService.queryAll();
					List<SAPAssessmentUser> list= sauService.queryAll();
					String[] ids=new String[list.size()];
					for(int i=0;i<list.size();i++){
						ids[i]=list.get(i).getUserId();
					}
					List<SAPUser> userList=sapUserService.queryAllByIds(ids);
					List<PushConfig> managerList=new ArrayList<PushConfig>();
					//获取所有上级的手机token
					for(int i=0;i<pclist.size();i++){
						for(int j=0;j<userList.size();j++){
							if(pclist.get(i).getUsername()==userList.get(j).getUsername()||
									pclist.get(i).getUsername().equals(userList.get(j).getUsername())){
								managerList.add(pclist.get(i));
							}
						}
					}
					if(managerList!=null&&managerList.size()>0){
						for(int i=0;i<managerList.size();i++){
							PushBo pb=new PushBo();
							pb.setCode("001");
							pb.setDeviceToken(managerList.get(i).getDeviceToken());
							pb.setTitle("新一期远景精神评价开始啦，请准备好对本月有工作交集的同事的评价。");
							pb.setText("最新一期的横向评价已开始，快去评价吧。");
							sendMessageByAndroid(pb);
							sendMessageByIOS(pb);
						}
					}
		    }
		}
	}
	
	
	/*
	 * 新增横向周期时推送消息
	 * 横向后期结束
	 * 消息推送
	 * 
	 * */
	public void AssessmentCyclesByEnd() throws Exception{
		System.out.println("4");
		AssessmentCycles ac=acService.queryRecent();
		if(ac!=null){
			Date lastDate=ac.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			//离最新一期创建时间有多少天
			long d = l / (1000 * 60 * 60 * 24);
			System.out.println("d:"+d);
		    if(d>23){
		    	List<PushConfig> pclist=pcService.queryAll();
		    	List<SAPAssessmentUser> list= sauService.queryAll();
		    	List<Assessment> asList=asService.queryAssessmentByCycleId(ac.getCycleId());
		    	for(int i=0;i<list.size();i++){
		    		for(int j=0;j<asList.size();j++){
		    			if(list.get(i).getUserId()==asList.get(j).getCriticId()||
		    					list.get(i).getUserId().equals(asList.get(j).getCriticId())){
		    				list.remove(i);
		    			}
		    		}
		    	}
				String[] ids=new String[list.size()];
				for(int i=0;i<list.size();i++){
					ids[i]=list.get(i).getUserId();
				}
				List<SAPUser> userList=sapUserService.queryAllByIds(ids);
				List<PushConfig> managerList=new ArrayList<PushConfig>();
				//获取所有上级的手机token
				for(int i=0;i<pclist.size();i++){
					for(int j=0;j<userList.size();j++){
						if(pclist.get(i).getUsername()==userList.get(j).getUsername()||
								pclist.get(i).getUsername().equals(userList.get(j).getUsername())){
							managerList.add(pclist.get(i));
						}
					}
				}
				if(managerList!=null&&managerList.size()>0){
					for(int i=0;i<managerList.size();i++){
						PushBo pb=new PushBo();
						pb.setCode("001");
						pb.setDeviceToken(managerList.get(i).getDeviceToken());
						pb.setTitle("本期远景精神评价快要结束了，请尽快对需要评价的同事进行评价。");
						pb.setText("最新一期的横向评价快结束了，快去评价吧。");
						sendMessageByAndroid(pb);
						sendMessageByIOS(pb);
					}
				}
		    }
		}
	}
	
	
	/*
	 * 新增横向评价
	 * 提醒被评价人查看评价
	 * 
	 * */
	public void addAssessment(String userId) throws Exception{
		SAPUser  user=sapUserService.queryById(userId);
		if(user!=null){
			List<PushConfig> list=pcService.queryByUsername(user.getUsername());
			if(list!=null&&list.size()>0){
				PushConfig pc=list.get(0);
				PushBo pb=new PushBo();
				pb.setCode("002");
				pb.setDeviceToken(pc.getDeviceToken());
				pb.setTitle("你收到了一条新的远景精神评价，请查看。");
				pb.setText("去看看你最新的横向评价吧");
				sendMessageByAndroid(pb);
				sendMessageByIOS(pb);
			}
		}
	}
	
	/*
	 * 新增横向评价
	 * 提醒被评价人上级查看评价
	 * 
	 * */
	public void addAssessmentToManager(String criticId,String userId) throws Exception{
		SAPEmpJob  sapEmpJob=empjobService.queryEMPJobById(userId);
		if(sapEmpJob!=null){
			String managerId=sapEmpJob.getManagerId();
			//评价人
			SAPUser critic=sapUserService.queryById(criticId);
			//下属
			SAPUser user=sapUserService.queryById(userId);
			//上级
			SAPUser Manager=sapUserService.queryById(managerId);
			List<PushConfig> list=pcService.queryByUsername(Manager.getUsername());
			if(list!=null&&list.size()>0){
				PushConfig pc=list.get(0);
				PushBo pb=new PushBo();
				pb.setCode("002");
				pb.setDeviceToken(pc.getDeviceToken());
				pb.setTitle("你收到了一条新的远景精神评价，请查看。");
				pb.setText("【挑战者app】温馨提示："+critic.getLastName()+" (横向主管姓名)刚对"+user.getLastName()+"（下属姓名）进行了横向反馈，请您点击此消息，以查看详情。");
				sendMessageByAndroid(pb);
				sendMessageByIOS(pb);
			}
		}
	}
	
	
	/*
	 * 安卓消息个推
	 * 跳转到评价界面
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map sendMessageByAndroid(PushBo pb) throws Exception{
		appkey = mapConfigService.getValue("Android_appkey");
		appMasterSecret = mapConfigService.getValue("Android_appMasterSecret");
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		unicast.setDeviceToken(pb.getDeviceToken());
		unicast.setTicker("挑战者消息推送");
		unicast.setTitle(pb.getTitle());
		unicast.setText(pb.getText());
		unicast.goAppAfterOpen();
		unicast.setAfterOpenAction(AfterOpenAction.go_custom);
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		unicast.setProductionMode();
		unicast.setExtraField("test", "helloworld");
		unicast.setCustomField(pb.getCode());
		client.send(unicast);
		Map map=new HashMap();
		map.put("result", "发送成功");
		return map;
	}
	
	/*
	 * 安卓消息个推
	 * 打开APP
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map sendMessageByAndroidToOpenApp(PushBo pb) throws Exception{
		appkey = mapConfigService.getValue("Android_appkey");
		appMasterSecret = mapConfigService.getValue("Android_appMasterSecret");
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		unicast.setDeviceToken(pb.getDeviceToken());
		unicast.setTicker("挑战者消息推送");
		unicast.setTitle(pb.getTitle());
		unicast.setText(pb.getText());
		unicast.goAppAfterOpen();
		unicast.setAfterOpenAction(AfterOpenAction.go_app);
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		unicast.setProductionMode();
		unicast.setExtraField("test", "helloworld");
		unicast.setCustomField(pb.getCode());
		client.send(unicast);
		Map map=new HashMap();
		map.put("result", "发送成功");
		return map;
	}
	
	
	/*
	 * IOS消息个推
	 * 跳转到评价界面
	 * */
	public void sendMessageByIOS(PushBo pb) throws Exception {
		appkey = mapConfigService.getValue("IOS_appkey");
		appMasterSecret = mapConfigService.getValue("IOS_appMasterSecret");
		IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
		unicast.setDeviceToken(pb.getDeviceToken());
		unicast.setDescription(pb.getText());
		unicast.setAlert(pb.getTitle());
		unicast.setBadge(0);
		unicast.setSound("1");
		unicast.setProductionMode();
		unicast.setCustomizedField("custom", pb.getCode());
		client.send(unicast);
	}
	
	/*
	 * IOS消息个推
	 * 打开APP
	 * 
	 * */
	public void sendMessageByIOSToOpenApp(PushBo pb) throws Exception {
		appkey = mapConfigService.getValue("IOS_appkey");
		appMasterSecret = mapConfigService.getValue("IOS_appMasterSecret");
		IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
		unicast.setDeviceToken(pb.getDeviceToken());
		unicast.setDescription(pb.getText());
		unicast.setAlert(pb.getTitle());
		unicast.setBadge(0);
		unicast.setSound("1");
		unicast.setProductionMode();
		client.send(unicast);
	}
	
	
	
	 
   
   
	
	
	
	
	
	
	
	public void sendAndroidBroadcast() throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
		broadcast.setTicker( "挑战者消息推送");
		broadcast.setTitle(  "中文的title");
		broadcast.setText(   "Android broadcast text");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("test", "helloworld");
		client.send(broadcast);
	}
	

	
	public void sendAndroidGroupcast() throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"test"},
      	 *			{"tag":"Test"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		groupcast.setFilter(filterJson);
		groupcast.setTicker( "Android groupcast ticker");
		groupcast.setTitle(  "中文的title");
		groupcast.setText(   "Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}
	
	public void sendAndroidCustomizedcast() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidCustomizedcastFile() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb"+"\n"+"alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidFilecast() throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey,appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setTicker( "Android filecast ticker");
		filecast.setTitle(  "中文的title");
		filecast.setText(   "Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}
	
	public void sendIOSBroadcast() throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge( 0);
		broadcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}
	
	public void sendIOSUnicast() throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( "xx");
		unicast.setAlert("IOS 单播测试");
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setTestMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}
	
	public void sendIOSGroupcast() throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"iostest"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge( 0);
		groupcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		groupcast.setTestMode();
		client.send(groupcast);
	}
	
	public void sendIOSCustomizedcast() throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge( 0);
		customizedcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		customizedcast.setTestMode();
		client.send(customizedcast);
	}
	
	public void sendIOSFilecast() throws Exception {
		IOSFilecast filecast = new IOSFilecast(appkey,appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge( 0);
		filecast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		filecast.setTestMode();
		client.send(filecast);
	}
	
}
