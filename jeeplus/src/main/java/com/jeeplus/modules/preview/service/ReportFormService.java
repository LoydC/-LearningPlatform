package com.jeeplus.modules.preview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.preview.entity.ReportForm;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.preview.dao.ReportFormDao;


import com.jeeplus.modules.preview.entity.PreclassDuty;
import com.jeeplus.modules.preview.dao.PreclassDutyDao;
import com.jeeplus.modules.preview.entity.PrepracticeDuty;
import com.jeeplus.modules.preview.dao.PrepracticeDutyDao;

/**
 * 预习报告单Service
 * @author loyd
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class ReportFormService extends CrudService<ReportFormDao, ReportForm> {

	@Autowired
	private PreclassDutyDao preclassDutyDao;
	@Autowired
	private PrepracticeDutyDao prepracticeDutyDao;
	
	public ReportForm get(String id) {
		ReportForm reportForm = super.get(id);
		reportForm.setPreclassDutyList(preclassDutyDao.findList(new PreclassDuty(reportForm)));
		reportForm.setPrepracticeDutyList(prepracticeDutyDao.findList(new PrepracticeDuty(reportForm)));
		return reportForm;
	}
	
	public List<ReportForm> findList(ReportForm reportForm) {
		return super.findList(reportForm);
	}
	
	public ReportForm findUniqueStudentReport(ReportForm reportForm) {
		ReportForm reportForm1 = dao.findUniqueStudentReport(reportForm);
		if(reportForm1!=null){
			reportForm1.setPreclassDutyList(preclassDutyDao.findList(new PreclassDuty(reportForm1)));
			reportForm1.setPrepracticeDutyList(prepracticeDutyDao.findList(new PrepracticeDuty(reportForm1)));
		}
		return reportForm1;

	}
	
	public Page<ReportForm> findPage(Page<ReportForm> page, ReportForm reportForm) {
		return super.findPage(page, reportForm);
	}
	
	public Page<ReportForm> findPageByTeacher(Page<ReportForm> page, ReportForm reportForm) {
		reportForm.setPage(page);
		page.setList(dao.findListByTeacher(reportForm));
		return page;
	}
	
	public Page<ReportForm> findPageByStudent(Page<ReportForm> page, ReportForm reportForm) {
		reportForm.setPage(page);
		page.setList(dao.findListByStudent(reportForm));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ReportForm reportForm) {
		super.save(reportForm);
		for (PreclassDuty preclassDuty : reportForm.getPreclassDutyList()){
			if (preclassDuty.getId() == null){
				continue;
			}
			if (PreclassDuty.DEL_FLAG_NORMAL.equals(preclassDuty.getDelFlag())){
				if (StringUtils.isBlank(preclassDuty.getId())){
					preclassDuty.setReportForm(reportForm);
					preclassDuty.preInsert();
					preclassDutyDao.insert(preclassDuty);
				}else{
					preclassDuty.preUpdate();
					preclassDutyDao.update(preclassDuty);
				}
			}else{
				preclassDutyDao.delete(preclassDuty);
			}
		}
		for (PrepracticeDuty prepracticeDuty : reportForm.getPrepracticeDutyList()){
			if (prepracticeDuty.getId() == null){
				continue;
			}
			if (PrepracticeDuty.DEL_FLAG_NORMAL.equals(prepracticeDuty.getDelFlag())){
				if (StringUtils.isBlank(prepracticeDuty.getId())){
					prepracticeDuty.setReportForm(reportForm);
					prepracticeDuty.preInsert();
					prepracticeDutyDao.insert(prepracticeDuty);
				}else{
					prepracticeDuty.preUpdate();
					prepracticeDutyDao.update(prepracticeDuty);
				}
			}else{
				prepracticeDutyDao.delete(prepracticeDuty);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportForm reportForm) {
		super.delete(reportForm);
		preclassDutyDao.delete(new PreclassDuty(reportForm));
		prepracticeDutyDao.delete(new PrepracticeDuty(reportForm));
	}
	
	/*
	 * 将查询到的原有的老师的List进行遍历，若存在当前学生填写，则替换
	 */
	public List<ReportForm> convertTracherList(List<ReportForm> rfList){
		for(int i=0;i<rfList.size();i++){
			rfList.get(i).setCreateBy(UserUtils.getUser());
			ReportForm studentRF = findUniqueStudentReport(rfList.get(i));
			if(studentRF != null){
				rfList.set(i, studentRF);
			}
		}
		return rfList;
	}
	
	/*
	 * 将查找到的学生记录进行处理，若查找为空（学生端没有填写），则重新new，否则直接返回
	 * @param findRF 是调用findUniqueStudent后的那个
	 * @param reportForm 传给controller的那个
	 */
	public ReportForm createStudentForm(ReportForm findRF,ReportForm reportForm){
		if(findRF == null){
			ReportForm studentReportForm = new ReportForm();
						
			studentReportForm.setReportFormNum(reportForm.getReportFormNum());
			studentReportForm.setLearningContent(reportForm.getLearningContent());
			studentReportForm.setEndTime(reportForm.getEndTime());
			
			List<PreclassDuty> preClassList = new ArrayList<PreclassDuty>();
			for(PreclassDuty pd : reportForm.getPreclassDutyList()){
				PreclassDuty newPd = new PreclassDuty();
				newPd.setLearningResource(pd.getLearningResource());
				newPd.setLearningTarget(pd.getLearningTarget());
				newPd.setQuestionAndThinking(pd.getQuestionAndThinking());
				newPd.setDoubt(pd.getDoubt());
				newPd.setLearningResourceText(pd.getLearningResourceText());
				preClassList.add(newPd);
			}
			studentReportForm.setPreclassDutyList(preClassList);
			
			List<PrepracticeDuty> prePracticeList = new ArrayList<PrepracticeDuty>();
			for(PrepracticeDuty pd : reportForm.getPrepracticeDutyList()){
				PrepracticeDuty newPd = new PrepracticeDuty();
				newPd.setPrepracticeDuty(pd.getPrepracticeDuty());
				newPd.setContentAndTarget(pd.getContentAndTarget());
				newPd.setDoubt(pd.getDoubt());
				prePracticeList.add(newPd);
			}
			studentReportForm.setPrepracticeDutyList(prePracticeList);
			
			studentReportForm.setExpandResources(reportForm.getExpandResources());
			studentReportForm.setDiscussionTopic(reportForm.getDiscussionTopic());
			studentReportForm.setABackup(UserUtils.getUser().getLoginName());

			return studentReportForm;
		}else{
			return findRF;
		}
	}
	
	/*
	 * 根据报告单名和用户类型查找对应的报告单
	 */
	public ReportForm findBycourseNameandUsertype(ReportForm form){
		ReportForm form2 = dao.findBycourseNameandUsertype(form);
		System.out.println(form2.getCreateBy().getUserType());
		return form2;
	
	}

}