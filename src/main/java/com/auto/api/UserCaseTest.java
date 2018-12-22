package com.auto.api;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author auto.yin<auto.yin@gmail.com>
 * 2018-06-19
 * @Description: <p></p>
 */
@Slf4j
public class UserCaseTest {

    public static void main(String[] args) {

        List<Integer> useCases = new ArrayList(
                //Arrays工具类的内部类，是只读的，不能新增和删除
                Arrays.asList(new Integer[]{1, 2, 3, 4})
        );

        //useCases.add(47);
        //useCases.add(48);
        //useCases.add(49);
        //useCases.add(50);
        Collections.addAll(useCases, 47, 48, 49, 50);

        trackT(useCases, PasswordUtils.class);

//        PasswordUtils passwordUtils = new PasswordUtils();

//        System.out.println(passwordUtils.encryptPassword("4gsdjhh"));

    }

    public static void trackT(List<Integer> useCases, Class<?> cl) {
        Method[] declaredMethods = cl.getDeclaredMethods();
        for (Method m : declaredMethods) {
            //获得注解的对象
            UseCase uc = m.getAnnotation(UseCase.class);

            if (uc != null) {
                log.info(String.format("Method %s ,Found Use Case:%d , %s ", m.getName(), uc.id(), uc.description()));
                //需要装箱
                useCases.remove(new Integer(uc.id()));
            }
        }
        for (int i : useCases) {
            log.info("Warning: Missing use case-" + i);
        }
    }


}
