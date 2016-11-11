package com.demo.springboot.web.dbsharding.config;
/**
 * Created by leo on 2016/11/7.
 */

import java.util.Map;

/**
 * DruidDataSourceConfig
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/7 10:05
 */
public class DruidDataSourceConfig {

    private Map<String, DruidConfig> datasources;

    public Map<String, DruidConfig> getDatasources() {
        return datasources;
    }

    public void setDatasources(Map<String, DruidConfig> datasources) {
        this.datasources = datasources;
    }
}
