package com.auto.concurrence.singleton;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description: <p></p>
 */
final public class HolderSingleton {

    private byte[]  data  = new byte[1024];

    static private class Holder{
        //clinit method : valatile, sync,
        static private HolderSingleton instance =  new HolderSingleton();
    }

    static public HolderSingleton getInstance(){
        return Holder.instance;
    }


    private HolderSingleton(){}



}
