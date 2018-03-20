package com.stylefeng.guns.common.persistence.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;

public class OSSUploadUtil implements FileUtil{
	
	private static Logger logger = Logger.getLogger(OSSUploadUtil.class);
	
	private final String ACCESS_KEY_ID = "";
	private final String ACCESS_KEY_SECRET = "";
	private final String END_POINT ="";
	private final String IMG_SERVICE = "music-fm";

	// 初始化一个OSSClient
	private OSS client = null;
	
    /**
     * 上传文件
     */
	@Override
	public String uploadFile(String basePath, MultipartFile file) throws IOException {
		return null;
	}
    /**
     * 暂时不做
     */
	@Override
	public String downloadFile(String filename) {
		return null;
	}
    /**
     * 暂时不做
     */
	@Override
	public String removeFile(String basePath, String fileName) {
		return null;
	}
    /**
     * 文件列表
     */
	@Override
	public List<String> listAllFile(String basePath) {
		return null;
	}
	
	private  void normalUploadFile() {
		File file = new File("C:\\Users\\Administrator\\Desktop\\u=1568832227,4289578758&fm=27&gp=0.jpg");
		String name = file.getName();
		System.out.println(file.toString());
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    String date = sdf.format(new Date());
	    StringBuffer fileName = new StringBuffer("icon-fm/");
	    fileName.append(date);
	    fileName.append(".jpg");
		client.putObject("poster-fm-gurui",fileName.toString(), file);
		
	}

}
