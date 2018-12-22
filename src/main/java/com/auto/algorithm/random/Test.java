package com.auto.algorithm.random;

import com.auto.entity.Person;
import com.auto.entity.RewardPrize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
public class Test {

    public static void test$CalcWeight() {

        List<RewardPrize> categorys = new ArrayList<RewardPrize>();
        RewardPrize wc1 = new RewardPrize("AAAAAAAA", 6);
        RewardPrize wc2 = new RewardPrize("BBBBBBBB", 2);
        RewardPrize wc3 = new RewardPrize("CCCCCCCC", 2);
        RewardPrize wc4 = new RewardPrize("DDDDDDDD", 8);
        categorys.add(wc1);
        categorys.add(wc2);
        categorys.add(wc3);
        categorys.add(wc4);

        CalcWeight calcWeight = new CalcWeight();
        for (int i=0;i<10000;i++){
            Map<String, Integer> calc = calcWeight.getCalc(categorys);
            System.out.println(calc);
        }
    }


    public static void test$RandomEngine() {
        Map<String, Integer> keyChanceMap = new HashMap<String, Integer>();
        keyChanceMap.put("a", 30);
        keyChanceMap.put("b", 10);
        keyChanceMap.put("c", 40);
        keyChanceMap.put("d", 20);

        RandomEngine.comp(keyChanceMap);

    }


    public static void test$WeightRandom() {

        List<Person> prizes = new ArrayList<Person>();
        Person wc1 = new Person("AAAAAAAA", 2);
        Person wc2 = new Person("BBBBBBBB", 4);
        Person wc3 = new Person("CCCCCCCC", 6);
        Person wc4 = new Person("DDDDDDDD", 8);
        prizes.add(wc1);
        prizes.add(wc2);
        prizes.add(wc3);
        prizes.add(wc4);

        for (int i = 0; i < 10000; i++) {
            Person weights = WeightRandom.percentage(prizes, Person.class, "num");
            System.out.println(weights);
        }
    }

    public static void main(String[] args) {
        test$RandomEngine();
    }




}
