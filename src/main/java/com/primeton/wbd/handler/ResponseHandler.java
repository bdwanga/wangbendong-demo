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
        String path = serverHttpRequest.getURI().getPath();

        if(path.contains("/swagger-resources")||path.contains("/webjars")||path.contains("/swagger-ui.html")||path.contains("/v2/api-docs"))
        {
            return returnValue;
        }

        if (returnValue instanceof JsonResult)
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
        //获取当前处理的方法
        String methodName = methodParameter.getMethod().getName();

        // 不执行方法的不拦截
        return !("error".equals(methodName));
    }

}
