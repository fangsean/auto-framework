package com.auto.common.mertics;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lhc
 * @date 2017年12月29日 下午5:14:20
 * 统计方法执行时间
 */
public class JProfileUtil {
     protected static final Logger LOGGER = LoggerFactory.getLogger(JProfileUtil.class);

    private static final Long defaultTime = 3000L ;

    public JProfileUtil() {
    }

    public static void start(String message) {
        JProfile.start(message);
    }

    public static void reset() {
        JProfile.release();
        long duration = JProfile.getDuration();
       /* if(duration > (long)Math.max(500, defaultTime)) {
            LOGGER.warn("Order request returned in {}ms\n{}\n", Long.valueOf(duration), getDetail());
        }*/

        JProfile.reset();
    }

    private static String getDetail() {
        return JProfile.dump("Detail: ", "        ");
    }
}
