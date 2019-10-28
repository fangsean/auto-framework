package com.auto.netty;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-10-24
 * @since JDK 1.8
 */
public class Demo {

    public static void main(String[] args) {

        try {
            Object ioHandler = new Object();
            int port = 8080;

            //打开管道，监听客户端连接
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved(InetAddress.getByName("IP").getHostName(), port);
            //绑定监听
            socketChannel.socket().bind(inetSocketAddress);
            //非阻塞模式
            socketChannel.configureBlocking(false);
            //建立reactor线程，创建多路复用器并启动线程
            Selector selector = Selector.open();
//            new Thread(new ReactorTask())
            //将socketChannel注册到Reactor多路复用器selector上，监听ACCEPT事件
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_ACCEPT, ioHandler);
            //多路复用器在线程run方法的无限循环中轮训准备就绪的Key
            int num = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key01 = iterator.next();
                //  deal with I/O event
            }

            //多路复用器监听到新的客户接入，处理新的请求，完成3次TCP握手，建立物理连接
            SocketChannel channel = socketChannel.accept();
            channel.configureBlocking(false);
            channel.socket().setReuseAddress(true);
            //新的连接注册到Reactor线程多路复用器
            SelectionKey register = socketChannel.register(selector, SelectionKey.OP_READ, ioHandler);

//            channel.read()



        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
    
    