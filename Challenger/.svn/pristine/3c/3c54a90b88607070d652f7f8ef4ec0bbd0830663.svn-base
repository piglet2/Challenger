/******************************************************************************
 * @File name   :      PraiseReporter.java
 *
 * @Package 	:	   com.envision.envservice.report
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-30 上午10:52:34
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
 * 2015-11-30 上午10:52:34    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.common.util.MapUtil;
import com.envision.envservice.entity.bo.PraisePoint;
import com.envision.envservice.entity.bo.TeamPoint;
import com.envision.envservice.entity.bo.TeamPoint.TeamPraisePoint;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.PraiseDetail;
import com.envision.envservice.report.entity.PraiseReport;
import com.envision.envservice.report.template.PraiseReportExcel;
import com.envision.envservice.service.MapConfigService;
import com.envision.envservice.service.OrgStructureService;
import com.envision.envservice.service.SpiritService;
import com.envision.envservice.service.UserService;

/**
 * @ClassName PraiseReporter
 * @author guowei.wang
 * @date 2015-11-30
 */
@Component
public class PraiseReporter {
	
	private static final int DEFAULT_COUNT = 0;
	
	private static final String ADD = "add";
	
	private static final String DEL = "delete";
	
	private static final String TEAM_LEADER = "report_team_leader";
	
	@Autowired
	private SpiritService spiritService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgStructureService orgStructureService;
	
	@Autowired
	private MapConfigService mapConfigService;
	
	public byte[] report() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XSSFWorkbook workBook = PraiseReportExcel.buildWorkBook(count());
		workBook.write(out);
		
