package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.Message;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息控制器
 */
@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*")
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    /**
     * 发送消息
     */
    @PostMapping("/send")
    public Result sendMessage(@RequestBody Message message) {
        try {
            // 设置服务端时间，确保立即可用
            if (message.getCreatedAt() == null) {
                message.setCreatedAt(java.time.LocalDateTime.now());
            }
            messageService.sendMessage(message);
            // 返回新记录的 id 便于前端对齐
            return Result.success(message.getId());
        } catch (Exception e) {
            return Result.error("消息发送失败: " + e.getMessage());
        }
    }

    /**
     * 标记消息为已读
     */
    @PutMapping("/read/{messageId}")
    public Result markAsRead(@PathVariable Integer messageId) {
        try {
            messageService.markAsRead(messageId);
            return Result.success("消息已标记为已读");
        } catch (Exception e) {
            return Result.error("操作失败: " + e.getMessage());
        }
    }

    /**
     * 标记用户所有未读消息为已读
     */
    @PutMapping("/readAll/{username}")
    public Result markAllAsRead(@PathVariable String username) {
        try {
            messageService.markAllAsRead(username);
            return Result.success("所有消息已标记为已读");
        } catch (Exception e) {
            return Result.error("操作失败: " + e.getMessage());
        }
    }

    /**
     * 查询两个用户之间的对话
     */
    @GetMapping("/conversation")
    public Result getConversation(@RequestParam String user1, @RequestParam String user2) {
        try {
            List<Message> conversation = messageService.getConversation(user1, user2);
            return Result.success(conversation);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户的聊天列表
     */
    @GetMapping("/chatList/{username}")
    public Result getChatList(@PathVariable String username) {
        try {
            List<Message> chatList = messageService.getChatList(username);
            return Result.success(chatList);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户未读消息数量
     */
    @GetMapping("/unreadCount/{username}")
    public Result getUnreadCount(@PathVariable String username) {
        try {
            Integer count = messageService.getUnreadCount(username);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户与特定用户的未读消息数量
     */
    @GetMapping("/unreadCountBetween")
    public Result getUnreadCountBetweenUsers(@RequestParam String senderUsername, @RequestParam String receiverUsername) {
        try {
            Integer count = messageService.getUnreadCountBetweenUsers(senderUsername, receiverUsername);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询商品相关的消息
     */
    @GetMapping("/business/{businessId}")
    public Result getMessagesByBusinessId(@PathVariable Integer businessId) {
        try {
            List<Message> messages = messageService.getMessagesByBusinessId(businessId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 删除消息
     */
    @DeleteMapping("/delete/{messageId}")
    public Result deleteMessage(@PathVariable Integer messageId) {
        try {
            messageService.deleteMessage(messageId);
            return Result.success("消息删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }

    /**
     * 查询系统消息
     */
    @GetMapping("/system/{username}")
    public Result getSystemMessages(@PathVariable String username) {
        try {
            List<Message> messages = messageService.getSystemMessages(username);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
}
