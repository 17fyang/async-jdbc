package com.stu.asyncJdbc.common;

import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 12:41
 * @Description:
 */
public class CommonConfig {
    // 内部传输使用ascii码表
    public static final Charset DEFAULT_CHARSET = CharsetUtil.US_ASCII;
    public static final String DEFAULT_CHARSET_NAME = DEFAULT_CHARSET.displayName();

    //当前版本号
    public static final String VERSION = "1.0.0";
}
