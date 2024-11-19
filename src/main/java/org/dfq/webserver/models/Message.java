package org.dfq.webserver.models;

public class Message {
    private String sender;
    private String content;
    private String type; // 消息类型，可以是 "CHAT", "JOIN", "LEAVE" 等

    // Getters and Setters

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

