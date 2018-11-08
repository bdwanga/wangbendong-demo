package com.primeton.wbd.handler;

import com.primeton.wbd.util.JsonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一返回值handler
 * <p>
 * 对返回值进行统一的封装返回
 *
 * @author wangbendong
 * @version 1.0
 * @date 2018.10.31
 * @since 1.8
 */
@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice
{

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter,
                                      MediaType mediaType, Class clas, ServerHttpRequest serverHttpRequest,
                                      ServerHttpResponse serverHttpResponse)
    {
        if(returnValue instanceof JsonResult)
        {
            return returnValue;
        }

        if (returnValue == null)
        {
            return new JsonResult<>();
        }

        return new JsonResult<>(returnValue);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class clas)
    {
//        //获取当前处理请求的controller的方法
//        String methodName = methodParameter.getMethod().getName();
//        // 不拦截/不需要处理返回值 的方法
//        String method = "loginCheck"; //如登录
//        //不拦截
//        return !method.equals(methodName);

        return true;
    }

}
