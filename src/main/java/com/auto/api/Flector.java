package com.auto.api;

import com.auto.entity.Person;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.invoker.Invoker;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p>主流程设计：通过属性名获取、计算、设置值</p>
 */
public class Flector<T> {

    //反射实现计算
    private DefaultReflectorFactory defaultReflectorFactory;
    private MetaClass metaClass;

    public Flector(Class<T> clazz) {
        defaultReflectorFactory = new DefaultReflectorFactory();
        metaClass = MetaClass.forClass(clazz, defaultReflectorFactory);
    }


    public Class<?> getType(String name) {
        Invoker invoker = metaClass.getGetInvoker(name);
        Class<?> type = invoker.getType();
        return type;
    }

    public <U> U getter(T obj, String name) {
        Invoker invoker = metaClass.getGetInvoker(name);
        U invoke = null;
        try {
            invoke = (U) invoker.invoke(obj, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return invoke;
    }

    public void setter(T obj, String name, Object[] value) {
        Invoker invoker = metaClass.getSetInvoker(name);
        try {
            invoker.invoke(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setter(T obj, String name, Object value) {
        Invoker invoker = metaClass.getSetInvoker(name);
        try {
            Object[] objects = {value};
            invoker.invoke(obj, objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Flector<Person> flector = new Flector(Person.class);

        List<Person> data = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(data)) {
            double totalNum = data.stream().mapToDouble(T -> {
                return flector.getter(T, "num");
            }).sum();
        }
    }

}