		return out.toByteArray();
	}
	
	private PraiseReport count() throws Exception {
		PraiseReport report = new PraiseReport();
		report.setObtainPraises(convertBos(calcPraise(PraiseType.PRAISE, true), true));
		report.setObtainEncourages(convertBos(calcPraise(PraiseType.ENCOURAGE, true), true));
		report.setGivePraises(convertBos(calcPraise(PraiseType.PRAISE, false), true));
		report.setGiveEncourages(convertBos(calcPraise(PraiseType.ENCOURAGE, false), true));
		
		report.setTeamPoints(countTeam());
		
		return report;
	}
	
	private Map<String, TeamPoint> countTeam() throws Exception {
		Map<String, TeamPoint> teamPoints = new LinkedHashMap<String, TeamPoint>();
		String[] tls = mapConfigService.getSplitValue(TEAM_LEADER);
		Map<String, UserBo> users = userService.queryUsers(true, false, tls);
		for (String teamleaderId : tls) {
			teamPoints.put(userService.displayName(users.get(teamleaderId)), countTeamPoint(teamleaderId));
		}
		
		return teamPoints;
	}
	
	private TeamPoint countTeamPoint(String teamleaderId) throws Exception {
		TeamPoint teamPoint = new TeamPoint();

		List<PraisePoint> praiseAll = filterTeam(teamleaderId, convertBos(calcPraise(PraiseType.PRAISE, true)));
		List<PraisePoint> encourageAll = filterTeam(teamleaderId, convertBos(calcPraise(PraiseType.ENCOURAGE, true)));
		teamPoint.setPraisePointAll(merge(praiseAll, encourageAll));
		
		List<PraisePoint> praiseTeam = filterTeam(teamleaderId, convertBos(calcPraise(teamleaderId, PraiseType.PRAISE, true)));
		List<PraisePoint> encourageTeam = filterTeam(teamleaderId, convertBos(calcPraise(teamleaderId, PraiseType.ENCOURAGE, true)));
		teamPoint.setPraisePointTeam(merge(praiseTeam, encourageTeam));
		
		return teamPoint;
	}
	
	private List<PraisePoint> filterTeam(String teamleaderId, List<PraisePoint> points) throws Exception {
		List<PraisePoint> teamPoint = new LinkedList<PraisePoint>();
		Set<String> lowerLevels = orgStructureService.queryLowerLevel(teamleaderId);
		for (PraisePoint point : points) {
			if (lowerLevels.contains(point.getUserId())) {
				teamPoint.add(point);
			}
		}
		
		return teamPoint;
	}
	
	private List<TeamPoint.TeamPraisePoint> merge(List<PraisePoint> praisePoints, List<PraisePoint> encouragePoints) {
		List<TeamPoint.TeamPraisePoint> teamPraisePoints = new LinkedList<TeamPoint.TeamPraisePoint>();
		
		for (PraisePoint praisePoint : praisePoints) {
			PraisePoint matchEncouragePoint = null;
			for (PraisePoint encouragePoint : encouragePoints) {
				if (praisePoint.getUserId().equals(encouragePoint.getUserId())) {
					matchEncouragePoint = encouragePoint;
				}
			}
			
			teamPraisePoints.add(buildTeamPraisePoint(praisePoint, matchEncouragePoint));
		}
		
		return teamPraisePoints;
	}

	private Map<String, Integer> calcPraise(PraiseType type, boolean isTarget) {
		return calcPraise(null, type, isTarget);
	}

	private Map<String, Integer> calcPraise(String userId, PraiseType type, boolean isTarget) {
		List<PraiseDetail> praiseAdd = spiritService.queryMatch(buildPraiseMatch(userId, type, ADD));
		List<PraiseDetail> praiseDEL = spiritService.queryMatch(buildPraiseMatch(userId, type, DEL));
		
		return merge(groupAndCount(praiseAdd, isTarget), groupAndCount(praiseDEL, isTarget), true);
	}
	
	private Map<String, Integer> groupAndCount(List<PraiseDetail> praiseDetails, boolean isTarget) {
		Map<String, Integer> idCountMapping = new HashMap<String, Integer>();
		for (PraiseDetail praiseDetail : praiseDetails) {
			String groupKey = groupKey(praiseDetail, isTarget);
			
			Integer count = idCountMapping.get(groupKey);
			if (count == null) {
				count = DEFAULT_COUNT;
			}
			idCountMapping.put(groupKey, ++count);
		}
		
		return idCountMapping;
	}
	
	private String groupKey(PraiseDetail praiseDetail, boolean isTarget) {
		return isTarget ? praiseDetail.getTargetUserId() : praiseDetail.getUserId();
	}
	
	private Map<String, Integer> merge(Map<String, Integer> addCount, Map<String, Integer> delCount, boolean needSort) {
		Map<String, Integer> merge = new HashMap<String, Integer>();
		for (Map.Entry<String, Integer> me : addCount.entrySet()) {
			String key = me.getKey();
			
			Integer addNum = me.getValue();
			Integer delNum = delCount.get(key) == null ? DEFAULT_COUNT : delCount.get(key);
			
			merge.put(key, addNum - delNum);
		}
		
		return needSort ? sortCount(merge) : merge;
	}
	
	private Map<String, Integer> sortCount(Map<String, Integer> count) {
		return MapUtil.sortByValue(count, false);
	}

	private PraiseDetail buildPraiseMatch(String userId, PraiseType type, String operation) {
		PraiseDetail match = new PraiseDetail();
		
		match.setpType(type.getType());
		match.setOperation(operation);
		if (userId != null) {
			match.setUserId(userId);
		}
		
		return match;
	}
	
	private List<PraisePoint> convertBos(Map<String, Integer> counts) throws Exception {
		return convertBos(counts, false);
	}

	private List<PraisePoint> convertBos(Map<String, Integer> counts, boolean valid) throws Exception {
		List<PraisePoint> bos = new LinkedList<PraisePoint>();

		Map<String, UserBo> users = userService.queryUsers(true, false, MapUtil.toKeyArr(counts, String.class));
		for (Map.Entry<String, Integer> me : counts.entrySet()) {
			PraisePoint bo = toBo(me.getKey(), userService.displayName(users.get(me.getKey())), me.getValue());
			
			if (valid && !isInvalid(bo)) {
				continue;
			}

			bos.add(bo);
		}
		
		return bos;
	}
	
	private boolean isInvalid(PraisePoint bo) {
		return bo.getCount() > 0;
	}
	
	private PraisePoint toBo(String userId, String name, Integer count) {
		PraisePoint prisePoint = new PraisePoint();
		prisePoint.setUserId(userId);
		prisePoint.setName(name);
		prisePoint.setCount(count);
		
		return prisePoint;
	}
	
	private TeamPraisePoint buildTeamPraisePoint(PraisePoint praisePoint, PraisePoint encouragePoint) {
		TeamPraisePoint teamPraisePoint = new TeamPraisePoint();
		teamPraisePoint.setUserId(praisePoint.getUserId());
		teamPraisePoint.setName(praisePoint.getName());
		teamPraisePoint.setPraiseCount(praisePoint.getCount());
		teamPraisePoint.setEncourageCount(encouragePoint == null ? 0 : encouragePoint.getCount()); 
		
		return teamPraisePoint;
	}
}
