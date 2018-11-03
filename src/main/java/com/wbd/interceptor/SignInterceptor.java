package com.wbd.interceptor;

import com.wbd.enums.ErrorEnum;
import com.wbd.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验用户是否登录拦截器
 */
@Component
public class SignInterceptor implements HandlerInterceptor
{

    /**
     * 在请求被处理之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServiceException
    {
        // 检查每个到来的请求对应的session域中是否有登录标识
        Object curUser = request.getSession().getAttribute("curUser");
        if (null == curUser)
        {
            throw new ServiceException(ErrorEnum.NOT_LOGIN, HttpStatus.UNAUTHORIZED);
        }

        return true;
    }
}
