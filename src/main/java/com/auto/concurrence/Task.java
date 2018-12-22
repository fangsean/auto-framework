package com.auto.concurrence;

import com.auto.concurrence.conf.ThreadAsyncConfigurer;
import com.auto.util.NumUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
@Service
@Log4j2
public class Task {

    private final static int threadCount = 60;

    @Autowired
    ThreadAsyncConfigurer asyncConfigurer;

    private void generate(List itemDatas) {

        /*threadNum = (itemDatas.size() % 10) == 0 ? (itemDatas.size() / 10) : (itemDatas.size() / 10) + 1;*/ // 计算需要开启的线程数
        //按方程计算threadNum数
        Integer threadNum = asyncConfigurer.getConfig().getCorePoolSize();
        threadNum += NumUtil.getNum(itemDatas.size());
        //根据线程池状态轮询
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        try {
            itemDatas.stream().forEach(itemData -> { // 提交任务
                service.submit(() -> {
                    /*do(itemData);*/ // 计算
                });
            });
        } catch (Exception e) {
        } finally {
            service.shutdown();
        }
        while (!service.isTerminated()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
