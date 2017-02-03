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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.EvaluationRateDao;
import com.envision.envservice.entity.bo.EvaluationRateBo;
import com.envision.envservice.entity.dto.Assessment;
import com.envision.envservice.entity.dto.AssessmentCycles;
import com.envision.envservice.entity.dto.Evaluation;
import com.envision.envservice.entity.dto.EvaluationPeriod;
import com.envision.envservice.entity.dto.EvaluationRate;
import com.envision.envservice.entity.dto.NotEvaluationUser;
import com.envision.envservice.entity.sap.SAPAssessmentUser;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.entity.sap.SAPUser;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class EvaluationRateService {
	
	@Autowired
	private EvaluationPeriodService evaluationPeriodService;
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private EvaluationRateDao evaluationRateDao;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private DomesticLocationService domesticLocationService;
	
	@Autowired
	private AssessmentCyclesService acService;
	
	@Autowired
	private AssessmentService asService;
	
	@Autowired
	private SendSMSService sendSmsService;
	
	@Autowired
	private NotEvaluationUserService notEvaluationUserService;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private SapAssessmentUserService sapAssessmentUserService;
	/*
	 * 新增所有纵向主管每一期的完成率
	 * 
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map addRate(){
		int success=0;
		int fail=0;
		//查询所有周期
		List<EvaluationPeriod> evaluationPeriodList=evaluationPeriodService.queryAll();
		//查询所有纵向主管
		List<SAPEmpJob> managerList=sapEmpJobService.queryManager();
		for(int i=0;i<evaluationPeriodList.size();i++){
			for(int j=0;j<managerList.size();j++){
				//对上级主管进行过滤
				if(filterManager(managerList.get(j))){
					String periodId=evaluationPeriodList.get(i).getPeriodId();
					String managerId=managerList.get(j).getUserId();
					int rate=0;
					//查询所有下级
					List<SAPEmpJob> userList=sapEmpJobService.queryByManagerId(managerId);
					//对下级进行过滤
					for(int z=0;z<userList.size();z++){
						if(filterUser(userList.get(z))){
							continue;
						}else{
							userList.remove(z);
							z--;
						}
					}
					//查询评价列表
					List<Evaluation> evaList=evaluationService.queryByPeriodIdAndManagerId(periodId,managerId);
					EvaluationRate evaluationRate=new EvaluationRate();
					evaluationRate.setPeriodId(periodId);
					evaluationRate.setManagerId(managerId);
					if(userList!=null&&userList.size()>0&&evaList!=null&&evaList.size()>0){
						if(userList.size()==evaList.size()||evaList.size()>userList.size()){
							rate=1;
						}
					}
					evaluationRate.setRate(rate);
					//删除已有完成率
					EvaluationRate er=queryByPeriodIdAndManagerId(periodId,managerId);
					if(er!=null){
						int del=deleteByPeriodIdAndManagerId(periodId,managerId);
						if(del<1){
							System.out.println(periodId+"期纵向评价已有的完成率删除失败 ");
						}
					}
					int result=evaluationRateDao.addEvaluationRate(evaluationRate);
					if(result>0){
						success++;
					}else{
						fail++;
					}
					System.out.println("新增完成率，纵向主管:"+managerId+"效验成功，success:"+success);
				}else{
					System.out.println("纵向主管:"+managerList.get(j).getUserId()+"效验失败");
					managerList.remove(j);
					j--;
				}
			}
			
		}
		Map map=new HashMap();
		map.put("success", success);
		map.put("fail", fail);
		return map;
	}
	
	/*
	 * 修改所有纵向主管最新一期的完成率
	 * 
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map updateRecentRate(){
		int success=0;
		int fail=0;
		//查询最新一期纵向评价
		EvaluationPeriod ep=evaluationPeriodService.queryRecent();
		//查询所有纵向主管
		List<SAPEmpJob> managerList=sapEmpJobService.queryManager();
		for(int j=0;j<managerList.size();j++){
			//对上级主管进行过滤
			if(filterManager(managerList.get(j))){
				String periodId=ep.getPeriodId();
				String managerId=managerList.get(j).getUserId();
				int rate=0;
				//查询所有下级
				List<SAPEmpJob> userList=sapEmpJobService.queryByManagerId(managerId);
				//对下级进行过滤
				for(int z=0;z<userList.size();z++){
					if(filterUser(userList.get(z))){
						continue;
					}else{
						userList.remove(z);
						z--;
					}
				}
				//查询评价列表
				List<Evaluation> evaList=evaluationService.queryByPeriodIdAndManagerId(periodId,managerId);
				EvaluationRate evaluationRate=new EvaluationRate();
				evaluationRate.setPeriodId(periodId);
				evaluationRate.setManagerId(managerId);
				if(userList!=null&&userList.size()>0&&evaList!=null&&evaList.size()>0){
					if(userList.size()==evaList.size()||evaList.size()>userList.size()){
						rate=1;
					}
				}
				evaluationRate.setRate(rate);
				//删除已有完成率
				EvaluationRate er=queryByPeriodIdAndManagerId(periodId,managerId);
				if(er!=null){
					int del=deleteByPeriodIdAndManagerId(periodId,managerId);
					if(del<1){
						System.out.println("最新一期纵向评价已有的完成率删除失败 ");
					}
				}
				
				int result=evaluationRateDao.addEvaluationRate(evaluationRate);
				if(result>0){
					success++;
				}else{
					fail++;
				}
			}else{
				System.out.println("纵向主管:"+managerList.get(j).getUserId()+"效验失败");
				managerList.remove(j);
				j--;
			}
		}
			
		Map map=new HashMap();
		map.put("success", success);
		map.put("fail", fail);
		return map;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EvaluationRate queryByPeriodIdAndManagerId(String periodId,String managerId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		map.put("managerId", managerId);
		EvaluationRate er=evaluationRateDao.queryByPeriodIdAndManagerId(map);
		
		return er;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int deleteByPeriodIdAndManagerId(String periodId,String managerId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		map.put("managerId", managerId);
		int result=evaluationRateDao.deleteByPeriodIdAndManagerId(map);
		
		return result;
	}
	
	//查询最近6期的完成率，不包括最新一期
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EvaluationRate> queryByManagerIdAndPeriodIdLimitSix(String managerId){
		List<EvaluationPeriod> eplist=evaluationPeriodService.queryByEvaluationRate();
		EvaluationPeriod ep=eplist.get(eplist.size()-1);
		String periodId=ep.getPeriodId();
		Map map=new HashMap();
		map.put("managerId", managerId);
		map.put("periodId", periodId);
		List<EvaluationRate> list=evaluationRateDao.queryByManagerIdAndPeriodIdLimitSix(map);
		return list;
	}
	
	
	 //计算完成率
    public int getRate(String managerId){
    	//最近6期的完成率
    	List<EvaluationRate> list=queryByManagerIdAndPeriodIdLimitSix(managerId);
    	int result=0;
    	if(list!=null&&list.size()>0){
    		int evaNum=0;
        	int total=list.size();
        	for(int i=0;i<list.size();i++){
        		EvaluationRate er=list.get(i);
        		evaNum=evaNum+er.getRate();
        	}
        	float rate=(float)evaNum/(float)total;
        	DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        	double db=Double.parseDouble(df.format(rate));
        	double re=db*100;
        	result=(int)re;
    	}
    	return result;
    }
	
    //计算所有纵向主管完成率
    public int queryRateRank(String managerId){
    	int rank=0;
    	List<SAPEmpJob> list=sapEmpJobService.queryManager();
    	List<EvaluationRateBo>  rateList=new ArrayList<EvaluationRateBo>();
    	System.out.println("1");
    	//过滤纵向主管
    	for(int i=0;i<list.size();i++){
    		boolean flag=filterManager(list.get(i));
    		if(!flag){
    			list.remove(i);
    			i--;
    		}
    	}
    	System.out.println("2");
    	//计算每个主管的完成率
    	for(int i=0;i<list.size();i++){
    		String mId=list.get(i).getUserId();
    		int rt=getRate(mId);
			EvaluationRateBo bo=new EvaluationRateBo();
    		bo.setManagerId(mId);
			bo.setRate(rt);
    		rateList.add(bo);
    	}
    	System.out.println("3");
    	//对完成率进行降序排序
    	Collections.sort(rateList, new Comparator<EvaluationRateBo>() {
            public int compare(EvaluationRateBo o1, EvaluationRateBo o2) {
                return o2.getRate().compareTo(o1.getRate());
            }
        });
    	System.out.println("4");
    	//计算每个主管的完成率排名
    	for(int i=0;i<rateList.size();i++){
    		if(rateList.get(i).getManagerId()==managerId||
    				managerId.equals(rateList.get(i).getManagerId())){
    			rank=i;
    		}
    	}
    	System.out.println("5");
    	System.out.println("计算排名率 rank:"+rank);
    	System.out.println("计算排名率 rateList.size:"+rateList.size());
    	float rate=(float)rank/(float)rateList.size();
    	int result=floatToInt(rate);
    	return result;
    }
    
    
    
    //对上级主管进行过滤
    public Boolean filterManager(SAPEmpJob empjob){
    	Boolean flag=false;
    	SAPUser user=SAPEmpJobToSAPUser(empjob);
    	String userId=empjob.getUserId();
    	if(user!=null){
    		if(userId!=null){
    			//效验是不是张雷和ANDERSEN KURT
    			if(userId!="16062"&&!"16062".equals(userId)&&userId!="65678"&&!"65678".equals(userId)){
    				//效验工作地点是否在国内
        			if(domesticLocationService.checkLocation(user)){
        				//效验员工id是否正确
        				if(checkUserId(userId)){
        					flag=true;
        				}
        			}
    			}
    		}
    	}
    	return flag;
    }
    
    //对下级进行过滤
    public Boolean filterUser(SAPEmpJob empjob){
    	Boolean flag=false;
    	SAPUser user=SAPEmpJobToSAPUser(empjob);
    	String userId=empjob.getUserId();
    	//效验是否为正式员工
    	if(user!=null){
    		if(user.getCustom06()!=null&&!"".equals(user.getCustom06())){
        		if(user.getCustom06().equals("正式员工")){
        			//效验是否为考勤类正式员工
            	    if(!checkNotEvaluationUser(empjob)){
	            		if(userId!=null){
	            			//效验是不是张雷和ANDERSEN KURT
	            			if(userId!="16062"&&!"16062".equals(userId)&&userId!="65678"&&!"65678".equals(userId)){
	            				//效验纵向主管是不是张雷和ANDERSEN KURT
	            				if(empjob.getManagerId()!="16062"&&!"16062".equals(empjob.getManagerId())&&
	            						empjob.getManagerId()!="65678"&&!"65678".equals(empjob.getManagerId())){
		            				//效验工作地点是否在国内
		                			if(domesticLocationService.checkLocation(user)){
		                				//效验员工id是否正确；
		                				if(checkUserId(userId)){
		                					flag=true;
		                				}
		                			}
	            				}
	            			}
	            		}
	            	}
	        	}
    	    }
    	}
    	return flag;
    }
    
    //数据转换
    public SAPUser SAPEmpJobToSAPUser(SAPEmpJob empjob){
    	SAPUser user=sapUserService.queryById(empjob.getUserId());
    	return user;
    }
    
    /**
     * 判断员工id
     */
    public boolean checkUserId(String userId){
    	boolean flag=false;
    	if(userId.length()==5){
    		if(isInteger(userId)){
    			flag=true;
    		}
    	}
    	return flag;
    }
    
    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
	     try {
	      Integer.parseInt(value);
	      return true;
	     } catch (NumberFormatException e) {
	      return false;
	     }
    }
    
    public int floatToInt(float rate){
    	int result=0;
    	DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
    	double db=Double.parseDouble(df.format(rate));
    	double re=db*100;
    	result=(int)re;
    	return result;
    }
    
    
    
  //计算纵向评价本年度有多少次评价机会
    public int gerTimeForEvaluation(String year) throws ParseException{
    	int result=0;
    	if("2016"==year||"2016".equals(year)){
			String begin = "2016-08-07";  
			String end = "2016-12-31"; 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Date dBegin = sdf.parse(begin);  
			Date dEnd = sdf.parse(end);  
			int days = (int)((dEnd.getTime() - dBegin.getTime())/86400000);
			result=(int)Math.ceil(days/14)+1;
    	}
    	if("2017"==year||"2017".equals(year)){
			String begin = "2017-01-09";  
			String end = "2017-12-31"; 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Date dBegin = sdf.parse(begin);  
			Date dEnd = sdf.parse(end);  
			int days = (int)((dEnd.getTime() - dBegin.getTime())/86400000);
			result=(int)Math.ceil(days/14)+1;
    	}
    	return result;
    }
    
    //计算横向评价本年度有多少次评价机会
    public int gerTimeForAssessment(String year) throws ParseException{
    	int result=0;
    	if("2016"==year||"2016".equals(year)){
			String begin = "2016-08-15";  
			String end = "2016-12-31"; 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Date dBegin = sdf.parse(begin);  
			Date dEnd = sdf.parse(end);  
			int days = (int)((dEnd.getTime() - dBegin.getTime())/86400000);
			result=(int)Math.ceil(days/28)+1;
    	}
    	if("2017"==year||"2017".equals(year)){
			String begin = "2017-01-02";  
			String end = "2017-12-31"; 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Date dBegin = sdf.parse(begin);  
			Date dEnd = sdf.parse(end);  
			int days = (int)((dEnd.getTime() - dBegin.getTime())/86400000);
			result=(int)Math.ceil(days/28)+1;
    	}
    	return result;
    }
    
    
    
    //计算上级纵向评价过多少期
    public int gerEvaNumToManager(String managerId) throws ParseException{
    	int result=0;
    	//查看所有纵向评价中，上级是否已经评论完
		List<EvaluationRate> list=queryByManagerIdLimitSix(managerId);
		for(int i=0;i<list.size();i++){
			result=result+list.get(i).getRate();
		}
		return result;
    }
    
 
    public List<EvaluationRate> queryByManagerIdLimitSix(String managerId) throws ParseException{
    	List<EvaluationRate> list=evaluationRateDao.queryByManagerIdLimitSix(managerId);
		return list;
    }
    
    
    //计算下级被纵向评价过多少期
    public int gerEvaNumToUser(String userId) throws ParseException{
    	List<Evaluation> list=evaluationService.queryByUserIdAndYear(userId);
		return list.size();
    }
    
    //查询上级的所有 在职的 正式员工 下级
    public List<SAPUser> queryListUser(String managerId){
    	List<SAPEmpJob> listse=sapEmpJobService.queryByManagerId(managerId);
    	List<SAPUser> listsu=new ArrayList<SAPUser>();
		for(int j=0;j<listse.size();j++){
			SAPUser su=sapUserService.queryById(listse.get(j).getUserId());
			if(su.getCustom06()=="正式员工"||"正式员工".equals(su.getCustom06())){
				listsu.add(su);
			}
		}
    	return listsu;
    }
    
    
    //查看下属最新一期纵向评价是否被评价过
    public Boolean queryRecentEvaluationByUserId(String userId){
    	Boolean flag=false;
    	EvaluationPeriod ep=evaluationPeriodService.queryRecent();
    	List<Evaluation> list=evaluationService.queryByPeriodId(ep.getPeriodId());
    	for(int i=0;i<list.size();i++){
    		String uId=list.get(i).getUserId();
    		if(userId.equals(uId)){
    			flag=true;
    		}
    	}
    	return flag;
    }
    
    //查看纵向主管是否完成最新一期纵向评价
    public Boolean queryRecentEvaluationByManagerId(String managerId){
    	Boolean flag=false;
    	//查询最新一期纵向评价
		EvaluationPeriod ep=evaluationPeriodService.queryRecent();
		String periodId=ep.getPeriodId();
		EvaluationRate er=queryByPeriodIdAndManagerId(periodId, managerId);
		if(er!=null){
			if(er.getRate()>0){
				flag=true;
			}
		}
		return flag;
    }
    
     //查看横向评价最新一期横向评价是否评价过
    public Boolean queryRecentAssessmentByCriticId(String criticId){
    	Boolean flag=false;
    	AssessmentCycles ac=acService.queryRecent();
    	List<Assessment> list=asService.queryAssessmentByCycleId(ac.getCycleId());
    	for(int i=0;i<list.size();i++){
    		String cId=list.get(i).getCriticId();
    		if(criticId.equals(cId)){
    			flag=true;
    		}
    	}
    	return flag;
    }
    
    
    /*
     * 纵向评价提醒纵向主管消息推送信息
     * 
     * */
    public  String getMessageToManager(String managerId) throws ParseException{
    	EvaluationPeriod ep=evaluationPeriodService.queryRecent();
    	Date date=ep.getPeriodDateTo();
    	int month=(int) getMonthAndDay(date).get("month");
    	System.out.println("月份 month:"+month);
    	int day=(int) getMonthAndDay(date).get("day");
    	System.out.println("日期 day:"+day);
		//已评价次数
		int evaNum=gerEvaNumToManager(managerId);
		System.out.println("已评价次数 evaNum:"+evaNum);
		//完成率
		int rate=getRate(managerId);
		System.out.println("完成率 rate:"+rate);
    	StringBuffer sb=new StringBuffer();
    	if(rate==100){
			sb.append("【挑战者app】温馨提示：在过去6期的纵向主管评价中，您每期都完成了对所有下属的评价。"
					+ "本期评价将于"+month+"月"+day+"日截止，请您再接再厉，尽快完成评价，以保持100%的完成率。");
		}
    	if(rate>0&&rate<100){
    		int rank=queryRateRank(managerId);
    		System.out.println("排名 rank:"+rank);
    		sb.append("【挑战者app】温馨提示：在过去6期的纵向主管评价中，您有"+evaNum+"期完成了对所有下属的评价，"
    				+ "整体完成率为"+rate+"%，有"+rank+"%的主管完成率排在您的前面。本期评价将于"+month+"月"+day+"日截止，请您抓住机会，尽快完成评价。");
    	}
		if(rate==0){
			sb.append("【挑战者app】温馨提示：在过去6期的纵向主管评价中，您一期都没有完成对所有下属的评价。"
					+ "本期评价将于"+month+"月"+day+"日截止，请您珍惜机会，尽快完成评价。");
		}
		System.out.println("信息："+sb.toString());
    	return sb.toString();
    	
    }
    
    //向下级推送信息
    public  String getMessageToUser(){
    	EvaluationPeriod ep=evaluationPeriodService.queryRecent();
    	Date date=ep.getPeriodDateTo();
    	int month=(int) getMonthAndDay(date).get("month");
    	int day=(int) getMonthAndDay(date).get("day");
    	StringBuffer sb=new StringBuffer();
    	sb.append("【挑战者app】温馨提示：本期纵向主管评价将于"+month+"月"+day+"日截止，"
    			+ "至今您的主管尚未完成对您的反馈。请提醒TA尽快完成。");
    	return sb.toString();
    }
    
  //横向评价提醒推送信息
    public  String getMessageToAsssmengt(int nullEvaNum){
    	StringBuffer sb=new StringBuffer();
    	sb.append("【挑战者app】温馨提示：本期您尚未对任何横向同事进行反馈，"
    			+ "本年度还剩"+nullEvaNum+"次机会，请您珍惜您的横向评价权、把握机会，及时反馈。");
    	return sb.toString();
    }
    
    
    /*
     * 获取date的月和日
     * 
     * */
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public Map getMonthAndDay(Date date){
	   Calendar cal= Calendar.getInstance();
	   cal.setTime(date);
	   int month=cal.get(Calendar.MONTH)+1;
	   int day=cal.get(Calendar.DAY_OF_MONTH);
	   Map map=new HashMap();
	   map.put("month", month);
	   map.put("day", day);
	   return map;
   }
   
   /*
    *效验是否为考勤类正式员工 
    * 
    * */
   public boolean checkNotEvaluationUser(SAPEmpJob empjob){
	   	boolean flag=false;
	   	String userId=empjob.getUserId();
	   	List<NotEvaluationUser>  list=notEvaluationUserService.queryAll();
	   	for(int i=0;i<list.size();i++){
	   		if(list.get(i).getUserId()==userId||userId.equals(list.get(i).getUserId())){
	   			flag=true;
	   		}
	   	}
	   	return flag;
   }
   
   /*
     * 生产环境
	 * 纵向评价主管提示
	 * 短信推送
	 * */
   @SuppressWarnings("static-access")
   public void sendMessageToManager(String type) throws Exception{
   	EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		if(lastEP!=null){
			Date lastDate=lastEP.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			long day = l / (1000 * 60 * 60 * 24);
		    // 这是他们相差的天数
		    if(day==9||day==11){
		    	List<SAPEmpJob> listse=sapEmpJobService.queryManager();
		    	for(int i=0;i<listse.size();i++){
		    		//过滤上级
		    		if(filterManager(listse.get(i))){
		    			String managerId=listse.get(i).getUserId();
//			    			if(managerId=="99314"||"99314".equals(managerId)){
			    				if(!queryRecentEvaluationByManagerId(managerId)){
				    	        	if(type=="SMS"||"SMS".equals(type)){
				    	        		System.out.println("信息推送 SMS");
				    	        		String msg=getMessageToManager(managerId);
				    	        		System.out.println("信息推送 SMS  msg:"+msg);
					    	        	String phone=SAPEmpJobToSAPUser(listse.get(i)).getCustom08();
//				    	        		String phone="15121051049";
				    	        		System.out.println("信息推送 SMS  phone:"+phone);
					    	        	if(phone!=null&&!"".equals(phone)){
					    		       		sendSmsService.sendMoblieMessage(msg,phone);
					    		       	}
				    				}
				    		       	if(type=="Email"||"Email".equals(type)){
				    		       		System.out.println("信息推送 Email");
				    		       		EvaluationPeriod ep=evaluationPeriodService.queryRecent();
				    		       		System.out.println("信息推送 Email period:"+ep.getPeriodId());
				    		        	Date date=ep.getPeriodDateTo();
				    		        	int m=(int) getMonthAndDay(date).get("month");
				    		        	System.out.println("信息推送 Email month:"+m);
				    		        	int d=(int) getMonthAndDay(date).get("day");
				    		        	System.out.println("信息推送 Email day:"+d);
				    		        	String subject="【挑战者app】温馨提示：本期纵向主管评价将于"+m+"月"+d+"日截止";
				    		        	System.out.println("信息推送 Email  subject:"+subject);
				    		       		String msg=getMessageToManager(managerId);
				    		       		System.out.println("信息推送 Email  msg:"+msg);
					    	        	String email=SAPEmpJobToSAPUser(listse.get(i)).getEmail();
//				    		       		String email="song.cai@envisioncn.com";
				    	        		System.out.println("信息推送 Email  email:"+email);
					    	        	if(email!=null&&!"".equals(email)){
					    	        		sendEmailService.sendEmail(email,msg,subject);
					    	        	}
				    		       	}
//			    			}
		    	    	}
		    		}
		    	}
		    }
		}
   }
   
   /*
	 * 纵向下级提示
	 * 消息推送
	 * 
	 * */
   @SuppressWarnings("static-access")
	public void sendMessageToUser(String type) throws Exception{
	   	EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		if(lastEP!=null){
			Date lastDate=lastEP.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			long day = l / (1000 * 60 * 60 * 24);
	    	//这是他们相差的天数
	    	if(day==9||day==11){
	    		List<SAPEmpJob> list=sapEmpJobService.queryAll();
		    	for(int i=0;i<list.size();i++){
		    		SAPEmpJob eb=sapEmpJobService.queryEMPJobById(list.get(i).getUserId());
		    		//过滤下级
		    		if(filterUser(eb)){
		    			String UserId=eb.getUserId();
		    			if(!queryRecentEvaluationByUserId(UserId)){
		    				//发送推送消息
		    				if(type=="SMS"||"SMS".equals(type)){
		    					String msg=getMessageToUser();
			    		       	String phone=SAPEmpJobToSAPUser(eb).getCustom08();
			    		       	if(phone!=null&&!"".equals(phone)){
			    		       		sendSmsService.sendMoblieMessage(msg,phone);
			    		       	}
		    				}
		    		       	if(type=="Email"||"Email".equals(type)){
		    		       		String msg=getMessageToUser();
			    	        	String email=SAPEmpJobToSAPUser(eb).getEmail();
			    	        	EvaluationPeriod ep=evaluationPeriodService.queryRecent();
		    		        	Date date=ep.getPeriodDateTo();
		    		        	int m=(int) getMonthAndDay(date).get("month");
		    		        	int d=(int) getMonthAndDay(date).get("day");
		    		        	String subject="【挑战者app】温馨提示：本期纵向主管评价将于"+m+"月"+d+"日截止";
			    	        	if(email!=null&&!"".equals(email)){
			    	        		sendEmailService.sendEmail(email,msg,subject);
			    	        	}
		    		       	}
		    	    	}
		    		}
		    	}
	    	}
		}
	   
   }
   
   /*
  	 * 横向评价提醒
  	 * 
  	 * */
      @SuppressWarnings("static-access")
	public void sendMessageOfAssessment(String type) throws Exception{
    	EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
  		if(lastEP!=null){
  			Date lastDate=lastEP.getCts();
  			Date now=new Date();
  			long l = now.getTime() - lastDate.getTime();
  			long day = l / (1000 * 60 * 60 * 24);
  	    	//这是他们相差的天数
  	    	if(day==23||day==25){
  	    		List<SAPAssessmentUser> list=sapAssessmentUserService.queryAll();
	  	    	for(int i=0;i<list.size();i++){
	  	    		//横向评价人id
	  	    		String CriticId=list.get(i).getUserId();
	  		      	if(!queryRecentAssessmentByCriticId(CriticId)){
	  		      		 AssessmentCycles ac=acService.queryLastOne();
	  		          	 String year=ac.getCycleId().substring(0,4);
	  		          	 //本年度评价机会
	  		          	 int index=gerTimeForAssessment(year);
	  		          	 //总评价次数
	  		          	 int des=Integer.parseInt(ac.getDes());
	  		          	 //本年度剩余评价次数
	  		          	 int nullEvaNum=index-des;
	  		          	//发送推送消息
	    				if(type=="SMS"||"SMS".equals(type)){
	    					String msg=getMessageToAsssmengt(nullEvaNum);
		    		       	String phone=SAPEmpJobToSAPUser(sapEmpJobService.queryEMPJobById(CriticId)).getCustom08();
		    		       	if(phone!=null&&!"".equals(phone)){
		    		       		sendSmsService.sendMoblieMessage(msg,phone);
		    		       	}
	    				}
	    		       	if(type=="Email"||"Email".equals(type)){
	    		       		String msg=getMessageToAsssmengt(nullEvaNum);
		    	        	String email=SAPEmpJobToSAPUser(sapEmpJobService.queryEMPJobById(CriticId)).getEmail();
		    	        	AssessmentCycles a=acService.queryRecent();
	    		        	Date date=a.getCyclesDateTo();
	    		        	int m=(int) getMonthAndDay(date).get("month");
	    		        	int d=(int) getMonthAndDay(date).get("day");
	    		        	String subject="【挑战者app】温馨提示：本期横向主管评价将于"+m+"月"+d+"日截止";
		    	        	if(email!=null&&!"".equals(email)){
		    	        		sendEmailService.sendEmail(email,msg,subject);
		    	        	}
	    		       	}
	  		      	 }
	  	    	}
  	    	}
  		}
      }
   
   /*
    * 测试环境
	* 纵向评价主管提示
	* 短信推送
	* */
	 @SuppressWarnings("static-access")
	 public void sendMessageToManagerOfTest(String type) throws Exception{
	 	String managerId="80272";
	 	SAPEmpJob eb=sapEmpJobService.queryEMPJobById(managerId);
	 	//过滤上级
		if(filterManager(eb)){
			if(!queryRecentEvaluationByManagerId(managerId)){
		     	if(type=="SMS"||"SMS".equals(type)){
		    		String msg=getMessageToManager(managerId);
			       	String phone="15121051049";
		       		sendSmsService.sendMoblieMessage(msg,phone);
				}
		       	if(type=="Email"||"Email".equals(type)){
		       		String msg=getMessageToManager(managerId);
		        	String email="song.cai@envisioncn.com";
		        	EvaluationPeriod ep=evaluationPeriodService.queryRecent();
		        	Date date=ep.getPeriodDateTo();
		        	int m=(int) getMonthAndDay(date).get("month");
		        	int d=(int) getMonthAndDay(date).get("day");
		        	String subject="【挑战者app】温馨提示：本期纵向主管评价将于"+m+"月"+d+"日截止";
	        		sendEmailService.sendEmail(email,msg,subject);
		       	}
		 	}
		}
	 }
   
	/*
	 * 测试环境
	 * 纵向下级提示
	 * 消息推送
	 * */
	@SuppressWarnings("static-access")
	public void sendMessageToUserOfTest(String type) throws Exception{
		String userId="87270";
		SAPEmpJob eb=sapEmpJobService.queryEMPJobById(userId);
		//过滤下级
		if(filterUser(eb)){
			if(!queryRecentEvaluationByUserId(userId)){
		    	 //发送推送消息
		    	if(type=="SMS"||"SMS".equals(type)){
		    		String msg=getMessageToUser();
		    		String phone="15121051049";
		       		sendSmsService.sendMoblieMessage(msg,phone);
				}
		       	if(type=="Email"||"Email".equals(type)){
		       		String msg=getMessageToUser();
		       		String email="song.cai@envisioncn.com";
		        	EvaluationPeriod ep=evaluationPeriodService.queryRecent();
		        	Date date=ep.getPeriodDateTo();
		        	int m=(int) getMonthAndDay(date).get("month");
		        	int d=(int) getMonthAndDay(date).get("day");
		        	String subject="【挑战者app】温馨提示：本期纵向主管评价将于"+m+"月"+d+"日截止";
	        		sendEmailService.sendEmail(email,msg,subject);
		       	}
			 }
		}
	 }
	 
	
	 /*
	  * 测试环境
	  * 横向评价提醒
	  * 测试环境
	  * */
    @SuppressWarnings("static-access")
	public void sendMessageOfAssessmentOfTest(String type) throws Exception{
		String CriticId="14411";
      	if(!queryRecentAssessmentByCriticId(CriticId)){
      		 AssessmentCycles ac=acService.queryLastOne();
          	 String year=ac.getCycleId().substring(0,4);
          	 //本年度评价机会
          	 int index=gerTimeForAssessment(year);
          	 //总评价次数
          	 int des=Integer.parseInt(ac.getDes());
          	 //本年度剩余评价次数
          	 int nullEvaNum=index-des;
          	//发送推送消息
			if(type=="SMS"||"SMS".equals(type)){
				String msg=getMessageToAsssmengt(nullEvaNum);
				String phone="15121051049";
	       		sendSmsService.sendMoblieMessage(msg,phone);
			}
	       	if(type=="Email"||"Email".equals(type)){
	       		String msg=getMessageToAsssmengt(nullEvaNum);
	       		String email="song.cai@envisioncn.com";
	        	AssessmentCycles a=acService.queryRecent();
	        	Date date=a.getCyclesDateTo();
	        	int m=(int) getMonthAndDay(date).get("month");
	        	int d=(int) getMonthAndDay(date).get("day");
	        	String subject="【挑战者app】温馨提示：本期横向主管评价将于"+m+"月"+d+"日截止";
        		sendEmailService.sendEmail(email,msg,subject);
	       	}
      	 }
	}
    
}
