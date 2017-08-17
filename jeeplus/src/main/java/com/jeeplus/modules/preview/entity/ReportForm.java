package com.jeeplus.modules.preview.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 预习报告单Entity
 * @author loyd
 * @version 2017-08-11
 */
public class ReportForm extends DataEntity<ReportForm> {
	
	private static final long serialVersionUID = 1L;
	private String reportFormNum;		// 任务单名称
	private String learningContent;		// 学习内容
	private String expandResources;		// 拓展资源
	private String discussionTopic;		// 讨论主题
	private String spendTime;		// 学习花费时间
	private String learnDifficulty;		// 学习难度
	private String learnTargerDegree;		// 学习目标完成度
	private String existProblem;		// 存在问题
	private String advice;		// 建议
	private String score;		// 预习报告单成绩
	private Date endTime;		// 预习报告截止提交时间
	private String correctFlag;		//学生姓名
	private String aBackup;		// 备用字段一
	private String bBackup;		// 备用字段二
	private String cBackup;		// 备用字段三
	private List<PreclassDuty> preclassDutyList = Lists.newArrayList();		// 子表列表
	private List<PrepracticeDuty> prepracticeDutyList = Lists.newArrayList();		// 子表列表
	
	public ReportForm() {
		super();
	}

	public ReportForm(String id){
		super(id);
	}

	@ExcelField(title="任务单名称", align=2, sort=6)
	public String getReportFormNum() {
		return reportFormNum;
	}

	public void setReportFormNum(String reportFormNum) {
		this.reportFormNum = reportFormNum;
	}
	
	@ExcelField(title="学习内容", align=2, sort=7)
	public String getLearningContent() {
		return learningContent;
	}

	public void setLearningContent(String learningContent) {
		this.learningContent = learningContent;
	}
	
	@ExcelField(title="拓展资源", align=2, sort=9)
	public String getExpandResources() {
		return expandResources;
	}

	public void setExpandResources(String expandResources) {
		this.expandResources = expandResources;
	}
	
	@ExcelField(title="讨论主题", align=2, sort=10)
	public String getDiscussionTopic() {
		return discussionTopic;
	}

	public void setDiscussionTopic(String discussionTopic) {
		this.discussionTopic = discussionTopic;
	}
	
	@ExcelField(title="学习花费时间", dictType="learning_spend_time", align=2, sort=11)
	public String getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}
	
	@ExcelField(title="学习难度", dictType="learning_difficulty", align=2, sort=12)
	public String getLearnDifficulty() {
		return learnDifficulty;
	}

	public void setLearnDifficulty(String learnDifficulty) {
		this.learnDifficulty = learnDifficulty;
	}
	
	@ExcelField(title="学习目标完成度", dictType="learning_target_degree", align=2, sort=13)
	public String getLearnTargerDegree() {
		return learnTargerDegree;
	}

	public void setLearnTargerDegree(String learnTargerDegree) {
		this.learnTargerDegree = learnTargerDegree;
	}
	
	@ExcelField(title="存在问题", align=2, sort=14)
	public String getExistProblem() {
		return existProblem;
	}

	public void setExistProblem(String existProblem) {
		this.existProblem = existProblem;
	}
	
	@ExcelField(title="建议", align=2, sort=15)
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
	@ExcelField(title="预习报告单成绩", dictType="preview_score", align=2, sort=17)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="预习报告截止提交时间不能为空")
	@ExcelField(title="预习报告截止提交时间", align=2, sort=18)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="批改标志", align=2, sort=19)
	public String getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(String correctFlag) {
		this.correctFlag = correctFlag;
	}
	
	@ExcelField(title="备用字段一", align=2, sort=20)
	public String getABackup() {
		return aBackup;
	}

	public void setABackup(String aBackup) {
		this.aBackup = aBackup;
	}
	
	@ExcelField(title="备用字段二", align=2, sort=21)
	public String getBBackup() {
		return bBackup;
	}

	public void setBBackup(String bBackup) {
		this.bBackup = bBackup;
	}
	
	@ExcelField(title="备用字段三", align=2, sort=22)
	public String getCBackup() {
		return cBackup;
	}

	public void setCBackup(String cBackup) {
		this.cBackup = cBackup;
	}
	
	public List<PreclassDuty> getPreclassDutyList() {
		return preclassDutyList;
	}

	public void setPreclassDutyList(List<PreclassDuty> preclassDutyList) {
		this.preclassDutyList = preclassDutyList;
	}
	public List<PrepracticeDuty> getPrepracticeDutyList() {
		return prepracticeDutyList;
	}

	public void setPrepracticeDutyList(List<PrepracticeDuty> prepracticeDutyList) {
		this.prepracticeDutyList = prepracticeDutyList;
	}
}