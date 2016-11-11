package com.demo.springboot.web.controller;
/**
 * Created by leo on 2016/11/10.
 */

import com.demo.springboot.web.webSocket.TopicMessage;
import com.demo.springboot.web.webSocket.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * WebSocketController
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/10 10:44
 */
@RestController
public class WebSocketController {

    private static int SIZE = 3;
    private Queue<Object> queue = new ArrayDeque<Object>();
    public SimpMessagingTemplate template;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @SubscribeMapping("/init/{coordinationId}")
    public Map<String,Object> init(@DestinationVariable("coordinationId") int coordinationId) {
        System.out.println("------------新用户 " + coordinationId + " 进入，空间初始化---------");
        Map<String, Object> document = new HashMap<String, Object>();
        document.put("chat",queue);
        return document;
    }

    // 广播
    @MessageMapping("/live")
    @SendTo("/topic/live")
    public TopicMessage sendTopic(TopicMessage msg) {
        if (queue.size() >= SIZE) {
            queue.poll();
        }
        queue.offer(msg);
        return msg;
    }

    // 广播 方式写点对点
    @MessageMapping("/point")
    public void sendPoint(UserMessage userMessage) {
        String dest = "/topic/point" + userMessage.getCoordinationId();
        this.template.convertAndSend(dest, userMessage);
    }

    //点对点
    @MessageMapping("/message")
    public void userMessage(UserMessage userMessage) throws Exception {
        this.template.convertAndSendToUser(userMessage.getCoordinationId(), "/message", userMessage);
    }

}
