package com.auto.env.condition;

public class LinuxCondition extends AbstractCondition {

    private final String OS_NAME = "linux";

    @Override
    protected String getOsName() {
        return OS_NAME;
    }
}
