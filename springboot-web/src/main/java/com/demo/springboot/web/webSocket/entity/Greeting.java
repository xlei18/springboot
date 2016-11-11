package com.demo.springboot.web.webSocket.entity;
/**
 * Created by leo on 2016/11/11.
 */

/**
 * Greeting
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/11 10:38
 */
public class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
