package life.gao.community.provider;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import life.gao.community.exception.CustomizeErrorCode;
import life.gao.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class AliyunProvider {
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucket}")
    private String yourBucketName;
    private String yourObjectName;


    public String upload(MultipartFile file, String location) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);//通过key授权

        InputStream fileStream = null;
        try {
            fileStream = file.getInputStream();

            System.out.println(file.getName()+" ,"+file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();

        }


        String split[]=file.getContentType().split("/");
        String generatedFileName= UUID.randomUUID().toString()+"."+split[1];

        ossClient.putObject(yourBucketName, location+"/"+generatedFileName, fileStream);


        Date expiration = new Date(new Date().getTime() +1000*60*60*24);// 生成URL
        URL url = ossClient.generatePresignedUrl(yourBucketName, location+"/"+generatedFileName, expiration);

        ossClient.shutdown();
        if(url!=null){
            return url.toString();
        }else{
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }


// 关闭OSSClient。

    }


}
