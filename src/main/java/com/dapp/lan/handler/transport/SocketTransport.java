package com.dapp.lan.handler.transport;

import com.dapp.lan.config.TransportContextHolder;
import com.dapp.lan.handler.lc.MessageCode;
import com.dapp.lan.handler.lc.message.DeviceProxyMessageFactory;
import com.dapp.lan.handler.lc.message.serverrequest.ConnectResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Admin
 */
@Slf4j
public class SocketTransport {
    private String address;
    private Integer port;
    private ChannelHandlerContext sourceCtx;
    private String connectId;
    private TransportContextHolder holder;
    private Channel channel;
    private NioEventLoopGroup group;

    public SocketTransport(String address,
                           Integer port,
                           ChannelHandlerContext sourceCtx,
                           String connectId,
                           TransportContextHolder holder) {
        this.address = address;
        this.port = port;
        this.sourceCtx = sourceCtx;
        this.connectId = connectId;
        this.holder = holder;
    }

    public void transport() {
        group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new TransportHandler(SocketTransport.this, sourceCtx, connectId, holder));
                        }
                    });
            ChannelFuture future = bootstrap.connect(address, port);
            this.channel = future.channel();
        } catch (Exception e){
            ConnectResponse response = new ConnectResponse();
            response.setId(connectId);
            response.setResult(1);
            sourceCtx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyMessage(MessageCode.CONNECT,  response));
            log.error("[SocketTransport-start] fail ......");
            group.shutdownGracefully();
        }
    }

    public void close() {
        channel.close();
        group.shutdownGracefully();
    }
}
