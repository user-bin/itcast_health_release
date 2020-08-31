package com.itheima;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestQiniuDelete {
    public static void main(String[] args) {
//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释

        String accessKey = "6LLhkymg55mZ2RiAMdloHAr-nYbhYgY2eAI2yOWb";
        String secretKey = "ZaqnW4nGn930xrYmMUYD-4-f02MSZK2kmAL-eJEa";
        String bucket = "hm-31";
        String key = "Fu3Ic6TV6wIbJt793yaGeBmCkzTX";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }
}
