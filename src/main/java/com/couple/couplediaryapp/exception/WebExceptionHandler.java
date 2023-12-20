package com.couple.couplediaryapp.exception;

import com.couple.couplediaryapp.common.Const;
import com.couple.couplediaryapp.common.ResVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public ResVo NPE() {
        return new ResVo(Const.FAIL);
    }

    @ExceptionHandler(Exception.class)
    public ResVo E() {
        return new ResVo(Const.FAIL);
    }
}