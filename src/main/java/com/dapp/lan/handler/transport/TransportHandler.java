package com.dapp.lan.handler.transport;

import com.dapp.lan.config.TransportContextHolder;
import com.dapp.lan.handler.lc.MessageCode;
import com.dapp.lan.handler.lc.message.serverrequest.ConnectResponse;
import com.dapp.lan.protobuf.DeviceProxyMessageProto;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Admin
 */
@Slf4j
public class TransportHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final static int MAX_DATA_LEN = 1300;
    private ChannelHandlerContext sourceCtx;
    private String connectId;
    private TransportContextHolder holder;
    private SocketTransport socketTransport;
    DeviceProxyMessageProto.DeviceProxyMessage.Builder builder = DeviceProxyMessageProto.DeviceProxyMessage.newBuilder();


    public TransportHandler(SocketTransport socketTransport, ChannelHandlerContext sourceCtx, String connectId, TransportContextHolder holder) {
        this.sourceCtx = sourceCtx;
        this.connectId = connectId;
        this.holder = holder;
        this.socketTransport = socketTransport;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        int readLength = Math.min(msg.readableBytes(), MAX_DATA_LEN);
//        System.out.println("leng-----------------------"+msg.readableBytes());
        while (readLength > 0) {
            byte[] data = new byte[readLength];
//            System.out.println("THandler.read--------------------------------"+data.toString());
            builder.setCode(MessageCode.PROXY_DATA.value());
            builder.setTag(0);
            msg.readBytes(data);
            builder.setData(ByteString.copyFrom(data));
            builder.setId(connectId);
//            System.out.println("builder.data----------------------------------"+builder.getData().toStringUtf8());
            sourceCtx.writeAndFlush(builder.build());
            builder.clear();
            readLength = Math.min(msg.readableBytes(), MAX_DATA_LEN);
//            System.out.println("readLength--------------------------"+readLength);
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

        builder.setCode(MessageCode.CONNECT.value());
        builder.setTag(0);
        builder.setId(connectId);
        builder.setResult(0);
        builder.setData(ByteString.copyFrom(response.toString().getBytes(StandardCharsets.UTF_8)));
        sourceCtx.writeAndFlush(builder.build());
        builder.clear();

//        sourceCtx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyMessage(MessageCode.CONNECT, response));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        socketTransport.close();
        log.info("[TransportHandler-channelInactive], close a connection is {}", connectId);
    }


}
