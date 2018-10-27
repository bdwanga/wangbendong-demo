package com.wbd.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wbd.enums.ErrorEnum;
import com.wbd.exception.ServiceException;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> implements Serializable
{
    private static final long serialVersionUID = 7023397262788301448L;

    public static final String SUCCESS = "0";

    private String state;

    private String message;

    private String error;

    private Object[] params;

    private T data;

    public JsonResult()
    {
        this.state = SUCCESS;
    }

    public JsonResult(String state, String msg)
    {
        this.state = state;
        this.message = msg;
    }

    public JsonResult(String state, String msg, String error, Object[] params, T data)
    {
        this.state = state;
        this.message = msg;
        this.error = error;
        this.params = params;
        this.data = data;
    }

    public JsonResult(T data)
    {
        this(SUCCESS, null, null, null, data);
    }

    public JsonResult(Object[] params, T data)
    {
        this(SUCCESS, null, null, params, data);
    }

    public JsonResult(Exception e)
    {
        if (e instanceof ServiceException)
        {
            ServiceException se = (ServiceException) e;
            this.state = se.getErrCode();
            this.message = se.getMessageOnly();
            this.params = se.getParams();
            this.data = data;
        }
        else
        {
            this.state = ErrorEnum.SYSTEM_ERROR.getCode();
            this.message = e.toString();
            this.error = Utils.getStackTraceInfo(e);
        }
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Object[] getParams()
    {
        return params;
    }

    public void setParams(Object[] params)
    {
        this.params = params;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}
