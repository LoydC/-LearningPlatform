/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.experimentreport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.experimentreport.entity.ExperimentReport;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.experimentreport.dao.ExperimentReportDao;

/**
 * 实验报告单Service
 * @author Loyd
 * @version 2017-08-13
 */
@Service
@Transactional(readOnly = true)
public class ExperimentReportService extends CrudService<ExperimentReportDao, ExperimentReport> {

	public ExperimentReport get(String id) {
		return super.get(id);
	}
	
	public List<ExperimentReport> findList(ExperimentReport experimentReport) {
		return super.findList(experimentReport);
	}
	
	public Page<ExperimentReport> findPage(Page<ExperimentReport> page, ExperimentReport experimentReport) {
		return super.findPage(page, experimentReport);
	}
	
	/*
	 * 查询老师的所有实验报告单
	 */
	public Page<ExperimentReport> findPageByTeacher(Page<ExperimentReport> page, ExperimentReport experimentReport) {
		experimentReport.setPage(page);
		page.setList(dao.findListByTeacher(experimentReport));
		return page;
	}
	/*
	 * 查询所有学生实验报告单
	 */
	public Page<ExperimentReport> findPageByStudent(Page<ExperimentReport> page, ExperimentReport experimentReport) {
		
		
		experimentReport.setPage(page);
		page.setList(dao.findListByStudent(experimentReport));	
		return page;
	}
	/*
	 * 查询学生的实验报告单
	 */
	public ExperimentReport findUniqueStudentReport(ExperimentReport experimentReport) {
		ExperimentReport experimentReport2 = dao.findUniqueStudentExperimentReport(experimentReport);
	
		return experimentReport2;

	}
	/*
	 * 将查询到的原有的老师的List进行遍历，若存在当前学生填写，则替换
	 */
	public List<ExperimentReport> convertTracherList(List<ExperimentReport> erfList){
		for(int i=0;i<erfList.size();i++){
			erfList.get(i).setCreateBy(UserUtils.getUser());
			ExperimentReport studentERF = findUniqueStudentReport(erfList.get(i));
			if(studentERF != null){
				erfList.set(i, studentERF);
			}
		}
		return erfList;
	}
	/*
	 * 将查找到的学生记录进行处理，若查找为空（学生端没有填写），则重新new，否则直接返回
	 * @param findRF 是调用findUniqueStudent后的那个
	 * @param reportForm 传给controller的那个
	 */
	public ExperimentReport createStudentForm(ExperimentReport findRF,ExperimentReport reportForm){
		if(findRF == null){
			ExperimentReport studentReportForm = new ExperimentReport();
						
			studentReportForm.setExperimentReportName(reportForm.getExperimentReportName());
			studentReportForm.setExperimentReportPath(reportForm.getExperimentReportPath());
			studentReportForm.setEndTime(reportForm.getEndTime());
			studentReportForm.setBackupFieldb(UserUtils.getUser().getLoginName());

			return studentReportForm;
		}else{
			return findRF;
		}
	}
	
	@Transactional(readOnly = false)
	public void save(ExperimentReport experimentReport) {
		super.save(experimentReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExperimentReport experimentReport) {
		super.delete(experimentReport);
	}
	
	
	
	
}