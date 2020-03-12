package com.auto.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-29
 * @Description: <p></p>
 */
public class ListCaseTest {


    @Test
    public void test() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();

        list.add("1");
        list.add("12");
        list.add("123");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");

        Iterator it = list.iterator();
        while (it.hasNext()) {
            list.remove(it.next());
        }
        System.out.println(list.size());

    }



    @Test
    public void test1() {
        ArrayList list = new ArrayList();

        list.add("1");
        list.add("12");
        list.add("123");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");

        Iterator it = list.iterator();

        while (it.hasNext()) {
            Object next = it.next();
            it.remove();
            //list.remove(next);
        }

        System.out.println(list.size());

    }


}
