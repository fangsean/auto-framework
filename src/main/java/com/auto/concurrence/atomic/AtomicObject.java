package com.auto.concurrence.atomic;

import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description: <p></p>
 */
public class AtomicObject /*AtomicReference*/ /*AtomicStampedReference*/ implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807051L;

    private static Unsafe unsafe = getUnsafe();;

    private static final long valueOffset;
    private volatile int value;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset(AtomicObject.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    @CallerSensitive
    private static Unsafe getUnsafe()  {
        Class<?> unsafeClass = Unsafe.class;
        for (Field f : unsafeClass.getDeclaredFields()) {
            if ("theUnsafe".equals(f.getName())) {
                f.setAccessible(true);
                try {
                    return (Unsafe) f.get(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public AtomicObject(int value){
        this.value = value;
    }

    public AtomicObject(){
        this(0);
    }


    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }

    public final int getAndDecrement() {
        return unsafe.getAndAddInt(this, valueOffset, -1);
    }

    public final void lazySet(int newValue) {
        unsafe.putOrderedInt(this, valueOffset, newValue);
    }

    public final int getAndSet(int newValue) {
        return unsafe.getAndSetInt(this, valueOffset, newValue);
    }

    public final int get() {
        return value;
    }

    public static void main(String[] args) {
        AtomicObject atomicObject = new AtomicObject(0);
        atomicObject.lazySet(1);
        atomicObject.getAndSet(2);
        int i = atomicObject.get();
        System.out.println(i);

    }


}
