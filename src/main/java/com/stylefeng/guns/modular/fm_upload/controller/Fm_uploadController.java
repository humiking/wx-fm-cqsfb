package com.stylefeng.guns.modular.fm_upload.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.AlertSystemErrorEnum;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.TipMessageException;
import com.stylefeng.guns.common.persistence.util.JsonResponse;
import com.stylefeng.guns.modular.system.service.IWxFmListService;
import com.stylefeng.guns.modular.system.service.impl.WxFmListServiceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 电台上传控制器
 *
 * @author dongzhong
 * @Date 2018-03-05 13:41:08
 */
@Controller
@RequestMapping("/fm_upload")
public class Fm_uploadController extends BaseController {
	
	private static final Logger logger =LoggerFactory.getLogger(Fm_uploadController.class);

    private String PREFIX = "/fm_upload/fm_upload/";
    
    @Autowired
    private IWxFmListService wxFmListService;

   
    
    /**
     * 获取电台上传列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam("limit") int limit,//每页条数
    		                 @RequestParam("offset") int offset,//当前页
    		                 @RequestParam(value = "FmName",required = false ,defaultValue= "") String FmName
    ) {
    	JsonResponse jsonResponse = new JsonResponse();
    	logger.info(FmName);
    	try {
    		Page<Map<String,Object>> page = new Page<>(offset, limit);
    		List<Map<String,Object>> data = wxFmListService.getFullList(page,FmName);
    		page.setRecords(data);
			return this.packForBT(page);
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
    	
    }
    /**
     * 跳转到电台上传首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "fm_upload.html";
    }

    /**
     * 跳转到添加电台上传
     */
    @RequestMapping("/fm_upload_add")
    public String fm_uploadAdd() {
        return PREFIX + "fm_upload_add.html";
    }

    /**
     * 跳转到修改电台上传
     */
    @RequestMapping("/fm_upload_update/{fm_uploadId}")
    public String fm_uploadUpdate(@PathVariable Integer fm_uploadId, Model model) {
        return PREFIX + "fm_upload_edit.html";
    }


    /**
     * 新增电台上传
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除电台上传
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改电台上传
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 电台上传详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
