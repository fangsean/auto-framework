package com.auto.common.classloader;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author auto.yin [auto.yin@gmail.com]
 * 2018-12-22
 * @Description: <p></p>
 */

/**
 * 服务端类,实现监听客户端请求 *
 */
public class Server {
    private ServerSocket ss = null;
    private int port = 8067;
    private Socket sc = null;

    public Server() {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listener() {
        while (true) {
            try {
                sc = ss.accept();
                Thread t = new Thread(new TaskDo(sc));
                //交给任务处理线程处理
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.listener();
    }
}

/**
 * 此类主要是读取客户端传来的字节码,并实现任务执行 *
 */
class TaskDo implements Runnable {
    Socket s;
    InputStream is;
    OutputStream os;
    BufferedInputStream bis;
    byte[] b = null;

    public TaskDo(Socket s) {
        this.s = s;
        try {
            os = s.getOutputStream();
            is = s.getInputStream();
            bis = new BufferedInputStream(is);
            b = new byte[bis.available()];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            read(b);
            execute(null, b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                is.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void read(byte[] b) throws Exception {
        bis.read(b);
    }

    private void execute(String codeName, byte[] code) {
        ClassLoaderTest fileSystemClassLoader = null;
        try {
            fileSystemClassLoader = new ClassLoaderTest();
            fileSystemClassLoader.execute(codeName, code);
        } catch (Exception exception) {
        }
    }
}