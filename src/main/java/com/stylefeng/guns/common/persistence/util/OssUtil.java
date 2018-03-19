package com.stylefeng.guns.common.persistence.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.CharSet;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.StorageClass;

public class OssUtil {
	private static  String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	private static  String accessKeyId  = "";
	private static  String accessKeySecret  = "";
	
	public static void main(String[] args) throws OSSException, ClientException, UnsupportedEncodingException {
		ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
		conf.setIdleConnectionTime(1000);
		conf.setProtocol(Protocol.HTTPS);
		OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret,conf);
		//创建bucket
//		createBucket(client);
		//获取BucketList并把object放进去
//		getBucketListAndPutObject(client);
        //查询某个bucket下面的object
//		queryObjectInBucket(client);
		//上传文件
//		normalUploadFile(client);
		//开启断点上传
		
		
		
		
		
		
		
		client.shutdown();
		
	}

	private static void normalUploadFile(OSSClient client) {
		File file = new File("C:\\Users\\Administrator\\Desktop\\u=1568832227,4289578758&fm=27&gp=0.jpg");
		String name = file.getName();
		System.out.println(file.toString());
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    String date = sdf.format(new Date());
	    StringBuffer fileName = new StringBuffer("icon-fm/");
	    fileName.append(date);
	    fileName.append(".jpg");
		client.putObject("poster-fm-gurui",fileName.toString(), file);
		queryObjectInBucket(client);
		
	}

	private static void queryObjectInBucket(OSS client) {
//		ObjectListing objectListing = client.listObjects("txt-test-nj");
//		System.out.println(objectListing);
//		for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//		    System.out.println(" - " + objectSummary.getKey() + "  " + 
//		            "(size = " + objectSummary.getSize() + ")");
//		}
		
		ObjectListing objectListing1 = client.listObjects("poster-fm-gurui");
		System.out.println(objectListing1);
		for (OSSObjectSummary objectSummary : objectListing1.getObjectSummaries()) {
			System.out.println(" - " + objectSummary.getKey() + "  " + 
					"(size = " + objectSummary.getSize() + ")");
		}
		
	}

	private static void getBucketListAndPutObject(OSSClient client) throws OSSException, ClientException, UnsupportedEncodingException {
		List<Bucket> buckets = client.listBuckets();
		for(Bucket bucket:buckets){
			System.out.println("-"+bucket.getName());
			System.out.println("----"+bucket.toString());
			if(bucket.getName().equals("txt-test-nj")){
				System.out.println("上传开始");
				String content = new String("测试第二次");
				client.putObject(bucket.getName(), "ni1.txt", new ByteArrayInputStream(content.getBytes("UTF-8")));
				System.err.println("上传结束");
			}
		}
		
	}

	private static void createBucket(OSSClient client) {
		CreateBucketRequest createBucketRequest= new CreateBucketRequest("txt-test-nj");
		//设置权限默认私有读写，设置权限为公共读
		createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
		createBucketRequest.setStorageClass(StorageClass.IA);
		client.createBucket(createBucketRequest);
		
	}

}
