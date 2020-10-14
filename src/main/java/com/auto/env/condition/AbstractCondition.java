package com.auto.env.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class AbstractCondition implements Condition {

    private final String OS_KEY = "os.name";

    protected abstract String getOsName();

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return conditionContext.getEnvironment().getProperty(OS_KEY).equalsIgnoreCase(getOsName());
    }
}
