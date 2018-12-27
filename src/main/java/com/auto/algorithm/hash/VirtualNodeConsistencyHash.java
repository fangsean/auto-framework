package com.auto.algorithm.hash;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-27
 * @Description: <p>一致性hash:带虚拟节点</p>
 */
public class VirtualNodeConsistencyHash {

    // 待添加入Hash环的服务器列表
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111", "192.168.0.4:111"};

    //真实结点列表
    private static List<String> realNode = new LinkedList<>();

    //虚拟节点
    private static SortedMap<Integer, String> virtualNode = new TreeMap<Integer, String>();

    /**
     * 程序初始化，将所有的服务器放入sortedMap中
     */
    static {
        for (int i = 0; i < servers.length; i++) {

            //原始的服务器添加到真实结点列表
            realNode.add(servers[i]);
        }
        for (String node : realNode) {
            //添加虚拟节点
            for (int j = 0; j < servers.length; j++) {
                int hash = virtalNodeHash(node, j);
                System.out.println("[" + getNode(node, j) + "]加入集合中, 其Hash值为" + hash);
                virtualNode.put(hash, getNode(node, j));
            }
        }
        System.out.println();
    }


    /**
     * FNV1_32_HASH
     * 不带虚拟节点
     *
     * @return
     */
    private static int fnv132Hash(String str) {
        final int p = 16777619;
        long hash = 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        /*hash += hash << 5;*/
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return (int)hash;
    }

    /**
     * 带虚拟节点
     *
     * @param str
     * @return
     */
    private static int virtalNodeHash(String str, int i) {
        return fnv132Hash(getNode(str, i));
    }

    private static String getNode(String str, int i) {
        String suffix = "&&VN";
        return str + suffix + i;
    }


    /**
     * 得到应当路由到的结点
     */
    private static String getServer(String node) {
        // 得到带路由的结点的Hash值
        int hash = fnv132Hash(node);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = virtualNode.tailMap(hash);
        // 第一个Key就是顺时针过去离node最近的那个结点
        int i = subMap.firstKey();
        // 返回对应的服务器名称
        return subMap.get(i);
    }

    private static String getServerSufix(String node) {
        String virtualNode = getServer(node);
        return virtualNode.substring(0, virtualNode.indexOf("&&"));
    }


    public static void main(String[] args) {
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" + fnv132Hash(nodes[i]) + ", 被路由到结点[" + getServerSufix(nodes[i]) + "]");
    }


}
