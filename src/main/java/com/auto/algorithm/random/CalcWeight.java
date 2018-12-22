package com.auto.algorithm.random;

import com.auto.entity.RewardPrize;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
public class CalcWeight {

    public  Map<String, Integer> getCalc(List<RewardPrize> beans){
        Map<String, Integer> showMap = null;
        Double sum = getSum(beans);
        Double random = 0.0;
        showMap = new LinkedHashMap<String, Integer>();
        for(int i = 0; i < 7; i++) {
            random = getRandom(sum);
            RewardPrize cw = getKW(beans, random);
            if(showMap.containsKey(cw.getId())) {
                showMap.put(cw.getSupplierNo(), showMap.get(cw.getSupplierNo()) + 1);
            } else {
                showMap.put(cw.getSupplierNo(),1);
            }
        }
        return showMap;
    }

    public  RewardPrize getKW(List<RewardPrize> nodes, Double rd) {
        RewardPrize ret = null;
        int curWt = 0;
        for(RewardPrize n : nodes){
            curWt += n.getWeights();
            if(curWt >= rd) {
                ret = n;
                break;
            }
        }
        return ret;
    }
    public static Double getSum(List<RewardPrize> nodes) {
        Double sum = 0.0;
        for(RewardPrize n : nodes)
            sum += n.getWeights();
        return sum;
    }
    public static Double getRandom(Double seed) {
        return (double)Math.round(Math.random() * seed);
    }

}
