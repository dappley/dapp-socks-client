package com.dapp.lan.handler.lc;

import com.dapp.lan.protobuf.DeviceProxyMessageProto;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

/**
 * @author Sun
 * @description:
 * @param: null
 * @return:
 * @date: 2022/3/28 10:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UDPClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final static int MAX_DATA_LEN = 1300;
    private ChannelHandlerContext sourceCtx;
    private  String resp ;



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String response = msg.content().toString(CharsetUtil.UTF_8);
        DeviceProxyMessageProto.DeviceProxyMessage.Builder builder = DeviceProxyMessageProto.DeviceProxyMessage.newBuilder();
        if (response.startsWith(resp)) {
            int readLength = Math.min(msg.content().readableBytes(), MAX_DATA_LEN);
            while (readLength > 0) {
                byte[] data = new byte[readLength];
                msg.content().readBytes(data);
                builder.setData(ByteString.copyFrom(data));
                ctx.writeAndFlush(builder.build());
                builder.clear();
                readLength = Math.min(msg.content().readableBytes(), MAX_DATA_LEN);
            }
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        sourceCtx.close();
    }
}
