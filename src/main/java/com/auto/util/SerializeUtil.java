package com.auto.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
public class SerializeUtil {

    private static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class) ;

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null ;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.error("[SerializeUtil][serialize][param][%s]", JsonUtils.toJson(object),e);
        }finally {
            try {
                if(oos!=null) {
                    oos.close();
                }
                if(baos!=null) {
                    baos.close();
                }
            } catch (IOException e) {
                logger.error("[SerializeUtil][serialize][IOException][param][%s]", JsonUtils.toJson(object),e);
            }
        }
        return bytes;
    }

    public static Object unserialize( byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("[SerializeUtil][unserialize][param]",e);
        }finally {
            try {
                if(bais!=null){
                    bais.close();
                }
            } catch (IOException e) {
                logger.error("[SerializeUtil][unserialize][IOException][param][%s]",e);
            }
        }
        return null;
    }
}

