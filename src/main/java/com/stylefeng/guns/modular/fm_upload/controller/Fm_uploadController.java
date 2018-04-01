package com.stylefeng.guns.modular.fm_upload.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.AlertSystemErrorEnum;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.TipMessageException;
import com.stylefeng.guns.common.persistence.util.JsonResponse;
import com.stylefeng.guns.common.persistence.util.OSSUploadUtil;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.service.IWxFmListService;

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
    
    @Autowired
    private OSSUploadUtil oSSUploadUtil;

   
    
    /**
     * 获取电台上传列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(
    		           @RequestParam(value = "FmName",required = false ,defaultValue= "") String FmName,//
    		           @RequestParam(value = "publishStatus",required = false,defaultValue= "-1") Integer publishStatus,//发布状态：0：未发布 5：已发布 10：已下架
    		           @RequestParam(value = "minCreateTime",required = false,defaultValue= "") String minCreateTime,//创建时间下限
    		           @RequestParam(value = "maxCreateTime",required = false,defaultValue= "") String maxCreateTime,//创建时间上限
    		           @RequestParam(value = "minPublishTime",required = false,defaultValue= "") String minPublishTime,//发布时间下限
    		           @RequestParam(value = "maxPublishTime",required = false,defaultValue= "") String maxPublishTime,//发布时间上限
    		           @RequestParam(value = "sortType",required = false,defaultValue= "1") int sortType//排序方式 1:权重倒序  2:创建时间倒序 3:创建时间升序  4:发布时间倒序 5:发布时间升序
    ) {
    	JsonResponse jsonResponse = new JsonResponse();
    	try {
    		Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
    		List<Map<String,Object>> data = wxFmListService.getFullList(page,FmName,publishStatus,minCreateTime,maxCreateTime,minPublishTime,maxPublishTime,sortType);
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
     * 删除电台上传
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("fmId") long fmId
    		) {
    	JsonResponse jsonResponse = new JsonResponse();
    	try {
    		wxFmListService.delete(fmId);
			return jsonResponse.setSuccessful();
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
    }

    /**
     * 电台上传详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(@RequestParam("fmId") long  fmId 
                         ) {
    	JsonResponse jsonResponse = new JsonResponse();
    	try {
			Map<String,Object> data = wxFmListService.detail(fmId);
    		return jsonResponse.setSuccessful().setData(data);
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
    }
    
	/**
	 * 添加电台音乐
	 */
	@RequestMapping("/add")
	@ResponseBody
	public JsonResponse add(
			                @RequestParam("name") String name,
			                @RequestParam(value = "poster",required = false,defaultValue = "") String poster,
			                @RequestParam("artistorName") String artistorName,
			                @RequestParam("totalDuration") long totalDuration,
			                @RequestParam("weight") Integer weight,
			                @RequestParam(value = "urlAndDuration",required = false,defaultValue = "") String urlAndDuration
			                
	){
		JsonResponse jsonResponse = new JsonResponse();
		try {
			wxFmListService.add(name,poster,artistorName,totalDuration,weight,urlAndDuration);
			return jsonResponse.setSuccessful();
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
	}

	/**
	 * 编辑电台音乐
	 */
	@RequestMapping("/update")
	@ResponseBody
	public JsonResponse update(
			@RequestParam("name") String name,
            @RequestParam("poster") String poster,
            @RequestParam("artistorName") String artistorName,
            @RequestParam("totalDuration") long totalDuration,
            @RequestParam("weight") Integer weight,
            @RequestParam("urlAndDuration") String urlAndDuration,
            @RequestParam("fmId") long fmId
			){
		JsonResponse jsonResponse = new JsonResponse();
		try {
			wxFmListService.update(name,poster,artistorName,totalDuration,weight,urlAndDuration,fmId);
			return jsonResponse.setSuccessful();
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
	}
	
	/**
	 * 发布或下架
	 */
	@RequestMapping("/updatePublishStatus")
	@ResponseBody
	public JsonResponse updatePublishStatus(@RequestParam("publishStatus") Integer publishStatus,
			                                @RequestParam("fmId") long fmId ){
		JsonResponse jsonResponse = new JsonResponse();
		try {
			wxFmListService.updatePublishStatus(publishStatus,fmId);
			return jsonResponse.setSuccessful();
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
		
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
	public JsonResponse uploadPoster(@RequestPart("file") MultipartFile file, 
			                         ModelMap modelMap,
			                         HttpServletRequest request,
			                         HttpServletResponse response
			                         ) {
		JsonResponse jsonResponse = new JsonResponse();
		String path = null;
		String oldPath = request.getParameter("oldPath");
		try {
			if(request instanceof MultipartHttpServletRequest){
				logger.info("看该文件是否是MultipartHttpServletRequest");
				if(file == null){
					logger.info("请求中没有文件。。。");
					logger.info("request file null oldPath:"+oldPath);
					return jsonResponse.setError("请求中没有文件！");
				}
				logger.info("request file name :"+file.getName()+","+file.getOriginalFilename()+","+file.getSize());
				path = oSSUploadUtil.uploadFile("", file);
				
				if(StringUtils.isNullOrEmpty(StringUtils.trim(oldPath))){
					String key = oldPath.split("/")[oldPath.split("/").length-1];
					oSSUploadUtil.removeFile("", key);
				}				
				
			} else{
				logger.error("no right request!");
			}
			modelMap.addAttribute("images", path);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("filename", path);
			logger.info("上传文件接口返回数据，result:"+result.toString());
			return jsonResponse.setSuccessful().setData(result);
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch(IOException e){
			logger.error("上传文件出现异常",e);
			return jsonResponse.setError("上传文件出现异常");
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
	}
	
	/**
	 * 上传音乐
	 */
	@RequestMapping("uploadMusic")
	@ResponseBody
	public JsonResponse uploadMusic() {
		JsonResponse jsonResponse = new JsonResponse();
		
		return null;
	}
	
	/**
	 * 设置权重
	 */
	@RequestMapping("/setWeight")
	@ResponseBody
	public JsonResponse setWeight(@RequestParam(value="fmId") long fmId,
			                      @RequestParam(value="weight") int weight) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
			wxFmListService.setWeight(weight,fmId);
			return jsonResponse.setSuccessful();
		} catch(TipMessageException e){
			logger.info(e.getMessage());
			return jsonResponse.setError(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError(AlertSystemErrorEnum.SYSTEM_ERROR_REPORT.getMessage());
		}
	}
}
