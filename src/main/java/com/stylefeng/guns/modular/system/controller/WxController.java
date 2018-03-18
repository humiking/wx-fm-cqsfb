package com.stylefeng.guns.modular.system.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.ResultCodeEnum;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.model.WxFmList;
import com.stylefeng.guns.common.persistence.util.JsonResponse;
import com.stylefeng.guns.modular.system.service.IWxFmListService;
import com.stylefeng.guns.modular.system.service.IWxMp3urlListService;
import com.stylefeng.guns.modular.system.service.IWxService;
import com.stylefeng.guns.modular.system.service.impl.WxFmListServiceImpl;
import com.stylefeng.guns.modular.system.service.impl.WxMp3urlListServiceImpl;

@Controller
@RequestMapping("/wx")
public class WxController extends BaseController{
	private final static Logger logger = LoggerFactory.getLogger(WxController.class);
	
	@Autowired
	private IWxService wxServiceImpl;
	
	@Autowired
	private IWxFmListService wxFmListService;
	
	@Autowired
	private IWxMp3urlListService wxMp3urlListService;
	
	@RequestMapping("/banner")
	@ResponseBody
	public JsonResponse home(@RequestParam(value = "numbersOfBanners", required = false, defaultValue = "3") int numbersOfBanners) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
			logger.info("调用banner");
			List<String> data=wxServiceImpl.getBanners(numbersOfBanners);
			return jsonResponse.setSuccessful().setData(data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(ResultCodeEnum.SYSTEM_OPERATION_ERROR.getMessage());
		}
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public JsonResponse list(@RequestParam(value = "current",required = false,defaultValue = "1") int current,
			                 @RequestParam(value = "size",required = false,defaultValue = "10") int size){
		JsonResponse jsonResponse = new JsonResponse();
		try {
			logger.info("调用list");
			Page<WxFmList> page = new Page<WxFmList>(current, size);
			Map<String,Object> data = wxFmListService.list(page);
			data.put("total", page.getTotal());
			return jsonResponse.setSuccessful().setData(data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(ResultCodeEnum.SYSTEM_OPERATION_ERROR.getMessage());
		}
	}
	
	//添加电台音乐
	@RequestMapping("add")
	@ResponseBody
	public JsonResponse add(){
		return null;
	}

	//编辑电台音乐
	@RequestMapping()
	@ResponseBody
	public JsonResponse update(){
		return null;
	}
	
	//上传海报
	/**
	 * 上传文件到阿里云OSS上
	 *  创建内容: 1）上传文件到OSS中 
	 *  2） 将文件名存储到session中
	 *
	 * @param request
	 * @param response
	 * @return 例子：{"filename":https://yjj-img-www.oss-cn-hangzhou.aliyuncs.com/15087254452311508725445231.jpg}
	 *  method = RequestMethod.POST
	 */
	@RequestMapping("/uploadPoster")
	@ResponseBody
	public JsonResponse uploadPoster() {
		JsonResponse jsonResponse = new JsonResponse();
		return null;
	}
	
	//上传音乐
	@RequestMapping("uploadMusic")
	@ResponseBody
	public JsonResponse uploadMusic() {
		JsonResponse jsonResponse = new JsonResponse();
		return null;
	}
}
