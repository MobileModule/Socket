package com.xuhao.didi.socket.common.interfaces.default_protocol;

import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.socket.common.interfaces.utils.DataType;

import java.nio.ByteOrder;

public class DruidDefaultReaderProtocol implements IReaderProtocol {

    /**
     * 第一层v0+v1 蓝牙传输最外层数据（蓝牙分包发送）分组信息(1byte)+分组数据(mtu DATAS)
     * <p>
     * 第二层v0+v1【数据最外层】 命令序号(seq 1byte)+功能码(cmd 1byte)+数据长度(len 2byte)+数据(DATA)+校验(crc 2byte)
     * 第三层v0 DATA 指令类型(4 bytes)+ 数据包长度(4 bytes)+protobuf
     * 第三层v1 DATA 版本协议（2 bytes）+厂家ID（2 bytes）+指令类型（2 bytes）+数据包长度（2 bytes）+
     * 是否加密（1 byte 0：no 1：yes）+预留位（1 byte）+校验位（2 bytes crc16）+protobuf
     */
    @Override
    public int getHeaderLength() {
        return 12;
    }

    /**
     * 数据总长度
     */
    @Override
    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
        return DataType.byteArrayTo16(header, 6);
    }
}
