package com.auto.concurrence.singleton;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description: <p></p>
 */
public /*enum*/  class EnumSington {

    /*INSTANCE*/;

    private byte[] data = new byte[1024];

    private EnumSington() {
        System.out.println(EnumSington.class.getName());
    }


    public static void method() {
        // ... Sington instace
    }

    public static EnumSington getInstance() {
        /*return INSTANCE*/
        ;
        return EnumHolder.INSTANCE.getInstance();
    }

    private enum EnumHolder {
        INSTANCE;
        private EnumSington instance;

        EnumHolder() {
            instance = new EnumSington();
        }

        private EnumSington getInstance() {
            return instance;
        }

    }

}
