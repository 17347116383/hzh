package com.dy.rediscore;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: MessageEncoder
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/16 13:16
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MessageEncoder   extends MessageToByteEncoder<Message> {


    // 编码格式
    private final Charset charset = Charset.forName("UTF-8");
    // 需要压缩的长度
    private final int compressLength=1024;

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());

     /*   String source=msg.getData();
        byte[] body=source.getBytes(charset);
        if(body.length > compressLength){
            msg.setZip((byte)1);
            // 加压
            //body=ZipTool.compress(body);
        }

        //cmdId(2)+type(1)+zip(1)+body(4)=8
        //out = Unpooled.directBuffer(8+body.length);
        //cmdId
        out.writeShort(msg.getCmdId());
        //type
        out.writeByte(msg.getType());
        //是否加压
        out.writeByte(msg.getZip());
        //长度
        out.writeInt(body.length);
        //内容
        out.writeBytes(body);*/
    }



}
