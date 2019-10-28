package com.auto.algorithm.data;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.MultiMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-09
 * @since JDK 1.8
 */
public class DataClean {

    @Data
    public static class FileBean {

        private String fileName;

        private MultiMap content;

    }


    public static void main(String[] args) throws IOException {
        String dir = "/mnt/work/myworkstore/dtwave/宝马国际化/data";
        File files = new File(dir);
        if (!files.exists()) {
            System.exit(0);
        }
        LinkedList<FileBean> objects = Lists.newLinkedList();
        for (String filename : files.list()) {
            FileBean bean = new FileBean();
            objects.add(bean);
            bean.fileName = filename;
            MultiMap content = new MultiMap();
            bean.content = content;
            Files.asCharSource(new File(dir, filename), Charset.defaultCharset())
                    .readLines(new LineProcessor<String>() {
                        @Override
                        public boolean processLine(String line) {
                            //这里处理一行字符串
                            if (StringUtils.isBlank(line)) {
                                return false;
                            }
                            try {
                                String[] s = line.split(" ");
                                String s1 = s[1];
                                if (StringUtils.contains(".", s1)) {
                                    String[] split = s1.split(".");
                                    String key = split[0];
                                    content.put(key, line);
                                } else {
                                    content.put(s1, line);
                                }
                            } catch (Exception e) {
                                content.put(line, line);
                            }
                            return true;
                        }

                        @Override
                        public String getResult() {
                            return null;
                        }
                    });
        }

        System.out.println(objects);

    }


}
    
    