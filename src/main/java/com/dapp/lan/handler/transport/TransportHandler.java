package com.dapp.lan.handler.transport;

import com.dapp.lan.config.TransportContextHolder;
import com.dapp.lan.handler.lc.MessageCode;
import com.dapp.lan.handler.lc.message.DeviceProxyMessageFactory;
import com.dapp.lan.handler.lc.message.serverrequest.ConnectResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Admin
 */
@Slf4j
public class TransportHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final static int MAX_DATA_LEN = 1500;
    private ChannelHandlerContext sourceCtx;
    private String connectId;
    private TransportContextHolder holder;
    private SocketTransport socketTransport;

    public TransportHandler(SocketTransport socketTransport, ChannelHandlerContext sourceCtx, String connectId, TransportContextHolder holder) {
        this.sourceCtx = sourceCtx;
        this.connectId = connectId;
        this.holder = holder;
        this.socketTransport = socketTransport;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        int readLength = Math.min(msg.readableBytes(), MAX_DATA_LEN);
        while (readLength > 0) {
            sourceCtx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyDataMessage(connectId, msg, readLength));
            readLength = Math.min(msg.readableBytes(), MAX_DATA_LEN);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        holder.addContext(connectId, ctx);
        ConnectResponse response = new ConnectResponse();
        response.setId(connectId);
        response.setResult(0);
        log.info("connection id {} is active, wait to receive ......", connectId);
        sourceCtx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyMessage(MessageCode.CONNECT, response));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        socketTransport.close();
        log.info("[TransportHandler-channelInactive], close a connection is {}", connectId);
    }


}
