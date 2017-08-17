package com.jeeplus.modules.preview.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.preview.entity.ReportForm;

/**
 * 预习报告单DAO接口
 * @author loyd
 * @version 2017-08-11
 */
@MyBatisDao
public interface ReportFormDao extends CrudDao<ReportForm> {

	public List<ReportForm> findListByTeacher(ReportForm reportForm);
	
	public List<ReportForm> findListByStudent(ReportForm reportForm);
	
	public ReportForm findUniqueStudentReport(ReportForm reportForm);
	
	public ReportForm findBycourseNameandUsertype(ReportForm reportForm);
}