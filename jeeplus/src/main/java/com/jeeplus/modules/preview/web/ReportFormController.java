package com.jeeplus.modules.preview.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.filemanagement.entity.CourseFile;
import com.jeeplus.modules.filemanagement.entity.EducationResource;
import com.jeeplus.modules.filemanagement.service.EducationResourceService;
import com.jeeplus.modules.filemanagement.service.FileManagementService;
import com.jeeplus.modules.preview.entity.PreclassDuty;
import com.jeeplus.modules.preview.entity.PrepracticeDuty;
import com.jeeplus.modules.preview.entity.ReportForm;
import com.jeeplus.modules.preview.service.ReportFormService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 预习报告单Controller
 * @author loyd
 * @version 2017-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/preview/reportForm")
public class ReportFormController extends BaseController {

	@Autowired
	private FileManagementService fileManagementService;

    @Autowired
    private EducationResourceService educationResourceService;
	
	@Autowired
	private ReportFormService reportFormService;
	
	@ModelAttribute
	public ReportForm get(@RequestParam(required=false) String id) {
		ReportForm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportFormService.get(id);
		}
		if (entity == null){
			entity = new ReportForm();
		}
		return entity;
	}
	
	/**
	 * 预习报告单列表页面
	 */
	@RequiresPermissions("preview:reportForm:list")
	@RequestMapping(value = {"list", ""})
	public String list(ReportForm reportForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		reportForm.setCreateBy(UserUtils.getUser());
		Page<ReportForm> page = reportFormService.findPageByTeacher(new Page<ReportForm>(request, response), reportForm); 
		model.addAttribute("page", page);
		return "modules/preview/reportFormList";
	}

	/**
	 * 显示报告单对应的所有学生的报告
	 */
	@RequestMapping(value = "findAllReport")
	public String findAllReport(ReportForm reportForm, HttpServletRequest request, HttpServletResponse response, Model model) {	
		Page<ReportForm> page = reportFormService.findPageByStudent(new Page<ReportForm>(request, response), reportForm); 
		model.addAttribute("page", page);
		return "modules/preview/allStudentreportFormList";
	}
		

	/**
	 * 学生端查看预习报告单列表页面
	 */
	@RequestMapping(value = "student")
	public String studentList(ReportForm reportForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		reportForm.setCreateBy(UserUtils.getUser());
		Page<ReportForm> page = reportFormService.findPageByTeacher(new Page<ReportForm>(request, response), reportForm); 
		page.setList(reportFormService.convertTracherList(page.getList()));
	
		model.addAttribute("page", page);
		return "modules/preview/studentReportFormList";
	}
	
	/**
	 * 学生端点击报告单按钮
	 */
	@RequestMapping(value = "studentForm")
	public String studentForm(ReportForm reportForm, Model model) {
		
		//创建查询的RF
		ReportForm queryRF  = new ReportForm();
		queryRF.setReportFormNum(reportForm.getReportFormNum());
		queryRF.setCreateBy(UserUtils.getUser());
		
		//根据查询条件，找到那个学生RF（或者为空）
		ReportForm findRF = reportFormService.findUniqueStudentReport(queryRF);
		
		//再次处理一下找到的RF，若为空重新new
		findRF = reportFormService.createStudentForm(findRF, reportForm);
		
		model.addAttribute("reportForm", findRF);

		return "modules/preview/studentReportFormForm";
	}
	
	/**
	 * 查看，增加，编辑预习报告单表单页面
	 */
