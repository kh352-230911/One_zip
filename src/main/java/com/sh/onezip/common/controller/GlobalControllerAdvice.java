package com.sh.onezip.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        log.debug("오류가 발생했습니다...");
        log.error(e.getMessage(), e); // String msg, Throwable t
        return "common/error";
    }
}
