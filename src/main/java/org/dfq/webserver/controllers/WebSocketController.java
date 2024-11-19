package org.dfq.webserver.controllers;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @MessageMapping("/chat") // 客户端发送消息的路径
    @SendTo("/topic/messages") // 广播消息的订阅路径
    public Message sendMessage(Message message) {
        return message; // 返回消息给订阅的客户端
    }
}


