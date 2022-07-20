package com.dapp.lan.server;

import com.dapp.lan.config.PersistentConnectionConfiguration;
import com.dapp.lan.config.TransportContextHolder;
import com.dapp.lan.handler.lc.ConnectionListener;
import com.dapp.lan.handler.lc.DefaultRequestHandler;
import com.dapp.lan.handler.lc.decoder.ClientMessageDecoder;
import com.dapp.lan.handler.lc.encoder.ClientMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Admin
 */
@Slf4j
public class PersistentConnectionClient {

    private TransportContextHolder holder;
    private EventLoopGroup group = new NioEventLoopGroup(2);
    private PersistentConnectionConfiguration connectionConfiguration;


    public PersistentConnectionClient connectionConfiguration(PersistentConnectionConfiguration connectionConfiguration) {
        this.connectionConfiguration = connectionConfiguration;
        this.holder = new TransportContextHolder();
        return this;
    }

    public void run() {
        createBootstrap(group);
    }

    public void createBootstrap(EventLoopGroup loop) {
        try {
            log.info("Start connect......");
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loop)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //Socks5Message ByteBuf
                            ch.pipeline().addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS))
                                    .addLast(new ClientMessageDecoder())
                                    .addLast(new ClientMessageEncoder())
                                    .addLast(new DefaultRequestHandler(PersistentConnectionClient.this));
                        }
                    });

            ChannelFuture future = bootstrap.connect(connectionConfiguration.getHost(), connectionConfiguration.getPort());
            future.addListener(new ConnectionListener(PersistentConnectionClient.this));
        } catch (Exception e){
            log.error("[PersistentConnectionClient-start] fail ......", e);
        }
    }

    public TransportContextHolder getHolder() {
        return holder;
    }

    public void setHolder(TransportContextHolder holder) {
        this.holder = holder;
    }

    public PersistentConnectionConfiguration getConnectionConfiguration() {
        return connectionConfiguration;
    }

    public void setConnectionConfiguration(PersistentConnectionConfiguration connectionConfiguration) {
        this.connectionConfiguration = connectionConfiguration;
    }
}
