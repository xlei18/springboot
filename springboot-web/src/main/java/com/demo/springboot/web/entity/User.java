package com.demo.springboot.web.entity;
/**
 * Created by leo on 2016/10/28.
 */

import com.alibaba.fastjson.JSON;

/**
 * RestfulUser
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/28 13:04
 */
public class User {

    private Long id;
    private String name;
    private Integer age;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
