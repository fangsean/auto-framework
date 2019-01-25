package com.auto.active.mq;

import java.util.Map;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-20
 * @Description: <p></p>
 */
abstract
public class MethodMessage {

    protected final Map<String, Object> params;

    public MethodMessage(Map<String, Object> params) {
        this.params = params;
    }

    public abstract void execute();

}
