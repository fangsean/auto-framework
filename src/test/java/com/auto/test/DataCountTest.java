package com.auto.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.auto.algorithm.data.BloomFilters;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class DataCountTest {

    @Test
    public void hashMapTest() {
        long star = System.currentTimeMillis();
        Set<Integer> hashset = new HashSet<>(10000000);
        for (int i = 0; i < 10000000; i++) {
            hashset.add(i);
        }
        Assert.assertTrue(hashset.contains(1));
        Assert.assertTrue(hashset.contains(2));
        Assert.assertTrue(hashset.contains(3));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }


    @Test
    public void bloomFilterTest() {

        long star = System.currentTimeMillis();
        BloomFilters bloomFilters = new BloomFilters(10000000);
        for (int i = 0; i < 10000000; i++) {
            bloomFilters.addByHashCode(i + "");
        }
        Assert.assertTrue(bloomFilters.exist(1 + ""));
        Assert.assertTrue(bloomFilters.exist(2 + ""));
        Assert.assertTrue(bloomFilters.exist(3 + ""));
        Assert.assertTrue(bloomFilters.exist(99999 + ""));
        Assert.assertFalse(bloomFilters.exist(400230340 + ""));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }

    @Test
    public void guavaTest() {

        long star = System.currentTimeMillis();
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 10000000, 0.01);
        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }
        Assert.assertTrue(filter.mightContain(1));
        Assert.assertTrue(filter.mightContain(2));
        Assert.assertTrue(filter.mightContain(3));
        Assert.assertFalse(filter.mightContain(10000000));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));

    }

    @Test
    public void hash(){

        System.out.println(64>>1);

    }

    @Test
    public void zhongweishu() {

        int a = -20;
        int b = 11;

        int mid = a + (b - a) / 2;
        int mid01 = a + (b - a + 1) / 2;

        int mid1 = (a + b) >> 1;
        int mid11 = (a + b + 1) >> 1;

        System.out.println(mid);
        System.out.println(mid01);
        System.out.println(mid1);
        System.out.println(mid11);

    }

}
