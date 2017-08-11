package com.jeeplus.modules.filemanagement.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.filemanagement.entity.EducationResource;
import com.jeeplus.modules.filemanagement.entity.UserWatchBehavior;
import com.jeeplus.modules.filemanagement.service.EducationResourceService;

/**
 * 文件管理Controller
 * 
 * @author loyd
 * @version 2017-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/video")
public class VideoController extends BaseController {
	
    @Autowired
    private EducationResourceService educationResourceService;
	
    @ModelAttribute
    public EducationResource get(@RequestParam(required=false) String id) {
        EducationResource entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = educationResourceService.get(id);
        }
        if (entity == null){
            entity = new EducationResource();
        }
        return entity;
    }
    
    
    /*
     * 播放视频
     */
    @RequestMapping(value = "play")
	public String play(String videoId,String videoPath, HttpServletRequest request, HttpServletResponse response,Model model) {
		
    	//EducationResource er = educationResourceService.get(videoId);
    	videoPath = "/jeeplus" + videoPath;
    	try {
			videoPath= java.net.URLDecoder.decode(videoPath , "UTF-8");	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
    	
    	model.addAttribute("videoId",videoId);
    	//model.addAttribute("videoPath","http://video-js.zencoder.com/oceans-clip.mp4");
		model.addAttribute("videoPath", videoPath);

		System.out.println("videoId：" +videoId + "  videoPath：" + videoPath);
		
    	return "modules/filemanagement/video";

	}

	/*
	 * 点击教学资源展示的form
	 */
    @RequestMapping(value = "form")
    public String form(String videoId, HttpServletRequest request, HttpServletResponse response,Model model) {
        model.addAttribute("educationResource", educationResourceService.get(videoId));
        model.addAttribute("videoId",videoId);
        model.addAttribute("videoPath",educationResourceService.get(videoId).getServerPath());
        return "modules/filemanagement/videoResourceForm";
    }
    
    /*
     * 接受前台返回的视频观看情况的json
     */
	@RequestMapping(value={ "data"})
	public String saveVideoplay(@RequestBody String userWatchBehavior,Model model){
		JSONObject jsonObj = JSONObject.parseObject(userWatchBehavior);	
		UserWatchBehavior userBehavior = JSONObject.toJavaObject(jsonObj, UserWatchBehavior.class);
		System.out.println("timeupdate"+userBehavior.getTimeupdate());
		System.out.println("palylist"+userBehavior.getPlayList());  
		System.out.println("pauselist"+userBehavior.getPauseList());

		return adminPath; 
		
	}
}