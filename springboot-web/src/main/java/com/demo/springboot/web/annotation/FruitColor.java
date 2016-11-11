package com.demo.springboot.web.annotation;

import java.lang.annotation.*;

/**
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/25 15:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {

    /**
     * 颜色枚举
     * @author peida
     *
     */
    public enum Color{ BULE,RED,GREEN};

    /**
     * 颜色属性
     * @return
     */
    Color fruitColor() default Color.GREEN;

}
