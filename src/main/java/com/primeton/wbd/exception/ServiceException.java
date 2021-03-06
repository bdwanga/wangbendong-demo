package com.primeton.wbd.exception;

import com.primeton.wbd.enums.ErrorEnum;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class ServiceException extends Exception
{
    private final static String ERROR_CODE = "ErrCode: ";

    private final static String ERROR_MESSAGE = "Message: ";

    private final static String DEFAULT_ERROR_CODE = "00000";

    /**
     * http状态码
     */
    private HttpStatus httpStatus = HttpStatus.OK;

    private String errCode;

    private String message;

    private Object[] params;//NOSONAR

    /**
     * 额外信息
     */
    private Object additional;//NOSONAR

    /**
     * 无参构造方法.
     *
     * @deprecated 不推荐使用
     */
    public ServiceException()
    {
        super();
    }

    /**
     * 构造方法.
     *
     * @param httpStatus http状态码
     * @param errCode    异常编号
     * @param message    用户自定义异常描述信息
     * @param params     异常描述时用到的格式化参数
     * @param cause      上层异常
     * @param additional 额外信息，前端用
     */
    public ServiceException(HttpStatus httpStatus, String errCode, String message, Object[] params, Throwable cause, Object additional)
    {
        super(cause);
        this.httpStatus = httpStatus;
        this.errCode = errCode;
        this.message = MessageFormat.format(message, params);
        this.params = params;
        this.additional = additional;
    }

    /**
     * 构造方法.
     *
     * @param errCode 异常编号
     * @param message 用户自定义异常描述信息
     * @param params  异常描述时用到的格式化参数
     */
    public ServiceException(String errCode, String message, Object... params)
    {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errCode, message, params, null, null);
    }

    /**
     * 构造方法.
     *
     * @param message 用户自定义异常描述信息
     */
    public ServiceException(String message)
    {
        this(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_CODE, message, null, null, null);
    }

    /**
     * 构造方法.
     *
     * @param error 异常枚举接口
     */
    public ServiceException(ErrorCoded error)
    {
        this(HttpStatus.OK, error.codeValue(), error.message(), null, null, null);
    }

    /**
     * 构造方法.
     *
     * @param error 异常枚举接口
     * @param httpStatus 返回HTTP状态
     */
    public ServiceException(ErrorCoded error, HttpStatus httpStatus)
    {
        this(httpStatus, error.codeValue(), error.message(), null, null, null);
    }

    /**
     * 构造方法.
     *
     * @param errCode    异常编号
     * @param message    用户自定义异常描述信息
     * @param params     异常描述时用到的格式化参数
     * @param additional 额外信息，前端用
     */
    public ServiceException(String errCode, String message, Object[] params, Object additional)
    {
        this(errCode, message, params, null, additional);
    }

    /**
     * 构造方法.
     *
     * @param message 异常编号
     * @param cause   上层异常
     */
    public ServiceException(String message, Throwable cause)
    {
        this(null, message, null, cause, null);
    }

    /**
     * 构造方法.
     *
     * @param errCode 异常编号
     * @param message 用户自定义异常描述信息
     * @param cause   上层异常
     */
    public ServiceException(String errCode, String message, Throwable cause)
    {
        this(errCode, message, null, cause, null);
    }

    /**
     * 构造方法.
     *
     * @param errCode 异常编号
     * @param message 用户自定义异常描述信息
     * @param params  异常描述时用到的格式化参数
     * @param cause   上层异常
     */
    public ServiceException(String errCode, String message, Object[] params, Throwable cause)
    {
        this(errCode, message, params, cause, null);
    }

    /**
     * 构造方法.
     *
     * @param errCode    异常编号
     * @param message    用户自定义异常描述信息
     * @param params     异常描述时用到的格式化参数
     * @param cause      上层异常
     * @param additional 额外信息，前端用
     */
    public ServiceException(String errCode, String message, Object[] params, Throwable cause, Object additional)
    {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errCode, message, params, cause, additional);
    }

    /**
     * 构造方法.
     *
     * @param httpStatus http状态码
     * @param errCode    异常编号
     * @param message    用户自定义异常描述信息
     * @param params     异常描述时用到的格式化参数
     */
    public ServiceException(HttpStatus httpStatus, String errCode, String message, Object... params)
    {
        this(httpStatus, errCode, message, params, null, null);
    }

    /**
     * 构造方法.
     *
     * @param httpStatus http状态码
     * @param errCode    异常编号
     * @param cause      上层异常
     */
    public ServiceException(int httpStatus, String errCode, Throwable cause)
    {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errCode, null, null, null, null);
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public String getErrCode()
    {
        return errCode;
    }

    public Object[] getParams()
    {
        return params;
    }

    @Override
    public String getMessage()
    {
        StringBuilder messageBuffer = new StringBuilder();
        if (errCode != null)
        {
            messageBuffer.append(ERROR_CODE).append(errCode).append("\n");
        }
        String resourceMessage = getMessageOnly();
        if ((resourceMessage != null) && (resourceMessage.length() > 0))
        {
            messageBuffer.append(ERROR_MESSAGE).append(resourceMessage);
        }

        return messageBuffer.toString();
    }

    /**
     * 只取得异常信息，不带编号
     *
     * @return 异常信息，不带编号
     */
    public String getMessageOnly()
    {
        return this.message;
    }

    /**
     * 取得额外信息
     *
     * @return 额外信息
     */
    public Object getAdditional()
    {
        return additional;
    }

    /**
     * 构造方法不够齐全，这个方法还是有必要的，否则补全构造方法。<br>
     *
     * @param additional The additional to set.
     */
    public void setAdditional(Object additional)
    {
        this.additional = additional;
    }
}
