package com.example.shopserver.mapper;

import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.pojo.entity.Orders;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrdersMapper {
    /**
     * 查询所有订单
     * @return
     */
    @Select("SELECT o.id, o.cusername, o.customername, o.customerphone, o.customerposition, o.created_at, o.busername, o.business_id FROM orders o JOIN business b ON o.business_id = b.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "cusername", column = "cusername"),
        @Result(property = "customername", column = "customername"),
        @Result(property = "customerphone", column = "customerphone"),
        @Result(property = "customerposition", column = "customerposition"),
        @Result(property = "created_at", column = "created_at"),
        @Result(property = "busername", column = "busername"),
        @Result(property = "business_id", column = "business_id")
    })
    List<Orders> selectAllOrders();

    /**
     * 查询个人订单
     * @param username
     * @return
     */
    @Select("SELECT b.id,b.title,b.description,b.name,b.phone,b.photo FROM business b JOIN orders o ON o.business_id = b.id WHERE o.customername = #{username}")
    List<Business> selectPersonalOrders(String username);

    /**
     * 查询商家订单
     * @param username
     * @return
     */
    @Select("SELECT * FROM orders WHERE busername = #{username}")
    List<Orders> selectBusinessOrders(String username);

    /**
     * 插入订单
     * @param orders
     */
    @Insert("INSERT INTO orders(cusername, customername, customerphone, customerposition, business_id, busername) VALUES (#{cusername}, #{customername}, #{customerphone}, #{customerposition}, #{business_id}, #{busername})")
    void insertOrder(Orders orders);

    /**
     * 更新商品状态为已售出
     * @param orders
     */
    @Update("UPDATE business SET status = 2 WHERE id = #{business_id}")
    void updateBusinessStatusToOpen(Orders orders);

    /**
     * 更新订单信息
     * @param orders
     */
    @Update("UPDATE orders SET cusername = #{cusername}, customerphone = #{customerphone}, customerposition = #{customerposition} WHERE customername = #{customername}")
    void updateOrder(Orders orders);

    /**
     * 删除订单
     * @param orderId
     */
    @Delete("DELETE FROM orders WHERE id = #{orderId}")
    void deleteOrder(Integer orderId);

    /**
     * 更新商品状态为正在售卖
     * @param orderId
     */
    @Update("UPDATE business SET status = 1 WHERE id = (SELECT business_id FROM orders WHERE id = #{orderId})")
    void updateBusinessStatusToClose(Integer orderId);

}
