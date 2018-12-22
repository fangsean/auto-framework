package com.auto.algorithm.data;

public class BloomFilters {

    private int arraySize;
    private int[] array;

    public BloomFilters(int arraySize) {
        this.arraySize = arraySize;
        array = new int[arraySize];
    }

    public void addByHashCode(String key) {

        int hashCode$1 = hashCode$1(key);
        int hashCode$2 = hashCode$2(key);
        int hashCode$3 = hashCode$3(key);

        array[hashCode$1 % arraySize] = 1;
        array[hashCode$2 % arraySize] = 1;
        array[hashCode$3 % arraySize] = 1;

    }

    public boolean exist(String key) {

        int hashCode$1 = hashCode$1(key);
        int hashCode$2 = hashCode$2(key);
        int hashCode$3 = hashCode$3(key);

        int hashCodeIndex$1 = array[hashCode$1 % arraySize];
        if (hashCodeIndex$1 == 0) {
            return false;
        }
        int hashCodeIndex$2 = array[hashCode$2 % arraySize];
        if (hashCodeIndex$2 == 0) {
            return false;
        }
        int hashCodeIndex$3 = array[hashCode$3 % arraySize];
        if (hashCodeIndex$3 == 0) {
            return false;
        }

        return true;
    }

    private int hashCode$1(String key) {
        int hash = 0, i = 0;
        for (; i < key.length(); i++) {
            hash = (2 ^ 5) * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    private int hashCode$2(String key) {

        final int p = 16777619;
        int hash = (int) 2166136261L, i = 0;

        for (; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        return Math.abs(hash);
    }

    private int hashCode$3(String key) {

        int hash = 0, i = 0;
        for (; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += hash << 10;
            hash ^= hash >> 6;
        }
        hash += hash << 3;
        hash ^= hash >> 11;
        hash += hash << 15;

        return Math.abs(hash);
    }

}
