package com.itheima;

import com.itheima.constant.RedisMessageConstant;
import com.itheima.service.ValidateCodeService;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:config/*"})
public class TestSMS {

//    @Reference
    ValidateCodeService validateCodeService;

//    @Test
    public void testSMS(){
        validateCodeService.sendValidateCodeShortMessage("18211111111", RedisMessageConstant.SENDTYPE_LOGIN, "1234");
    }

//   @Test
    public void testCheck(){
          System.out.println(validateCodeService.checkValidateCode("18211111111", RedisMessageConstant.SENDTYPE_LOGIN, "1234"));
    }
}
