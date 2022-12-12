package com.xuhao.didi.core.utils;

/**
 * Created by xuhao on 15/12/9.
 */
public class BytesUtils {

    /**
     * 生成打印16进制日志所需的字符串
     *
     * @param bytes 数据源
     * @return 字符串给日志使用
     */
    public static final String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString(0x100 | (0xff & b)).substring(1));
        }
        return sb.toString();
    }
}
