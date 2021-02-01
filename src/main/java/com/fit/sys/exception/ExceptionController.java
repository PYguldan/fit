package com.fit.sys.exception;

import com.fit.sys.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Map;

/**
 * 全局异常捕获
 * @author : hongwq
 * @date : 2020-07-16
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionController {
    public String logRequestInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUri = request.getRequestURI();

        log.error("异常 requestURI ---> {}",requestUri);
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            log.error("异常参数 ---> {} : {}", entry.getKey(), entry.getValue());
        }
        return requestUri;
    }

    public void logExceptionInfo(Throwable e) {
        StringWriter sw = new StringWriter();
        try(PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
        }
        log.error(sw.toString());
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(Throwable e) {
        String uri = logRequestInfo();
        logExceptionInfo(e);
        Result result = new Result();
        result.setCode(1000);
        result.setMsg(e.getMessage());
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        String uri = logRequestInfo();
        logExceptionInfo(e);
        Result result = new Result();
        result.setCode(e.getStatus());
        result.setMsg(e.getMessage());
        return ResponseEntity.ok(result);
    }
}
