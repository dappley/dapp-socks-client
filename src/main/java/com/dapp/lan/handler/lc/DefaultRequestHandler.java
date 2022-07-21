package com.dapp.lan.handler.lc;

import com.dapp.lan.handler.lc.common.ErrorCode;
import com.dapp.lan.handler.lc.common.ProxyDataMessage;
import com.dapp.lan.handler.lc.message.DeviceProxyMessage;
import com.dapp.lan.handler.lc.message.DeviceProxyMessageFactory;
import com.dapp.lan.handler.lc.message.clientrequest.HeartbeatRequest;
import com.dapp.lan.handler.lc.message.clientrequest.HeartbeatResponse;
import com.dapp.lan.handler.lc.message.clientrequest.LoginRequest;
import com.dapp.lan.handler.lc.message.clientrequest.LoginResponse;
import com.dapp.lan.handler.lc.message.serverrequest.ConnectRequest;
import com.dapp.lan.handler.lc.message.serverrequest.DisconnectRequest;
import com.dapp.lan.handler.transport.SocketTransport;
import com.dapp.lan.server.PersistentConnectionClient;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author Admin
 */
@Slf4j
public class DefaultRequestHandler extends SimpleChannelInboundHandler<DeviceProxyMessage> {

    private PersistentConnectionClient client;

    public DefaultRequestHandler(PersistentConnectionClient client) {
        this.client = client;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceProxyMessage msg) throws Exception {
        MessageCode messageCode;
        try {
            messageCode = MessageCode.value2MessageCode(msg.getCode());
        } catch (Exception e) {
            log.error("Invalid message code {}", msg.getCode());
            return;
        }

        switch (messageCode) {
            case LOGIN:
                loginResponse(ctx, msg);
                break;
            case CONNECT:
                connectResponse(msg, ctx);
                break;
            case PROXY_DATA:
                transferData(msg, ctx);
                break;
            case HEARTBEAT:
                heartbeatResponse(msg, ctx);
                break;
            case DISCONNECT:
                disconnect(msg, ctx);
                break;
            default:
                log.error("Unexpected");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) {
                HeartbeatRequest request = new HeartbeatRequest();
                request.setUserName(client.getConnectionConfiguration().getUsername());
                ctx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyMessage(MessageCode.HEARTBEAT, request));
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("Channel activate......");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(client.getConnectionConfiguration().getUsername());
        ctx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyMessage(MessageCode.LOGIN, loginRequest));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Connect close........");
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                client.createBootstrap(eventLoop);
            }
        }, 5L, TimeUnit.SECONDS);
    }

    private void loginResponse(ChannelHandlerContext ctx, DeviceProxyMessage msg) {
        Gson gson = new Gson();
        LoginResponse response = gson.fromJson(
                new String(msg.getData(), StandardCharsets.UTF_8), LoginResponse.class);
        if (ErrorCode.FAIL.code.equals(response.getError())) {
            log.info("Login Fail : {}",  ErrorCode.getValue(response.getError()));
            ctx.channel().close();
        } else {
            client.getConnectionConfiguration().setUsername(response.getUsername());
            log.info("Login Success, proxy [ip:{}, port:{}] to url is : http://{}.dappworks.net",
                    client.getConnectionConfiguration().getProxyIp(), client.getConnectionConfiguration().getProxyPort(), response.getUsername());
            log.info("Login Success, proxy [ip:{}, port:{}] to url is : https://{}.dappworks.net",
                    client.getConnectionConfiguration().getProxyIp(), client.getConnectionConfiguration().getProxyPort(), response.getUsername());
        }
    }

    private void connectResponse(DeviceProxyMessage msg, ChannelHandlerContext ctx) {
        Gson gson = new Gson();
        ConnectRequest connectRequest = gson.fromJson(
                new String(msg.getData(), StandardCharsets.UTF_8), ConnectRequest.class);
        SocketTransport transport = new SocketTransport(client.getConnectionConfiguration().getProxyIp(),
                client.getConnectionConfiguration().getProxyPort(),
                ctx,
                connectRequest.getId(),
                client.getHolder());
        transport.transport();
    }

    private void transferData(DeviceProxyMessage msg, ChannelHandlerContext ctx) {
        ProxyDataMessage dataMessage = DeviceProxyMessageFactory.decodeProxyDataMessage(msg);
        ChannelHandlerContext context = client.getHolder().getContext(dataMessage.getId());
        if (context != null) {
            ByteBuf sendBuf = ctx.alloc().buffer(dataMessage.getData().length);
            sendBuf.writeBytes(dataMessage.getData());
            context.writeAndFlush(sendBuf);
        }
    }

    private void heartbeatResponse(DeviceProxyMessage msg, ChannelHandlerContext ctx) {
        Gson gson = new Gson();
        HeartbeatResponse response = gson.fromJson(
                new String(msg.getData(), StandardCharsets.UTF_8), HeartbeatResponse.class);
    }

    private void disconnect(DeviceProxyMessage msg, ChannelHandlerContext ctx) {
        Gson gson = new Gson();
        DisconnectRequest response = gson.fromJson(
                new String(msg.getData(), StandardCharsets.UTF_8), DisconnectRequest.class);
        ChannelHandlerContext context = client.getHolder().getContext(response.getId());
        if (context != null) {
            client.getHolder().removeContext(response.getId());
            context.channel().close();
        }
    }
}
