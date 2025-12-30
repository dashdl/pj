package com.example.shopserver.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private Integer id; // 订单ID
    private String cusername; // 顾客用户名
    private String customername; // 顾客姓名
    private String customerphone; // 顾客电话
    private String customerposition; // 顾客地址
    private LocalDateTime created_at; // 下单时间
    private String busername; // 商家用户名
    private Integer business_id; // 商品ID
}
