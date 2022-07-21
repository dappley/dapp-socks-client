package com.dapp.lan.handler.lc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetSocketAddress;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UDPClient {

    private ChannelHandlerContext sourceCtx;
    private int port= 8011;
    private String req = "谚语字典查询？";
    private String resp ="谚语查询结果：";

    public void run() throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UDPClientHandler(sourceCtx,resp));

            Channel ch = b.bind(0).sync().channel();
            ch.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer(req, CharsetUtil.UTF_8),
                            new InetSocketAddress("255.255.255.255", port))
            ).sync();
            if (!ch.closeFuture().await(15000)) {
                System.out.println("查询超时！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new UDPClient().run();

    }


}
