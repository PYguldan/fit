package com.fit;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private final static String APP_ID="2016101500695579";

    private final static String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV8ND59zY124IWoINl/ekC4tbTA1YtdP/UInmcmuXRvHrRmQJ/FEat8cKz4vTzFwKzg65OfkgRPwk+rVB1CeI6G7BZohxkhlzGeXxBNz69b7GLsp+7snFwRt6zikbZt35wA76dUVf8/TGM+CsDxAQasH9X6HRtV0ufCH3l/rjZErFOSCho3RmBN2Pxb7mmuJmBJYKi6IMcQIg5k+GYsIt/UVqpW5DFL+PX00wfduMTT/UjYdtOUovsPGJ9f+v4dFwR6SwQALaEwolNCcEyPLQFPAGFdiMJrUzUUKYxHxCkoBzQGI5BVt/RwHOTklQPQ5Au8KHOt7HJP+YHXJBAPKfVAgMBAAECggEAW4K0KDsGrOwhgynHyDGlSoujTPnWWLv1f0TjL/8hkFYWJ2HZMjHvyS2qBRvZH5vFnEYb1fAgj9YHEGe6mRPFLG3FvY7BAWE3sjJm8W6ELh3DIF2OO4H21e2K8czM3QeudTLz3RPOPatR2JWFuzCgyKE7ty06EZmbn54RKJPDwUdAYtMaFfOJ6MclB+PYuDmD+7CkFp9NNR4gRVeXJNDWRiTT9hFlow5ekFXV8sLRj+xHWZj6J0SSovQ4LcRwYKODrWwb0TcmqOAMIxnibj+7hGDnTRZ/CzhgIgTuWZbB5ppBkW7FBUSSe4ZFr7g7AMbPcvyW8QUDnsjm0aMrSHLMqQKBgQDz5YTfTTDw2N73+VZPwI2ECo6270Cag8FlMPgLnwkuABXzBfK5sq7GjKwBEy2+Bd98oNuW1GEtXsT5pIWEkGRDjRAS/iRC5HqLke+HvuOXE0/O7lJOTpxW9YqnS6VGnY6ztI0mCyRqmqqbBG5Ld55r1IcqeQjaZe4jlzCeBATXSwKBgQCdYayvxnlNgjSenVmUUejnODhnu8aA0Xa3yxOyqx85M26yQqQLg3uWRJWOWZV5DvAi3I/lKUTA2gwU7TmMbo2xkfhWG4g/9mmFt689Wpp5gXdhiw61hkr9aEIfAoauxC8EcgK+ml1eAcqb28qhF74GZqYuF4lBLHpkK7oKTYlpXwKBgF71QaJfP2rGRSlu3nf2lCKQqbf3tsNyAN4RDsIXjU9noSQZcMN0GjAbnOxeNzUAvF6dkmFLFvq/Jxm7QHC4dxEF3vhGEoAozL2CeCv12k57qlr61vi+Coig2PcBsLuppGG7LildtTP8O+hW9UpUrE13VZjnvtapUOpydu9OyUO5AoGAUh3Lr69cTY/ZisVJHzWZ00MnUyDL0xz317AzJ+9XjE4EvSg+Ve+VxXMloa+maRLdQyNYRVbCE+nxkd2xQE/SyMsEC1G0RsN4jZeo7NdgcHOX6aJX22klqZEDpxB3W6rraWNfgToj60/9+lK1KFauBML8hGItcH8XoTqbiJKqVvsCgYEA7A73r/LJFtOLR/jQ6bQngkaKwaeQtNyteH/k1ttMdZxmgWJiYJFohh9IPT+T54GSI6CPfK9mZmsBSoV49SOu3XYEb0Hqn6u1/q8mAH2kKFpjwEjJOeIRBUGIRGeP9TS0hnx6xAFNciGxwqwizgFy3HKdoNpdwyCytfWO5MRMV1M=";

    private final static String CHARSET="UTF-8";

    private final static String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyqy/RAaXbRQNl52+r571tL0EnnyZdhowLNQ7EDfeRgsLpM/3EafgoyYa0JfGhb7SRPZK+HHACAWXzZLgbA7D3AHLoGJ2seunK2sm0cxFDMX0+joeh6t+9rPtaRNbv3F2iSHo0mx05L+1A6RqRSXWh1fCMzY+QLsKcmIDNewYzMJxGey4eZhi6RVAsCG8Kp7RLpKuq1f311mnRFAFG4KVW6/h9U1gWIzo/AaOE6QX6kzvHDBzpDKs8JGXbbAPHdwU8MW6BX3eCq7ZY4z6/68wFumtH1MWjVxQOnMv+w3TMkOZSUMUHVx0TXRBhV5884VOU9y7NcCUkfx1c1E80LHogQIDAQAB";


    private final static String GATEWAY_URL="https://openapi.alipaydev.com/gateway.do";

    private final static String FORMAT="json";

    private final static String SIGNTYPE="RSA2";
    static AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT,CHARSET, ALIPAY_PUBLIC_KEY, SIGNTYPE);

    public static void test() throws Exception {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest (); //创建API对应的request类
        request . setBizContent ( "{"   +
                "\"out_trade_no\":\"20150320010101002\"," + //商户订单号
                "\"total_amount\":\"88.88\","   +
                "\"subject\":\"Iphone6 16G\","   +
                "\"store_id\":\"NJ_001\","   +
                "\"timeout_express\":\"90m\"}" ); //订单允许的最晚付款时间
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.print( response.getBody ());
        //根据response中的结果继续业务逻辑处理
    }

    public static void oldSDK() throws AlipayApiException {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT,CHARSET, ALIPAY_PUBLIC_KEY, SIGNTYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
        AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数
        //此次只是参数展示，未进行字符串转义，实际情况下请转义
        request.setBizContent("  {" +
                "    \"primary_industry_name\":\"IT科技/IT软件与服务\"," +
                "    \"primary_industry_code\":\"10001/20102\"," +
                "    \"secondary_industry_code\":\"10001/20102\"," +
                "    \"secondary_industry_name\":\"IT科技/IT软件与服务\"" +
                " }");
        AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request);
        //调用成功，则处理业务逻辑
        if(response.isSuccess()){
            System.out.println(response);
        }
    }

    static void query() throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl( "https://www.baidu.com" );
//        alipayRequest.setNotifyUrl( "http://domain.com/CallBack/notify_url.jsp" ); //在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent( "{"  +
                "    \"out_trade_no\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) + "\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":88.88,"  +
                "    \"subject\":\"Iphone6 16G\","  +
                "    \"body\":\"Iphone6 16G\","  +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
                "    \"extend_params\":{"  +
                "    \"sys_service_provider_id\":\"2088511833207846\""  +
                "    }" +
                "  }" ); //填充业务参数
        System.out.print(alipayClient.pageExecute(alipayRequest).getBody());
    }

    public static void main(String[] args) throws Exception {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "\"out_trade_no\":\"20210204015124265\"" +
//                "\"trade_no\":\"2014112611001004680 073956707\"," +
//                "\"org_pid\":\"2088101117952222\"," +
//                "      \"query_options\":[" +
//                "        \"trade_settle_info\"" +
//                "      ]" +
                "  }");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}
