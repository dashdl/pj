package com.example.shopserver.service.impl;

import com.example.shopserver.mapper.OrdersMapper;
import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.pojo.entity.Orders;
import com.example.shopserver.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 获取所有订单
     * @return
     */
    @Override
    public List<Orders> getAllOrders() {
        return ordersMapper.selectAllOrders();
    }

    /**
     * 获取个人订单
     * @param username
     * @return
     */
    @Override
    public List<Business> getPersonalOrders(String username) {
        return ordersMapper.selectPersonalOrders(username);
    }

    /**
     * 获取商家订单
     * @param username
     * @return
     */
    @Override
    public List<Orders> getBusinessOrders(String username) {
        return ordersMapper.selectBusinessOrders(username);
    }

    /**
     * 添加订单
     * @param orders
     */
    @Override
    public void addOrder(Orders orders) {
        ordersMapper.updateBusinessStatusToOpen(orders);
        ordersMapper.insertOrder(orders);
    }

    /**
     * 更新订单
     * @param orders
     */
    @Override
    public void updateOrder(Orders orders) {
        ordersMapper.updateOrder(orders);
    }

    /**
     * 删除订单
     * @param orderId
     */
    @Override
    public void deleteOrder(Integer orderId) {
        ordersMapper.deleteOrder(orderId);
        ordersMapper.updateBusinessStatusToClose(orderId);
    }
}
