package com.example.shopserver.pojo.entity;

import java.time.LocalDateTime;

public class ChatItem {
    private String otherUsername;
    private LocalDateTime lastMessageTime;

    public String getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(String otherUsername) {
        this.otherUsername = otherUsername;
    }

    public LocalDateTime getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}


