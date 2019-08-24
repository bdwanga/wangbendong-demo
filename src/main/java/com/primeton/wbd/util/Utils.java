package com.primeton.wbd.util;

import com.primeton.wbd.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

public class Utils
{
    private final static Logger log = LoggerFactory.getLogger(Utils.class);

    //私有构造器防止被外部改造
    private Utils()
    {

    }

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
            log.error("Utils.closeStream关闭流异常", e);
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
     * @param ex
     */
    public static void assertNotNull(Object data, ServiceException ex) throws ServiceException
    {
        if (null == data)
        {
            throw ex;
        }

        if (data instanceof String && ((String) data).length() == 0)
        {
            throw ex;
        }
    }

    /**
     * 判读对象必须为空
     *
     * @param data
     * @param ex
     */
    public static void assertNull(Object data, ServiceException ex) throws ServiceException
    {
        if (null == data)
        {
            return;
        }

        if (data instanceof String && ((String) data).length() == 0)
        {
            return;
        }

        if (data instanceof Collection && ((Collection) data).size() == 0)
        {
            return;
        }

        throw ex;
    }

    /**
     * 判读字符串是否相同
     *
     * @param data
     * @param compareData
     * @param ex
     */
    public static void assertStrEquals(String data, String compareData, ServiceException ex) throws ServiceException
    {
        if (!StringUtils.equals(data, compareData))
        {
            throw ex;
        }
    }
}
