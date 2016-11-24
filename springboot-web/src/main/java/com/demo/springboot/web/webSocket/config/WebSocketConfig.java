package com.demo.springboot.web.webSocket.config;
/**
 * Created by leo on 2016/11/11.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * WebSocketConfig
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/11 10:39
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 在topic域上可以向客户端发消息
        registry.enableSimpleBroker("/user","/topic");
        // 客户端向服务端广播发送时的主题上面需要加”/app”作为前缀
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 这个和客户端创建连接时的url有关,后面在客户端的代码中可以看到,允许跨域
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS();
//        registry.addEndpoint("/toPoint").setAllowedOrigins("*").withSockJS();
    }

}