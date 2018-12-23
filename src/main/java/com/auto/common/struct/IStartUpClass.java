package com.auto.common.struct;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * @Description: <p></p>
 */
public interface IStartUpClass {
    /**
     * 启动类的执行方法, 为使各个启动类的不正常启动不影响其他启动类和进程, 要求该方法不能抛出异常

     * 由于该方法是在启动WEB服务器的时候被执行, 因此实现时要考虑程序是否会阻塞, 考虑以下两点:
     * 1. 如果程序会一直阻塞, 则在该方法内部应建立一个线程封装类来封装真正要执行的方法, 避免永久性阻塞

     * 2. 如果程序是短暂阻塞, 且是在WEB服务启动之前必须初始化的, 如资料加载等, 则直接实现该方法并调用相应程序

     */
    void execute();
}
