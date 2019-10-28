package com.auto.algorithm.random;


import com.auto.util.Flector;
import com.auto.entity.RewardPrize;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.*;


/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
public class WeightRandom<T> {
    private static final Logger logger = LoggerFactory.getLogger(WeightRandom.class);

    /**
     * 计算权重
     *
     * @param ts            数据
     * @param clazz         数据类型
     * @param weightColumns 权重字段
     * @param <T>
     * @return t
     */
    public static <T> T percentage(List<T> ts, Class<T> clazz, String weightColumns) {
        T t = null;
        try {
            Flector<T> flector = new Flector<T>(clazz);
            Integer weightSum = 00;
            //计算总权重
            if (CollectionUtils.isEmpty(ts) || ((weightSum = ts.stream().mapToInt($t -> {
                return flector.getter($t, weightColumns);
            }).sum()) <= 0)) {
                return t;
            }
            Integer m = 0;
            Integer weight = 0;
            //取值 n=[0,weightSum)
            Random random = new Random();
            Integer n = random.nextInt(weightSum);
            for (T tmp : ts) {
                //先计算weight 权重值小的选重率越小，反之，权重值大的选重率越高
                weight += (Integer) flector.getter(tmp, weightColumns);
                /*根据随机权重分区*/
                if (n >= m && n < m + weight) {
                    t = tmp;
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("计算权重出错原因：" + e.getMessage());
        }
        return t;
    }


    /**
     * 模仿 RandomLoadBalance 方式
     *
     * @param ts
     * @param clazz
     * @param weightColumns
     * @param <T>
     * @return
     */
    public static <T> T selector(List<T> ts, Class<T> clazz, String weightColumns) {
        Flector<T> flector = new Flector<T>(clazz);
        Integer totalWeight = 00;
        //计算总权重
        int length = ts.size();
        boolean sameWeight = true;
        Random random = new Random();

        int offset;
        int i;
        for (offset = 0; offset < length; ++offset) {
            i = (int) flector.getter(ts.get(offset), weightColumns);
            totalWeight += i;
            if (sameWeight && offset > 0 && i != (int) flector.getter(ts.get(offset - 1), weightColumns)) {
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            offset = random.nextInt(totalWeight);
            for (i = 0; i < length; ++i) {
                offset -= (int) flector.getter(ts.get(i), weightColumns);
                if (offset < 0) {
                    return ts.get(i);
                }
            }
        }
        return ts.get(random.nextInt(length));
    }

    public static void main(String[] args) {

        List<RewardPrize> prizes = new ArrayList<RewardPrize>();
        RewardPrize wc1 = new RewardPrize("AAAAAAAA", 4, 7);
        RewardPrize wc2 = new RewardPrize("BBBBBBBB", 1, 1);
        RewardPrize wc3 = new RewardPrize("CCCCCCCC", -1, 1);
        RewardPrize wc4 = new RewardPrize("DDDDDDDD", -1, 1);
        prizes.add(wc1);
        prizes.add(wc2);
        prizes.add(wc3);
        prizes.add(wc4);


        MultiHashMap multiHashMap = new MultiHashMap();

        for (int i = 0; i < 10000; i++) {
            RewardPrize weights = selector(prizes, RewardPrize.class, "weights");
            multiHashMap.put(weights.getSupplierNo(), weights);
            System.out.println(weights);
        }

        Set set = multiHashMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            ArrayList o = (ArrayList) multiHashMap.get(next);
            System.out.println(next + "->" + o.size());
        }

    }

}
