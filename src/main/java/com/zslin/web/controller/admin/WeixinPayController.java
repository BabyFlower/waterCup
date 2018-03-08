package com.zslin.web.controller.admin;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.zslin.web.model.Account;
import com.zslin.web.model.Order;
import com.zslin.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Administrator on 2017/5/5.
 */

@RestController
@RequestMapping("/pay")
public class WeixinPayController {

    @Autowired
    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping(value="/call",method= RequestMethod.POST)
    public String weixin_pay(@RequestParam String money,@RequestParam String method,@RequestParam String username,@RequestParam String input) throws Exception {

        if(money.equals("else")){
            try{

                float tmoney=Float.parseFloat(input)*100;
                Integer rmoney=(int)tmoney;
                money=rmoney.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        long time=new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化当前日期
        String sdate = sdf.format(time);

        //微信支付
        if(method.equals("weixin")){
        // 账号信息
        String appid = PayConfigUtil.APP_ID;  // appid
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret
        String mch_id = PayConfigUtil.MCH_ID; // 商业号
        String key = PayConfigUtil.API_KEY; // key

        String currTime = PayCommonUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;

        String order_price = money; // 价格   注意：价格的单位是分
        String body = "水杯子充值卡";   // 商品名称
        String out_trade_no ; // 订单号
        out_trade_no=time+"";
        String attach= ""; //附加信息，存在用户名
        // 获取发起电脑 ip
        String spbill_create_ip = PayConfigUtil.CREATE_IP;
        // 回调接口
        String notify_url = PayConfigUtil.NOTIFY_URL;
        String trade_type = "NATIVE";

        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", order_price);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        packageParams.put("attach", attach);
        String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);
        packageParams.put("sign", sign);

        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        System.out.println(requestXML);

        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);
        Map map = XMLUtil.doXMLParse(resXml);
        String return_code = (String) map.get("return_code");
        String result_code= (String) map.get("result_code");
        if (return_code.equals("SUCCESS")&&result_code.equals("SUCCESS")){
           Account account=this.accountService.findByEmail(username);
            Order order=new Order();
            order.setAccount(account);
            order.setFee(order_price);
            order.setOrdernumber(out_trade_no);
            order.setTime(sdate);
            order.setState("OrderSuccess");
            order.setPaymethod("weixin");
            this.orderService.save(order);
        }
        //String prepay_id = (String) map.get("prepay_id");
        String urlCode = (String) map.get("code_url");
        return urlCode;

        }
        //支付宝
        else{
            Configs.init("application-dev.properties");
            tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
            // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
            // 需保证商户系统端不能重复，建议通过数据库sequence生成，
            String outTradeNo = "tradeprecreate" + System.currentTimeMillis()
                    + (long) (Math.random() * 10000000L);

            // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
            String subject = "南京水杯子水卡充值";

            // (必填) 订单总金额，单位为元，不能超过1亿元
            // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
            String totalAmount;
            if(money.length()<=2){
               totalAmount = input;
           }else{
               totalAmount = money.substring(0,money.length()-2);
           }
            // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
            // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
            String undiscountableAmount = "0";

            // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
            // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
            String sellerId = "";

            // 订单描述
            String body = "";

            // 商户操作员编号，添加此参数可以为商户操作员做销售统计
            String operatorId = "test_operator_id";

            // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
            String storeId = "test_store_id";

            // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId("2088521468950893");

            // 支付超时，定义为120分钟
            String timeoutExpress = "120m";

            // 创建扫码支付请求builder，设置请求参数
            AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                    .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                    .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                    .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                    .setTimeoutExpress(timeoutExpress)
                    .setNotifyUrl("http://106.14.149.28:8090/zhifubao/back");//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置


            AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    System.out.println("支付宝预下单成功: )");
                    Account account=this.accountService.findByEmail(username);
                    Order order=new Order();
                    order.setAccount(account);
                    order.setFee(money);
                    order.setOrdernumber(outTradeNo);
                    order.setTime(sdate);
                    order.setState("OrderSuccess");
                    order.setPaymethod("zhifubao");
                    this.orderService.save(order);
                    AlipayTradePrecreateResponse response = result.getResponse();
                    return   response.getQrCode(); //返回给客户端二维码

                case FAILED:
                    System.out.println("支付宝预下单失败!!!");
                    break;

                case UNKNOWN:
                    System.out.println("系统异常，预下单状态未知!!!");
                    break;

                default:
                    System.out.println("不支持的交易状态，交易返回异常!!!");
                    break;
            }

            return null;
        }

    }

}

