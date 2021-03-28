package com.itdy.hqsm.aop;

import java.lang.annotation.*;

/**
 * 标志性注解
 *
 * @author
 * @date 2019
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdviceOne {
}
