package com.demo.springboot.web.webSocket;
/**
 * Created by leo on 2016/11/10.
 */

/**
 * UserMessage
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/10 17:57
 */
public class UserMessage {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCoordinationId(String coordinationId) {
        this.coordinationId = coordinationId;
    }

    public String getMsg() {
        return msg;
    }

    public String getCoordinationId() {
        return coordinationId;
    }

    private String name;
    private String msg;
    private String coordinationId;
}
