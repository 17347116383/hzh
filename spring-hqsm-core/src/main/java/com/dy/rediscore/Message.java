package com.dy.rediscore;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: Message
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/16 13:13
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class Message {


        private int length;   //数据长度
        private String data;  //要发送的数据
        private short cmdId;  //业务编号
        private byte[] content;  //  数据
        private byte type;  //消息类型  0xAF 表示心跳包    0xBF 表示超时包  0xCF 业务信息包
        private	byte zip = 0 ;  //是否压缩，1是，0不是

    public Message() {

    }

    /**
              * 封装要发送的数据包
              * @param data 业务数据
              * @param cmdId 业务标识号
              * @param type 消息类型
              */
        public Message(String data,short cmdId,byte type){
            this.data=data;
            this.cmdId=cmdId;
            this.type=type;
        }

    public Message(int length, String data, short cmdId, byte[] content, byte type, byte zip) {
        this.length = length;
        this.data = data;
        this.cmdId = cmdId;
        this.content = content;
        this.type = type;
        this.zip = zip;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Message(int length, String data, short cmdId, byte type, byte zip) {
        this.length = length;
        this.data = data;
        this.cmdId = cmdId;
        this.type = type;
        this.zip = zip;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public short getCmdId() {
            return cmdId;
        }

        public void setCmdId(short cmdId) {
            this.cmdId = cmdId;
        }

        public byte getType() {
            return type;
        }

        public void setType(byte type) {
            this.type = type;
        }

        public byte getZip() {
            return zip;
        }

        public void setZip(byte zip) {
            this.zip = zip;
        }

}
