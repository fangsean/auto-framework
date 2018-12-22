package com.auto.algorithm.random;

import com.auto.entity.RewardPrize;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
@Slf4j
public class MathRandom {

    public RewardPrize PercentageRandom(List<RewardPrize> prizes) {
        int random = -2;
        try {
            //计算总权重
            int sumWeight = prizes.stream().filter(prize -> 1 == prize.getStatus()).mapToInt(RewardPrize::getWeights).sum();

            double randomNumber = Math.random();
            double d1 = 0;
            double d2 = 0;

            for (int i = 0; i < prizes.size(); i++) {
                d2 += Double.parseDouble(String.valueOf(prizes.get(i).getWeights())) / sumWeight;
                if (i == 0) {
                    d1 = 0;
                } else {
                    d1 += Double.parseDouble(String.valueOf(prizes.get(i - 1).getWeights())) / sumWeight;
                }
                if (randomNumber >= d1 && randomNumber <= d2) {
                    random = i;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("生成抽奖随机数出错，出错原因：" + e.getMessage());
            random = -1;
        }
        return prizes.get(random);
    }

    /**
     * 测试主程序
     *
     * @param agrs
     */
    public static void main(String[] agrs) {
        int i = 0;
        MathRandom a = new MathRandom();
        List<RewardPrize> prizes = new ArrayList();
        RewardPrize rp1 = new RewardPrize(2944L, "8809505542204", 6, 1);
        RewardPrize rp2 = new RewardPrize(2314L, "101009", 2, 1);
        RewardPrize rp3 = new RewardPrize(2991L, "32535345345", 4, 1);
        RewardPrize rp4 = new RewardPrize(2317L, "jmfense180ml", 6, 0);
        RewardPrize rp5 = new RewardPrize(4436L, "JM05542204", 8, 1);

        prizes.add(rp1);
        prizes.add(rp2);
        prizes.add(rp3);
        prizes.add(rp4);
        prizes.add(rp5);


        for (i = 0; i < 10000; i++)// 打印100个测试概率的准确性
        {
            System.out.println(a.PercentageRandom(prizes));
        }
    }


}
