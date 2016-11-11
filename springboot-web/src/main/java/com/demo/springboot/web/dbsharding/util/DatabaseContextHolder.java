package com.demo.springboot.web.dbsharding.util;
/**
 * Created by leo on 2016/11/7.
 */

/**
 * DatabaseContextHolder
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/7 10:43
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return ((String) contextHolder.get());
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
