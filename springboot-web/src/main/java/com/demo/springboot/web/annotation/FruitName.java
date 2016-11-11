package com.demo.springboot.web.annotation;

import java.lang.annotation.*;

/**
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/25 15:07
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {

    String value() default "";

}
