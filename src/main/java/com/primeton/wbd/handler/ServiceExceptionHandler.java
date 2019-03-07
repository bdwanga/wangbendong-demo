package com.primeton.wbd.handler;

import com.primeton.wbd.exception.ServiceException;
import com.primeton.wbd.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class ServiceExceptionHandler
{
    private static Logger log = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    //捕捉到的异常
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<JsonResult> handleServiceException(ServiceException exception)
    {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(new JsonResult(exception), exception.getHttpStatus());
    }

    //其他异常
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<JsonResult> handleServerException(Exception exception)
    {
        log.error("系统错误：",exception);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Class exceptionClazz = exception.getClass();
        if (Objects.equals(MissingServletRequestParameterException.class, exceptionClazz))
        {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else if (Objects.equals(HttpRequestMethodNotSupportedException.class, exceptionClazz))
        {
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(new JsonResult(exception), httpStatus);
    }
}
