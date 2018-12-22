package com.auto.common.job;

import lombok.extern.slf4j.Slf4j;

/**
 * @author auto.yin<auto.yin @ gmail.com>
 * @Description: <p></p>
 */
@Slf4j
public class ScanWorkJobProcess {

    /**本地异常日志记录对象 */

    public static boolean flag = false;

    public void begin() {
        System.out.println("ScanWorkJobProcess.begin");
    }
    public void over() {
        System.out.println("ScanWorkJobProcess.over");
    }

    public void process001(boolean flag) {
        System.out.println("ScanWorkJob.process001");
    }

    public void process002(boolean flag) {
        System.out.println("ScanWorkJob.process002");
    }

    public void process003(boolean flag) {
        System.out.println("ScanWorkJob.process003");
    }


}
