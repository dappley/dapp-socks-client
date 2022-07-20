package com.dapp.lan.config;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Admin
 */

public class TransportContextHolder {
    private ConcurrentHashMap<String, ChannelHandlerContext> contexts = new ConcurrentHashMap<>();

    public void addContext(String connectId, ChannelHandlerContext context) {
        contexts.put(connectId, context);
    }

    public void removeContext(String connectId) {
        contexts.remove(connectId);
    }

    public ChannelHandlerContext getContext(String connectId) {
        return contexts.get(connectId);
    }

    public int size() {
        return contexts.size();
    }
}
