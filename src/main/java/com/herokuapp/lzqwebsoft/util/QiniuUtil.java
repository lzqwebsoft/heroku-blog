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
import com.qiniu.storage.BucketManager;
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
        InputStream in = QiniuUtil.class.getResourceAsStream("/qiniu-keys.properties");
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
        String key = image.getId();
        byte[] uploadBytes = image.getContent();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            image.setQiniuHash(putRet.hash);
            image.setQiniuKey(putRet.key);
        } catch (QiniuException ex) {
            System.err.println(ex.code());
            System.out.println(ex.error());
            ex.printStackTrace();
        }
        return image;
    }

    // 七牛云删除
    public static void delete(Image image) {
        // 如果图片在七牛云的qiniu key不存在，则直接返回
        if (image.getQiniuKey() == null || image.getQiniuKey().trim().length() == 0) {
            return;
        }
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = getZoneConfig();
        String accessKey = qiniuKeys.getProperty("qiniu.accessKey");
        String secretKey = qiniuKeys.getProperty("qiniu.secretKey");
        String bucket = qiniuKeys.getProperty("qiniu.bucket");
        String key = image.getQiniuKey();
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            // 如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            ex.printStackTrace();
        }
    }

    // 获取七牛云的区域设置
    private static Configuration getZoneConfig() {
        String zone = qiniuKeys.getProperty("qiniu.zone");
        if (zone.equalsIgnoreCase("zone0")) {
            return new Configuration(Zone.zone0());
        } else if (zone.equalsIgnoreCase("zone1")) {
            return new Configuration(Zone.zone1());
        } else if (zone.equalsIgnoreCase("zone2")) {
            return new Configuration(Zone.zone2());
        } else if (zone.equalsIgnoreCase("zoneNa0")) {
            return new Configuration(Zone.zoneNa0());
        } else {
            return new Configuration(Zone.zone0());
        }
    }
}
