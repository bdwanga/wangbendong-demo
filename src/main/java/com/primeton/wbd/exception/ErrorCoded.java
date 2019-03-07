package com.primeton.wbd.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCoded
{
    String codeValue();

    String message();

    default ServiceException exception() {
        ServiceException serviceException = new ServiceException(this);

        return serviceException;
    }
    default ServiceException exception(HttpStatus httpStatus) {
        ServiceException serviceException = new ServiceException(this, httpStatus);

        return serviceException;
    }

    default ServiceException exception(Object... params) {
        ServiceException serviceException = new ServiceException(this.codeValue(), this.message(), params);

        return serviceException;
    }

    default ServiceException exception(HttpStatus httpStatus, Object... params) {
        ServiceException serviceException = new ServiceException(httpStatus, this.codeValue(), this.message(), params);

        return serviceException;
    }

    default ServiceException exception(HttpStatus httpStatus, Object additional, Object... params) {
        ServiceException serviceException = new ServiceException(httpStatus, this.codeValue(), this.message(), params, additional);

        return serviceException;
    }
}
