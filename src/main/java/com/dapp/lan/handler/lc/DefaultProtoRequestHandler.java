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
import com.dapp.lan.protobuf.DeviceProxyMessageProto;
import com.dapp.lan.server.PersistentConnectionClient;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Admin
 */
@Slf4j
public class DefaultProtoRequestHandler extends ChannelInboundHandlerAdapter {

    private PersistentConnectionClient client;

    DeviceProxyMessageProto.DeviceProxyMessage.Builder builder = DeviceProxyMessageProto.DeviceProxyMessage.newBuilder();


    public DefaultProtoRequestHandler(PersistentConnectionClient client) {
        this.client = client;
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DeviceProxyMessageProto.DeviceProxyMessage protoMsg = (DeviceProxyMessageProto.DeviceProxyMessage)msg;
        MessageCode messageCode;
        try {
            messageCode = MessageCode.value2MessageCode(protoMsg.getCode());
        } catch (Exception e) {
            log.error("Invalid message code {}", protoMsg.getCode());
            return;
        }

        switch (messageCode) {
            case LOGIN:
                loginResponse(ctx, protoMsg);
                break;
            case CONNECT:
                connectResponse(protoMsg, ctx);
                break;
            case PROXY_DATA:
                transferData(protoMsg, ctx);
                break;
            case UDP_ASSOCIATE:
                udpRes(ctx, protoMsg);
                break;
            case HEARTBEAT:
                heartbeatResponse(protoMsg, ctx);
                break;
            case DISCONNECT:
                disconnect(protoMsg, ctx);
                break;
            default:
                log.error("Unexpected");
        }
    }

    private void udpRes(ChannelHandlerContext ctx, DeviceProxyMessageProto.DeviceProxyMessage msg) throws Exception {
//        if(!Objects.isNull(msg.getPort()) && !Objects.isNull(msg.getData())){
//            new UDPClient(ctx,msg.getPort(),msg.getData().toStringUtf8(),"谚语查询结果：").run();
//        }
        if (!Objects.isNull(msg.getPort())) {
            LogEventMonitor monitor = new LogEventMonitor(
                    new InetSocketAddress(msg.getPort()));
            try {
                Channel channel = monitor.bind();
                System.out.println("LogEventMonitor running");
                channel.closeFuture().sync();
            } finally {
                monitor.stop();
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) {
//                HeartbeatRequest request = new HeartbeatRequest();
//                request.setUserName(client.getConnectionConfiguration().getUsername());
//                ctx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyMessage(MessageCode.HEARTBEAT, request));

                builder.setCode(MessageCode.HEARTBEAT.value());
                builder.setUserName(client.getConnectionConfiguration().getUsername());
                ctx.writeAndFlush(builder.build());
                builder.clear();
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("Channel activate......");

//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUserName(client.getConnectionConfiguration().getUsername());
//        ctx.writeAndFlush(DeviceProxyMessageFactory.encodeProxyMessage(MessageCode.LOGIN, loginRequest));


        builder.setCode(MessageCode.LOGIN.value());
        builder.setUserName(client.getConnectionConfiguration().getUsername());
        ctx.writeAndFlush(builder.build());
        builder.clear();

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

    private void loginResponse(ChannelHandlerContext ctx, DeviceProxyMessageProto.DeviceProxyMessage msg) {
//        Gson gson = new Gson();
//        LoginResponse response = gson.fromJson(
//                new String(msg.getData(), StandardCharsets.UTF_8), LoginResponse.class);
        if (ErrorCode.FAIL.code.equals(msg.getError())) {
            log.info("Login Fail : {}",  ErrorCode.getValue(msg.getError()));
            ctx.channel().close();
        } else {
            client.getConnectionConfiguration().setUsername(msg.getUserName());
            log.info("Login Success, proxy [ip:{}, port:{}] to url is : http://{}.dappworks.net",
                    client.getConnectionConfiguration().getProxyIp(), client.getConnectionConfiguration().getProxyPort(), msg.getUserName());
            log.info("Login Success, proxy [ip:{}, port:{}] to url is : https://{}.dappworks.net",
                    client.getConnectionConfiguration().getProxyIp(), client.getConnectionConfiguration().getProxyPort(), msg.getUserName());
        }
    }

    private void connectResponse(DeviceProxyMessageProto.DeviceProxyMessage msg, ChannelHandlerContext ctx) {
//        Gson gson = new Gson();
//        ConnectRequest connectRequest = gson.fromJson(
//                new String(msg.getData(), StandardCharsets.UTF_8), ConnectRequest.class);

//        SocketTransport transport = new SocketTransport(client.getConnectionConfiguration().getProxyIp(),
//                client.getConnectionConfiguration().getProxyPort(),
//                ctx,
//                connectRequest.getId(),
//                client.getHolder());

        SocketTransport transport = new SocketTransport(msg.getDst(),
                msg.getPort(),
                ctx,
                msg.getId(),
                client.getHolder());
        transport.transport();
    }

    private void transferData(DeviceProxyMessageProto.DeviceProxyMessage msg, ChannelHandlerContext ctx) {
//        ProxyDataMessage dataMessage = DeviceProxyMessageFactory.decodeProxyDataMessage(msg);
//        byte[] byteArray = msg.getData().toByteArray();
//        ProxyDataMessage dataMessage = DeviceProxyMessageFactory.decodeProxyDataByteMessage(byteArray);
        ChannelHandlerContext context = client.getHolder().getContext(msg.getId());
        if (context != null) {
            ByteBuf sendBuf = ctx.alloc().buffer(msg.getData().toByteArray().length);
            sendBuf.writeBytes(msg.getData().toByteArray());
//            System.out.println("client.transferData--------------------------------"+msg.getData().toStringUtf8());
            context.writeAndFlush(sendBuf) ;
        }
    }

    private void heartbeatResponse(DeviceProxyMessageProto.DeviceProxyMessage msg, ChannelHandlerContext ctx) {
//        Gson gson = new Gson();
//        HeartbeatResponse response = gson.fromJson(
//                new String(msg.getData(), StandardCharsets.UTF_8), HeartbeatResponse.class);
    }

    private void disconnect(DeviceProxyMessageProto.DeviceProxyMessage msg, ChannelHandlerContext ctx) {
//        Gson gson = new Gson();
//        DisconnectRequest response = gson.fromJson(
//                new String(msg.getData(), StandardCharsets.UTF_8), DisconnectRequest.class);
        ChannelHandlerContext context = client.getHolder().getContext(msg.getId());
        if (context != null) {
            client.getHolder().removeContext(msg.getId());
            context.channel().close();
        }
    }
}
