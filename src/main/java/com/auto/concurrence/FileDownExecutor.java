package com.auto.concurrence;

import com.auto.common.mertics.JProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownExecutor {

    public static final Logger logger = LoggerFactory.getLogger(FileDownExecutor.class);


    public final static String file_remote = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2122666338,3181618951&fm=173&app=25&f=JPEG?w=640&h=554&s=95AAE7FB940246EE302D242803009053";

    public final static String file_local = "/home/auto.yin/aa.jpg";

    public final static int threadCount = 3;

    private final static FileDownExecutor executor = new FileDownExecutor();

    public static FileDownExecutor init() {
        return executor;
    }


    private class DownThreadImpl extends Thread {

        private URL url;
        private String fileName;
        private long start;
        private long end;

        public DownThreadImpl(ThreadGroup group, String name, URL url, String fileName, long start, long end) {
            super(name);
            this.url = url;
            this.fileName = fileName;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
//            super.run();
            HttpURLConnection connection = null;
            InputStream in = null;
            RandomAccessFile randomAccessFile = null;
            try {

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("range", String.format("bytes=%s-%s", start, end));

                connection.connect();
                int responseCode = connection.getResponseCode();
                if (200 == responseCode) {
                    int contentLength = connection.getContentLength();
                    logger.debug(String.format("thread:%s ,down size: %s", this.getName(), contentLength));
                    in = connection.getInputStream();
                    randomAccessFile = new RandomAccessFile(new File(this.fileName), "rwd");
                    randomAccessFile.seek(start);
                    byte[] bytes = new byte[2 << 3];
                    int length = 0;
                    while ((length = in.read(bytes)) != -1) {
                        randomAccessFile.write(bytes, 0, length);
                        System.out.println(String.format("%s: %s %s", getName(), length, new String(bytes, "UTF-8")));
                    }
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != randomAccessFile)
                        randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (null != in) {
                        in.close();
                        in = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connection.disconnect();
                connection = null;
            }
        }
    }


    /**
     * 单线程断点下载
     *
     * @throws IOException
     */
    /*public static void nomalDown() throws IOException {

        File file = new File(file_local);
        long size = file.length();
        System.err.println(file.length());

        URL url = new URL(file_remote);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
//设置下载区间
        con.setRequestProperty("range", "bytes=" + size + "-");
        con.connect();
        int code = con.getResponseCode();
        System.err.println(code);
        if (code == 200) {
            InputStream in = con.getInputStream();
            int serverSize = con.getContentLength();
            System.err.println("服务器返回的长度:" + serverSize);
            System.err.println("这次从哪开开始写:" + size);
//使用RandomAccessFile可以在制定位置向文件中写数据
            RandomAccessFile out = new RandomAccessFile(file, "rw");
            out.seek(size);

            byte[] b = new byte[1024];
            int len = -1;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.close();
            out = null;

        }

    }*/


    /**
     * 多线程断点下载
     *
     * @throws IOException
     */
    public void breakDown4Thread() throws IOException {

        URL url = new URL(file_remote);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoInput(true);
        /*con.setRequestProperty("range","bytes="+1+"-");*/
        con.connect();
        int code = con.getResponseCode();
        con.disconnect();
        if (200 != code) {
            return;
        }

        int contentLength = con.getContentLength();

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(file_local), "rw");

        //local file content Length
        long localLength = randomAccessFile.length();

        randomAccessFile.close();

        //more thread

        long everyThreadLength = 0L;
        if (localLength == 0) {
            everyThreadLength = (contentLength / threadCount + (contentLength % threadCount == 0 ? 0 : 1));
        } else if (localLength < contentLength) {
            everyThreadLength = ((contentLength - localLength) / threadCount) + ((contentLength - localLength) % threadCount == 0 ? 0 : 1);
        } else {
            return;
        }

        System.out.println(String.format("every thread's length %s", everyThreadLength));

        long start = 0L, end = 0L, i = 0L;
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        String fileName = file_local;

        for (; i < threadCount; i++) {
            if (localLength == 0L) {
                start = i * everyThreadLength;
            } else {
                start = localLength + 1 + i * everyThreadLength;
            }
            end = start + everyThreadLength - 1;
            if (end > contentLength) {
                end = contentLength;
            }

            String name = Thread.currentThread().getName() + "-" + i;
            //doing down thread
            new DownThreadImpl(group, name, url, fileName, start, end).start();
        }

    }

    public static void main(String[] args) {

        JProfile.enter("test");

        FileDownExecutor executor = FileDownExecutor.init();
        try {
            executor.breakDown4Thread();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            JProfile.release();
        }


    }


}
