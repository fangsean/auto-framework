package com.auto.test.potato;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("local")
public class Main {

    @Test
    public void server() throws InterruptedException {

        Thread thread = new Thread(() -> server(null));

        thread.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(200));

    }

    @Test
    public void client() throws InterruptedException {

        Thread thread1 = new Thread(() -> client(null));

        thread1.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(200));

    }

    public static void server(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        NioEventLoopGroup workEventExecutors = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventExecutors, workEventExecutors)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ServiceChannelHandler());
            ChannelFuture channelFuture =
                    serverBootstrap.bind(8082).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
            workEventExecutors.shutdownGracefully();
        }
    }

    public static class ServiceChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) {
            socketChannel.pipeline().addLast(new ServiceChannelHandlerAdapter());
        }
    }


    public static class ServiceChannelHandlerAdapter extends ChannelHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf message = (ByteBuf) msg;
            byte[] req = new byte[message.readableBytes()];
            message.readBytes(req);
            String body = new String(req, "UTF-8");
            System.out.println("Result is : " + body);
            ByteBuf byteBuf = Unpooled.copiedBuffer("accept orver".getBytes());
            ctx.write(byteBuf);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
        }
    }


    /////////////////// client ///////////////////

    public static void client(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<NioServerSocketChannel>() {
                        @Override
                        protected void initChannel(NioServerSocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new ClientChannelHandlerAdapter());
                        }
                    });
            ChannelFuture channelFuture =
                    bootstrap.connect(InetAddress.getLocalHost(), 8082).sync();

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }

    public static class ClientChannelHandlerAdapter extends ChannelHandlerAdapter {
        private final ByteBuf message;

        public ClientChannelHandlerAdapter() {
            byte[] req = "=======hello netty.==========".getBytes();
            this.message = Unpooled.buffer(req.length);
            this.message.writeBytes(req);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(message);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf message = (ByteBuf) msg;
            byte[] req = new byte[message.readableBytes()];
            message.readBytes(req);
            String body = new String(req, "UTF-8");
            System.out.println("Now is : " + body);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            System.out.println("=========释放资源==========");
            ctx.close();
        }
    }

}