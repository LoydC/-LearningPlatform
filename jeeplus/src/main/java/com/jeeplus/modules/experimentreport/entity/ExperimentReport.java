/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.experimentreport.entity;


import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 实验报告单Entity
 * @author Loyd
 * @version 2017-08-13
 */
public class ExperimentReport extends DataEntity<ExperimentReport> {
	
	private static final long serialVersionUID = 1L;
	private String experimentReportName;		// 实验报告名称
	private String experimentReportPath;		// 实验报告路径
	private String score;		// 成绩
	private Date endTime;		// 截止时间
	private String userType;		// 用户类型
	private String backupFielda;		// 备用字段一学生自己的文件路径
	private String backupFieldb;		// 备用字段二学生姓名
	private String backupFieldc;		// 备用字段三课程名
	
	public ExperimentReport() {
		super();
	}

	public ExperimentReport(String id){
		super(id);
	}

	@ExcelField(title="实验报告名称", align=2, sort=6)
	public String getExperimentReportName() {
		return experimentReportName;
	}

	public void setExperimentReportName(String experimentReportName) {
		this.experimentReportName = experimentReportName;
	}
	
	@ExcelField(title="实验报告路径", align=2, sort=7)
	public String getExperimentReportPath() {
		return experimentReportPath;
	}

	public void setExperimentReportPath(String experimentReportPath) {
		this.experimentReportPath = experimentReportPath;
	}
	
	@ExcelField(title="成绩", dictType="experiment_score	experiment_score", align=2, sort=8)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="实验报告截止提交时间不能为空")
	@ExcelField(title="截止时间", align=2, sort=9)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="用户类型", align=2, sort=10)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@ExcelField(title="备用字段一", align=2, sort=11)
	public String getBackupFielda() {
		return backupFielda;
	}

	public void setBackupFielda(String backupFielda) {
		this.backupFielda = backupFielda;
	}
	
	@ExcelField(title="备用字段二", align=2, sort=12)
	public String getBackupFieldb() {
		return backupFieldb;
	}

	public void setBackupFieldb(String backupFieldb) {
		this.backupFieldb = backupFieldb;
	}
	
	@ExcelField(title="备用字段三", align=2, sort=13)
	public String getBackupFieldc() {
		return backupFieldc;
	}

	public void setBackupFieldc(String backupFieldc) {
		this.backupFieldc = backupFieldc;
	}
	
}