/******************************************************************************
 * @File name   :      PraiseReportExcel.java
 *
 * @Package 	:	   com.envision.envservice.report.template
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-30 上午10:52:03
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
 * 2015-11-30 上午10:52:03    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.report.template;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.entity.bo.PraisePoint;
import com.envision.envservice.entity.bo.TeamPoint;
import com.envision.envservice.entity.bo.TeamPoint.TeamPraisePoint;
import com.envision.envservice.report.entity.PraiseReport;

/**
 * 点赞统计报表Excel模板
 * 
 * @ClassName PraiseReportExcel
 * @author guowei.wang
 * @date 2015-11-30
 */
public class PraiseReportExcel {
	
	private static final int INDEX_SHEET_DAY = 0;

	private static final int INDEX_SHEET_TEAM = 3;
	
	private static final int INDEX_CELL_TEAM_FIR = 1;

	private static final int INDEX_CELL_TEAM_SEC = 2;
	
	private static final int INDEX_CELL_TEAM_THI = 3;
	
	private static final int RANGE_GOURP_TEAM = 4;

	private static final int LEN_DAY_SHEET_CELL = 12;
	
	private static final String SHEET_TEAM = "TEAM";
	
	private static final String FIELD_TEAM = "团队";

	private static final String FIELD_USERNAME = "姓名";
	
	private static final String FIELD_OBTAIN_PRAISE = "获赞数";

	private static final String FIELD_OBTAIN_ENCOURAGES = "获鼓励数";

	private static final String FIELD_GIVE_PRAISE = "给赞数";
	
	private static final String FIELD_GIVE_ENCOURAGES = "给鼓励数";
	
	private static final String FIELD_GIVE_TYPE_TEAM = "TL给出";

	private static final String FIELD_GIVE_TYPE_ALL = "全部";
	
	private static final Color COLOR_TEAM_TITLE = new Color(155, 194, 230);

	private static final Color COLOR_TYPE_TITLE = new Color(217, 217, 217);

	public static XSSFWorkbook buildWorkBook(PraiseReport report) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		buildDaySheet(workbook, report.getObtainPraises(), report.getObtainEncourages(), report.getGivePraises(), report.getGiveEncourages());
		buildTeamSheet(workbook, report.getTeamPoints());

