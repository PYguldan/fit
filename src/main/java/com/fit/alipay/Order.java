package com.fit.alipay;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : hongwq
 * @date : 2021-02-04
 **/
@Data
public class Order implements Serializable {

    /**
     * 商户订单号。64 个字符以内的大小，仅支持字母、数字、下划线。需保证该参数在商户端不重复。
     */
    @JSONField(alternateNames = "out_trade_no")
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 销售产品码，与支付宝签约的产品码名称。
     * 注：目前仅支持FAST_INSTANT_TRADE_PAY
     */
    @JSONField(alternateNames = "product_code")
    @JsonProperty("product_code")
    private static final String productCode = "FAST_INSTANT_TRADE_PAY";

    /**
     * 订单总金额，单位为人民币（元），取值范围为 0.01~100000000.00，精确到小数点后两位。
     */
    @JSONField(alternateNames = "total_amount")
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    /**
     * 商品标题/交易标题/订单标题/订单关键字等。
     * 注意：不可使用特殊字符，如 /，=，& 等。
     */
    private String subject;

    /**
     * 订单描述
     */
    private String body;
}
