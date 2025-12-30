package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.pojo.entity.Orders;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 获取所有订单
     * @return
     */
    @GetMapping("/allorder")
    public List<Orders> getAllBusinesses() {
        List<Orders> ordersList = ordersService.getAllOrders();
        return ordersList;
    }

    /**
     * 获取个人订单
     * @param username
     * @return
     */
    @GetMapping("/personal/{username}")
    public List<Business> getPersonalOrders(@PathVariable String username) {
        List<Business> personalOrders = ordersService.getPersonalOrders(username);
        return personalOrders;
    }

    /**
     * 获取商家订单
     * @param username
     * @return
     */
    @GetMapping("/business/{username}")
    public List<Orders> getBusinessOrders(@PathVariable String username) {
        List<Orders> businessOrders = ordersService.getBusinessOrders(username);
        return businessOrders;
    }

    /**
     * 添加订单
     * @param orders
     * @return
     */
    @PostMapping("/add")
    public Result addOrder(@RequestBody Orders orders) {
        ordersService.addOrder(orders);
        return Result.success("添加成功");
    }

    /**
     * 更新订单
     * @param orders
     * @return
     */
    @PutMapping("/update/")
    public Result updateOrder(@RequestBody Orders orders) {
        ordersService.updateOrder(orders);
        return Result.success("更新成功");
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
        return Result.success("删除成功");
    }

}
