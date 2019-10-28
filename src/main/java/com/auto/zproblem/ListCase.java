package com.auto.zproblem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-29
 * @Description: <p></p>
 */
public class ListCase {


    public static void main(String[] args) {
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



    public void test() {

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
            it.next();
            it.remove();
        }

        System.out.println(list.size());

    }


}
