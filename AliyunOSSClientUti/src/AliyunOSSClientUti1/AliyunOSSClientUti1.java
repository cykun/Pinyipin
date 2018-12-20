package AliyunOSSClientUti1;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

//import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import java.util.Date;
import java.net.URL;

public class AliyunOSSClientUti1 {
	//阿里云API的内或外网域名
	private static String ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";
	//阿里云API的密钥Access Key ID
	private static String ACCESS_KEY_ID = "LTAInno0OXDGwTKy";
	//阿里云API的密钥Access Key Secret
	private static String ACCESS_KEY_SECRET = "zXoz01L35MtL9gDc9tHO84Aad6J50R";
	//阿里云API的bucket名称
	private static String BACKET_NAME = "pingyip";
	//阿里云API的文件夹名称
	private static String FOLDER = "somnus/";
	
	//创建存储空间
	public static String cteatBuckerNanme(OSSClient ossClient,String bucketName) {
		final String bucketNames = bucketName;
		if(!ossClient.doesBucketExist(bucketNames)) {
			//创建存储空间
			Bucket bucket = ossClient.createBucket(bucketNames);
			return bucket.getName();
		}
		return bucketNames;
	}
	
	//删除存储空间
	public static void deleteBucket(OSSClient ossClient,String bucketName) {
		ossClient.deleteBucket(bucketName);
	}
	
	//创建模拟文件夹
	public static String creatFolder(OSSClient ossClient, String bucketName,String floder) {
		//文件夹名
		final String keySuffixWithSlash = floder;
		//判断文文件夹是否存在
		if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
			//创建文件夹
			ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
			//得到文件夹
			OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
			String fileDir=object.getKey();
			return fileDir;
		}
		return keySuffixWithSlash;
	}
	
	//删除文件夹
	public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){ 
		ossClient.deleteObject(bucketName, folder + key);
	}
	
	//上传图片
	public static  String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
		String resultStr = null;
		try {
			//以输入流形式上传文件
			InputStream is = new FileInputStream(file);
			//文件名
			String fileName = file.getName();
			//文件大小
			Long fileSize = file.length();
			//创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			//上传的文件的长度
			metadata.setContentLength(is.available());
			//指定该Object被下载时的网页的缓存行为
			metadata.setCacheControl("no-cache");
			//指定该Object下设置Header
			metadata.setHeader("Pragma", "no-cache");
			//指定该Object被下载时的内容编码格式
			metadata.setContentEncoding("utf-8");
			//文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，  
            //如果没有扩展名则填默认值application/octet-stream
			metadata.setContentType(getContentType(fileName));
			//指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
			metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
			//上传文件   (上传文件流的形式)
			PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
			//解析结果
			resultStr = putResult.getETag();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultStr;
	}
	
	//通过文件名判断并获取OSS服务文件上传时文件的contentType
	public static  String getContentType(String fileName) {
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if(".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if(".gif".equalsIgnoreCase(fileExtension)) {
			 return "image/gif";
		}
		if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
			return "image/jpeg";
		}
		if(".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if(".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if(".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if(".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		return "image/jpeg";
	}
	


	
	public static void main(String[] args) {
		//初始化
		OSSClient ossClient=new OSSClient(ENDPOINT,ACCESS_KEY_ID,ACCESS_KEY_SECRET);
		//图片地址
		String files ="D:\\image\\1.jpg,D:\\image\\2.jpg,D:\\image\\3.jpg";
		String[] file=files.split(",");
		for(String filename:file) {
			File filess=new File(filename);
			String md5key = AliyunOSSClientUti1.uploadObject2OSS(ossClient, filess, BACKET_NAME, FOLDER);
			//得到图片的地址
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);
			URL url=ossClient.generatePresignedUrl("cosimage", md5key, expiration);
			System.out.println(url.toString());
		}
	}
}
