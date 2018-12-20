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
	//������API���ڻ���������
	private static String ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";
	//������API����ԿAccess Key ID
	private static String ACCESS_KEY_ID = "LTAInno0OXDGwTKy";
	//������API����ԿAccess Key Secret
	private static String ACCESS_KEY_SECRET = "zXoz01L35MtL9gDc9tHO84Aad6J50R";
	//������API��bucket����
	private static String BACKET_NAME = "pingyip";
	//������API���ļ�������
	private static String FOLDER = "somnus/";
	
	//�����洢�ռ�
	public static String cteatBuckerNanme(OSSClient ossClient,String bucketName) {
		final String bucketNames = bucketName;
		if(!ossClient.doesBucketExist(bucketNames)) {
			//�����洢�ռ�
			Bucket bucket = ossClient.createBucket(bucketNames);
			return bucket.getName();
		}
		return bucketNames;
	}
	
	//ɾ���洢�ռ�
	public static void deleteBucket(OSSClient ossClient,String bucketName) {
		ossClient.deleteBucket(bucketName);
	}
	
	//����ģ���ļ���
	public static String creatFolder(OSSClient ossClient, String bucketName,String floder) {
		//�ļ�����
		final String keySuffixWithSlash = floder;
		//�ж����ļ����Ƿ����
		if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
			//�����ļ���
			ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
			//�õ��ļ���
			OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
			String fileDir=object.getKey();
			return fileDir;
		}
		return keySuffixWithSlash;
	}
	
	//ɾ���ļ���
	public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){ 
		ossClient.deleteObject(bucketName, folder + key);
	}
	
	//�ϴ�ͼƬ
	public static  String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
		String resultStr = null;
		try {
			//����������ʽ�ϴ��ļ�
			InputStream is = new FileInputStream(file);
			//�ļ���
			String fileName = file.getName();
			//�ļ���С
			Long fileSize = file.length();
			//�����ϴ�Object��Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			//�ϴ����ļ��ĳ���
			metadata.setContentLength(is.available());
			//ָ����Object������ʱ����ҳ�Ļ�����Ϊ
			metadata.setCacheControl("no-cache");
			//ָ����Object������Header
			metadata.setHeader("Pragma", "no-cache");
			//ָ����Object������ʱ�����ݱ����ʽ
			metadata.setContentEncoding("utf-8");
			//�ļ���MIME�������ļ������ͼ���ҳ���룬�������������ʲô��ʽ��ʲô�����ȡ�ļ�������û�û��ָ�������Key���ļ�������չ�����ɣ�  
            //���û����չ������Ĭ��ֵapplication/octet-stream
			metadata.setContentType(getContentType(fileName));
			//ָ����Object������ʱ�����ƣ�ָʾMINME�û����������ʾ���ӵ��ļ����򿪻����أ����ļ����ƣ�
			metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
			//�ϴ��ļ�   (�ϴ��ļ�������ʽ)
			PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
			//�������
			resultStr = putResult.getETag();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultStr;
	}
	
	//ͨ���ļ����жϲ���ȡOSS�����ļ��ϴ�ʱ�ļ���contentType
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
		//��ʼ��
		OSSClient ossClient=new OSSClient(ENDPOINT,ACCESS_KEY_ID,ACCESS_KEY_SECRET);
		//ͼƬ��ַ
		String files ="D:\\image\\1.jpg,D:\\image\\2.jpg,D:\\image\\3.jpg";
		String[] file=files.split(",");
		for(String filename:file) {
			File filess=new File(filename);
			String md5key = AliyunOSSClientUti1.uploadObject2OSS(ossClient, filess, BACKET_NAME, FOLDER);
			//�õ�ͼƬ�ĵ�ַ
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);
			URL url=ossClient.generatePresignedUrl("cosimage", md5key, expiration);
			System.out.println(url.toString());
		}
	}
}
