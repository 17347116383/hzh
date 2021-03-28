package com.dy.websocket;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.marshalling.CompatibleMarshallingDecoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * @ProjectName: hqsm-parent  行
 * @Package: com.dy.websocket
 * @ClassName: DecoderCompatibleMarshallingHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 18:39
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderLineBasedFrameHandler extends LineBasedFrameDecoder {
/*一个解码器，它将行结束时接收到的{@link ByteBuf}分隔开。
两个{@code“\ n”}和{@code " \ r \ n "}处理。
字节流应该是UTF-8字符编码或ASCII格式。当前的实现
直接将{@code byte}转换为{@code char}，然后将{@code char}与几个低范围进行比较
 ASCII字符，如{@code '\n'}或{@code '\r'}。UTF-8没有使用低范围[0..0x7F]
            多字节codepoint表示的字节值因此完全支持此实现*/

    public DecoderLineBasedFrameHandler(int maxLength) {
        super(maxLength);
    }
}
