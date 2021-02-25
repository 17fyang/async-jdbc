package com.stu.asyncJdbc.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 18:47
 * @Description:
 */
public class PacketAnalysisException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(PacketAnalysisException.class);

    public PacketAnalysisException() {
        this("包解析异常");
    }

    public PacketAnalysisException(String message) {
        super(message);
    }
}
