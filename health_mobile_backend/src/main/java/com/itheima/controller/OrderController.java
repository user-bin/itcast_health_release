package  com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.service.ValidateCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Reference
    ValidateCodeService validateCodeService;

    @Reference
    private OrderService orderService;

    //体检预约
    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map){
        log.debug("submitOrder: param { " + map.toString() + " }");
        String telephone = (String) map.get("telephone");
        //校验用户输入的验证码是否正确
        String validateCode = (String) map.get("validateCode");//获取用户页面输入的验证码
        //如果验证码校验不通过， 则返回错误消息
        if(!validateCodeService.checkValidateCode(telephone, RedisMessageConstant.SENDTYPE_ORDER, validateCode)){
            //验证码输入错误
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }
        //通过dubbo调用服务实现预约逻辑
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        Result result = orderService.order(map);

        if(result.isFlag()){
            String orderDate = (String) map.get("orderDate");
            //给用户发送短信通知
            validateCodeService.sendCommonShortMessage(telephone, "success");
        }
        log.debug(MessageConst.ORDER_SUCCESS);
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        log.debug("OrderController: findById: " + id);
        Map<String ,Object> map =  orderService.findById(id);
        Date orderDate = (Date) map.get("orderDate");
        String orderDateStr = new SimpleDateFormat("yyyy-MM-dd").format(orderDate);
        map.put("orderDate", orderDateStr);
        log.debug("查询预约信息成功！！");
        log.debug(map.toString());
        return new Result(true,MessageConst.QUERY_ORDER_SUCCESS, map);
    }
}
