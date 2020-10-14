package com.auto.algorithm.tree.menu;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树型转换工具
 */
public class TreeTransfer {

    /**
     * @param data
     * @param groupCollect
     * @param selfMapper
     * @param <K>
     * @param <T>
     * @return
     */
    private static <K, T extends TreeObject> T transf(Function<? super T, ? extends K> selfMapper,
                                                      Map<K, List<T>> groupCollect, T data) {
        List<T> datas = groupCollect.get(selfMapper.apply(data));
        if (CollectionUtils.isEmpty(datas)) {
            data.setHide(true);
            return data;
        }
        for (T t : datas) {
            transf(selfMapper, groupCollect, t);
        }
        data.setChildren(datas);
        return data;
    }


    /**
     * @param datas
     * @param parentMapper
     * @param selfMapper
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T extends TreeObject> List<T> transf(Function<? super T, ? extends K> parentMapper,
                                                           Function<? super T, ? extends K> selfMapper,
                                                           List<T> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            return datas;
        }
        Map<K, T> collect = datas.stream().collect(Collectors.toMap(selfMapper, Function.identity(), (old, now) -> now));
        // 分组
        final Map<K, List<T>> groupCollect = datas.stream().collect(Collectors.groupingBy(parentMapper));
        for (Map.Entry<K, List<T>> entry : groupCollect.entrySet()) {
            K k = entry.getKey();
            // 有且只有一个根部，就是 collect.get(k)
            T data = collect.get(k);
            if (null == data) {
                continue;
            }
            // 转换树形结构数据
            transf(selfMapper, groupCollect, data);
            if (null != collect.get(parentMapper.apply(data))) {
                data.setHide(true);
            }
            // 接入数据
            // ...
        }

        // 保留外层数据
        return datas.stream().filter(T::isShow).collect(Collectors.toList());
    }



    public static void main(String[] args) {
        List<TreeMenuNode> treeNodeList = Lists.newLinkedList();
        {
            treeNodeList.add(TreeMenuNode.builder().parentId("--").id("0").name("0").sort(0).build());
            treeNodeList.add(TreeMenuNode.builder().parentId("0").id("1").name("1").sort(1).build());
            treeNodeList.add(TreeMenuNode.builder().parentId("--").id("2").name("2").sort(2).build());
            treeNodeList.add(TreeMenuNode.builder().parentId("2").id("3").name("3").sort(3).build());
            treeNodeList.add(TreeMenuNode.builder().parentId("0").id("4").name("4").sort(4).build());
        }

        List<TreeMenuNode> transfer = transf(TreeMenuNode::getParentId, TreeMenuNode::getId, treeNodeList);

        System.out.println(transfer);
    }

}
