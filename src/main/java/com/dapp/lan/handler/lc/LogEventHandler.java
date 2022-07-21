package com.dapp.lan.handler.lc;

import com.dapp.lan.protobuf.DeviceProxyMessageProto;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;


public class LogEventHandler
        extends SimpleChannelInboundHandler<LogEvent> {

    private final static int MAX_DATA_LEN = 1300;
    private ChannelHandlerContext sourceCtx;
    DeviceProxyMessageProto.DeviceProxyMessage.Builder pbuilder = DeviceProxyMessageProto.DeviceProxyMessage.newBuilder();

    public LogEventHandler(ChannelHandlerContext sourceCtx) {
        this.sourceCtx = sourceCtx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             LogEvent event) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(event.getReceivedTimestamp());
        builder.append(" [");
        builder.append(event.getSource().toString());
        builder.append("] [");
        builder.append(event.getLogfile());
        builder.append("] : ");
        builder.append(event.getMsg());
        System.out.println(builder.toString());
        pbuilder.setData(ByteString.copyFromUtf8(builder.toString()));
        sourceCtx.writeAndFlush(pbuilder.build());
        pbuilder.clear();
    }
}