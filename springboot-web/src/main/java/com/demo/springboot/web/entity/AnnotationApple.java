package com.demo.springboot.web.entity;

import com.alibaba.fastjson.JSON;
import com.demo.springboot.web.annotation.FruitColor;
import com.demo.springboot.web.annotation.FruitName;

/**
 * Apple
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/25 15:10
 */
public class AnnotationApple {

    @FruitName("Apple")
    private String appleName;

    @FruitColor(fruitColor= FruitColor.Color.RED)
    private String appleColor;

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }
    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }
    public String getAppleName() {
        return appleName;
    }

    public void displayName(){
        System.out.println("水果的名字是：苹果");
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
