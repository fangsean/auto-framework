package com.auto.common.classloader;

/**
 * @author auto.yin [auto.yin@gmail.com]
 * 2018-12-22
 * @Description: <p></p>
 */
public class TaskIntfImpl implements TaskIntf {
    static {
        System.out.println("我被加载了");
    }

    @Override
    public void execute() {
        System.out.println("我被执行了 我被" + this.getClass().getClassLoader() + "加载");
    }
}

