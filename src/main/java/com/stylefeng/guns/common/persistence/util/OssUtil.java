package com.stylefeng.guns.common.persistence.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PartSummary;
import com.aliyun.oss.model.StorageClass;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

public class OssUtil {
	private static  String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	private static  String accessKeyId  = "LTAI4Aq5P1qnq0Ag";
	private static  String accessKeySecret  = "D9n42csyD6D8eLNg9fy8dxhdnlNckm";
	
    private static String bucketName = "music-fm-gurui";
    private static String key = "music-fm/Die Prinzen-Ich schenk Dir die Welt.mp3";
    private static String localFilePath = "D:\\KwDownload\\song\\Die Prinzen-Ich schenk Dir die Welt.mp3";
    
    private static String genFilePath = "E:\\Youku Files\\download\\a.mp3";
	
    private static OSS client = null;
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
	private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
	
	public static void main(String[] args) throws OSSException, ClientException, UnsupportedEncodingException {
		ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
		conf.setIdleConnectionTime(1000);
		conf.setProtocol(Protocol.HTTPS);
		client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret,conf);
		//创建bucket
//		createBucket(client);
		//获取BucketList并把object放进去
//		getBucketListAndPutObject(client);
        //查询某个bucket下面的object
//		queryObjectInBucket(client);
		//上传文件
//		normalUploadFile(client);
		//开启断点上传
		multipartUploadSample(client);
		
		
		
		
		
		
		
	}

	private static void multipartUploadSample(OSS client) {
		
		try {
			
			/*
	         * Claim a upload id firstly
	         */
			String uploadId = claimUploadId(client);
			System.out.println("Claiming a new upload id " + uploadId + "\n");
			
			/*
             * Calculate how many parts to be divided
             */
            final long partSize = 5 * 1024 * 1024L;   // 5MB
            final File sampleFile = createSampleFile();
            long fileLength = sampleFile.length();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            if (partCount > 10000) {
                throw new RuntimeException("Total parts count should not exceed 10000");
            } else {                
                System.out.println("Total parts count " + partCount + "\n");
            }
            
            /*
             * Upload multiparts to your bucket
             */
            System.out.println("Begin to upload multiparts to OSS from a file\n");
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                executorService.execute(new PartUploader(sampleFile, startPos, curPartSize, i + 1, uploadId));
            }
            
            /*
             * Waiting for all parts finished
             */
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            /*
             * Verify whether all parts are finished
             */
            if (partETags.size() != partCount) {
                throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
            } else {
                System.out.println("Succeed to complete multiparts into an object named " + key + "\n");
            }
            
            /*
             * View all parts uploaded recently
             */
            listAllParts(uploadId);
            
            /*
             * Complete to upload multiparts
             */
            completeMultipartUpload(uploadId);
            
            /*
             * Fetch the object that newly created at the step below.
             */
            System.out.println("Fetching an object");
            client.getObject(new GetObjectRequest(bucketName, key), new File(genFilePath));
            
			
			
		} catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            if (client != null) {
                client.shutdown();
            }
        }
		
		
	}
	/**
	 * Complete to upload multiparts
	 */
	private static void completeMultipartUpload(String uploadId) {
		// Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartETag>() {

            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });
        
        System.out.println("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest = 
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
	}
    /**
     * View all parts uploaded recently
     */
	private static void listAllParts(String uploadId) {
		System.out.println("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = client.listParts(listPartsRequest);
        
        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            System.out.println("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
        System.out.println();
	}

	private static class PartUploader implements Runnable {
	        
	        private File localFile;
	        private long startPos;        
	        
	        private long partSize;
	        private int partNumber;
	        private String uploadId;
	        
	        public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId) {
	            this.localFile = localFile;
	            this.startPos = startPos;
	            this.partSize = partSize;
	            this.partNumber = partNumber;
	            this.uploadId = uploadId;
	        }
	        
	        @Override
	        public void run() {
	            InputStream instream = null;
	            try {
	                instream = new FileInputStream(this.localFile);
	                instream.skip(this.startPos);
	                
	                UploadPartRequest uploadPartRequest = new UploadPartRequest();
	                uploadPartRequest.setBucketName(bucketName);
	                uploadPartRequest.setKey(key);
	                uploadPartRequest.setUploadId(this.uploadId);
	                uploadPartRequest.setInputStream(instream);
	                uploadPartRequest.setPartSize(this.partSize);
	                uploadPartRequest.setPartNumber(this.partNumber);
	                
	                UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
	                System.out.println("Part#" + this.partNumber + " done\n");
	                synchronized (partETags) {
	                    partETags.add(uploadPartResult.getPartETag());
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                if (instream != null) {
	                    try {
	                        instream.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        } 
	    }
    
	/**
	 * 创建文件
	 */
	private static File createSampleFile() {
		File file = new File(localFilePath);

        return file;
	}
	
    /**
     * 分页上传需要一个UploadId
     */
	private static String claimUploadId(OSS client) {
		//创建上传文件
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
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
