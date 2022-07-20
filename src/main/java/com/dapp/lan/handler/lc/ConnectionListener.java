package com.dapp.lan.handler.lc;

import com.dapp.lan.server.PersistentConnectionClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author: suxinsen
 * @create_time: 2020/12/16 11:03
 **/
@Slf4j
public class ConnectionListener implements ChannelFutureListener {

    private PersistentConnectionClient persistentConnectionClient;

    public ConnectionListener(PersistentConnectionClient persistentConnectionClient) {
        this.persistentConnectionClient = persistentConnectionClient;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    persistentConnectionClient.createBootstrap(loop);
                }
            }, 5L, TimeUnit.SECONDS);
        }
    }

}
