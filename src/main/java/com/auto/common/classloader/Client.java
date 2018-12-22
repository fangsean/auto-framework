package com.auto.common.classloader;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author auto.yin [auto.yin@gmail.com]
 * 2018-12-22
 * @Description: <p></p>
 */

/**
 * 客户端类,负责读取实现了TaskIntf接口的类的字节码,并发送给服务端 *
 */
public class Client {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 2; i++) {
                byte[] code = getClassDefinition(TaskIntfImpl.class.getName());
                send(code);
            }
        } catch (Exception ex) {
        }
    }

    private static byte[] getClassDefinition(String codeName) {
        String userDir = System.getProperty("user.dir") + "/frost-extend/target/classes";
        ClassLoaderTest fscl1 = null;
        try {
            fscl1 = new ClassLoaderTest(userDir);
        } catch (Exception fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return fscl1.findClassBytes(codeName);
    }

    private static void send(byte[] b) {
        OutputStream os = null;
        Socket s = null;
        try {
            s = new Socket("127.0.0.1", 8067);
            os = s.getOutputStream();
            os.write(b);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
    