package com.demo.springboot.web.webSocket.controller;
/**
 * Created by leo on 2016/11/11.
 */

import com.demo.springboot.web.webSocket.entity.Greeting;
import com.demo.springboot.web.webSocket.entity.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * GreetingController
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/11 10:39
 */
@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
