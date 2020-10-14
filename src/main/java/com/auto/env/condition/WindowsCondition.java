package com.auto.env.condition;

public class WindowsCondition extends AbstractCondition {

    private final String OS_NAME = "windows";

    @Override
    protected String getOsName() {
        return OS_NAME;
    }

}
