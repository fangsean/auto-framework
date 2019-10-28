package com.auto.common.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Create Date Time: 2019-05-11 .
 * Update Date Time:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@ContextConfiguration
public @interface AfAlias {
}
