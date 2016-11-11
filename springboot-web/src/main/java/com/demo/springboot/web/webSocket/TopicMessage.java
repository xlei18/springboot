package com.demo.springboot.web.webSocket;
/**
 * Created by leo on 2016/11/10.
 */

/**
 * TopicMessage
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/10 17:57
 */
public class TopicMessage {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }
}
