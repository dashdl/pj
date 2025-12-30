package com.example.shopserver.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id; // 用户ID
    private String username; // 用户名
    private String password; // 密码
    private String avatarUrl; // 头像URL
    private Integer status; // 用户状态
    private LocalDateTime createdAt; // 注册时间
}
