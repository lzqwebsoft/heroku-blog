package com.herokuapp.lzqwebsoft.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.gson.Gson;
import com.qiniu.util.Auth;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;

import com.herokuapp.lzqwebsoft.pojo.Image;

/**
 * 七牛云上传工具
 * 
 * @author johnny
 * 
 */
public class QiniuUtil {
    private static Properties qiniuKeys = new Properties();

    static {
        // 加载七牛云配置文件
        InputStream in = MailUtil.class.getResourceAsStream("/qiniu-keys.properties");
        try {
            qiniuKeys.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 七牛文件上传
    public static Image upload(Image image) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = getZoneConfig();
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = qiniuKeys.getProperty("qiniu.accessKey");
        String secretKey = qiniuKeys.getProperty("qiniu.secretKey");
        String bucket = qiniuKeys.getProperty("qiniu.bucket");
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = image.getFileName();
        byte[] uploadBytes = image.getContent();
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                ex2.printStackTrace();
            }
        }
        return image;
    }

    // 获取七牛云的区域设置
    private static Configuration getZoneConfig() {
        String zone = qiniuKeys.getProperty("qiniu.zone");
        if(zone.equalsIgnoreCase("zone0")) {
            return new Configuration(Zone.zone0());
        } else if(zone.equalsIgnoreCase("zone1")) {
            return new Configuration(Zone.zone1());
        } else if(zone.equalsIgnoreCase("zone2")) {
            return new Configuration(Zone.zone2());
        } else if(zone.equalsIgnoreCase("zoneNa0")) {
            return new Configuration(Zone.zoneNa0());
        } else {
            return new Configuration(Zone.zone0());
        }
    }
}
