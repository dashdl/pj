package com.example.shopserver.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 发布商品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {
    private Integer id; // 商品ID
    private String username; // 商家用户名
    private String name; // 商家姓名
    private String phone; // 商家电话
    private String title; // 商品标题
    private String description; // 商品描述
    private String content; // 商品内容
    private String position; // 发货地址
    private Integer type; // 商品类型
    private String status; // 商品状态
    private String photo; // 商品图片
    private String price; // 商品价格
    private LocalDateTime created_at; // 发布商品时间
}
