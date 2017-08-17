/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.experimentreport.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.experimentreport.entity.ExperimentReport;

/**
 * 实验报告单DAO接口
 * @author Loyd
 * @version 2017-08-13
 */
@MyBatisDao
public interface ExperimentReportDao extends CrudDao<ExperimentReport> {
	
	public List<ExperimentReport> findListByTeacher(ExperimentReport experimentReport);
	
	public List<ExperimentReport> findListByStudent(ExperimentReport experimentReport);
	
	public ExperimentReport findUniqueStudentExperimentReport(ExperimentReport experimentReport);
	
	

	
}