		return workbook;
	}

	private static void buildDaySheet(XSSFWorkbook workbook, List<PraisePoint> obtainPraises, 
														List<PraisePoint> obtainEncourages,
				 								 		List<PraisePoint> givePraises, 
				 								 		List<PraisePoint> giveEncourages) {
		XSSFSheet daySheet = workbook.createSheet(DateUtil.today());
		buildTitleRow(workbook, daySheet);
		buildDataRows(daySheet, obtainPraises, obtainEncourages, givePraises, giveEncourages);
	}
	
	private static void buildTeamSheet(XSSFWorkbook workbook, Map<String, TeamPoint> teamPoints) {
		XSSFSheet teamSheet = workbook.createSheet(SHEET_TEAM);
		
		int teamIndex = 0;
		for (Map.Entry<String, TeamPoint> me : teamPoints.entrySet()) {
			buildTeamCells(workbook, teamIndex, teamSheet, me.getKey(), me.getValue());
			
			teamIndex++;
		}
	}
	
	private static void buildTeamCells(XSSFWorkbook workbook, int teamIndex, XSSFSheet teamSheet, String teamleader, TeamPoint teamPoint) {
		int rowIndex = INDEX_SHEET_TEAM;
		buildTeamRow(teamIndex, rowIndex++, teamSheet, teamleader + FIELD_TEAM, buildStyle(workbook, COLOR_TEAM_TITLE));
		buildGroupRow(teamIndex, rowIndex++, teamSheet, FIELD_GIVE_TYPE_TEAM, buildStyle(workbook, COLOR_TYPE_TITLE));
		buildTitleRow(teamIndex, rowIndex++, teamSheet, buildStyle(workbook, COLOR_TYPE_TITLE));
		
		int dataSize = buildDataRow(teamIndex, rowIndex++, teamSheet, teamPoint.getPraisePointTeam());
		
		rowIndex = dataSize + (rowIndex++);
		buildGroupRow(teamIndex, rowIndex++, teamSheet, FIELD_GIVE_TYPE_ALL, buildStyle(workbook, COLOR_TYPE_TITLE));
		buildTitleRow(teamIndex, rowIndex++, teamSheet, buildStyle(workbook, COLOR_TYPE_TITLE));
		
		buildDataRow(teamIndex, rowIndex++, teamSheet, teamPoint.getPraisePointAll());
	}

	private static int buildDataRow(int teamIndex, int rowIndex, XSSFSheet teamSheet, List<TeamPraisePoint> pointTeam) {
		for (int i = 0; i < pointTeam.size(); i++) {
			XSSFRow dataRow = getRow(teamSheet, rowIndex + i);
			dataRow.createCell(INDEX_CELL_TEAM_FIR + teamIndex * RANGE_GOURP_TEAM).setCellValue(pointTeam.get(i).getPraiseCount());
			dataRow.createCell(INDEX_CELL_TEAM_SEC + teamIndex * RANGE_GOURP_TEAM).setCellValue(pointTeam.get(i).getEncourageCount());
			dataRow.createCell(INDEX_CELL_TEAM_THI + teamIndex * RANGE_GOURP_TEAM).setCellValue(pointTeam.get(i).getName());
		}
		return pointTeam.size();
	}

	private static void buildTitleRow(int teamIndex, int rowIndex, XSSFSheet teamSheet, XSSFCellStyle style) {
		XSSFRow titleRow = getRow(teamSheet, rowIndex);
		XSSFCell cellF = titleRow.createCell(INDEX_CELL_TEAM_FIR + teamIndex * RANGE_GOURP_TEAM);
		XSSFCell cellS = titleRow.createCell(INDEX_CELL_TEAM_SEC + teamIndex * RANGE_GOURP_TEAM);
		XSSFCell cellT = titleRow.createCell(INDEX_CELL_TEAM_THI + teamIndex * RANGE_GOURP_TEAM);
		
		setStyle(style, cellF, cellS, cellT);

		cellF.setCellValue(FIELD_OBTAIN_PRAISE);
		cellS.setCellValue(FIELD_OBTAIN_ENCOURAGES);
		cellT.setCellValue(FIELD_USERNAME);
	}

	private static void buildGroupRow(int teamIndex, int rowIndex, XSSFSheet teamSheet, String cellValue, XSSFCellStyle style) {
		XSSFRow groupRow = getRow(teamSheet, rowIndex);
		XSSFCell cellF = groupRow.createCell(INDEX_CELL_TEAM_FIR + teamIndex * RANGE_GOURP_TEAM);
		XSSFCell cellS = groupRow.createCell(INDEX_CELL_TEAM_SEC + teamIndex * RANGE_GOURP_TEAM);
		XSSFCell cellT = groupRow.createCell(INDEX_CELL_TEAM_THI + teamIndex * RANGE_GOURP_TEAM);

		setStyle(style, cellF, cellS, cellT);

		cellF.setCellValue(cellValue);
	}

	private static void buildTeamRow(int teamIndex, int rowIndex, XSSFSheet teamSheet, String cellValue, XSSFCellStyle style) {
		XSSFRow teamNameRow = getRow(teamSheet, rowIndex);
		XSSFCell cellF = teamNameRow.createCell(INDEX_CELL_TEAM_FIR + teamIndex * RANGE_GOURP_TEAM);
		XSSFCell cellS = teamNameRow.createCell(INDEX_CELL_TEAM_SEC + teamIndex * RANGE_GOURP_TEAM);
		XSSFCell cellT = teamNameRow.createCell(INDEX_CELL_TEAM_THI + teamIndex * RANGE_GOURP_TEAM);
		
		setStyle(style, cellF, cellS, cellT);
		
		cellF.setCellValue(cellValue);
	}
	
	private static void buildTitleRow(XSSFWorkbook workbook, XSSFSheet sheet) {
		buildTitleCells(sheet.createRow(INDEX_SHEET_DAY), buildStyle(workbook, Color.GRAY));
	}

	private static void buildTitleCells(XSSFRow titleRow, XSSFCellStyle titleStyle) {
		XSSFCell[] cells = createCells(titleRow, LEN_DAY_SHEET_CELL, titleStyle);
		cells[0].setCellValue(FIELD_OBTAIN_PRAISE);
		cells[1].setCellValue(FIELD_USERNAME);
		cells[2].setCellValue(StringUtils.EMPTY);
		cells[3].setCellValue(FIELD_OBTAIN_ENCOURAGES);
		cells[4].setCellValue(FIELD_USERNAME);
		cells[5].setCellValue(StringUtils.EMPTY);
		cells[6].setCellValue(FIELD_GIVE_PRAISE);
		cells[7].setCellValue(FIELD_USERNAME);
		cells[8].setCellValue(StringUtils.EMPTY);
		cells[9].setCellValue(FIELD_GIVE_ENCOURAGES);
		cells[10].setCellValue(FIELD_USERNAME);
		cells[11].setCellValue(StringUtils.EMPTY);
	}
	
	private static XSSFCellStyle buildStyle(XSSFWorkbook workbook, Color color) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(new XSSFColor(color));
		
		return style;
	}

	private static void buildDataRows(XSSFSheet sheet, List<PraisePoint> obtainPraises, 
													   List<PraisePoint> obtainEncourages,
													   List<PraisePoint> givePraises, 
													   List<PraisePoint> giveEncourages) {
		int maxRows = maxRows(obtainPraises, obtainEncourages, givePraises, giveEncourages);
		for (int index = 0; index <= maxRows; index++) {
			XSSFRow dataRow = sheet.createRow(index + 1);
			
			buildDataCells(dataRow, index, obtainPraises, obtainEncourages, givePraises, giveEncourages);
		}
	}

	private static void buildDataCells(XSSFRow countRow, int index, List<PraisePoint> obtainPraises,
									   								List<PraisePoint> obtainEncourages,
									   								List<PraisePoint> givePraises,
									   								List<PraisePoint> giveEncourages) {
		PraisePoint obtainPraise = avoidNull(obtainPraises, index);
		PraisePoint obtainEncourage = avoidNull(obtainEncourages, index);
		PraisePoint givePraise = avoidNull(givePraises, index);
		PraisePoint giveEncourage = avoidNull(giveEncourages, index);
		
		XSSFCell[] cells = createCells(countRow, LEN_DAY_SHEET_CELL);
		if (obtainPraise != null) {
			cells[0].setCellValue(obtainPraise.getCount());
			cells[1].setCellValue(obtainPraise.getName());
		}

		if (obtainEncourage != null) {
			cells[3].setCellValue(obtainEncourage.getCount());
			cells[4].setCellValue(obtainEncourage.getName());
		}

		if (givePraise != null) {
			cells[6].setCellValue(givePraise.getCount());
			cells[7].setCellValue(givePraise.getName());
		}

		if (giveEncourage != null) {
			cells[9].setCellValue(giveEncourage.getCount());
			cells[10].setCellValue(giveEncourage.getName());
		}
	}
	
	private static XSSFRow getRow(XSSFSheet sheet, int rowNum) {
		return sheet.getRow(rowNum) == null ? sheet.createRow(rowNum) : sheet.getRow(rowNum);
	}
	
	private static XSSFCell[] createCells(XSSFRow row, int sum) {
		return createCells(row, sum, null);
	}

	private static XSSFCell[] createCells(XSSFRow row, int sum, XSSFCellStyle style) {
		XSSFCell[] cells = new XSSFCell[sum];
		for (int i = 0; i < sum; i++) {
			XSSFCell cell = row.createCell(i); 
			cells[i] = cell; 
			if (style != null) {
				cell.setCellStyle(style);
			}
		}
		
		return cells;
	}
	
	private static void setStyle(XSSFCellStyle style, XSSFCell... cells) {
		for (XSSFCell cell : cells) {
			cell.setCellStyle(style);
		}
	}
	
	private static int maxRows(List<?>... lists) {
		int max = 0;
		for (List<?> list : lists) {
			max = list.size() > max ? list.size() : max;
		}
		
		return max;
	}
	
	private static <T> T avoidNull(List<T> list, int index) {
		return index >= list.size() ? null : list.get(index);
	}
}
