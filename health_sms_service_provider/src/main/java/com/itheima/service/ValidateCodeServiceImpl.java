package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.itheima.exception.BusinessRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {


    private String accessKey = "LTAI4GDCjfozE115Vkvv5DZx";
    private String secretKey = "0ecAT5SZoDbARo5UeBlziJFRGPZd8J";
    private String signName = "健康人生";
    private String validateCodeTemplateCode = "SMS_162221957";
    private String commonTemplateCode = "SMS_165106805";


    /**
     * 发送验证码短信
     * @param telephone  手机号码
     * @param type   发送短信的类型: 预约，登录，获取密码
     * @param validateCode  要发送给用户的验证码
     */
    @Override
    public void sendValidateCodeShortMessage(String telephone, String type, String validateCode) {
        // 要想发送短信，必须配置认证信息（鉴权）（密钥）
//        需要调整的内容： 密钥，手机号码， 签名， 模板， 验证码

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, secretKey);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", telephone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", validateCodeTemplateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+validateCode+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if(response.getHttpStatus() == 200){
                log.debug("发送验证码短信成功！！！");
                //发送成功后，需要存储验证码到redis
                saveValidateCodeToRedis(telephone,type,validateCode);
            }else{
                log.debug("发送验证码短信失败");
                throw new BusinessRuntimeException("发送验证码失败!!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.debug("发送验证码短信失败");
            throw new BusinessRuntimeException("发送验证码失败!!!");
        }
    }

    @Autowired
    JedisPool jedisPool;

    /**
     *
     * 13400000000 :  123456
     *
     * 存储验证码到redis中
     *
     *  类型 + 手机号码：key
     *  验证码为：value
     *
     *  存储时间：5分钟
     * @param telephone
     * @param type
     * @param validateCode
     */
    private void saveValidateCodeToRedis(String telephone, String type, String validateCode){
        Jedis jedis = jedisPool.getResource();
        /**
         * key ： 键： 类型 + 手机号
         * seconds： 存储时间：单位秒
         * value ： 验证码
         */
        jedis.setex(type + "-" + telephone, 5 * 60, validateCode);
    }

    /**
     * 检验验证码
     * @param telephone  手机号码
     * @param type  验证码的类型(手机快速登录， 预约服务的功能, 找回密码)
     * @param validateCode  用户输入的验证码
     * @return
     */
    @Override
    public boolean checkValidateCode(String telephone, String type, String validateCode) {
        //redis中取出验证码
        Jedis jedis = jedisPool.getResource();
        String validateCodeInRedis = jedis.get(type + "-" + telephone);
        if(validateCodeInRedis != null && validateCodeInRedis.equalsIgnoreCase(validateCode)){
            log.debug("校验验证码成功!!!");
            return true;
        }
        log.debug("校验验证码失败!!!");
        return false;
    }


    /**
     * 发送通用短信
     * @param telephone 手机号
     * @param param  通用短信中的参数
     */
    @Override
    public void sendCommonShortMessage(String telephone, String... param) {
        log.debug("【企业】发送通用短信!!!!");
    }
}
