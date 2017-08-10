package com.jeeplus.modules.preview.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.preview.dao.StudentRecordsDoubtDao;
import com.jeeplus.modules.preview.dao.StudentReportformRecordsDao;
import com.jeeplus.modules.preview.entity.PreclassDuty;
import com.jeeplus.modules.preview.entity.PrepracticeDuty;
import com.jeeplus.modules.preview.entity.ReportForm;
import com.jeeplus.modules.preview.entity.StudentRecordsDoubt;
import com.jeeplus.modules.preview.entity.StudentReportformRecords;


/**
 * 学生报告单记录Service
 * @author loyd
 * @version 2017-08-08
 */
@Service
@Transactional(readOnly = true)
public class StudentReportformRecordsService extends CrudService<StudentReportformRecordsDao, StudentReportformRecords> {

	@Autowired
	private StudentRecordsDoubtDao studentRecordsDoubtDao;
	
	public StudentReportformRecords get(String id) {
		StudentReportformRecords studentReportformRecords = super.get(id);
		studentReportformRecords.setStudentRecordsDoubtList(studentRecordsDoubtDao.findList(new StudentRecordsDoubt(studentReportformRecords)));
		return studentReportformRecords;
	}
	
	public List<StudentReportformRecords> findList(StudentReportformRecords studentReportformRecords) {
		return super.findList(studentReportformRecords);
	}
	
	public Page<StudentReportformRecords> findPage(Page<StudentReportformRecords> page, StudentReportformRecords studentReportformRecords) {
		return super.findPage(page, studentReportformRecords);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentReportformRecords studentReportformRecords) {
		super.save(studentReportformRecords);
		for (StudentRecordsDoubt studentRecordsDoubt : studentReportformRecords.getStudentRecordsDoubtList()){
			if (studentRecordsDoubt.getId() == null){
				continue;
			}
			if (StudentRecordsDoubt.DEL_FLAG_NORMAL.equals(studentRecordsDoubt.getDelFlag())){
				if (StringUtils.isBlank(studentRecordsDoubt.getId())){
					studentRecordsDoubt.setStudentReportformRecords(studentReportformRecords);
					studentRecordsDoubt.preInsert();
					studentRecordsDoubtDao.insert(studentRecordsDoubt);
				}else{
					studentRecordsDoubt.preUpdate();
					studentRecordsDoubtDao.update(studentRecordsDoubt);
				}
			}else{
				studentRecordsDoubtDao.delete(studentRecordsDoubt);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentReportformRecords studentReportformRecords) {
		super.delete(studentReportformRecords);
		studentRecordsDoubtDao.delete(new StudentRecordsDoubt(studentReportformRecords));
	}
	
	/*
	 * 将ReportForm类和StudentReportFormRecords类转换成Json
	 */
	public Map<String,Object> buildJson(ReportForm reportForm,StudentReportformRecords studentReportformRecords) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("reportformNum",reportForm.getReportFormNum());
		map.put("leaningcontent", reportForm.getLearningContent());
		
		List<Map<String,String>> preclassMapList = new ArrayList<Map<String,String>>();
		
		List<PreclassDuty> preClassDutyList = reportForm.getPreclassDutyList();
		List<StudentRecordsDoubt> doubtList = studentReportformRecords.getStudentRecordsDoubtList();
		
		for(int i=0;i<preClassDutyList.size();i++){
			Map<String,String> preClassMap = new HashMap<String, String>();
			preClassMap.put("preClassDutyId", preClassDutyList.get(i).getId());
			preClassMap.put("flag", "1");
			//preClassMap.put("learningResource",preClassDutyList.get(i).getLearningResource().getResourceName());
			preClassMap.put("learningTarget", preClassDutyList.get(i).getLearningTarget());
			preClassMap.put("questionAndThinking", preClassDutyList.get(i).getQuestionAndThinking());
			//preClassMap.put("doubt", doubtList.get(i).getDoubt());
			preclassMapList.add(preClassMap);
		}
		
		List<Map<String,String>> prePracticeMapList = new ArrayList<Map<String,String>>();
		
		List<PrepracticeDuty> prePracticeDutyList = reportForm.getPrepracticeDutyList();
		
		for(int i=0;i<prePracticeDutyList.size();i++){
			Map<String,String> prePracticeMap = new HashMap<String, String>();
			prePracticeMap.put("prePracticeDutyId", prePracticeDutyList.get(i).getId());
			prePracticeMap.put("flag", "0");
			//prePracticeMap.put("practiceDuty", prePracticeDutyList.get(i).getPracticeDuty().getResourceName());
			prePracticeMap.put("contentAndTarget", prePracticeDutyList.get(i).getContentAndTarget());
			//prePracticeMap.put("doubt", doubtList.get(i + preClassDutyList.size()).getDoubt());
			prePracticeMapList.add(prePracticeMap);
		}
		
		map.put("leaningData", preclassMapList);
		map.put("practiceDutyData", prePracticeMapList);
		map.put("spendTime", studentReportformRecords.getSpendTime());
		map.put("learnDifficulty", studentReportformRecords.getLearnDifficulty());
		map.put("learnTarget", studentReportformRecords.getLearnTarget());
		map.put("existProblem", studentReportformRecords.getExistProblem());
		map.put("advice", studentReportformRecords.getAdvice());
		
		return map;
	}
	
}