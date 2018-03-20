package com.stylefeng.guns.common.persistence.util;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件下载，上传，删除
 *
 */
public interface FileUtil {
	
	public String uploadFile(final String basePath,MultipartFile file) throws IOException;
	public String downloadFile(String filename);
	public String removeFile(final String basePath,String fileName);
	public List<String> listAllFile(String basePath);

}
