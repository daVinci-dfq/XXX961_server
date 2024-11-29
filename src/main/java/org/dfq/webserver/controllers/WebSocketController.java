package org.dfq.webserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/WebSocket")
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 广播发送信息
     *
     * @param message 信息
     */
    @MessageMapping("/sendToAll")
    @SendTo("/topic/notice")
    public String sendToAll(String message) {
        return "服务端通知: " + message;
    }

//    /**
//     * 点对点发送消息
//     *
//     * @param principal 当前用户
//     * @param username  接收消息的用户
//     * @param message   消息内容
//     */
//    @MessageMapping("/sendToUser/{username}")
//    public void sendToUser(Principal principal, @DestinationVariable String username, String message) {
//
//        String sender = principal.getName(); // 发送人
//        String receiver = username; // 接收人
//
//        log.info("sender:{}; receiver:{}", sender, receiver);
//        // 发送消息给指定用户 /user/{username}/queue/greeting
//        simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/greeting", new ChatMessage(sender, message));
//    }
}


