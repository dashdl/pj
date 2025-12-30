package com.example.shopserver.service.impl;

import com.example.shopserver.mapper.MessageMapper;
import com.example.shopserver.pojo.entity.Message;
import com.example.shopserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息服务实现类
 */
@Service
public class MessageServiceImpl implements MessageService {
    
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void sendMessage(Message message) {
        messageMapper.insertMessage(message);
    }

    @Override
    public void markAsRead(Integer messageId) {
        messageMapper.markAsRead(messageId);
    }

    @Override
    public void markAllAsRead(String username) {
        messageMapper.markAllAsReadByUsername(username);
    }

    @Override
    public List<Message> getConversation(String user1, String user2) {
        return messageMapper.selectConversation(user1, user2);
    }

    @Override
    public List<Message> getChatList(String username) {
        // 兼容旧返回，直接透传 DTO 列表（字段名与前端容错一致）
        return (List) messageMapper.selectChatListDto(username);
    }

    @Override
    public Integer getUnreadCount(String username) {
        return messageMapper.selectUnreadCount(username);
    }

    @Override
    public Integer getUnreadCountBetweenUsers(String senderUsername, String receiverUsername) {
        return messageMapper.selectUnreadCountBetweenUsers(senderUsername, receiverUsername);
    }

    @Override
    public List<Message> getMessagesByBusinessId(Integer businessId) {
        return messageMapper.selectMessagesByBusinessId(businessId);
    }

    @Override
    public void deleteMessage(Integer messageId) {
        messageMapper.deleteMessage(messageId);
    }

    @Override
    public List<Message> getSystemMessages(String username) {
        return messageMapper.selectSystemMessages(username);
    }
}
