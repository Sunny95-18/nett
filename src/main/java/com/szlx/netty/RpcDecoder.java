package com.szlx.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Sunny
 * @version 1.0
 * @date 2021/5/13 14:43
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> target;

    public RpcDecoder(Class target) {
        this.target = target;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {   // 不够长度丢弃
            return;
        }
        in.markReaderIndex();   // 标记一下当前的readIndex的位置
        int dataLength = in.readInt();  // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4

        if (in.readableBytes() < dataLength) {  // 读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = JSON.parseObject(data, target);    // 将byte数据转化为我们需要的对象
        out.add(obj);
    }

}
