package com.demo.springboot.erueka;
/**
 * Created by leo on 2016/10/31.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EruekaApplication
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/31 14:53
 */
@EnableEurekaServer
@SpringBootApplication
public class EruekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EruekaApplication.class, args);
    }
}
