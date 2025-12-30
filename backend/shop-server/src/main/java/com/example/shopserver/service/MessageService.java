package com.example.shopserver.service;

import com.example.shopserver.pojo.entity.Message;

import java.util.List;

/**
 * 消息服务接口
 */
public interface MessageService {
    
    /**
     * 发送消息
     */
    void sendMessage(Message message);
    
    /**
     * 标记消息为已读
     */
    void markAsRead(Integer messageId);
    
    /**
     * 标记用户所有未读消息为已读
     */
    void markAllAsRead(String username);
    
    /**
     * 查询两个用户之间的对话
     */
    List<Message> getConversation(String user1, String user2);
    
    /**
     * 查询用户的聊天列表
     */
    List<Message> getChatList(String username);
    
    /**
     * 查询用户未读消息数量
     */
    Integer getUnreadCount(String username);
    
    /**
     * 查询用户与特定用户的未读消息数量
     */
    Integer getUnreadCountBetweenUsers(String senderUsername, String receiverUsername);
    
    /**
     * 查询商品相关的消息
     */
    List<Message> getMessagesByBusinessId(Integer businessId);
    
    /**
     * 删除消息
     */
    void deleteMessage(Integer messageId);
    
    /**
     * 查询系统消息
     */
    List<Message> getSystemMessages(String username);
}
