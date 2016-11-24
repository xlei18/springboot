package com.demo.springboot.eruekaclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EruekaClientApplication {

    @Value("${demo.tet1}")
    private String str;

    @RequestMapping("/")
    public String home() {
        System.out.println("=======================str:" + str);
        return "Hello world";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(EruekaClientApplication.class).web(true).run(args);
    }
}
