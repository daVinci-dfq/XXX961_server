package org.dfq.webserver.config;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // WebSocket连接建立时
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket连接已建立: " + session.getId());
    }

    // 接收到的文本消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("收到消息: " + message.getPayload());

        // 回应消息
        session.sendMessage(new TextMessage("服务器回应: " + message.getPayload()));
    }

    // WebSocket连接关闭时
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket连接已关闭: " + session.getId());
    }

    // 传输错误
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket传输错误: " + exception.getMessage());
        session.close(CloseStatus.SERVER_ERROR);
    }

    // 支持分片消息
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

