package com.example.shopserver.mapper;

import com.example.shopserver.pojo.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    
    /**
     * 发送消息
     */
    @Insert("INSERT INTO messages(sender_username, receiver_username, business_id, content, message_type, created_at) " +
            "VALUES (#{senderUsername}, #{receiverUsername}, #{businessId}, #{content}, #{messageType}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertMessage(Message message);

    /**
     * 标记消息为已读
     */
    @Update("UPDATE messages SET is_read = 1, read_at = NOW() WHERE id = #{id}")
    void markAsRead(Integer id);

    /**
     * 标记用户所有未读消息为已读
     */
    @Update("UPDATE messages SET is_read = 1, read_at = NOW() WHERE receiver_username = #{username} AND is_read = 0")
    void markAllAsReadByUsername(String username);

    /**
     * 查询两个用户之间的对话
     */
    @Select("SELECT id, sender_username AS senderUsername, receiver_username AS receiverUsername, " +
            "business_id AS businessId, content, message_type AS messageType, is_read AS isRead, " +
            "created_at AS createdAt, read_at AS readAt " +
            "FROM messages WHERE (sender_username = #{user1} AND receiver_username = #{user2}) OR " +
            "(sender_username = #{user2} AND receiver_username = #{user1}) ORDER BY created_at ASC")
    List<Message> selectConversation(@Param("user1") String user1, @Param("user2") String user2);

    /**
     * 查询用户的聊天列表
     */
    @Select("SELECT DISTINCT " +
            "CASE WHEN sender_username = #{username} THEN receiver_username ELSE sender_username END as otherUsername, " +
            "MAX(created_at) as lastMessageTime " +
            "FROM messages " +
            "WHERE sender_username = #{username} OR receiver_username = #{username} " +
            "GROUP BY otherUsername " +
            "ORDER BY lastMessageTime DESC")
    List<com.example.shopserver.pojo.entity.ChatItem> selectChatListDto(String username);

    /**
     * 查询用户未读消息数量
     */
    @Select("SELECT COUNT(*) FROM messages WHERE receiver_username = #{username} AND is_read = 0")
    Integer selectUnreadCount(String username);

    /**
     * 查询用户与特定用户的未读消息数量
     */
    @Select("SELECT COUNT(*) FROM messages WHERE sender_username = #{senderUsername} AND receiver_username = #{receiverUsername} AND is_read = 0")
    Integer selectUnreadCountBetweenUsers(@Param("senderUsername") String senderUsername, @Param("receiverUsername") String receiverUsername);

    /**
     * 查询商品相关的消息
     */
    @Select("SELECT id, sender_username AS senderUsername, receiver_username AS receiverUsername, " +
            "business_id AS businessId, content, message_type AS messageType, is_read AS isRead, " +
            "created_at AS createdAt, read_at AS readAt FROM messages WHERE business_id = #{businessId} ORDER BY created_at DESC")
    List<Message> selectMessagesByBusinessId(Integer businessId);

    /**
     * 删除消息
     */
    @Delete("DELETE FROM messages WHERE id = #{id}")
    void deleteMessage(Integer id);

    /**
     * 查询系统消息
     */
    @Select("SELECT id, sender_username AS senderUsername, receiver_username AS receiverUsername, " +
            "business_id AS businessId, content, message_type AS messageType, is_read AS isRead, " +
            "created_at AS createdAt, read_at AS readAt FROM messages WHERE message_type = 2 AND receiver_username = #{username} ORDER BY created_at DESC")
    List<Message> selectSystemMessages(String username);
}
