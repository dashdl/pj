package com.example.shopserver.pojo.entity;

import java.time.LocalDateTime;

/**
 * 消息实体类
 */
public class Message {
    private Integer id;
    private String senderUsername;
    private String receiverUsername;
    private Integer businessId;
    private String content;
    private Integer messageType;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    // 构造函数
    public Message() {}

    public Message(String senderUsername, String receiverUsername, Integer businessId, 
                   String content, Integer messageType) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.businessId = businessId;
        this.content = content;
        this.messageType = messageType;
        this.isRead = false;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderUsername='" + senderUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' +
                ", businessId=" + businessId +
                ", content='" + content + '\'' +
                ", messageType=" + messageType +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                ", readAt=" + readAt +
                '}';
    }
}
