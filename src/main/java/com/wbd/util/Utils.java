package com.wbd.util;

import com.wbd.enums.ErrorEnum;
import com.wbd.exception.ServiceException;
import org.apache.commons.lang.StringUtils;

import java.io.Closeable;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils
{
    /**
     * 关闭流
     *
     * @param stream
     */
    public static void closeStream(Closeable stream)
    {
        try
        {
            if (null != stream)
            {
                stream.close();
            }
        }
        catch (Exception e)
        {

        }
    }

    /**
     * 获取e.printStackTrace() 的具体信息，赋值给String 变量，并返回
     *
     * @param e Exception
     * @return e.printStackTrace() 中的信息
     */
    public static String getStackTraceInfo(Exception e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);
        sw.flush();
        pw.flush();
        String stackTraceInfo = sw.toString();

        closeStream(sw);
        closeStream(pw);

        return stackTraceInfo;
    }

    /**
     * 判读对象不能为空
     *
     * @param data
     * @param errorEnum
     */
    public static void assertNotNull(Object data, ErrorEnum errorEnum)
    {
        if (null == data)
        {
            throw new ServiceException(errorEnum);
        }

        if (data instanceof String && ((String) data).length() == 0)
        {
            throw new ServiceException(errorEnum);
        }
    }

    /**
     * 判读对象必须为空
     *
     * @param data
     * @param errorEnum
     */
    public static void assertNull(Object data, ErrorEnum errorEnum)
    {
        if (null == data)
        {
            return;
        }

        if (data instanceof String && ((String) data).length() == 0)
        {
            return;
        }

        throw new ServiceException(errorEnum);
    }

    /**
     * 判读字符串是否相同
     *
     * @param data
     * @param compareData
     * @param errorEnum
     */
    public static void assertStrEquals(String data, String compareData, ErrorEnum errorEnum)
    {
        if (!StringUtils.equals(data, compareData))
        {
            throw new ServiceException(errorEnum);
        }
    }
}