//	@RequiresPermissions(value={"preview:reportForm:view","preview:reportForm:add","preview:reportForm:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ReportForm reportForm, Model model) {
		model.addAttribute("reportForm", reportForm);	
		return "modules/preview/reportFormForm";
	}

	/**
	 * 保存预习报告单
	 */
	@RequestMapping(value = "save")
	public String save(ReportForm reportForm, Model model, RedirectAttributes redirectAttributes) throws Exception{
				
		List<PreclassDuty> preClassList =  reportForm.getPreclassDutyList();
		
		if(preClassList != null && preClassList.size() != 0 && preClassList.get(0).getLearningResource() != null){	
			for(PreclassDuty pd : preClassList){
				String[] resourceIds = pd.getLearningResource().getId().split(",");
				StringBuilder sb = new StringBuilder();
				for(String id : resourceIds){
					sb.append(educationResourceService.get(id).getResourceName() + " ");
				}
				pd.setLearningResourceText(sb.toString());
			}
		}
		
		reportForm.setPreclassDutyList(preClassList);
			
		if (!beanValidator(model, reportForm)){
			return form(reportForm, model);
		}
		if(!reportForm.getIsNewRecord()){//编辑表单保存
			ReportForm t = reportFormService.get(reportForm.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(reportForm, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			reportFormService.save(t);//保存
		}else{//新增表单保存
			reportFormService.save(reportForm);//保存
		}
		addMessage(redirectAttributes, "保存预习报告单成功");
		
		if(reportForm.getCreateBy().getUserType().equals("3")){
			return "redirect:"+Global.getAdminPath()+"/preview/reportForm";
		}else{
			return "redirect:"+Global.getAdminPath()+"/preview/reportForm/findAllReport?id="+reportForm.getId();
		}
		
	}
	

	/**
	 * 保存预习报告单
	 */
	@RequestMapping(value = "studentSave")
	public String studentSave(ReportForm reportForm, Model model, RedirectAttributes redirectAttributes) throws Exception{		
		if (!beanValidator(model, reportForm)){
			return studentForm(reportForm, model);
		}
		if(!reportForm.getIsNewRecord()){//编辑表单保存
			ReportForm t = reportFormService.get(reportForm.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(reportForm, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			reportFormService.save(t);//保存
		}else{//新增表单保存
			reportForm.setCorrectFlag(UserUtils.getUser().getName());
			
			reportFormService.save(reportForm);//保存
			ReportForm form = reportFormService.findBycourseNameandUsertype(reportForm);
			form.setCorrectFlag("1");
			reportFormService.save(form);//保存
			
		}
		addMessage(redirectAttributes, "保存预习报告单成功");
		return "redirect:"+Global.getAdminPath()+"/preview/reportForm/student?repage";
	}
	
	/**
	 * 删除预习报告单
	 */
	@RequiresPermissions("preview:reportForm:del")
	@RequestMapping(value = "delete")
	public String delete(ReportForm reportForm, RedirectAttributes redirectAttributes) {
		reportFormService.delete(reportForm);
		addMessage(redirectAttributes, "删除预习报告单成功");
		return "redirect:"+Global.getAdminPath()+"/preview/reportForm/?repage";
	}
	
	/**
	 * 批量删除预习报告单
	 */
	@RequiresPermissions("preview:reportForm:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			reportFormService.delete(reportFormService.get(id));
		}
		addMessage(redirectAttributes, "删除预习报告单成功");
		return "redirect:"+Global.getAdminPath()+"/preview/reportForm/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("preview:reportForm:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ReportForm reportForm, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "预习报告单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ReportForm> page = reportFormService.findPage(new Page<ReportForm>(request, response, -1), reportForm);
    		new ExportExcel("预习报告单", ReportForm.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出预习报告单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/preview/reportForm/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("preview:reportForm:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ReportForm> list = ei.getDataList(ReportForm.class);
			for (ReportForm reportForm : list){
				try{
					reportFormService.save(reportForm);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条预习报告单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条预习报告单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入预习报告单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/preview/reportForm/?repage";
    }
	
	/**
	 * 下载导入预习报告单数据模板
	 */
	@RequiresPermissions("preview:reportForm:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "预习报告单数据导入模板.xlsx";
    		List<ReportForm> list = Lists.newArrayList(); 
    		new ExportExcel("预习报告单数据", ReportForm.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/preview/reportForm/?repage";
    }
	
	/**
	 * 获取机构JSON数据。
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade
	 *            显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			@RequestParam(required = false) String type, @RequestParam(required = false) Long grade,
			@RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();

		List<EducationResource> resourceList = educationResourceService.findList(new EducationResource());
		CourseFile cf = fileManagementService.getCourseFileList(resourceList);

		return traverseFolder(cf,mapList);
	}

	public List<Map<String, Object>> traverseFolder(CourseFile cf,List<Map<String, Object>> mapList) {

		Map<String, Object> map = Maps.newHashMap();
		map.put("id", cf.getId());
		map.put("pId", cf.getPid());
		map.put("pIds", cf.getPids());
		map.put("name", cf.getText());
	
		mapList.add(map);
		
		List<CourseFile> files = cf.getChildren();

		if(files != null){
			for (CourseFile file2 : files) {
				traverseFolder(file2,mapList);
			}
		}
		
		return mapList;
	
	}
	

}
