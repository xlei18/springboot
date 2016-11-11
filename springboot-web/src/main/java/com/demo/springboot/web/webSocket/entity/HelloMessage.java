package com.demo.springboot.web.webSocket.entity;
/**
 * Created by leo on 2016/11/11.
 */

/**
 * HelloMessage
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/11 10:37
 */
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
