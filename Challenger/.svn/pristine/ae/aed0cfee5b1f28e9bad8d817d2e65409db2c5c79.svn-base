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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.EvaluationRateDao;
import com.envision.envservice.dao.WordRateDao;
import com.envision.envservice.entity.dto.Evaluation;
import com.envision.envservice.entity.dto.EvaluationPeriod;
import com.envision.envservice.entity.dto.WordRate;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.entity.sap.SAPUser;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class WordRateService {
	
	@Autowired
	private EvaluationPeriodService evaluationPeriodService;
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private WordRateDao wordRateDao;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private DomesticLocationService domesticLocationService;
	
	/*
	 * 新增所有纵向主管每一期的文字评价率
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
					for(int z=0;z<userList.size();z++){
						if(filterUser(userList.get(z))){
							String userId=userList.get(z).getUserId();
							//查询评价
							Evaluation eva=evaluationService.queryByPeriodIdAndManagerIdAndUserId(periodId, managerId, userId);
							WordRate wordRate=new WordRate();
							wordRate.setPeriodId(periodId);
							wordRate.setManagerId(managerId);
							wordRate.setUserId(userId);
							if(eva!=null&&eva.getPriseWill()>0&&eva.getPriseWisdom()>0&&eva.getPriseLove()>0){
								if(eva.getProsWill()!=null&&!eva.getProsWill().equals("")
										||eva.getProsWisdom()!=null&&!eva.getProsWisdom().equals("")
										||eva.getProsLove()!=null&&!eva.getProsLove().equals("")){
										rate=1;
								}else{
									rate=0;
								}
							}else{
								rate=0;
							}
							wordRate.setRate(rate);
							//删除已有完成率
							WordRate er=queryByPeriodIdAndManagerIdAndUserId(periodId,managerId,userId);
							if(er!=null){
								int del=deleteByPeriodIdAndManagerIdAndUserId(periodId,managerId,userId);
								if(del<1){
									System.out.println(periodId+"期纵向评价已有的文字评价率删除失败 ");
								}
							}
							int result=wordRateDao.addWordRate(wordRate);
							if(result>0){
								success++;
							}else{
								fail++;
							}
							System.out.println("新增文字评价率periodId:"+periodId+",managerId:"+wordRate.getManagerId()+
									",userId:"+wordRate.getUserId()+",Rate:"+wordRate.getRate()+",success:"+success);
						}else{
							System.out.println("下级:"+userList.get(z).getUserId()+"效验失败");
							userList.remove(z);
							z--;
						}
					}
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
	 * 修改所有纵向主管最新一期的文字评价率
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
				for(int z=0;z<userList.size();z++){
					if(filterUser(userList.get(z))){
						String userId=userList.get(z).getUserId();
						//查询评价列表
						Evaluation eva=evaluationService.queryByPeriodIdAndManagerIdAndUserId(periodId, managerId, userId);
						WordRate wordRate=new WordRate();
						wordRate.setPeriodId(periodId);
						wordRate.setManagerId(managerId);
						wordRate.setUserId(userId);
						if(eva!=null&&eva.getPriseWill()>0&&eva.getPriseWisdom()>0&&eva.getPriseLove()>0){
							if(eva.getProsWill()!=null||!"".equals(eva.getProsWill())
									||eva.getProsWisdom()!=null||!"".equals(eva.getProsWisdom())
									||eva.getProsLove()!=null||!"".equals(eva.getProsLove())){
									rate=1;
							}else{
								rate=0;
							}
						}else{
							rate=0;
						}
						wordRate.setRate(rate);
						//删除已有完成率
						WordRate er=queryByPeriodIdAndManagerIdAndUserId(periodId,managerId,userId);
						if(er!=null){
							int del=deleteByPeriodIdAndManagerIdAndUserId(periodId,managerId,userId);
							if(del<1){
								System.out.println(periodId+"期纵向评价已有的文字评价率删除失败 ");
							}
						}
						int result=wordRateDao.addWordRate(wordRate);
						if(result>0){
							success++;
						}else{
							fail++;
						}
						System.out.println("修改最新一期文字评价率periodId:"+periodId+",managerId:"+wordRate.getManagerId()+
								",userId:"+wordRate.getUserId()+",Rate:"+wordRate.getRate()+",success:"+success);
					}else{
						System.out.println("下级:"+userList.get(z).getUserId()+"效验失败");
						userList.remove(z);
						z--;
					}
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
	public WordRate queryByPeriodIdAndManagerIdAndUserId(String periodId,String managerId,String userId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		map.put("managerId", managerId);
		map.put("userId", userId);
		WordRate er=wordRateDao.queryByPeriodIdAndManagerIdAndUserId(map);
		
		return er;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int deleteByPeriodIdAndManagerIdAndUserId(String periodId,String managerId,String userId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		map.put("managerId", managerId);
		map.put("userId", userId);
		int result=wordRateDao.deleteByPeriodIdAndManagerIdAndUserId(map);
		
		return result;
	}
	
	public List<WordRate> queryByManagerId(String managerId){
		List<WordRate> list=wordRateDao.queryByManagerId(managerId);
		
		return list;
	}
	
	/*
	 * 获取文字评价率
	 * 
	 * */
	public int getWordRate(String managerId){
		List<WordRate> list=wordRateDao.queryByManagerId(managerId);
		int result=0;
		int total=list.size();
		int evaNum=0;
		for(int i=0;i<list.size();i++){
			int num=list.get(i).getRate();
			evaNum=evaNum+num;
		}
		
		float rate=(float)evaNum/(float)total;
    	DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
    	double db=Double.parseDouble(df.format(rate));
    	double re=db*100;
    	result=(int)re;
    	return result;
		
	}
	
	//对上级主管进行过滤
    public Boolean filterManager(SAPEmpJob empjob){
    	Boolean flag=false;
    	SAPUser user=SAPEmpJobToSAPUser(empjob);
    	String userId=empjob.getUserId();
    	if(user!=null){
     		//效验是不是张雷和ANDERSEN KURT
    		if(userId!=null){
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
             		//效验是不是张雷和ANDERSEN KURT
            		if(userId!=null){
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
}
