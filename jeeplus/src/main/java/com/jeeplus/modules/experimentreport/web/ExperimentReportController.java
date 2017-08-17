/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.experimentreport.web;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.experimentreport.entity.ExperimentReport;
import com.jeeplus.modules.experimentreport.service.ExperimentReportService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 实验报告单Controller
 * 
 * @author Loyd
 * @version 2017-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/experimentreport/experimentReport")
public class ExperimentReportController extends BaseController {

	@Autowired
	private ExperimentReportService experimentReportService;

	@ModelAttribute
	public ExperimentReport get(@RequestParam(required = false) String id) {
		ExperimentReport entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = experimentReportService.get(id);
		}
		if (entity == null) {
			entity = new ExperimentReport();
		}
		return entity;
	}

	/**
	 * 实验报告列表页面
	 */
	@RequiresPermissions("experimentreport:experimentReport:list")
	@RequestMapping(value = { "list", "" })
	public String list(ExperimentReport experimentReport, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		experimentReport.setCreateBy(UserUtils.getUser());
		Page<ExperimentReport> page = experimentReportService.findPage(new Page<ExperimentReport>(request, response),
				experimentReport);
		model.addAttribute("page", page);
		return "modules/experimentreport/experimentReportList";
	}

	/**
	 * 查看，增加，编辑实验报告表单页面
	 */
	// @RequiresPermissions(value={"experimentreport:experimentReport:view","experimentreport:experimentReport:add","experimentreport:experimentReport:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ExperimentReport experimentReport, Model model) {
		model.addAttribute("experimentReport", experimentReport);
		return "modules/experimentreport/experimentReportForm";
	}

	@Autowired
	private HttpServletRequest request;

	/*
	 * 上传实验报告单文件
//上传文件
    	if (!file.isEmpty()) {
    		String displayPath = educationResource.getDisplayPath();
			String path = request.getServletContext().getRealPath("/file/" + displayPath);
			String filename = file.getOriginalFilename();
			File filepath = new File(path, filename);
			if (!filepath.getParentFile().exists()) {
				filepath.getParentFile().mkdirs();
			}
			file.transferTo(new File(path + File.separator + filename));

			educationResource.setServerPath("/file/" + displayPath +"/"+ filename);
		}
	 */
	public String fileUpload(MultipartFile file) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				String displayPath = UserUtils.getUser().getPhone();
				// 文件保存路径
				String filePath = request.getServletContext().getRealPath("/experimentReport/" + displayPath);
				String filename = file.getOriginalFilename();
				File filepath = new File(filePath, filename);
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				// 转存文件
				file.transferTo(new File(filePath + File.separator + filename));
				
				return "/experimentReport/"+ displayPath +"/"+ filename;
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

	/*
	 * 学生端查看实验报告单列表页面
	 */
	@RequestMapping(value = "student")
	public String studentList(ExperimentReport experimentReport, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		experimentReport.setCreateBy(UserUtils.getUser());
		Page<ExperimentReport> page = experimentReportService
				.findPageByTeacher(new Page<ExperimentReport>(request, response), experimentReport);
		page.setList(experimentReportService.convertTracherList(page.getList()));

		model.addAttribute("page", page);
		return "modules/experimentreport/studentExperimentReportList";
	}

	/**
	 * 学生端点击报告单按钮
	 */
	@RequestMapping(value = "studentForm")
	public String studentForm(ExperimentReport experimentReport, Model model) {

		// 创建查询的RF
		ExperimentReport queryRF = new ExperimentReport();
		queryRF.setExperimentReportName(experimentReport.getExperimentReportName());
		queryRF.setCreateBy(UserUtils.getUser());

		// 根据查询条件，找到那个学生RF（或者为空）
		ExperimentReport findRF = experimentReportService.findUniqueStudentReport(queryRF);

		// 再次处理一下找到的RF，若为空重新new
		findRF = experimentReportService.createStudentForm(findRF, experimentReport);

		model.addAttribute("experimentReport", findRF);

		return "modules/preview/studentReportFormForm";
	}

	/**
	 * 显示报告单对应的所有学生的报告
	 */
	@RequestMapping(value = "findAllReport")
	public String findAllReport(ExperimentReport experimentReport, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// experimentReport.getCreateBy().setPhone("vb");
		// System.out.println(experimentReport.getBackupFieldc());
		Page<ExperimentReport> page = experimentReportService
				.findPageByStudent(new Page<ExperimentReport>(request, response), experimentReport);
		model.addAttribute("page", page);
		return "modules/experimentreport/allStudentExperimentReportList";
	}

	/**
	 * 保存实验报告
	 */
	@RequiresPermissions(value = { "experimentreport:experimentReport:add",
			"experimentreport:experimentReport:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(@RequestParam("file") MultipartFile file, ExperimentReport experimentReport, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		experimentReport.setExperimentReportPath(fileUpload(file));
		
		if (!beanValidator(model, experimentReport)) {
			return form(experimentReport, model);
		}
		if (!experimentReport.getIsNewRecord()) {// 编辑表单保存
			ExperimentReport t = experimentReportService.get(experimentReport.getId());// 从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(experimentReport, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			experimentReportService.save(t);// 保存
		} else {// 新增表单保存
			experimentReportService.save(experimentReport);// 保存
		}
		addMessage(redirectAttributes, "保存实验报告成功");
		// if(experimentReport.getCreateBy().getUserType().equals("3")){
		// return
		// "redirect:"+Global.getAdminPath()+"/experimentreport/studentExperimentReportList";
		// }else{
		// return
		// "redirect:"+Global.getAdminPath()+"/experimentreport/findAllReport?id="+experimentReport.getId();
		// }

		return "redirect:" + Global.getAdminPath() + "/experimentreport/experimentReport/?repage";
	}

	/**
	 * 保存预习报告单
	 */
	@RequestMapping(value = "studentSave")
	public String studentSave(ExperimentReport experimentReport, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		if (!beanValidator(model, experimentReport)) {
			return studentForm(experimentReport, model);
		}
		if (!experimentReport.getIsNewRecord()) {// 编辑表单保存
			ExperimentReport t = experimentReportService.get(experimentReport.getId());// 从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(experimentReport, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			experimentReportService.save(t);// 保存
		} else {// 新增表单保存
			experimentReport.setBackupFieldb(UserUtils.getUser().getName());

			experimentReportService.save(experimentReport);// 保存

		}
		addMessage(redirectAttributes, "保存预习报告单成功");
		return "redirect:" + Global.getAdminPath() + "/preview/reportForm/student?repage";
	}

	/**
	 * 删除实验报告
	 */
	@RequiresPermissions("experimentreport:experimentReport:del")
	@RequestMapping(value = "delete")
	public String delete(ExperimentReport experimentReport, RedirectAttributes redirectAttributes) {
		experimentReportService.delete(experimentReport);
		addMessage(redirectAttributes, "删除实验报告成功");
		return "redirect:" + Global.getAdminPath() + "/experimentreport/experimentReport/?repage";
	}

	/**
	 * 批量删除实验报告
	 */
	@RequiresPermissions("experimentreport:experimentReport:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			experimentReportService.delete(experimentReportService.get(id));
		}
		addMessage(redirectAttributes, "删除实验报告成功");
		return "redirect:" + Global.getAdminPath() + "/experimentreport/experimentReport/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("experimentreport:experimentReport:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ExperimentReport experimentReport, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "实验报告" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<ExperimentReport> page = experimentReportService
					.findPage(new Page<ExperimentReport>(request, response, -1), experimentReport);
			new ExportExcel("实验报告", ExperimentReport.class).setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出实验报告记录失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/experimentreport/experimentReport/?repage";
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("experimentreport:experimentReport:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ExperimentReport> list = ei.getDataList(ExperimentReport.class);
			for (ExperimentReport experimentReport : list) {
				try {
					experimentReportService.save(experimentReport);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条实验报告记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条实验报告记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入实验报告失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/experimentreport/experimentReport/?repage";
	}

	/**
	 * 下载导入实验报告数据模板
	 */
	@RequiresPermissions("experimentreport:experimentReport:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "实验报告数据导入模板.xlsx";
			List<ExperimentReport> list = Lists.newArrayList();
			new ExportExcel("实验报告数据", ExperimentReport.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/experimentreport/experimentReport/?repage";
	}

}