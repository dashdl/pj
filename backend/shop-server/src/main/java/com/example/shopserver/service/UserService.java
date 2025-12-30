package com.example.shopserver.service;

import com.example.shopserver.pojo.entity.User;

import java.util.List;


public interface UserService {
    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    Boolean isUsernameExists(String username);

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 获取所有用户信息
     * @return
     */
    List<User> userList();
    
    /**
     * 根据用户名模糊查询用户
     * @param username
     * @return
     */
    List<User> searchUsersByUsername(String username);

    /**
     * 封停用户
     * @param id
     */
    void stopUser(Integer id);

    /**
     * 解除封停用户
     * @param id
     */
    void passUser(Integer id);

    /**
     * 更新用户个人信息
     * @param user
     */
    void updatePersonalInfo(User user);
}
