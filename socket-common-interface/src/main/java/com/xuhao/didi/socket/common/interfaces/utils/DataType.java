package com.xuhao.didi.socket.common.interfaces.utils;

import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * Created by LeaAnder on 2017/4/25.
 */
public class DataType {
    public static byte[] data16ToBytes(int data) {
        byte[] src = new byte[]{(byte) (data & 255), (byte) (data >> 8 & 255)};
        return src;
    }

    public static byte[] data32ToBytes(int value) {
        byte[] src = new byte[]{(byte) (value & 255), (byte) (value >> 8 & 255), (byte) (value >> 16 & 255), (byte) (value >> 24 & 255)};
        return src;
    }

    public static final int byteArrayTo32(byte[] bytes, int offset) {
        byte result = 0;
        int result1 = result | bytes[offset + 3] & 255;
        result1 <<= 8;
        result1 |= bytes[offset + 2] & 255;
        result1 <<= 8;
        result1 |= bytes[offset + 1] & 255;
        result1 <<= 8;
        result1 |= bytes[offset] & 255;
        return result1;
    }

    public static final int byteArrayTo16(byte[] bytes, int offset) {
        byte result = 0;
        int result1 = result | bytes[offset + 1] & 255;
        result1 <<= 8;
        result1 |= bytes[offset] & 255;
        return result1;
    }

    /**
     * @param bytes
     * @param offset
     * @return
     */
    public static final int byteArrayTo162(byte[] bytes, int offset) {
        int start = (bytes[offset] &0xFFFF)<<8;
        int end = bytes[offset + 1];
        int result = start | end;
        return result;
    }

    public static final int byteArrayToUnsigned16(byte[] bytes,int offset){
        return ((bytes[offset+1]&0xFF<<8))|(bytes[offset]&0xFF);
    }

    /**
     * 有符号int16转换
     * @param bytes
     * @param offset
     * @return
     */
    public static final int byteArrayToSigned16(byte[] bytes, int offset){
        return (bytes[offset+1] << 8) | (bytes[offset] & 0xFF);
    }

    public static final int byteArrayTo8(byte[] bytes, int offset) {
        byte dt = bytes[offset];
        return dt;
    }

    //鉴权Token: 由DeviceID和16位随机数使用CRC16_CCITT生成, 计算初值是0xFFFF
    // Token生成示例
    // device id: 11223344
    // generate token by: crc16("11121223344" + random(0x1DB7)) = 0x4C11
    // Msg Token is: 0x4C11DB7

    public static int getAuthToken(String deviceId) {
        byte[] auth = deviceId.getBytes();
        byte[] randombytes = new byte[]{0X00, 0x00, 0x00, 0x00};
        Random random = new Random();
        int random1 = random.nextInt(0xff);
        int random2 = random.nextInt(0xff);
        randombytes[0] = (byte) random1;
        randombytes[1] = (byte) random2;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(auth, 0, auth.length);
        os.write(random1);
        os.write(random2);
        byte[] token_bytes = os.toByteArray();
        int crc = CRCUtil.calc_crc16(token_bytes, token_bytes.length, 0);
        int token = ((crc << 16)) | DataType.byteArrayTo32(randombytes, 0);
        return token;
    }

    public static int getAuthToken2(String deviceId) {
        byte[] auth = deviceId.getBytes();
        Random random = new Random();
        int ii = random.nextInt(0xff);
        byte[] randombytes = DataType.data16ToBytes(ii);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(auth, 0, auth.length);
        os.write(randombytes[0]);
        os.write(randombytes[1]);
        byte[] token_bytes = os.toByteArray();
        int crc = CRCUtil.calc_crc16(token_bytes, token_bytes.length, 0);
        int token = ((crc << 16)) | DataType.byteArrayTo16(randombytes, 0);//191111 eb53
        return token;
    }
}
