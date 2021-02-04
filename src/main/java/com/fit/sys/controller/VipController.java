package com.fit.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.fit.alipay.Order;
import com.fit.alipay.service.impl.ALiPayServiceImpl;
import com.fit.sys.entity.Result;
import com.fit.sys.entity.User;
import com.fit.sys.service.IUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Many;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author : hongwq
 * @date : 2021-02-04
 **/
@Api(tags = "vip")
@RestController
@Slf4j
@RequestMapping("/sys/vip")
public class VipController {
    @Autowired
    private ALiPayServiceImpl aLiPayService;

    @Autowired
    private IUserService userService;

    @GetMapping("/updatevip")
    public ResponseEntity<Result<Object>> updateVip(Long outTradeNo, Long userId) throws AlipayApiException {
        Result result = new Result();
        Integer type = aLiPayService.checkOrder(outTradeNo);
        if (type != null) {
            result.setCode(200);
            User user = userService.getById(userId);
            long month = 0;
            if (type == 0) {
                month = 1;
            } else if (type == 1) {
                month = 3;
            } else if (type == 2) {
                month = 12;
            }
            if (user.getVip().isAfter(LocalDateTime.now())) {
                user.setVip(user.getVip().plusMonths(month));
            } else {
                user.setVip(LocalDateTime.now().plusMonths(month));
            }
            userService.updateById(user);
            result.setMsg("支付成功");
        } else {
            result.setCode(1000);
            result.setMsg("尚未支付成功");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Result<Object>> getOrder(int type, Long userId) throws AlipayApiException {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        result.setData(aLiPayService.createOrder(type, userId));
        return ResponseEntity.ok(result);
    }
}
