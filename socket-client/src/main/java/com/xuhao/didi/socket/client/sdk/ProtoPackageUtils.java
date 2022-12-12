package com.xuhao.didi.socket.client.sdk;

import com.xuhao.didi.socket.common.interfaces.utils.CRCUtil;
import com.xuhao.didi.socket.common.interfaces.utils.DataType;

import java.io.ByteArrayOutputStream;

public class ProtoPackageUtils {
    public static final int VERSION = 0x01;//版本协议
    public static final int FACTORY_ID = 0x06;//厂家ID
    public static final int ENCRYPTION = 0x00;//是否加密 0否，1是
    public static final int RESERVE = 0x00;//预留位

    /**
     * proto数据封装成协议
     * 版本协议（2 bytes）+厂家ID（2 bytes）+指令类型（2 bytes）+数据包长度（2 bytes）
     * +是否加密（1 byte 0：no 1：yes）+预留位（1 byte）+校验位（2 bytes crc16）+protobuf
     */
    public static byte[] doDeal(int cmd, byte[] data) {

        // Step one - package
        int data_len = (data == null) ? 0 : data.length;
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] version = DataType.data16ToBytes(VERSION);
        os.write(version, 0, version.length);

        byte[] factory_id = DataType.data16ToBytes(FACTORY_ID);
        os.write(factory_id, 0, factory_id.length);

        byte[] cmd_my = DataType.data16ToBytes(cmd);
        os.write(cmd_my, 0, cmd_my.length);

        byte[] length = DataType.data16ToBytes(data_len);
        os.write(length, 0, length.length);

        os.write((byte) ENCRYPTION);
        os.write((byte) RESERVE);

        //crc_16
        int crc = CRCUtil.calc_crc16(os.toByteArray(), os.toByteArray().length, 0);
        int crc_val = CRCUtil.calc_crc16_val(crc, data, data_len, 0);
        byte[] crcs = DataType.data16ToBytes(crc_val);
        os.write(crcs, 0, crcs.length);

        if (data_len > 0) {
            os.write(data, 0, data_len);
        }

        byte[] pkg = os.toByteArray();

        return pkg;
    }
}
