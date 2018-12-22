package com.auto.algorithm.random;


import com.auto.api.Flector;
import com.auto.entity.RewardPrize;
import com.auto.api.Flector;
import com.auto.entity.RewardPrize;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
public class WeightRandom<T> {
    private static final Logger logger = LoggerFactory.getLogger(WeightRandom.class);

    private static Random random = new Random();

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
            if (CollectionUtils.isEmpty(ts) || ((weightSum = ts.stream().mapToInt($t -> {return flector.getter($t, weightColumns);}).sum()) <= 0)) {
                return t;
            }
            Integer m = 0;
            Integer weight = 0;
            //取值 n=[0,weightSum)
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

    public static void main(String[] args) {

        List<RewardPrize> prizes = new ArrayList<RewardPrize>();
        RewardPrize wc1 = new RewardPrize("AAAAAAAA", 4, 1);
        RewardPrize wc2 = new RewardPrize("BBBBBBBB", 1, 1);
        RewardPrize wc3 = new RewardPrize("CCCCCCCC", -1, 1);
        RewardPrize wc4 = new RewardPrize("DDDDDDDD", -1, 1);
        prizes.add(wc1);
        prizes.add(wc2);
        prizes.add(wc3);
        prizes.add(wc4);

        for (int i = 0; i < 10000; i++) {
            RewardPrize weights = percentage(prizes, RewardPrize.class, "weights");
            System.out.println(weights);
        }

    }


}
