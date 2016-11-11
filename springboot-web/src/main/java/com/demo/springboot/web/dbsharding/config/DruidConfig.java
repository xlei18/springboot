package com.demo.springboot.web.dbsharding.config;
/**
 * Created by leo on 2016/11/7.
 */

/**
 * DruidConfig
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/7 10:06
 */
public class DruidConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private boolean isDefault = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
