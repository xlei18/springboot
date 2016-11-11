package com.demo.springboot.web.webSocket.controller;
/**
 * Created by leo on 2016/11/11.
 */

import com.demo.springboot.web.webSocket.entity.Greeting;
import com.demo.springboot.web.webSocket.entity.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * GreetingController
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/11 10:39
 */
@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("greeting(), " + message.getName() + "!");
    }

    @MessageMapping("/toPoint")
    public void send(HelloMessage message) throws Exception {
        System.out.println("name = " + message.getName());
        messagingTemplate.convertAndSendToUser(message.getName(), "/queue/point", new Greeting("send(), " + message.getName() + "!"));

    }

//    @MessageMapping("/toPoint")
//    @SendToUser("/queue/point")
//    public Greeting point(@Payload String xxx, HelloMessage message) throws Exception {
//        System.out.println(xxx);
//        Thread.sleep(1000); // simulated delay
//        template.convertAndSendToUser(xxx, "//queue/point","convertAndSendToUser()" + message.getName() + "!");
//        return new Greeting("point(), " + message.getName() + "!");
//    }

}
