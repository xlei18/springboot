package com.leo.demo.web3j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/25 15:12
 */
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.demo.springboot.web"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
