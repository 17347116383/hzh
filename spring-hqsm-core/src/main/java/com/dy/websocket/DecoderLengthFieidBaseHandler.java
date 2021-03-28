package com.dy.websocket;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @ProjectName: hqsm-parent   给予长度的解码器
 * @Package: com.dy.websocket
 * @ClassName: DecoderLengthFieidBaseHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 20:31
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderLengthFieidBaseHandler extends LengthFieldBasedFrameDecoder {
    /**
     *
     * @param maxFrameLength
     * @param lengthFieldOffset
     * @param lengthFieldLength
     */
    public DecoderLengthFieidBaseHandler(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {


        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }
}
