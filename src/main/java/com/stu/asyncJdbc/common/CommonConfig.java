package com.stu.asyncJdbc.common;

import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 12:41
 * @Description:
 */
public class CommonConfig {
    public static final Charset DEFAULT_CHARSET = CharsetUtil.US_ASCII;
    public static final String DEFAULT_CHARSET_NAME = DEFAULT_CHARSET.displayName();
}
