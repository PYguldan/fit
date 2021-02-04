package com.fit.alipay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : hongwq
 * @date : 2021-02-04
 **/
@Slf4j
@Service
public class ALiPayServiceImpl {
    @Value("${baseUrl}")
    String baseUrl;

    private final static String APP_ID="2016101500695579";

    private final static String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV8ND59zY124IWoINl/ekC4tbTA1YtdP/UInmcmuXRvHrRmQJ/FEat8cKz4vTzFwKzg65OfkgRPwk+rVB1CeI6G7BZohxkhlzGeXxBNz69b7GLsp+7snFwRt6zikbZt35wA76dUVf8/TGM+CsDxAQasH9X6HRtV0ufCH3l/rjZErFOSCho3RmBN2Pxb7mmuJmBJYKi6IMcQIg5k+GYsIt/UVqpW5DFL+PX00wfduMTT/UjYdtOUovsPGJ9f+v4dFwR6SwQALaEwolNCcEyPLQFPAGFdiMJrUzUUKYxHxCkoBzQGI5BVt/RwHOTklQPQ5Au8KHOt7HJP+YHXJBAPKfVAgMBAAECggEAW4K0KDsGrOwhgynHyDGlSoujTPnWWLv1f0TjL/8hkFYWJ2HZMjHvyS2qBRvZH5vFnEYb1fAgj9YHEGe6mRPFLG3FvY7BAWE3sjJm8W6ELh3DIF2OO4H21e2K8czM3QeudTLz3RPOPatR2JWFuzCgyKE7ty06EZmbn54RKJPDwUdAYtMaFfOJ6MclB+PYuDmD+7CkFp9NNR4gRVeXJNDWRiTT9hFlow5ekFXV8sLRj+xHWZj6J0SSovQ4LcRwYKODrWwb0TcmqOAMIxnibj+7hGDnTRZ/CzhgIgTuWZbB5ppBkW7FBUSSe4ZFr7g7AMbPcvyW8QUDnsjm0aMrSHLMqQKBgQDz5YTfTTDw2N73+VZPwI2ECo6270Cag8FlMPgLnwkuABXzBfK5sq7GjKwBEy2+Bd98oNuW1GEtXsT5pIWEkGRDjRAS/iRC5HqLke+HvuOXE0/O7lJOTpxW9YqnS6VGnY6ztI0mCyRqmqqbBG5Ld55r1IcqeQjaZe4jlzCeBATXSwKBgQCdYayvxnlNgjSenVmUUejnODhnu8aA0Xa3yxOyqx85M26yQqQLg3uWRJWOWZV5DvAi3I/lKUTA2gwU7TmMbo2xkfhWG4g/9mmFt689Wpp5gXdhiw61hkr9aEIfAoauxC8EcgK+ml1eAcqb28qhF74GZqYuF4lBLHpkK7oKTYlpXwKBgF71QaJfP2rGRSlu3nf2lCKQqbf3tsNyAN4RDsIXjU9noSQZcMN0GjAbnOxeNzUAvF6dkmFLFvq/Jxm7QHC4dxEF3vhGEoAozL2CeCv12k57qlr61vi+Coig2PcBsLuppGG7LildtTP8O+hW9UpUrE13VZjnvtapUOpydu9OyUO5AoGAUh3Lr69cTY/ZisVJHzWZ00MnUyDL0xz317AzJ+9XjE4EvSg+Ve+VxXMloa+maRLdQyNYRVbCE+nxkd2xQE/SyMsEC1G0RsN4jZeo7NdgcHOX6aJX22klqZEDpxB3W6rraWNfgToj60/9+lK1KFauBML8hGItcH8XoTqbiJKqVvsCgYEA7A73r/LJFtOLR/jQ6bQngkaKwaeQtNyteH/k1ttMdZxmgWJiYJFohh9IPT+T54GSI6CPfK9mZmsBSoV49SOu3XYEb0Hqn6u1/q8mAH2kKFpjwEjJOeIRBUGIRGeP9TS0hnx6xAFNciGxwqwizgFy3HKdoNpdwyCytfWO5MRMV1M=";

    private final static String CHARSET="UTF-8";

    private final static String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyqy/RAaXbRQNl52+r571tL0EnnyZdhowLNQ7EDfeRgsLpM/3EafgoyYa0JfGhb7SRPZK+HHACAWXzZLgbA7D3AHLoGJ2seunK2sm0cxFDMX0+joeh6t+9rPtaRNbv3F2iSHo0mx05L+1A6RqRSXWh1fCMzY+QLsKcmIDNewYzMJxGey4eZhi6RVAsCG8Kp7RLpKuq1f311mnRFAFG4KVW6/h9U1gWIzo/AaOE6QX6kzvHDBzpDKs8JGXbbAPHdwU8MW6BX3eCq7ZY4z6/68wFumtH1MWjVxQOnMv+w3TMkOZSUMUHVx0TXRBhV5884VOU9y7NcCUkfx1c1E80LHogQIDAQAB";


    private final static String GATEWAY_URL="https://openapi.alipaydev.com/gateway.do";

    private final static String FORMAT="json";

    private final static String SIGNTYPE="RSA2";

    private final static String[] title = {
            "NowFitness月度会员",
            "NowFitness季度会员",
            "NowFitness年度会员",
    };

    private final static String[] price = {
            "30.00",
            "88.00",
            "283.00",
    };

    public String createOrder(int type, Long userId) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT,CHARSET, ALIPAY_PUBLIC_KEY, SIGNTYPE);
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl(baseUrl + "checkvip.html");
//        alipayRequest.setNotifyUrl(baseUrl + "api/sys/vip/updatevip");
        String body = "{"  +
                "    \"out_trade_no\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) + "\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":" + price[type] + ","  +
                "    \"subject\":\"" + title[type] + "\","  +
                "    \"body\":\"" + title[type] + "\","  +
                "    \"passback_params\":\"userId%3d" + userId + "%26type%3d" + type + "\","  +
                "    \"extend_params\":{"  +
                "    \"sys_service_provider_id\":\"2088511833207846\""  +
                "    }" +
                "  }";
        alipayRequest.setBizContent( body ); //填充业务参数
        log.info(body);
        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    public Integer checkOrder(Long outTradeNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT,CHARSET, ALIPAY_PUBLIC_KEY, SIGNTYPE);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"" +
//                "\"trade_no\":\"2014112611001004680 073956707\"," +
//                "\"org_pid\":\"2088101117952222\"," +
//                "      \"query_options\":[" +
//                "        \"trade_settle_info\"" +
//                "      ]" +
                "  }");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            BigDecimal httpResult = JSONObject.parseObject(response.getBody()).getJSONObject("alipay_trade_query_response").getBigDecimal("total_amount");
            if (httpResult.compareTo(BigDecimal.valueOf(30)) == 0) {
                return 0;
            } else if (httpResult.compareTo(BigDecimal.valueOf(88)) == 0) {
                return 1;
            } else if (httpResult.compareTo(BigDecimal.valueOf(283)) == 0) {
                return 2;
            }
        }
        return null;
    }
}
