package com.fit.sys.exception;

import lombok.Getter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author : hongwq
 * @date : 2020-08-07
 **/
@Getter
public class BadRequestException extends RuntimeException {

    private Integer status = BAD_REQUEST.value();

    public BadRequestException(String msg){
        super(msg);
    }

}
