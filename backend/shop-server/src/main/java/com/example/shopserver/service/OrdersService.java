package com.example.shopserver.service;

import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.pojo.entity.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> getAllOrders();

    List<Business> getPersonalOrders(String username);

    List<Orders> getBusinessOrders(String username);

    void addOrder(Orders orders);

    void updateOrder(Orders orders);

    void deleteOrder(Integer orderId);
}
