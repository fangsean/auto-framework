package com.auto.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-19
 * @Description: <p></p>
 */
public class CacheReference extends SoftReference {


    public CacheReference(Object referent) {
        super(referent);
    }

    public CacheReference(Object referent, ReferenceQueue q) {
        super(referent, q);
    }

    public static void main(String[] args) {

        System.runFinalization();

    }


}