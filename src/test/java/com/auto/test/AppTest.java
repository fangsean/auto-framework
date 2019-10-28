package com.auto.test;

import org.springframework.core.io.ResourceLoader;
import com.auto.common.annotation.Af;
import com.auto.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * Created by jobob on 17/5/16.
 */
@Slf4j
public class AppTest {

    public static void main(String[] args) {

        for (int i=0;i<10000000;i++){
            new Thread(()->{
                System.out.println(System.currentTimeMillis());
            }).start();
        }


    }

    @Before
    public void test() {

        Af af = UserServiceImpl.class.getAnnotation(Af.class);
        String res = String.format("AliasForServiceImpl Annotation " + "Af.value = [%s], Af.attribute = [%s]", af.value(), af.attribute());
        log.error(res);

    }

    @Test
    public void test1(){
        Af af = AnnotationUtils.getAnnotation(UserServiceImpl.class, Af.class);
        String res = String.format("AnnotationUtils#getAnnotation(targetClazz, AnnotationClazz) " +
                "AliasForServiceImpl Annotation " +
                "Af.value = [%s], Af.attribute = [%s]", af.value(), af.value());
        log.error(res);
    }


//10:22:26.875 [main] ERROR com.auto.test.AppTest - AliasForServiceImpl Annotation Af.value = [aa], Af.attribute = [bb]
//10:23:15.393 [main] ERROR com.auto.test.AppTest - AliasForServiceImpl Annotation Af.value = [aa], Af.attribute = [bb]
}
